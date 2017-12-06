package com.duyp.architecture.mvvm;

import com.duyp.architecture.mvvm.data.remote.GithubService;
import com.duyp.architecture.mvvm.data.source.State;
import com.duyp.architecture.mvvm.test_utils.RxSchedulersOverrideRule;
import com.duyp.architecture.mvvm.ui.base.BaseViewModel;
import com.duyp.architecture.mvvm.ui.navigator.NavigatorHelper;
import com.duyp.architecture.mvvm.utils.SafeMutableLiveData;

import org.junit.Before;
import org.junit.Rule;
import org.junit.runner.RunWith;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import javax.inject.Inject;

import static org.powermock.api.mockito.PowerMockito.mock;

/**
 * Created by duypham on 10/21/17.
 *
 */

@RunWith(PowerMockRunner.class)
public abstract class BaseViewModelTest<T extends BaseViewModel> extends BaseTest {

    @Rule
    public RxSchedulersOverrideRule rxSchedulersOverrideRule = new RxSchedulersOverrideRule();

    @Inject
    protected GithubService githubService;

    @Inject
    protected T viewModel;

    protected SafeMutableLiveData<State> stateLiveData;

    protected NavigatorHelper mNavigatorHelper;

    @Before
    public void setup() {
        super.setup();
        // noinspection unchecked
        stateLiveData = mock(SafeMutableLiveData.class);
        mNavigatorHelper = mock(NavigatorHelper.class);
        Whitebox.setInternalState(viewModel, "stateLiveData", stateLiveData);
        Whitebox.setInternalState(viewModel, "navigatorHelper", mNavigatorHelper);
    }
}
