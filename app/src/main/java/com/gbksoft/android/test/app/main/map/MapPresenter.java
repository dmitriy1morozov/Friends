package com.gbksoft.android.test.app.main.map;

import android.util.Log;
import com.gbksoft.android.test.app.data.DataContract;
import com.gbksoft.android.test.app.data.Model;
import com.gbksoft.android.test.app.data.pojo.User;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.maps.android.SphericalUtil;
import java.util.Set;
import java.util.TreeSet;

public class MapPresenter implements MapContract.Presenter, DataContract.Presenter {

  private static final String TAG = "MyLogs MapPresenter";
  private MapContract.View mView;
  private Model mModel;
  private LatLngBounds mObservingRectangle;
  private Set<User> mTotalUsers;

  @Override public void attachView(MapContract.View view) {
    Log.d(TAG, "attachView: ");
    mView = view;
    mModel = new Model();
    mModel.attachPresenter(this);
    mTotalUsers = new TreeSet<>();
    mObservingRectangle = new LatLngBounds(new LatLng(0, 0), new LatLng(0, 0));
  }

  @Override public void detachView() {
    Log.d(TAG, "detachView: ");
    mView = null;
    mTotalUsers.clear();
    if(mModel != null) {
      mModel.detachPresenter(this);
      mModel = null;
    }
  }

  @Override public void mapChanged(LatLngBounds newRectangle) {
    Log.d(TAG, "mapChanged: ");
    LatLngBounds oldRectangle = mObservingRectangle;

    for(User user : mTotalUsers) {
      LatLng position = new LatLng(user.getLat(), user.getLon());
      if(newRectangle.contains(position) && !oldRectangle.contains(position)) {
        mView.dispatchUser(user);
      }
    }
    mObservingRectangle = newRectangle;
    mView.removeOutsideMarkers(mObservingRectangle);
  }

  @Override public void addUser(User user) {
    Log.d(TAG, "addUser: ");
    if(user.getName().isEmpty()) {
      mView.displayError("Username is empty. Please fill in the information");
    } else {
      mModel.addUser(user);
    }
  }

  //====================================================================================================================
  @Override public void dispatchUser(User user) {
    Log.d(TAG, "dispatchUser: ");
    if(!mTotalUsers.contains(user)) {
      mTotalUsers.add(user);
    }

    LatLng position = new LatLng(user.getLat(), user.getLon());
    if(mObservingRectangle.contains(position)) {
      mView.dispatchUser(user);
    }
  }

  @Override public void userRemoved(String uid) {
    Log.d(TAG, "userRemoved: uid = " + uid);
    User removingUser = new User(uid);
    if(mTotalUsers.contains(removingUser)) {
      mTotalUsers.remove(removingUser);
      mView.removeUser(uid);
    }
  }
}