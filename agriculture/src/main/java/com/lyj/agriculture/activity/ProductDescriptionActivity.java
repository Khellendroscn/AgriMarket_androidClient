package com.lyj.agriculture.activity;

import android.content.Context;
import android.os.Bundle;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.MenuItem;
import com.lyj.agriculture.R;
import com.lyj.agriculture.util.Const;
import com.lyj.agriculture.util.LogUtil;

public class ProductDescriptionActivity extends SherlockActivity {
	private TextView productName, productDes;
	private Context mContext = this;

	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.product_descrition);
		init();
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		setTitle("商品详细信息");
	}

	private void init() {
		productName = (TextView) findViewById(R.id.product_des_name);
		productDes = (TextView) findViewById(R.id.product_des_des);
		productName.setText(Const.productDetail.getProductName());
		productDes.setText(Const.productDetail.getProductDescriptionString());
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		if (item.getItemId() == android.R.id.home) {
			onBackPressed();
		}
		return super.onOptionsItemSelected(item);
	}

}
