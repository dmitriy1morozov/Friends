package com.gbksoft.android.test.app.main.list;

public interface ListContract {
  interface Presenter {
    void attachView(View view);
    void detachView();
    void removeUser(String id);
  }

  interface View {
    void displayError(String error);
    void refreshList();
    void displayNoDataStub();
    void displayData();
  }
}
