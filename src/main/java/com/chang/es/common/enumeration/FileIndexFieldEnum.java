package com.chang.es.common.enumeration;

public enum FileIndexFieldEnum {

	ID("id"),
	NAME("name"),
	FULL_NAME("fullname"),
	EXTENSION("extension"),
	TAG("tag"),
	LAST_MODIFY_TIME("lastmodifytime");
	
	private String name;
	
	FileIndexFieldEnum(String name){
		this.name = name;
	}

	public String getName() {
		return this.name;
	}

}
