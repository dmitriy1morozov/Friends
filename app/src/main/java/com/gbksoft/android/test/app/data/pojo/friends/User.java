package com.gbksoft.android.test.app.data.pojo.friends;

import com.google.gson.annotations.SerializedName;

public class User {

  @SerializedName("id")
  private String id;

  @SerializedName("first_name")
  private String firstName;

  @SerializedName("last_name")
  private String lastName;

  @SerializedName("gender")
  private String gender;

  @SerializedName("picture_url")
  private String pictureUrl;

  public void setId(String id) {
    this.id = id;
  }

  public String getId() {
    return id;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setGender(String gender) {
    this.gender = gender;
  }

  public String getGender() {
    return gender;
  }

  public void setPicture(String pictureUrl) {
    this.pictureUrl = pictureUrl;
  }

  public String getPictureUrl() {
    return pictureUrl;
  }

  @Override
  public String toString() {
    return
        "FacebookUser{" +
            "gender = '" + gender + '\'' +
            ",last_name = '" + lastName + '\'' +
            ",id = '" + id + '\'' +
            ",first_name = '" + firstName + '\'' +
            ",picture = '" + pictureUrl + '\'' +
            "}";
  }
}