package com.gbksoft.android.test.app.main;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.firebase.ui.auth.AuthUI;
import com.gbksoft.android.test.app.R;
import com.gbksoft.android.test.app.data.pojo.User;
import com.gbksoft.android.test.app.main.list.ListFragment;
import com.gbksoft.android.test.app.main.map.MapFragment;
import com.gbksoft.android.test.app.main.profile.ProfileFragment;
import com.google.firebase.auth.FirebaseAuth;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity implements FirebaseAuth.AuthStateListener, UserSelectedListener {

  public static final String PREF_NAME = "com.gbksoft.android.test.app";

  private static final String TAG = "MyLogs MainActivity";
  private static final String BUNDLE_SIGNED_ID = "isSignedIn";
  private static final int RC_SIGN_IN = 123;

  @BindView(R.id.tabs_main_menu) TabLayout mMenuTabLayout;
  @BindView(R.id.viewpager_main_root) MyViewPager mRootViewPager;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    ButterKnife.bind(this);
  }

  @Override protected void onStart() {
    Log.d(TAG, "onStart: ");
    super.onStart();
    if(!isNetworkAvailable()) {
      askToEnableNetwork();
    }
    initViewPager();
    FirebaseAuth.getInstance().addAuthStateListener(this);
  }

  @Override protected void onResume() {
    Log.d(TAG, "onResume: ");
    super.onResume();
    if(getSignedInUserId() == null) {
      navigateToLoginActivity();
    }
  }

  @Override protected void onStop() {
    super.onStop();
    FirebaseAuth.getInstance().removeAuthStateListener(this);
  }

  //==============================================================================================
  public void navigateToLoginActivity() {
    String profileTabTitle = getResources().getString(R.string.main_tab_title_profile);
    int profileTabIndex = ((MyViewPagerAdapter) mRootViewPager.getAdapter()).getTabPosition(profileTabTitle);
    mRootViewPager.setCurrentItem(profileTabIndex, true);
    if(isNetworkAvailable()) {
      List<AuthUI.IdpConfig> providers = Arrays.asList(
          new AuthUI.IdpConfig.EmailBuilder().build(),
          new AuthUI.IdpConfig.GoogleBuilder().build());
      Intent loginIntent = AuthUI.getInstance().createSignInIntentBuilder().setAvailableProviders(providers).build();
      startActivityForResult(loginIntent, RC_SIGN_IN);
    } else {
      askToEnableNetwork();
    }
  }

  public String getSignedInUserId() {
    SharedPreferences preferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
    return preferences.getString(BUNDLE_SIGNED_ID, null);
  }

  @Override public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
    SharedPreferences preferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
    preferences.edit().putString(BUNDLE_SIGNED_ID, firebaseAuth.getUid()).apply();
  }

  @Override public void selectUser(User user) {
    Log.d(TAG, "selectUser: " + user.getName());
    String mapTabTitle = getResources().getString(R.string.main_tab_title_map);
    int mapTabIndex = ((MyViewPagerAdapter) mRootViewPager.getAdapter()).getTabPosition(mapTabTitle);
    mRootViewPager.setCurrentItem(mapTabIndex, true);
    // When the FragmentPagerAdapter adds a fragment to the FragmentManager, it uses a special tag based on the particular position that the fragment will be placed.
    // FragmentPagerAdapter.getItem(int position) is only called when a fragment for that position does not exist.
    // After rotating, Android will notice that it already created/saved a fragment for this particular position
    // and so it simply tries to reconnect with it with FragmentManager.findFragmentByTag()
    // https://stackoverflow.com/a/15182405
    MapFragment mapFragment = (MapFragment) getSupportFragmentManager().findFragmentByTag(
        "android:switcher:" + mRootViewPager.getId() + ":" + mapTabIndex);
    mapFragment.selectUser(user);
  }

  //==============================================================================================
  private void initViewPager() {
    Log.d(TAG, "initViewPager: ");
    MyViewPagerAdapter myViewPagerAdapter = new MyViewPagerAdapter(getSupportFragmentManager());
    myViewPagerAdapter.addFragment(new ListFragment(), getResources().getString(R.string.main_tab_title_list));
    myViewPagerAdapter.addFragment(new MapFragment(), getResources().getString(R.string.main_tab_title_map));
    myViewPagerAdapter.addFragment(new ProfileFragment(), getResources().getString(R.string.main_tab_title_profile));
    mRootViewPager.setAdapter(myViewPagerAdapter);
    mMenuTabLayout.setupWithViewPager(mRootViewPager);
  }

  private boolean isNetworkAvailable() {
    ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
    assert connectivityManager != null;
    NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
    return activeNetworkInfo != null && activeNetworkInfo.isConnected();
  }

  private void askToEnableNetwork() {
    AlertDialog.Builder dialogBuidler = new AlertDialog.Builder(this);
    dialogBuidler.setTitle(R.string.main_dialog_no_network);
    dialogBuidler.setMessage(R.string.main_dialog_enable_network);
    dialogBuidler.setCancelable(false);
    dialogBuidler.setPositiveButton("Turn on Wifi", (dialog, which) -> {
      startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
    });
    dialogBuidler.setNegativeButton("Turn on mobile data", (dialog, which) -> {
      Intent intent = new Intent();
      intent.setComponent(
          new ComponentName("com.android.settings", "com.android.settings.Settings$DataUsageSummaryActivity"));
      startActivity(intent);
    });
    dialogBuidler.create().show();
  }
}