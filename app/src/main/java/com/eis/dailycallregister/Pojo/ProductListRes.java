package com.eis.dailycallregister.Pojo;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class ProductListRes{

	@SerializedName("pnameList")
	private List<String> pnameList;

	@SerializedName("error")
	private boolean error;

	@SerializedName("errormsg")
	private String errormsg;

	@SerializedName("prodidList")
	private List<String> prodidList;

	public void setPnameList(List<String> pnameList){
		this.pnameList = pnameList;
	}

	public List<String> getPnameList(){
		return pnameList;
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

	public void setProdidList(List<String> prodidList){
		this.prodidList = prodidList;
	}

	public List<String> getProdidList(){
		return prodidList;
	}

	@Override
 	public String toString(){
		return 
			"ProductListRes{" + 
			"pnameList = '" + pnameList + '\'' + 
			",error = '" + error + '\'' + 
			",errormsg = '" + errormsg + '\'' + 
			",prodidList = '" + prodidList + '\'' + 
			"}";
		}
}