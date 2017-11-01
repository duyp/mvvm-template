package com.duyp.architecture.mvvm.ui.modules.repo.detail;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.TextViewCompat;
import android.text.format.Formatter;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.duyp.architecture.mvvm.R;
import com.duyp.architecture.mvvm.data.model.LicenseModel;
import com.duyp.architecture.mvvm.data.model.Repo;
import com.duyp.architecture.mvvm.data.model.RepoDetail;
import com.duyp.architecture.mvvm.data.provider.color.ColorsProvider;
import com.duyp.architecture.mvvm.data.provider.scheme.SchemeParser;
import com.duyp.architecture.mvvm.databinding.ActivityRepoDetailBinding;
import com.duyp.architecture.mvvm.databinding.RepoHeaderIconsLayoutBinding;
import com.duyp.architecture.mvvm.databinding.TitleHeaderLayoutBinding;
import com.duyp.architecture.mvvm.helper.ActivityHelper;
import com.duyp.architecture.mvvm.helper.AppHelper;
import com.duyp.architecture.mvvm.helper.BundleConstant;
import com.duyp.architecture.mvvm.helper.Bundler;
import com.duyp.architecture.mvvm.helper.InputHelper;
import com.duyp.architecture.mvvm.helper.NameParser;
import com.duyp.architecture.mvvm.helper.ParseDateFormat;
import com.duyp.architecture.mvvm.helper.PrefGetter;
import com.duyp.architecture.mvvm.helper.ViewHelper;
import com.duyp.architecture.mvvm.ui.adapter.TopicsAdapter;
import com.duyp.architecture.mvvm.ui.base.activity.BaseViewModelActivity;
import com.duyp.architecture.mvvm.ui.widgets.SpannableBuilder;
import com.duyp.architecture.mvvm.utils.AvatarLoader;

import java.text.NumberFormat;

import javax.inject.Inject;

/**
 * Created by duypham on 10/29/17.
 *
 */

public class RepoDetailActivity extends BaseViewModelActivity<ActivityRepoDetailBinding, RepoDetailViewModel> {

    private int accentColor;
    private int iconColor;
    private static final NumberFormat numberFormat = NumberFormat.getNumberInstance();

    @Inject
    AvatarLoader glideLoader;

    @Inject
    TopicsAdapter topicsAdapter;

    @Inject
    RepoDetailFragmentManager repoDetailFragmentManager;

    private RepoHeaderIconsLayoutBinding headerIconBinding;
    private TitleHeaderLayoutBinding headerInfo;

    @Override
    public int getLayout() {
        return R.layout.activity_repo_detail;
    }

    @Override
    protected boolean canBack() {
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setToolbarTitle("");
        headerIconBinding = binding.header.icons;
        headerInfo = binding.header.info;
        accentColor = ViewHelper.getAccentColor(this);
        iconColor = ViewHelper.getIconColor(this);

        headerInfo.tagsIcon.setOnClickListener(v -> {
            boolean shouldShow = headerInfo.topicsList.getVisibility() != View.VISIBLE;
            headerInfo.topicsList.setVisibility(shouldShow ? View.VISIBLE : View.GONE);
        });
        headerInfo.detailsIcon.setOnClickListener(v -> {
            boolean shouldShow = headerInfo.description.getVisibility() != View.VISIBLE;
            headerInfo.description.setVisibility(shouldShow ? View.VISIBLE : View.GONE);
        });

        viewModel.getOnDataReady().observe(this, ready -> {
            if (ready != null && ready) {
                //noinspection ConstantConditions
                viewModel.getData().observe(this, this::populateData);
                invalidateOptionsMenu();
            }
        });

        viewModel.getWatchStatus().observe(this, state -> this.invalidateWatched(state, viewModel.getRepoDetail()));
        viewModel.getFolkStatus().observe(this, state -> this.invalidateForked(state, viewModel.getRepoDetail()));
        viewModel.getStarStatus().observe(this, state -> this.invalidateStarred(state, viewModel.getRepoDetail()));
        viewModel.getTopics().observe(this, strings -> topicsAdapter.setData(strings, true));
    }

