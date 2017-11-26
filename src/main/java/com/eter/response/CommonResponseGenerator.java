package com.eter.response;

import com.eter.response.entity.CommonPaging;
import com.eter.response.entity.CommonResponse;
import com.eter.response.entity.CommonResponsePaging;
import com.eter.response.entity.CommonStatus;

public class CommonResponseGenerator {

	public <T> CommonResponse<T> generateCommonResponse(Class<T> clz) {
		CommonResponse<T> resp = new CommonResponse<T>();
		return resp;
	}

	public <T> CommonResponse<T> generateCommonResponse(String responseCode, String responseDesc, Class<T> clz) {
		CommonResponse<T> resp = generateCommonResponse(clz);
		CommonStatus stat = new CommonStatus(responseCode, responseDesc);
		resp.setResponseStatus(stat);
		return resp;
	}

	public <T> CommonResponse<T> generateCommonResponse(T t) {
		CommonResponse<T> resp = new CommonResponse<T>();
		resp.setData(t);
		return resp;
	}

	public <T> CommonResponsePaging<T> generateCommonResponsePaging(Class<T> clz) {
		CommonResponsePaging<T> resp = new CommonResponsePaging<T>();
		return resp;
	}

	public <T> CommonResponsePaging<T> generateCommonResponsePaging(String responseCode, String responseDesc, Class<T> clz) {
		CommonResponsePaging<T> resp = generateCommonResponsePaging(clz);
		CommonStatus stat = new CommonStatus(responseCode, responseDesc);
		resp.setResponseStatus(stat);
		return resp;
	}

	public <T> CommonResponsePaging<T> generateCommonResponsePaging(CommonPaging<T> t) {
		CommonResponsePaging<T> resp = new CommonResponsePaging<T>();
		resp.setPaging(t);
		return resp;
	}
}
