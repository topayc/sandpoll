package com.sandwhale.testepoll.fragment;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.view.animation.AnticipateOvershootInterpolator;
import android.view.animation.BounceInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.TextView;

import com.sandwhale.testepoll.ActivityCreatePoll_1;
import com.sandwhale.testepoll.ActivityPoll;
import com.sandwhale.testepoll.R;
import com.sandwhale.testepoll.model.AppManager;
import com.sandwhale.testepoll.model.Poll;

public class FragmentPollList extends Fragment {
	public static final String KEY_CATEGOTY = "category";
	public static int testCode = 0;
	public ArrayList<Poll> mPollList;
	public PollListAdapter mPollListAdapter;
	public ListView mPollListView;

	public View mPollAddContainer;
	public View mTopContainer;

	public int mPollAddContainerHeight;

	public int mPollListViewHeight;
	public ImageView createPollBtn;

	public static FragmentPollList newInstance(int category) {
		FragmentPollList fragmentContent = new FragmentPollList();
		Bundle bundle = new Bundle();
		bundle.putInt(KEY_CATEGOTY, category);
		fragmentContent.setArguments(bundle);
		return fragmentContent;
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mPollList = new ArrayList<Poll>();

		for (int i = 0; i < 20; i++) {
			Poll poll = new Poll();
			poll.setPollName("좋아하는 영화는 무엇인가요? 아무것이나 원하시는 것 투표를 해주시면 땡큐!!");
			poll.setPeriod("무기한");
			poll.setVoteCount("20");
			mPollList.add(poll);
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		View view = inflater.inflate(R.layout.fragment_content_poll, null);
		mPollListView = (ListView) view.findViewById(R.id.poll_list);
		mPollAddContainer = view.findViewById(R.id.poll_add_container);
		mTopContainer = view.findViewById(R.id.top_container);
		createPollBtn = (ImageView) view.findViewById(R.id.poll_add_btn);
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		mPollListView.setOnItemClickListener(pollListListener);
		createPollBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent(getActivity(), ActivityCreatePoll_1.class);
				startActivity(i);
				getActivity().overridePendingTransition(R.anim.slide_in_bottom,
						R.anim.not_anim);

			}
		});
		
		 setPollAdapter();
		
	}

	OnTouchListener touchListener = new OnTouchListener() {

		private int startX, startY;
		public boolean pollAddContainerOpened;
		private boolean isListenerFirst = true;

		@Override
		public boolean onTouch(View v, MotionEvent event) {
			if (isListenerFirst){
				isListenerFirst = false;
				pollAddContainerOpened = false;
			}
			
			int action = event.getAction();

			switch (action) {
			case MotionEvent.ACTION_DOWN:
				startX = (int) event.getX();
				startY = (int) event.getY();
				return false;

			case MotionEvent.ACTION_MOVE:
				int deltaY1 = (int) event.getY() - startY;
				int childTop1 = mPollListView.getChildAt(0).getTop();
				if (mPollListView.getFirstVisiblePosition() == 0) {
					if (childTop1 == 0){
						if (pollAddContainerOpened && deltaY1 < 0) {
							return true;
						}
					}else {
						return false;
					}
				} else {
					return false;
				}
				break;

			case MotionEvent.ACTION_UP:
				int deltaY2 = (int) event.getY() - startY;
				
				int childTop2 = mPollListView.getChildAt(0).getTop();
			
				if (mPollListView.getFirstVisiblePosition() == 0) {
					if (childTop2 == 0) {
						if (deltaY2 > 10) {
							if (!pollAddContainerOpened) {
								pollAddContainerOpened = true;
								//mPollListView.clearAnimation();
								openPollAddContainer();						
								return true;
							} else {
								return false;
							}
						} else if (deltaY2 < -10) {
							if (pollAddContainerOpened) {
								pollAddContainerOpened = false;
								//mPollListView.clearAnimation();
								closePollAddContainer();							
								return true;
							} else {
								return false;
							}
						}
					}
				}
				return false;
			}
			return false;
		}

		private void closePollAddContainer() {
			mPollAddContainer.setVisibility(View.GONE);
		/*	ScaleAnimation closeAnim = new ScaleAnimation(1.0f, 0.0f, 1.0f ,0.0f, 
					Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
			closeAnim.setDuration(300);
			closeAnim.setInterpolator(new DecelerateInterpolator());
			AnimationListener closeAnimListener = new AnimationListener() {
				
				@Override
				public void onAnimationStart(Animation animation) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void onAnimationRepeat(Animation animation) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void onAnimationEnd(Animation animation) {
					mPollAddContainer.setVisibility(View.GONE);
					
				}
			};
			closeAnim.setAnimationListener(closeAnimListener);
			createPollBtn.startAnimation(closeAnim);*/
			
			/*int pollAddContainerHeight =mPollAddContainer.getHeight();
			int listBottom = mPollListView.getBottom();
			
			TranslateAnimation closeAnim = new TranslateAnimation(0,0,pollAddContainerHeight, 0);
			closeAnim.setDuration(100);
			closeAnim.setFillAfter(true);
			mPollListView.startAnimation(closeAnim);
			
			mPollListView.layout(mPollListView.getLeft(), mPollListView.getTop()-pollAddContainerHeight,
					mPollListView.getRight(), mPollListView.getBottom() - pollAddContainerHeight);*/
			
		}

		private void openPollAddContainer() {
			mPollAddContainer.setVisibility(View.VISIBLE);
			ScaleAnimation openAnim = new ScaleAnimation(0.0f, 1.2f, 0.0f ,1.2f, 
					Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
			openAnim.setDuration(300);
			openAnim.setInterpolator(new AnticipateOvershootInterpolator(2.0f,1.5f));
			createPollBtn.startAnimation(openAnim);
			
			
	/*		int pollAddContainerHeight = mPollAddContainer.getHeight();
			int listBottom = mPollListView.getBottom();
			
			TranslateAnimation openAnim = new TranslateAnimation(0,0,0, pollAddContainerHeight);
			openAnim.setDuration(100);
			openAnim.setFillAfter(true);
			mPollListView.startAnimation(openAnim);
			
			mPollListView.layout(mPollListView.getLeft(), mPollListView.getTop() + pollAddContainerHeight,
					mPollListView.getRight(), mPollListView.getBottom() + pollAddContainerHeight);*/
			
		}

	};

	AdapterView.OnItemClickListener pollListListener = new AdapterView.OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {

			Intent i = new Intent(getActivity(), ActivityPoll.class);
			i.putExtra(KEY_CATEGOTY, getArguments().getInt(KEY_CATEGOTY));
			startActivity(i);
			getActivity().overridePendingTransition(R.anim.slide_in_left,
					R.anim.slide_out_left);
		}
	};

	@Override
	public void onResume() {
		super.onResume();
		new Handler().postDelayed(new Runnable(){
			public void run(){
				mPollListView.setOnTouchListener(touchListener);
			}
		}, 1000);
		
	}

	public void setPollAdapter() {
		mPollListAdapter = new PollListAdapter(getActivity(), mPollList,
				getArguments().getInt(KEY_CATEGOTY));
		mPollListView.setAdapter(mPollListAdapter);
	}

	@Override
	public void onPause() {
		super.onPause();
	}

	
	 class PollListAdapter extends BaseAdapter {

		public Activity act;
		public int resId = R.layout.main_content_poll_list_row;
		public ArrayList<Poll> pollList;
		public LayoutInflater inflater;
		public int category;
		public int rowCacheNo = -1;

		public PollListAdapter(Activity act, ArrayList<Poll> pollList,
				int category) {
			this.act = act;
			this.category = category;
			this.pollList = pollList;
			this.inflater = (LayoutInflater) act
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return pollList.size();
		}

		@Override
		public Object getItem(int position) {
			return pollList.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {

			final PollListHolder holder;
			final int pos = position;
			if (convertView == null) {
				convertView = this.inflater.inflate(resId, parent, false);
				holder = new PollListHolder();
				holder.pollTitle = (TextView) convertView.findViewById(R.id.poll_title);
				holder.pollImage = (ImageView) convertView.findViewById(R.id.poll_image);
				holder.voteCount = (TextView) convertView.findViewById(R.id.poll_vote_count);
				holder.period = (TextView) convertView.findViewById(R.id.poll_period);
				holder.pollCategoryImage = (ImageView) convertView.findViewById(R.id.poll_category_image);
				holder.voteImage = (ImageView) convertView.findViewById(R.id.poll_vote_image);
				holder.periodImage = (ImageView) convertView.findViewById(R.id.poll_period_image);
				holder.overlay = (ImageView) convertView.findViewById(R.id.poll_image_overlay);
				convertView.setTag(holder);
			} else {
				holder = (PollListHolder) convertView.getTag();
			}

			hideView(holder);
			
			if ( pos  <=  rowCacheNo ){
				showView(holder);
				setContent(holder, pos);
				addOverlay(holder);
				
			}else{
				Log.d("로우 캐시", " 캐시됨 ");
				rowCacheNo++;
				final Animation fadeIn = AnimationUtils.loadAnimation(act,R.anim.fade_in_anim);
				AnimationListener animListener = new AnimationListener() {

					@Override
					public void onAnimationStart(Animation animation) {}
					
					@Override
					public void onAnimationRepeat(Animation animation) {}

					@Override
					public void onAnimationEnd(Animation animation) {
						showView(holder);
						addOverLayAnimation(holder);
						setContent(holder, pos);
					}
				};
				
				fadeIn.setAnimationListener(animListener);	
				holder.pollImage.startAnimation(fadeIn);	
			}
			return convertView;
		}

		public void hideView(PollListHolder holder) {
			holder.pollTitle.setVisibility(View.INVISIBLE);
			holder.voteCount.setVisibility(View.INVISIBLE);
			holder.period.setVisibility(View.INVISIBLE);
			holder.periodImage.setVisibility(View.INVISIBLE);
			holder.pollCategoryImage.setVisibility(View.INVISIBLE);
			holder.voteImage.setVisibility(View.INVISIBLE);
			holder.overlay.setVisibility(View.INVISIBLE);
		}

		public void showView(PollListHolder holder) {
			holder.pollTitle.setVisibility(View.VISIBLE);
			holder.voteCount.setVisibility(View.VISIBLE);
			holder.period.setVisibility(View.VISIBLE);
			holder.periodImage.setVisibility(View.VISIBLE);
			holder.pollCategoryImage.setVisibility(View.VISIBLE);
			holder.voteImage.setVisibility(View.VISIBLE);
			holder.overlay.setVisibility(View.VISIBLE);
		}

		public void addOverlay(PollListHolder holder){
			
		}
		
		public void addOverLayAnimation(PollListHolder holder){
			Animation fadeIn = AnimationUtils.loadAnimation(act,R.anim.fade_in_anim);
			holder.overlay.startAnimation(fadeIn);
		}
		
		public void setContent(PollListHolder holder, int position) {
			holder.pollCategoryImage.setImageResource(AppManager.getInstance()
					.getCategories().get(category).iconResId);
			holder.pollTitle.setText(pollList.get(position).getPollName());
			holder.period.setText(pollList.get(position).getPeriod());
			holder.voteCount.setText(pollList.get(position).getVoteCount());
		}
	}

	public static class PollListHolder {
		public ImageView pollImage;
		public TextView pollTitle;
		public TextView period;
		public TextView voteCount;
		public ImageView pollCategoryImage;
		public ImageView voteImage;
		public ImageView periodImage;
		public ImageView overlay;
	}

}
