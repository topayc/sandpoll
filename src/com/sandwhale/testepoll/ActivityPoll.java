package com.sandwhale.testepoll;

import java.util.ArrayList;
import java.util.Random;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ClipDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.sandwhale.testepoll.common.Initializer;
import com.sandwhale.testepoll.conponent.CustomizeDialog;
import com.sandwhale.testepoll.customview.SimpleSwipeView;
import com.sandwhale.testepoll.fragment.FragmentPollList;
import com.sandwhale.testepoll.model.AppManager;
import com.sandwhale.testepoll.model.PollVoteResult;

public class ActivityPoll extends Activity implements Initializer {

	CustomizeDialog mCustomizeDialog = null;  
	Button mVoteBtn;
	Button mReportBtn;
	PollGalleryAdapter mPollGalleryAdapter;
	ImageView mCategoryImage;
	ImageView mainImage;
	
	LinearLayout mPollChoiceContainer;
	LinearLayout mPollResultContainer;
	
	int mCategory;
	
	ArrayList<PollVoteResult> mPollVoteResultList = new ArrayList<PollVoteResult>();
	
	boolean mIsVotedItem = false;
	LayoutInflater mInflater;
	
	@SuppressWarnings("deprecation")
	Gallery pollImageGallery;
	
	public static String KEY_IMAGE_NUM ="image_num";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_poll);
		mCategory = getIntent().getIntExtra(FragmentPollList.KEY_CATEGOTY,-1);
		if (mCategory == -1 ){
			/**
			 *카테고리는 -1 이 나올 수가 없으며, 만약 1일 경우 다른 잘못된 경로로 접근한 것이 된다.
			*/
		}
		
		initInstance();
		initView();
		initListener();
		initLayout();

	}

	@Override
	public void initView() {
		// TODO Auto-generated method stub
		mPollResultContainer = (LinearLayout)findViewById(R.id.poll_result_container);
		mPollChoiceContainer = (LinearLayout)findViewById(R.id.poll_choice_container);
		mVoteBtn = (Button) findViewById(R.id.poll_vote_btn);
		mReportBtn = (Button)findViewById(R.id.reportBtn);
		pollImageGallery = (Gallery)findViewById(R.id.poll_gallery);
		mCategoryImage = (ImageView)findViewById(R.id.category_image);
		mainImage = (ImageView)findViewById(R.id.poll_main_image);
	}

	@Override
	public void initLayout() {
		mCategoryImage.setImageResource(AppManager.getInstance().getCategories().get(mCategory).iconResId);
		if (mIsVotedItem){
			mPollChoiceContainer.setVisibility(View.GONE );
			mPollResultContainer.setVisibility(View.VISIBLE);
		}else {
			mPollChoiceContainer.setVisibility(View.VISIBLE );
			mPollResultContainer.setVisibility(View.GONE);
		}
	}

	@Override
	public void initListener() {
		findViewById(R.id.backBtn).setOnClickListener(listener);
		findViewById(R.id.reportBtn).setOnClickListener(listener);
		mVoteBtn.setOnClickListener(listener);
		mReportBtn.setOnClickListener(listener);
		mainImage.setOnClickListener(listener);
		
	}
	
	@Override
	public void initInstance() {
		mInflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	Dialog reportDlg ;
	View.OnClickListener listener = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			switch(v.getId())
			{
			case R.id.backBtn:
				finish();
				overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
				break;
			case R.id.reportBtn:
				mCustomizeDialog = new CustomizeDialog(ActivityPoll.this);  
                mCustomizeDialog.setTitle("부적절한 폴 신고");  
                mCustomizeDialog.setMessage("해당 폴을 신고하시겠습니까? ");  
                mCustomizeDialog.show();  
				break;
			case R.id.poll_vote_btn:
				showPollResult();
				Log.d("투표 버튼", "투표 버튼이 클릭됨");
				break;
				
			case R.id.poll_main_image:
				Intent i = new Intent(ActivityPoll.this, ActivityPollImageViewer.class);
				startActivity(i);
				overridePendingTransition(R.anim.fade_in_anim,0);
				break;
			}
			
		}
	
	};

	private void showPollResult() {
		Log.d("투표 진행", "뷰를 세팅하고 결과를 보여줍니다");
		convertViewContainer();
		setPollVoteResult();
		addVoteResultView();
		convertProgressBarConfig();
		animationVoteProgress();
		
	}
	
	Handler proHandler = new Handler(){
		int completecount =  0;
		@Override
		public void handleMessage(Message msg) {
			completecount++;
			if (completecount == 5){
				shakeAnimationContainer();
				completecount = 0;
			}
		}
	};
	
	private void convertViewContainer() {
		// TODO Auto-generated method stub
		mPollChoiceContainer.setVisibility(View.GONE );
		mPollResultContainer.setVisibility(View.VISIBLE);
	}
	
	private void setPollVoteResult(){
		Random r = new Random();
		for (int i = 0; i < 5 ; i++){
			PollVoteResult result = new PollVoteResult();
			result.setVotedCount(r.nextInt(100));
			result.setPollImage(R.drawable.replay_image1 + i);
			mPollVoteResultList.add(result);
		}
		mPollVoteResultList.get(r.nextInt(mPollVoteResultList.size()-1)).setJoined(true);
	}
	
	private void addVoteResultView() {
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
				LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT);
		params.setMargins(0, 10, 0, 10);
		
		for (int i = 0; i < mPollVoteResultList.size(); i++){
			final SimpleSwipeView view = new SimpleSwipeView(ActivityPoll.this);
			if (mPollVoteResultList.get(i).isJoined()){
				view.setSwipeEnable(true);
			}
			else {
				view.setSwipeEnable(false);
			}
			mPollResultContainer.addView(view,  params);
		}
	}

	private void shakeAnimationContainer() {
		for (int i = 0; i < mPollVoteResultList.size(); i++){
			final SimpleSwipeView view = (SimpleSwipeView) mPollResultContainer.getChildAt(i);
			if (mPollVoteResultList.get(i).isJoined()){
				new Handler().postDelayed(new Runnable(){
					public void run(){
						view.initialAnimation();
					}
				}, 200);
				break;
			}
		}
		
	}

	
	private void animationVoteProgress() {
		for (int i = 0; i <mPollVoteResultList.size(); i++){
			final PollVoteResult result  = mPollVoteResultList.get(i);
			final int pos = i;
			new Thread(){
				public void run(){
					SimpleSwipeView view = (SimpleSwipeView) mPollResultContainer.getChildAt(pos);
					final ProgressBar  pBar = (ProgressBar)  view.findViewById(R.id.vote_progress);
					final TextView voteMount = (TextView)view.findViewById(R.id.vote_mount);
					for (int j = 0; j < result.getVotedCount(); j++){
						final int value = j;
						proHandler.post(new Runnable(){
							public void run(){
								pBar.setProgress(value);
								voteMount.setText("" + value);
							}
						});
						try {
							Thread.sleep(25);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					proHandler.sendEmptyMessage(0);
					
				}
			}.start();
		}
	}

	private void convertProgressBarConfig() {
		for (int i = 0; i <mPollVoteResultList.size(); i++){
			
			Drawable[] drawables = new Drawable[3];
			PollVoteResult result = mPollVoteResultList.get(i);
			ColorDrawable cd = new ColorDrawable(0xff000000);
			
			BitmapDrawable bd = (BitmapDrawable)getResources().getDrawable(result.getPollImage());
			ClipDrawable cp = new ClipDrawable(bd,Gravity.RIGHT, ClipDrawable.VERTICAL);
			
			drawables[0] = cd;
			drawables[1] = new ColorDrawable(0x00000000);
			drawables[2] = new ClipDrawable(bd , Gravity.LEFT, ClipDrawable.HORIZONTAL);
			
			
			LayerDrawable ld = new LayerDrawable(drawables);
			SimpleSwipeView view = (SimpleSwipeView) mPollResultContainer.getChildAt(i);
			View frontView = view.getFrontView();
			ProgressBar pBar = (ProgressBar) frontView.findViewById(R.id.vote_progress);
			pBar.setProgressDrawable(ld);
		}
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		mPollGalleryAdapter = new PollGalleryAdapter(this);
		pollImageGallery.setAdapter(mPollGalleryAdapter);
		
	}
	
	 public static class PollGalleryAdapter extends BaseAdapter{

		Activity activity;
		int resId = R.layout.poll_gallery_item;
		int[] galleryResIds = new int[]{
				R.drawable.replay_image1,R.drawable.replay_image2,R.drawable.replay_image3,
				R.drawable.replay_image4,R.drawable.replay_image6};
		LayoutInflater inflater;
		
		public PollGalleryAdapter(Activity acitivity){
			this.activity = acitivity;
			inflater = (LayoutInflater)acitivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		}
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return 5;
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			final int pos = position;
			GalleryAdapterHolder holder;
			if (convertView == null){
				convertView = this.inflater.inflate(resId,parent, false);
				holder = new GalleryAdapterHolder();
				holder.galleryImage = (ImageView)convertView.findViewById(R.id.poll_vote_image);
				convertView.setTag(holder);
			}else {
				holder = (GalleryAdapterHolder)convertView.getTag();
			}
			holder.galleryImage.setImageResource(galleryResIds[position]);
			holder.galleryImage.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					Intent i = new Intent(activity, ActivityPollImageViewer.class);
					i.putExtra(KEY_IMAGE_NUM, pos);
					activity.startActivity(i);
					activity.overridePendingTransition(R.anim.fade_in_anim,0);
					
				}
			});
			return convertView;
		}
		
	}
	
	public static class GalleryAdapterHolder {
		ImageView galleryImage;
	}
	
	
	
}
