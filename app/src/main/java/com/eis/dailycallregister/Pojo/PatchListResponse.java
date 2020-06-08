package com.eis.dailycallregister.Pojo;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class PatchListResponse{

	@SerializedName("patchlist")
	private List<PatchlistItem> patchlist;

	@SerializedName("clsList")
	private List<String> clsList;

	@SerializedName("clsVstList")
	private List<String> clsVstList;

	@SerializedName("error")
	private boolean error;

	@SerializedName("errormsg")
	private String errormsg;

	public void setPatchlist(List<PatchlistItem> patchlist){
		this.patchlist = patchlist;
	}

	public List<PatchlistItem> getPatchlist(){
		return patchlist;
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

	public List<String> getClsList() {
		return clsList;
	}

	public void setClsList(List<String> clsList) {
		this.clsList = clsList;
	}

	public List<String> getClsVstList() {
		return clsVstList;
	}

	public void setClsVstList(List<String> clsVstList) {
		this.clsVstList = clsVstList;
	}

	@Override
 	public String toString(){
		return 
			"PatchListResponse{" + 
			"patchlist = '" + patchlist + '\'' + 
			"clsVstList = '" + clsVstList + '\'' +
			"clsList = '" + clsList + '\'' +
			",error = '" + error + '\'' +
			",errormsg = '" + errormsg + '\'' + 
			"}";
		}
}