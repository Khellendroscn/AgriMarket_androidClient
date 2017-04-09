package com.lyj.agriculture.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lyj.agriculture.R;

public class TabViewHeader extends LinearLayout {
	private Context mContext;
	private TextView mHeader;
	private TextView mGreenBar;
	
	public TabViewHeader(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.mContext = context;
		inflateView();
	}
	
	private void inflateView() {
		LayoutInflater mInflater = LayoutInflater.from(mContext);
		View layout = mInflater.inflate(R.layout.tabview_tabheader, null);
		
		LinearLayout.LayoutParams mLayoutParams = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.LayoutParams.WRAP_CONTENT);
		addView(layout, mLayoutParams);
		
		mHeader = (TextView)layout.findViewById(R.id.erl_tabview_tabheader_textview);
		mGreenBar = (TextView)layout.findViewById(R.id.erl_tabview_tabheader_flagbar);
	}
	
	public void setText(String text){
		mHeader.setText(text);
	}
	
	public void setChecked(boolean isChecked){
		if(mHeader==null || mGreenBar==null)
			return;
		
		if(isChecked){
			mHeader.setTextColor(getResources().getColor(R.color.com_green_color));
			mGreenBar.setBackgroundColor(getResources().getColor(R.color.com_green_color));
		}
		else {
			mHeader.setTextColor(getResources().getColor(R.color.com_gray_color));
			mGreenBar.setBackgroundColor(getResources().getColor(R.color.form_divline_color));
		}
	}
}
