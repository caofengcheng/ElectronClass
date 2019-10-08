package com.electronclass.common.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ：LiMing
 * @date ：2019-04-17
 * @desc ：
 */
public class CommonPagerAdapter extends FragmentPagerAdapter {

    private List<String>   title     = new ArrayList<>();
    private List<Fragment> fragments = new ArrayList<>();
    public CommonPagerAdapter(FragmentManager fm, List<String> title, List<Fragment> fragments) {
        super(fm);
        this.title=title;
        this.fragments=fragments;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return title.get(position);
    }

    @Override
    public int getCount() {
        return title.size();
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Object obj = super.instantiateItem(container, position);
        return obj;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        super.destroyItem(container, position, object);
    }
}
