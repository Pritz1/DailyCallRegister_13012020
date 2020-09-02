package com.eis.dailycallregister.Pojo;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class P1P2P3DrProdRes{

	@SerializedName("dtypList")
	private List<String> dtypList;

	@SerializedName("error")
	private boolean error;

	@SerializedName("errormsg")
	private String errormsg;

	@SerializedName("prodidList")
	private List<String> prodidList;

	public void setDtypList(List<String> dtypList){
		this.dtypList = dtypList;
	}

	public List<String> getDtypList(){
		return dtypList;
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
			"P1P2P3DrProdRes{" + 
			"dtypList = '" + dtypList + '\'' + 
			",error = '" + error + '\'' + 
			",errormsg = '" + errormsg + '\'' + 
			",prodidList = '" + prodidList + '\'' + 
			"}";
		}
}