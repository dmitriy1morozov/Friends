package com.gbksoft.android.test.app.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gbksoft.android.test.app.R;

public class ProfileFragment extends Fragment {

		public ProfileFragment() {
				// Required empty public constructor
		}

		@Override public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
				View rootView = inflater.inflate(R.layout.fragment_profile, container, false);
				addToolbar(rootView);
				return rootView;
		}

		void addToolbar(View rootView){
				Toolbar myToolbar = rootView.findViewById(R.id.toolbar_main);
				((AppCompatActivity)getActivity()).setSupportActionBar(myToolbar);
				((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
				myToolbar.setTitle(null);

		}
}
