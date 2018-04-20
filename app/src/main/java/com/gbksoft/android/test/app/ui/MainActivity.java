package com.gbksoft.android.test.app.ui;

import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;
import com.gbksoft.android.test.app.R;

public class MainActivity extends AppCompatActivity {

		public static final String BUNDLE_PFOFILE_SETUP = "profileSetup";

		private Toolbar mToolbar;

		@Override protected void onCreate(Bundle savedInstanceState) {
				super.onCreate(savedInstanceState);
				setContentView(R.layout.activity_main);

				mToolbar = findViewById(R.id.toolbar_main);
				setSupportActionBar(mToolbar);

				getSupportActionBar().setTitle("");
				getSupportActionBar().setDisplayShowHomeEnabled(true);

				SharedPreferences prefs = getSharedPreferences(LoginActivity.PREF_NAME, MODE_PRIVATE);
				String token = prefs.getString(LoginActivity.PREF_TOKEN, null);

				final BottomNavigationView bottomNavigationView = findViewById(R.id.navigation_main_menu);
				bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
						@Override public boolean onNavigationItemSelected(@NonNull MenuItem item) {

								switch (item.getItemId()){
										case R.id.navigation_map:
												Toast.makeText(MainActivity.this, "Map", Toast.LENGTH_SHORT).show();
												break;
										case R.id.navigation_list:
												Toast.makeText(MainActivity.this, "List", Toast.LENGTH_SHORT).show();
												break;
										case R.id.navigation_profile:
												Toast.makeText(MainActivity.this, "Profile", Toast.LENGTH_SHORT).show();
												break;
								}
								return false;
						}
				});

				Boolean profileSetup = getIntent().getBooleanExtra(BUNDLE_PFOFILE_SETUP, false);
				if(profileSetup){
						//TODO if profileSetup then start ProfileFragment
				}
		}
}
