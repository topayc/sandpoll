package com.sandwhale.testepoll;



import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

public class ImageAdapter extends BaseAdapter {

	private LayoutInflater mInflater;
	private static final int[] ids = new int[]{
		R.drawable.replay_image1,R.drawable.replay_image2,R.drawable.replay_image3,
		R.drawable.replay_image4,R.drawable.replay_image6};

	public ImageAdapter(Context context) {
		mInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() {
		return ids.length;
	}

	@Override
	public Object getItem(int position) {
		return position;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.image_item, null);
		}
		//((ImageView) convertView.findViewById(R.id.imgView)).setScaleType(ScaleType.FIT_XY);
		((ImageView) convertView.findViewById(R.id.imgView))
				.setImageResource(ids[position]);
		return convertView;
	}

}