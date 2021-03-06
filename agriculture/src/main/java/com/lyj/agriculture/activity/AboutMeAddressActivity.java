package com.lyj.agriculture.activity;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.MenuItem;
import com.lyj.agriculture.R;
import com.lyj.agriculture.adapter.AboutMeAddressAdapter;
import com.lyj.agriculture.http.AsyncTaskWithDelayDlg;
import com.lyj.agriculture.http.HttpClientUtils;
import com.lyj.agriculture.http.IPPort;
import com.lyj.agriculture.model.AddressItem;
import com.lyj.agriculture.util.Const;
import com.lyj.agriculture.util.Util;
import com.lyj.agriculture.xmlparse.SAXParserContentHandler;

public class AboutMeAddressActivity extends SherlockActivity {
	private Context mContext = this;
	private List<Object> list;
	private AddressItem addressItem;
	private List<AddressItem> list_addressItem;
	private AboutMeAddressAdapter aboutMeAddressAdapter;
	private ListView listView;

	/*
	 * private String evaluateString; private int rating; private int orderID;
	 * private int productID;
	 */

	/*
	 * private Handler mHandler = new Handler() { public void
	 * handleMessage(Message msg) { if (msg.what == 0) { AlertDialog.Builder
	 * builder = new AlertDialog.Builder(mContext);
	 * builder.setMessage("评价成功，继续操作......"); builder.setPositiveButton("确定",
	 * new DialogInterface.OnClickListener() { public void
	 * onClick(DialogInterface dialog, int which) { WebAsyncTask webAsyncTask =
	 * new WebAsyncTask(mContext); webAsyncTask.execute();
	 * aboutMeEvaluateAdapter.notifyDataSetChanged(); } });
	 * builder.create().show(); } else { AlertDialog.Builder builder = new
	 * AlertDialog.Builder(mContext); builder.setMessage("评价失败......");
	 * builder.setPositiveButton("确定", null); builder.create().show(); } }; };
	 */

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.aboutme_address_list);
		init();
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		setTitle("收货地址管理");
	}

	private void init() {
		list = new ArrayList<Object>();
		list_addressItem = new ArrayList<AddressItem>();
		listView = (ListView) findViewById(R.id.aboutme_address_list);
		// 下载xml,并解析
		WebAsyncTask webAsyncTask = new WebAsyncTask(mContext);
		webAsyncTask.execute();

		listView.setOnItemClickListener(new MyOnItemClickListener());
	}

	private class MyOnItemClickListener implements OnItemClickListener {
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			Const.addressItem = list_addressItem.get(position);
			Util.gotoActivityNotFinish(mContext, AddressModifyActivity.class);
			finish();
		}
	}

	public boolean onCreateOptionsMenu(com.actionbarsherlock.view.Menu menu) {
		MenuItem shoppingCard = menu.add(0, 0, 0, "添加新的地址");
		shoppingCard.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS | MenuItem.SHOW_AS_ACTION_WITH_TEXT);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		if (item.getItemId() == android.R.id.home) {
			onBackPressed();
		} else if (item.getItemId() == 0) {
			Util.gotoActivityNotFinish(mContext, AddressAddActivity.class);
			finish();
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
				return httpClientUtils.getUrlContext(mContext, IPPort.URL_ADDRESS + Const.customerID);
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
					list = mSaxParserContentHandler.parseReadXml(new AddressItem(), result);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				list_addressItem.clear();
				addressItem = new AddressItem();
				for (Object object : list) {
					addressItem = (AddressItem) object;
					list_addressItem.add(addressItem);
				}
				aboutMeAddressAdapter = new AboutMeAddressAdapter(mContext, list_addressItem);
				listView.setAdapter(aboutMeAddressAdapter);
			}
		}
	}

}
