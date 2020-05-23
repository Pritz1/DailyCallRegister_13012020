package com.eis.dailycallregister.Pojo;

import com.google.gson.annotations.SerializedName;

public class IsDCRCorrectRes{

	@SerializedName("docsmp")
	private String docsmp;

	@SerializedName("docgiftnames")
	private String docgiftnames;

	@SerializedName("chem")
	private String chem;

	@SerializedName("docgift")
	private String docgift;

	@SerializedName("docsmpnames")
	private String docsmpnames;

	@SerializedName("doc")
	private String doc;

	@SerializedName("chmPobEnt")
	private String chmPobEnt;


    public String getDocsmp() {
        return docsmp;
    }

    public void setDocsmp(String docsmp) {
        this.docsmp = docsmp;
    }

    public String getDocgiftnames() {
        return docgiftnames;
    }

    public void setDocgiftnames(String docgiftnames) {
        this.docgiftnames = docgiftnames;
    }

    public String getChem() {
        return chem;
    }

    public void setChem(String chem) {
        this.chem = chem;
    }

    public String getDocgift() {
        return docgift;
    }

    public void setDocgift(String docgift) {
        this.docgift = docgift;
    }

    public String getDocsmpnames() {
        return docsmpnames;
    }

    public void setDocsmpnames(String docsmpnames) {
        this.docsmpnames = docsmpnames;
    }

    public String getDoc() {
        return doc;
    }

    public void setDoc(String doc) {
        this.doc = doc;
    }

    public String getChmPobEnt() {
        return chmPobEnt;
    }

    public void setChmPobEnt(String chmPobEnt) {
        this.chmPobEnt = chmPobEnt;
    }

    @Override
 	public String toString(){
		return 
			"IsDCRCorrectRes{" + 
			"docsmp = '" + docsmp + '\'' + 
			",docgiftnames = '" + docgiftnames + '\'' + 
			",chem = '" + chem + '\'' + 
			",docgift = '" + docgift + '\'' + 
			",docsmpnames = '" + docsmpnames + '\'' + 
			",doc = '" + doc + '\'' + 
			",chmPobEnt = '" + chmPobEnt + '\'' +
			"}";
		}
}