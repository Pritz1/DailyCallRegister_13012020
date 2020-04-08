package com.eis.dailycallregister.Pojo;

import com.google.gson.annotations.SerializedName;

public class DefaultResponse {

    @SerializedName("error")
    private boolean error;

    @SerializedName("errormsg")
    private String errormsg;

    public DefaultResponse(boolean error, String errormsg) {
        this.error = error;
        this.errormsg = errormsg;
    }

    public boolean isError() {
        return error;
    }

    public String getErrormsg() {
        return errormsg;
    }
}
