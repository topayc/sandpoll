package com.sandwhale.testepoll.config;

import java.io.File;
import java.util.TimeZone;

import android.content.Context;
import android.os.Build;
import android.os.Environment;
import android.telephony.TelephonyManager;

public class DeviceInfo {
	public String deviceName;
	public boolean isRooted;
	public int osVersion;
	public String deviceTimeZone;
	public String deviceModelName;
	public String deviceCountry;
	public String devicelanguage;
	public String deviceRegion;
	public String carrier;
	public String carrierIso;

	private Context context;
	
	private static DeviceInfo deviceInfo;
	
	private static final String ROOT_PATH = Environment.getExternalStorageDirectory() + "";
	private static final String ROOTING_PATH_1 = "/system/bin/su";
	private static final String ROOTING_PATH_2 = "/system/xbin/su";
	private static final String ROOTING_PATH_3 = "/system/app/SuperUser.apk";
	private static final String ROOTING_PATH_4 = "/data/data/com.noshufou.android.su";
	
	public String[] RootFilesPath = new String[] { 
			ROOT_PATH + ROOTING_PATH_1,
			ROOT_PATH + ROOTING_PATH_2, ROOT_PATH + ROOTING_PATH_3,
			ROOT_PATH + ROOTING_PATH_4 };

	private  DeviceInfo(Context context) {
		this.context = context;
		initialize();
	}

	public static DeviceInfo getInstance(Context context){
		if (deviceInfo == null){
			deviceInfo = new DeviceInfo(context);
		}
		return deviceInfo;
	}
	
	private void initialize() {
		deviceName = "android";
		isRooted = checkRooting();
		osVersion = Build.VERSION.SDK_INT;
		deviceTimeZone = TimeZone.getDefault().getID();
		deviceModelName = Build.MODEL;
		deviceCountry= context.getResources().getConfiguration().locale.getDisplayCountry();
		devicelanguage = context.getResources().getConfiguration().locale.getDisplayLanguage();
		deviceRegion = devicelanguage;
		
		TelephonyManager telManager = (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
		carrier = telManager.getNetworkOperatorName();
		carrierIso = telManager.getNetworkCountryIso();
	}

	private boolean checkRooting() {
		boolean isRootingFlag = false;

		try {
			Runtime.getRuntime().exec("su");
			isRootingFlag = true;
		} catch (Exception e) {
			// Exception 나면 루팅 false;
			isRootingFlag = false;
		}

		if (!isRootingFlag) {
			isRootingFlag = checkRootingFiles(createFiles(RootFilesPath));
		}
		return false;
	}
	
	  /**
     * 루팅파일 의심 Path를 가진 파일들을 생성 한다.
     */
    private File[] createFiles(String[] sfiles){
        File[] rootingFiles = new File[sfiles.length];
        for(int i=0 ; i < sfiles.length; i++){
            rootingFiles[i] = new File(sfiles[i]);
        }
        return rootingFiles;
    }
     
    /**
     * 루팅파일 여부를 확인 한다.
     */
    private boolean checkRootingFiles(File... file){
        boolean result = false;
        for(File f : file){
            if(f != null && f.exists() && f.isFile()){
                result = true;
                break;
            }else{
                result = false;
            }
        }
        return result;
    }

}
