package com.lyj.agriculture.view;

import java.util.ArrayList;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.lyj.agriculture.R;
import com.lyj.agriculture.adapter.ComViewPagerAdapter;

public class TabView {
	private Context mContext;
	private View mMainView;
	private LayoutInflater mInflater;

	private int mTabCount = 0;

	private LinearLayout mHeaderLayout, mFootLayout;
	private ViewPager mViewPager;

	private ArrayList<TabViewHeader> headerList;
	private ArrayList<View> splitList;
	private ArrayList<ImageView> dotList;
	private ArrayList<View> mViews;

	public TabView(Context context) {
		mContext = context;
		mTabCount = 0;
		InflateView();
	}

	private View InflateView() {
		mInflater = LayoutInflater.from(mContext);
		mMainView = mInflater.inflate(R.layout.tabview, null);

		mHeaderLayout = (LinearLayout) mMainView.findViewById(R.id.erl_tabview_tabheaderlayout);
		mFootLayout = (LinearLayout) mMainView.findViewById(R.id.erl_tabview_footlayout);
		mViewPager = (ViewPager) mMainView.findViewById(R.id.erl_tabview_bodyviewpager);

		mHeaderLayout.removeAllViews();
		mFootLayout.removeAllViews();
		mViewPager.removeAllViews();

		headerList = new ArrayList<TabViewHeader>();
		splitList = new ArrayList<View>();
		dotList = new ArrayList<ImageView>();
		mViews = new ArrayList<View>();

		return mMainView;
	}

	private TabViewHeader getNewHeader(String tabName) {
		TabViewHeader tabViewHeader = new TabViewHeader(mContext, null);
		LinearLayout.LayoutParams lParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
				LinearLayout.LayoutParams.WRAP_CONTENT);
		lParams.weight = 1;
		tabViewHeader.setLayoutParams(lParams);
		// tabViewHeader.setGravity(Gravity.CENTER);
		tabViewHeader.setText(tabName);
		tabViewHeader.setClickable(true);
		tabViewHeader.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				TabViewHeader tabViewHeader = (TabViewHeader) v;
				if (tabViewHeader == null)
					return;
				int index = headerList.indexOf(v);
				if (index < mTabCount)
					setCurView(index);
			}
		});
		return tabViewHeader;
	}

	private View getSplitFlag() {
		View view = new View(mContext);
		LinearLayout.LayoutParams lParams = new LinearLayout.LayoutParams(1, 30);
		lParams.topMargin = 20;
		// lParams.bottomMargin = 10;
		view.setLayoutParams(lParams);

		view.setBackgroundColor(mContext.getResources().getColor(R.color.com_lightgray_color));
		return view;
	}

	private ImageView getFootDot() {
		ImageView view = new ImageView(mContext);
		LinearLayout.LayoutParams lParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
				LinearLayout.LayoutParams.WRAP_CONTENT);
		lParams.gravity = Gravity.CENTER;
		lParams.leftMargin = 5;
		lParams.rightMargin = 5;
		view.setLayoutParams(lParams);
		view.setImageResource(R.drawable.tab_change);
		return view;
	}

	public void addTabView(String tabName, View contentView) {
		// 如果不是第一个tab页，先添加分割符
		if (mTabCount != 0) {
			View splitView = getSplitFlag();
			splitList.add(splitView);
			mHeaderLayout.addView(splitView);
		}

		// 添加tabheader
		TabViewHeader headerView = getNewHeader(tabName);
		headerList.add(headerView);
		mHeaderLayout.addView(headerView);

		// 添加底部dot
		ImageView dotView = getFootDot();
		dotList.add(dotView);
		mFootLayout.addView(dotView);

		// 添加内容view
		mViews.add(contentView);
		// mViewPager.addView(contentView);

		mTabCount++;
	}

	public View getMainView() {
		mViewPager.setAdapter(new ComViewPagerAdapter(mViews));
		mViewPager.setOnPageChangeListener(pageChangeListener);
		setCurView(0);
		return mMainView;
	}

	public void setCurView(int index) {
		if (index >= mTabCount)
			return;
		changeAllHeaderStyle(index);
		mViewPager.setCurrentItem(index);
	}

	private void changeAllHeaderStyle(int index) {
		for (int i = 0; i < mTabCount; i++) {
			if (i == index)
				changeHeaderStyle(index, true);
			else {
				changeHeaderStyle(i, false);
			}
		}
	}

	private void changeHeaderStyle(int index, boolean checkd) {
		if (index >= mTabCount)
			return;
		TabViewHeader header = headerList.get(index);
		header.setChecked(checkd);
		dotList.get(index).setEnabled(!checkd);
	}

	ViewPager.OnPageChangeListener pageChangeListener = new ViewPager.OnPageChangeListener() {
		@Override
		public void onPageSelected(int arg0) {
			changeAllHeaderStyle(arg0);
		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {

		}

		@Override
		public void onPageScrollStateChanged(int arg0) {

		}
	};
}
