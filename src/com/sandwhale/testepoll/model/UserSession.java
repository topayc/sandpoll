package com.sandwhale.testepoll.model;

import com.sandwhale.testepoll.common.CommonUtils;
import com.sandwhale.testepoll.preference.UserPreference;

import android.content.Context;

public class UserSession {
	
	public static UserSession userSession;
	public User user;
	
	private UserSession(){}
	
	public static UserSession getUserSession(){
		if (userSession  == null){
			userSession = new UserSession();
			userSession.user = new User();
		}
		return userSession;
	}
	
	public void load(Context context){
		UserPreference userPref = new UserPreference(context);
		user.nick = userPref.getString(UserPreference.KEY_NICK, "");
		user.email = userPref.getString(UserPreference.KEY_EMAIL, "");
		user.pass = userPref.getString(UserPreference.KEY_PASS, "");
		user.uuid = userPref.getString(UserPreference.KEY_UUID, "");
		user.birth = userPref.getString(UserPreference.KEY_BIRTH, "");
		user.gender = userPref.getString(UserPreference.KEY_GENDER, "");
		user.city = userPref.getString(UserPreference.KEY_CITY, "");
		user.job = userPref.getString(UserPreference.KEY_JOB, "");
		user.profileImage = userPref.getString(UserPreference.KEY_PROFILE_IMAGE, "");
		user.userId = userPref.getString(UserPreference.KEY_USER_KEY, "");
	}
	
	public void save(Context context){
		UserPreference userPref = new UserPreference(context);
		userPref.putString(UserPreference.KEY_NICK, user.nick);
		userPref.putString(UserPreference.KEY_EMAIL, user.email);
		userPref.putString(UserPreference.KEY_PASS, user.pass);
		userPref.putString(UserPreference.KEY_UUID, user.uuid);
		userPref.putString(UserPreference.KEY_BIRTH, user.birth);
		userPref.putString(UserPreference.KEY_GENDER, user.gender);
		userPref.putString(UserPreference.KEY_CITY, user.city);
		userPref.putString(UserPreference.KEY_JOB, user.job);
		userPref.putString(UserPreference.KEY_PROFILE_IMAGE, user.profileImage);
		userPref.putString(UserPreference.KEY_USER_KEY, user.userId);
		userPref.commit();
	}
	
	public boolean isAuthencated(){
		if (user == null || CommonUtils.isNullOrEmpty(user.userId)){
			return false;
		}else {
			return true;
		}
	}
	
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}



	public static class User {
		public String nick;
		public String email;
		public String pass;
		public String uuid;
		public String birth;
		public String gender;
		public String city;
		public String job;
		public String profileImage;
		public String userId;

		public  User() {}
	}
}
