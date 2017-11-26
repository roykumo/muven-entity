package com.eter.cake.persistence.service;

import java.util.List;

import com.eter.cake.persistence.entity.rest.KeyValue;
import com.eter.response.entity.CommonPaging;

public interface BaseService {
	<T> T getById(String id);

	<T> List<T> getListPaging(int startRow, int rowPerPage, String sortDir, String sort, List<KeyValue> filter);

	int countListPaging(List<KeyValue> filter);

	<T> T save(T model);

	<T> CommonPaging<T> getPaging(int pageSize, int page, String sortDir, String sort, List<KeyValue> filter);
}
