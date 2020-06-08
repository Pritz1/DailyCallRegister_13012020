package com.eis.dailycallregister.Pojo;

import com.google.gson.annotations.SerializedName;

public class ChemistdataItem{

	@SerializedName("keyp")
	private String keyp;

	@SerializedName("mobileno")
	private String mobileno;

	@SerializedName("img_url")
	private String img_url;

	@SerializedName("tcpid")
	private String tcpid;

	@SerializedName("stname")
	private String stname;

	@SerializedName("novisit")
	private String novisit;

	@SerializedName("add1")
	private String add1;

	@SerializedName("add2")
	private String add2;

	@SerializedName("add3")
	private String add3;

	@SerializedName("city")
	private String city;

	@SerializedName("state")
	private String state;

	@SerializedName("pincode")
	private String pincode;

	@SerializedName("cls")
	private String cls;


	public void setKeyp(String keyp){
		this.keyp = keyp;
	}

	public String getKeyp(){
		return keyp;
	}

	public void setMobileno(String mobileno){
		this.mobileno = mobileno;
	}

	public String getMobileno(){
		return mobileno;
	}

	public String getImg_url() {
		return img_url;
	}

	public void setImg_url(String img_url) {
		this.img_url = img_url;
	}

	public String getTcpid() {
		return tcpid;
	}

	public void setTcpid(String tcpid) {
		this.tcpid = tcpid;
	}

	public String getStname() {
		return stname;
	}

	public void setStname(String stname) {
		this.stname = stname;
	}

	public String getNovisit() {
		return novisit;
	}

	public void setNovisit(String novisit) {
		this.novisit = novisit;
	}

	public String getAdd1() {
		return add1;
	}

	public void setAdd1(String add1) {
		this.add1 = add1;
	}

	public String getAdd2() {
		return add2;
	}

	public void setAdd2(String add2) {
		this.add2 = add2;
	}

	public String getAdd3() {
		return add3;
	}

	public void setAdd3(String add3) {
		this.add3 = add3;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getPincode() {
		return pincode;
	}

	public void setPincode(String pincode) {
		this.pincode = pincode;
	}

	public String getCls() {
		return cls;
	}

	public void setCls(String cls) {
		this.cls = cls;
	}

	@Override
 	public String toString(){
		return 
			"ChemistdataItem{" + 
			"keyp = '" + keyp + '\'' + 
			",mobileno = '" + mobileno + '\'' +
			",img_url = '" + img_url + '\'' +
			",tcpid = '" + tcpid + '\'' +
			",stname = '" + stname + '\'' +
			",novisit = '" + novisit + '\'' +
			",add1 = '" + add1 + '\'' +
			",add2 = '" + add2 + '\'' +
			",add3 = '" + add3 + '\'' +
			",city = '" + city + '\'' +
			",state = '" + state + '\'' +
			",pincode = '" + pincode + '\'' +
			",cls = '" + cls + '\'' +
			"}";
		}
}
