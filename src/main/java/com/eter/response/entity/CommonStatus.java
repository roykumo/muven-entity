package com.eter.response.entity;

public class CommonStatus {
	String responseCode;
	String responseDesc;

	public CommonStatus() {
		
	}
	
	public CommonStatus(String responseCode, String responseDesc) {
		this.responseCode = responseCode;
		this.responseDesc = responseDesc;
	}
	
	public String getResponseCode() {
		return responseCode;
	}

	public String getResponseDesc() {
		return responseDesc;
	}

	public void setResponseCode(String responseCode) {
		this.responseCode = responseCode;
	}

	public void setResponseDesc(String responseDesc) {
		this.responseDesc = responseDesc;
	}
}
