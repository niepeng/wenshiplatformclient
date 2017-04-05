package com.baibutao.app.waibao.yun.android.tasks;

import org.json.JSONObject;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Handler;
import android.util.Log;

import com.baibutao.app.waibao.yun.android.activites.LoginActivity;
import com.baibutao.app.waibao.yun.android.activites.UpdateClientActivity;
import com.baibutao.app.waibao.yun.android.activites.common.BaseActivity;
import com.baibutao.app.waibao.yun.android.androidext.EewebApplication;
import com.baibutao.app.waibao.yun.android.config.Config;
import com.baibutao.app.waibao.yun.android.remote.RemoteManager;
import com.baibutao.app.waibao.yun.android.remote.Request;
import com.baibutao.app.waibao.yun.android.remote.Response;
import com.baibutao.app.waibao.yun.android.tasks.message.MessageHelper;
import com.baibutao.app.waibao.yun.android.util.JsonUtil;

/**
 * @author lsb
 *
 * @date 2012-5-30 ����10:30:10
 */
public class CheckUpdateTask implements Runnable {
	
	private Handler handler;
	
	private String lastAndroidClientUrl;
	
	private EewebApplication cardApplication;
	
	private BaseActivity baseActivity;
	
	private MessageHelper messageHelper;
	
	public CheckUpdateTask(EewebApplication cardApplication, BaseActivity baseActivity, Handler handler, MessageHelper messageHelper) {
		super();
		this.cardApplication = cardApplication;
		this.baseActivity = baseActivity;
		this.handler = handler;
		this.messageHelper = messageHelper;
	}

	@Override
	public void run() {
		try {
			final RemoteManager remoteManager = RemoteManager.getRawRemoteManager();
			final Request request = remoteManager.createQueryRequest(Config.Values.YUN_CHECK_VERSION_URL);
			request.addParameter("user", cardApplication.getUserDO().getUsername());
			Response response = remoteManager.execute(request);

			if (!response.isSuccess()) {
				Log.w("check update", "fail, because of " + response.getMessage());
				return;
			}
			
			JSONObject jsonObject = (JSONObject)response.getModel();
			JSONObject json = jsonObject.getJSONObject("data");
			int lastAndroidVersion = JsonUtil.getInt(json, "lastversion", 1);
			String lastAndroidClientUrl = JsonUtil.getString(json, "downurl", "");
//			int acceptLowestAndroidVersion = JsonUtil.getInt(json, "lastAndroidVersion", 0);
			logInfo("lastAndroidVersion:" + lastAndroidVersion);
			logInfo("lastAndroidClientUrl:" + lastAndroidClientUrl);
//			logInfo("acceptLowestAndroidVersion:" + acceptLowestAndroidVersion);
			
			this.lastAndroidClientUrl = lastAndroidClientUrl;
			
			final int currentVersion = cardApplication.getVersionCode();
			
			if (currentVersion >= lastAndroidVersion) {
				logInfo("current version:" + currentVersion + ", need not update.");
				return;
			}
			
			/*if (currentVersion < acceptLowestAndroidVersion) {
				logInfo("current version:" + currentVersion + ", but server accept version:" + acceptLowestAndroidVersion + ", must be update!");
				// �ͻ��˰汾̫�ͣ�������������
				// O ����Ӧ�øĳ�ǿ������
				alertChooseForUpdate();
				return;
			}*/
			
			if (currentVersion < lastAndroidVersion) {
				logInfo("current version:" + currentVersion + ", lastAndroidVersion:" + lastAndroidVersion + ", you can choose update.");
				// ���µĿͻ��˰汾���Ƿ���Ҫ���£�
				alertChooseForUpdate();
				return;
			}
			
			
		} catch (Exception e) {
			Log.e("check update", e.getMessage(), e);
		}
		
	}
	
	
	private static void logInfo(String msg) {
		Log.i("check update", msg);
	}
	
	private void alertChooseForUpdate() {
		handler.post(new Runnable() {
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
					messageHelper.set(true);
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
			Intent intent = new Intent();
//			intent.setClass(baseActivity, NavigationActivity.class);
			intent.setClass(baseActivity, LoginActivity.class);
			baseActivity.startActivity(intent);
			baseActivity.finish();
		}
		
	};
	
	
	
}

