package com.sandwhale.testepoll.common;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.util.Log;

public class CommonUtils {
	public static String TAG = "teamteam";

	public static int str2int(String txt, int mydefault) {
		int num = 0;
		if (txt == null || "".equals(txt)) {
			num = mydefault;
		} else {
			try {
				num = Integer.parseInt(txt);
			} catch (NumberFormatException e) {
				Log.e(TAG, e.toString());
			}
		}
		return num;
	}

	// //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// String 을 int 로 변환
	// //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public static int str2int(String txt) {
		int num = 0;
		try {
			num = Integer.parseInt(txt);
		} catch (NumberFormatException e) {
			Log.e("스트링--> 정수 변환오류", e.toString());
		}
		return num;
	}

	// //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// int 형을 String 로 변환
	// //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public static String int2string(int value) {
		return Integer.toString(value);
	}

	// //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// String 이 null 이면 공백으로 반환한다. null 이 아니면 원래의 String 를 그대로 반환
	// //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public static String null2empty(String str) {
		if (str == null)
			str = "";
		return str;
	}

	public static boolean isNullOrEmpty(String str) {
		if (str == null || "".equals(str.trim())){
			return true;
		}else {
			return false;
		}
	}
	
	public static boolean isNullOrZeroOrEmpty(String str){
		if (str == null || "".equals(str.trim()) || "0".equals(str)){
			return true;
		}else {
			return false;
		}
	}

	// //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// 이메일 형식에 맞는치 체크한다.
	// //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public static boolean isValidEmail(String email) {
		String regExpn = "^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
				+ "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
				+ "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
				+ "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
				+ "[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
				+ "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$";

		CharSequence inputStr = email;
		Pattern pattern = Pattern.compile(regExpn, Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(inputStr);

		if (matcher.matches())
			return true;
		else
			return false;
	}

	public static void CopyStream(InputStream is, OutputStream os) {
		final int buffer_size = 1024;
		try {
			byte[] bytes = new byte[buffer_size];
			for (;;) {
				int count = is.read(bytes, 0, buffer_size);
				if (count == -1)
					break;
				os.write(bytes, 0, count);
			}
		} catch (IOException ex) {
			Log.v("CopyStream",ex.getMessage() + " !! " + ex.toString() );
		}
	}

	public static boolean checkMinLength(String value,int min){
		String tmp = value.trim();
		if (tmp.length() >= min){
			return true;
		}else {
			return false;
		}	
	}
	
	public static String getBeforeExtension(String path, String insert){
		if ( getFileExtension(path).equals("") || getFileExtension(path) == null){
			return null;
		}
		StringBuffer sb = new StringBuffer(path);
		int index = path.lastIndexOf(".");
		sb.insert(index, insert);
		return sb.toString();
	}
	

	public static String getFileExtension(String file){
		return file.substring(file.lastIndexOf(".")+1,file.length());
			
	}
	
}
