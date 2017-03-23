package com.baibutao.app.waibao.yun.android.activites.device;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.baibutao.app.waibao.yun.android.R;
import com.baibutao.app.waibao.yun.android.activites.common.BaseActivity;
import com.baibutao.app.waibao.yun.android.biz.bean.DeviceBean;

/**
 * <p>标题: </p>
 * <p>描述: </p>
 * <p>版权: lsb</p>
 * <p>创建时间: 2017年3月16日  下午4:08:15</p>
 * <p>作者：niepeng</p>
 */
public class DeviceDetailActivity extends BaseActivity {
	
	private DeviceBean deviceBean;
	
	private TextView titleTv;
	private TextView tempTv;
	private TextView humiTv;
	private TextView statusTv;
	private TextView timeTv;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.device_detail);
		
        Bundle bundle = this.getIntent().getExtras();
        deviceBean = (DeviceBean)bundle.get("deviceBean");
        
        titleTv = (TextView) findViewById(R.id.device_detail_title);
        tempTv = (TextView) findViewById(R.id.device_detail_tmp_tv);
        humiTv = (TextView) findViewById(R.id.device_detail_humi_tv);
        statusTv = (TextView) findViewById(R.id.device_detail_status_tv);
        timeTv = (TextView) findViewById(R.id.device_detail_time_tv);
        initData();
	}
	
	public void initData() {
		titleTv.setText(deviceBean.getDevName() + "\n" + deviceBean.getArea());
		if (deviceBean.getDataBean().isSuccess()) {
			tempTv.setText(deviceBean.getDataBean().getTemp());
			humiTv.setText(deviceBean.getDataBean().getHumi());
			statusTv.setText(deviceBean.getDataBean().showStatus());
			timeTv.setText("最后上传时间：" + deviceBean.getDataBean().getTime());
		} else {
			tempTv.setText("--.--");
			humiTv.setText("--.--");
			statusTv.setTextColor(getResources().getColor(R.color.black));
			statusTv.setText(getResources().getString(R.string.app_device_offline));
			timeTv.setText("");
		}
	}

	public void handleBack(View v) {
		this.finish();
	}
	
	public void handleHistoryData(View v) {
		Intent intent = new Intent(DeviceDetailActivity.this, DeviceHistoryActivity.class);
		intent.putExtra("deviceBean", deviceBean);
		startActivity(intent);
	}
	
	public void handleHistoryAlarm(View v) {
		Intent intent = new Intent(DeviceDetailActivity.this, DeviceHistoryAlarmActivity.class);
		intent.putExtra("deviceBean", deviceBean);
		startActivity(intent);
	}
	
	public void handleSetupInfo(View v) {
		Intent intent = new Intent(DeviceDetailActivity.this, DeviceSetupActivity.class);
		intent.putExtra("deviceBean", deviceBean);
		startActivityForResult(intent, ACTIVITY_RESULT_CODE);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == ACTIVITY_RESULT_CODE) {
			Bundle bundle = data.getExtras();
			DeviceBean tmp = (DeviceBean) bundle.get("deviceBean");
			if (tmp != null) {
				deviceBean = tmp;
				initData();
			}
			return;
		}

		if (resultCode == ACTIVITY_DEL_RESULT_CODE) {
			Intent intent = DeviceDetailActivity.this.getIntent();
			DeviceDetailActivity.this.setResult(ACTIVITY_DEL_RESULT_CODE, intent);
			DeviceDetailActivity.this.finish();
			return;
		}
	}  
	
}
