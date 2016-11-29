package com.chang.entity;

public class FileEntity {
	
	private String id;
	
	//文件名
	private String name;
	
	//扩展名
	private String extension;
	
	private String fullName;
	
	private String tag;
	
	private Long lastModify;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getExtension() {
		return extension;
	}

	public void setExtension(String extension) {
		this.extension = extension;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public Long getLastModify() {
		return lastModify;
	}

	public void setLastModify(Long lastModify) {
		this.lastModify = lastModify;
	}
}
