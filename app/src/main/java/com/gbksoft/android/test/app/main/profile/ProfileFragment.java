package com.gbksoft.android.test.app.main.profile;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import com.bumptech.glide.Glide;
import com.gbksoft.android.test.app.R;
import com.gbksoft.android.test.app.data.pojo.friends.User;
import com.gbksoft.android.test.app.main.MainActivity;

public class ProfileFragment extends Fragment implements ProfileContract.View {

  public static final String TAB_NAME = "Profile";

  @BindView(R.id.image_profile_picture) ImageView mAvatarImage;
  @BindView(R.id.button_profile_edit_image) Button mEditAvatarButton;
  @BindView(R.id.edittext_profile_firstname) EditText mFirstNameText;
  @BindView(R.id.edittext_profile_lastname) EditText mLastNameText;
  @BindView(R.id.radio_profile_male) RadioButton mMaleRadio;
  @BindView(R.id.radio_profile_female) RadioButton mFemaleRadio;
  @BindView(R.id.text_profile_back) TextView mBackButton;
  @BindView(R.id.button_profile_save) Button mDoneButton;
  private Unbinder unbinder;

  private ProfileContract.Presenter mPresenter;

  public ProfileFragment() {
  }

  @Override public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    View rootView = inflater.inflate(R.layout.fragment_profile, container, false);
    unbinder = ButterKnife.bind(this, rootView);

    mPresenter = new ProfilePresenter();
    mPresenter.attachView(this);
    //TODO set actual ID, null - request current user
    mPresenter.setUserID(null);
    return rootView;
  }

  @Override public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    if(getActivity() instanceof OnSaveProfileListener) {
      OnSaveProfileListener callback = (OnSaveProfileListener) getActivity();
      mPresenter.setOnSaveListner(callback);
    }
  }

  @Override public void onStart() {
    super.onStart();
    mPresenter.viewIsReady();
  }

  @Override public void onDestroyView() {
    super.onDestroyView();
    mPresenter.detachView();
    unbinder.unbind();
  }

  //==============================================================================================
  @OnClick(R.id.button_profile_edit_image) public void onChangeAvatar() {
    Toast.makeText(getActivity(), "Change Avatar!", Toast.LENGTH_SHORT).show();
  }

  @OnClick(R.id.button_profile_save) public void onSaveProfile() {
    mPresenter.saveProfile();
  }

  @OnClick(R.id.text_profile_back) public void onBackToHome() {
    if(getActivity() instanceof MainActivity) {
      ((MainActivity) getActivity()).navigateToLoginActivity();
    }
  }

  //==============================================================================================
  @Override public void displayError(String error) {
    Toast.makeText(getActivity(), error, Toast.LENGTH_SHORT).show();
  }

  @Override public void displayUser(User user) {
    //TODO most optimistic scenario. Handle missing details in user
    Uri pictureUrl = Uri.parse(user.getPictureUrl());
    Glide.with(this).load(pictureUrl).into(mAvatarImage);
    mFirstNameText.setText(user.getFirstName());
    mLastNameText.setText(user.getLastName());
    boolean isMale = user.getGender().equals("male");
    boolean isFemale = user.getGender().equals("female");
    mMaleRadio.setChecked(isMale);
    mFemaleRadio.setChecked(isFemale);
  }

  @Override public int getAvatarWidth() {
    DisplayMetrics metrics = new DisplayMetrics();
    getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);
    int width = metrics.widthPixels;
    int height = metrics.heightPixels;
    return Math.min(width, height);
  }
}