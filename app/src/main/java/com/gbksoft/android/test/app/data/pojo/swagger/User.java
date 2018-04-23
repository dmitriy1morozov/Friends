package com.gbksoft.android.test.app.data.pojo.swagger;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("com.robohorse.robopojogenerator")
public class User{

	@SerializedName("country")
	private String country;

	@SerializedName("image")
	private String image;

	@SerializedName("gender")
	private String gender;

	@SerializedName("city")
	private String city;

	@SerializedName("last_name")
	private String lastName;

	@SerializedName("created_at")
	private int createdAt;

	@SerializedName("lon")
	private int lon;

	@SerializedName("accessToken")
	private AccessToken accessToken;

	@SerializedName("updated_at")
	private int updatedAt;

	@SerializedName("id")
	private int id;

	@SerializedName("first_name")
	private String firstName;

	@SerializedName("email")
	private String email;

	@SerializedName("lat")
	private String lat;

	@SerializedName("username")
	private String username;

	public void setCountry(String country){
		this.country = country;
	}

	public String getCountry(){
		return country;
	}

	public void setImage(String image){
		this.image = image;
	}

	public String getImage(){
		return image;
	}

	public void setGender(String gender){
		this.gender = gender;
	}

	public String getGender(){
		return gender;
	}

	public void setCity(String city){
		this.city = city;
	}

	public String getCity(){
		return city;
	}

	public void setLastName(String lastName){
		this.lastName = lastName;
	}

	public String getLastName(){
		return lastName;
	}

	public void setCreatedAt(int createdAt){
		this.createdAt = createdAt;
	}

	public int getCreatedAt(){
		return createdAt;
	}

	public void setLon(int lon){
		this.lon = lon;
	}

	public int getLon(){
		return lon;
	}

	public void setAccessToken(AccessToken accessToken){
		this.accessToken = accessToken;
	}

	public AccessToken getAccessToken(){
		return accessToken;
	}

	public void setUpdatedAt(int updatedAt){
		this.updatedAt = updatedAt;
	}

	public int getUpdatedAt(){
		return updatedAt;
	}

	public void setId(int id){
		this.id = id;
	}

	public int getId(){
		return id;
	}

	public void setFirstName(String firstName){
		this.firstName = firstName;
	}

	public String getFirstName(){
		return firstName;
	}

	public void setEmail(String email){
		this.email = email;
	}

	public String getEmail(){
		return email;
	}

	public void setLat(String lat){
		this.lat = lat;
	}

	public String getLat(){
		return lat;
	}

	public void setUsername(String username){
		this.username = username;
	}

	public String getUsername(){
		return username;
	}

	@Override
 	public String toString(){
		return 
			"User{" + 
			"country = '" + country + '\'' + 
			",image = '" + image + '\'' + 
			",gender = '" + gender + '\'' + 
			",city = '" + city + '\'' + 
			",last_name = '" + lastName + '\'' + 
			",created_at = '" + createdAt + '\'' + 
			",lon = '" + lon + '\'' + 
			",accessToken = '" + accessToken + '\'' + 
			",updated_at = '" + updatedAt + '\'' + 
			",id = '" + id + '\'' + 
			",first_name = '" + firstName + '\'' + 
			",email = '" + email + '\'' + 
			",lat = '" + lat + '\'' + 
			",username = '" + username + '\'' + 
			"}";
		}
}