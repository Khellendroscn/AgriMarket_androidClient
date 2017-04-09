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
import com.lyj.agriculture.model.OrderOutline;

public class AboutMeOrderAdapter extends BaseAdapter {

	private List<OrderOutline> list_orderOutline = new ArrayList<OrderOutline>();
	private LayoutInflater mInflater = null;
	private Context mContext;
	private ImageLoader imageLoader;
	private TextView orderNum;
	private TextView orderName;
	private TextView orderCount;
	private TextView orderFarm;
	private ImageView orderImage;
	private TextView orderAllMoney;

	public AboutMeOrderAdapter(Context content, List<OrderOutline> list_orderOutline) {
		this.list_orderOutline = list_orderOutline;
		this.mContext = content;
		mInflater = LayoutInflater.from(mContext);
		imageLoader = new ImageLoader(mContext);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list_orderOutline.size();
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
		convertView = mInflater.inflate(R.layout.person_order_all_item, null);
		orderAllMoney = (TextView) convertView.findViewById(R.id.person_order_all_money);
		orderNum = (TextView) convertView.findViewById(R.id.person_order_all_num);
		orderName = (TextView) convertView.findViewById(R.id.person_order_all_name);
		orderCount = (TextView) convertView.findViewById(R.id.person_order_all_count);
		orderFarm = (TextView) convertView.findViewById(R.id.person_order_all_farm);
		orderImage = (ImageView) convertView.findViewById(R.id.person_order_all_image);
		if (position > 0) {
			if (list_orderOutline.get(position - 1).getFarmID() == list_orderOutline.get(position).getFarmID())
				orderFarm.setVisibility(View.GONE);
		}
		orderFarm.setText("店铺："  + list_orderOutline.get(position).getFarmName() + ":");
		orderName.setText(list_orderOutline.get(position).getProductName());
		orderCount.setText("数量：" + list_orderOutline.get(position).getProductCount());
		orderNum.setText("订单编号:"+list_orderOutline.get(position).getOrderID());
		orderAllMoney.setText("小计：￥" + list_orderOutline.get(position).getSum() + "");
		imageLoader.DisplayImage(list_orderOutline.get(position).getProductImage(), orderImage);
		return convertView;
	}
}
