package com.baibutao.app.waibao.yun.android.activites;

import java.lang.reflect.Field;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.RelativeLayout;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabHost.TabSpec;
import android.widget.TabWidget;
import android.widget.TextView;

import com.baibutao.app.waibao.yun.android.R;
import com.baibutao.app.waibao.yun.android.activites.common.BaseActivity;
import com.baibutao.app.waibao.yun.android.activites.common.NavigationTabIndexEnum;
import com.baibutao.app.waibao.yun.android.activites.common.TabFlushEnum;
import com.baibutao.app.waibao.yun.android.androidext.EewebApplication;
import com.baibutao.app.waibao.yun.android.androidext.MyLocationListenner;
import com.baibutao.app.waibao.yun.android.common.GlobalUtil;
import com.baibutao.app.waibao.yun.android.service.MessageService;
import com.baibutao.app.waibao.yun.android.util.ActionConstant;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;


public class NavigationActivity extends BaseActivity {
	
	// Ĭ��ѡ��tab��index
	private final int DEFAULT_CHECKED = 0;

	private TabHost tabs;
	
	private TabWidget tabWidget;

	private static final int NAVEIGATION[][] = {
//		{ R.drawable.foot_near_1, R.drawable.foot_near_2 }, 
//		{ R.drawable.foot_shopping_1, R.drawable.foot_shopping_2},
//		{ R.drawable.foot_more_1, R.drawable.foot_more_2 } 
		{ R.drawable.udata, R.drawable.data }, 
		{ R.drawable.ualarm, R.drawable.alarm},
		{ R.drawable.umore, R.drawable.more } 
	};

	protected static final Class<?>[] TAB_ITEMS = { InTimeActivity.class, AlarmActivity.class, SetupActivity.class };

