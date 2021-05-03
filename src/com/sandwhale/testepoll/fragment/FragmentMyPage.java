package com.sandwhale.testepoll.fragment;

import com.sandwhale.testepoll.ActivityMain;
import com.sandwhale.testepoll.R;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class FragmentMyPage extends Fragment {
	
	public TextView actBtn;
	public TextView settingBtn;
	public TextView serviceBtn;
	
	public static FragmentMyPage newInstance(){
		FragmentMyPage frag= new FragmentMyPage();
		return frag;
	}
	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);	
		View providerLay = inflater.inflate(R.layout.fragment_my_page, null);
		actBtn = (TextView)providerLay.findViewById(R.id.act_btn);
		settingBtn = (TextView)providerLay.findViewById(R.id.setting_btn);
		serviceBtn = (TextView)providerLay.findViewById(R.id.service_center_btn);
		return providerLay;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		setListener();
	}
	
	public void setListener(){
		actBtn.setOnClickListener(listener);
		settingBtn.setOnClickListener(listener);
		serviceBtn.setOnClickListener(listener);
	}
	
	View.OnClickListener listener = new View.OnClickListener() {	
		@Override
		public void onClick(View v) {
			ActivityMain activityMain = ((ActivityMain)getActivity());
			switch(v.getId())
			{
			case R.id.act_btn:
				activityMain.changeMainContentFragment(FragmentAct.newInstance());
				activityMain.changeTitle(R.string.activity);
				activityMain.toggleRightDrawer();
				break;
				
			case R.id.setting_btn:
				activityMain.changeMainContentFragment( FragmentSettings.newInstance());
				activityMain.changeTitle(R.string.settings);
				activityMain.toggleRightDrawer();
				break;
				
			case R.id.service_center_btn:
				activityMain.changeMainContentFragment( FragmentServiceCenter.newInstance());
				activityMain.changeTitle(R.string.service_center);
				activityMain.toggleRightDrawer();
		
				break;
			}
		}
	};
	
	@Override
	public void onResume() {
		super.onResume();
	}
	
	@Override
	public void onPause() {
		super.onPause();
	}
	
}
