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
import com.lyj.agriculture.model.NewOutline;
import com.lyj.agriculture.util.LogUtil;

public class NewsAdapter extends BaseAdapter {

	private List<NewOutline> list_newOutline = new ArrayList<NewOutline>();
	private LayoutInflater mInflater = null;
	private Context mContext;
	private ImageLoader imageLoader;

	public NewsAdapter(Context content, List<NewOutline> list_newOutline) {
		this.list_newOutline = list_newOutline;
		this.mContext = content;
		mInflater = LayoutInflater.from(mContext);
		imageLoader = new ImageLoader(mContext);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list_newOutline.size();
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
		NewsViewHolder holder;
		if (convertView == null) {
			holder = new NewsViewHolder();
			convertView = mInflater.inflate(R.layout.news_item, null);
			holder.newsImage = (ImageView) convertView.findViewById(R.id.news_item_image);
			holder.newsDate = (TextView) convertView.findViewById(R.id.news_item_time);
			holder.newsName = (TextView) convertView.findViewById(R.id.news_item_name);
			convertView.setTag(holder);
		} else {
			holder = (NewsViewHolder) convertView.getTag();
		}
		holder.newsName.setText(list_newOutline.get(position).getNewName());
		holder.newsDate.setText(list_newOutline.get(position).getNewDate());
		if (position > 0) {
			if (list_newOutline.get(position).getNewDate().equals(list_newOutline.get(position - 1).getNewDate()))
				holder.newsDate.setVisibility(View.INVISIBLE);
		}
		imageLoader.DisplayImage(list_newOutline.get(position).getNewImage(), holder.newsImage);

		return convertView;
	}

	static class NewsViewHolder {

		ImageView newsImage;
		TextView newsName;
		TextView newsDate;

	}

}
