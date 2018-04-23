package com.gbksoft.android.test.app.main.map;

import com.gbksoft.android.test.app.data.Model;
import com.google.android.gms.maps.model.LatLngBounds;

public class MapPresenter implements MapContract.Presenter {

  private MapContract.View mView;
  private Model mModel;


  @Override public void attachView(MapContract.View view) {
    mView = view;
    mModel = new Model();
  }

  @Override public void detachView() {
    mView = null;
    mModel = null;
  }


  @Override public void mapChanged(LatLngBounds mapRectangle) {

  }
}
