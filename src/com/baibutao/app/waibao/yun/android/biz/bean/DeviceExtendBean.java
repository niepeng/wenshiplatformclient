package com.baibutao.app.waibao.yun.android.biz.bean;

import java.io.Serializable;

/**
 * <p>����: �豸����չ��Ϣ</p>
 * <p>����: </p>
 * <p>��Ȩ: lsb</p>
 * <p>����ʱ��: 2017��1��20��  ����12:22:21</p>
 * <p>���ߣ�niepeng</p>
 */
public class DeviceExtendBean implements Serializable {
	
	private static final long serialVersionUID = -4967215127510314319L;

	private String snaddr;

	// ����¶�
	private String maxTemp;
	
	// ����¶�
	private String minTemp;
	
	// ���ʪ��
	private String maxHumi;
	
	// ���ʪ��
	private String minHumi;
	
	// �¶Ȼز�
	private String tempHC;
	
	// ʪ�Ȼز�
	private String humiHC;
	
	
	
	// -------------- extend attribute --------------------

	// -------------- normal method -----------------------
	
	public boolean isDataChange(DeviceExtendBean deviceExtendBean) {
		if(deviceExtendBean == null) {
			return false;
		}
		
		if(!maxHumi.equals(deviceExtendBean.getMaxHumi())) {
			return true;
		}
		
		if(!minHumi.equals(deviceExtendBean.getMinHumi())) {
			return true;
		}
		
		if(!humiHC.equals(deviceExtendBean.getHumiHC())) {
			return true;
		}
		
		if(!maxTemp.equals(deviceExtendBean.getMaxTemp())) {
			return true;
		}
		
		if(!minTemp.equals(deviceExtendBean.getMinTemp())) {
			return true;
		}
		
		if(!tempHC.equals(deviceExtendBean.getTempHC())) {
			return true;
		}

		return false;
	}

	// -------------- setter/getter -----------------------

	public String getMaxTemp() {
		return maxTemp;
	}

	public void setMaxTemp(String maxTemp) {
		this.maxTemp = maxTemp;
	}

	public String getMinTemp() {
		return minTemp;
	}

	public void setMinTemp(String minTemp) {
		this.minTemp = minTemp;
	}

	public String getMaxHumi() {
		return maxHumi;
	}

	public void setMaxHumi(String maxHumi) {
		this.maxHumi = maxHumi;
	}

	public String getMinHumi() {
		return minHumi;
	}

	public void setMinHumi(String minHumi) {
		this.minHumi = minHumi;
	}

	public String getTempHC() {
		return tempHC;
	}

	public void setTempHC(String tempHC) {
		this.tempHC = tempHC;
	}

	public String getHumiHC() {
		return humiHC;
	}

	public void setHumiHC(String humiHC) {
		this.humiHC = humiHC;
	}

	public String getSnaddr() {
		return snaddr;
	}

	public void setSnaddr(String snaddr) {
		this.snaddr = snaddr;
	}
	
}
