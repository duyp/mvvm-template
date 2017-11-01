package com.duyp.architecture.mvvm.ui.modules.main;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.duyp.androidutils.AlertUtils;
import com.duyp.architecture.mvvm.R;
import com.duyp.architecture.mvvm.databinding.ActivityMainBinding;
import com.duyp.architecture.mvvm.ui.base.activity.BaseViewModelActivity;

import javax.inject.Inject;

import it.sephiroth.android.library.bottomnavigation.BottomNavigation;

/**
 * Created by duypham on 10/23/17.
 *
 */

public class MainActivity extends BaseViewModelActivity<ActivityMainBinding, MainViewModel>
        implements BottomNavigation.OnMenuItemSelectionListener, ViewPager.OnPageChangeListener{

    @Inject
    MainDrawerHolder drawerHolder;

    @Inject
    MainPagerAdapter adapter;

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
            binding.bottom.bottomNavigation.setOnMenuItemClickListener(this);

            binding.pager.setOffscreenPageLimit(3);
            binding.pager.setAdapter(adapter);
            binding.pager.addOnPageChangeListener(this);
            binding.pager.setCurrentItem(viewModel.getCurrentTab());
            onPageSelected(viewModel.getCurrentTab());

            drawerHolder.updateUser(userLiveData.getValue());
            userLiveData.observe(this, drawerHolder::updateUser);
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
            AlertUtils.showToastLongMessage(this, "Notification feature will be implemented soon!");
//            ViewHelper.tintDrawable(item.setIcon(R.drawable.ic_notifications_none).getIcon(), ViewHelper.getIconColor(this));
//            startActivity(new Intent(this, NotificationActivity.class));
//            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onMenuItemSelect(int id, int position, boolean fromUser) {
        if (position > 1) {
            AlertUtils.showToastLongMessage(this, "Coming soon...");
        }
        if (fromUser) {
            binding.pager.setCurrentItem(position);
        }
    }

    @Override
    public void onMenuItemReselect(int id, int position, boolean fromUser) {}

    @Override
    public void onBackPressed() {
        if (!drawerHolder.closeDrawer()) {
            super.onBackPressed();
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}

    @Override
    public void onPageSelected(int position) {
        hideShowShadow(position < 2);
        viewModel.setCurrentTab(position);
        binding.bottom.bottomNavigation.setSelectedIndex(position, true);
    }

    @Override
    public void onPageScrollStateChanged(int state) {}
}
