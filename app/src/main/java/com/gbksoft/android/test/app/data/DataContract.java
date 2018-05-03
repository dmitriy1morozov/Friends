package com.gbksoft.android.test.app.data;

import com.gbksoft.android.test.app.data.pojo.User;

public interface DataContract {
  interface Model{
    void addUser(User user);
    void removeUser(String id);
    void attachPresenter(Presenter presenter);
    void detachPresenter(Presenter presenter);
  }

  interface Presenter {
    void userRemoved(String uid);
    void dispatchUser(User user);
  }
}
