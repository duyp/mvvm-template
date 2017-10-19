package com.duyp.architecture.mvvm.data.remote;

import com.duyp.architecture.mvvm.model.remote.ApiResponse;
import com.duyp.architecture.mvvm.test_utils.RemoteTestUtils;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import okhttp3.MediaType;
import okhttp3.ResponseBody;
import retrofit2.Response;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created by duypham on 10/19/17.
 * reference: https://github.com/googlesamples/android-architecture-components
 */

@RunWith(JUnit4.class)
public class ApiResponseTest {

    @Test
    public void exception() {
        Exception exception = new Exception("foo");
        ApiResponse<String> apiResponse = new ApiResponse<>(exception);
        assertThat(apiResponse.links, notNullValue());
        assertThat(apiResponse.body, nullValue());
        assertThat(apiResponse.code, is(500));
        assertThat(apiResponse.errorMessage, is("foo"));
    }

    @Test
    public void success() {
        ApiResponse<String> apiResponse = new ApiResponse<>(Response.success("foo"));
        assertThat(apiResponse.errorMessage, nullValue());
        assertThat(apiResponse.code, is(200));
        assertThat(apiResponse.body, is("foo"));
        assertThat(apiResponse.getNextPage(), is(nullValue()));
    }

    @Test
    public void link() {
        String link = "<https://api.github.com/search/repositories?q=foo&page=2>; rel=\"next\","
                + " <https://api.github.com/search/repositories?q=foo&page=34>; rel=\"last\"";
        okhttp3.Headers headers = okhttp3.Headers.of("link", link);
        ApiResponse<String> response = new ApiResponse<>(Response.success("foo", headers));
        assertThat(response.getNextPage(), is(2));
    }

    @Test
    public void badPageNumber() {
        String link = "<https://api.github.com/search/repositories?q=foo&page=dsa>; rel=\"next\"";
        okhttp3.Headers headers = okhttp3.Headers.of("link", link);
        ApiResponse<String> response = new ApiResponse<>(Response.success("foo", headers));
        assertThat(response.getNextPage(), nullValue());
    }

    @Test
    public void badLinkHeader() {
        String link = "<https://api.github.com/search/repositories?q=foo&page=dsa>; relx=\"next\"";
        okhttp3.Headers headers = okhttp3.Headers.of("link", link);
        ApiResponse<String> response = new ApiResponse<>(Response.success("foo", headers));
        assertThat(response.getNextPage(), nullValue());
    }

    @Test
    public void error() {
        ApiResponse<String> response = new ApiResponse<String>(Response.error(400,
                ResponseBody.create(MediaType.parse("application/txt"), "blah")));
        assertThat(response.code, is(400));
        assertThat(response.errorMessage, is("blah"));
    }
}
