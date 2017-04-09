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
import com.lyj.agriculture.model.Exhibition;
import com.lyj.agriculture.model.NewOutline;

public class CharAgriAdapter extends BaseAdapter {

	private List<Exhibition> list_exhibition = new ArrayList<Exhibition>();
	private LayoutInflater mInflater = null;
	private Context mContext;
	private ImageLoader imageLoader;

	public CharAgriAdapter(Context content, List<Exhibition> list_exhibition) {
		this.list_exhibition = list_exhibition;
		this.mContext = content;
		mInflater = LayoutInflater.from(mContext);
		imageLoader = new ImageLoader(mContext);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list_exhibition.size();
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
		ExhibiViewHolder holder;
		if (convertView == null) {
			holder = new ExhibiViewHolder();
			convertView = mInflater.inflate(R.layout.news_item, null);
			holder.exhibiImage = (ImageView) convertView.findViewById(R.id.news_item_image);
			holder.exhibiName = (TextView) convertView.findViewById(R.id.news_item_time);
			holder.exhibiDate = (TextView) convertView.findViewById(R.id.news_item_name);
			convertView.setTag(holder);
		} else {
			holder = (ExhibiViewHolder) convertView.getTag();
		}
		holder.exhibiName.setText(list_exhibition.get(position).getProductName());
		holder.exhibiDate.setVisibility(View.INVISIBLE);
		imageLoader.DisplayImage(list_exhibition.get(position).getProductImage(), holder.exhibiImage);

		return convertView;
	}

	static class ExhibiViewHolder {

		ImageView exhibiImage;
		TextView exhibiName;
		TextView exhibiDate;
	}

}
