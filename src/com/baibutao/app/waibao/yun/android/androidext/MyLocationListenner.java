package com.baibutao.app.waibao.yun.android.androidext;

import com.baibutao.app.waibao.yun.android.activites.common.BaseActivity;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;

public class MyLocationListenner implements BDLocationListener {

	private BaseActivity baseActivity;
	
	public MyLocationListenner(BaseActivity baseActivity) {
		this.baseActivity = baseActivity;
	}

	@Override
	public void onReceiveLocation(BDLocation location) {
		if (location == null) {
			return;
		}

		baseActivity.getHuiApplication().setLatitude(location.getLatitude());
		baseActivity.getHuiApplication().setLongitude(location.getLongitude());

		// StringBuffer sb = new StringBuffer(256);
		// sb.append("time : ");
		// sb.append(location.getTime());
		// sb.append("\nerror code : ");
		// sb.append(location.getLocType());
		// sb.append("\nlatitude : ");
		// sb.append(location.getLatitude());
		// sb.append("\nlontitude : ");
		// sb.append(location.getLongitude());
		// sb.append("\nradius : ");
		// sb.append(location.getRadius());
		// if (location.getLocType() == BDLocation.TypeGpsLocation) {
		// sb.append("\nspeed : ");
		// sb.append(location.getSpeed());
		// sb.append("\nsatellite : ");
		// sb.append(location.getSatelliteNumber());
		// } else if (location.getLocType() == BDLocation.TypeNetWorkLocation) {
		// sb.append("\naddr : ");
		// sb.append(location.getAddrStr());
		// }
		// baseActivity.alert(sb.toString());
	}

	public void onReceivePoi(BDLocation poiLocation) {
		if (poiLocation == null) {
			return;
		}
//		StringBuffer sb = new StringBuffer(256);
//		sb.append("Poi time : ");
//		sb.append(poiLocation.getTime());
//		sb.append("\nerror code : ");
//		sb.append(poiLocation.getLocType());
//		sb.append("\nlatitude : ");
//		sb.append(poiLocation.getLatitude());
//		sb.append("\nlontitude : ");
//		sb.append(poiLocation.getLongitude());
//		sb.append("\nradius : ");
//		sb.append(poiLocation.getRadius());
//		if (poiLocation.getLocType() == BDLocation.TypeNetWorkLocation) {
//			sb.append("\naddr : ");
//			sb.append(poiLocation.getAddrStr());
//		}
//		if (poiLocation.hasPoi()) {
//			sb.append("\nPoi:");
//			sb.append(poiLocation.getPoi());
//		} else {
//			sb.append("noPoi information");
//		}
//
//		baseActivity.alert(sb.toString());
	}
}