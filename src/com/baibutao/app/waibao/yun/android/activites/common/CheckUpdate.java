/**
 * 
 */
package com.baibutao.app.waibao.yun.android.activites.common;

import java.util.concurrent.Future;

import org.json.JSONObject;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.DialogInterface.OnDismissListener;
import android.content.Intent;
import android.util.Log;

import com.baibutao.app.waibao.yun.android.activites.UpdateClientActivity;
import com.baibutao.app.waibao.yun.android.remote.Response;
import com.baibutao.app.waibao.yun.android.util.JsonUtil;

/**
 * @author niepeng
 *
 * @date 2012-9-13 ����2:02:06
 */
public class CheckUpdate implements OnDismissListener  {
	
	private String lastAndroidClientUrl;
	
	private Future<Response> responseFuture;
	
	private BaseActivity baseActivity;
	
	public CheckUpdate(BaseActivity baseActivity) {
		this.baseActivity = baseActivity;
	}

	
	@Override
	public void onDismiss(DialogInterface dialog) {

		if (responseFuture == null) {
			return;
		}
		try {
			Response response = responseFuture.get();
			if (!response.isSuccess()) {
				baseActivity.toastLong(response.getMessage());
				return;
			}
			JSONObject jsonObject = (JSONObject) response.getModel();
			JSONObject json = jsonObject.getJSONObject("data");
			final int lastAndroidVersion = JsonUtil.getInt(json, "lastversion", 1);
			final int currentVersion = baseActivity.getHuiApplication().getVersionCode();
			lastAndroidClientUrl = JsonUtil.getString(json, "downurl", "");

			if ((currentVersion < lastAndroidVersion)) {
				// ���µĿͻ��˰汾���Ƿ���Ҫ���£�
				alertChooseForUpdate();
				return;
			} else {
				baseActivity.toastLong("��ǰ�Ѿ������°汾");
			}

		} catch (Exception e) {
			baseActivity.logError(e.getMessage(), e);
		}
	
		
	}
	
	private void alertChooseForUpdate() {
		baseActivity.getHandler().post(new Runnable() {
			@Override
			public void run() {
				try {
					AlertDialog.Builder builder = new AlertDialog.Builder(baseActivity);
					AlertDialog alertDialog = builder.create();
					alertDialog.setTitle("�������");
					alertDialog.setMessage("���µĿͻ��˰汾���Ƿ���µ����°汾��");
					alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "����", updateClientOnClickListener);
					alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "������", doNotUpdateOnClickListener);
					alertDialog.show();
				} catch (Exception e) {
					Log.e("check update", e.getMessage(), e);
				}
			}
		});
	}
	
	
	OnClickListener updateClientOnClickListener = new OnClickListener() {

		@Override
		public void onClick(DialogInterface dialog, int which) {
			Intent intent = new  Intent();
			intent.putExtra("lastAndroidClientUrl", lastAndroidClientUrl);
			intent.setClass(baseActivity, UpdateClientActivity.class);
			baseActivity.startActivity(intent);
		}
		
	};
	
	OnClickListener doNotUpdateOnClickListener = new OnClickListener() {
		@Override
		public void onClick(DialogInterface dialog, int which) {
			
		}
		
	};

	public void setResponseFuture(Future<Response> responseFuture) {
		this.responseFuture = responseFuture;
	}
	

}
