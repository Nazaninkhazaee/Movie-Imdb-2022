package com.coding.movie.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ErrorWebModel {
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("error_code")
    @Expose
    private Integer error_code;
    @SerializedName("hard_fault")
    @Expose
    private Boolean hard_fault;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("serverTime")
    @Expose
    private long serverTime;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getError_code() {
        return error_code;
    }

    public void setError_code(Integer error_code) {
        this.error_code = error_code;
    }

    public Boolean getHard_fault() {
        return hard_fault;
    }

    public void setHard_fault(Boolean hard_fault) {
        this.hard_fault = hard_fault;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public long getServerTime() {
        return serverTime;
    }

    public void setServerTime(long serverTime) {
        this.serverTime = serverTime;
    }
}
