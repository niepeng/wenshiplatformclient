package com.baibutao.app.waibao.yun.android.remotesimple;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;

import android.util.Log;

/**
 * <p>����: </p>
 * <p>����: </p>
 * <p>��Ȩ: lsb</p>
 * <p>����ʱ��: 2017��2��28��  ����10:34:03</p>
 * <p>���ߣ�niepeng</p>
 */
public class Httpclient {
	
	public static final int TIMEOUT = 5000;
	
	public static final String DEFAULT_CHARSET = "UTF-8";
	
	public static String subPostForBody(String url, String content, String charset, String headKey, String headValue) {
		Map<String, String> map = new HashMap<String, String>();
		map.put(headKey, headValue);
		return subPostForBody(url, content, charset, map);
	}

	public static String subPostForBody(String url, String content, String charset, Map<String, String> headerMap) {
		HttpPost httpPost = new HttpPost(url);
		try {
			if(charset == null || charset.length() == 0) {
				charset = DEFAULT_CHARSET;
			}
			StringEntity postEntity = new StringEntity(content, charset);
			if(headerMap != null && headerMap.size() > 0) {
				for(Entry<String, String> entry : headerMap.entrySet()) {
					httpPost.addHeader(entry.getKey(), entry.getValue());
				}
			} else {
				httpPost.addHeader("Content-Type", "text/xml");
			}
			httpPost.setEntity(postEntity);
			
			HttpClient httpClient = createHttpclient();
			HttpResponse response = httpClient.execute(httpPost);
			HttpEntity entity = response.getEntity();
			return EntityUtils.toString(entity, charset);
		} catch (Exception e) {
			e.printStackTrace();
			Log.e("kkkkkkkkk", e.toString());
		}
		return null;
	}

	private static HttpClient createHttpclient() {
		HttpParams httpParams = new BasicHttpParams();
		HttpConnectionParams.setConnectionTimeout(httpParams, TIMEOUT);
		HttpConnectionParams.setSoTimeout(httpParams, TIMEOUT);
		HttpClient httpClient = new DefaultHttpClient(httpParams);
		return httpClient;
	} 
}
