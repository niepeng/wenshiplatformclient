/**
 * 
 */
package com.baibutao.app.waibao.yun.android.activites;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.baibutao.app.waibao.yun.android.R;
import com.baibutao.app.waibao.yun.android.activites.common.BaseActivity;
import com.baibutao.app.waibao.yun.android.activites.common.CheckUpdate;
import com.baibutao.app.waibao.yun.android.activites.common.ThreadHelper;
import com.baibutao.app.waibao.yun.android.config.Config;
import com.baibutao.app.waibao.yun.android.remote.RemoteManager;
import com.baibutao.app.waibao.yun.android.remote.Request;

/**
 * @author niepeng
 * 
 * @date 2012-9-12
 */
public class SetupActivity extends BaseActivity {
	

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.setup);
		
//		Vibrator vib = (Vibrator) getSystemService(Service.VIBRATOR_SERVICE);
//		long[] pattern = {200, 1000, 200, 1000, 200, 1000};
//		vib.vibrate(pattern , -1);
	}
	
	
	public void handleAbout(View v) {
		Intent intent = new Intent(this, MoreAboutActivity.class);
		startActivity(intent);
	}
	
	public void handleUpdateSetup(View v) {
		Intent intent = new Intent(this, SetupUpdateDataTimeActivity.class);
		startActivity(intent);
	}
	
	public void handleNewProducts(View v) {
		Intent intent = new Intent(this, SetupNewProductActivity.class);
		startActivity(intent);
	}
	
	public void handleCheckUpdate(View v) {
		RemoteManager remoteManager = RemoteManager.getSecurityRemoteManager();
		String checkUpdateUrl = Config.getConfig().getProperty(Config.Names.CHECK_UPDATE_URL);
		Request request = remoteManager.createQueryRequest(checkUpdateUrl);
		ProgressDialog progressDialog = showProgressDialog(R.string.app_read_data);
		CheckUpdate checkUpdate = new CheckUpdate(this); 
		progressDialog.setOnDismissListener(checkUpdate);
		checkUpdate.setResponseFuture(eewebApplication.asyInvoke(new ThreadHelper(progressDialog, request)));
	}
	

}
