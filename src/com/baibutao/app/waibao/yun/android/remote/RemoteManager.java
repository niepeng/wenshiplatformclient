package com.baibutao.app.waibao.yun.android.remote;

import com.baibutao.app.waibao.yun.android.androidext.EewebApplication;
import com.baibutao.app.waibao.yun.android.remote.http.HttpRemoteManager;
import com.baibutao.app.waibao.yun.android.remote.proxy.AutoConnectRemoteManager;
import com.baibutao.app.waibao.yun.android.remote.proxy.CheckNetworkStateRemoteManager;
import com.baibutao.app.waibao.yun.android.remote.proxy.SecurityRemoteManager;

/**
 * @author lsb
 *
 * @date 2012-5-29 ����11:18:35
 */
public abstract class RemoteManager {

public abstract Request createQueryRequest(String target);
	
	public abstract Request createPostRequest(String target);
	
	public abstract Response execute(Request request);
	
	protected static EewebApplication cardApplication;
	
	private static RemoteManager defaultRemoteManager = new HttpRemoteManager();
	
	public static void init(EewebApplication cardApplication) {
		RemoteManager.cardApplication = cardApplication;
	}
	
	/**
	 * ԭ����RemoteManager��û�������κδ��� һ�㲻�Ƽ�ʹ��
	 * @return
	 */
	public static RemoteManager getRawRemoteManager() {
		return defaultRemoteManager;
	}
	
	/**
	 * ��̨�����Ĳ��õ�¼����ʹ�����
	 * 
	 * ����ȫǩ����RemoteManager
	 * @return
	 */
	public static RemoteManager getSecurityRemoteManager() {
		return new CheckNetworkStateRemoteManager(new SecurityRemoteManager(defaultRemoteManager));
	}
	
	
	public static RemoteManager getAutoLoginSecurityRemoteManager() {
		return new CheckNetworkStateRemoteManager(new SecurityRemoteManager(defaultRemoteManager));
	}
	
	/**
	 * �������ݵ�ʱ�������������������������
	 * 
	 * 1������ȫǩ��
	 * 4���������״̬
	 * @return
	 */
	public static RemoteManager getPostOnceRemoteManager() {
		return new CheckNetworkStateRemoteManager(new SecurityRemoteManager(defaultRemoteManager));
	}
	
	/**
	 * �û�����������һ�㽨��ʹ�����
	 * 
	 * 1������ȫǩ��
	 * 2���Զ�������3�Σ�
	 * 3���Զ���¼
	 * 4���������״̬
	 * @return
	 */
	public static RemoteManager getFullFeatureRemoteManager() {
		return new CheckNetworkStateRemoteManager(new AutoConnectRemoteManager(new SecurityRemoteManager(defaultRemoteManager), 1));
	}
	
}
