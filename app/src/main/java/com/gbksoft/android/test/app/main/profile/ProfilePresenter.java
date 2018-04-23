package com.gbksoft.android.test.app.main.profile;

import com.gbksoft.android.test.app.data.Model;
import com.gbksoft.android.test.app.data.ModelContract;
import com.gbksoft.android.test.app.data.pojo.friends.User;

public class ProfilePresenter implements ProfileContract.Presenter {

  private String mId;
  private ProfileContract.View mView;
  private ModelContract mModel;

  private OnSaveProfileListener mOnSaveProfileListener;

  @Override public void attachView(ProfileContract.View view) {
    mView = view;
    mModel = new Model();
  }

  @Override public void setUserID(String userId) {
    mId = userId;
  }

  @Override public void detachView() {
    mView = null;
    mModel = null;
  }

  @Override public void setOnSaveListner(OnSaveProfileListener callback) {
    mOnSaveProfileListener = callback;
  }

  @Override public void saveProfile() {
    //TODO save profile logic and then if success
    mOnSaveProfileListener.saveProfile();
  }

  @Override public void viewIsReady() {
    int pictureWwidth = mView.getAvatarWidth();
    //TODO move network request to model layer
    //TODO id - заглушка. Используй его для получения конкретного юзера из локальной БД
    mModel.getUserData("id", pictureWwidth, new ModelContract.GetUserDataCallback() {
      @Override public void dispatchUser(User user) {
        mView.displayUser(user);
      }

      @Override public void onError(String error) {
        mView.displayError(error);
      }
    });
  }
}
