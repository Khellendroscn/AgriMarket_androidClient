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
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.MenuItem;
import com.lyj.agriculture.R;
import com.lyj.agriculture.http.HttpClientUtils;
import com.lyj.agriculture.http.IPPort;
import com.lyj.agriculture.model.ExpertAdvisor;
import com.lyj.agriculture.util.Const;
import com.lyj.agriculture.util.LogUtil;
import com.lyj.agriculture.util.Util;

public class FankuiActivity extends SherlockActivity {
	private TextView tv_yijian, tv_lianxifangshi, tv_youxiang;
	private Button btn_ok;
	private Context mContext = this;
	private Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			if (msg.what == 0) {
				AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
				builder.setMessage("谢谢你提供宝贵的建议，我们会尽快做出答复。");
				builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
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
		setContentView(R.layout.fankui);
		init();
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		setTitle("你的意见");
	}

	private void init() {
		// 下载xml,并解析

		tv_yijian = (TextView) findViewById(R.id.fankui_yijian);
		tv_lianxifangshi = (TextView) findViewById(R.id.fankui_lianxifangshi);
		tv_youxiang = (TextView) findViewById(R.id.fankui_youxiang);
		btn_ok = (Button) findViewById(R.id.fankui_btn);

		btn_ok.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if (tv_youxiang.getText().toString().equals("")) {
					Toast.makeText(mContext, "请填写你宝贵的建议", Toast.LENGTH_SHORT).show();
				} else {
					new Thread(new Runnable() {
						public void run() {
							ExpertAdvisor propose = new ExpertAdvisor();
							propose.setCustomerID(Const.customerID);
							propose.setEmail(tv_youxiang.getText().toString());
							propose.setQuestion(tv_yijian.getText().toString());
							propose.setTelNumber(tv_lianxifangshi.getText().toString());
							ArrayList<ExpertAdvisor> list = new ArrayList<ExpertAdvisor>();
							list.add(propose);
							String xmlStr = Util.produceXmlPropose(list);
							HttpClientUtils httpClientUtils = new HttpClientUtils();
							if (httpClientUtils.sendPOSTRequestboolean(IPPort.URL_PORPSEADD, xmlStr)) {
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
