package com.baibutao.app.waibao.yun.android.biz.bean;

import java.io.Serializable;

/**
 * <p>����: �豸��ͨ����</p>
 * <p>����: </p>
 * <p>��Ȩ: lsb</p>
 * <p>����ʱ��: 2017��1��19��  ����3:42:15</p>
 * <p>���ߣ�niepeng</p>
 */
public class DeviceDataBean implements Serializable {

	private static final long serialVersionUID = 6310369464846269974L;

	private String snaddr;
	
	// ʪ��
	private String humi;
	
	// -1Ϊ������ֵ���ޣ�0Ϊ����������ֵ�ڣ�1���������ֵ
	private int humiStatus;
	
	// �¶�
	private String temp;
	
	// -1Ϊ������ֵ���ޣ�0Ϊ����������ֵ�ڣ�1���������ֵ
	private int tempStatus;
	
	// ������
	private String in1;
	
	private String time;
	
	/*
	 * 0 -- ���쳣; 
	 * 1 -- �����ߣ����ȼ���ߣ� 
	 * 2 -- �������쳣�����ȼ��ڶ��ߣ�һ���������쳣���������������쳣���𻵣� 
	 * 3 -- ������δ����
	 */
	private String abnormal;
	
	// -------------- extend attribute --------------------
	
	private String startTime;
	
	private String endTime;
	
	private String rangeTime;

	// -------------- normal method -----------------------
	
	public boolean isSuccess() {
		return "0".equals(abnormal);
	}
	
	public boolean isNotConnection() {
		return "3".equals(abnormal);
	}
	
	public String showStatus() {
		StringBuilder sb = new StringBuilder();
		if(tempStatus < 0) {
			sb.append("�¶�ƫ��");
		} else if(tempStatus == 0) {
			sb.append("�¶�����");
		}else if(tempStatus > 0) {
			sb.append("�¶�ƫ��");
		}
		
		sb.append(",");
		
		if(humiStatus < 0) {
			sb.append("ʪ��ƫ��");
		} else if(humiStatus == 0) {
			sb.append("ʪ������");
		}else if(humiStatus > 0) {
			sb.append("ʪ��ƫ��");
		}
		return sb.toString();
	}
	
	// -------------- setter/getter -----------------------
	
	public String getSnaddr() {
		return snaddr;
	}

	public void setSnaddr(String snaddr) {
		this.snaddr = snaddr;
	}

	public String getHumi() {
		return humi;
	}

	public void setHumi(String humi) {
		this.humi = humi;
	}

	public int getHumiStatus() {
		return humiStatus;
	}

	public void setHumiStatus(int humiStatus) {
		this.humiStatus = humiStatus;
	}

	public String getTemp() {
		return temp;
	}

	public void setTemp(String temp) {
		this.temp = temp;
	}

	public int getTempStatus() {
		return tempStatus;
	}

	public void setTempStatus(int tempStatus) {
		this.tempStatus = tempStatus;
	}

	public String getIn1() {
		return in1;
	}

	public void setIn1(String in1) {
		this.in1 = in1;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getAbnormal() {
		return abnormal;
	}

	public void setAbnormal(String abnormal) {
		this.abnormal = abnormal;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getRangeTime() {
		return rangeTime;
	}

	public void setRangeTime(String rangeTime) {
		this.rangeTime = rangeTime;
	}
	
}