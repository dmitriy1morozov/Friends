package com.gbksoft.android.test.app.data;

import com.gbksoft.android.test.app.data.pojo.friends.User;

public interface ModelContract {
  interface GetUserDataCallback{
    void dispatchUser(User user);
    void onError(String error);
  }

  void getUserData(String id, int pictureWidth, GetUserDataCallback callback);
}
