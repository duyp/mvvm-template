package com.duyp.architecture.mvvm.ui.modules.repo.detail.code.prettifier;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;

import com.duyp.architecture.mvvm.R;
import com.duyp.architecture.mvvm.databinding.GeneralViewerLayoutBinding;
import com.duyp.architecture.mvvm.helper.AppHelper;
import com.duyp.architecture.mvvm.helper.BundleConstant;
import com.duyp.architecture.mvvm.helper.Bundler;
import com.duyp.architecture.mvvm.helper.PrefGetter;
import com.duyp.architecture.mvvm.ui.base.fragment.BaseViewModelFragment;
import com.prettifier.pretty.PrettifyWebView;

import it.sephiroth.android.library.bottomnavigation.BottomNavigation;

/**
 * Created by duypham on 10/30/17.
 * 
 */

public class ViewerFragment extends BaseViewModelFragment<GeneralViewerLayoutBinding, ViewerViewModel> 
        implements PrettifyWebView.OnContentChangedListener, AppBarLayout.OnOffsetChangedListener{

    public static final String TAG = ViewerFragment.class.getSimpleName();

    public static ViewerFragment newInstance(@NonNull String url, @Nullable String htmlUrl) {
        return newInstance(url, htmlUrl, false);
    }

    public static ViewerFragment newInstance(@NonNull String url, boolean isRepo) {
        return newInstance(url, null, isRepo);
    }

    public static ViewerFragment newInstance(@NonNull String url, @Nullable String htmlUrl, boolean isRepo) {
        return newInstance(Bundler.start()
                .put(BundleConstant.ITEM, url)
                .put(BundleConstant.EXTRA_TWO, htmlUrl)
                .put(BundleConstant.EXTRA, isRepo)
                .end());
    }

    private static ViewerFragment newInstance(@NonNull Bundle bundle) {
        ViewerFragment fragmentView = new ViewerFragment();
        fragmentView.setArguments(bundle);
        return fragmentView;
    }

    private boolean isWrap = PrefGetter.isWrapCode();

    @Nullable private AppBarLayout appBarLayout;
    @Nullable private BottomNavigation bottomNavigation;
    private boolean isAppBarListener;
    private boolean isAppBarMoving;
    private boolean isAppBarExpanded = true;
    
    @Override
    protected int getLayout() {
        return R.layout.general_viewer_layout;
    }

    @Override
    protected Class<ViewerViewModel> getViewModelClass() {
        return ViewerViewModel.class;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (viewModel.isMarkdown()) {
            onSetMdText(new ViewerViewModel.MdTextValue(viewModel.getDownloadedStream(), viewModel.getUrl(), false));
        } else {
            onSetCode(viewModel.getDownloadedStream());
        }
        getActivity().invalidateOptionsMenu();
        binding.stateLayout.setEmptyText(R.string.no_data);
        if (savedInstanceState == null) {
            binding.stateLayout.showReload(0);
        }
        binding.stateLayout.setOnReloadListener(view1 -> viewModel.onWorkOnline());
        if (viewModel.isRepo()) {
            appBarLayout = getActivity().findViewById(R.id.appbar);
            bottomNavigation = getActivity().findViewById(R.id.bottomNavigation);

            if (appBarLayout != null && !isAppBarListener) {
                appBarLayout.addOnOffsetChangedListener(this);
                isAppBarListener = true;
            }
        }

        viewModel.getCode().observe(this, this::onSetCode);
        viewModel.getImageUrl().observe(this, this::onSetImageUrl);
        viewModel.getMdTextValue().observe(this, this::onSetMdText);
    }

    @Override public void onStart() {
        super.onStart();
        if (AppHelper.isDeviceAnimationEnabled(getContext())) {
            if (appBarLayout != null && !isAppBarListener) {
                appBarLayout.addOnOffsetChangedListener(this);
                isAppBarListener = true;
            }
        }
    }

    @Override public void onStop() {
        if (AppHelper.isDeviceAnimationEnabled(getContext())) {
            if (appBarLayout != null && isAppBarListener) {
                appBarLayout.removeOnOffsetChangedListener(this);
                isAppBarListener = false;
            }
        }
        super.onStop();
    }


    @Override public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.wrap_menu_option, menu);
        menu.findItem(R.id.wrap).setVisible(false);
    }

    @Override public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        MenuItem menuItem = menu.findItem(R.id.wrap);
        if (menuItem != null) {
            if (viewModel.isMarkdown() || viewModel.isRepo() || viewModel.isImage()) {
                menuItem.setVisible(false);
            } else {
                menuItem.setVisible(true).setCheckable(true).setChecked(isWrap);
            }
        }
    }

    @Override public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.wrap) {
            item.setChecked(!item.isChecked());
            isWrap = item.isChecked();
            setLoading(true);
            onSetCode(viewModel.getDownloadedStream());
        }
        return super.onOptionsItemSelected(item);
    }

    public void onSetImageUrl(@Nullable ViewerViewModel.ImageValue imageValue) {
        if (imageValue != null) {
            binding.webView.loadImage(imageValue.getUrl(), imageValue.isSvg());
            binding.webView.setOnContentChangedListener(this);
            binding.webView.setVisibility(View.VISIBLE);
        }
        getActivity().invalidateOptionsMenu();
    }

    public void onSetMdText(@Nullable ViewerViewModel.MdTextValue value) {
        if (value != null) {
            binding.webView.setVisibility(View.VISIBLE);
            binding.readmeLoader.setIndeterminate(false);
            binding.webView.setGithubContentWithReplace(value.getText(), value.getBaseUrl(), value.isReplace());
            binding.webView.setOnContentChangedListener(this);
        }
        getActivity().invalidateOptionsMenu();
    }

    public void onSetCode(@Nullable String text) {
        if (text != null) {
            binding.webView.setVisibility(View.VISIBLE);
            binding.readmeLoader.setIndeterminate(false);
            binding.webView.setSource(text, isWrap);
            binding.webView.setOnContentChangedListener(this);
        }
        getActivity().invalidateOptionsMenu();
    }

    public void onViewAsCode() {
        viewModel.onLoadContentAsStream();
    }

    @Override public void onContentChanged(int progress) {
        if (binding.readmeLoader != null) {
            binding.readmeLoader.setProgress(progress);
            if (progress == 100) {
                setLoading(false);
                if (!viewModel.isMarkdown() && !viewModel.isImage()) {
                    binding.webView.scrollToLine(viewModel.getUrl());
                }
            }
        }
    }

    @Override public void onScrollChanged(boolean reachedTop, int scroll) {
        if (AppHelper.isDeviceAnimationEnabled(getContext())) {
            if (viewModel.isRepo() && appBarLayout != null && bottomNavigation != null && binding.webView != null) {
                boolean shouldExpand = binding.webView.getScrollY() == 0;
                if (!isAppBarMoving && shouldExpand != isAppBarExpanded) {
                    isAppBarMoving = true;
                    isAppBarExpanded = shouldExpand;
                    bottomNavigation.setExpanded(shouldExpand, true);
                    appBarLayout.setExpanded(shouldExpand, true);
                    binding.webView.setNestedScrollingEnabled(shouldExpand);
                    if (shouldExpand)
                        binding.webView.onTouchEvent(MotionEvent.obtain(0, 0, MotionEvent.ACTION_UP, 0, 0, 0));
                }
            }
        }
    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
        verticalOffset = Math.abs(verticalOffset);
        if (verticalOffset == 0 || verticalOffset == appBarLayout.getTotalScrollRange())
            isAppBarMoving = false;
    }

    @Override
    public void setLoading(boolean loading) {
        binding.readmeLoader.setVisibility(loading ? View.VISIBLE : View.GONE);
        if (loading) {
            binding.readmeLoader.setIndeterminate(true);
            binding.stateLayout.showProgress();
        } else {
            binding.readmeLoader.setIndeterminate(false);
            binding.stateLayout.hideProgress();
            if (!viewModel.isImage()) {
                new Handler(Looper.myLooper()).postDelayed(() -> {
                    binding.stateLayout.showReload(viewModel.getDownloadedStream() == null ? 0 : 1);
                }, 100);
            }
        }
    }
}
