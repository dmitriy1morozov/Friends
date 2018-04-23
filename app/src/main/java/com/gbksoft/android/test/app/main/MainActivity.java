package com.gbksoft.android.test.app.main;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.NavUtils;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.facebook.AccessToken;
import com.gbksoft.android.test.app.R;
import com.gbksoft.android.test.app.main.list.ListFragment;
import com.gbksoft.android.test.app.main.map.MapFragment;
import com.gbksoft.android.test.app.main.profile.OnSaveProfileListener;
import com.gbksoft.android.test.app.main.profile.ProfileFragment;

public class MainActivity extends AppCompatActivity implements OnSaveProfileListener {

  public static final String PREF_NAME = "com.gbksoft.android.test.app";
  public static final String BUNDLE_SHOULD_SETUP_PROFILE = "profileSetup";

  @BindView(R.id.tabs_main_menu) TabLayout mMenuTabLayout;
  @BindView(R.id.viewpager_main_root) MyViewPager mRootViewPager;

  private ViewPagerAdapter mViewPagerAdapter;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    ButterKnife.bind(this);

    initViewPager();
  }

  @Override protected void onStart() {
    super.onStart();
    if(!isFacebookLoggedIn()) {
      navigateToLoginActivity();
      return;
    }

    SharedPreferences prefs = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
    Boolean isInitialSetup = prefs.getBoolean(BUNDLE_SHOULD_SETUP_PROFILE, false);

    int profileTabPosition = mViewPagerAdapter.getTabPosition(ProfileFragment.TAB_NAME);
    int mapTabPosition = mViewPagerAdapter.getTabPosition(MapFragment.TAB_NAME);
    if(isInitialSetup && profileTabPosition != -1) {
      mRootViewPager.setCurrentItem(profileTabPosition);
      hideBottomMenu();
    } else {
      mRootViewPager.setCurrentItem(mapTabPosition);
      showBottomMenu();
    }
  }

  @Override public void saveProfile() {
    SharedPreferences prefs = getSharedPreferences(MainActivity.PREF_NAME, MainActivity.MODE_PRIVATE);
    prefs.edit().putBoolean(MainActivity.BUNDLE_SHOULD_SETUP_PROFILE, false).apply();
    showBottomMenu();
    int mapTabPosition = mViewPagerAdapter.getTabPosition(MapFragment.TAB_NAME);
    mRootViewPager.setCurrentItem(mapTabPosition);
  }

  //==============================================================================================
  public void navigateToLoginActivity() {
    Intent upIntent = NavUtils.getParentActivityIntent(this);
    if(NavUtils.shouldUpRecreateTask(MainActivity.this, upIntent) || isTaskRoot()) {
      TaskStackBuilder.create(this).addNextIntentWithParentStack(upIntent).startActivities();
    } else {
      NavUtils.navigateUpTo(MainActivity.this, upIntent);
    }
  }

  //==============================================================================================
  private void initViewPager() {
    mViewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
    mViewPagerAdapter.addFragment(new MapFragment(), MapFragment.TAB_NAME);
    mViewPagerAdapter.addFragment(new ListFragment(), ListFragment.TAB_NAME);
    mViewPagerAdapter.addFragment(new ProfileFragment(), ProfileFragment.TAB_NAME);
    mRootViewPager.setAdapter(mViewPagerAdapter);
    mMenuTabLayout.setupWithViewPager(mRootViewPager);
  }

  private boolean isFacebookLoggedIn() {
    AccessToken accessToken = AccessToken.getCurrentAccessToken();
    return accessToken != null;
  }

  private void hideBottomMenu() {
    mMenuTabLayout.setVisibility(View.GONE);
    mRootViewPager.disableScroll();
  }

  private void showBottomMenu() {
    mMenuTabLayout.setVisibility(View.VISIBLE);
    mRootViewPager.enableScroll();
  }
}
