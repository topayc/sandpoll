package com.sandwhale.testepoll.preference;

import android.content.Context;
import android.content.SharedPreferences;

public class UserPreference {
	private Context mContext;
	private SharedPreferences mPreferences; 
	private SharedPreferences.Editor mEditor; 
	
	public static final String PREF_FILE_NAME = "sandwhales_user";

	public static final String KEY_NICK = "nick";
	public static final String KEY_EMAIL = "email";
	public static final String KEY_PASS="pass";
	public static final String KEY_UUID = "uuid";
	public static final String KEY_USER_KEY = "user_id";
	public static final String KEY_DEVICE_ID ="device_id";
	public static final String KEY_BIRTH="birth";
	public static final String KEY_GENDER ="gender";
	public static final String KEY_CITY="city";
	public static final String KEY_JOB="job";
	public static final String KEY_PROFILE_IMAGE ="profile_image";
	
	public UserPreference(Context context){
		this.mContext = context;
		mPreferences = context.getSharedPreferences(PREF_FILE_NAME, 0);
		mEditor = mPreferences.edit();
	}
	
	public int getInt(String key, int defaultValue){
		return mPreferences.getInt(key, defaultValue);
	}
	
	public String getString(String key, String defalutValue){
		return mPreferences.getString(key, defalutValue);
	}
	
	public boolean getBoolean(String key, boolean defaultValue){
		return mPreferences.getBoolean(key, defaultValue);
	}
	
	public void putString(String key, String value){
		mEditor.putString(key, value);
	}
	
	public void putInt(String key, int value){
		mEditor.putInt(key, value);
	}
	
	public void putBoolean(String key, boolean value){
		mEditor.putBoolean(key, value);
	}
	
	public void commit(){
		mEditor.commit();
	}
}
