package com.lyj.agriculture.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.MenuItem;
import com.lyj.agriculture.R;
import com.lyj.agriculture.http.AsyncTaskWithDelayDlg;
import com.lyj.agriculture.http.HttpClientUtils;
import com.lyj.agriculture.http.IPPort;
import com.lyj.agriculture.http.ImageLoader;
import com.lyj.agriculture.model.ProductDetail;
import com.lyj.agriculture.model.ShoppingCart;
import com.lyj.agriculture.model.ShoppingCartItem;
import com.lyj.agriculture.util.Const;
import com.lyj.agriculture.util.LogUtil;
import com.lyj.agriculture.util.Util;
import com.lyj.agriculture.xmlparse.SAXParserContentHandler;

public class ProductDetailActivity extends SherlockActivity {
	private Context mContext = this;
	private List<Object> list = null;
	private ImageLoader imageLoader;
	private ProductDetail productDetail;
	private ImageView productDetailImage;
	private TextView productEvaluateCount, productDetailPrice, productCount, productDetailFarm, productDetailName;
	private Button productDetailShoppingCard, productDetailBuy;
	private RelativeLayout productDetailNamePrice;
	private LinearLayout productEvaluateLayout;
	RatingBar ratingBar;
	public Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			if (msg.what == 1) {
				Util.gotoActivityNotFinish(mContext, ProductShoppingCardActivity.class);
			} else {
				Toast.makeText(mContext, "加入购物车失败", Toast.LENGTH_SHORT).show();
			}
		};
	};

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.productdetail);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		setTitle("商品详细信息");
		init();
	}

	private void init() {

		// 下载xml,并解析
		WebAsyncTask webAsyncTask = new WebAsyncTask(mContext);
		webAsyncTask.execute();

		imageLoader = new ImageLoader(mContext);

		productDetailImage = (ImageView) findViewById(R.id.product_detail_image);
		productDetailPrice = (TextView) findViewById(R.id.product_detail_price);
		productCount = (TextView) findViewById(R.id.product_detail_count);
		productDetailFarm = (TextView) findViewById(R.id.product_detail_farm);
		productDetailName = (TextView) findViewById(R.id.product_detail_name);
		productDetailShoppingCard = (Button) findViewById(R.id.product_detail_shopping_card);
		productDetailBuy = (Button) findViewById(R.id.product_detail_bug);
		ratingBar = (RatingBar) findViewById(R.id.rating);
		productEvaluateCount = (TextView) findViewById(R.id.product_evaluate);
		productDetailNamePrice = (RelativeLayout) findViewById(R.id.product_detail_name_price);
		productEvaluateLayout = (LinearLayout) findViewById(R.id.product_evaluate_layout);

		productDetailShoppingCard.setOnClickListener(new MyOnClickListener());
		productDetailBuy.setOnClickListener(new MyOnClickListener());
		productDetailNamePrice.setOnClickListener(new NameDescriptionOnclickListener());
		productEvaluateLayout.setOnClickListener(new productEvaluateOnClickListener());
	}

	private class productEvaluateOnClickListener implements OnClickListener {
		public void onClick(View v) {
			Util.gotoActivityNotFinish(mContext, ProductEvaluateActivity.class);
		}
	}

	private class NameDescriptionOnclickListener implements OnClickListener {
		public void onClick(View v) {
			Const.productDetail = productDetail;
			Util.gotoActivityNotFinish(mContext, ProductDescriptionActivity.class);
		}
	}

	private class MyOnClickListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.product_detail_bug:
				if (Const.customerID == -1) {
					AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
					builder.setMessage("请先登录用户，在下单。");
					builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
							// Util.gotoActivityNotFinish(mContext,
							// AboutMeEvaluateLoginActivity.class);
							// finish();
							Intent intent = new Intent();
							intent.setClass(mContext, AboutMeEvaluateLoginActivity.class);
							startActivityForResult(intent, 100);
						}
					});
					builder.setNegativeButton("取消", null);
					builder.create().show();
				} else {
					goBuy();
				}
				break;
			case R.id.product_detail_shopping_card:
				if (Const.customerID == -1) {
					AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
					builder.setMessage("请先登录用户，在加入购物车。");
					builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
							Intent intent = new Intent();
							intent.setClass(mContext, AboutMeEvaluateLoginActivity.class);
							startActivityForResult(intent, 100);
						}
					});
					builder.setNegativeButton("取消", null);
					builder.create().show();
				} else {
					addProduct();
				}

				break;
			default:
				break;
			}
		}

	}

	private void goBuy() {
		if (productDetail.getProductCount() <= 0) {
			Toast.makeText(mContext, "库存不足，无法购买", Toast.LENGTH_SHORT).show();
			return;
		}
		List<ShoppingCartItem> list2 = new ArrayList<ShoppingCartItem>();
		ShoppingCartItem scardItem = new ShoppingCartItem();
		scardItem.setFarmID(productDetail.getFarmID());
		scardItem.setFarmName(productDetail.getFarmName());
		scardItem.setProductCount(1);
		scardItem.setProductID(productDetail.getProductID());
		scardItem.setProductImage(productDetail.getProductImage());
		scardItem.setProductName(productDetail.getProductName());
		scardItem.setProductPrice(productDetail.getProductPrice());
		list2.add(scardItem);
		Const.list_shoppingCartItems = list2;
		Util.gotoActivityNotFinish(mContext, OrderActivity.class);
	}

	@Override
	public boolean onCreateOptionsMenu(com.actionbarsherlock.view.Menu menu) {
		MenuItem shoppingCard = menu.add(0, 0, 0, "购物车");
		shoppingCard.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS | MenuItem.SHOW_AS_ACTION_WITH_TEXT);
		shoppingCard.setIcon(R.drawable.shopping_card);

		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		if (item.getItemId() == android.R.id.home) {
			onBackPressed();
		} else if (item.getItemId() == 0) {
			Util.gotoActivityNotFinish(mContext, ProductShoppingCardActivity.class);
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
				// httpClientUtils.sendPOSTRequestHttpClient(U, params)
				return httpClientUtils.getUrlContext(mContext, IPPort.URL_PRODUCTDETAIL + +Const.productID);
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
					list = mSaxParserContentHandler.parseReadXml(new ProductDetail(), result);
					productDetail = new ProductDetail();
					productDetail = (ProductDetail) list.get(0);
					imageLoader.DisplayImage(productDetail.getProductImage(), productDetailImage);
					productDetailPrice.setText("价格:￥ " + productDetail.getProductPrice() + "/" + ""
							+ productDetail.getProductSize());
					productCount.setText("库存: " + productDetail.getProductCount());
					if (productDetail.getFarmName() == null) {
						productDetailFarm.setText("服务: 本商品由" + "顺丰" + "专业配送");
					} else {
						productDetailFarm.setText("服务: 本商品由" + productDetail.getFarmName() + "专业配送");
					}
					productDetailName.setText(productDetail.getProductName());
					ratingBar.setProgress((int) productDetail.getAvgStar());
					Log.i("lyj", "count = "+ productDetail.getEvaluateCount() );
					productEvaluateCount.setText("(" + productDetail.getEvaluateCount() + ")");

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	private void addProduct() {
		if (productDetail.getProductCount() <= 0) {
			Toast.makeText(mContext, "库存不足，无法购买", Toast.LENGTH_SHORT).show();
			return;
		}
		new Thread(new Runnable() {
			public void run() {
				ShoppingCart shoppingCart = new ShoppingCart();
				shoppingCart.setCustomerID(Const.customerID);
				shoppingCart.setProductCount(1);
				shoppingCart.setProductID(Const.productID);
				ArrayList<ShoppingCart> list = new ArrayList<ShoppingCart>();
				list.add(shoppingCart);
				String xmlStr = Util.produceXmlShoppingCart(list);
				HttpClientUtils httpClientUtils = new HttpClientUtils();
				System.out.println("POST:\n"+xmlStr);
				if (httpClientUtils.sendPOSTRequestboolean(IPPort.URL_SHOPPINGCARDPOST, xmlStr)) {
					mHandler.sendEmptyMessage(1);
				} else {
					mHandler.sendEmptyMessage(-1);
				}
			}
		}).start();

	}
}
