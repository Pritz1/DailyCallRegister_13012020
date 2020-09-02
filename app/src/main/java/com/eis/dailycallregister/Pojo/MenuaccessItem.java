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
	
	
	@SerializedName("hoojt")
	private String hoojt;

	@SerializedName("audioMsg")
	private String audioMsg;

	@SerializedName("imgMsg")
	private String imgMsg;

	@SerializedName("spclDcr") //during lockdown
	private String spclRep;

	@SerializedName("spclDcrChPopup") //during lockdown -> if pop up questionare required in chemist data or not.
	private String spclDcrChPopup;

	@SerializedName("dcrChQPopup") //for pop up questionnaire required in chemist data or not.
	private String dcrChQPopup;

	@SerializedName("chemAddEdit") //06/05/2020 - prithvi
	private String chemAddEdit;

	@SerializedName("sodPhn") //06/05/2020 - prithvi
	private String sodPhn;

	@SerializedName("otherCust") //06/05/2020 - prithvi
	private String otherCust;

	@SerializedName("p1p2p3") //06/05/2020 - prithvi
	private String p1p2p3;

	public void setDcr(String dcr){
		this.dcr = dcr;
	}

	public String getDcr(){
		return dcr;
	}
	public String getHoojt() {
		return hoojt;
	}

	public void setHoojt(String hoojt) {
		this.hoojt = hoojt;
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

	public String getSpclRep() {
		return spclRep;
	}

	public void setSpclRep(String spclRep) {
		this.spclRep = spclRep;
	}

	public String getSpclDcrChPopup() {
		return spclDcrChPopup;
	}

	public void setSpclDcrChPopup(String spclDcrChPopup) {
		this.spclDcrChPopup = spclDcrChPopup;
	}

	public String getChemAddEdit() {
		return chemAddEdit;
	}

	public void setChemAddEdit(String chemAddEdit) {
		this.chemAddEdit = chemAddEdit;
	}

	public String getSodPhn() {
		return sodPhn;
	}

	public String getOtherCust() {
		return otherCust;
	}

	public void setOtherCust(String otherCust) {
		this.otherCust = otherCust;
	}

	public String getP1p2p3() {
		return p1p2p3;
	}

	public void setP1p2p3(String p1p2p3) {
		this.p1p2p3 = p1p2p3;
	}

	@Override
 	public String toString(){
		return 
			"MenuaccessItem{" + 
			"dcr = '" + dcr + '\'' + 
			",hodcr = '" + hodcr + '\'' +
			",hoojt = '" + hoojt + '\'' +
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
			",spclRep = '" + spclRep + '\'' +
			",spclDcrChPopup = '" + spclDcrChPopup + '\'' +
			",chemAddEdit = '" + chemAddEdit + '\'' +
			",sodPhn = '" + sodPhn + '\'' +
			",otherCust = '" + otherCust + '\'' +
			",p1p2p3 = '" + p1p2p3 + '\'' +
			"}";
		}
}