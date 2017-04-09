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
import com.lyj.agriculture.model.FClassification;

public class AgriSellAdapter extends BaseAdapter {

	private List<FClassification> list_FClassification = new ArrayList<FClassification>();
	private LayoutInflater mInflater = null;
	private Context mContext;
	private ImageLoader imageLoader;

	public AgriSellAdapter(Context content, List<FClassification> list_FClassification) {
		this.list_FClassification = list_FClassification;
		this.mContext = content;
		mInflater = LayoutInflater.from(mContext);
		imageLoader = new ImageLoader(mContext);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list_FClassification.size();
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
		AgriSellViewHolder holder;
		if (convertView == null) {
			holder = new AgriSellViewHolder();
			convertView = mInflater.inflate(R.layout.agri_sell_item, null);
			holder.fClassificationImage = (ImageView) convertView.findViewById(R.id.agri_sell_image);
			holder.fClassificationName = (TextView) convertView.findViewById(R.id.agri_sell_fclassification);
			holder.fClassificationDescription = (TextView) convertView.findViewById(R.id.agri_sell_fclassificationdes);
			convertView.setTag(holder);
		} else {
			holder = (AgriSellViewHolder) convertView.getTag();
		}
		holder.fClassificationName.setText(list_FClassification.get(position).getFClassificationName());
		holder.fClassificationDescription.setText(list_FClassification.get(position).getFClassificationDescription());
		imageLoader.DisplayImage(list_FClassification.get(position).getFClassificationImage(),
				holder.fClassificationImage);

		// holder.fClassificationImage.setImageBitmap();
		return convertView;
	}

	static class AgriSellViewHolder {

		ImageView fClassificationImage;
		TextView fClassificationName;
		TextView fClassificationDescription;

	}

}
