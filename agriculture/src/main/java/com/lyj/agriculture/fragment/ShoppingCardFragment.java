package com.lyj.agriculture.fragment;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.lyj.agriculture.R;
import com.lyj.agriculture.activity.OrderActivity;
import com.lyj.agriculture.adapter.ShoppingCardAdapter;
import com.lyj.agriculture.http.AsyncTaskWithDelayDlg;
import com.lyj.agriculture.http.HttpClientUtils;
import com.lyj.agriculture.http.IPPort;
import com.lyj.agriculture.model.ShoppingCartItem;
import com.lyj.agriculture.util.Const;
import com.lyj.agriculture.util.Util;
import com.lyj.agriculture.xmlparse.SAXParserContentHandler;

public class ShoppingCardFragment extends Fragment {

	private Context mContext;
	private List<Object> list = null;

	private ShoppingCartItem shoppingCartItem;
	private List<ShoppingCartItem> list_shoppingCartItem;
	private ShoppingCardAdapter shoppingCardAdapter;
	private ListView listView;
	private static TextView tvAllMoney;
	private static Button btnGoBuy, buyContinue;
	public static float allMoney;
	public static Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			if (tvAllMoney != null && btnGoBuy != null) {
				allMoney = Float.valueOf(msg.getData().getString("allMoney"));
				tvAllMoney.setText("总价：￥" + allMoney);
				btnGoBuy.setVisibility(View.VISIBLE);
				if (!(allMoney > 0.01)) {
					btnGoBuy.setVisibility(View.INVISIBLE);
				}
			}
		};
	};

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		return inflater.inflate(R.layout.shopping_card_list, null);
	}

	public void onActivityCreated(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		listView = (ListView) getView().findViewById(R.id.shopping_card_list);
		tvAllMoney = (TextView) getView().findViewById(R.id.shopping_cart_all);
		btnGoBuy = (Button) getView().findViewById(R.id.shopping_cart_go_buy);
		buyContinue = (Button) getView().findViewById(R.id.shopping_cart_continue);
		buyContinue.setText(" 删除 ");
		buyContinue.setVisibility(View.VISIBLE);;
		init();
	}

	private void init() {
		mContext = getActivity();
		list_shoppingCartItem = new ArrayList<ShoppingCartItem>();
		list = new ArrayList<Object>();

		buyContinue.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				shoppingCardAdapter.deleteItem();
			}
		});

		btnGoBuy.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Const.list_shoppingCartItems = shoppingCardAdapter.getList_shoppingCartItem();
				Util.gotoActivityNotFinish(mContext, OrderActivity.class);
			}
		});
		// 下载xml,并解析
		WebAsyncTask webAsyncTask = new WebAsyncTask(mContext);
		webAsyncTask.execute();
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
				// httpClientUtils.sendPOSTRequestHttpClient(IPPort.URL_SHOPPINGCARD
				// + Const.customerID, params)
				return httpClientUtils.getUrlContext(mContext, IPPort.URL_SHOPPINGCARDGET + Const.customerID);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute2(String result) {
			if (result == null) {
				Toast.makeText(mContext, "连接服务器异常11", Toast.LENGTH_SHORT).show();
			} else {
				try {
					SAXParserContentHandler mSaxParserContentHandler = new SAXParserContentHandler();
					list = mSaxParserContentHandler.parseReadXml(new ShoppingCartItem(), result);

					shoppingCartItem = new ShoppingCartItem();
					for (Object object : list) {
						shoppingCartItem = (ShoppingCartItem) object;
						list_shoppingCartItem.add(shoppingCartItem);

					}
					if (list_shoppingCartItem.size() == 0) {
						Toast.makeText(mContext, "购物车还没有东西", Toast.LENGTH_SHORT).show();
						buyContinue.setVisibility(View.INVISIBLE);
					}
					shoppingCardAdapter = new ShoppingCardAdapter(mContext, list_shoppingCartItem);
					listView.setAdapter(shoppingCardAdapter);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

}
