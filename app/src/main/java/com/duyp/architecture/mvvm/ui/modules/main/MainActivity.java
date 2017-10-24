package com.duyp.architecture.mvvm.ui.modules.main;

import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.v4.view.GravityCompat;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.duyp.architecture.mvvm.R;
import com.duyp.architecture.mvvm.databinding.ActivityMainBinding;
import com.duyp.architecture.mvvm.helper.TypeFaceHelper;
import com.duyp.architecture.mvvm.ui.base.activity.BaseViewModelActivity;

import javax.inject.Inject;

/**
 * Created by duypham on 10/23/17.
 *
 */

public class MainActivity extends BaseViewModelActivity<ActivityMainBinding, MainViewModel> {

    @Inject
    MainDrawerHolder drawerHolder;

    @Override
    public int getLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected boolean canBack() {
        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    private void init() {
        viewModel.ensureInUserScope(userLiveData -> {

            setToolbarIcon(R.drawable.ic_menu);
            drawerHolder.init(binding.drawer);
            binding.bottom.bottomNavigation.setOnMenuItemClickListener(viewModel);

            userLiveData.observe(this, drawerHolder::updateUser);

            viewModel.setFragmentManager(getSupportFragmentManager());
            Integer currentTab = viewModel.getCurrentTab().getValue();
            int position = currentTab == null ? MainViewModel.FEEDS : currentTab;
            binding.bottom.bottomNavigation.setSelectedIndex(position, true);
            viewModel.onMenuItemReselect(0, position, false); // ensure onMenuItemReSelect if bottomNavigation wo'nt fire event

            viewModel.getCurrentTab().observe(this, tab -> {
                hideShowShadow(tab != null && tab == MainViewModel.FEEDS);
            });
        }, this::navigateLogin);
    }

    @Override public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            if (binding.drawer != null) binding.drawer.openDrawer(GravityCompat.START);
            return true;
        } else if (item.getItemId() == R.id.search) {
//            startActivity(new Intent(this, SearchActivity.class));
//            return true;
        } else if (item.getItemId() == R.id.notifications) {
//            ViewHelper.tintDrawable(item.setIcon(R.drawable.ic_notifications_none).getIcon(), ViewHelper.getIconColor(this));
//            startActivity(new Intent(this, NotificationActivity.class));
//            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
