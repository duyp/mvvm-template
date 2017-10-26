package com.duyp.architecture.mvvm.ui.widgets;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;

/**
 * Created by duypham on 9/20/17.
 * Custom {@link TabLayout} with icon placed next to title for all tabs
 */

public class CustomIconTabLayout extends TabLayout {

    CustomTabTitleView[] titleViews;

    public CustomIconTabLayout(Context context) {
        super(context);
    }

    public CustomIconTabLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomIconTabLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void setupWithViewPager(@Nullable ViewPager viewPager) {
        super.setupWithViewPager(viewPager);
        if (viewPager != null) {
            PagerAdapter adapter = viewPager.getAdapter();
            if (adapter != null && adapter instanceof BasePagerAdapterWithIcon) {
                int n = getTabCount();
                titleViews = new CustomTabTitleView[n];
                for (int i = 0; i < n; i++) {
                    titleViews[i] = new CustomTabTitleView(getContext());
                    titleViews[i].setTitle(adapter.getPageTitle(i).toString());
                    titleViews[i].setImageResource(((BasePagerAdapterWithIcon) adapter).getPageIcon(i));
                    // noinspection ConstantConditions
                    getTabAt(i).setCustomView(titleViews[i]);
                }
            }
        } else {
            titleViews = null;
        }
    }

    public void setTitleAt(int position, String newTitle) {
        if (titleViews != null && titleViews.length > position) {
            titleViews[position].setTitle(newTitle);
        }
    }
}
