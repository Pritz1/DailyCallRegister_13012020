package com.eis.dailycallregister.Pojo;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class SpclDcrdDrListRes{

	@SerializedName("error")
	private boolean error;

	@SerializedName("spcldcrddrlst")
	private List<SpcldcrddrlstItem> spcldcrddrlst;

	public void setSpcldcrddrlst(List<SpcldcrddrlstItem> spcldcrddrlst){
		this.spcldcrddrlst = spcldcrddrlst;
	}

	public List<SpcldcrddrlstItem> getSpcldcrddrlst(){
		return spcldcrddrlst;
	}

	public void setError(boolean error){
		this.error = error;
	}

	public boolean isError(){
		return error;
	}

	@Override
 	public String toString(){
		return 
			"SpclDcrdDrListRes{" +
					"error = '" + error + '\'' +
					",spcldcrddrlst = '" + spcldcrddrlst + '\'' +
			"}";
		}
}