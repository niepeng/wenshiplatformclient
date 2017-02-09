package com.baibutao.app.waibao.yun.android.activites.common;

import java.util.Date;
import java.util.Map;

import android.app.ActivityGroup;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.baibutao.app.waibao.yun.android.R;
import com.baibutao.app.waibao.yun.android.androidext.EewebApplication;
import com.baibutao.app.waibao.yun.android.biz.LoadImgDO;
import com.baibutao.app.waibao.yun.android.util.AssetsUtil;
import com.baibutao.app.waibao.yun.android.util.DateUtil;
import com.baibutao.app.waibao.yun.android.util.StringUtil;

/**
 * @author lsb
 * 
 * @date 2012-5-29 下午10:36:46
 */
public class BaseActivity extends ActivityGroup {

	protected EewebApplication eewebApplication;
	
	protected static Drawable defaultImage;
	
	protected static Drawable defaultImageSmall;

	protected final Handler handler = new Handler();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		eewebApplication = (EewebApplication) this.getApplication();
		eewebApplication.addActivity(this);
		
		if(defaultImage == null) {
			defaultImage = getResources().getDrawable(R.drawable.default_img);
		}
		
		if(defaultImageSmall == null) {
			defaultImageSmall = getResources().getDrawable(R.drawable.default_img_small);
		}
		
	}
	
	@Override
	protected void onResume() {
		super.onResume();
//		StatService.onResume(this);
	}
	
	@Override
	protected void onPause() {
		super.onPause();
//		StatService.onPause(this);
	}
	
	protected ProgressDialog showProgressDialog(int message) {
		String title = getString(R.string.app_name);
		String messageString = getString(message);
		return ProgressDialog.show(this, title, messageString);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if (isTabActivity()) {
				confirm("确认退出eeweb监控平台？", new OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						eewebApplication.finishAllActivities(); 
					}
				}, null);
				return true;
			} else {
				this.finish();
			}
		}
		return super.onKeyDown(keyCode, event);
	}
	
	private boolean isTabActivity() {
		for (TabFlushEnum tabFlushEnum : eewebApplication.tabFlushEnums) {
			if (this.getClass().getName().equals(tabFlushEnum.getTabActivity().getName())) {
				return true;
			}
		}
		return false;
	}
	
	
	protected String loadContent(String pathFile, Map<String, String> internVars) {
		String content = AssetsUtil.getAssertAsString(this, pathFile);
		if (!StringUtil.isEmpty(content) && internVars != null && !internVars.isEmpty()) {
			for (Map.Entry<String, String> entry : internVars.entrySet()) {
				content = content.replaceAll(entry.getKey(), entry.getValue());
			}
				
		}
		return content.replaceAll("\r\n", "\n");
	}
	

	public void alert(CharSequence message) {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		AlertDialog alertDialog = builder.create();
		alertDialog.setTitle(getMessageBoxTitle());
		alertDialog.setMessage(message);
		String buttonLabel = getString(R.string.app_btn_label_ok);
		alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, buttonLabel, (Message) null);
		alertDialog.show();
	}

	protected void confirm(CharSequence message, OnClickListener onYesButton, OnClickListener onNoButton) {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		AlertDialog alertDialog = builder.create();
		alertDialog.setTitle(getMessageBoxTitle());
		alertDialog.setMessage(message);
		alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, getString(R.string.app_btn_label_ok), onYesButton);
		alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, getString(R.string.app_btn_label_canel), onNoButton);
		alertDialog.show();
	}
	
	public String getUid() {
		return eewebApplication.getUid();
	}
	
	public void setViewVisible(final View... views) {
		setView(true, views);
	}
	
	
	public void setViewGone(final View ...views) {
		setView(false, views);
	}
	
	private void setView(final boolean isShow, final View... views) {
		if (views == null || views.length == 0) {
			return;
		}
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				for (View view : views) {
					if(view == null) {
						continue;
					}
					int v = view.getVisibility();
					if (isShow && v == View.VISIBLE) {
						continue;
					}
					if (!isShow && v == View.GONE) {
						continue;
					}
					if (isShow) {
						view.setVisibility(View.VISIBLE);
					} else {
						view.setVisibility(View.GONE);
					}
				}
			}
		});
	}
	
	public void setViewGoneBySynchronization(final View... views) {
		setViewBySynchronization(false, views);
	}
	
	public void setViewVisiableBySynchronization(final View... views) {
		setViewBySynchronization(true, views);
	}
	
	
	private void setViewBySynchronization(final boolean isShow, final View... views) {
		if (views == null || views.length == 0) {
			return;
		}
		for (View view : views) {
			if (view == null) {
				continue;
			}
			int v = view.getVisibility();
			if (isShow && v == View.VISIBLE) {
				continue;
			}
			if (!isShow && v == View.GONE) {
				continue;
			}
			if (isShow) {
				view.setVisibility(View.VISIBLE);
			} else {
				view.setVisibility(View.GONE);
			}
		}
	}
	
	
	/**
	 * 隐藏键盘
	 */
	public void hiddenBoard(View v) {
		InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
	}
	
	
	public void asyLoadImages(final ImageView imageView, final String fileName, final String picUrl) {
		LoadImgDO loadImgDO = new LoadImgDO();
		loadImgDO.setHandler(handler);
		loadImgDO.setImageView(imageView);
		loadImgDO.setFileName(fileName);
		loadImgDO.setPicUrl(picUrl);
		eewebApplication.addLoadImage(loadImgDO);
	}
	
	public void asyLoadImages(final ImageView imageView, final String fileName, final String picUrl, int newWidth) {
		LoadImgDO loadImgDO = new LoadImgDO();
		loadImgDO.setHandler(handler);
		loadImgDO.setImageView(imageView);
		loadImgDO.setFileName(fileName);
		loadImgDO.setPicUrl(picUrl);
		loadImgDO.setNewWidth(newWidth);
		eewebApplication.addLoadImage(loadImgDO);
	}
	
	public void setImageViewSize(final ImageView imageView, final Bitmap bitmap, final int newWidth) {
		if (imageView == null) {
			return;
		}
		imageView.getLayoutParams().width = newWidth;
		imageView.getLayoutParams().height = (int) (newWidth * bitmap.getHeight() / Double.valueOf(bitmap.getWidth()).doubleValue());
	}
	

	protected boolean lengthValidator(String content, int min, int max) {
		if (min > max || content == null) {
			return true;
		}

		int length = content.getBytes().length;
		if (length >= min && length <= max) {
			return true;
		}
		return false;
	}
	
	
	public CharSequence getTimes(Date startTime, Date endTime) {
		if (startTime == null) {
			return null;
		}
		if(endTime == null) {
			return DateUtil.format(startTime, DateUtil.DATE_MMddHHmm);
		}
		return DateUtil.format(startTime, DateUtil.DATE_MMddHHmm) + " ~ " + DateUtil.format(endTime, DateUtil.DATE_MMddHHmm);
	}
	
	
	public String getNameByUrl(String url) {
		if(StringUtil.isEmpty(url)) {
			return url;
		}
		int index = url.lastIndexOf("/");
		if(index < 1) {
			return url;
		}
		return url.substring(index + 1);
	}



	protected String getMessageBoxTitle() {
		return this.getTitle().toString();
	}

	protected void toastShort(String message) {
		Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
	}

	protected void toastShort(int message) {
		Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
	}

	protected void toastLong(String message) {
		Toast.makeText(this, message, Toast.LENGTH_LONG).show();
	}

	protected void toastLong(int message) {
		Toast.makeText(this, message, Toast.LENGTH_LONG).show();
	}
	

	protected String getLogTag() {
		return getClass().getSimpleName();
	}

	protected final void logError(String msg, Throwable e) {
		Log.e(getLogTag(), msg, e);
	}

	protected final void logError(String msg) {
		Log.e(getLogTag(), msg);
	}

	protected final void logWarn(String msg) {
		Log.w(getLogTag(), msg);
	}

	protected final void logInfo(String msg) {
		Log.i(getLogTag(), msg);
	}

	protected final void logDebug(String msg) {
		Log.d(getLogTag(), msg);
	}


	public EewebApplication getHuiApplication() {
		return eewebApplication;
	}


	public Handler getHandler() {
		return handler;
	}
	
}
