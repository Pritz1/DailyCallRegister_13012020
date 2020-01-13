package com.eis.dailycallregister.Pojo;

import com.google.gson.annotations.SerializedName;

public class PropsItem{

	@SerializedName("dcr")
	private String dcr;

	@SerializedName("RetailerAlert")
	private String retailerAlert;

	@SerializedName("PatientProfile")
	private String patientProfile;

	@SerializedName("RetailReachout")
	private String retailReachout;

	public void setDcr(String dcr){
		this.dcr = dcr;
	}

	public String getDcr(){
		return dcr;
	}

	public void setRetailerAlert(String retailerAlert){
		this.retailerAlert = retailerAlert;
	}

	public String getRetailerAlert(){
		return retailerAlert;
	}

	public void setPatientProfile(String patientProfile){
		this.patientProfile = patientProfile;
	}

	public String getPatientProfile(){
		return patientProfile;
	}

	public void setRetailReachout(String retailReachout){
		this.retailReachout = retailReachout;
	}

	public String getRetailReachout(){
		return retailReachout;
	}

	@Override
 	public String toString(){
		return 
			"PropsItem{" + 
			"dcr = '" + dcr + '\'' + 
			",retailerAlert = '" + retailerAlert + '\'' + 
			",patientProfile = '" + patientProfile + '\'' + 
			",retailReachout = '" + retailReachout + '\'' + 
			"}";
		}
}