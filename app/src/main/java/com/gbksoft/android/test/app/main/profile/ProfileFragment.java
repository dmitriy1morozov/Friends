package com.gbksoft.android.test.app.main.profile;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import com.firebase.ui.auth.AuthUI;
import com.gbksoft.android.test.app.R;
import com.gbksoft.android.test.app.main.MainActivity;

public class ProfileFragment extends Fragment {

  public static final String TAB_NAME = "Profile";

  @BindView(R.id.text_profile_logout) TextView mLogoutText;
  private Unbinder unbinder;

  @Override public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    View rootView = inflater.inflate(R.layout.fragment_profile, container, false);
    unbinder = ButterKnife.bind(this, rootView);
    return rootView;
  }

  @Override public void onDestroyView() {
    super.onDestroyView();
    unbinder.unbind();
  }

  //==============================================================================================
  @OnClick(R.id.text_profile_logout) public void onLogout() {
    if(getActivity() instanceof MainActivity) {
      MainActivity mainActivity = (MainActivity) getActivity();
      AuthUI.getInstance().signOut(mainActivity).addOnCompleteListener(task -> mainActivity.navigateToLoginActivity());
    }
  }
}