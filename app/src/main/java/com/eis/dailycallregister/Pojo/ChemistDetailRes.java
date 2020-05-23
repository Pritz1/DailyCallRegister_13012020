package com.eis.dailycallregister.Pojo;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ChemistDetailRes {

	@SerializedName("chemistdata")
	private List<ChemistdataItem> chemistdata;

	@SerializedName("statelist")
	private List<String> statelist;

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

	@Override
 	public String toString(){
		return 
			"ChemistDetailRes{" +
			"chemistdata = '" + chemistdata + '\'' +
			",statelist = '" + statelist + '\'' +
			",error = '" + error + '\'' + 
			",errormsg = '" + errormsg + '\'' +

			"}";
		}
}