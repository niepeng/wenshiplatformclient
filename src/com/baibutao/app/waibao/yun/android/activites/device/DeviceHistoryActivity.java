package com.baibutao.app.waibao.yun.android.activites.device;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;

import liuyongxiang.robert.com.testtime.wheelview.ScreenInfo;
import liuyongxiang.robert.com.testtime.wheelview.WheelMain;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.TextView;

import com.baibutao.app.waibao.yun.android.R;
import com.baibutao.app.waibao.yun.android.activites.common.BaseActivity;
import com.baibutao.app.waibao.yun.android.activites.common.ThreadHelper;
import com.baibutao.app.waibao.yun.android.biz.bean.DeviceBean;
import com.baibutao.app.waibao.yun.android.biz.bean.DeviceDataBean;
import com.baibutao.app.waibao.yun.android.config.Config;
import com.baibutao.app.waibao.yun.android.remote.RemoteManager;
import com.baibutao.app.waibao.yun.android.remote.Request;
import com.baibutao.app.waibao.yun.android.remote.Response;
import com.baibutao.app.waibao.yun.android.remote.parser.StringResponseParser;
import com.baibutao.app.waibao.yun.android.util.CollectionUtil;
import com.baibutao.app.waibao.yun.android.util.DateUtil;
import com.baibutao.app.waibao.yun.android.util.JsonUtil;
/**
 * @author niepeng
 * 
 * @date 2012-9-13 ����1:12:28
 */
public class DeviceHistoryActivity extends BaseActivity {
	
	private DeviceBean deviceBean;

	private TextView startTimeTv;
	
	private TextView endTimeTv;
	
	private Spinner distanceSpinner;
	
	private Future<Response> responseFuture;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.device_history);
		
		Bundle bundle = this.getIntent().getExtras();
        deviceBean = (DeviceBean)bundle.get("deviceBean");
		
		distanceSpinner = (Spinner) findViewById(R.id.device_history_distance);
		
		startTimeTv = (TextView) findViewById(R.id.device_history_start_time);
		endTimeTv = (TextView) findViewById(R.id.device_history_end_time);
		
		Date now = new Date();
		startTimeTv.setText(DateUtil.format(DateUtil.changeDay(now, -1),DateUtil.DEFAULT_DATE_FMT));
		endTimeTv.setText(DateUtil.format(now, DateUtil.DEFAULT_DATE_FMT));
		
		if(distanceSpinner.getAdapter().getCount() > 0) {
			distanceSpinner.setSelection(distanceSpinner.getAdapter().getCount()-1);
		}
		
	}
	
	public void handleBack(View v) {
		this.finish();
	}
	
	public void handleSelectStart(View v) {
		showBottoPopupWindow(startTimeTv, endTimeTv);
	}

	public void handleSelectEnd(View v) {
		showBottoPopupWindow(endTimeTv, endTimeTv);
	}
	
	public void handleSubmit(View v) {
		String startTimeStr = startTimeTv.getText().toString();
		String endTimeStr = endTimeTv.getText().toString();
		
		Date startTime = DateUtil.parse(startTimeStr, DateUtil.DEFAULT_DATE_FMT);
		Date endTime = DateUtil.parse(endTimeStr, DateUtil.DEFAULT_DATE_FMT);
		
		String distance = (String)distanceSpinner.getSelectedItem();
		distance = distance.substring(0,distance.indexOf("����"));
				
		RemoteManager remoteManager = RemoteManager.getRawRemoteManager();
		remoteManager.setResponseParser(new StringResponseParser());
		Request request = remoteManager.createPostRequest(Config.Values.URL);
		final Map<String, Object> map = CollectionUtil.newHashMap();
		map.put("snaddr", deviceBean.getSnaddr());
		map.put("startTime", DateUtil.formatDefault(startTime));
		map.put("endTime", DateUtil.formatDefault(endTime));
		map.put("rangeTime", distance);
		request.setBody(JsonUtil.mapToJson(map));
		request.addHeader("type", "getHisData");
		
		
		ProgressDialog progressDialog = showProgressDialog(R.string.app_read_data);
		progressDialog.setOnDismissListener(new LoadData());
		responseFuture = eewebApplication.asyInvoke(new ThreadHelper(progressDialog, request, remoteManager));
		
	}
	
	private class LoadData implements OnDismissListener {

		@Override
		public void onDismiss(DialogInterface dialog) {
			if (responseFuture == null) {
				return;
			}
			
			try {
				Response response = responseFuture.get();
				List<DeviceDataBean> dataList = null;
				
				JSONObject jsonObject = JsonUtil.getJsonObject(response.getModel());
				JSONArray timeList = JsonUtil.getJsonArray(jsonObject, "timeList");
				JSONArray humiList = JsonUtil.getJsonArray(jsonObject, "humiList");
				JSONArray tempList = JsonUtil.getJsonArray(jsonObject, "tempList");
				if (timeList != null && humiList != null && tempList != null && timeList.length() > 0
						&& timeList.length() == humiList.length() && timeList.length() == tempList.length()) {
					dataList = CollectionUtil.newArrayList(timeList.length());
					for (int i = 0, size = timeList.length(); i < size; i++) {
						DeviceDataBean bean = new DeviceDataBean();
						bean.setTemp(tempList.getString(i));
						bean.setHumi(humiList.getString(i));
						bean.setTime(timeList.getString(i));
						dataList.add(bean);
					}
				} else {
					toastLong(R.string.app_no_data);
					return;
				}
				
				// ��ת���µ�ҳ��չʾ����
				if (!CollectionUtil.isEmpty(dataList)) {
					Intent intent = new Intent(DeviceHistoryActivity.this, DeviceHistoryDataActivity.class);
					Bundle bundle = new Bundle();
					bundle.putParcelableArrayList("dataList", (ArrayList) dataList);
					bundle.putSerializable("deviceBean", deviceBean);
					intent.putExtras(bundle);
					startActivity(intent);
				}
				
			} catch (Exception e) {
				DeviceHistoryActivity.this.logError(e.getMessage(), e);
			}
			

		}

	}
	
}
