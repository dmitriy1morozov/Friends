package com.gbksoft.android.test.app.main.list;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.gbksoft.android.test.app.R;

public class ListFragment extends Fragment {

  public static final String TAB_NAME = "List";

  public ListFragment() {
    // Required empty public constructor
  }

  @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    View rootView = inflater.inflate(R.layout.fragment_list, container, false);
    return rootView;
  }

  //================================================================================================

}
