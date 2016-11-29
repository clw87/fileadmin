package com.chang.es.common.enumeration;

public enum ClusterIPEnum {
	CHANG_ES1("127.0.0.1", "leju office pc");
	
	private String name;
	private String desc;
	
	ClusterIPEnum(String name, String desc){
		this.name = name;
		this.desc = desc;
	}

	public String getName() {
		return this.name;
	}

	public String getDesc() {
		return this.desc;
	}
	
}
