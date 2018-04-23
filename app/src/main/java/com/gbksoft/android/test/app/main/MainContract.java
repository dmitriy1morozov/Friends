package com.gbksoft.android.test.app.main;

public interface MainContract {
  interface Presenter{
    void attachView(View view);
    void detachView();
    void viewIsReady();
  }

  interface View {

  }
}
