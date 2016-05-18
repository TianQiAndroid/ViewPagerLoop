package com.example.viewpagerloop;

import java.util.ArrayList;
import java.util.List;
import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;

public class MainActivity extends Activity {

	private List<ImageView> mImageList;
	private LoopViewPager mLoopViewPager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mImageList = new ArrayList<ImageView>();

		for (int i = 0; i < 4; i++) {
			ImageView iv = new ImageView(getApplicationContext());
			switch (i) {
			case 0:

				iv.setBackgroundResource(R.drawable.ic_launcher);
				break;
			case 1:
				iv.setBackgroundResource(R.drawable.ic_launcher1);

				break;
			case 2:
				iv.setBackgroundResource(R.drawable.ic_launcher2);

				break;
			case 3:
				iv.setBackgroundResource(R.drawable.ic_launcher3);

				break;
			default:
				break;
			}
			mImageList.add(iv);
		}
		mLoopViewPager = (LoopViewPager) findViewById(R.id.loopViewPager);
		mLoopViewPager.setImageList(mImageList);
		mLoopViewPager.startLoop(2000);
	}
}
