package com.lyj.agriculture.adapter;

import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.lyj.agriculture.R;
import com.lyj.agriculture.activity.ProductShoppingCardActivity;
import com.lyj.agriculture.fragment.ShoppingCardFragment;
import com.lyj.agriculture.http.HttpClientUtils;
import com.lyj.agriculture.http.IPPort;
import com.lyj.agriculture.http.ImageLoader;
import com.lyj.agriculture.model.ShoppingCart;
import com.lyj.agriculture.model.ShoppingCartItem;
import com.lyj.agriculture.util.Const;
import com.lyj.agriculture.util.LogUtil;
import com.lyj.agriculture.util.Util;

public class ShoppingCardAdapter extends BaseAdapter {

	private List<ShoppingCartItem> list_shoppingCartItem = new ArrayList<ShoppingCartItem>();
	private LayoutInflater mInflater = null;
	private Context mContext;
	private ImageLoader imageLoader;
	private int curCount;
	private ImageView shoppingCardImage;
	private TextView shoppingCardProductName;
	private TextView shoppingCardNumber;
	private ImageButton shoppingCardDec;
	private TextView shoppingCardProductCount;
	private ImageButton shoppingCardInc;
	private TextView shoppingCardPrice;
	private TextView shoppingCardFarm;
	private CheckBox checkbox;
	private List<Boolean> list;
	private int postTag;

	Handler shopmHandler = new Handler() {
		public void handleMessage(Message msg) {
			notifyDataSetChanged();
		};
	};

	public ShoppingCardAdapter(Context content, List<ShoppingCartItem> list_shoppingCartItem) {
		this.list_shoppingCartItem = list_shoppingCartItem;
		this.mContext = content;
		mInflater = LayoutInflater.from(mContext);
		imageLoader = new ImageLoader(mContext);
		list = new ArrayList<Boolean>();
		for (int i = 0; i < list_shoppingCartItem.size(); i++) {
			list.add(true);
		}
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		if (list_shoppingCartItem.size() == 0) {
			sentMessage();
			sentMessage2();
		}
		return list_shoppingCartItem.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		// if (convertView == null)
		convertView = mInflater.inflate(R.layout.shopping_card_item, null);
		shoppingCardImage = (ImageView) convertView.findViewById(R.id.shopping_card_image);
		shoppingCardProductName = (TextView) convertView.findViewById(R.id.shopping_card_product_name);
		shoppingCardNumber = (TextView) convertView.findViewById(R.id.shopping_card_number);
		shoppingCardProductCount = (TextView) convertView.findViewById(R.id.shopping_card_product_count);
		shoppingCardPrice = (TextView) convertView.findViewById(R.id.shopping_card_price);
		shoppingCardDec = (ImageButton) convertView.findViewById(R.id.shopping_card_dec);
		shoppingCardInc = (ImageButton) convertView.findViewById(R.id.shopping_card_inc);
		checkbox = (CheckBox) convertView.findViewById(R.id.shopping_card_checkbox);
		shoppingCardFarm = (TextView) convertView.findViewById(R.id.shopping_card_farm);

		shoppingCardImage.setTag(position);
		shoppingCardProductName.setTag(position);
		shoppingCardNumber.setTag(position);
		shoppingCardProductCount.setTag(position);
		shoppingCardPrice.setTag(position);
		shoppingCardDec.setTag(position);
		shoppingCardInc.setTag(position);
		checkbox.setTag(position);
		shoppingCardFarm.setTag(position);

		checkbox.setChecked(list.get(position));

		imageLoader.DisplayImage(list_shoppingCartItem.get(position).getProductImage(), shoppingCardImage);
		shoppingCardProductName.setText(list_shoppingCartItem.get(position).getProductName());
		shoppingCardNumber.setText("编号：" + Const.customerID + "_" + list_shoppingCartItem.get(position).getProductID());
		shoppingCardFarm.setText("店铺：" + list_shoppingCartItem.get(position).getFarmName());

		shoppingCardProductCount.setText(list_shoppingCartItem.get(position).getProductCount() + "");
		shoppingCardPrice.setText("价格：￥"
				+ Util.DecimalFormat(
						list_shoppingCartItem.get(position).getProductPrice()
								* list_shoppingCartItem.get(position).getProductCount(), 1));
		curCount = Integer.valueOf((String) shoppingCardProductCount.getText());
		if (curCount == 1) {
			shoppingCardDec.setImageDrawable(mContext.getResources().getDrawable(
					R.drawable.edit_product_num_des_no_enable));
			shoppingCardDec.setEnabled(false);
		} else {
			shoppingCardDec.setImageDrawable(mContext.getResources()
					.getDrawable(R.drawable.edit_product_num_des_normal));
			shoppingCardDec.setEnabled(true);
		}
		shoppingCardDec.setOnClickListener(new DecOnClickListener());
		shoppingCardInc.setOnClickListener(new IncOnclickListener());
		checkbox.setOnCheckedChangeListener(new CheckBoxOnCheckedChangeListener());
		shoppingCardProductCount.setOnClickListener(new shoppingCardProductCountOnclickListener());
		sentMessage();
		sentMessage2();
		return convertView;
	}

