package com.baibutao.app.waibao.yun.android.remote.proxy;

import android.util.Log;

import com.baibutao.app.waibao.yun.android.common.MessageCodes;
import com.baibutao.app.waibao.yun.android.remote.RemoteManager;
import com.baibutao.app.waibao.yun.android.remote.Request;
import com.baibutao.app.waibao.yun.android.remote.Response;

/**
 * @author lsb
 *
 * @date 2012-5-29 ����11:40:07
 */
public class AutoConnectRemoteManager  extends RemoteManager {

	private RemoteManager remoteManager;
	
	private int maxTryTime = 3;
	
	public AutoConnectRemoteManager(RemoteManager remoteManager, int maxTryTime) {
		super();
		this.remoteManager = remoteManager;
		this.maxTryTime = maxTryTime;
	}

	@Override
	public Request createPostRequest(String target) {
		return remoteManager.createPostRequest(target);
	}

	@Override
	public Request createQueryRequest(String target) {
		return remoteManager.createQueryRequest(target);
	}

	@Override
	public Response execute(Request request) {
		// ע��,������ܻ���һ������, ���������Ѿ����͵�����,
		// ���Ƿ�������Ӧ�����������������,��ô���ܻ�����������Ͷ������
		Response response = null;
		for (int i = 0; i < maxTryTime; ++i) {
			response = remoteManager.execute(request);
			if (response.isSuccess()) {
				return response;
			}
			if (response.getCode() != MessageCodes.CONNECT_FAILED) {
				return response;
			}
			Log.w("AutoConnectRemoteManager" , "��������ʧ��, 1�������");
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
			}
		}
		return response;
	}

}
