package com.duyp.architecture.mvvm.data.remote;

import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;

import com.duyp.androidutils.network.CustomTrustManager;
import com.duyp.androidutils.network.Tls12SocketFactory;
import com.duyp.architecture.mvvm.data.BuildConfig;
import com.duyp.architecture.mvvm.data.remote.interceptors.AuthorizationInterceptor;
import com.duyp.architecture.mvvm.utils.RemoteConstants;
import com.google.gson.Gson;

import java.io.File;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.annotation.Nullable;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.X509TrustManager;

import io.reactivex.schedulers.Schedulers;
import okhttp3.Cache;
import okhttp3.ConnectionSpec;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.ResponseBody;
import okhttp3.TlsVersion;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.CallAdapter;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by duypham on 7/23/17.
 * Factory class for creating ok http client and services
 */

public class ServiceFactory {

    public interface UserTokenProducer {
        String getUserToken();
    }

    public static <T> T makeService(Class<T> serviceClass, Gson gson, OkHttpClient okHttpClient) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BuildConfig.REST_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(new ApiResponseCallAdapterFactory())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
                .build();
        return retrofit.create(serviceClass);
    }

    public static OkHttpClient.Builder makeOkHttpClientBuilder(Context context, @NonNull UserTokenProducer producer) {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();

        if (BuildConfig.DEBUG) {
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        }

        OkHttpClient.Builder builder;
        builder = new OkHttpClient.Builder()
                .addInterceptor(new AuthorizationInterceptor(producer))
                .addInterceptor(logging)
                .followRedirects(true)
                .followSslRedirects(true)
                .retryOnConnectionFailure(true)
                .cache(null)
                .connectTimeout(RemoteConstants.TIME_OUT_API, TimeUnit.SECONDS)
                .writeTimeout(RemoteConstants.TIME_OUT_API, TimeUnit.SECONDS)
                .readTimeout(RemoteConstants.TIME_OUT_API, TimeUnit.SECONDS);

        // add cache to client
        final File baseDir = context.getCacheDir();
        if (baseDir != null) {
            final File cacheDir = new File(baseDir, "HttpResponseCache");
            builder.cache(new Cache(cacheDir, 10 * 1024 * 1024)); // 10 MB
        }

        return enableTls12OnPreLollipop(builder);
    }

    /**
     * Enable TLS 1.2 on Pre Lollipop android versions
     * @param client OkHttpClient Builder
     * @return builder with SSL Socket Factory set
     * according to {@link OkHttpClient.Builder#sslSocketFactory(SSLSocketFactory)} deprecation,
     * Please add config SSL with {@link X509TrustManager} by using {@link CustomTrustManager}
     *  * how to enable tls on android 4.4
     * <a href="https://github.com/square/okhttp/issues/2372"></a>
     */
    private static OkHttpClient.Builder enableTls12OnPreLollipop(OkHttpClient.Builder client) {
        if (Build.VERSION.SDK_INT >= 16 && Build.VERSION.SDK_INT < 22) {
            try {
                SSLContext sc = SSLContext.getInstance("TLSv1.2");
                sc.init(null, null, null);
                // TODO: 9/7/17 set SSL socket factory with X509TrustManager
                client.sslSocketFactory(new Tls12SocketFactory(sc.getSocketFactory()));

                ConnectionSpec cs = new ConnectionSpec.Builder(ConnectionSpec.MODERN_TLS)
                        .tlsVersions(TlsVersion.TLS_1_2)
                        .build();

                List<ConnectionSpec> specs = new ArrayList<>();
                specs.add(cs);
                specs.add(ConnectionSpec.COMPATIBLE_TLS);
                specs.add(ConnectionSpec.CLEARTEXT);

                client.connectionSpecs(specs);
            } catch (Exception exc) {
                Log.e("OkHttpTLSCompat", "Error while setting TLS 1.2", exc);
            }
        }

        return client;
    }
}
