/**
 * 
 */
package com.baibutao.app.waibao.yun.android.common;

import java.util.Map;

import com.baibutao.app.waibao.yun.android.util.CollectionUtil;
import com.baibutao.app.waibao.yun.android.util.StringUtil;

/**
 * @author niepeng
 *
 * @date 2012-10-18 ����3:42:50
 */
public class CooperationUtil {

	public static final Map<String, String> cooperaters = CollectionUtil.newTreeMap();
	static {
		cooperaters.put("douban", "����");
		cooperaters.put("juhuasuan", "�ۻ���");
		cooperaters.put("weibo", "����΢��");
	}

	public static String format(String name) {
		if (StringUtil.isBlank(name)) {
			return "������飺����";
		}
		String result = cooperaters.get(name);
		if (StringUtil.isBlank(result)) {
			return "������飺����";
		}
		return "������飺" + result;
	}
}
