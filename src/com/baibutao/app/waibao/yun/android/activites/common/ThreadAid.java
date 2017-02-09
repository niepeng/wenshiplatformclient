package com.baibutao.app.waibao.yun.android.activites.common;

import java.util.concurrent.Callable;

import android.util.Log;

import com.baibutao.app.waibao.yun.android.remote.RemoteManager;
import com.baibutao.app.waibao.yun.android.remote.Request;
import com.baibutao.app.waibao.yun.android.remote.Response;

/**
 * @author lsb
 *
 * @date 2012-7-9 ����04:10:13
 */
public class ThreadAid implements Callable<Response> {
	
	private ThreadListener threadListener;

	private Request request;
	
	
	public ThreadAid(ThreadListener threadListener, Request request) {
		super();
		this.threadListener = threadListener;
		this.request = request;
	}

	@Override
	public Response call() throws Exception {
		Response response = null;
		try {
			RemoteManager remoteManager = RemoteManager.getFullFeatureRemoteManager();
			response = remoteManager.execute(request);
			return response;
		} catch (Exception e) {
			Log.e("ThreadAid", e.getMessage(), e);
			throw new RuntimeException(e);
		} finally {
			threadListener.onPostExecute(response);
		}
	}


}

