package com.gbksoft.android.test.app.data.pojo.swagger;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("com.robohorse.robopojogenerator")
public class AccessToken{

	@SerializedName("expired_at")
	private int expiredAt;

	@SerializedName("token")
	private String token;

	public void setExpiredAt(int expiredAt){
		this.expiredAt = expiredAt;
	}

	public int getExpiredAt(){
		return expiredAt;
	}

	public void setToken(String token){
		this.token = token;
	}

	public String getToken(){
		return token;
	}

	@Override
 	public String toString(){
		return 
			"AccessToken{" + 
			"expired_at = '" + expiredAt + '\'' + 
			",token = '" + token + '\'' + 
			"}";
		}
}