package com.eis.dailycallregister.Pojo;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class LoginResponse{

	@SerializedName("menuaccess")
	private List<MenuaccessItem> menuaccess;

	@SerializedName("error")
	private boolean error;

	@SerializedName("errormsg")
	private String errormsg;

	public void setMenuaccess(List<MenuaccessItem> menuaccess){
		this.menuaccess = menuaccess;
	}

	public List<MenuaccessItem> getMenuaccess(){
		return menuaccess;
	}

	public void setError(boolean error){
		this.error = error;
	}

	public boolean isError(){
		return error;
	}

	public void setErrormsg(String errormsg){
		this.errormsg = errormsg;
	}

	public String getErrormsg(){
		return errormsg;
	}

	@Override
 	public String toString(){
		return 
			"LoginResponse{" + 
			"menuaccess = '" + menuaccess + '\'' + 
			",error = '" + error + '\'' + 
			",errormsg = '" + errormsg + '\'' + 
			"}";
		}
}