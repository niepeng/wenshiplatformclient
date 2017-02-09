package com.baibutao.app.waibao.yun.android.biz.enums;
public enum EqutypeEnum {

	HUMI(1, "ʪ��"),
	TEMP(2, "�¶�"),
	TEMP_HUMI(3,"��ʪ��");
	
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
