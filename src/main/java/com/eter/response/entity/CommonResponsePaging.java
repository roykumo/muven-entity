package com.eter.response.entity;

import com.eter.response.CommonResponseConstant;

public class CommonResponsePaging<T> {
	CommonStatus responseStatus;
	String requestId;
	CommonPaging<T> paging;

	public CommonResponsePaging() {
		responseStatus = new CommonStatus(CommonResponseConstant.DEFAULT_SUCCESS_CODE, CommonResponseConstant.DEFAULT_SUCCESS_DESC);
	}

	public CommonResponsePaging(CommonPaging<T> paging) {
		this();
		this.paging = paging;
	}

	public CommonResponsePaging(CommonStatus status) {
		this.responseStatus = status;
	}

	public CommonPaging<T> getPaging() {
		return paging;
	}

	public String getRequestId() {
		return requestId;
	}

	public CommonStatus getResponseStatus() {
		return responseStatus;
	}

	public void setPaging(CommonPaging<T> paging) {
		this.paging = paging;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

	public void setResponseStatus(CommonStatus responseStatus) {
		this.responseStatus = responseStatus;
	}

}
