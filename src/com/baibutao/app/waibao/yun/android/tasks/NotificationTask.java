package com.baibutao.app.waibao.yun.android.tasks;

import java.util.Date;
import java.util.List;

import org.json.JSONObject;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.baibutao.app.waibao.yun.android.R;
import com.baibutao.app.waibao.yun.android.activites.NavigationActivity;
import com.baibutao.app.waibao.yun.android.androidext.EewebApplication;
import com.baibutao.app.waibao.yun.android.biz.JSONHelper;
import com.baibutao.app.waibao.yun.android.biz.dataobject.AlarmMsgDO;
import com.baibutao.app.waibao.yun.android.common.Tuple;
import com.baibutao.app.waibao.yun.android.config.Config;
import com.baibutao.app.waibao.yun.android.remote.RemoteManager;
import com.baibutao.app.waibao.yun.android.remote.Request;
import com.baibutao.app.waibao.yun.android.remote.Response;
import com.baibutao.app.waibao.yun.android.util.ActionConstant;
import com.baibutao.app.waibao.yun.android.util.CollectionUtil;
import com.baibutao.app.waibao.yun.android.util.DateUtil;
import com.baibutao.app.waibao.yun.android.util.MD5;

public class NotificationTask {

	private EewebApplication eewebApplication;

	public NotificationTask(EewebApplication eewebApplication) {
		this.eewebApplication = eewebApplication;
	}

	public void execute() {
		try {
			// �������ˣ���ȡ�Ƿ���Ҫ֪ͨ�ĸ���
			final RemoteManager remoteManager = RemoteManager.getFullFeatureRemoteManager();
			final Request request = remoteManager.createQueryRequest(Config.getConfig().getProperty(Config.Names.ALARM_LIST_URL));
			request.addParameter("userId", eewebApplication.getUserDO().getId());
			request.addParameter("psw", MD5.getMD5(eewebApplication.getUserDO().getPsw().getBytes()));
			
			final boolean requestAlarmFlag = eewebApplication.getLastRequestAlarmTime() != null;
			if (requestAlarmFlag) {
				request.addParameter("time", DateUtil.format(eewebApplication.getLastRequestAlarmTime(), DateUtil.DATE_FMT));
			}
			new Thread(new Runnable() {
				@Override
				public void run() {
					try {
						Response response = remoteManager.execute(request);
						if (response.isSuccess()) {
							Tuple.Tuple2<List<AlarmMsgDO>, Date> result = JSONHelper.json2AlarmList((JSONObject) response.getModel());
							List<AlarmMsgDO> list = result._1();
							Date currentTime = result._2();
							if (currentTime != null) {
								eewebApplication.setLastRequestAlarmTime(currentTime);
							}
							notifyMsg(list, requestAlarmFlag);
						}
					} catch (Exception e) {
						Log.e(NotificationTask.class.getName(), "notfication task error", e);
					}
				}
			}).start();
		} catch (Exception e) {
			Log.e(NotificationTask.class.getName(), "notfication task error", e);
		}
	}

	private void notifyMsg(List<AlarmMsgDO> list, boolean flag) {
		if (CollectionUtil.isEmpty(list) || !flag) {
			return;
		}

		// ����֪ͨ
		NotificationManager mNotificationManager = (NotificationManager) eewebApplication.getSystemService(Context.NOTIFICATION_SERVICE);
		// ����֪ͨ��չ�ֵ�������Ϣ
		int icon = R.drawable.ic_launcher;
		CharSequence tickerText = "���ƽ̨֪ͨ";
		long when = System.currentTimeMillis() + 2000;
		Notification notification = new Notification(icon, tickerText, when);

		notification.defaults = Notification.DEFAULT_SOUND;
		notification.flags |= Notification.FLAG_AUTO_CANCEL;

		// ��������֪ͨ��ʱҪչ�ֵ�������Ϣ
		Context context = eewebApplication.getApplicationContext();
		CharSequence contentTitle = "���ƽ̨-�����豸���ֱ���";
		String content = "";
		for (int i = 0; i < 3 && i < list.size(); i++) {
			AlarmMsgDO tmp = list.get(i);
			content += (tmp.getAreaName() + "-" + tmp.getShowName() + "��" + tmp.getReason() + "; \r\n ");
		}
		CharSequence contentText = content;
		Intent notificationIntent = new Intent(context, NavigationActivity.class);
		notificationIntent.setAction("sdfaf.dasfs.d.ds");
		notificationIntent.putExtra(ActionConstant.NOTIFICATION_FLAG_KEY, ActionConstant.NOTIFICATION_FLAG_VALUE);
		PendingIntent contentIntent = PendingIntent.getActivity(context, 0, notificationIntent, 0);
		notification.setLatestEventInfo(context, contentTitle, contentText, contentIntent);
		// ��mNotificationManager��notify����֪ͨ�û����ɱ�������Ϣ֪ͨ
		mNotificationManager.notify(995, notification);
	}

}
