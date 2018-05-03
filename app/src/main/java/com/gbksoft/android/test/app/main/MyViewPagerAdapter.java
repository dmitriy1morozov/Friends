package com.gbksoft.android.test.app.main;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;
import java.util.ArrayList;

class MyViewPagerAdapter extends FragmentPagerAdapter {

  private ArrayList<Fragment> mFragments = new ArrayList<>();
  private ArrayList<String> mTitles = new ArrayList<>();

  MyViewPagerAdapter(FragmentManager fragmentManager) {
    super(fragmentManager);
  }

  @Override public Fragment getItem(int position) {
    return mFragments.get(position);
  }

  @Override public int getCount() {
    return mFragments.size();
  }

  @Nullable @Override public CharSequence getPageTitle(int position) {
    return mTitles.get(position);
  }

  @Override public Object instantiateItem(ViewGroup container, int position) {
    Object object = super.instantiateItem(container, position);
    mFragments.set(position, (Fragment) object);
    return object;
  }

  //==============================================================================================
  public void addFragment(Fragment fragment, String title) {
    this.mFragments.add(fragment);
    this.mTitles.add(title);
  }

  public int getTabPosition(String name) {
    return mTitles.indexOf(name);
  }
}