package com.eter.util;

public class JsonFilter {
	private String entity;
	private String[] fieldsException;

	public JsonFilter(String entity, String... fieldsException) {
		this.entity = entity;
		this.fieldsException = fieldsException;
	}

	public String getEntity() {
		return entity;
	}

	public void setEntity(String entity) {
		this.entity = entity;
	}

	public String[] getFieldsException() {
		return fieldsException;
	}

	public void setFieldsException(String... fieldsException) {
		this.fieldsException = fieldsException;
	}

}
