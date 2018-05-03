package com.gbksoft.android.test.app.main.list;

import com.gbksoft.android.test.app.data.DataContract;
import com.gbksoft.android.test.app.data.Model;
import com.gbksoft.android.test.app.data.pojo.User;
import com.gbksoft.android.test.app.data.pojo.UserNameComparator;
import java.util.ArrayList;
import java.util.Collections;

class ListPresenter implements ListContract.Presenter, DataContract.Presenter {

  private ArrayList<User> mUsers;
  private ListContract.View mView;
  private Model mModel;

  ListPresenter(ArrayList<User> users) {
    mUsers = users;
  }

  @Override public void attachView(ListContract.View view) {
    mView = view;
    mModel = new Model();
    mModel.attachPresenter(this);
    refreshView();
  }

  @Override public void detachView() {
    mView = null;
    mUsers.clear();
    if(mModel != null) {
      mModel.detachPresenter(this);
      mModel = null;
    }
  }

  @Override public void removeUser(String id) {
    mModel.removeUser(id);
  }

  //====================================================================================================================
  @Override public void userRemoved(String uid) {
    User removal = new User(uid);
    if(mUsers.contains(removal)) {
      mUsers.remove(removal);
    }
    Collections.sort(mUsers, new UserNameComparator());
    refreshView();
  }

  @Override public void dispatchUser(User user) {
    if(!mUsers.contains(user)) {
      mUsers.add(user);
    }
    Collections.sort(mUsers, new UserNameComparator());
    refreshView();
  }

  //====================================================================================================================
  private void refreshView() {
    mView.refreshList();
    if(mUsers.isEmpty()) {
      mView.displayNoDataStub();
    } else {
      mView.displayData();
    }
  }
}
