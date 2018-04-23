package com.gbksoft.android.test.app.login;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.bumptech.glide.Glide;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginBehavior;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.gbksoft.android.test.app.R;
import com.gbksoft.android.test.app.main.MainActivity;
import java.util.Arrays;

public class LoginActivity extends AppCompatActivity implements FacebookCallback<LoginResult> {

  @BindView(R.id.image_login_logo) ImageView mLogoImageView;
  @BindView(R.id.button_login_facebook) LoginButton mLoginButton;
  private CallbackManager mCallbackManager;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_login);
    ButterKnife.bind(this);
  }

  @Override protected void onStart() {
    super.onStart();
    String logoUrl = getResources().getString(R.string.login_logo_url);
    Glide.with(this).load(logoUrl).into(mLogoImageView);

    mCallbackManager = CallbackManager.Factory.create();
    mLoginButton.setReadPermissions("public_profile","email");
    mLoginButton.registerCallback(mCallbackManager, this);
    LoginManager.getInstance().registerCallback(mCallbackManager, this);
    LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("public_profile", "email"));
  }

  @Override public void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    mCallbackManager.onActivityResult(requestCode, resultCode, data);
  }

  //==============================================================================================
  @Override public void onSuccess(LoginResult loginResult) {
    Log.d("MyLogs", "onSuccess: " + loginResult.getAccessToken().getToken());
    SharedPreferences prefs = getSharedPreferences(MainActivity.PREF_NAME, MODE_PRIVATE);
    prefs.edit().putBoolean(MainActivity.BUNDLE_SHOULD_SETUP_PROFILE, true).apply();
    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
    startActivity(intent);
  }

  @Override public void onCancel() {
  }

  @Override public void onError(FacebookException error) {
  }
}