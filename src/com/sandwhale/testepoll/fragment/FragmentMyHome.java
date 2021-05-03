package com.sandwhale.testepoll.fragment;

import java.util.ArrayList;
import java.util.Random;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ClipDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

import com.sandwhale.testepoll.ActivityPoll.PollGalleryAdapter;
import com.sandwhale.testepoll.R;
import com.sandwhale.testepoll.customview.SimpleSwipeView;
import com.sandwhale.testepoll.model.AppManager;
import com.sandwhale.testepoll.model.BasePoll;
import com.sandwhale.testepoll.model.ImagePoll;
import com.sandwhale.testepoll.model.PollVoteResult;
import com.sandwhale.testepoll.model.ImagePoll.SubImagePollResult;
import com.sandwhale.testepoll.model.TextPoll;
import com.sandwhale.testepoll.model.TextPoll.SubTextPollResult;

public class FragmentMyHome extends Fragment {
	
	public ArrayList<BasePoll> pollList;
	public ArrayList<Follower> followerList;
	public LayoutInflater inflater;
	
	public ListView myHomeList;
	public MyHomeAdapter homeAdpater;
	
	public static FragmentMyHome newInstance(){
		FragmentMyHome myHome = new FragmentMyHome();
		return myHome;
	}
	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		inflater = (LayoutInflater)getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}
	
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		View myHomeLay = inflater.inflate(R.layout.fragment_myhome, null);
		myHomeList = (ListView)myHomeLay.findViewById(R.id.myhome_list);
		return myHomeLay;
	
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		makeDummyFolloerList();
		makeDummyPollList();
		setListener();
		addHeaderView();
		setMyHomeAdapter();
	}
	

	private void makeDummyPollList() {
		pollList = new ArrayList<BasePoll>();
		Random r = new Random();
	
		for (int i = 0 ; i < 30; i++){
			int pollType = r.nextInt(100)% 2;
			boolean isVoted = r.nextInt(50) % 2 == 0? true: false;
			switch(pollType){
			case 0:
				ImagePoll imagePoll = new ImagePoll();
				imagePoll.setPollType(pollType);
				imagePoll.setCategory(r.nextInt(10));
				imagePoll.setVoted(isVoted);
				imagePoll.setJoinedSubPoll(r.nextInt(5));
				
				
				ArrayList<SubImagePollResult> subList = new ArrayList<SubImagePollResult>();
				
				for (int k = 0; k < 5; k++){
					SubImagePollResult sub = new SubImagePollResult();
					sub.setImagePath(R.drawable.replay_image1 + k);
					sub.setSubVoteCount(r.nextInt(100));
					subList.add(sub);
				}
				
				imagePoll.setSubPolls(subList);
				pollList.add(imagePoll);
				break;
				
			case 1:
				TextPoll textPoll = new TextPoll();
				textPoll.setPollType(pollType);
				textPoll.setVoted(isVoted);
				textPoll.setCategory(r.nextInt(10));
				
				ArrayList<SubTextPollResult> subList2 = new ArrayList<SubTextPollResult>();
				for (int j = 0; j < 5; j++){
					SubTextPollResult sub = new SubTextPollResult();
					sub.setSubPollText(j + 1 + ". " + "마음에 드시는 것은 어떤 것인가요?");
					sub.setSubVoteCount(r.nextInt(100));
					subList2.add(sub);
				}
				textPoll.setSubPolls(subList2);
				pollList.add(textPoll);
				break;
			}
		}
	}

	private void makeDummyFolloerList() {
		followerList = new ArrayList<Follower>();
		for (int i = 0; i< 10; i++){
			Follower f = new Follower();
			f.setName("Follower" + 1);
			f.setImagePath(null);
			followerList.add(f);
		}
	}

	@Override
	public void onResume() {
		super.onResume();
	}
	
	private void setMyHomeAdapter() {
		homeAdpater = new MyHomeAdapter(getActivity());
		myHomeList.setAdapter(homeAdpater);
		
	}

	private void addHeaderView() {
	 View followerView = inflater.inflate(R.layout.fragment_myhome_header_follower,null);
	 myHomeList.addHeaderView(followerView);
	 
	}

	@Override
	public void onPause() {
		super.onPause();
	}
	
	public void setListener(){
		
	}
	
	class MyHomeAdapter extends BaseAdapter{

		Activity activity;
		ProHandler proHandler;
		int resId = R.layout.poll_gallery_item;
		int[] galleryResIds = new int[]{
				R.drawable.replay_image1,R.drawable.replay_image2,R.drawable.replay_image3,
				R.drawable.replay_image4,R.drawable.replay_image6};
		
		public MyHomeAdapter(Activity activity){
			this.activity = activity;
			proHandler = new ProHandler();
		}
		@Override
		public int getCount() {
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
			if(convertView == null){
				switch(pollList.get(position).getPollType()){
				case BasePoll.POLL_TYPE_IMAGE:
					convertView = inflater.inflate(R.layout.fragment_myhome_row_imagepoll, parent, false);
					break;
				case BasePoll.POLL_TYPE_TEXT:
					convertView = inflater.inflate(R.layout.fragment_myhome_row_textpoll, parent, false);
					break;
				}
			}
			
			switch(pollList.get(position).getPollType()){
			
			case BasePoll.POLL_TYPE_IMAGE:
				final ImagePoll imagePoll = (ImagePoll)pollList.get(position);
				final LinearLayout imagePollResultContainer = 
						(LinearLayout)convertView.findViewById(R.id.fragment_image_poll_result_container);
				final LinearLayout imagePollChoiceContainer = 
						(LinearLayout)convertView.findViewById(R.id.fragment_image_poll_choice_container);
				
				Button voteBtn = (Button) convertView.findViewById(R.id.fragment_myhome_poll_vote_btn);
				//Button reportBtn = (Button)convertView.findViewById(R.id.reportBtn);
				Gallery pollImageGallery = (Gallery)convertView.findViewById(R.id.poll_gallery);
				ImageView categoryImage = (ImageView)convertView.findViewById(R.id.category_image);
				ImageView mainImage = (ImageView)convertView.findViewById(R.id.poll_main_image);
				
				voteBtn.setOnClickListener(new View.OnClickListener() {
					
					@Override
					public void onClick(View v) {
						imagePollChoiceContainer.setVisibility(View.GONE );
						imagePollResultContainer.setVisibility(View.VISIBLE);
						imagePollResultContainer.removeAllViews();
				
						imagePoll.setJoinedSubPoll(new Random().nextInt(5));
						imagePoll.setVoted(true);
						
						for (int i = 0; i <imagePoll.getSubPolls().size(); i++){
							SimpleSwipeView subPollView = new SimpleSwipeView(activity);
							
							LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
									LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT);
							params.setMargins(0, 10, 0, 10);
						
							imagePollResultContainer.addView(subPollView,  params);
							convertProgressBarConfig(subPollView, imagePoll.getSubPolls().get(i));
							if (imagePoll.joinedSubPoll == i){
								subPollView.setSwipeEnable(true);
								proHandler.setAnimView(subPollView);
								
							}
							else {
								subPollView.setSwipeEnable(false);
							}
								
						}
						
						for (int i = 0; i <imagePoll.getSubPolls().size(); i++){
							final SubImagePollResult result  = imagePoll.getSubPolls().get(i);
							final int pos = i;
							new Thread(){
								public void run(){
									SimpleSwipeView view = (SimpleSwipeView) imagePollResultContainer.getChildAt(pos);
									final ProgressBar  pBar = (ProgressBar)  view.findViewById(R.id.vote_progress);
									final TextView voteMount = (TextView)view.findViewById(R.id.vote_mount);
									for (int j = 0; j < result.getSubVoteCount(); j++){
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
				});
				
				categoryImage.setImageResource(AppManager.getInstance().getCategories().get(imagePoll.getCategory()).iconResId);
				
				if (imagePoll.isVoted){
					imagePollChoiceContainer.setVisibility(View.GONE );
					imagePollResultContainer.setVisibility(View.VISIBLE);
					imagePollResultContainer.removeAllViews();
					for (int i = 0; i <imagePoll.getSubPolls().size(); i++){
						SimpleSwipeView subPollView = new SimpleSwipeView(activity);
						
						LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
								LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT);
						params.setMargins(0, 10, 0, 10);
						
							if (imagePoll.joinedSubPoll == i){
								subPollView.setSwipeEnable(true);
								proHandler.shakeAnimtionContainer(subPollView);
							}
							else {
								subPollView.setSwipeEnable(false);
							}
							imagePollResultContainer.addView(subPollView,  params);
							convertProgressBarConfig(subPollView, imagePoll.getSubPolls().get(i));
							
						}
				}else {
					imagePollChoiceContainer.setVisibility(View.VISIBLE );
					imagePollResultContainer.setVisibility(View.GONE);
					PollGalleryAdapter mPollGalleryAdapter = new PollGalleryAdapter(activity);
					pollImageGallery.setAdapter(mPollGalleryAdapter);
				}
				
				break;
				
			case BasePoll.POLL_TYPE_TEXT:
				TextPoll textPoll = (TextPoll)pollList.get(position);
				ImageView textPollcategoryImage = (ImageView)convertView.findViewById(R.id.text_poll_category_image);
				ImageView textPollmainImage = (ImageView)convertView.findViewById(R.id.text_poll_main_image);
				textPollcategoryImage.setImageResource(AppManager.getInstance().getCategories().get(textPoll.getCategory()).iconResId);
				LinearLayout textPollResultContainer = (LinearLayout) convertView.findViewById(R.id.text_poll_result_container);
				LinearLayout textPollChoiceContainer = (LinearLayout) convertView.findViewById(R.id.text_poll_choice_container);
				
				if (textPoll.isVoted){
					textPollChoiceContainer.setVisibility(View.GONE );
					textPollResultContainer.setVisibility(View.VISIBLE);
					textPollResultContainer.removeAllViews();
					
					for (int i = 0; i < textPoll.getSubPolls().size(); i++){
						SubTextPollResult result = textPoll.getSubPolls().get(i);
						View subTextPollItemView = inflater.inflate(R.layout.text_poll_result, null);
						TextView subPollText = (TextView)subTextPollItemView .findViewById(R.id.text_poll_title);
						TextView subPollTextVoteCount =(TextView)subTextPollItemView.findViewById(R.id.text_poll_vote_count);
						RatingBar ratingBar = (RatingBar)subTextPollItemView.findViewById(R.id.ratingbar);
						
						subPollText.setText(result.getSubPollText());
						subPollTextVoteCount.setText(String.valueOf(result.getSubVoteCount()));
						int voteCount = 15 * result.getSubVoteCount() / 100;
						ratingBar.setRating(voteCount);
						textPollResultContainer.addView(subTextPollItemView);
					}
					
				}else {
					textPollChoiceContainer.setVisibility(View.VISIBLE );
					textPollResultContainer.setVisibility(View.GONE);
					textPollChoiceContainer.removeAllViews();
					
					for(int i = 0;i < textPoll.getSubPolls().size(); i++){
						View subImagePollItemView = inflater.inflate(R.layout.item_textpoll, null);
						TextView subPollText = (TextView)subImagePollItemView.findViewById(R.id.text_poll_item);
						subPollText.setText(textPoll.getSubPolls().get(i).getSubPollText());
						textPollChoiceContainer.addView(subImagePollItemView);
					}
				}
				break;
			}
			return convertView;
		}

		@Override
		public int getItemViewType(int position) {
			return pollList.get(position).getPollType();
		}

		@Override
		public int getViewTypeCount() {
			return 2;
		}
		
		/**
		 * 프로그레스바를 커스터 마이징 한다.
		 */
		private void convertProgressBarConfig(SimpleSwipeView subPollView , SubImagePollResult result) {		
			Drawable[] drawables = new Drawable[3];
			ColorDrawable cd = new ColorDrawable(0xff000000);
			
			BitmapDrawable bd = (BitmapDrawable)getResources().getDrawable(result.getImagePath());
			ClipDrawable cp = new ClipDrawable(bd,Gravity.RIGHT, ClipDrawable.VERTICAL);
				
			drawables[0] = cd;
			drawables[1] = new ColorDrawable(0x00000000);
			drawables[2] = new ClipDrawable(bd , Gravity.LEFT, ClipDrawable.HORIZONTAL);
				
			LayerDrawable ld = new LayerDrawable(drawables);
			View frontView = subPollView.getFrontView();
			ProgressBar pBar = (ProgressBar) frontView.findViewById(R.id.vote_progress);
			TextView voteMount = (TextView)frontView.findViewById(R.id.vote_mount);
			pBar.setProgressDrawable(ld);
			pBar.setProgress(result.getSubVoteCount());
			voteMount.setText("" + result.getSubVoteCount());
		}
	}
	
	class ProHandler extends Handler{
		int completecount =  0;
		SimpleSwipeView animView;
		
		public SimpleSwipeView getAnimView() {
			return animView;
		}

		public void setAnimView(SimpleSwipeView animView) {
			this.animView = animView;
		}

		@Override
		public void handleMessage(Message msg) {
			completecount++;
			if (completecount == 5){
					shakeAnimationContainer();
				completecount = 0;
			}
		}
		
		public  void shakeAnimationContainer() {
			if (animView !=null)
				animView.initialAnimation();
		}
		
		public void shakeAnimtionContainer(SimpleSwipeView view){
			if (view !=null)
				view.initialAnimation();
		}
	}

}
