package com.example.abc.dailycaloriecounter.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.abc.dailycaloriecounter.R;

import java.util.ArrayList;
import java.util.List;

public class FragmentViewPagerAdapter extends FragmentStatePagerAdapter {
    private final List<Fragment> mFragments = new ArrayList<>();
    private final List<Integer> mFragmentIcons = new ArrayList<>();
    private final List<String> mFragmentTitles = new ArrayList<>();
    Context mContext;
    FragmentManager fragmentManager;

    public FragmentViewPagerAdapter(FragmentManager fm) {
        super(fm);
        fragmentManager=fm;
    }

    public FragmentViewPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
        fragmentManager=fm;
    }

    public void addFragment(Fragment fragment, String title, int iconId) {
        mFragments.add(fragment);
        mFragmentTitles.add(title);
        mFragmentIcons.add(iconId);
    }

    public void addFragment(Fragment fragment, String title) {
        mFragments.add(fragment);
        mFragmentTitles.add(title);
    }

    public void addFragment(Fragment fragment) {
        mFragments.add(fragment);
        mFragmentTitles.add("");
    }
    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mFragmentTitles.get(position);
    }

    public View getTabView(int position) {
        View tab = LayoutInflater.from(mContext).inflate(R.layout.tabbar_view, null);
        TextView tabText = (TextView) tab.findViewById(R.id.tabText);
        ImageView tabImage = (ImageView) tab.findViewById(R.id.tabImage);
        tabText.setText(getPageTitle(position));
        tabImage.setBackgroundResource(mFragmentIcons.get(position));
        if (position == 0) {
            tab.setSelected(true);
        }
        return tab;
    }

    public void removePage(int index) {
        if (index < mFragments.size()) {
            mFragments.remove(index);
            notifyDataSetChanged();
        }
    }
}
