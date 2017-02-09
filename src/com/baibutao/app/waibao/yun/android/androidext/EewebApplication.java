/**
 * 
 */
package com.baibutao.app.waibao.yun.android.androidext;

import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;

import android.app.Activity;
import android.app.Application;
import android.widget.TabHost;

import com.baibutao.app.waibao.yun.android.activites.common.TabFlushEnum;
import com.baibutao.app.waibao.yun.android.as.AsynchronizedInvoke;
import com.baibutao.app.waibao.yun.android.biz.LoadImgDO;
import com.baibutao.app.waibao.yun.android.biz.dataobject.UserDO;
import com.baibutao.app.waibao.yun.android.common.GlobalUtil;
import com.baibutao.app.waibao.yun.android.common.MobileUseInfo;
import com.baibutao.app.waibao.yun.android.common.UserInfoHolder;
import com.baibutao.app.waibao.yun.android.config.Config;
import com.baibutao.app.waibao.yun.android.localcache.FileCache;
import com.baibutao.app.waibao.yun.android.localcache.ImageCache;
import com.baibutao.app.waibao.yun.android.remote.RemoteManager;
import com.baibutao.app.waibao.yun.android.util.CollectionUtil;
import com.baibutao.app.waibao.yun.android.util.StringUtil;
import com.baidu.location.LocationClient;

/**
 * @author niepeng
 *
 * @date 2012-9-8 ����5:14:39
 */
public class EewebApplication extends Application {
	
	private AsynchronizedInvoke asynchronizedInvoke;
	
	private int versionCode;

	private String versionName;
	
	private TabHost tabHost;
	
	public TabFlushEnum[] tabFlushEnums = { TabFlushEnum.IN_TIME, TabFlushEnum.ALARM, TabFlushEnum.MORE };

	private List<Activity> activities = CollectionUtil.newArrayList();
	
	private String uid;
	
	public LocationClient mLocationClient; 
	
	private double longitude;
	
	private double latitude;
	
	private Date lastRequestAlarmTime;
	
	private boolean reflushAlarmActivity;
	
	private UserDO userDO;
	
	@Override
	public void onCreate() {
		super.onCreate();
		init();
	}
	
	private void init() {
		
		Config config = Config.getConfig();
		config.init(this);

		GlobalUtil.init(this);
		
		MobileUseInfo mobileUseInfo = MobileUseInfo.getMobileUseInfo();
    	mobileUseInfo.init(this);
    	
		RemoteManager.init(this);
		ImageCache.init(this);
		FileCache.init(this);
		
		asynchronizedInvoke = new AsynchronizedInvoke();
		asynchronizedInvoke.init();
		
		// ɾ������ͼƬ
//		ImageCache.getLocalCacheManager().clearLocalCache();
	}
	

	@Override
	public void onTerminate() {
		try {
			asynchronizedInvoke.cleanup();
			mLocationClient.stop();
		} catch (Exception e) {
		}
		super.onTerminate();
	}

	public void addLoadImage(LoadImgDO loadImgDO) {
		asynchronizedInvoke.addLoadImage(loadImgDO);
	}

	public <V> Future<V> asyInvoke(Callable<V> callable) {
		return asynchronizedInvoke.invoke(callable);
	}

	public void asyCall(Runnable runnable) {
		asynchronizedInvoke.call(runnable);
	}

	public int getVersionCode() {
		if (versionCode == 0) {
			getVersionInfo();
		}
		return versionCode;
	}

	private void getVersionInfo() {
		versionName = "Android_" + Config.getConfig().getVersionPerperty("hui.version.name");
		versionCode = Integer.parseInt(Config.getConfig().getVersionPerperty("hui.version.code"));
	}

	public String getVersionName() {
		if (StringUtil.isEmpty(versionName)) {
			getVersionInfo();
		}
		return versionName;
	}

	public void finishAllActivities() {
		synchronized (activities) {
			for (Activity a : activities) {
				a.finish();
			}
			activities.clear();
		}
	}
	

	public void addActivity(Activity activity) {
		if (activity == null) {
			return;
		}
		synchronized (activities) {
			activities.add(activity);
		}
	}
	
	public MobileUseInfo getMobileUserInfo() {
		return MobileUseInfo.getMobileUseInfo();
	}
	

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}
	
	public TabHost getTabHost() {
		return tabHost;
	}

	public void setTabHost(TabHost tabHost) {
		this.tabHost = tabHost;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public Date getLastRequestAlarmTime() {
		return lastRequestAlarmTime;
	}

	public void setLastRequestAlarmTime(Date lastRequestAlarmTime) {
		this.lastRequestAlarmTime = lastRequestAlarmTime;
	}

	public boolean isReflushAlarmActivity() {
		return reflushAlarmActivity;
	}

	public void setReflushAlarmActivity(boolean reflushAlarmActivity) {
		this.reflushAlarmActivity = reflushAlarmActivity;
	}

	public UserDO getUserDO() {
		if(userDO == null) {
			userDO = UserInfoHolder.getUserDO(this);
		}
		return userDO;
	}

	public void setUserDO(UserDO userDO) {
		UserInfoHolder.saveUser(this, userDO);
		this.userDO = userDO;
	}
	
}
