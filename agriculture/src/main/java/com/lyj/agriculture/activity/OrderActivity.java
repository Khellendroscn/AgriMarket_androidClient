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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.MenuItem;
import com.lyj.agriculture.R;
import com.lyj.agriculture.adapter.OrderAdapter;
import com.lyj.agriculture.http.AsyncTaskWithDelayDlg;
import com.lyj.agriculture.http.HttpClientUtils;
import com.lyj.agriculture.http.IPPort;
import com.lyj.agriculture.model.AddressItem;
import com.lyj.agriculture.model.OrderInsert;
import com.lyj.agriculture.model.OrderInsertItem;
import com.lyj.agriculture.model.ShoppingCartItem;
import com.lyj.agriculture.model.key;
import com.lyj.agriculture.util.Const;
import com.lyj.agriculture.util.Util;
import com.lyj.agriculture.xmlparse.SAXParserContentHandler;

public class OrderActivity extends SherlockActivity {
	private static TextView orderAddress;
	private TextView orderAllMoney;
	private ListView orderListView;
	private Button orderBuy;
	private Context mContext = this;
	private List<AddressItem> list_AddressItem;
	private List<Object> list;
	private AddressItem addressItem;
	private List<ShoppingCartItem> list_shoppingCartItems;
	private OrderAdapter orderAdapter;
	private int orderID;
	private ImageView addressModify;

	Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			if (msg.what == 0) {
				AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
				builder.setMessage("下单成功！点击继续购物返回首页。");
				builder.setPositiveButton("继续购物", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						Util.gotoActivityNotFinish(mContext, FragmentChangeActivity.class);
						finish();
					}
				});
				builder.create().show();
			} else {

			}
		};
	};

	public static Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			if (msg.what == 0) {
				orderAddress.setText(Const.orderAddressItem.getName() + ",  " + Const.orderAddressItem.getAddress()
						+ ",  " + Const.orderAddressItem.getTelNumber());
			}

		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.order);
		init();
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		setTitle("填写订单");
	}

	private void init() {
		list = new ArrayList<Object>();
		list_AddressItem = new ArrayList<AddressItem>();
		orderAddress = (TextView) findViewById(R.id.order_address);
		orderAllMoney = (TextView) findViewById(R.id.order_all_money);
		orderListView = (ListView) findViewById(R.id.order_listView);
		addressModify = (ImageView) findViewById(R.id.order_address_modify);
		orderBuy = (Button) findViewById(R.id.btn_buy);
		orderBuy.setOnClickListener(new orderBuyOnclickListener());
		list_shoppingCartItems = Const.list_shoppingCartItems;
		orderAdapter = new OrderAdapter(mContext, list_shoppingCartItems);
		orderListView.setAdapter(orderAdapter);
		orderAllMoney.setText("总金额：￥" + ProductShoppingCardActivity.allMoney);

		// 下载xml,并解析
		WebAsyncTask webAsyncTask = new WebAsyncTask(mContext);
		webAsyncTask.execute();

		addressModify.setOnClickListener(new addressOnClickListener());
		orderAddress.setOnClickListener(new addressOnClickListener());
	}

	private class addressOnClickListener implements OnClickListener {
		public void onClick(View v) {
			// Product
			Util.gotoActivityNotFinish(mContext, OrderAddressActivity.class);
		}
	}

	private class orderBuyOnclickListener implements OnClickListener {
		public void onClick(View v) {
			if (orderAddress.getText().toString().equals("")||orderAddress.getText().toString().equals("填写地址")) {
				Toast.makeText(mContext, "请填写收货地址", Toast.LENGTH_SHORT).show();
				return;
			}
			new Thread(new Runnable() {
				public void run() {
					ArrayList<OrderInsert> list_orderInsert = new ArrayList<OrderInsert>();
					OrderInsert orderInsert = new OrderInsert();
					orderInsert.setCustomerID(Const.customerID);
					orderInsert.setAddressID(Const.orderAddressItem.getAddressID());
					orderInsert.setSum(ProductShoppingCardActivity.allMoney);
					list_orderInsert.add(orderInsert);
					String xmlString = Util.produceXmlOrderInsert(list_orderInsert);

					key k = new key();
					HttpClientUtils httpClientUtils = new HttpClientUtils();
					String xmlResult = httpClientUtils.sendPOSTRequesString(IPPort.URL_ORDERPOST, xmlString);
					SAXParserContentHandler mSaxParserContentHandler = new SAXParserContentHandler();
					try {
						k = (key) mSaxParserContentHandler.parseReadXmlObject(new key(), xmlResult);
						orderID = k.getPrimaryKey();
					} catch (Exception e) {
						e.printStackTrace();
					}

					ArrayList<OrderInsertItem> list_orderInsertItem = new ArrayList<OrderInsertItem>();
					for (int i = 0; i < list_shoppingCartItems.size(); i++) {
						OrderInsertItem orderInsertItem = new OrderInsertItem();
						orderInsertItem.setFarmID(list_shoppingCartItems.get(i).getFarmID());
						orderInsertItem.setOrderID(orderID);
						orderInsertItem.setPartSum(Util.DecimalFormat(list_shoppingCartItems.get(i).getProductPrice()
								* list_shoppingCartItems.get(i).getProductCount(), 1));
						orderInsertItem.setProductCount(list_shoppingCartItems.get(i).getProductCount());
						orderInsertItem.setProductID(list_shoppingCartItems.get(i).getProductID());
						list_orderInsertItem.add(orderInsertItem);
					}
					xmlString = Util.produceXmlOrderInsertItem(list_orderInsertItem);
					httpClientUtils = new HttpClientUtils();
					if (httpClientUtils.sendPOSTRequestboolean(IPPort.URL_ORDERITEMPOST, xmlString)) {
						// "请检sendPOSTRequestboolean用户名或密码是否正确");
						mHandler.sendEmptyMessage(0);
					}
				}
			}).start();
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
				return httpClientUtils.getUrlContext(mContext, IPPort.URL_DEFAULTADDRESS + Const.customerID);
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
					list = mSaxParserContentHandler.parseReadXml(new AddressItem(), result);
					addressItem = new AddressItem();
					for (Object object : list) {
						addressItem = (AddressItem) object;
						list_AddressItem.add(addressItem);
					}
					if (list_AddressItem.get(0).getAddressID() != 0) {
						Const.orderAddressItem = list_AddressItem.get(0);
						orderAddress
								.setText(list_AddressItem.get(0).getName() + ",  "
										+ list_AddressItem.get(0).getAddress() + ",  "
										+ list_AddressItem.get(0).getTelNumber());
					} else {
						orderAddress.setText("填写地址");
						Const.orderAddressItem = new AddressItem();
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

}
