package com.gbksoft.android.test.app.main.map;

import com.gbksoft.android.test.app.data.pojo.User;
import com.google.android.gms.maps.model.LatLngBounds;

public interface MapContract {
  interface Presenter{
    void attachView(View view);
    void detachView();
    void mapChanged(LatLngBounds mapRectangle);
    void addUser(User user);
  }

  interface View {
    void displayError(String error);
    void dispatchUser(User users);
    void removeUser(String uid);
    void removeOutsideMarkers(LatLngBounds visibleRectangle);
  }
}
