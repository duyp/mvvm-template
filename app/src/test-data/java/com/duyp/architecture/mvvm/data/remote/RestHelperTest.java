package com.duyp.architecture.mvvm.data.remote;

import com.duyp.androidutils.rx.functions.PlainConsumer;
import com.duyp.architecture.mvvm.data.model.ErrorEntity;
import com.duyp.architecture.mvvm.data.model.GitHubErrorResponse;
import com.duyp.architecture.mvvm.data.model.Repo;
import com.duyp.architecture.mvvm.helper.ResponseHelper;
import com.duyp.architecture.mvvm.helper.RestHelper;
import com.duyp.architecture.mvvm.test_utils.RxSchedulersOverrideRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.List;

import io.reactivex.Single;

import static com.duyp.architecture.mvvm.test_utils.ModelTestUtils.sampleUser;
import static com.duyp.architecture.mvvm.test_utils.RemoteTestUtils.errorResponse;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.mockStatic;

import static com.duyp.architecture.mvvm.test_utils.ModelTestUtils.sampleRepoList;
/**
 * Created by duypham on 10/19/17.
 *
 */

@RunWith(PowerMockRunner.class)
public class RestHelperTest {

    @Mock
    private GithubService githubService;

    @Rule
    public RxSchedulersOverrideRule rxSchedulersOverrideRule = new RxSchedulersOverrideRule();

    @Before
    public void setup() {}

    @Test
    @SuppressWarnings("unchecked")
    public void successRequest() throws Exception{

        List<Repo> list = sampleRepoList(10, sampleUser(1L));
        when(githubService.getAllPublicRepositories(any())).thenReturn(Single.just(list));

        PlainConsumer<List<Repo>> consumer = PowerMockito.mock(PlainConsumer.class);
        PlainConsumer<ErrorEntity> errorEntityPlainConsumer = PowerMockito.mock(PlainConsumer.class);

        RestHelper.makeRequest(githubService.getAllPublicRepositories(null), true, consumer, errorEntityPlainConsumer);

        verify(consumer, times(1)).accept(list);
        verify(errorEntityPlainConsumer, times(0)).accept(any());
    }

    @Test
    @SuppressWarnings("unchecked")
    public void errorRequest() throws Exception {

        PlainConsumer<List<Repo>> consumer = PowerMockito.mock(PlainConsumer.class);
        PlainConsumer<ErrorEntity> errorEntityPlainConsumer = PowerMockito.mock(PlainConsumer.class);

        String message = "blah";
        when(githubService.getAllPublicRepositories(any())).thenReturn(errorResponse(406, message));

        RestHelper.makeRequest(githubService.getAllPublicRepositories(null), true, consumer, errorEntityPlainConsumer);

        verify(consumer, times(0)).accept(any());
        verify(errorEntityPlainConsumer, times(1)).accept(Matchers.eq(new ErrorEntity(message, 406)));
    }
}
