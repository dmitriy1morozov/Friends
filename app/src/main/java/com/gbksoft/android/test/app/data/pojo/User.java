package com.gbksoft.android.test.app.data.pojo;

import android.support.annotation.NonNull;
import java.util.Comparator;
import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("com.robohorse.robopojogenerator")
public class User implements Comparable<User> {

  @SerializedName("mUid")
  private String mUid;
  @SerializedName("mName")
  private String mName;
  @SerializedName("mLat")
  private double mLat;
  @SerializedName("mLon")
  private double mLon;
  @SerializedName("mPictureId")
  private int mPictureId;

  public User() {
  }

  public User(String uid) {
    mUid = uid;
  }

  public void setUid(String uid) {
    this.mUid = uid;
  }

  public String getUid() {
    return mUid;
  }

  public void setName(String fullName) {
    this.mName = fullName;
  }

  public String getName() {
    return mName;
  }

  public void setLat(double lat) {
    this.mLat = lat;
  }

  public double getLat() {
    return mLat;
  }

  public void setLon(double lon) {
    this.mLon = lon;
  }

  public double getLon() {
    return mLon;
  }

  public void setPictureId(int picId) {
    this.mPictureId = picId;
  }

  public int getPictureId() {
    return mPictureId;
  }

  @Override
  public String toString() {
    return
        "User{" +
            "mUid = '" + mUid + '\'' +
            ",mName = '" + mName + '\'' +
            ",mLat = '" + mLat + '\'' +
            ",mLon = '" + mLon + '\'' +
            ",mPictureUri = '" + mPictureId + '\'' +
            "}";
  }

  @Override public int compareTo(@NonNull User o) {
    return this.getUid().compareTo(o.getUid());
  }

  @Override public boolean equals(Object obj) {
    User user2 = (User) obj;
    String uid2 = user2.getUid();
    return uid2.equals(getUid());
  }
}