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
import com.lyj.agriculture.model.AddressItem;
import com.lyj.agriculture.util.Const;

public class OrderAddressAdapter extends BaseAdapter {

	private List<AddressItem> list_addressItem = new ArrayList<AddressItem>();
	private LayoutInflater mInflater = null;
	private Context mContext;
	private TextView name;
	private TextView phone;
	private TextView address;
	private ImageView btn;


	public OrderAddressAdapter(Context content, List<AddressItem> list_addressItem) {
		this.list_addressItem = list_addressItem;
		this.mContext = content;
		mInflater = LayoutInflater.from(mContext);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list_addressItem.size();
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
		convertView = mInflater.inflate(R.layout.order_address_item, null);
		name = (TextView) convertView.findViewById(R.id.about_me_item_name);
		phone = (TextView) convertView.findViewById(R.id.about_me_item_phone);
		address = (TextView) convertView.findViewById(R.id.about_me_item_address);
		btn = (ImageView) convertView.findViewById(R.id.select);
		if (list_addressItem.get(position).getAddressID() == Const.orderAddressItem.getAddressID()) {
			btn.setVisibility(View.VISIBLE);
		}

		name.setText("姓名：" + list_addressItem.get(position).getName());
		phone.setText("电话：" + list_addressItem.get(position).getTelNumber());
		address.setText("地址：" + list_addressItem.get(position).getAddress());

		return convertView;
	}
}
