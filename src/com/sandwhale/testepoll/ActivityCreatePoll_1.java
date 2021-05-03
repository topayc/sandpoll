package com.sandwhale.testepoll;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.sandwhale.testepoll.common.CommonUtils;
import com.sandwhale.testepoll.common.Initializer;
import com.sandwhale.testepoll.config.AppInfo;
import com.sandwhale.testepoll.conponent.ListDialog;

public class ActivityCreatePoll_1 extends Activity implements Initializer {
	
	public  AppInfo appInfo;
	Button mCancelBtn;
	Button mNextBtn;
	TextView mTitleView;
	
    ImageButton mselectCateBtn;
    EditText mPollTitleEdit;
    ImageButton mSelectImgBtn;
    EditText mPollDetailEdit;
	
	boolean mIsComplete = false;
	int mCheckFormCount = 3;
	
	String[] categories;
	int[] categoryRes;
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_create_poll_1);
		
		initInstance();
		initView();
		initListener();
		initLayout();
	}

	@Override
	public void initInstance() {
		try {
			appInfo = AppInfo.getInstance(this);
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	@Override
	public void initView() {
		// TODO Auto-generated method stub
		mCancelBtn = (Button)findViewById(R.id.backBtn);
		mNextBtn = (Button)findViewById(R.id.reportBtn);
		mTitleView = (TextView)findViewById(R.id.mainTitle);
		mselectCateBtn = (ImageButton)findViewById(R.id.select_category_btn);
		mPollTitleEdit = (EditText)findViewById(R.id.poll_title_edit);
		mSelectImgBtn = (ImageButton)findViewById(R.id.select_img_btn);
		mPollDetailEdit =(EditText)findViewById(R.id.poll_content_edit);	
	}
	
	@Override
	public void initListener() {
		// TODO Auto-generated method stub
		
		mCancelBtn.setOnClickListener(clickListener);
		mNextBtn.setOnClickListener(clickListener);
		mSelectImgBtn.setOnClickListener(clickListener);
		mselectCateBtn.setOnClickListener(clickListener);
		mPollTitleEdit.setOnFocusChangeListener(editFocusListener);
	}
	
	View.OnFocusChangeListener editFocusListener= new View.OnFocusChangeListener() {
		
		@Override
		public void onFocusChange(View v, boolean hasFocus) {
			if (!hasFocus){
				if (!CommonUtils.isNullOrEmpty(mPollTitleEdit.getText().toString())){
					mCheckFormCount++;
				}else {
					mCheckFormCount--;
				}
				
				setNextActivated();
			}
		}
	};
	
	View.OnClickListener clickListener = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			switch(v.getId()){
			case R.id.backBtn:
				finish();
				overridePendingTransition(0, R.anim.slide_out_bottom);
				break;
				
			case R.id.reportBtn:
				Intent i = new Intent(ActivityCreatePoll_1.this, ActivityCreatePoll_2.class);
				startActivity(i);
				overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
				break;
				
			case R.id.select_category_btn:
				//showCategoryDialog();
				break;
				
			case R.id.select_img_btn:
				break;
				
			}
			
		}
	};

	
	public void showCategoryDialog(){
		SelectCategoryDialogFragment categoryDialog = SelectCategoryDialogFragment.newInstance();
		FragmentManager fManager = getFragmentManager();
		categoryDialog.show(fManager ,"categoryDialog");
		
		
	}
	
	@Override
	public void initLayout() {
		mTitleView.setText(R.string.create_new_poll);
		setNextActivated();
	}
	
	public void setNextActivated(){
		mNextBtn.setEnabled(isFormFilled());
	}
	
	public boolean isFormFilled(){
		if (mCheckFormCount < 2){
			return false;
		}else {
			return true;
		}
	}
	
	public boolean checkForm(){
		return false;
	}
	
	protected void onCategorySelected(int position) {
		// TODO Auto-generated method stub
		
	}

	
	public static class SelectCategoryDialogFragment extends DialogFragment  {
		
		public int ownerId;
		
		public static SelectCategoryDialogFragment newInstance() {
			SelectCategoryDialogFragment fragment = new SelectCategoryDialogFragment();
	        return fragment;
	    }

	    @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);

	    }
	    
	    @Override
	    public Dialog onCreateDialog(Bundle savedInstanceState) {
	    	/*LayoutInflater inflator  = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	    	View layout = inflator.inflate(R.layout.fragment_dlg_select_category, null);
	    	final ListView categoryList = (ListView)layout.findViewById(R.id.category_list);
	    	
	    	categoryList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
	    		public void onItemClick(AdapterView<?> parent, View view,
	   				 int position, long id) {
	    			((ActivityCreatePoll_1)getActivity()).onCategorySelected(position);
	    		}
	    		
			});*/
	    	ListDialog dialog = new ListDialog(getActivity());
	    	dialog.setOnItemClickListener(new AdapterView.OnItemClickListener() {
	
				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						 int position, long id) {
					((ActivityCreatePoll_1)getActivity()).onCategorySelected(position);
					SelectCategoryDialogFragment .this.dismiss();
					
				}		
			});	
	    	
	    	dialog.setAdpater(new ListDialogAdpater(getActivity()));
	        return dialog;
	    }
	}
	
	public static class ListDialogAdpater extends BaseAdapter{

		Activity activity;
		LayoutInflater inflater;
		
		public ListDialogAdpater(Activity activity){
			this.activity  = activity;
			inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		}
		@Override
		public int getCount() {
	
			try {
				return AppInfo.getInstance(activity).categories.length;
			} catch (NameNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return 0;
		}

		@Override
		public Object getItem(int arg0) {
			return null;
		}

		@Override
		public long getItemId(int arg0) {	
			return arg0;
		}

	

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView == null){
				convertView = inflater.inflate(R.layout.list_dlg_row, parent, false);
			}
			ImageView cateImag = (ImageView)convertView.findViewById(R.id.cate_image);
			TextView cateName = (TextView)convertView.findViewById(R.id.cate_name);
			try{
				cateImag.setImageResource(AppInfo.getInstance(activity).categoryResIds[position]);
				cateName.setText(AppInfo.getInstance(activity).categoryResIds[position]);
				
			}catch(Exception e){
			}finally{
				return null;
			}
			
		}
		
	}

}
