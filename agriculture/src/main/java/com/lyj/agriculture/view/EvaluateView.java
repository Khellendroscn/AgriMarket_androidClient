package com.lyj.agriculture.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RatingBar.OnRatingBarChangeListener;

import com.lyj.agriculture.R;

public class EvaluateView extends LinearLayout {

	public EvaluateView(Context context) {
		super(context);
		mContext = context;
		inflateView();
	}

	public EvaluateView(Context context, AttributeSet attrs) {
		super(context, attrs);
		mContext = context;
		inflateView();
	}

	private Context mContext;
	private RatingBar evaluate_ratingBar;
	private EditText evaluate_et;
	private int ratingInt;

	private void inflateView() {
		LayoutInflater mInflater = LayoutInflater.from(mContext);
		View layout = mInflater.inflate(R.layout.evaluate, null);
		evaluate_ratingBar = (RatingBar) layout.findViewById(R.id.evaluate_rb);
		evaluate_et = (EditText) layout.findViewById(R.id.evaluate_et);
		LinearLayout.LayoutParams mLayoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.LayoutParams.WRAP_CONTENT);

		evaluate_ratingBar.setOnRatingBarChangeListener(new OnRatingBarChangeListener() {

			@Override
			public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
				// TODO Auto-generated method stub
				ratingInt = (int) rating;
			}
		});

		addView(layout, mLayoutParams);
	}

	public int getRatingBar() {
		return ratingInt;
	}

	public String getEdit() {
		return evaluate_et.getText().toString();
	}

	public void setEdit(String s) {
		evaluate_et.setText(s.toString());
	}

	public void setRatingBar(int i) {
		evaluate_ratingBar.setProgress(i);
	}

}