    @Override public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.repo_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override public boolean onPrepareOptionsMenu(Menu menu) {
        if (viewModel.getData() != null) {
            RepoDetail repoModel = viewModel.getData().getData();
            if (repoModel != null && repoModel.isFork() && repoModel.getParent() != null) {
                MenuItem menuItem = menu.findItem(R.id.originalRepo);
                menuItem.setVisible(true);
                menuItem.setTitle(repoModel.getParent().getFullName());
            }
//        menu.findItem(R.id.deleteRepo).setVisible(getPresenter().isRepoOwner());
            if (menu.findItem(R.id.deleteRepo) != null)
                menu.findItem(R.id.deleteRepo).setVisible(false);//removing delete permission.
        }
        return super.onPrepareOptionsMenu(menu);
    }

    @Override public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
//            startActivity(new Intent(this, MainActivity.class));
            finish();
        } else if (viewModel.getRepoDetail() != null){
            RepoDetail repoModel = viewModel.getRepoDetail();
            if (item.getItemId() == R.id.share) {
                ActivityHelper.shareUrl(this, repoModel.getHtmlUrl());
                return true;
            } else if (item.getItemId() == R.id.browser) {
                ActivityHelper.startCustomTab(this, repoModel.getHtmlUrl());
                return true;
            } else if (item.getItemId() == R.id.copy) {
                AppHelper.copyToClipboard(this, repoModel.getHtmlUrl());
                return true;
            } else if (item.getItemId() == R.id.originalRepo) {
                if (repoModel.getParent() != null) {
                    SchemeParser.launchUri(this, repoModel.getParent().getHtmlUrl());
                }
                return true;
            } else if (item.getItemId() == R.id.deleteRepo) {
//                MessageDialogView.newInstance(getString(R.string.delete_repo), getString(R.string.delete_repo_warning),
//                        Bundler.start().put(BundleConstant.EXTRA_TWO, true)
//                                .put(BundleConstant.YES_NO_EXTRA, true)
//                                .end()).show(getSupportFragmentManager(), MessageDialogView.TAG);
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    public void populateData(RepoDetail repoModel) {
        if (repoDetailFragmentManager.getRepoDetail() == null) {
            binding.bottom.bottomNavigation.setOnMenuItemClickListener(repoDetailFragmentManager);
            // delay for better activity's start up time
            new Handler(Looper.myLooper()).postDelayed(() -> repoDetailFragmentManager.init(repoModel, viewModel), 300);

        }

        if (repoModel.isHasProjects()) {
            binding.bottom.bottomNavigation.inflateMenu(R.menu.repo_with_project_bottom_nav_menu);
        }

        ///////////// HEADER INFO /////////////////////////////////////////
        if (repoModel.getTopics() != null && !repoModel.getTopics().isEmpty()) {
            headerInfo.tagsIcon.setVisibility(View.VISIBLE);
            if (headerInfo.topicsList.getAdapter() == null) {
                headerInfo.topicsList.setAdapter(topicsAdapter);
            }
        } else {
            headerInfo.tagsIcon.setVisibility(View.GONE);
        }

        headerInfo.detailsIcon.setVisibility(InputHelper.isEmpty(repoModel.getDescription()) ? View.GONE : View.VISIBLE);
        headerInfo.language.setVisibility(InputHelper.isEmpty(repoModel.getLanguage()) ? View.GONE : View.VISIBLE);

        if (!InputHelper.isEmpty(repoModel.getLanguage())) {
            headerInfo.language.setText(repoModel.getLanguage());
            headerInfo.language.setTextColor(ColorsProvider.getColorAsColor(repoModel.getLanguage(), headerInfo.language.getContext()));
        }

        if (repoModel.getOwner() != null) {
            headerInfo.avatarLayout.bindData(glideLoader, repoModel.getOwner());
        } else if (repoModel.getOrganization() != null) {
            headerInfo.avatarLayout.bindData(glideLoader, repoModel.getOrganization());
        }

        headerInfo.description.setText(repoModel.getDescription());
        headerInfo.detailsIcon.setVisibility(InputHelper.isEmpty(repoModel.getDescription()) ? View.GONE : View.VISIBLE);

        headerInfo.date.setText(SpannableBuilder.builder()
                .append(ParseDateFormat.getTimeAgo(repoModel.getPushedAt()))
                .append(" ,").append(" ")
                .append(repoModel.getHumanReadableSize()));

        headerInfo.size.setVisibility(View.GONE);
        headerInfo.headerTitle.setText(repoModel.getFullName());
        TextViewCompat.setTextAppearance(headerInfo.headerTitle, R.style.TextAppearance_AppCompat_Medium);
        headerInfo.headerTitle.setTextColor(ViewHelper.getPrimaryTextColor(this));

        ///////////// ICONS /////////////////
        headerIconBinding.setVm(viewModel);
        headerIconBinding.wikiLayout.setVisibility(repoModel.isHasWiki() ? View.VISIBLE : View.GONE);
        headerIconBinding.pinText.setText(R.string.pin);

        headerIconBinding.forkRepo.setText(numberFormat.format(repoModel.getForksCount()));
        headerIconBinding.starRepo.setText(numberFormat.format(repoModel.getStargazersCount()));
        headerIconBinding.watchRepo.setText(numberFormat.format(repoModel.getSubsCount()));

        if (repoModel.getLicense() != null) {
            headerIconBinding.licenseLayout.setVisibility(View.VISIBLE);
            LicenseModel licenseModel = repoModel.getLicense();
            headerIconBinding.license.setText(!InputHelper.isEmpty(licenseModel.getSpdxId()) ? licenseModel.getSpdxId() : licenseModel.getName());
        }

        supportInvalidateOptionsMenu();
        if (!PrefGetter.isRepoGuideShowed()) {}
    }

    public void invalidateWatched(Boolean b, @Nullable RepoDetail repoModel) {
        headerIconBinding.watchRepoLayout.setEnabled(b != null && repoModel != null);
        headerIconBinding.watchRepoImage.tintDrawableColor(b != null && b && repoModel != null ? accentColor : iconColor);
        headerIconBinding.watchRepo.setText(numberFormat.format(repoModel != null ? repoModel.getSubsCount() : 0));
    }

    public void invalidateStarred(Boolean b, @Nullable RepoDetail repoModel) {
        headerIconBinding.starRepoLayout.setEnabled(b != null && repoModel != null);
        headerIconBinding.starRepoImage.setImageResource(b != null && b && repoModel != null? R.drawable.ic_star_filled : R.drawable.ic_star);
        headerIconBinding.starRepoImage.tintDrawableColor(b != null && b && repoModel != null? accentColor : iconColor);
        headerIconBinding.starRepo.setText(numberFormat.format(repoModel != null ? repoModel.getStargazersCount(): 0));
    }

    public void invalidateForked(Boolean b, @Nullable RepoDetail repoModel) {
        headerIconBinding.forkRepoLayout.setEnabled(b != null && repoModel != null);
        headerIconBinding.forkRepoImage.tintDrawableColor(b != null && b && repoModel != null ? accentColor : iconColor);
        headerIconBinding.forkRepo.setText(numberFormat.format(repoModel!= null ? repoModel.getForksCount(): 0));
    }

    public static Intent createIntent(@NonNull Context context, @NonNull String repoName, @NonNull String login) {
        return createIntent(context, repoName, login, Tab.CODE);
    }

    public static Intent createIntent(@NonNull Context context, @NonNull String repoName, @NonNull String login,
                                      @Tab int tab) {
        return createIntent(context, repoName, login, tab, -1);
    }

    public static Intent createIntent(@NonNull Context context, @NonNull String repoName, @NonNull String login,
                                      @Tab int tab, int showWhat) {
        Intent intent = new Intent(context, RepoDetailActivity.class);
        intent.putExtras(Bundler.start()
                .put(BundleConstant.ID, repoName)
                .put(BundleConstant.EXTRA_TWO, login)
                .put(BundleConstant.EXTRA_TYPE, tab)
                .put(BundleConstant.EXTRA_THREE, showWhat)
                .end());
        return intent;
    }

    public static void startRepoDetailActivity(@NonNull Context context, @NonNull NameParser nameParser) {
        if (!InputHelper.isEmpty(nameParser.getName()) && !InputHelper.isEmpty(nameParser.getUsername())) {
            Intent intent = new Intent(context, RepoDetailActivity.class);
            intent.putExtras(Bundler.start()
                    .put(BundleConstant.ID, nameParser.getName())
                    .put(BundleConstant.EXTRA_TWO, nameParser.getUsername())
                    .put(BundleConstant.EXTRA_TYPE, Tab.CODE)
                    .put(BundleConstant.IS_ENTERPRISE, nameParser.isEnterprise())
                    .end());
            context.startActivity(intent);
        }
    }
}
