package com.eter.response.exception;

@SuppressWarnings("serial")
public class UserException extends Exception {
	String errorCode;
	String errorDesc;
	
	public UserException(String errorCode, String errorDesc) {
		this.errorCode = errorCode;
		this.errorDesc = errorDesc;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public String getErrorDesc() {
		return errorDesc;
	}

	@Override
	public String getMessage() {
		return errorCode + " - " + errorDesc;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public void setErrorDesc(String errorDesc) {
		this.errorDesc = errorDesc;
	}

}
