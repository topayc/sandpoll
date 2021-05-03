package com.sandwhale.testepoll;

import com.sandwhale.testepoll.model.AppManager;
import com.sandwhale.testepoll.model.UserSession;
import com.sandwhale.testepoll.service.external.GCMController;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;

public class ActivitySplash extends Activity {

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    requestWindowFeature(Window.FEATURE_NO_TITLE);
	    setContentView(R.layout.activity_splash);
	    
	    initializeApp();
	    
        new Handler().postDelayed(new Runnable() {
			public void run() {
				Intent i = new Intent(ActivitySplash.this, ActivityMain.class);
				startActivity(i);
				finish();
			}
		}, 400);
	}
	
	private void initializeApp() {
		AppManager.getInstance().init(this);
		UserSession.getUserSession().load(this);
		getGCMRegisterId();
	}

	private void getGCMRegisterId() {
		GCMController gcmController = new GCMController(this);
		gcmController.registGCM();
	}
}
