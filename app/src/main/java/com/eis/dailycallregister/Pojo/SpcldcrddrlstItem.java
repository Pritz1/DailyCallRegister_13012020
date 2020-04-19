package com.eis.dailycallregister.Pojo;

import com.google.gson.annotations.SerializedName;

public class SpcldcrddrlstItem{

	@SerializedName("drcd")
	private String drcd;

	@SerializedName("custflg")
	private String custflg;

	@SerializedName("drname")
	private String drname;

	@SerializedName("catcd")
	private String catcd;

	@SerializedName("degree")
	private String degree;

	@SerializedName("drtype")
	private String drtype;

	@SerializedName("tcpid")
	private String tcpid;

	@SerializedName("class")
	private String jsonMemberClass;

	@SerializedName("novisit")
	private String novisit;

	@SerializedName("wnetid")
	private String wnetid;

@SerializedName("cntcd")
	private String cntcd;

@SerializedName("mobileno")
	private String mobileno;

	public void setDrcd(String drcd){
		this.drcd = drcd;
	}

	public String getDrcd(){
		return drcd;
	}

	public void setCustflg(String custflg){
		this.custflg = custflg;
	}

	public String getCustflg(){
		return custflg;
	}

	public void setDrname(String drname){
		this.drname = drname;
	}

	public String getDrname(){
		return drname;
	}

	public void setCatcd(String catcd){
		this.catcd = catcd;
	}

	public String getCatcd(){
		return catcd;
	}

	public void setDegree(String degree){
		this.degree = degree;
	}

	public String getDegree(){
		return degree;
	}

	public void setDrtype(String drtype){
		this.drtype = drtype;
	}

	public String getDrtype(){
		return drtype;
	}

	public void setTcpid(String tcpid){
		this.tcpid = tcpid;
	}

	public String getTcpid(){
		return tcpid;
	}

	public void setJsonMemberClass(String jsonMemberClass){
		this.jsonMemberClass = jsonMemberClass;
	}

	public String getJsonMemberClass(){
		return jsonMemberClass;
	}

	public void setNovisit(String novisit){
		this.novisit = novisit;
	}

	public String getNovisit(){
		return novisit;
	}

	public void setWnetid(String wnetid){
		this.wnetid = wnetid;
	}

	public String getWnetid(){
		return wnetid;
	}

    public String getCntcd() {
        return cntcd;
    }

    public void setCntcd(String cntcd) {
        this.cntcd = cntcd;
    }

	public String getMobileno() {
		return mobileno;
	}

	public void setMobileno(String mobileno) {
		this.mobileno = mobileno;
	}

	@Override
 	public String toString(){
		return 
			"SpcldcrddrlstItem{" + 
			"drcd = '" + drcd + '\'' + 
			",custflg = '" + custflg + '\'' + 
			",drname = '" + drname + '\'' + 
			",catcd = '" + catcd + '\'' + 
			",degree = '" + degree + '\'' + 
			",drtype = '" + drtype + '\'' + 
			",tcpid = '" + tcpid + '\'' + 
			",class = '" + jsonMemberClass + '\'' + 
			",novisit = '" + novisit + '\'' + 
			",wnetid = '" + wnetid + '\'' + 
			",cntcd = '" + cntcd + '\'' +
			",mobileno = '" + mobileno + '\'' +
			"}";
		}
}