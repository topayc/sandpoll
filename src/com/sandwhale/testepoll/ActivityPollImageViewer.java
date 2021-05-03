package com.sandwhale.testepoll;

import org.taptwo.android.widget.CircleFlowIndicator;
import org.taptwo.android.widget.ViewFlow;


import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

public class ActivityPollImageViewer extends Activity {

	private ViewFlow viewFlow;
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	
	    /*Window win = getWindow();
	    win.requestFeature(Window.FEATURE_NO_TITLE);
	    win.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);*/
	    setContentView(R.layout.activity_poll_image_viewer);
	    
	    int imageNum = getIntent().getIntExtra(ActivityPoll.KEY_IMAGE_NUM,0);
		viewFlow = (ViewFlow)findViewById(R.id.viewflow);
		viewFlow.setAdapter(new ImageAdapter(this), imageNum);
		CircleFlowIndicator indic = (CircleFlowIndicator) findViewById(R.id.viewflowindic);
		viewFlow.setFlowIndicator(indic);
	}

	@Override
	public void onBackPressed() {
		finish();
		overridePendingTransition(0, R.anim.fade_out_anim);
		super.onBackPressed();
	}

	
}
