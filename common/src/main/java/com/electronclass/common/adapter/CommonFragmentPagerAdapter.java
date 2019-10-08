package com.electronclass.common.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import java.util.List;

public class CommonFragmentPagerAdapter extends FragmentPagerAdapter {
    private List<Fragment> fragments;

    public CommonFragmentPagerAdapter(FragmentManager fm, List<Fragment> fragments) {
        super(fm);
        this.fragments = fragments;
    }

    @Override
    public Fragment getItem(int i) {
        return fragments.get(i);
    }

    @Override
    public int getCount() {
        return fragments == null ? 0 : fragments.size();
    }

    public void bindViewPager(final ViewPager viewPager) {
        bindViewPager(viewPager, new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                viewPager.setCurrentItem(i);
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }

    public void bindViewPager(ViewPager viewPager, ViewPager.OnPageChangeListener onPageChangeListener) {
        viewPager.setAdapter(this);
        viewPager.setOffscreenPageLimit(getCount());
        viewPager.addOnPageChangeListener(onPageChangeListener);
    }
}
