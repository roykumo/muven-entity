package com.eter.response.entity;


public class CommonResponse<T> {
	CommonStatus responseStatus;
	T data;

	public CommonResponse() {
		responseStatus = new CommonStatus("00", "Success");
	}

	public CommonResponse(CommonStatus status) {
		this.responseStatus = status;
	}

	public CommonResponse(T data) {
		this();
		this.data = data;
	}

	public T getData() {
		return data;
	}

	public CommonStatus getResponseStatus() {
		return responseStatus;
	}

	public void setData(T data) {
		this.data = data;
	}

	public void setResponseStatus(CommonStatus responseStatus) {
		this.responseStatus = responseStatus;
	}

}
