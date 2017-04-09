package com.lyj.agriculture.activity;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.MenuItem;
import com.lyj.agriculture.R;
import com.lyj.agriculture.http.HttpClientUtils;
import com.lyj.agriculture.http.IPPort;
import com.lyj.agriculture.model.AddressItem;
import com.lyj.agriculture.util.Const;
import com.lyj.agriculture.util.Util;

public class AddressAddActivity extends SherlockActivity {

	private EditText et_name, et_phone, et_address;
	private Button btn_ok;
	private Context mContext = this;

	private Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			if (msg.what == 0) {
				AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
				builder.setMessage("添加地址成功，请继续操作");
				builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						Util.gotoActivityNotFinish(mContext, AboutMeAddressActivity.class);
						finish();
					}
				});
				builder.create().show();
			}
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.order_address);
		init();
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		setTitle("添加新地址");
	}

	private void init() {

		et_name = (EditText) findViewById(R.id.order_address_item_name);
		et_phone = (EditText) findViewById(R.id.order_address_item_phone);
		et_address = (EditText) findViewById(R.id.order_address_item_address);
		btn_ok = (Button) findViewById(R.id.order_address_ok);
		btn_ok.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if (et_name.getText().toString().equals("") || et_phone.getText().toString().equals("")
						|| et_address.getText().toString().equals("")) {
					Toast.makeText(mContext, "请检查是否有漏填信息", Toast.LENGTH_SHORT).show();
				} else {
					new Thread(new Runnable() {
						public void run() {
							AddressItem addressItem = new AddressItem();
							addressItem.setAddress( et_address.getText().toString());
							addressItem.setCustomerID(Const.customerID);
							addressItem.setName(et_name.getText().toString());
							addressItem.setTelNumber(et_phone.getText().toString());
							ArrayList<AddressItem> list = new ArrayList<AddressItem>();
							list.add(addressItem);
							String xmlStr = Util.produceXmlAddressItem(list);
							HttpClientUtils httpClientUtils = new HttpClientUtils();
							if (httpClientUtils.sendPOSTRequestboolean(IPPort.URL_ADDRESSADD, xmlStr)) {
								mHandler.sendEmptyMessage(0);
							} else {
								mHandler.sendEmptyMessage(1);
							}
						}
					}).start();
				}
			}
		});
	}

	public boolean onCreateOptionsMenu(com.actionbarsherlock.view.Menu menu) {

		return super.onCreateOptionsMenu(menu);
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
