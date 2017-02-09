package com.baibutao.app.waibao.yun.android.biz.dataobject;

public class SetupDO {

	// ��ʱ���ݸ������ڣ���λ ��
	private int updateIntime;
	
	// ��������ʱ�����ڣ���λ ��
	private int alarmtime;
	
	public SetupDO() {
		this.alarmtime = 5 * 60;
		this.updateIntime = 1 * 60;
	}
	
	public SetupDO(int intime, int alarm) {
		this.updateIntime = intime;
		this.alarmtime = alarm;
	}

	public int getUpdateIntime() {
		return updateIntime;
	}

	public void setUpdateIntime(int updateIntime) {
		this.updateIntime = updateIntime;
	}

	public int getAlarmtime() {
		return alarmtime;
	}

	public void setAlarmtime(int alarmtime) {
		this.alarmtime = alarmtime;
	}
	
}
