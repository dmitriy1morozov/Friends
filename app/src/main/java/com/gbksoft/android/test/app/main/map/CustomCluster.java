package com.gbksoft.android.test.app.main.map;

import com.gbksoft.android.test.app.data.pojo.User;
import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.clustering.ClusterItem;

public class CustomCluster implements ClusterItem {

  private final User mUser;

  public CustomCluster(User user) {
    mUser = user;
  }

  public User getUser() {
    return mUser;
  }

  @Override public LatLng getPosition() {
    return new LatLng(mUser.getLat(), mUser.getLon());
  }

  @Override public String getTitle() {
    return mUser.getName();
  }

  @Override public String getSnippet() {
    return null;
  }

  @Override public boolean equals(Object obj) {
    String uid2 = ((CustomCluster) obj).getUser().getUid();
    return uid2.equals(mUser.getUid());
  }
}
