package com.duyp.architecture.mvvm.data.remote;

import com.duyp.androidutils.rx.functions.PlainConsumer;
import com.duyp.architecture.mvvm.model.Repo;
import com.duyp.architecture.mvvm.model.remote.ApiResponse;
import com.duyp.architecture.mvvm.model.remote.ErrorEntity;
import com.duyp.architecture.mvvm.test_utils.RxSchedulersOverrideRule;
import com.duyp.architecture.mvvm.utils.ApiUtils;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.List;

import io.reactivex.Single;
import io.reactivex.functions.Consumer;

import static com.duyp.architecture.mvvm.model.ModelUtils.sampleUser;
import static com.duyp.architecture.mvvm.test_utils.RemoteTestUtils.errorResponse;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import static com.duyp.architecture.mvvm.model.ModelUtils.sampleRepoList;

import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.is;
/**
 * Created by duypham on 10/19/17.
 *
 */

@RunWith(PowerMockRunner.class)
public class ApiUtilsTest {

    @Mock
    GithubService githubService;

    @Rule
    public RxSchedulersOverrideRule rxSchedulersOverrideRule = new RxSchedulersOverrideRule();


    @Test
    public void successRequest() throws Exception{

        List<Repo> list = sampleRepoList(10, sampleUser(1L));
        ApiResponse<List<Repo>> succesResponse = new ApiResponse<List<Repo>>(retrofit2.Response.success(list));
        when(githubService.getAllPublicRepositories(any())).thenReturn(Single.just(succesResponse));

        PlainConsumer<ApiResponse<List<Repo>>> consumer = PowerMockito.mock(PlainConsumer.class);
        PlainConsumer<ErrorEntity> errorEntityPlainConsumer = PowerMockito.mock(PlainConsumer.class);

        ApiUtils.makeRequest(githubService.getAllPublicRepositories(null), true, response1 -> {
            assertThat(response1.body, is(list));
                consumer.accept(response1);
        }, errorEntityPlainConsumer);

        verify(consumer, times(1)).accept(succesResponse);
        verify(errorEntityPlainConsumer, times(0)).accept(any());
    }

    @Test
    public void errorRequest() throws Exception {

        Consumer<ApiResponse<List<Repo>>> consumer = PowerMockito.mock(Consumer.class);
        PlainConsumer<ErrorEntity> errorEntityPlainConsumer = PowerMockito.mock(PlainConsumer.class);

        when(githubService.getAllPublicRepositories(any())).thenReturn(errorResponse(406));

        ApiUtils.makeRequest(githubService.getAllPublicRepositories(null), true, response -> {}, errorEntity -> {
            assertThat(errorEntity.getHttpCode(), is(406));
            errorEntityPlainConsumer.accept(errorEntity);
        });

        verify(consumer, times(0)).accept(any());
        verify(errorEntityPlainConsumer, times(1)).accept(any());
    }
}
