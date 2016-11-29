package com.chang.es.common.enumeration;

public enum IndexNameEnum {
	FILE_PROP("filepropertities", "文件属性");
	
	private String name;
	private String desc;
	
	IndexNameEnum(String name, String desc){
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
