package com.eis.dailycallregister.Pojo;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class SpclDcrDcrdChLstRes{

	@SerializedName("error")
	private boolean error;

	@SerializedName("spcldcrdchlst")
	private List<SpcldcrdchlstItem> spcldcrdchlst;

	public void setError(boolean error){
		this.error = error;
	}

	public boolean isError(){
		return error;
	}

	public void setSpcldcrdchlst(List<SpcldcrdchlstItem> spcldcrdchlst){
		this.spcldcrdchlst = spcldcrdchlst;
	}

	public List<SpcldcrdchlstItem> getSpcldcrdchlst(){
		return spcldcrdchlst;
	}

	@Override
 	public String toString(){
		return 
			"SpclDcrDcrdChLstRes{" + 
			"error = '" + error + '\'' + 
			",spcldcrdchlst = '" + spcldcrdchlst + '\'' + 
			"}";
		}
}