package com.baibutao.app.waibao.yun.android.biz.bean;

import java.io.Serializable;

/**
 * <p>����: ������Ϣ</p>
 * <p>����: </p>
 * <p>��Ȩ: lsb</p>
 * <p>����ʱ��: 2017��1��19��  ����8:26:09</p>
 * <p>���ߣ�niepeng</p>
 */
public class AlarmBean implements Serializable {

	private static final long serialVersionUID = -7173358265352967918L;

	private String msg;
	
	// 1:�¶ȹ���;2:�¶ȹ���;3:ʪ�ȹ���;4:ʪ�ȹ���;5:���ر���;6:�豸����;7:�������쳣;8:������δ����
	private String type;
	
	private String alarmTime;
	
	private String beginEndMark;
	
	// -------------- extend attribute --------------------

	// -------------- normal method -----------------------

	// -------------- setter/getter -----------------------

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getAlarmTime() {
		return alarmTime;
	}

	public void setAlarmTime(String alarmTime) {
		this.alarmTime = alarmTime;
	}

	public String getBeginEndMark() {
		return beginEndMark;
	}

	public void setBeginEndMark(String beginEndMark) {
		this.beginEndMark = beginEndMark;
	}
	
}