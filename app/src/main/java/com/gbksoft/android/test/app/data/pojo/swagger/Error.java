package com.gbksoft.android.test.app.data.pojo.swagger;

import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("com.robohorse.robopojogenerator")
public class Error{

	@SerializedName("result")
	private List<ResultItem> result;

	@SerializedName("code")
	private int code;

	@SerializedName("status")
	private String status;

	public void setResult(List<ResultItem> result){
		this.result = result;
	}

	public List<ResultItem> getResult(){
		return result;
	}

	public void setCode(int code){
		this.code = code;
	}

	public int getCode(){
		return code;
	}

	public void setStatus(String status){
		this.status = status;
	}

	public String getStatus(){
		return status;
	}

	@Override
 	public String toString(){
		return 
			"Error{" + 
			"result = '" + result + '\'' + 
			",code = '" + code + '\'' + 
			",status = '" + status + '\'' + 
			"}";
		}
}