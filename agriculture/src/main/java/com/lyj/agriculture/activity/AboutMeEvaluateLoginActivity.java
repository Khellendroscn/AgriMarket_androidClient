package com.lyj.agriculture.activity;

import java.util.ArrayList;

import android.app.AlertDialog;
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
import com.lyj.agriculture.model.CustomerConfirm;
import com.lyj.agriculture.model.CustomerRequest;
import com.lyj.agriculture.util.Const;
import com.lyj.agriculture.util.Util;
import com.lyj.agriculture.xmlparse.SAXParserContentHandler;

public class AboutMeEvaluateLoginActivity extends SherlockActivity {
	private Context mContext = this;
	private EditText et_name, et_password;
	private Button login;
	private TextView sign;
	private String name, password;
	private CustomerRequest customerRequest;

	private Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			if (msg.what == 0) {
				// 成功
				Util.saveLoginInfo(mContext, name, password);
				Const.customerID = customerRequest.getCustomerID();
				Const.customerName = customerRequest.getCustomerName();
				Toast.makeText(mContext, "登录成功！请继续操作", Toast.LENGTH_SHORT).show();
				setResult(100);
				//Util.gotoActivityNotFinish(mContext, ProductDetailActivity.class);
				AboutMeFragment.mHandler.sendEmptyMessage(0);
				finish();
			} else {
				Toast.makeText(mContext, "登录失败！请检查用户名或密码是否正确", Toast.LENGTH_SHORT).show();
			}
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		init();
		setTitle("用户登录");
	}

	private void init() {
		et_name = (EditText) findViewById(R.id.editText1);
		et_password = (EditText) findViewById(R.id.editText2);
		login = (Button) findViewById(R.id.buuton_login);
		sign = (TextView) findViewById(R.id.login_sign);

		name = Util.getLoginName(mContext);
		password = Util.getLoginPassword(mContext);
		et_name.setText(name);
		et_password.setText(password);
		sign.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Util.gotoActivityNotFinish(mContext, AboutMeEvaluateSignActivity.class);
				finish();
			}
		});

		login.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				name = et_name.getText().toString();
				password = et_password.getText().toString();
				if (name.equals("")) {
					Toast.makeText(mContext, "用户名不能为空", Toast.LENGTH_SHORT).show();

				} else if (password.equals("")) {
					Toast.makeText(mContext, "密码不能为空", Toast.LENGTH_SHORT).show();
				} else {
					Login();
				}
			}
		});
	}

	private void Login() {
		new Thread(new Runnable() {
			public void run() {
				ArrayList<CustomerConfirm> list_customerConfirm = new ArrayList<CustomerConfirm>();
				CustomerConfirm customerConfirm = new CustomerConfirm();
				customerConfirm.setCustomerName(name);
				customerConfirm.setPassword(password);
				list_customerConfirm.add(customerConfirm);
				String xmlString = Util.produceXmlCustomerConfirm(list_customerConfirm);

				HttpClientUtils httpClientUtils = new HttpClientUtils();
				String xmlResult = httpClientUtils.sendPOSTRequesString(IPPort.URL_CUSTOMERCONFIRM, xmlString);
				try {
					customerRequest = new CustomerRequest();
					SAXParserContentHandler mSaxParserContentHandler = new SAXParserContentHandler();
					customerRequest = (CustomerRequest) mSaxParserContentHandler.parseReadXmlObject(
							new CustomerRequest(), xmlResult);
					if (customerRequest.getCustomerID() == 0 || customerRequest.getCustomerName() == null) {
						mHandler.sendEmptyMessage(1);// 不成功
					} else {
						mHandler.sendEmptyMessage(0);
					}
				} catch (Exception e) {
					e.printStackTrace();
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
