package com.sandwhale.testepoll.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewManager;
import android.view.Window;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.sandwhale.testepoll.ActivityMain;
import com.sandwhale.testepoll.R;
import com.sandwhale.testepoll.common.AndroUtils;

public class FragmentServiceProvider extends Fragment{
	
	public TextView regBtn;
	public TextView loginBtn;
	public TextView serviceBtn;
	
	View providerLay;
	
	public static FragmentServiceProvider newInstance(){
		FragmentServiceProvider rightFragment = new FragmentServiceProvider();
		return rightFragment;
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
		
		providerLay = inflater.inflate(R.layout.fragment_service_provider, null);
		regBtn = (TextView)providerLay.findViewById(R.id.reg_btn);
		loginBtn = (TextView)providerLay.findViewById(R.id.login_btn);
		serviceBtn = (TextView)providerLay.findViewById(R.id.service_center_btn);
		return providerLay;

	
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		regBtn.setOnClickListener(clickListener);
		loginBtn.setOnClickListener(clickListener);
		serviceBtn.setOnClickListener(clickListener);
	}
	

	@Override
	public void onResume() {
		super.onResume();
	}
	
	@Override
	public void onPause() {
		super.onPause();
	}
	
	
	Window win ;
	LinearLayout regView;
	ViewFlipper mRegFlipper;
	
	LinearLayout loginView;
	ViewFlipper mLoginFlipper; 
	PopupWindow regPop;
	PopupWindow loginPop;
	
	
	
	
	View.OnClickListener clickListener = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			LayoutInflater inflater = (LayoutInflater)getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			Point size = AndroUtils.getScreenSize(getActivity());
			
			switch(v.getId()){
			
			case R.id.reg_btn:
			
				regView = (LinearLayout)inflater.inflate(R.layout.register_form, null);
				regPop = new PopupWindow(regView, size.x, size.y -(size.y/3) ,true);
				regPop.showAtLocation(providerLay, Gravity.LEFT|Gravity.TOP, 0, AndroUtils.getStatusBarHeight(getActivity()));
				setRegProcessListenerAndView(regView);
			
				/*	win = getActivity().getWindow();
				LinearLayout.LayoutParams paramlinear = new LinearLayout.LayoutParams(
						LinearLayout.LayoutParams.MATCH_PARENT,
						LinearLayout.LayoutParams.WRAP_CONTENT);
				win.addContentView( regView, paramlinear);
				((ActivityMain)getActivity()).mNav.toggleRightDrawer();*/
				break;
				
			case R.id.login_btn:
				loginView = (LinearLayout)inflater.inflate(R.layout.login_form, null);
				loginPop = new PopupWindow(loginView, size.x, size.y -(size.y/3) ,true);
				loginPop.showAtLocation(providerLay, Gravity.LEFT|Gravity.TOP, 0, AndroUtils.getStatusBarHeight(getActivity()));
				setLoginProcessListenerAndView(loginView);
		
				/*win = getActivity().getWindow();
				LinearLayout.LayoutParams paramlinear1 = new LinearLayout.LayoutParams(
						LinearLayout.LayoutParams.MATCH_PARENT,
						LinearLayout.LayoutParams.WRAP_CONTENT);
				win.addContentView( loginView, paramlinear1);
				((ActivityMain)getActivity()).mNav.toggleRightDrawer();*/
				
				break;
			case R.id.service_center_btn:
				ActivityMain activityMain = ((ActivityMain)getActivity());
				activityMain.changeMainContentFragment(FragmentServiceCenter.newInstance());
				activityMain.changeTitle(R.string.service_center);
				activityMain.toggleRightDrawer();
				break;
			}	
		}
	};
	
	View.OnClickListener regProcListener = new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			switch(v.getId()){
			case R.id.reg_process_close_btn:
				if (regView !=null && regPop.isShowing()){
					regPop.dismiss();
					/*((ViewManager) regView.getParent()).removeView(regView);*/
					regView = null;
					regPop = null;
				}
				break;
			
			case R.id.reg_pre_nav_btn1:
				setBackAnimation();
				mRegFlipper.showPrevious();
				break;
	
			case R.id.reg_pre_nav_btn2:
				setBackAnimation();
				mRegFlipper.showPrevious();
				break;
				
			case R.id.email_reg_btn:
				setNextAnimation();
				mRegFlipper.showNext();
				break;
			
			case R.id.next1:
				setNextAnimation();
				mRegFlipper.showNext();
				break;
			}
		}
	};
	
	View.OnClickListener loginProcListener = new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			switch(v.getId()){
			case R.id.login_process_close_btn:
				if (loginView != null && loginPop.isShowing()){
				/*	((ViewManager) loginView.getParent()).removeView(loginView);*/
					loginPop.dismiss();
					loginView = null;
					loginPop = null;
				}
				
				break;
			
			case R.id.login_pre_nav_btn1:
				setBackLoginAnimation();
				mLoginFlipper.showPrevious();
				break;
				
			case R.id.reset_pass_btn:
				setNextLoginAnimation();
				mLoginFlipper.showNext();
				break;

			}
		}
	};

	
	private void setBackLoginAnimation(){
		mLoginFlipper.setInAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.backview_in));
		mLoginFlipper.setOutAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.backview_out));
	}
	
	private void setNextLoginAnimation(){
		mLoginFlipper.setInAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.viewin));
		mLoginFlipper.setOutAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.viewout));
	}
	
	private void setBackAnimation(){
		mRegFlipper.setInAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.backview_in));
		mRegFlipper.setOutAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.backview_out));
	}
	
	private void setNextAnimation(){
		mRegFlipper.setInAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.viewin));
		mRegFlipper.setOutAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.viewout));
	}
	
	
	private void setLoginProcessListenerAndView(LinearLayout loginView){
		mLoginFlipper = (ViewFlipper)loginView.findViewById(R.id.login_form_flipper);
		loginView.findViewById(R.id.login_process_close_btn).setOnClickListener(loginProcListener);
		loginView.findViewById(R.id.login_pre_nav_btn1).setOnClickListener(loginProcListener);
		loginView.findViewById(R.id.reset_pass_btn).setOnClickListener(loginProcListener);
		
	}
	
	private void setRegProcessListenerAndView(LinearLayout regView) {
		mRegFlipper = (ViewFlipper)regView.findViewById(R.id.reg_form_flipper);
		regView.findViewById(R.id.reg_pre_nav_btn1).setOnClickListener(regProcListener);
		regView.findViewById(R.id.reg_pre_nav_btn2).setOnClickListener(regProcListener);
		
		regView.findViewById(R.id.reg_process_close_btn).setOnClickListener(regProcListener);
		
		regView.findViewById(R.id.email_reg_btn).setOnClickListener(regProcListener);
		regView.findViewById(R.id.next1).setOnClickListener(regProcListener);
	}

}