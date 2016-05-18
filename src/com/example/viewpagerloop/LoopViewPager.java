package com.example.viewpagerloop;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.ImageView;

public class LoopViewPager extends ViewPager {

	private List<ImageView> mImageList;
	private List<String> mImageUrlList;

	private int position;
	private int delayMillis;

	private MyHandler handler;

	public MyHandler getHandler() {
		return handler;
	}

	public Runnable getR() {
		return r;
	}

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}

	public int getDelayMillis() {
		return delayMillis;
	}

	public void setDelayMillis(int delayMillis) {
		this.delayMillis = delayMillis;
	}

	static class MyHandler extends Handler {
		WeakReference<LoopViewPager> mWeakReference;

		MyHandler(LoopViewPager viewPager) {
			mWeakReference = new WeakReference<LoopViewPager>(viewPager);
		}

		@Override
		public void handleMessage(Message msg) {
			LoopViewPager viewPager = mWeakReference.get();
			if (viewPager != null) {
				viewPager.setPosition(viewPager.getPosition() + 1);
				viewPager.setCurrentItem(viewPager.getPosition());
				viewPager.getHandler().postDelayed(viewPager.getR(),
						viewPager.getDelayMillis());
			}
		}
	}

	public LoopViewPager(Context context) {
		super(context);
		init();
	}

	public LoopViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	@SuppressLint("ClickableViewAccessibility")
	private void init() {
		handler = new MyHandler(this);
		setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					handler.removeCallbacks(r);
					break;
				case MotionEvent.ACTION_UP:
				case MotionEvent.ACTION_CANCEL:
					handler.postDelayed(r, delayMillis);
					break;
				default:
					break;
				}
				return false;
			}
		});
		
		setOnPageChangeListener(new OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int arg0) {
				LoopViewPager.this.setPosition(arg0);
			}
			
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				
			}
			
			@Override
			public void onPageScrollStateChanged(int arg0) {
				
			}
		});
	}

	public void setImageUrlList(List<String> imageUrlList) {
		this.mImageUrlList = imageUrlList;
		mImageList = new ArrayList<ImageView>();
		int count = imageUrlList.size();
		for (int i = 0; i < count; i++) {
			ImageView imageView = new ImageView(getContext());
			mImageList.add(imageView);
		}

		setAdapter(new LoopViewPagerAdapter());
		position = Integer.MAX_VALUE / 2 - (Integer.MAX_VALUE / 2)
				% mImageList.size();
		setCurrentItem(position);
	}

	public void setImageList(List<ImageView> imageList) {
		this.mImageList = imageList;
		setAdapter(new LoopViewPagerAdapter());
		position = Integer.MAX_VALUE / 2 - (Integer.MAX_VALUE / 2)
				% mImageList.size();
		setCurrentItem(position);
	}

	private class LoopViewPagerAdapter extends PagerAdapter {

		@Override
		public int getCount() {

			return Integer.MAX_VALUE;
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {

			return arg0 == arg1;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			position = position % mImageList.size();
			container.removeView(mImageList.get(position));
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			position = position % mImageList.size();
			ViewParent parent = mImageList.get(position).getParent();
			if (parent != null) {
				container.removeView(mImageList.get(position));
			}
			container.addView(mImageList.get(position));
			return mImageList.get(position);
		}

	}

	private Runnable r = new Runnable() {
		public void run() {
			handler.sendEmptyMessage(0);
		}
	};

	/**
	 * 启动轮播图
	 * @param delayMillis 时间间隔
	 */
	public void startLoop(int delayMillis) {
		this.delayMillis = delayMillis;
		handler.postDelayed(r, delayMillis);

	}

}
