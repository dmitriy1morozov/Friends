package com.gbksoft.android.test.app.main.map;

import com.google.android.gms.maps.model.LatLngBounds;

public interface MapContract {
  interface Presenter{
    void attachView(View view);
    void detachView();
    void mapChanged(LatLngBounds mapRectangle);
  }

  interface View{
    void displayError(String error);
  }
}
