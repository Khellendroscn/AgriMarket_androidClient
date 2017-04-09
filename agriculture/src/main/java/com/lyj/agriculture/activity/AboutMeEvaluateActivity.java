package com.lyj.agriculture.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.MenuItem;
import com.lyj.agriculture.R;
import com.lyj.agriculture.adapter.AboutMeEvaluateAdapter;
import com.lyj.agriculture.http.AsyncTaskWithDelayDlg;
import com.lyj.agriculture.http.HttpClientUtils;
import com.lyj.agriculture.http.IPPort;
import com.lyj.agriculture.model.MyProductEvaluate;
import com.lyj.agriculture.model.OrderEvaluateAdd;
import com.lyj.agriculture.util.Const;
import com.lyj.agriculture.util.Util;
import com.lyj.agriculture.view.EvaluateView;
import com.lyj.agriculture.xmlparse.SAXParserContentHandler;

public class AboutMeEvaluateActivity extends SherlockActivity {
	private Context mContext = this;
	private List<Object> list;
	private MyProductEvaluate myProductEvaluate;
	private List<MyProductEvaluate> list_myProductEvaluate;
	private AboutMeEvaluateAdapter aboutMeEvaluateAdapter;
	private ListView listView;
	private String evaluateString;
	private int rating;
	private int orderID;
	private int productID;

	private Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			if (msg.what == 0) {
				AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
				builder.setMessage("评价成功，继续操作......");
				builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						WebAsyncTask webAsyncTask = new WebAsyncTask(mContext);
						webAsyncTask.execute();
						aboutMeEvaluateAdapter.notifyDataSetChanged();
					}
				});
				builder.create().show();
			} else {
				AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
				builder.setMessage("评价失败......");
				builder.setPositiveButton("确定", null);
				builder.create().show();
			}
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.person_order_all_list);
		init();
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		setTitle("订单评价");
	}

	private void init() {
		list = new ArrayList<Object>();
		list_myProductEvaluate = new ArrayList<MyProductEvaluate>();
		listView = (ListView) findViewById(R.id.person_order_all_list);
		// 下载xml,并解析
		WebAsyncTask webAsyncTask = new WebAsyncTask(mContext);
		webAsyncTask.execute();

		listView.setOnItemClickListener(new MyOnItemClickListener());
	}

	private class MyOnItemClickListener implements OnItemClickListener {
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			orderID = list_myProductEvaluate.get(position).getOrderID();
			productID = list_myProductEvaluate.get(position).getProductID();
			if (list_myProductEvaluate.get(position).getEvaluateOrNot().equals("0")) {
				// 评价
				final EvaluateView evaluateView = new EvaluateView(mContext);
				AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
				builder.setTitle("请评价商品");
				builder.setView(evaluateView);
				builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						evaluateString = evaluateView.getEdit();
						rating = evaluateView.getRatingBar();
						new Thread(new Runnable() {
							public void run() {
								OrderEvaluateAdd orderEvaluateAdd = new OrderEvaluateAdd();
								orderEvaluateAdd.setOrderEvaluateDescription(evaluateString);
								orderEvaluateAdd.setOrderID(orderID);
								orderEvaluateAdd.setProductID(productID);
								orderEvaluateAdd.setStar(rating);
								ArrayList<OrderEvaluateAdd> list = new ArrayList<OrderEvaluateAdd>();
								list.add(orderEvaluateAdd);
								String xmlStr = Util.produceXmlOrderEvaluateAdd(list);
								HttpClientUtils httpClientUtils = new HttpClientUtils();
								if (httpClientUtils.sendPOSTRequestboolean(IPPort.URL_ORDEREVALUATEADD, xmlStr)) {
									mHandler.sendEmptyMessage(0);
								} else {
									mHandler.sendEmptyMessage(1);
								}
							}
						}).start();

					}
				});
				builder.setNegativeButton("返回", null);
				builder.create().show();

			} else {

			}
		}
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
				return httpClientUtils.getUrlContext(mContext, IPPort.URL_MYPRODUCTOEDEREVAUATE + Const.customerID);
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
					list = mSaxParserContentHandler.parseReadXml(new MyProductEvaluate(), result);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				list_myProductEvaluate.clear();
				myProductEvaluate = new MyProductEvaluate();
				for (Object object : list) {
					myProductEvaluate = (MyProductEvaluate) object;
					list_myProductEvaluate.add(myProductEvaluate);
				}
				aboutMeEvaluateAdapter = new AboutMeEvaluateAdapter(mContext, list_myProductEvaluate);
				listView.setAdapter(aboutMeEvaluateAdapter);
			}
		}
	}

}
