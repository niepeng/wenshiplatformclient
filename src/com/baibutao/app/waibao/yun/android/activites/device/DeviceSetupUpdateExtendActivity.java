package com.baibutao.app.waibao.yun.android.activites.device;

import java.util.Map;
import java.util.concurrent.Future;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.baibutao.app.waibao.yun.android.R;
import com.baibutao.app.waibao.yun.android.activites.common.BaseActivity;
import com.baibutao.app.waibao.yun.android.activites.common.ThreadHelper;
import com.baibutao.app.waibao.yun.android.biz.bean.DeviceBean;
import com.baibutao.app.waibao.yun.android.biz.bean.DeviceExtendBean;
import com.baibutao.app.waibao.yun.android.config.Config;
import com.baibutao.app.waibao.yun.android.remote.RemoteManager;
import com.baibutao.app.waibao.yun.android.remote.Request;
import com.baibutao.app.waibao.yun.android.remote.Response;
import com.baibutao.app.waibao.yun.android.remote.parser.StringResponseParser;
import com.baibutao.app.waibao.yun.android.util.CollectionUtil;
import com.baibutao.app.waibao.yun.android.util.JsonUtil;

/**
 * <p>����: </p>
 * <p>����: </p>
 * <p>��Ȩ: lsb</p>
 * <p>����ʱ��: 2017��3��17��  ����3:57:47</p>
 * <p>���ߣ�niepeng</p>
 */
public class DeviceSetupUpdateExtendActivity extends BaseActivity {
	
	private DeviceBean deviceBean;
	private Future<Response> responseFuture;
	private Future<Response> upResponseFuture;
	
	private EditText highTempEt;
	private EditText lowTempEt;
	private TextView distanceTempTv;
	
	private EditText highHumiEt;
	private EditText lowHumiEt;
	private TextView distanceHumiTv;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.device_setup_update_extend);

		Bundle bundle = this.getIntent().getExtras();
		deviceBean = (DeviceBean) bundle.get("deviceBean");
		
		highTempEt = (EditText) findViewById(R.id.device_update_extend_high_temp_et);
		lowTempEt = (EditText) findViewById(R.id.device_update_extend_low_temp_et);
		distanceTempTv = (TextView) findViewById(R.id.device_update_extend_distance_temp_et);
		
		highHumiEt = (EditText) findViewById(R.id.device_update_extend_high_humi_et);
		lowHumiEt = (EditText) findViewById(R.id.device_update_extend_low_humi_et);
		distanceHumiTv = (TextView) findViewById(R.id.device_update_extend_distance_humi_et);
		
		RemoteManager remoteManager = RemoteManager.getRawRemoteManager();
		remoteManager.setResponseParser(new StringResponseParser());
		Request request = remoteManager.createPostRequest(Config.Values.URL);
		final Map<String, Object> map = CollectionUtil.newHashMap();
		map.put("snaddr", deviceBean.getSnaddr());
		request.setBody(JsonUtil.mapToJson(map));
		request.addHeader("type", "getThreshold");
		
		ProgressDialog progressDialog = showProgressDialog(R.string.app_read_data);
		progressDialog.setOnDismissListener(new LoadData());
		responseFuture = eewebApplication.asyInvoke(new ThreadHelper(progressDialog, request, remoteManager));
		
	}

	public void handleBack(View v) {
		this.finish();
	}

	
	public void handleSubmit(View v) {
		
		DeviceExtendBean tmpBean = new DeviceExtendBean();
		tmpBean.setSnaddr(deviceBean.getSnaddr());
		tmpBean.setMaxTemp(highTempEt.getText().toString());
		tmpBean.setMinTemp(lowTempEt.getText().toString());
		tmpBean.setTempHC(distanceTempTv.getText().toString());
		
		tmpBean.setMaxHumi(highHumiEt.getText().toString());
		tmpBean.setMinHumi(lowHumiEt.getText().toString());
		tmpBean.setHumiHC(distanceHumiTv.getText().toString());
		
		if(!deviceBean.getDeviceExtendBean().isDataChange(tmpBean)) {
			this.finish();
			return;
		}

		RemoteManager remoteManager = RemoteManager.getRawRemoteManager();
		remoteManager.setResponseParser(new StringResponseParser());
		Request request = remoteManager.createPostRequest(Config.Values.URL);
		final Map<String, Object> map = CollectionUtil.newHashMap();
		map.put("snaddr", tmpBean.getSnaddr());
		map.put("maxTemp", tmpBean.getMaxTemp());
		map.put("minTemp", tmpBean.getMinTemp());
		map.put("tempHC", tmpBean.getTempHC());
		
		map.put("maxHumi", tmpBean.getMaxHumi());
		map.put("minHumi", tmpBean.getMinHumi());
		map.put("humiHC", tmpBean.getHumiHC());
		
		request.setBody(JsonUtil.mapToJson(map));
		request.addHeader("type", "modifyTH");
		
		ProgressDialog progressDialog = showProgressDialog(R.string.app_up_data);
		progressDialog.setOnDismissListener(new UpData());
		upResponseFuture = eewebApplication.asyInvoke(new ThreadHelper(progressDialog, request, remoteManager));
	}
	
	public void initData() {
		DeviceExtendBean extendBean = deviceBean.getDeviceExtendBean();
		if(extendBean == null) {
			return;
		}
		
		highTempEt.setText(extendBean.getMaxTemp());
		lowTempEt.setText(extendBean.getMinTemp());
		distanceTempTv.setText(extendBean.getTempHC());
		
		highHumiEt.setText(extendBean.getMaxHumi());
		lowHumiEt.setText(extendBean.getMinHumi());
		distanceHumiTv.setText(extendBean.getTempHC());
	}
	
	private class LoadData implements OnDismissListener {
		@Override
		public void onDismiss(DialogInterface dialog) {
			if (responseFuture == null) {
				return;
			}

			try {

				Response response = responseFuture.get();
				DeviceExtendBean extendBean = JsonUtil.jsonToBean((String) response.getModel(), DeviceExtendBean.class);
				if (extendBean == null || !deviceBean.getSnaddr().equals(extendBean.getSnaddr())) {
					toastLong(R.string.data_opt_fail);
					return;
				}
				deviceBean.setDeviceExtendBean(extendBean);
				
				runOnUiThread(new Runnable() {
					public void run() {
						initData();
					}
				});

				
			} catch (Exception e) {
				DeviceSetupUpdateExtendActivity.this.logError(e.getMessage(), e);
			}

		}

	}
	
	private class UpData implements OnDismissListener {
		@Override
		public void onDismiss(DialogInterface dialog) {
			if (upResponseFuture == null) {
				return;
			}

			try {
				Response response = upResponseFuture.get();
				if (response.isDataSuccess()) {
					toastLong(R.string.app_opt_success);
					DeviceSetupUpdateExtendActivity.this.finish();
				} else {
					toastLong(R.string.data_opt_fail);
				}
			} catch (Exception e) {
				DeviceSetupUpdateExtendActivity.this.logError(e.getMessage(), e);
			}

		}
		
	}


}
