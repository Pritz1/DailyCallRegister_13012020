package com.eis.dailycallregister.Pojo;

import com.google.gson.annotations.SerializedName;

public class MenuaccessItem{

	@SerializedName("dcr")
	private String dcr;

	@SerializedName("retailerAlert")
	private String retailerAlert;

	@SerializedName("elearning")
	private String elearning;

	@SerializedName("retailReachOut")
	private String retailReachOut;

	@SerializedName("mtp")
	private String mtp;

	@SerializedName("vps")
	private String vps;

	@SerializedName("report")
	private String report;

	@SerializedName("uploadVisitingCard")
	private String uploadVisitingCard;

	@SerializedName("mgrRcpa")
	private String mgrRcpa;

	@SerializedName("patientProfile")
	private String patientProfile;

	@SerializedName("hodcr") //patanjali
	private String hodcr;

	@SerializedName("homtp")
	private String hoMtp;

	@SerializedName("audioMsg")
	private String audioMsg;

	@SerializedName("imgMsg")
	private String imgMsg;

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

	public void setElearning(String elearning){
		this.elearning = elearning;
	}

	public String getElearning(){
		return elearning;
	}

	public void setRetailReachOut(String retailReachOut){
		this.retailReachOut = retailReachOut;
	}

	public String getRetailReachOut(){
		return retailReachOut;
	}

	public void setMtp(String mtp){
		this.mtp = mtp;
	}

	public String getMtp(){
		return mtp;
	}

	public void setVps(String vps){
		this.vps = vps;
	}

	public String getVps(){
		return vps;
	}

	public void setReport(String report){
		this.report = report;
	}

	public String getReport(){
		return report;
	}

	public void setUploadVisitingCard(String uploadVisitingCard){
		this.uploadVisitingCard = uploadVisitingCard;
	}

	public String getUploadVisitingCard(){
		return uploadVisitingCard;
	}

	public void setMgrRcpa(String mgrRcpa){
		this.mgrRcpa = mgrRcpa;
	}

	public String getMgrRcpa(){
		return mgrRcpa;
	}

	public void setPatientProfile(String patientProfile){
		this.patientProfile = patientProfile;
	}

	public String getPatientProfile(){
		return patientProfile;
	}
	
		public String getHodcr() {
		return hodcr;
	}

	public void setHodcr(String hodcr) {
		this.hodcr = hodcr;
	}

	public String getHoMtp() {
		return hoMtp;
	}

	public void setHoMtp(String hoMtp) {
		this.hoMtp = hoMtp;
	}

	public String getAudioMsg() {
		return audioMsg;
	}

	public void setAudioMsg(String audioMsg) {
		this.audioMsg = audioMsg;
	}

	public String getImgMsg() {
		return imgMsg;
	}

	public void setImgMsg(String imgMsg) {
		this.imgMsg = imgMsg;
	}

	@Override
 	public String toString(){
		return 
			"MenuaccessItem{" + 
			"dcr = '" + dcr + '\'' + 
			",retailerAlert = '" + retailerAlert + '\'' + 
			",elearning = '" + elearning + '\'' + 
			",retailReachOut = '" + retailReachOut + '\'' + 
			",mtp = '" + mtp + '\'' + 
			",vps = '" + vps + '\'' + 
			",report = '" + report + '\'' + 
			",uploadVisitingCard = '" + uploadVisitingCard + '\'' + 
			",mgrRcpa = '" + mgrRcpa + '\'' + 
			",patientProfile = '" + patientProfile + '\'' + 
			",audioMsg = '" + audioMsg + '\'' +
			",imgMsg = '" + imgMsg + '\'' +
			"}";
		}
}