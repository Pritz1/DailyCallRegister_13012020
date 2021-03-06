package com.eis.dailycallregister.Pojo;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ChemistDetailRes {

	@SerializedName("chemistdata")
	private List<ChemistdataItem> chemistdata;

	@SerializedName("statelist")
	private List<String> statelist;

	@SerializedName("clsList")
	private List<String> clsList;

	@SerializedName("clsVstList")
	private List<String> clsVstList;

	@SerializedName("patchlist")
	private List<PatchlistItem> patchlist;

	@SerializedName("errormsg")
	private String errormsg;

	@SerializedName("error")
	private boolean error;

	public void setChemistdata(List<ChemistdataItem> chemistdata){
		this.chemistdata = chemistdata;
	}

	public List<ChemistdataItem> getChemistdata(){
		return chemistdata;
	}

	public void setError(boolean error){
		this.error = error;
	}

	public boolean isError(){
		return error;
	}

	public String getErrormsg() {
		return errormsg;
	}

	public void setErrormsg(String errormsg) {
		this.errormsg = errormsg;
	}

	public List<String> getStatelist() {
		return statelist;
	}

	public void setStatelist(List<String> statelist) {
		this.statelist = statelist;
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

	public List<PatchlistItem> getPatchlist() {
		return patchlist;
	}

	public void setPatchlist(List<PatchlistItem> patchlist) {
		this.patchlist = patchlist;
	}

	@Override
 	public String toString(){
		return 
			"ChemistDetailRes{" +
			"chemistdata = '" + chemistdata + '\'' +
			",statelist = '" + statelist + '\'' +
			",clsList = '" + clsList + '\'' +
			",clsVstList = '" + clsVstList + '\'' +
			",patchlist = '" + patchlist + '\'' +
			",error = '" + error + '\'' +
			",errormsg = '" + errormsg + '\'' +

			"}";
		}
}