	public BDLocationListener myListener;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.navigation);
		tabs = (TabHost) findViewById(android.R.id.tabhost);
		
		
		// ��������
		Intent intent = new Intent(this, MessageService.class);
		startService(intent);
		
		EewebApplication app = (EewebApplication) getApplication();
		app.setTabHost(tabs);

		tabWidget = (TabWidget) findViewById(android.R.id.tabs);
		int width = GlobalUtil.dipToPixel(50) * 2;
		int height = GlobalUtil.dipToPixel(50);

		tabs.setup(this.getLocalActivityManager());

		addNewsList(tabs);
		addShoppingList(tabs);
		addMore(tabs);

		tabs.setCurrentTab(DEFAULT_CHECKED);

		tabWidget.setPadding(0, 0, 0, 0);

		for (int i = 0; i < tabWidget.getChildCount(); i++) {
			RelativeLayout relativeLayout = (RelativeLayout) tabWidget.getChildAt(i);
			relativeLayout.getLayoutParams().height = height;
			relativeLayout.getLayoutParams().width = width;
			relativeLayout.setPadding(0, -3, 0, 0);

			final TextView tv = (TextView) relativeLayout.findViewById(android.R.id.title);
			tv.setPadding(tv.getPaddingLeft(), -2, tv.getPaddingRight(), 5);
			tv.setTextColor(this.getResources().getColorStateList(android.R.color.white));
			tv.setTextSize(11);
			tv.setGravity(Gravity.CENTER);

			if (i == DEFAULT_CHECKED) {
				tv.setCompoundDrawablesWithIntrinsicBounds(0, NAVEIGATION[i][1], 0, 0);
				relativeLayout.setBackgroundResource(R.color.black);
			} else {
				tv.setCompoundDrawablesWithIntrinsicBounds(0, NAVEIGATION[i][0], 0, 0);
				relativeLayout.setBackgroundResource(R.drawable.nav_selector);
			}
		}

		/**
		 * �˷�����Ϊ��ȥ��ϵͳĬ�ϵ�ɫ�׵ĵ׽�
		 * 
		 * �� TabWidget��mBottomLeftStrip��mBottomRightStrip ����˽�б������������ǿ���ͨ����������ȡ
		 * 
		 * ���ڻ���֪��Android 2.2�Ľӿ�����ô���ģ������ȼӸ��жϺ�һЩ
		 */
		Field mBottomLeftStrip;
		Field mBottomRightStrip;
		if (Integer.parseInt(Build.VERSION.SDK) <= 7) {
			try {
				mBottomLeftStrip = tabWidget.getClass().getDeclaredField("mBottomLeftStrip");
				mBottomRightStrip = tabWidget.getClass().getDeclaredField("mBottomRightStrip");
				if (!mBottomLeftStrip.isAccessible()) {
					mBottomLeftStrip.setAccessible(true);
				}
				if (!mBottomRightStrip.isAccessible()) {
					mBottomRightStrip.setAccessible(true);
				}
				mBottomLeftStrip.set(tabWidget, getResources().getDrawable(R.drawable.tab_one_normal));
				mBottomRightStrip.set(tabWidget, getResources().getDrawable(R.drawable.tab_one_normal));
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			// ȥ��ϵͳĬ�ϵ�ɫ�׵ĵ׽�(̫BT�ķ�����)
			tabs.setPadding(tabs.getPaddingLeft(), tabs.getPaddingTop(), tabs.getPaddingRight(), tabs.getPaddingBottom() - 5);
		}

		/**
		 * �����tabѡ���ʱ�򣬸���ͼƬ
		 */
		tabs.setOnTabChangedListener(new OnTabChangeListener() {
			@Override
			public void onTabChanged(String tabId) {
				tabChange(Integer.parseInt(tabId));
			}
		});
//		notifyDoSomething();
		
		
		/**
		 * �ٶȶ�λ����
		 * �ٶȶ�λsdk ˵����http://developer.baidu.com/map/geosdk-android-developv2.6.htm
		 * API��˵����ַ��http://developer.baidu.com/map/geosdk-android-classv2.6.htm
		 */
		 myListener = new MyLocationListenner(this);
		 eewebApplication.mLocationClient = new LocationClient(this); //����LocationClient��
		 eewebApplication.mLocationClient.registerLocationListener(myListener); //ע���������
	    LocationClientOption option = new LocationClientOption();
	    option.setOpenGps(true);
//	    option.setAddrType("detail");
	    option.setCoorType("bd09ll"); // option.setCoorType("gcj02"); ����������Դ
	    option.setScanSpan(300000);	 // ���¼��ʱ�䣬��λ����
//	    option.disableCache(true);//��ֹ���û��涨λ
//	    option.setPoiNumber(1);	//��෵��POI����
//	    option.setPoiDistance(1000); //poi��ѯ����
//	    option.setPoiExtraInfo(true); //�Ƿ���ҪPOI�ĵ绰�͵�ַ����ϸ��Ϣ
	    eewebApplication.mLocationClient.setLocOption(option);
	    eewebApplication.mLocationClient.start();
	    
	    if (eewebApplication.mLocationClient != null && eewebApplication.mLocationClient.isStarted()) {
	    	eewebApplication.mLocationClient.requestLocation();
	    } 
		    
	}
	
	public void tabChange(int tabIndex) {
		tabs.setCurrentTab(tabIndex);
		
		for (int i = 0; i < tabWidget.getChildCount(); i++) {
			RelativeLayout relativeLayout = (RelativeLayout) tabWidget.getChildAt(i);
			final TextView tv = (TextView) relativeLayout.findViewById(android.R.id.title);
			if (tabs.getCurrentTab() == i) {
				tv.setCompoundDrawablesWithIntrinsicBounds(0, NAVEIGATION[i][1], 0, 0);
				relativeLayout.setBackgroundResource(R.color.black);
			} else {
				tv.setCompoundDrawablesWithIntrinsicBounds(0, NAVEIGATION[i][0], 0, 0);
				relativeLayout.setBackgroundResource(R.drawable.nav_selector);
			}
		}
		
	}
	
	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		setIntent(intent);
		notifyDoSomething();
	}


	public void notifyDoSomething() {
		int flag = getIntent().getIntExtra(ActionConstant.NOTIFICATION_FLAG_KEY, 0);
		if (flag == ActionConstant.NOTIFICATION_FLAG_VALUE) {
			eewebApplication.setReflushAlarmActivity(true);
			tabChange(TabFlushEnum.ALARM.getIndex());
		}
	}

	private void addNewsList(TabHost tabs) {
		Intent intent = new Intent(NavigationActivity.this, InTimeActivity.class);
		TabSpec tabSpec = tabs.newTabSpec(NavigationTabIndexEnum.IN_TIME.getStringValue());
		tabSpec.setIndicator("ʵʱ", null);
		tabSpec.setContent(intent);
		tabs.addTab(tabSpec);
	}
	
	private void addShoppingList(TabHost tabs) {
		Intent intent = new Intent(NavigationActivity.this, AlarmActivity.class);
		TabSpec tabSpec = tabs.newTabSpec(NavigationTabIndexEnum.ALARM.getStringValue());
		tabSpec.setIndicator("����", null);
		tabSpec.setContent(intent);
		tabs.addTab(tabSpec);
	}
	
	private void addMore(TabHost tabs) {
		Intent intent = new Intent(NavigationActivity.this, SetupActivity.class);
		TabSpec tabSpec = tabs.newTabSpec(NavigationTabIndexEnum.SET_UP.getStringValue());
		tabSpec.setIndicator("����", null);
		tabSpec.setContent(intent);
		tabs.addTab(tabSpec);
	}
	
}