	private class shoppingCardProductCountOnclickListener implements OnClickListener{
		public void onClick(View v) {
			int tag = (Integer) v.getTag();
			postTag = tag;
			
			LayoutInflater inflater = LayoutInflater.from(mContext);
			View view = inflater.inflate(R.layout.product_count, null);
			final EditText et  = (EditText) view.findViewById(R.id.prodtc_count_edit);
			AlertDialog.Builder alb = new AlertDialog.Builder(mContext);
			alb.setTitle("输入需要购买的数量");
			alb.setView(view);
			alb.setPositiveButton("确定", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					list_shoppingCartItem.get(postTag).setProductCount(Integer.valueOf(et.getText().toString()));

					new Thread(new Runnable() {
						public void run() {
							ShoppingCart shoppingCart = new ShoppingCart();
							shoppingCart.setCustomerID(Const.customerID);
							LogUtil.i("lyj", "et.getText().toString() = "+et.getText().toString());
							shoppingCart.setProductCount(Integer.valueOf(et.getText().toString()));
							shoppingCart.setProductID(list_shoppingCartItem.get(postTag).getProductID());
							ArrayList<ShoppingCart> list_count = new ArrayList<ShoppingCart>();
							list_count.add(shoppingCart);
							String xmlStr = Util.produceXmlShoppingCart(list_count);
							HttpClientUtils httpClientUtils = new HttpClientUtils();
							httpClientUtils.sendPOSTRequestboolean(IPPort.URL_SHOPPINGCARDPOST, xmlStr);
						}
					}).start();
					shopmHandler.sendEmptyMessage(0);
				}
			});
			alb.setNegativeButton("取消", null);
			alb.create().show();
			
		}
	}
	
	private class DecOnClickListener implements OnClickListener {
		public void onClick(View v) {
			int tag = (Integer) v.getTag();
			postTag = tag;
			list_shoppingCartItem.get(tag).setProductCount(list_shoppingCartItem.get(tag).getProductCount() - 1);
			new Thread(new Runnable() {
				public void run() {
					ShoppingCart shoppingCart = new ShoppingCart();
					shoppingCart.setCustomerID(Const.customerID);
					shoppingCart.setProductCount(-1);
					shoppingCart.setProductID(list_shoppingCartItem.get(postTag).getProductID());
					ArrayList<ShoppingCart> list_dec = new ArrayList<ShoppingCart>();
					list_dec.add(shoppingCart);
					String xmlStr = Util.produceXmlShoppingCart(list_dec);
					HttpClientUtils httpClientUtils = new HttpClientUtils();
					httpClientUtils.sendPOSTRequestboolean(IPPort.URL_SHOPPINGCARDPOST, xmlStr);
					shopmHandler.sendEmptyMessage(0);
				}
			}).start();

			
		}
	}

	private class IncOnclickListener implements OnClickListener {
		public void onClick(View v) {
			int tag = (Integer) v.getTag();
			postTag = tag;
			list_shoppingCartItem.get(tag).setProductCount(list_shoppingCartItem.get(tag).getProductCount() + 1);
			new Thread(new Runnable() {
				public void run() {
					ShoppingCart shoppingCart = new ShoppingCart();
					shoppingCart.setCustomerID(Const.customerID);
					shoppingCart.setProductCount(1);
					shoppingCart.setProductID(list_shoppingCartItem.get(postTag).getProductID());
					ArrayList<ShoppingCart> list_inc = new ArrayList<ShoppingCart>();
					list_inc.add(shoppingCart);
					String xmlStr = Util.produceXmlShoppingCart(list_inc);
					HttpClientUtils httpClientUtils = new HttpClientUtils();
					httpClientUtils.sendPOSTRequestboolean(IPPort.URL_SHOPPINGCARDPOST, xmlStr);
					shopmHandler.sendEmptyMessage(0);
				}
			}).start();

			
		}
	}

	private class CheckBoxOnCheckedChangeListener implements OnCheckedChangeListener {
		public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
			int tag = (Integer) buttonView.getTag();
			List<Boolean> list2 = new ArrayList<Boolean>();
			for (int i = 0; i < list.size(); i++) {
				if (tag == i) {
					if (isChecked) {
						list2.add(true);
					} else {
						list2.add(false);
					}
				} else {
					list2.add(list.get(i));
				}
			}
			list.clear();
			for (int i = 0; i < list2.size(); i++) {
				list.add(list2.get(i));
			}
			notifyDataSetChanged();
		}
	}

	public float getAllMoney() {
		float allMoney = 0;
		for (int i = 0; i < list_shoppingCartItem.size(); i++) {
			if (list.get(i))
				allMoney = allMoney + list_shoppingCartItem.get(i).getProductCount()
						* list_shoppingCartItem.get(i).getProductPrice();
		}
		return Util.DecimalFormat(allMoney, 1);
	}

	private void sentMessage() {
		Message msg = new Message();
		Bundle b = new Bundle();
		b.putString("allMoney", getAllMoney() + "");
		msg.setData(b);
		ProductShoppingCardActivity.mHandler.sendMessage(msg);

	}

	private void sentMessage2() {
		Message msg = new Message();
		Bundle b = new Bundle();
		b.putString("allMoney", getAllMoney() + "");
		msg.setData(b);
		ShoppingCardFragment.mHandler.sendMessage(msg);
	}

	List<ShoppingCartItem> list_shoppingCartItem2;
	ArrayList<Integer> arrayListInt = new ArrayList<Integer>();

	public void deleteItem() {
		list_shoppingCartItem2 = new ArrayList<ShoppingCartItem>();
		list_shoppingCartItem2.clear();
		for (int i = 0; i < list_shoppingCartItem.size(); i++) {
			if (!list.get(i)) {
				list_shoppingCartItem2.add(list_shoppingCartItem.get(i));
			} else {
				arrayListInt.add(i);
			}
		}

		new Thread(new Runnable() {
			public void run() {
				for (int i = 0; i < arrayListInt.size(); i++) {
					HttpClientUtils httpClientUtils = new HttpClientUtils();
					httpClientUtils.sendPOSTNoRequestboolean(IPPort.URL_SHOPPINGCARDDELETE + Const.customerID
							+ "&productID=" + list_shoppingCartItem.get(arrayListInt.get(i)).getProductID());
					if (i == arrayListInt.size() - 1) {
						list.clear();
						list_shoppingCartItem.clear();
						for (int j = 0; j < list_shoppingCartItem2.size(); j++) {
							list_shoppingCartItem.add(list_shoppingCartItem2.get(j));
							list.add(false);
						}
						shopmHandler.sendEmptyMessage(0);
					}
				}

			}
		}).start();

	}

	public List<ShoppingCartItem> getList_shoppingCartItem() {
		return list_shoppingCartItem;
	}

	/*
	 * // 异步下载网络xml数据 private class WebAsyncTask extends
	 * AsyncTaskWithDelayDlg<Void, Void, String> {
	 * 
	 * private int productID;
	 * 
	 * public WebAsyncTask(Context context, int productID) { super(context);
	 * this.productID = productID; }
	 * 
	 * @Override protected String doInBackground2(Void... params) { try {
	 * HttpClientUtils httpClientUtils = new HttpClientUtils(); LogUtil.i("lyj",
	 * "url" + IPPort.URL_SHOPPINGCARDDELETE + Const.customerID + "&productID="
	 * + productID); return httpClientUtils.getUrlContext(mContext,
	 * IPPort.URL_SHOPPINGCARDDELETE + Const.customerID + "&productID=" +
	 * productID); } catch (Exception e) { e.printStackTrace(); } return null; }
	 * 
	 * @Override protected void onPostExecute2(String result) { if (result ==
	 * null) { Toast.makeText(mContext, "连接服务器异常", Toast.LENGTH_SHORT).show(); }
	 * else {
	 * 
	 * } } }
	 */
}
