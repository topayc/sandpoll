package com.sandwhale.testepoll.customview;

import com.sandwhale.testepoll.R;

import android.content.Context;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.RelativeLayout;
import android.widget.Toast;



public class SimpleSwipeView extends RelativeLayout {

	View frontView;
	View backView;
	boolean isOpend = false;
	GestureDetector gestureScanner;
	Context context;
	LayoutInflater inflater;
	
	boolean isSwipeEnable = true;

	Animation closeAnim;
	Animation openAnim;
	Animation bouncAnimation;

	float animRate = 0.8f;

	private static final int SWIPE_MIN_DISTANCE = 70;
	private static final int SWIPE_MAX_OFF_PATH = 250;
	private static final int SWIPE_THRESHOLD_VELOCITY = 100;

	public View getFrontView() {
		return frontView;
	}

	public void setFrontView(View frontView) {
		this.frontView = frontView;
	}

	public View getBackView() {
		return backView;
	}

	public void setBackView(View backView) {
		this.backView = backView;
	}

	public boolean isOpend() {
		return isOpend;
	}

	public void setOpend(boolean isOpend) {
		this.isOpend = isOpend;
	}

	public Animation getCloseAnim() {
		return closeAnim;
	}

	public void setCloseAnim(Animation closeAnim) {
		this.closeAnim = closeAnim;
	}

	public Animation getOpenAnim() {
		return openAnim;
	}

	public void setOpenAnim(Animation openAnim) {
		this.openAnim = openAnim;
	}

	public SimpleSwipeView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
		init();
	}

	public SimpleSwipeView(Context context) {
		super(context);
		this.context = context;
		init();
	}

	public SimpleSwipeView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		this.context = context;
	}

	private void init() {
		setClickable(true);
		gestureScanner = new GestureDetector(context, gestureListener);
		inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		frontView = inflater.inflate(R.layout.front_view, null);
		backView = inflater.inflate(R.layout.back_view, null);

		openAnim = new TranslateAnimation(Animation.RELATIVE_TO_PARENT, 0f,
				Animation.RELATIVE_TO_PARENT, -0.8f,
				Animation.RELATIVE_TO_PARENT, 0f, Animation.RELATIVE_TO_PARENT,
				0f);
		openAnim.setDuration(200);
		openAnim.setInterpolator(new AccelerateInterpolator());
		openAnim.setFillAfter(true);

		closeAnim = new TranslateAnimation(Animation.RELATIVE_TO_PARENT, -0.8f,
				Animation.RELATIVE_TO_PARENT, 0f, Animation.RELATIVE_TO_PARENT,
				0f, Animation.RELATIVE_TO_PARENT, 0f);
		closeAnim.setDuration(200);
		closeAnim.setInterpolator(new AccelerateInterpolator());
		openAnim.setFillAfter(true);

		bouncAnimation = new TranslateAnimation(Animation.RELATIVE_TO_PARENT,
				0f, Animation.RELATIVE_TO_PARENT, -0.2f,
				Animation.RELATIVE_TO_PARENT, 0f, Animation.RELATIVE_TO_PARENT,
				0f);
		bouncAnimation.setRepeatCount(1);
		bouncAnimation.setDuration(400);
		bouncAnimation.setRepeatMode(Animation.REVERSE);
		bouncAnimation.setInterpolator(new DecelerateInterpolator());

		RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
				RelativeLayout.LayoutParams.MATCH_PARENT,
				RelativeLayout.LayoutParams.MATCH_PARENT);

		this.addView(backView, params);
		this.addView(frontView, params);

	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent me) {
		this.gestureScanner.onTouchEvent(me);
		return super.dispatchTouchEvent(me);
	}

	GestureDetector.OnGestureListener gestureListener = new GestureDetector.OnGestureListener() {
		@Override
		public boolean onSingleTapUp(MotionEvent e) {
			if (isSwipeEnable == false) return false;
			
			if (!isOpend) {
				onToggleAnimateion();
			}
			return false;
		}

		@Override
		public void onShowPress(MotionEvent e) {
		}

		@Override
		public boolean onScroll(MotionEvent e1, MotionEvent e2,
				float distanceX, float distanceY) {
			return false;
		}

		@Override
		public void onLongPress(MotionEvent e) {
		}

		@Override
		public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
				float velocityY) {
			if (isSwipeEnable == false) return false;

			try {

				if (Math.abs(e1.getY() - e2.getY()) > SWIPE_MAX_OFF_PATH)
					return false;

				if (e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE
						&& Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {

					if (!isOpend) {
						onToggleAnimateion();
					}
				}

				else if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE
						&& Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
					if (isOpend) {
						onToggleAnimateion();
					}
				}

				else if (e1.getY() - e2.getY() > SWIPE_MIN_DISTANCE
						&& Math.abs(velocityY) > SWIPE_THRESHOLD_VELOCITY) {
					
				}

				else if (e2.getY() - e1.getY() > SWIPE_MIN_DISTANCE
						&& Math.abs(velocityY) > SWIPE_THRESHOLD_VELOCITY) {
					
				}
			} catch (Exception e) {

			}

			return true;
		}

		@Override
		public boolean onDown(MotionEvent e) {

			return false;
		}
	};

	public void setSwipeEnable(boolean enable){
		isSwipeEnable = enable;
	}
	
	private void onToggleAnimateion() {
		if (isOpend) {
			frontView.startAnimation(closeAnim);
			isOpend = false;
		} else {
			frontView.startAnimation(openAnim);
			isOpend = true;
		}
	}

	public void initialAnimation() {
		frontView.startAnimation(bouncAnimation);
	}
}
