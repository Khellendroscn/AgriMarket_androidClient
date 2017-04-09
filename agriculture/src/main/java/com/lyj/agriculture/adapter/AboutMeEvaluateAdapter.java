package com.lyj.agriculture.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.lyj.agriculture.R;
import com.lyj.agriculture.http.ImageLoader;
import com.lyj.agriculture.model.MyProductEvaluate;

public class AboutMeEvaluateAdapter extends BaseAdapter {

	private List<MyProductEvaluate> list_myProductEvaluate = new ArrayList<MyProductEvaluate>();
	private LayoutInflater mInflater = null;
	private Context mContext;
	private ImageLoader imageLoader;
	private TextView orderNum;
	private TextView orderName;
	private ImageView orderImage;
	private TextView orderAllMoney;

	public AboutMeEvaluateAdapter(Context content, List<MyProductEvaluate> list_myProductEvaluate) {
		this.list_myProductEvaluate = list_myProductEvaluate;
		this.mContext = content;
		mInflater = LayoutInflater.from(mContext);
		imageLoader = new ImageLoader(mContext);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list_myProductEvaluate.size();
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
		convertView = mInflater.inflate(R.layout.person_order_all_item, null);
		orderAllMoney = (TextView) convertView.findViewById(R.id.person_order_all_money);
		orderNum = (TextView) convertView.findViewById(R.id.person_order_all_num);
		orderName = (TextView) convertView.findViewById(R.id.person_order_all_name);
		orderImage = (ImageView) convertView.findViewById(R.id.person_order_all_image);
		if (position > 0) {
			if (list_myProductEvaluate.get(position - 1).getOrderID() == list_myProductEvaluate.get(position)
					.getOrderID())
				orderNum.setVisibility(View.GONE);
		}
		orderNum.setText("订单" + list_myProductEvaluate.get(position).getOrderID() + ":");
		orderName.setText(list_myProductEvaluate.get(position).getProductName());
		if (list_myProductEvaluate.get(position).getEvaluateOrNot().equals("1")) {
			orderAllMoney.setText("已评价");
			orderAllMoney.setTextColor(mContext.getResources().getColor(R.color.com_lightgray_color));

		} else {
			orderAllMoney.setText("去评价");
		}

		imageLoader.DisplayImage(list_myProductEvaluate.get(position).getProductImage(), orderImage);
		return convertView;
	}
}
