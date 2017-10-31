package com.duyp.architecture.mvvm.ui.modules.issue.detail;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.duyp.architecture.mvvm.R;
import com.duyp.architecture.mvvm.data.model.IssueDetail;
import com.duyp.architecture.mvvm.data.model.User;
import com.duyp.architecture.mvvm.data.model.type.IssueState;
import com.duyp.architecture.mvvm.databinding.IssuePagerActivityBinding;
import com.duyp.architecture.mvvm.helper.ActivityHelper;
import com.duyp.architecture.mvvm.helper.BundleConstant;
import com.duyp.architecture.mvvm.helper.Bundler;
import com.duyp.architecture.mvvm.helper.InputHelper;
import com.duyp.architecture.mvvm.helper.ParseDateFormat;
import com.duyp.architecture.mvvm.helper.PrefGetter;
import com.duyp.architecture.mvvm.helper.ViewHelper;
import com.duyp.architecture.mvvm.ui.base.activity.BaseViewModelActivity;
import com.duyp.architecture.mvvm.ui.modules.repo.detail.RepoDetailActivity;
import com.duyp.architecture.mvvm.ui.modules.repo.detail.Tab;
import com.duyp.architecture.mvvm.ui.widgets.SpannableBuilder;
import com.duyp.architecture.mvvm.ui.widgets.dialog.MessageDialogView;
import com.duyp.architecture.mvvm.utils.AvatarLoader;

import javax.inject.Inject;

/**
 * Created by duypham on 10/31/17.
 *
 */

public class IssueDetailPagerActivity extends BaseViewModelActivity<IssuePagerActivityBinding, IssueDetailViewModel> {

    @Inject
    AvatarLoader avatarLoader;

    @Override
    public int getLayout() {
        return R.layout.issue_pager_activity;
    }

    @Override
    protected boolean canBack() {
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setToolbarTitle("");
        viewModel.getOnDataReady().observe(this, isReady -> {
            if (isReady != null && isReady) {
                setupIssue();
                // noinspection ConstantConditions
                viewModel.getData().observe(this, this::updateIssueData);
            }
        });
    }

    private void setupIssue() {
        invalidateOptionsMenu();
        IssueDetail issueModel = viewModel.getIssueData();
        if (issueModel == null) return;

        setTaskName(issueModel.getRepoName() + " - " + issueModel.getTitle());
        setToolbarTitle(String.format("#%s", issueModel.getNumber()));
        if (getSupportActionBar() != null) {
            getSupportActionBar().setSubtitle(issueModel.getRepoName());
        }
//        if (isUpdate) {
//            IssueTimelineFragment issueDetailsView = getIssueTimelineFragment();
//            if (issueDetailsView != null && viewModel.getIssue() != null) {
//                issueDetailsView.onUpdateHeader();
//            }
//        } else {
//            if (pager.getAdapter() == null) {
//                Logger.e(viewModel.commentId);
//                pager.setAdapter(new FragmentsPagerAdapter(getSupportFragmentManager(), FragmentPagerAdapterModel
//                        .buildForIssues(this, viewModel.commentId)));
//            } else {
//                onUpdateTimeline();
//            }
//        }
//        if (!viewModel.isLocked() || viewModel.isOwner()) {
//            pager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
//                @Override public void onPageSelected(int position) {
//                    super.onPageSelected(position);
//                    hideShowFab();
//                }
//            });
//        }
        hideShowFab();
    }

    // ========================================================================================
    // Toolbar options menu
    // ========================================================================================

