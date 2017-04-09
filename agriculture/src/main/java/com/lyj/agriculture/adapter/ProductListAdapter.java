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
import com.lyj.agriculture.model.ProductOutline;

public class ProductListAdapter extends BaseAdapter {

	private List<ProductOutline> list_ProductOutline = new ArrayList<ProductOutline>();
	private LayoutInflater mInflater = null;
	private Context mContext;
	private ImageLoader imageLoader;

	public ProductListAdapter(Context content, List<ProductOutline> list_ProductOutline) {
		this.list_ProductOutline = list_ProductOutline;
		this.mContext = content;
		mInflater = LayoutInflater.from(mContext);
		imageLoader = new ImageLoader(mContext);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list_ProductOutline.size();
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
		ProductViewHolder holder;
		if (convertView == null) {
			holder = new ProductViewHolder();
			convertView = mInflater.inflate(R.layout.product_item, null);
			holder.productImage = (ImageView) convertView.findViewById(R.id.product_image);
			holder.productName = (TextView) convertView.findViewById(R.id.product_name);
			holder.productPrice = (TextView) convertView.findViewById(R.id.product_price);
			holder.productSellCount = (TextView) convertView.findViewById(R.id.product_sell_count);
			convertView.setTag(holder);
		} else {
			holder = (ProductViewHolder) convertView.getTag();
		}
		holder.productName.setText(list_ProductOutline.get(position).getProductName());
		holder.productPrice.setText("价格：￥"+list_ProductOutline.get(position).getProductPrice());
		holder.productSellCount.setText("最近售出:"+list_ProductOutline.get(position).getRecentSellCount()+"");
		imageLoader.DisplayImage(list_ProductOutline.get(position).getProductImage(), holder.productImage);

		return convertView;
	}

	static class ProductViewHolder {
		ImageView productImage;
		TextView productName;
		TextView productPrice;
		TextView productSellCount;
	}

}
