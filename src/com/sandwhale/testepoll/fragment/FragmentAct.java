package com.sandwhale.testepoll.fragment;

import java.util.ArrayList;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.sandwhale.testepoll.R;
import com.sandwhale.testepoll.ActivityMain.LeftMenuViewHolder;
import com.sandwhale.testepoll.model.Category;

public class FragmentAct extends Fragment {
	View mActLayout;
	ListView mActList;
	ActListAdpater mActListAdpater;
	
	
	public static FragmentAct newInstance(){
		FragmentAct actFragment = new FragmentAct();
		return actFragment;
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
		mActLayout = inflater.inflate(R.layout.fragment_act, null);
		mActList = (ListView)mActLayout.findViewById(R.id.act_list);
		return mActLayout;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	
	}
	

	@Override
	public void onResume() {
		super.onResume();
		mActListAdpater = new ActListAdpater(getActivity());
		mActList.setAdapter(mActListAdpater);
		
	}
	
	@Override
	public void onPause() {
		super.onPause();
	}
	
	public static class ActListAdpater extends BaseAdapter{

		public Activity  activity;
		public ArrayList<Category> categories;
		public int rowResId = R.layout.fragment_act_item_row;
		public LayoutInflater inflater;
		
		public ActListAdpater(Activity activity){
			this.activity =activity;
			inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		}
		
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return 20;
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
			ActListHolder holder;
			if (convertView == null){
				convertView = this.inflater.inflate(rowResId,parent, false);
				holder = new ActListHolder();
				holder.actImageView = (ImageView)convertView.findViewById(R.id.act_list);
				holder.questionUserView = (TextView)convertView.findViewById(R.id.question_user);
				holder.questionView = (TextView)convertView.findViewById(R.id.question);
				holder.questionDate = (TextView)convertView.findViewById(R.id.question_date);
				convertView.setTag(holder);
			}else {
				holder = (ActListHolder)convertView.getTag();
			}
			return convertView;
		}	
	}
	
	public static class ActListHolder {
		ImageView actImageView;
		TextView questionUserView;
		TextView questionView;
		TextView questionDate;
		
	}

	
}
