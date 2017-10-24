package com.duyp.architecture.mvvm.data.provider;

import com.duyp.architecture.mvvm.data.remote.RemoteConstants;
import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import io.realm.RealmObject;

/**
 * Created by duypham on 10/21/17.
 *
 */

public class GsonProvider {

    /**
     * Make gson with custom date time deserializer
     * @return {@link Gson} object
     */
    public static Gson makeGson() {
        return new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .excludeFieldsWithModifiers(Modifier.FINAL, Modifier.TRANSIENT, Modifier.STATIC)
                .setDateFormat("yyyy-MM-dd HH:mm:ss")
//                .registerTypeAdapter(Date.class, new DateDeserializer())
                .disableHtmlEscaping()
                .setPrettyPrinting()
                .create();
    }

    /**
     * Make gson which {@link DateDeserializer} and compatible with {@link RealmObject}
     * @return {@link Gson} object
     */
    public static Gson makeGsonForRealm() {
        return new GsonBuilder()
                .setExclusionStrategies(new ExclusionStrategy() {
                    @Override
                    public boolean shouldSkipField(FieldAttributes f) {
                        return f.getDeclaringClass().equals(RealmObject.class);
                    }

                    @Override
                    public boolean shouldSkipClass(Class<?> clazz) {
                        return false;
                    }
                })
                .registerTypeAdapter(Date.class, new DateDeserializer())
                .create();
    }

    private static class DateDeserializer implements JsonDeserializer<Date> {
        @Override
        public Date deserialize(JsonElement element, Type arg1, JsonDeserializationContext arg2) throws JsonParseException {
            String date = element.getAsString();
            SimpleDateFormat formatter = new SimpleDateFormat(RemoteConstants.DATE_TIME_FORMAT, Locale.getDefault());
            formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
            try {
                return formatter.parse(date);
            } catch (ParseException e) {
                return null;
            }
        }
    }
}
