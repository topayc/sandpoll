package com.sandwhale.testepoll;

import java.util.ArrayList;
import java.util.Random;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.navdrawer.SimpleSideDrawer;
import com.sandwhale.testepoll.common.Initializer;
import com.sandwhale.testepoll.fragment.FragmentAct;
import com.sandwhale.testepoll.fragment.FragmentInitial;
import com.sandwhale.testepoll.fragment.FragmentMyHome;
import com.sandwhale.testepoll.fragment.FragmentMyPage;
import com.sandwhale.testepoll.fragment.FragmentPollList;
import com.sandwhale.testepoll.fragment.FragmentServiceCenter;
import com.sandwhale.testepoll.fragment.FragmentServiceProvider;
import com.sandwhale.testepoll.fragment.FragmentSettings;
import com.sandwhale.testepoll.model.AppManager;
import com.sandwhale.testepoll.model.Category;
import com.sandwhale.testepoll.model.UserSession;
import com.sandwhale.testepoll.preference.UserPreference;

public class ActivityMain extends Activity implements Initializer {
	
	public SimpleSideDrawer mNav;
	private LeftMenuAdapter mLeftMenuAdapter;
	private ListView mLeftMenuList;
	
	public int mCurCategory;
	private LayoutInflater mInflater;
	public View  menuHomeHeaderView;
	public View categoryDelim;
	
	public TextView titleView;
	
	/*
	 * 1 : myHome             ===> 로그인 상태
	 * 2 : myHome + initial   ===> 비 로그인 상태 
	 * 3 : pollList           ===> 카테고리별 폴 리스트 
	 * 4 : settings           ===> 설정 
	 * 5 : activity           ===> 활동 내역  
	 * 6 : service_center     ===> 서비스 센터 
	 */
	public int mMainFrameStatus = -1;
	
	public static final int LOGIN_FRAME = 1;
	public static final int LOGIN_NOT_FRAME = 2;
	public static final int POLL_LIST_FRAME = 3;
	public static final int SETTING_FRAME = 4;
	public static final int ACT_FRAME = 5;
	public static final int SERVICE_CENTER_FRAME = 6;
	/**
	 * MainFrame 에 add 되는 프래그먼트 태그
	 */
	public static final String TAG_MYHOME ="myHome";
	public static final String TAG_INITIAL ="initial";
	public static final String TAG_POLL_LIST ="pollList";
	public static final String TAG_SETTING ="setting";
	public static final String TAG_ACTIVITY = "activity";
	public static final String TAG_SERVICE_CENTER ="serviceCenter";
	
	public static final String TAG_MAIN_CONTENT_FRAME = "main_content";
	
	public static int loginStaus = 0;
	
	/**
	 * right 프레임에 add 되는 프래그 먼트 태그
	 */
	public static final String TAG_RIGHT ="rightFragment";
	
	public static final int MAIN_FRAME_AUTH  =1;
	public static final int MAIN_FRAME_NOT_AUTH = 2;
	public static final int MAIN_FRAME_LIST_POLL = 3;
	
