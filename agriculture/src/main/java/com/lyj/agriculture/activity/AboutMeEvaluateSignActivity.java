package com.lyj.agriculture.activity;

import java.util.ArrayList;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.MenuItem;
import com.lyj.agriculture.R;
import com.lyj.agriculture.fragment.AboutMeFragment;
import com.lyj.agriculture.http.HttpClientUtils;
import com.lyj.agriculture.http.IPPort;
import com.lyj.agriculture.model.CustomerAdd;
import com.lyj.agriculture.model.CustomerConfirm;
import com.lyj.agriculture.model.CustomerRequest;
import com.lyj.agriculture.util.Const;
import com.lyj.agriculture.util.Util;
import com.lyj.agriculture.xmlparse.SAXParserContentHandler;

public class AboutMeEvaluateSignActivity extends SherlockActivity {
	private Context mContext = this;
	private EditText et_name, et_password;
	private Button sign;
	private TextView login;
	private String name, password;

	private Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			if (msg.what == 0) {
				// 成功
				Toast.makeText(mContext, "注册成功！请继续操作", Toast.LENGTH_SHORT).show();
				Util.gotoActivityNotFinish(mContext, AboutMeEvaluateLoginActivity.class);
				finish();
			} else {
				Toast.makeText(mContext, "注册失败！", Toast.LENGTH_SHORT).show();
			}
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sign);
		init();
		setTitle("用户注册");
	}

	private void init() {
		et_name = (EditText) findViewById(R.id.editText1);
		et_password = (EditText) findViewById(R.id.editText2);
		sign = (Button) findViewById(R.id.buuton_login);
		login = (TextView) findViewById(R.id.login);
		login.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Util.gotoActivityNotFinish(mContext, AboutMeEvaluateLoginActivity.class);
				finish();
			}
		});

		sign.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				name = et_name.getText().toString();
				password = et_password.getText().toString();
				if (name.equals("")) {
					Toast.makeText(mContext, "用户名不能为空", Toast.LENGTH_SHORT).show();
				} else if (password.equals("")) {
					Toast.makeText(mContext, "密码不能为空", Toast.LENGTH_SHORT).show();
				} else {
					// Login();
					sign();
				}
			}
		});
	}

	private void sign() {
		new Thread(new Runnable() {
			public void run() {
				ArrayList<CustomerAdd> list_customerAdd = new ArrayList<CustomerAdd>();
				CustomerAdd customerAdd = new CustomerAdd();
				customerAdd.setCustomerName(name);
				customerAdd.setPassword(password);
				customerAdd.setEmail(null);
				list_customerAdd.add(customerAdd);
				String xmlString = Util.produceXmlCustomerAdd(list_customerAdd);

				HttpClientUtils httpClientUtils = new HttpClientUtils();
				if (httpClientUtils.sendPOSTRequestboolean(IPPort.URL_CUSTOMERADD, xmlString)) {
					mHandler.sendEmptyMessage(0);
				} else {
					mHandler.sendEmptyMessage(1);
				}

			}
		}).start();
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
