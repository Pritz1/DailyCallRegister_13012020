package com.eis.dailycallregister.Pojo;

import com.google.gson.annotations.SerializedName;

public class PatchlistItem{

	@SerializedName("town")
	private String town;

	@SerializedName("tcpid")
	private String tcpid;

	public void setTown(String town){
		this.town = town;
	}

	public String getTown(){
		return town;
	}

	public void setTcpid(String tcpid){
		this.tcpid = tcpid;
	}

	public String getTcpid(){
		return tcpid;
	}

	@Override
 	public String toString(){
		return 
			/*"PatchlistItem{" +
			"town = '" + town + '\'' + 
			",tcpid = '" + tcpid + '\'' + 
			"}";*/
                tcpid + " - " + town;
		}
}