	public static final String TAG ="ActivityMain";
    
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        
        initInstance();
        initView();
        initListener();
        initLayout();    
  
    }
	
	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }

	@Override
	public void initView() {
		menuHomeHeaderView = mInflater.inflate(R.layout.menu_home, null);
		titleView = (TextView)findViewById(R.id.mainTitle);
	}
	
	@Override
	public void initInstance() {
        mNav = new SimpleSideDrawer(this);
        mInflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mCurCategory = -1;
	}

	@Override
	public void initListener() {
		//밑의 뷰로 이벤트 전파를 막는다.
		findViewById(R.id.main_container).setOnClickListener(new View.OnClickListener() {	
			@Override
			public void onClick(View v) {
			}
		});
	}
	
	@Override
	public void initLayout() {
		  mNav.setLeftBehindContentView(R.layout.activity_behind_left_simple);
		  mLeftMenuList =(ListView) mNav.findViewById(R.id.left_menu_list);
		  
	        findViewById(R.id.leftBtn).setOnClickListener(new OnClickListener() {
	            @Override 
	            public void onClick(View v) {
	                mNav.toggleLeftDrawer();
	            }
	        });
	        
	        mNav.setRightBehindContentView(R.layout.activity_behind_right_simple);
	        
	        findViewById(R.id.rightBtn).setOnClickListener(new OnClickListener() {
	            @Override 
	            public void onClick(View v) {
	            	adjustRightFrame();
	            	mNav.toggleRightDrawer();
	            	
	                
	            }
	        });
	          
	        menuHomeHeaderView.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					mCurCategory = -1;
					resetListSelect();
					initialAdujstMainFrame();
					changeTitle(R.string.tastepoll);
					mNav.toggleLeftDrawer();
					

				}
			});
	        initialAdujstMainFrame();
	        changeTitle(R.string.tastepoll);
	        mLeftMenuList.setOnItemClickListener(leftMenuListener);
	    	addheaderViewToList();
			refreshMenuListView();    
	}
	
	private void initialAdujstMainFrame() {
		/**
		 * 로그인 상태를 변화시켜 뷰를 테스트함 
		 */
		loginStaus++;
		boolean isAuth = loginStaus % 2 == 0 ? true : false;
		/*boolean isAuth = UserSession.getUserSession().isAuthencated();*/
		if (isAuth){
			changeMainContentFragment(FragmentMyHome.newInstance());
		}else {
			changeMainContentFragment(FragmentInitial.newInstance());
		}	
	}

	
	public void resetListSelect(){
		int childCount = mLeftMenuList.getChildCount();
		for (int i = 2; i <childCount; i++){
			View childView = mLeftMenuList.getChildAt(i);
			View selectedBar = childView.findViewById(R.id.selected_bar);
			selectedBar.setVisibility(View.INVISIBLE);
			TextView text = (TextView)childView.findViewById(R.id.menu_title);
			text.setTextColor(0xff888888);
		}
	}
	
	public void toggleLeftDrawer(){
		mNav.toggleLeftDrawer();	
	}
	
	public void toggleRightDrawer(){
		 mNav.toggleRightDrawer();
	}
	
	AdapterView.OnItemClickListener leftMenuListener = new AdapterView.OnItemClickListener() {
		@Override
		public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
			int selectedCategory = position -2;
			/*
			if (mCurCategory == selectedCategory) {
				toggleLeftDrawer();
				return;
			}else {*/
				
				resetListSelect();
				View selectedBar = view.findViewById(R.id.selected_bar);
				selectedBar.setVisibility(View.VISIBLE);
				TextView textView = (TextView)view.findViewById(R.id.menu_title);
				textView.setTextColor(Color.WHITE);
				toggleLeftDrawer();
				mCurCategory = selectedCategory;
				ListPoll(position-2);
			/*}*/
		}
	};
		
	/**
	 * 카테고리의 폴 리스트를 보여줌 
	 */
	public void ListPoll(int category){
		changeMainContentFragment(FragmentPollList.newInstance(category));
		String categoryName = getCategoryName(category);
		changeTitle(categoryName);
		mMainFrameStatus = POLL_LIST_FRAME;
	}
	
	public void changeMainContentFragment(Fragment fragment){
		FragmentTransaction ft = getFragmentTransaction();
		ft.replace(R.id.activity_main_content_fragment,fragment, TAG_MAIN_CONTENT_FRAME);
		ft.commit();
	}
	
	
	private String getCategoryName(int category) {
		String[] categoryName = getResources().getStringArray(R.array.left_menu_category);
		return categoryName[category];
	}

	@Override
	protected void onPause() {
		super.onPause();
	}

	@Override
	protected void onResume() {
		super.onResume();
	}
	
	/**
	 * Left 메뉴의 리스트에 헤더뷰를 추가 
	 */
	public void addheaderViewToList(){
		categoryDelim = mInflater.inflate(R.layout.category_delim, null);
		mLeftMenuList.addHeaderView(menuHomeHeaderView);
		mLeftMenuList.addHeaderView(categoryDelim);
	}
	
	/**
	 * Left 메뉴의 리스트의 헤더뷰를 제거 
	 */
	public void removeHeadeViewFromList(){
		mLeftMenuList.removeHeaderView(menuHomeHeaderView);
		mLeftMenuList.removeHeaderView(categoryDelim);
	}
	
	/**
	 * Left 메뉴에 어댑터를 부착
	 */
	public void refreshMenuListView(){
		mLeftMenuAdapter = new LeftMenuAdapter(this, AppManager.getInstance().getCategories() , R.layout.left_menu_list_row);		
	    mLeftMenuList.setAdapter(mLeftMenuAdapter);	
	}
	
	/**
	 * Main frame 의 모든 Fragment 를 제거한다.
	 */
	public void removeAllMainFragment(FragmentTransaction ft, int removeTarget){
		ArrayList<Fragment> removeFragments = new ArrayList<Fragment>();
		
		switch(removeTarget){
			
			case LOGIN_FRAME:
				removeFragments.add(getFragmentManager().findFragmentByTag(TAG_MYHOME));
				break;
				
			case LOGIN_NOT_FRAME:
				removeFragments.add(getFragmentManager().findFragmentByTag(TAG_MYHOME));
				removeFragments.add(getFragmentManager().findFragmentByTag(TAG_INITIAL));
				break;
				
			case POLL_LIST_FRAME:
				removeFragments.add(getFragmentManager().findFragmentByTag(TAG_POLL_LIST));
				break;
				
			case SETTING_FRAME:
				removeFragments.add(getFragmentManager().findFragmentByTag(TAG_SETTING));
				break;
				
			case ACT_FRAME:
				removeFragments.add(getFragmentManager().findFragmentByTag(TAG_ACTIVITY));
				break;
				
			case SERVICE_CENTER_FRAME:
				removeFragments.add(getFragmentManager().findFragmentByTag(TAG_SERVICE_CENTER));
				break;	
		}
		
		for (int i = 0; i < removeFragments.size(); i++){
			Fragment f = removeFragments.get(i);
			if (f != null){
				ft.remove(f);
			}
		}	
	}
	
	/**
	 * right 프레임의 Fragment 를 모두 제거
	 */
	public void removeAllRightFragment(FragmentTransaction ft){
		Fragment fragment = getFragmentManager().findFragmentByTag(TAG_RIGHT);
		if (fragment != null)
			ft.remove(fragment);	
	}
	
	/**
	 * Right 프래임의 프래그먼트를  조정
	 */
	private void adjustRightFrame() {
		
		FragmentTransaction ft = getFragmentTransaction();
		removeAllRightFragment(ft);
		boolean isAuth = UserSession.getUserSession().isAuthencated();
		Fragment fragment;
		
		loginStaus++;
		
		/**
		 * 임시로 로그인상태로 변경한다.
		 */
		if (loginStaus % 2  == 0 ? true :false){
			fragment = FragmentMyPage.newInstance();
		}
		else {
			fragment = FragmentServiceProvider.newInstance();
		}
		ft.add(R.id.activity_main_right_fragment, fragment,TAG_RIGHT);
		ft.commit();
	}
	
	/**
	 * 메인프레임의 프래그먼트를 조정
	 */
	public void adjustMainFrame(){
		FragmentTransaction ft = getFragmentTransaction();
		removeAllMainFragment(ft,mMainFrameStatus);
		
		boolean isAuth = UserSession.getUserSession().isAuthencated();
		
		if (isAuth){
			ft.add(R.id.activity_main_content_fragment, FragmentMyHome.newInstance(),TAG_MYHOME);
		}else {
			ft.add(R.id.activity_main_content_fragment, FragmentMyHome.newInstance(),TAG_MYHOME);
			ft.add(R.id.activity_main_content_fragment, FragmentInitial.newInstance(),TAG_INITIAL);	
		}
		ft.commit();
	}

	public FragmentTransaction getFragmentTransaction(){
		FragmentTransaction ft = getFragmentManager().beginTransaction();
		return ft;
	}
	
	public String getGcmId(){
		return new UserPreference(this).getString(UserPreference.KEY_UUID, "");
	}
	
	public void changeTitle(int stringRes){
		titleView.setText(stringRes);
	}
	
	public void changeTitle(String message){
		titleView.setText(message);
	}
	
	public static class LeftMenuAdapter extends BaseAdapter {
		public Activity  activity;
		public ArrayList<Category> categories;
		public int rowResId;
		public LayoutInflater inflater;
		
		public LeftMenuAdapter(Activity activity,ArrayList<Category> categories, int resId){
			this.activity = activity;
			this.categories = categories;
			this.rowResId = resId;
			this.inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		}
		
		@Override
		public int getCount() {
			return categories.size();
		}

		@Override
		public Object getItem(int position) {
			return categories.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {	
			LeftMenuViewHolder holder;
			if (convertView == null){
				convertView = this.inflater.inflate(rowResId,parent, false);
				holder = new LeftMenuViewHolder();
				holder.menuTitle = (TextView)convertView.findViewById(R.id.menu_title);
				holder.memuImage = (ImageView)convertView.findViewById(R.id.menu_image);
				convertView.setTag(holder);
			}else {
				holder = (LeftMenuViewHolder)convertView.getTag();
			}		
			holder.memuImage.setImageResource(categories.get(position).getIconResId());
			holder.menuTitle.setText(categories.get(position).getName());
			return convertView;
			}
		}
		
		public static class LeftMenuViewHolder{
			public ImageView memuImage;
			public TextView menuTitle;
		}
}



























