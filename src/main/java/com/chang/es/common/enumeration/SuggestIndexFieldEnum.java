package com.chang.es.common.enumeration;

public enum SuggestIndexFieldEnum {
	
	HITS("hits"),
	REFS("refs"),
	SCORES("scores"),
	FIRSTLETTER("firstletter"),
	PINYIN("pinyin"),
	WORD("word");
	
	private String name;
	
	SuggestIndexFieldEnum(String name){
		this.name = name;
	}

	public String getName() {
		return this.name;
	}
}
