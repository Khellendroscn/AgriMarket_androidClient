package com.lyj.agriculture.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.MenuItem;
import com.lyj.agriculture.R;
import com.lyj.agriculture.adapter.AboutMeOrderAdapter;
import com.lyj.agriculture.adapter.OrderAdapter;
import com.lyj.agriculture.http.AsyncTaskWithDelayDlg;
import com.lyj.agriculture.http.HttpClientUtils;
import com.lyj.agriculture.http.IPPort;
import com.lyj.agriculture.model.AddressItem;
import com.lyj.agriculture.model.OrderInsert;
import com.lyj.agriculture.model.OrderInsertItem;
import com.lyj.agriculture.model.OrderOutline;
import com.lyj.agriculture.model.ShoppingCartItem;
import com.lyj.agriculture.model.key;
import com.lyj.agriculture.util.Const;
import com.lyj.agriculture.util.LogUtil;
import com.lyj.agriculture.util.Util;
import com.lyj.agriculture.xmlparse.SAXParserContentHandler;

public class AboutMeOrderActivity extends SherlockActivity {
	private Context mContext = this;
	private List<Object> list;
	private OrderOutline orderOutline;
	private List<OrderOutline> list_orderOutline;
	private AboutMeOrderAdapter aboutMeOrderAdapter;
	private ListView listView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.person_order_all_list);
		init();
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		setTitle("全部订单");
	}

	private void init() {
		list = new ArrayList<Object>();
		list_orderOutline = new ArrayList<OrderOutline>();
		listView = (ListView) findViewById(R.id.person_order_all_list);
		// 下载xml,并解析
		WebAsyncTask webAsyncTask = new WebAsyncTask(mContext);
		webAsyncTask.execute();

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

	// 异步下载网络xml数据
	private class WebAsyncTask extends AsyncTaskWithDelayDlg<Void, Void, String> {
		public WebAsyncTask(Context context) {
			super(context);
		}

		@Override
		protected String doInBackground2(Void... params) {
			try {
				HttpClientUtils httpClientUtils = new HttpClientUtils();
				return httpClientUtils.getUrlContext(mContext, IPPort.URL_ORDEROUTLINE + Const.customerID);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute2(String result) {
			if (result == null) {
				Toast.makeText(mContext, "连接服务器异常", Toast.LENGTH_SHORT).show();
			} else {
					SAXParserContentHandler mSaxParserContentHandler = new SAXParserContentHandler();
					try {
						list = mSaxParserContentHandler.parseReadXml(new OrderOutline(), result);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					orderOutline = new OrderOutline();
					for (Object object : list) {
						orderOutline = (OrderOutline) object;
						list_orderOutline.add(orderOutline);
					}
					aboutMeOrderAdapter = new AboutMeOrderAdapter(mContext, list_orderOutline);
					listView.setAdapter(aboutMeOrderAdapter);
			}
		}
	}

}
