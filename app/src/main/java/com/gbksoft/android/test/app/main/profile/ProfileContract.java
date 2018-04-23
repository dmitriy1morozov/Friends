package com.gbksoft.android.test.app.main.profile;

import com.gbksoft.android.test.app.data.pojo.friends.User;

public interface ProfileContract {
  interface Presenter{
    void attachView(View view);
    void setUserID(String userId);
    void detachView();
    void setOnSaveListner(OnSaveProfileListener listener);
    void saveProfile();
    void viewIsReady();
  }

  interface View{
    void displayError(String error);
    void displayUser(User user);
    int getAvatarWidth();
  }
}
