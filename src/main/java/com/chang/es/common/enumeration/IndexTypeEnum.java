package com.chang.es.common.enumeration;

public enum IndexTypeEnum {
	FILE("file", "文件属性");
	
	private String name;
	private String desc;
	
	IndexTypeEnum(String name, String desc){
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
