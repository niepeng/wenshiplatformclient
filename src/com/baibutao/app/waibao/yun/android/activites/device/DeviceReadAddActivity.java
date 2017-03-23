package com.baibutao.app.waibao.yun.android.activites.device;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.baibutao.app.waibao.yun.android.R;
import com.baibutao.app.waibao.yun.android.activites.common.BaseActivity;

/**
 * <p>����: </p>
 * <p>����: </p>
 * <p>��Ȩ: lsb</p>
 * <p>����ʱ��: 2017��3��17��  ����8:11:22</p>
 * <p>���ߣ�niepeng</p>
 */
public class DeviceReadAddActivity extends BaseActivity {
	
	private boolean returnNeedRefulsh;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.device_ready_add);
		returnNeedRefulsh = false;
	}
	
	public void handleBack(View v) {
		Intent intent = this.getIntent();
		intent.putExtra("needRefulsh", returnNeedRefulsh);
		this.setResult(ACTIVITY_RESULT_CODE, intent);
		this.finish();
	}
	
	public void handleByOne(View v) {
		alert("handleByOne");
	}

	public void handleBySeri(View v) {
		Intent intent = new Intent(this, DeviceAddBySeriActivity.class);
		startActivityForResult(intent, ACTIVITY_RESULT_CODE);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == ACTIVITY_RESULT_CODE) {
			returnNeedRefulsh = true;
			return;
		}
	}
}
