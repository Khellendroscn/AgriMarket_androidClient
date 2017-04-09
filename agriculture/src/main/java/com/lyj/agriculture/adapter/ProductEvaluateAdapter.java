package com.lyj.agriculture.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RatingBar;
import android.widget.TextView;

import com.lyj.agriculture.R;
import com.lyj.agriculture.model.OrderEvaluate;

public class ProductEvaluateAdapter extends BaseAdapter {
	private List<OrderEvaluate> list_orderEvaluate = new ArrayList<OrderEvaluate>();
	private LayoutInflater mInflater = null;
	private Context mContext;

	public ProductEvaluateAdapter(Context content, List<OrderEvaluate> list_orderEvaluate) {
		this.list_orderEvaluate = list_orderEvaluate;
		this.mContext = content;
		mInflater = LayoutInflater.from(mContext);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list_orderEvaluate.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ProEvaViewHolder holder;
		if (convertView == null) {
			holder = new ProEvaViewHolder();
			convertView = mInflater.inflate(R.layout.product_evaluate_item, null);
			holder.proEvabar = (RatingBar) convertView.findViewById(R.id.product_evaluate_bar);
			holder.proEvaNameTime = (TextView) convertView.findViewById(R.id.product_evaluate_name_time);
			holder.proEvaDec = (TextView) convertView.findViewById(R.id.product_evaluate_dec);
			holder.proBuyTime = (TextView) convertView.findViewById(R.id.product_evaluate_bug_time);

			convertView.setTag(holder);
		} else {
			holder = (ProEvaViewHolder) convertView.getTag();
		}
		holder.proEvabar.setProgress(list_orderEvaluate.get(position).getStar());
		holder.proEvaNameTime.setText(list_orderEvaluate.get(position).getCustomerName() + ":"
				+ list_orderEvaluate.get(position).getOrderEvaluateDateCreate());

		holder.proEvaDec.setText("评价："+list_orderEvaluate.get(position).getOrderEvaluateDescription());
		holder.proBuyTime.setText("购买日期："+list_orderEvaluate.get(position).getDateCreate());

		// holder.fClassificationImage.setImageBitmap();
		return convertView;
	}

	static class ProEvaViewHolder {
		RatingBar proEvabar;
		TextView proEvaNameTime;
		TextView proEvaDec;
		TextView proBuyTime;
	}

}
