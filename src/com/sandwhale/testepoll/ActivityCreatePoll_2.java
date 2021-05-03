package com.sandwhale.testepoll;

import java.util.ArrayList;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.TranslateAnimation;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.sandwhale.testepoll.common.CommonUtils;
import com.sandwhale.testepoll.common.Initializer;
import com.sandwhale.testepoll.model.PollItem;

public class ActivityCreatePoll_2 extends Activity implements Initializer {

	Button mCancelBtn;
	Button mCompleteBtn;;
	TextView mTitleView;
	SeekBar mSeekBar;
	TextView testView;
	LinearLayout mContainer;
	
	View mHeaderView1;
	View mHeaderView2;
	View mFooterView;
	
	ListView mPollListContaner;
	
	TextView mOneDayView, mOneWeekView, mOneMonthView,mLimitlessView;
	LayoutInflater mInflater;
	
	ArrayList<PollItem> mPollItems ;
	PollCreate2Adapter adapter;
	


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_create_poll_2);

		initInstance();
		initView();
		initListener();
		initLayout();

	}

	@Override
	public void initInstance() {
		// TODO Auto-generated method stub
		mInflater = (LayoutInflater)getSystemService(LAYOUT_INFLATER_SERVICE);
		mPollItems = new ArrayList<PollItem>();
		for (int i = 0; i <2; i++){
			PollItem item = new PollItem();
			mPollItems.add(item);
		}

	}

	@Override
	public void initView() {
		// TODO Auto-generated method stub
		mTitleView = (TextView) findViewById(R.id.mainTitle);
		mCancelBtn = (Button) findViewById(R.id.backBtn);
		mCompleteBtn = (Button) findViewById(R.id.reportBtn);
	
		mContainer = (LinearLayout) findViewById(R.id.container);
		mPollListContaner = (ListView)findViewById(R.id.poll_list_container);
		
		mHeaderView1 = mInflater.inflate(R.layout.activity_create_poll_2_list_header, null);
		mHeaderView2 = mInflater.inflate(R.layout.activity_create_poll_2_list_header2, null);
		mFooterView = mInflater.inflate(R.layout.activity_create_poll_2_list_footer,null);
		
		mOneDayView = (TextView)mHeaderView1.findViewById(R.id.one_day);
		mOneMonthView = (TextView)mHeaderView1.findViewById(R.id.one_month);
		mOneWeekView = (TextView)mHeaderView1.findViewById(R.id.one_week);
		mLimitlessView = (TextView)mHeaderView1.findViewById(R.id.limitless_day);
		mSeekBar = (SeekBar)mHeaderView1.findViewById(R.id.seekbar);
	
	}

	@Override
	public void initListener() {
		// TODO Auto-generated method stub
		mCancelBtn.setOnClickListener(navListener);
		mCompleteBtn.setOnClickListener(navListener);
		((Button)mFooterView.findViewById(R.id.add_item)).setOnClickListener(itemAddListener);
		mSeekBar.setOnSeekBarChangeListener(seekBarListener);
	
	}

	@Override
	public void initLayout() {
		// TODO Auto-generated method stub
		mTitleView.setText(R.string.create_new_poll);
		mPollListContaner.addHeaderView(mHeaderView1);
		mPollListContaner.addHeaderView(mHeaderView2);
		mPollListContaner.addFooterView(mFooterView);

	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		adapter = new PollCreate2Adapter(this);
		mPollListContaner.setAdapter(adapter);
		
	}

	/**
	 * 뷰의 크기 위치등을 알아낼 때는 이 메서드를 오버라이딩 한다.
	 */
	public void onWindowFocusChanged(boolean hasFocus) {

	}

	View.OnClickListener itemAddListener = new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			PollItem item = new PollItem();
			mPollItems.add(item);
			adapter.setAddItemPos(mPollItems.size()-1);
			adapter.notifyDataSetChanged();
			Log.d("추가할 인덱스 - 리스너", " -->   " + mPollItems.size());	
		}
	};
	
	/**
	 * SeekBar 리스너 
	 */
	SeekBar.OnSeekBarChangeListener seekBarListener = new SeekBar.OnSeekBarChangeListener() {
		
		@Override
		public void onStopTrackingTouch(SeekBar seekBar) {	}
		
		@Override
		public void onStartTrackingTouch(SeekBar seekBar) {	}
		
		@Override
		public void onProgressChanged(SeekBar seekBar, int progress,
				boolean fromUser) {
			mOneDayView.setText(R.string.one_day);
			mOneDayView.setTextColor(Color.parseColor("#aaaaaa"));
			mOneWeekView.setText(R.string.one_week);
			mOneWeekView.setTextColor(Color.parseColor("#aaaaaa"));
			mOneMonthView.setText(R.string.one_month);
			mOneMonthView.setTextColor(Color.parseColor("#aaaaaa"));
			mLimitlessView.setText(R.string.limitless);
			mLimitlessView.setTextColor(Color.parseColor("#aaaaaa"));
			
			switch(progress){
			case 0:
				mOneDayView.setText(Html.fromHtml(toBold(getResources().getString(R.string.one_day))));
				mOneDayView.setTextColor(Color.parseColor("#000000"));
				break;
			case 1:
				mOneWeekView.setText(Html.fromHtml(toBold(getResources().getString(R.string.one_week))));
				mOneWeekView.setTextColor(Color.parseColor("#000000"));
				break;
			case 2:
				mOneMonthView.setText(Html.fromHtml(toBold(getResources().getString(R.string.one_month))));
				mOneMonthView.setTextColor(Color.parseColor("#000000"));
				break;
			case 3:
				mLimitlessView.setText(Html.fromHtml(toBold(getResources().getString(R.string.limitless))));
				mLimitlessView.setTextColor(Color.parseColor("#000000"));
				break;
			}
		}
	};
	
	public String toBold(String data){
		return "<b>" + data + "</b>";
	}
	
	/**
	 * 네비게이션 버튼 리스너 
	 */
	View.OnClickListener navListener = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			switch (v.getId()) {

			/**
			 * 뒤로 가기 버튼
			 */
			case R.id.backBtn:
				finish();
				overridePendingTransition(R.anim.slide_in_right,
						R.anim.slide_out_right);
				break;

			/**
			 * 폴 작성 완료 버튼
			 */
			case R.id.reportBtn:
				break;

			}

		}
	};

	 class PollCreate2Adapter extends BaseAdapter {
		
		private Activity activity;
		private int addItemPos = -1;
		public PollCreate2Adapter(Activity activity){
			this.activity = activity;
		}
		
		
		public int getAddItemPos() {return addItemPos;}
		public void setAddItemPos(int addItemPos) {this.addItemPos = addItemPos;}

		@Override
		public int getCount() {
			return mPollItems.size();
		}

		@Override
		public Object getItem(int position) {
			return mPollItems.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			
			final int pos = position;
			if (convertView == null){
				convertView = mInflater.inflate(R.layout.activity_create_poll_2_list_item_row, parent, false);
			}
			
			final LinearLayout pollImageContainer = (LinearLayout)convertView.findViewById(R.id.poll_image_container);
			EditText detailEdit = (EditText)convertView.findViewById(R.id.poll_detail_edit);
			PollItem item = mPollItems.get(pos);
			
			if (!CommonUtils.isNullOrEmpty(item.pollName)){
				detailEdit.setText(item.pollName);
			}else{
				detailEdit.setText("");
			}
			
			ImageButton removeBtn = (ImageButton)convertView.findViewById(R.id.remove_item_btn);
			
			if (mPollItems.size() <3 ){
				removeBtn.setVisibility(View.INVISIBLE);
				removeBtn.setEnabled(false);
			}else {
				removeBtn.setVisibility(View.VISIBLE);
				removeBtn.setEnabled(true);
				removeBtn.setOnClickListener(new View.OnClickListener() {
					
					@Override
					public void onClick(View v) {
						dismissAnimation(pollImageContainer, pos);
						Log.d("제거할 인덱스", " -->   " + pos);	
					}
				});
			}
			
			if (addItemPos !=1 && addItemPos == pos){
				Log.d("추가할 인덱스 - getView", " -->   " + pos);	
				enterViewAnimation(pollImageContainer);
			}
			
			
		
			return convertView;
		}
		
		
		private void enterViewAnimation(View v){
			TranslateAnimation enterAnim = new TranslateAnimation(
					Animation.RELATIVE_TO_PARENT, 1f,
					Animation.RELATIVE_TO_PARENT,0f,
					Animation.RELATIVE_TO_PARENT,0f,
					Animation.RELATIVE_TO_PARENT,0f);
			enterAnim.setDuration(300);
			enterAnim.setInterpolator(new AccelerateInterpolator());
			enterAnim.setFillAfter(true);
			
			AnimationListener animationListener = new AnimationListener() {
				
				@Override
				public void onAnimationStart(Animation animation) {
				}
				
				@Override
				public void onAnimationRepeat(Animation animation) {}
				
				@Override
				public void onAnimationEnd(Animation animation) {
					setAddItemPos(-1);
					
				}
			};
			
			enterAnim.setAnimationListener(animationListener);
			v.startAnimation(enterAnim);
		}
		
		private void dismissAnimation(final LinearLayout pollImageContainer, final int pos) {
			TranslateAnimation dissmissAnim = new TranslateAnimation(
														Animation.RELATIVE_TO_PARENT, 0f,
														Animation.RELATIVE_TO_PARENT,1f,
														Animation.RELATIVE_TO_PARENT,0f,
														Animation.RELATIVE_TO_PARENT,0f);
			dissmissAnim.setDuration(300);
			dissmissAnim.setInterpolator(new AccelerateInterpolator());
			dissmissAnim.setFillAfter(false);
			
			AnimationListener animationListener = new AnimationListener() {
				
				@Override
				public void onAnimationStart(Animation animation) {
				}
				
				@Override
				public void onAnimationRepeat(Animation animation) {}
				
				@Override
				public void onAnimationEnd(Animation animation) {
					mPollItems.remove(pos);
					notifyDataSetChanged();
					
				}
			};
			
			dissmissAnim.setAnimationListener(animationListener);
			pollImageContainer.startAnimation(dissmissAnim);
		}
		
		
		
	}
}

