    @Override public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.issue_menu, menu);
        menu.findItem(R.id.closeIssue).setVisible(viewModel.isOwner());
        menu.findItem(R.id.lockIssue).setVisible(viewModel.isOwner());
        menu.findItem(R.id.labels).setVisible(viewModel.isRepoOwner());
        return super.onCreateOptionsMenu(menu);
    }

    @Override public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onNavToRepoClicked();
            return true;
        }
        IssueDetail issueModel = viewModel.getIssueData();
        if (issueModel == null) return false;

        if (item.getItemId() == R.id.share) {
            ActivityHelper.shareUrl(this, issueModel.getHtmlUrl());
            return true;
        } else if (item.getItemId() == R.id.closeIssue) {
            MessageDialogView.newInstance(
                    issueModel.getState().equals(IssueState.OPEN) ? getString(R.string.close_issue) : getString(R.string.re_open_issue),
                    getString(R.string.confirm_message), Bundler.start().put(BundleConstant.EXTRA, true)
                            .put(BundleConstant.YES_NO_EXTRA, true).end())
                    .show(getSupportFragmentManager(), MessageDialogView.TAG);
            return true;
        } else if (item.getItemId() == R.id.lockIssue) {
            MessageDialogView.newInstance(
                    viewModel.isLocked() ? getString(R.string.unlock_issue) : getString(R.string.lock_issue),
                    viewModel.isLocked() ? getString(R.string.unlock_issue_details) : getString(R.string.lock_issue_details),
                    Bundler.start().put(BundleConstant.EXTRA_TWO, true)
                            .put(BundleConstant.YES_NO_EXTRA, true)
                            .end())
                    .show(getSupportFragmentManager(), MessageDialogView.TAG);
            return true;
        } else if (item.getItemId() == R.id.labels) {
//            LabelsDialogFragment.newInstance(viewModel.getIssue() != null ? viewModel.getIssue().getLabels() : null,
//                    viewModel.getRepoId(), viewModel.getLogin())
//                    .show(getSupportFragmentManager(), "LabelsDialogFragment");
            return true;
        } else if (item.getItemId() == R.id.edit) {
//            CreateIssueActivity.startForResult(this, viewModel.getLogin(), viewModel.getRepoId(),
//                    viewModel.getIssue(), isEnterprise());
            return true;
        } else if (item.getItemId() == R.id.milestone) {
//            MilestoneDialogFragment.newInstance(viewModel.getLogin(), viewModel.getRepoId())
//                    .show(getSupportFragmentManager(), "MilestoneDialogFragment");
            return true;
        } else if (item.getItemId() == R.id.assignees) {
//            AssigneesDialogFragment.newInstance(viewModel.getLogin(), viewModel.getRepoId(), true)
//                    .show(getSupportFragmentManager(), "AssigneesDialogFragment");
            return true;
        } else if (item.getItemId() == R.id.subscribe) {
            viewModel.onSubscribeOrMute(false);
            return true;
        } else if (item.getItemId() == R.id.mute) {
            viewModel.onSubscribeOrMute(true);
            return true;
        } else if (item.getItemId() == R.id.browser) {
            ActivityHelper.startCustomTab(this, issueModel.getHtmlUrl());
            return true;
        } else if (item.getItemId() == R.id.pinUnpin) {
//            if (PrefGetter.isProEnabled()) {
//                viewModel.onPinUnpinIssue();
//            } else {
//                PremiumActivity.Companion.startActivity(this);
//            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem closeIssue = menu.findItem(R.id.closeIssue);
        MenuItem lockIssue = menu.findItem(R.id.lockIssue);
        MenuItem milestone = menu.findItem(R.id.milestone);
        MenuItem labels = menu.findItem(R.id.labels);
        MenuItem assignees = menu.findItem(R.id.assignees);
        MenuItem edit = menu.findItem(R.id.edit);
        MenuItem editMenu = menu.findItem(R.id.editMenu);
        MenuItem pinUnpin = menu.findItem(R.id.pinUnpin);
        boolean isOwner = viewModel.isOwner();
        boolean isLocked = viewModel.isLocked();
        boolean isCollaborator = viewModel.isCollaborator();
        boolean isRepoOwner = viewModel.isRepoOwner();
        editMenu.setVisible(isOwner || isCollaborator || isRepoOwner);
        milestone.setVisible(isCollaborator || isRepoOwner);
        labels.setVisible(isCollaborator || isRepoOwner);
        assignees.setVisible(isCollaborator || isRepoOwner);
        edit.setVisible(isCollaborator || isRepoOwner || isOwner);
        lockIssue.setVisible(isOwner || isCollaborator);
        labels.setVisible(viewModel.isRepoOwner() || isCollaborator);
        closeIssue.setVisible(isOwner || isCollaborator);
        // TODO: 10/31/17 pinned issue
//        pinUnpin.setVisible(false);
//        if (viewModel.getIssueData() != null) {
//            boolean isPinned = PinnedIssues.isPinned(viewModel.getIssue().getId());
//            pinUnpin.setIcon(isPinned ? ContextCompat.getDrawable(this, R.drawable.ic_pin_filled)
//                    : ContextCompat.getDrawable(this, R.drawable.ic_pin));
//            closeIssue.setTitle(viewModel.getIssue().getState() == IssueState.closed ? getString(R.string.re_open) : getString(R.string.close));
//            lockIssue.setTitle(isLocked ? getString(R.string.unlock_issue) : getString(R.string.lock_issue));
//        }
        return super.onPrepareOptionsMenu(menu);
    }

    // ========================================================================================
    // Data population
    // ========================================================================================

    private void updateIssueData(@Nullable IssueDetail issueModel) {
        if (issueModel == null) {
            return;
        }
        User userModel = issueModel.getUser();
        binding.header.headerTitle.setText(issueModel.getTitle());
        binding.header.detailsIcon.setVisibility(InputHelper.isEmpty(issueModel.getTitle())
                || !ViewHelper.isEllipsed(binding.header.headerTitle) ? View.GONE : View.VISIBLE);
        if (userModel != null) {
            binding.header.size.setVisibility(View.GONE);
            String username;
            CharSequence parsedDate;
            if (issueModel.getState().equals(IssueState.CLOSED)) {
                username = issueModel.getClosedBy() != null ? issueModel.getClosedBy().getLogin() : "N/A";
                parsedDate = issueModel.getClosedAt() != null ? ParseDateFormat.getTimeAgo(issueModel.getClosedAt()) : "N/A";
            } else {
                parsedDate = ParseDateFormat.getTimeAgo(issueModel.getCreatedAt());
                username = issueModel.getUser() != null ? issueModel.getUser().getLogin() : "N/A";
            }
            binding.header.date.setText(SpannableBuilder.builder()
                    .append(ContextCompat.getDrawable(this,
                            issueModel.getState().equals(IssueState.OPEN) ? R.drawable.ic_issue_opened_small : R.drawable.ic_issue_closed_small))
                    .append(" ")
                    .append(issueModel.getState())
                    .append(" ").append(getString(R.string.by)).append(" ").append(username).append(" ")
                    .append(parsedDate));
            binding.header.avatarLayout.bindData(avatarLoader, userModel);
        }
    }

    private void hideShowFab() {
//        if (viewModel.isLocked() && !viewModel.isOwner()) {
//            getSupportFragmentManager().beginTransaction().hide(commentEditorFragment).commit();
//            return;
//        }
//        getSupportFragmentManager().beginTransaction().show(commentEditorFragment).commit();
    }

    protected void onNavToRepoClicked() {
        Intent intent = RepoDetailActivity.createIntent(this, viewModel.getRepoId(), viewModel.getLogin(), Tab.ISSUES);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
        finish();
    }

    public static Intent createIntent(@NonNull Context context, @NonNull String repoId, @NonNull String login, int number) {
        return createIntent(context, repoId, login, number, false);

    }

    public static Intent createIntent(@NonNull Context context, @NonNull String repoId,
                                      @NonNull String login, int number, boolean showToRepoBtn) {
        return createIntent(context, repoId, login, number, showToRepoBtn, false);

    }

    public static Intent createIntent(@NonNull Context context, @NonNull String repoId,
                                      @NonNull String login, int number, boolean showToRepoBtn,
                                      boolean isEnterprise) {
        return createIntent(context, repoId, login, number, showToRepoBtn, isEnterprise, 0);

    }

    public static Intent createIntent(@NonNull Context context, @NonNull String repoId,
                                      @NonNull String login, int number, boolean showToRepoBtn,
                                      boolean isEnterprise, long commentId) {
        Intent intent = new Intent(context, IssueDetailPagerActivity.class);
        intent.putExtras(Bundler.start()
                .put(BundleConstant.ID, number)
                .put(BundleConstant.EXTRA, login)
                .put(BundleConstant.EXTRA_TWO, repoId)
                .put(BundleConstant.EXTRA_THREE, showToRepoBtn)
                .put(BundleConstant.IS_ENTERPRISE, isEnterprise)
                .put(BundleConstant.EXTRA_SIX, commentId)
                .end());
        return intent;

    }
}
