package com.gbksoft.android.test.app.data.pojo.swagger;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("com.robohorse.robopojogenerator")
public class ResultItem{

	@SerializedName("field")
	private String field;

	@SerializedName("message")
	private String message;

	public void setField(String field){
		this.field = field;
	}

	public String getField(){
		return field;
	}

	public void setMessage(String message){
		this.message = message;
	}

	public String getMessage(){
		return message;
	}

	@Override
 	public String toString(){
		return 
			"ResultItem{" + 
			"field = '" + field + '\'' + 
			",message = '" + message + '\'' + 
			"}";
		}
}