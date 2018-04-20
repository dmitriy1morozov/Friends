package com.gbksoft.android.test.app.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.bumptech.glide.Glide;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.gbksoft.android.test.app.R;

public class LoginActivity extends AppCompatActivity implements FacebookCallback<LoginResult> {

		public static final String PREF_NAME = "com.gbksoft.android.test.app";
		public static final String PREF_TOKEN = "accessToken";

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
				mLoginButton.setReadPermissions("email");
				mLoginButton.registerCallback(mCallbackManager, this);
				LoginManager.getInstance().registerCallback(mCallbackManager, this);
		}

		@Override public void onActivityResult(int requestCode, int resultCode, Intent data) {
				super.onActivityResult(requestCode, resultCode, data);
				mCallbackManager.onActivityResult(requestCode,resultCode,data);
		}

		//==============================================================================================
		@Override public void onSuccess(LoginResult loginResult) {
				AccessToken accessToken = loginResult.getAccessToken();
				//TODO Token may expire!!
				String token = accessToken.getToken();
				SharedPreferences prefs = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
				prefs.edit().putString(PREF_TOKEN, token).apply();

				Intent intent = new Intent(LoginActivity.this, MainActivity.class);
				intent.putExtra(MainActivity.BUNDLE_PFOFILE_SETUP, true);
				startActivity(intent);
		}

		@Override public void onCancel() {

		}

		@Override public void onError(FacebookException error) {

		}
}
