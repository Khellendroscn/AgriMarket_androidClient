package com.lyj.agriculture.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.lyj.agriculture.R;
import com.lyj.agriculture.model.ShoppingCartItem;
import com.lyj.agriculture.util.Util;

public class OrderAdapter extends BaseAdapter {

	private List<ShoppingCartItem> list_shoppingCartItem = new ArrayList<ShoppingCartItem>();
	private LayoutInflater mInflater = null;
	private Context mContext;

	public OrderAdapter(Context content, List<ShoppingCartItem> list_shoppingCartItem) {
		this.list_shoppingCartItem = list_shoppingCartItem;
		this.mContext = content;
		mInflater = LayoutInflater.from(mContext);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list_shoppingCartItem.size();
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
		OrderViewHolder holder;
		if (convertView == null) {
			holder = new OrderViewHolder();
			convertView = mInflater.inflate(R.layout.order_item, null);
			holder.orderName = (TextView) convertView.findViewById(R.id.order_item_name);
			holder.orderPeice = (TextView) convertView.findViewById(R.id.order_item_peice);
			holder.orderCount = (TextView) convertView.findViewById(R.id.order_item_count);
			holder.orderFarm = (TextView) convertView.findViewById(R.id.order_item_farm);
			holder.orderMoney = (TextView) convertView.findViewById(R.id.order_item_money);
			convertView.setTag(holder);
		} else {
			holder = (OrderViewHolder) convertView.getTag();
		}
		holder.orderName.setText(list_shoppingCartItem.get(position).getProductName());
		holder.orderPeice.setText("单价：￥"+list_shoppingCartItem.get(position).getProductPrice());
		holder.orderCount.setText("数量："+list_shoppingCartItem.get(position).getProductCount());
		holder.orderFarm.setText("店铺："+list_shoppingCartItem.get(position).getFarmName());
		holder.orderMoney.setText("小计：￥"+Util.DecimalFormat(list_shoppingCartItem.get(position).getProductPrice()*list_shoppingCartItem.get(position).getProductCount(),1));
		
		return convertView;
	}

	static class OrderViewHolder {

		TextView orderName;
		TextView orderPeice;
		TextView orderCount;
		TextView orderFarm;
		TextView orderMoney;

	}

}
