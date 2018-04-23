package com.gbksoft.android.test.app.data.pojo.facebook;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("com.robohorse.robopojogenerator")
public class Picture {

  @SerializedName("data")
  private Data data;

  public void setData(Data data) {
    this.data = data;
  }

  public Data getData() {
    return data;
  }

  @Override
  public String toString() {
    return
        "Picture{" +
            "data = '" + data + '\'' +
            "}";
  }
}