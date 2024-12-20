package com.sandwhale.testepoll.common;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Point;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.provider.MediaStore;
import android.telephony.PhoneNumberUtils;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class AndroUtils {

	// //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// 폰의 로케일이 한국어 인지를 조사함
	// //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public static boolean isLocalelKorea(Context context){
		return context.getResources().getConfiguration().locale.getLanguage().equalsIgnoreCase("ko");
	}
	
	// //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// 폰의 로케일을 반환
	// //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public static Locale getLocale(Context context){
		return context.getResources().getConfiguration().locale;
	}
	
	// //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// 폰의 로케일의 언어코드 문자열을 반환함
	// //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public static String getLocaleText(Context context){
		return context.getResources().getConfiguration().locale.getLanguage();
	}
	
	// //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// 비트맵을 바이트 배열로 변환
	// //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public static byte[] bitmapToByteArray(Bitmap bitmap) {
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		bitmap.compress(CompressFormat.JPEG, 100, stream);
		byte[] byteArray = stream.toByteArray();
		return byteArray;
	}

	// //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// 바이트 배열을 비트맵으로 변환
	// //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public static Bitmap byteArrayToBitmap(byte[] byteArray) {
		Bitmap bitmap = BitmapFactory.decodeByteArray(byteArray, 0,
				byteArray.length);
		return bitmap;
	}

	// //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// URI 로 부터 실제 sdcard 의 경로를 구한다(이미지 갤러리 등에서 활용
	// //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public static String getRealImagePath(Activity activity, Uri uriPath) {
		String[] proj = { MediaStore.Images.Media.DATA };
		Cursor cursor = activity.getContentResolver().query(uriPath, proj,
				null, null, null);
		int index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
		cursor.moveToFirst();
		String path = cursor.getString(index);
		// path = path.substring(5);
		return path;
	}

	// //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// 이미지 파일로 부터 비트맵을 구한다.이미지 파일은 이미지의 폭이나 넓이중 하나가 지정한 크기 이하가 될때까지
	// 축소 샘플링 비율을 높여 나간다.
	// //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public static Bitmap decodeFile(File f) {
		try {
			BitmapFactory.Options o = new BitmapFactory.Options();
			o.inJustDecodeBounds = true;
			FileInputStream stream1 = new FileInputStream(f);
			BitmapFactory.decodeStream(stream1, null, o);
			stream1.close();

			final int REQUIRED_SIZE = 70; // 리사이즈 할 크기
			int width_tmp = o.outWidth, height_tmp = o.outHeight;
			int scale = 1;
			Log.v("원본 이미지 사이즈", "width--->" + o.outWidth + " : "
					+ "height --->" + o.outHeight);
			while (true) {
				if (width_tmp / 2 < REQUIRED_SIZE
						|| height_tmp / 2 < REQUIRED_SIZE)
					break;
				width_tmp /= 2;
				height_tmp /= 2;
				scale *= 2;
			}

			Log.v("리사이즈 이미지 사이즈", "width--->" + width_tmp + " : "
					+ "height --->" + height_tmp);

			BitmapFactory.Options o2 = new BitmapFactory.Options();
			o2.inSampleSize = scale;
			FileInputStream stream2 = new FileInputStream(f);
			Bitmap bitmap = BitmapFactory.decodeStream(stream2, null, o2);
			stream2.close();
			return bitmap;
		} catch (FileNotFoundException e) {
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	// //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// 위의 메서드와 동일하나, 조정할 크기를 지정해서 호출한다.
	// //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public static Bitmap decodeFile(File f, int samplingSize) {
		try {
			BitmapFactory.Options o = new BitmapFactory.Options();
			o.inJustDecodeBounds = true;
			FileInputStream stream1 = new FileInputStream(f);
			BitmapFactory.decodeStream(stream1, null, o);
			stream1.close();

			int width_tmp = o.outWidth, height_tmp = o.outHeight;
			int scale = 1;
			Log.v("원본 이미지 사이즈", "width--->" + o.outWidth + " : "
					+ "height --->" + o.outHeight);
			while (true) {
				if (width_tmp / 2 < samplingSize
						|| height_tmp / 2 < samplingSize)
					break;
				width_tmp /= 2;
				height_tmp /= 2;
				scale *= 2;
			}

			Log.v("리사이즈 이미지 사이즈", "width--->" + width_tmp + " : "
					+ "height --->" + height_tmp);

			BitmapFactory.Options o2 = new BitmapFactory.Options();
			o2.inSampleSize = scale;
			FileInputStream stream2 = new FileInputStream(f);
			Bitmap bitmap = BitmapFactory.decodeStream(stream2, null, o2);
			stream2.close();
			return bitmap;
		} catch (FileNotFoundException e) {
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	// //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// Bitmap 를 지정한 넗이와 폭으로 비율을 맞추어 축소한다.
	// //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public static Bitmap createResizedBitmap(Bitmap srcBit, int newWidth,
			int newHeight) {
		int width = srcBit.getWidth();
		int height = srcBit.getHeight();

		// calculate the scale - in this case = 0.4f
		float scaleWidth = ((float) newWidth) / width;
		float scaleHeight = ((float) newHeight) / height;

		Matrix matrix = new Matrix();
		matrix.postScale(scaleWidth, scaleHeight);
		Bitmap resizedBitmap = Bitmap.createBitmap(srcBit, 0, 0, width, height,
				matrix, true);

		width = resizedBitmap.getWidth();
		height = resizedBitmap.getHeight();

		Log.i("ImageResize",
				"Image Resize Result : "
						+ Boolean.toString((newHeight == height)
								&& (newWidth == width)));
		return resizedBitmap;
	}

	// //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// 인자값이 널, 공백등이 아니라면, 뷰에 인자를 표시한다.
	// 텍스트뷰와 에디트텍스트 위젯만 사용한다.
	// //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public static void setText(View view, String val) {
		if (val == null || val.trim().length() < 1)
			return;
		if (view instanceof TextView) {
			((TextView) view).setText(val);
		} else if (view instanceof EditText) {
			((EditText) view).setText(val);
		}
	}

	// //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// 토스트를 출력한다.
	// //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public static void showToastMessage(Context context, String message,
			int time) {
		Toast.makeText(context, message, time).show();
	}

	public static void showToastMessage(Context context, int resId, int time) {
		Toast.makeText(context, resId, time).show();
	}

	// //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// 폰의 전화번호를 가져온다.
	// //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public String getMyPhoneNumber(Context context) {
		TelephonyManager telephonyManager;
		telephonyManager = (TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE);
		String tel = telephonyManager.getLine1Number();
		if (tel == null)
			tel = "";
		return tel;
	}

	// //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// 동적으로 문자열이 가리키는 액티비티로 전환한다.
	// //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public static void goActivity(Context context, String activityClass) {
		Class<?> cls;
		Activity targetActivity;
		try {
			cls = Class.forName(activityClass);
			targetActivity = (Activity) cls.newInstance();
			Intent intent = new Intent(context, targetActivity.getClass());
			context.startActivity(intent);
		} catch (ClassNotFoundException e) {
			AlertDialog dialog = new AlertDialog.Builder(context).create();
			dialog.setTitle("안내");
			dialog.setMessage("죄송합니다.\n페이지를 찾을 수 없습니다.");
			dialog.setButton(1, "확인", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					return;
				}
			});
			dialog.show();
		} catch (InstantiationException e) {
		} catch (IllegalAccessException e) {
		}
	}

	// //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// 동적으로 문자열이 가리키는 액티비티 객체를 구한다.
	// //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public static Activity findActivity(Context context, String activityClass) {

		Class<?> cls;
		Activity activity = null;
		try {
			cls = Class.forName(activityClass);
			activity = (Activity) cls.newInstance();

		} catch (ClassNotFoundException e) {
			AlertDialog dialog = new AlertDialog.Builder(context).create();
			dialog.setTitle("안내");
			dialog.setMessage("죄송합니다.\n페이지를 찾을 수 없습니다.");
			dialog.setButton("확인", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					return;
				}
			});
			dialog.show();
		} catch (InstantiationException e) {

		} catch (IllegalAccessException e) {

		}
		return activity;
	}

	// //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// 지정된 전화번호로 문자 메시지를 송신함
	// //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public void sendSMS(String destinationAddress, String text) {
		if (!PhoneNumberUtils.isWellFormedSmsAddress(destinationAddress)) {
			return;
		}
		SmsManager sms = SmsManager.getDefault();
		sms.sendTextMessage(destinationAddress, null, text, null, null);
	}

	// //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// 지정한 주소로 웹 브라우저를 띄움
	// //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public void gotoWeb(Context context, String destinationAddress) {

		if (context == null)
			return;
		Uri uri = Uri.parse("http://" + destinationAddress);
		Intent intent = new Intent(Intent.ACTION_VIEW, uri);
		context.startActivity(intent);
	}

	// //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// 이메일 형식에 맞는지 체크한다.안드로이드 8 이상에서 사용할 수 있음
	// //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public static boolean isValidEmail(CharSequence target) {
		if (target == null) {
			return false;
		} else {
			return android.util.Patterns.EMAIL_ADDRESS.matcher(target)
					.matches();
		}
	}

	// //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// 앱의 버젼코드(versionCode) 를 가져온다.
	// //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public static int getAppVersion(Context context) {
		try {
			PackageInfo packageInfo = context.getPackageManager()
					.getPackageInfo(context.getPackageName(), 0);
			return packageInfo.versionCode;
		} catch (NameNotFoundException e) {
			throw new RuntimeException("Coult not get package name: " + e);
		}
	}

	public static void showProgress(Context context, ProgressBar pBar) {
		WindowManager winManager = (WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE);
		WindowManager.LayoutParams param = new WindowManager.LayoutParams();
		param.gravity = Gravity.CENTER | Gravity.CENTER;
		param.x = 0;
		param.y = -100;
		param.width = WindowManager.LayoutParams.WRAP_CONTENT;
		param.height = WindowManager.LayoutParams.WRAP_CONTENT;
		// 백키등의 이벤트를 허용하려면 아래의 주석을 푼다.
		param.flags = WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN /*
																		 * |
																		 * WindowManager
																		 * .
																		 * LayoutParams
																		 * .
																		 * FLAG_NOT_FOCUSABLE
																		 */;
		param.format = PixelFormat.TRANSLUCENT;
		winManager.addView(pBar, param);
	}

	public static void closeProgress(Context context, ProgressBar pBar) {
		WindowManager winManager = (WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE);
		winManager.removeView(pBar);
	}

	public static Bitmap getRoundedCornerBitmap(Bitmap bitmap, int pixels) {
		Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
				bitmap.getHeight(), Config.ARGB_8888);
		Canvas canvas = new Canvas(output);

		final int color = 0xff424242;
		final Paint paint = new Paint();
		final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
		final RectF rectF = new RectF(rect);
		final float roundPx = pixels;

		paint.setAntiAlias(true);
		canvas.drawARGB(0, 0, 0, 0);
		paint.setColor(color);
		canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

		paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
		canvas.drawBitmap(bitmap, rect, rect, paint);

		return output;
	}

	public static Bitmap getSimilarBitmap(File f, int width, int height) {
		try {
			BitmapFactory.Options o = new BitmapFactory.Options();
			o.inJustDecodeBounds = true;
			FileInputStream stream1 = new FileInputStream(f);
			BitmapFactory.decodeStream(stream1, null, o);
			stream1.close();

			float widthScale = o.outWidth / width;
			float heightScale = o.outHeight / height;
			float scale = widthScale > heightScale ? widthScale : heightScale;
			if (scale > 8) {
				o.inSampleSize = 8;
			} else if (scale > 6) {
				o.inSampleSize = 6;
			} else if (scale > 4) {
				o.inSampleSize = 4;
			} else if (scale > 2) {
				o.inSampleSize = 2;
			} else {
				o.inSampleSize = 1;
			}
			o.inJustDecodeBounds = false;
			FileInputStream stream2 = new FileInputStream(f);
			Bitmap bitmap = BitmapFactory.decodeStream(stream2, null, o);
			stream2.close();
			return bitmap;
		} catch (FileNotFoundException e) {
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;

	}
	
	//키보드 숨기기 
	public static void hideSoftKeyboard(Context con, EditText editText){
		InputMethodManager imm = (InputMethodManager)con.getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
	}
	
	//키보드 보여주기
	public static void showSoftkeyboard(Context con, EditText editText){
		InputMethodManager imm = (InputMethodManager)con.getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.showSoftInput(editText, InputMethodManager.SHOW_FORCED);
	}
	
	//와이파인지 3G 인지 상관없이 인터넷 가능여부를 반환한다.
	public static boolean isNetworkAvailable(Context context){
		ConnectivityManager manager = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo mobile = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
		NetworkInfo wifi = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
			         
			if (mobile.isConnected() || wifi.isConnected()){
			     return true;
			}else{
			      return false;
			}
	}
	
	//현재 연결된 네트워크 타입을 반환한다.
	public static String getActiveNetwork(Context context){
		ConnectivityManager manager = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = manager.getActiveNetworkInfo();
		/*"MOBILE" 과 "WIFI " 둘중의 하나를 리턴한다*/
		return networkInfo.getTypeName();
		
	}
	
	/*실시간 네트워크 상태 값을 수신하는 브로드캐스트
	이 리시버를 manefest 파일에 리시버로 등록하고 , intent-filter 로 "android.net.conn.CONNECTIVITY_CHANGE"를 등록한다*/
	public static class ConnReceiver extends BroadcastReceiver {
		 
	    @Override
	    public void onReceive(Context context, Intent intent) {
	        String action = intent.getAction();
	         
	        // 네트웍에 변경이 일어났을때 발생하는 부분
	        if (action.equals(ConnectivityManager.CONNECTIVITY_ACTION)) {
	            ConnectivityManager connectivityManager =
	                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
	            NetworkInfo activeNetInfo = connectivityManager.getActiveNetworkInfo();
	            NetworkInfo mobNetInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
	            Toast.makeText(context,"Active Network Type : " + activeNetInfo.getTypeName() , Toast.LENGTH_SHORT).show();
	            Toast.makeText(context,"Mobile Network Type : " + mobNetInfo.getTypeName() , Toast.LENGTH_SHORT).show();
	        }
	    }
	}
	
	public static String getStringFromResource(Context context,int resId){
		return context.getResources().getString(resId);
	}
	
	
	public static int convertDp2Pixel(Activity act, int dp){
		DisplayMetrics dm = act.getResources().getDisplayMetrics();
		int pixel = (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, dm);
		return pixel;
	}
	
	public static Point getScreenSize(Context context){
		DisplayMetrics metrics = new DisplayMetrics();
		WindowManager winManager = (WindowManager)context.getSystemService(Context.WINDOW_SERVICE);
		winManager.getDefaultDisplay().getMetrics(metrics);
		Point size = new Point(metrics.widthPixels, metrics.heightPixels);
		return size;
		
	}
	
	public static int getStatusBarHeight(Context context){
		Rect rectgle= new Rect(); 
		Window window= ((Activity) context).getWindow(); window.getDecorView().getWindowVisibleDisplayFrame(rectgle); 
		return rectgle.top; 	
	}
	
}
