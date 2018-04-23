package com.gbksoft.android.test.app.data;

import android.os.Bundle;
import android.util.Log;
import com.facebook.AccessToken;
import com.facebook.FacebookRequestError;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.gbksoft.android.test.app.data.network.Swagger;
import com.gbksoft.android.test.app.data.pojo.facebook.FacebookUser;
import com.gbksoft.android.test.app.data.pojo.friends.User;
import com.google.gson.Gson;
import java.io.IOException;
import java.util.Locale;
import org.json.JSONObject;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Model implements ModelContract {

  @Override public void getUserData(String id, int pictureWidth, final GetUserDataCallback callback) {
    AccessToken accessToken = AccessToken.getCurrentAccessToken();
    Log.d("MyLogs", "getUserData: accessToken = " + accessToken.getToken());
    Swagger retrofit = Swagger.mRetrofit.create(Swagger.class);
    Call<com.gbksoft.android.test.app.data.pojo.swagger.User> call = retrofit.facebookAuth(accessToken.getToken());
    call.enqueue(new Callback<com.gbksoft.android.test.app.data.pojo.swagger.User>() {
      @Override public void onResponse(Call<com.gbksoft.android.test.app.data.pojo.swagger.User> call,
          Response<com.gbksoft.android.test.app.data.pojo.swagger.User> response) {
        try {
          Log.d("MyLogs", "onResponse: WOOHOO! " + response.errorBody().string());
        } catch(IOException e) {
          e.printStackTrace();
        }
      }
      @Override public void onFailure(Call<com.gbksoft.android.test.app.data.pojo.swagger.User> call, Throwable t) {
        Log.d("MyLogs", "onResponse: SAD! " + t.getMessage());
      }
    });


    GraphRequest request = GraphRequest.newMeRequest(accessToken, new GraphRequest.GraphJSONObjectCallback() {
      @Override public void onCompleted(JSONObject object, GraphResponse response) {
        FacebookRequestError error = response.getError();
        if(error != null) {
          callback.onError(error.getErrorMessage());
        } else {
          //TODO check if local datbase already has such user, if not - download from Swagger
          Gson gson = new Gson();
          FacebookUser facebookUser = gson.fromJson(object.toString(), FacebookUser.class);
          User friendsUser = new User();
          friendsUser.setId(facebookUser.getId());
          friendsUser.setFirstName(facebookUser.getFirstName());
          friendsUser.setLastName(facebookUser.getLastName());
          friendsUser.setGender(facebookUser.getGender());
          friendsUser.setPicture(facebookUser.getPicture().getData().getUrl());
          //TODO save downloaded FriendUser to DB. If success then dispatch it to Presenter
          callback.dispatchUser(friendsUser);
        }
      }
    });

    Bundle parameters = new Bundle();
    String fields = String.format(Locale.US, "id,picture.width(%s),first_name,last_name,gender", pictureWidth);
    parameters.putString("fields", fields);
    request.setParameters(parameters);
    request.executeAsync();
  }
}
