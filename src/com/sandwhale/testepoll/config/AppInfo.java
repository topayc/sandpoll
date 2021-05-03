package com.sandwhale.testepoll.config;

import com.sandwhale.testepoll.R;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;

public class AppInfo {
	private static AppInfo appInfo = null;

	public String appVersion;
	public int appVersionCode;
	public String appName;
	public String[] categories;
	public int[] categoryResIds = { 
											R.drawable.all,R.drawable.enter,
											R.drawable.com, R.drawable.fashion,
											R.drawable.life, R.drawable.travle,
											R.drawable.soc, R.drawable.relation,
											R.drawable.sports,R.drawable.etc 
											};

	private AppInfo(Context context) throws NameNotFoundException {

		PackageInfo pInfo = context.getPackageManager().getPackageInfo(
				context.getPackageName(), 0);
		appVersion = pInfo.versionName;
		appVersionCode = pInfo.versionCode;
		categories = context.getResources().getStringArray(
				R.array.left_menu_category);

	}

	public static AppInfo getInstance(Context context)
			throws NameNotFoundException {
		if (appInfo == null) {
			appInfo = new AppInfo(context);
		}
		return appInfo;
	}
}
