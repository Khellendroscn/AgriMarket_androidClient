package com.lyj.agriculture.activity;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
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
import com.lyj.agriculture.adapter.ProductEvaluateAdapter;
import com.lyj.agriculture.adapter.ShoppingCardAdapter;
import com.lyj.agriculture.http.AsyncTaskWithDelayDlg;
import com.lyj.agriculture.http.HttpClientUtils;
import com.lyj.agriculture.http.IPPort;
import com.lyj.agriculture.model.OrderEvaluate;
import com.lyj.agriculture.model.ShoppingCartItem;
import com.lyj.agriculture.util.Const;
import com.lyj.agriculture.util.Util;
import com.lyj.agriculture.xmlparse.SAXParserContentHandler;

public class ProductEvaluateActivity extends SherlockActivity {

	private Context mContext = this;
	private List<Object> list = null;

	private OrderEvaluate orderEvaluate;
	private List<OrderEvaluate> list_orderEvaluate;
	private ProductEvaluateAdapter productEvaluateAdapter;
	private ListView listView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.product_evaluate_list);
		listView = (ListView) findViewById(R.id.product_evaluate_list);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		setTitle("用户评价");
		init();
	}

	private void init() {
		list_orderEvaluate = new ArrayList<OrderEvaluate>();
		list = new ArrayList<Object>();

		// 下载xml,并解析
		WebAsyncTask webAsyncTask = new WebAsyncTask(mContext);
		webAsyncTask.execute();
	}

	@Override
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
				return httpClientUtils.getUrlContext(mContext, IPPort.URL_PRODUCTEVALUATE + Const.productID);
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
				try {
					SAXParserContentHandler mSaxParserContentHandler = new SAXParserContentHandler();
					list = mSaxParserContentHandler.parseReadXml(new OrderEvaluate(), result);
					orderEvaluate = new OrderEvaluate();
					for (Object object : list) {
						orderEvaluate = (OrderEvaluate) object;
						list_orderEvaluate.add(orderEvaluate);

					}
					productEvaluateAdapter = new ProductEvaluateAdapter(mContext, list_orderEvaluate);
					listView.setAdapter(productEvaluateAdapter);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

}
