package com.baibutao.app.waibao.yun.android.biz.enums;
public enum EqutypeEnum {

	HUMI(1, "物業"),
	TEMP(2, "梁業"),
	TEMP_HUMI(3,"梁物業");
	
	private final int id;
	
	private final String meaning;

	private EqutypeEnum(int id, String meaning) {
		this.id = id;
		this.meaning = meaning;
	}

	public int getId() {
		return id;
	}

	public String getMeaning() {
		return meaning;
	}
	
} 
