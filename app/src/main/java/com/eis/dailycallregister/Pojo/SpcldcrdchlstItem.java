package com.eis.dailycallregister.Pojo;

import com.google.gson.annotations.SerializedName;

public class SpcldcrdchlstItem{

	@SerializedName("custflg")
	private String custflg;

	@SerializedName("stcd")
	private String stcd;

	@SerializedName("dcrno")
	private String dcrno;

	@SerializedName("cntcd")
	private String cntcd;

	@SerializedName("tcpid")
	private String tcpid;

	@SerializedName("sttype")
	private String sttype;

	@SerializedName("class")
	private String jsonMemberClass;

	@SerializedName("wnetid")
	private String wnetid;

	@SerializedName("stname")
	private String stname;

	@SerializedName("pob")
	private String pob;

	public void setCustflg(String custflg){
		this.custflg = custflg;
	}

	public String getCustflg(){
		return custflg;
	}

	public void setStcd(String stcd){
		this.stcd = stcd;
	}

	public String getStcd(){
		return stcd;
	}

	public void setDcrno(String dcrno){
		this.dcrno = dcrno;
	}

	public String getDcrno(){
		return dcrno;
	}

	public void setCntcd(String cntcd){
		this.cntcd = cntcd;
	}

	public String getCntcd(){
		return cntcd;
	}

	public void setTcpid(String tcpid){
		this.tcpid = tcpid;
	}

	public String getTcpid(){
		return tcpid;
	}

	public void setSttype(String sttype){
		this.sttype = sttype;
	}

	public String getSttype(){
		return sttype;
	}

	public void setJsonMemberClass(String jsonMemberClass){
		this.jsonMemberClass = jsonMemberClass;
	}

	public String getJsonMemberClass(){
		return jsonMemberClass;
	}

	public void setWnetid(String wnetid){
		this.wnetid = wnetid;
	}

	public String getWnetid(){
		return wnetid;
	}

	public void setStname(String stname){
		this.stname = stname;
	}

	public String getStname(){
		return stname;
	}

	public String getPob() {
		return pob;
	}

	public void setPob(String pob) {
		this.pob = pob;
	}

	@Override
 	public String toString(){
		return 
			"SpcldcrdchlstItem{" + 
			"custflg = '" + custflg + '\'' + 
			",stcd = '" + stcd + '\'' + 
			",dcrno = '" + dcrno + '\'' + 
			",cntcd = '" + cntcd + '\'' + 
			",tcpid = '" + tcpid + '\'' + 
			",sttype = '" + sttype + '\'' + 
			",class = '" + jsonMemberClass + '\'' + 
			",wnetid = '" + wnetid + '\'' + 
			",stname = '" + stname + '\'' + 
			",pob = '" + pob + '\'' +
			"}";
		}
}