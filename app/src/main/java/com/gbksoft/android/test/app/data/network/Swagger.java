package com.gbksoft.android.test.app.data.network;

import com.gbksoft.android.test.app.data.pojo.swagger.User;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface Swagger {

  String BASE_URL = "https://test-api.live.gbksoft.net/rest/";
  String VERSION = "v1";
  String AUTH_FACEBOOK = "/user/login/facebook?";

  @POST(VERSION + AUTH_FACEBOOK) Call<User>
  facebookAuth(
      @Query("code") String code
  );

  Retrofit mRetrofit = new Retrofit.Builder().baseUrl(BASE_URL)
      .addConverterFactory(GsonConverterFactory.create())
      .build();
}
