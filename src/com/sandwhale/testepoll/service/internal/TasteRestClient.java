package com.sandwhale.testepoll.service.internal;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.conn.ssl.SSLSocketFactory;

import android.content.Context;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class TasteRestClient {

	private static AsyncHttpClient client = new AsyncHttpClient();

	public static void init() {
		try {
			KeyStore trustStore = KeyStore.getInstance(KeyStore
					.getDefaultType());
			trustStore.load(null, null);
			SSLSocketFactory sf = new MySSLSocketFactory(trustStore);
			sf.setHostnameVerifier(MySSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
			client.setSSLSocketFactory(sf);
		} catch (Exception e) {
		}
	}

	/**
	 * 컨텍스트 없는 get 요청 

	 */
	public static void get(String url, RequestParams param,
			AsyncHttpResponseHandler responseHandler) {

	}

	/**
	 * 컨텍스트 없는 post 요청 
	 */
	public static void post(String url, RequestParams param,
			AsyncHttpResponseHandler responseHandler) {

	}

	/**
	 * 컨텍스트를 포함한 get 요청 ,  컨텍스트를 키로 해당 요청을 취소할 수 있음 
	 */
	public static void get(Context context, String url, RequestParams params,
			AsyncHttpResponseHandler responseHandler) {

	}

	/**
	 * 컨텍스트를 포함한 post 요청, 컨텍스트를 키로 해당 요청을 취소할 수 있음 
	 * @param context
	 * @param url
	 * @param params
	 * @param responseHandler
	 */
	public static void post(Context context, String url, RequestParams params,
			AsyncHttpResponseHandler responseHandler) {

	}

	public static void autoLogin() {

	}

	public static void logout() {

	}

	public static void loginMannually() {

	}

	public static void updateChannelSetting() {

	}

	public static void changePassword() {

	}

	public static void cheeckUserDuplicate() {

	}

	public static void joinMemvber() {

	}

	public static void logDevice() {

	}

	public static void updateProfile() {

	}

	public static void getProfile() {

	}

	public static void updatePushSetting() {

	}

	public static void registerDevice() {

	}

	public static void getUserSetting() {

	}

	public static void cancelRequest(Context context,
			boolean mayInterruptIfRunning) {
		client.cancelRequests(context, mayInterruptIfRunning);
	}

	public static void cancelRequest(Context context) {
		cancelRequest(context, true);
	}

	/**
	 * 사설 인증서를 자동 인증하기 위한 소켓 팩토리 클래스 
	 */
	public static class MySSLSocketFactory extends SSLSocketFactory {
		SSLContext sslContext = SSLContext.getInstance("TLS");

		public MySSLSocketFactory(KeyStore truststore)
				throws NoSuchAlgorithmException, KeyManagementException,
				KeyStoreException, UnrecoverableKeyException {
			super(truststore);

			TrustManager tm = new X509TrustManager() {
				public void checkClientTrusted(X509Certificate[] chain,
						String authType) throws CertificateException {
				}

				public void checkServerTrusted(X509Certificate[] chain,
						String authType) throws CertificateException {
				}

				public X509Certificate[] getAcceptedIssuers() {
					return null;
				}
			};

			sslContext.init(null, new TrustManager[] { tm }, null);
		}

		@Override
		public Socket createSocket(Socket socket, String host, int port,
				boolean autoClose) throws IOException, UnknownHostException {
			return sslContext.getSocketFactory().createSocket(socket, host,
					port, autoClose);
		}

		@Override
		public Socket createSocket() throws IOException {
			return sslContext.getSocketFactory().createSocket();
		}
	}
}
