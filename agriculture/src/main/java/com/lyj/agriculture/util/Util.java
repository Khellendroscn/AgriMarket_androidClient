package com.lyj.agriculture.util;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.util.ArrayList;

import org.xmlpull.v1.XmlPullParserFactory;
import org.xmlpull.v1.XmlSerializer;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.Uri;

import com.lyj.agriculture.model.AddressItem;
import com.lyj.agriculture.model.CustomerAdd;
import com.lyj.agriculture.model.CustomerConfirm;
import com.lyj.agriculture.model.OrderEvaluateAdd;
import com.lyj.agriculture.model.OrderInsert;
import com.lyj.agriculture.model.OrderInsertItem;
import com.lyj.agriculture.model.ExpertAdvisor;
import com.lyj.agriculture.model.ShoppingCart;

public class Util {

	public static void goToGitHub(Context context) {
		Uri uriUrl = Uri.parse("http://github.com/jfeinstein10/slidingmenu");
		Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
		context.startActivity(launchBrowser);
	}

	public static void saveLoginInfo(Context context, String username, String password) {
		// 获取SharedPreferences对象
		SharedPreferences sharedPre = context.getSharedPreferences("config", context.MODE_PRIVATE);
		// 获取Editor对象
		Editor editor = sharedPre.edit();
		// 设置参数
		editor.putString("username", username);
		editor.putString("password", password);
		// 提交
		editor.commit();
	}

	public static String getLoginName(Context context) {
		SharedPreferences settings = context.getSharedPreferences("config", 0);
		return settings.getString("username", "");
	}

	public static String getLoginPassword(Context context) {
		SharedPreferences settings = context.getSharedPreferences("config", 0);
		return settings.getString("password", "");
	}

	public static void CopyStream(InputStream is, OutputStream os) {
		final int buffer_size = 1024;
		try {
			byte[] bytes = new byte[buffer_size];
			for (;;) {
				int count = is.read(bytes, 0, buffer_size);
				if (count == -1)
					break;
				os.write(bytes, 0, count);
				is.close();
				os.close();
			}
		} catch (Exception ex) {
		}
	}

	public static void gotoActivityNotFinish(Context context, Class actClass) {
		try {
			Intent intent = new Intent(context, actClass);
			context.startActivity(intent);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

	}


	public static float DecimalFormat(float f, int i) {
		try {
			BigDecimal b = new BigDecimal(f);
			return (float) b.setScale(i, BigDecimal.ROUND_HALF_UP).doubleValue();
		} catch (Exception e) {
			// TODO: handle exception
		}
		return 0;
	}

	public static String produceXmlShoppingCart(ArrayList<ShoppingCart> list) {
		StringWriter stringWriter = new StringWriter();
		try {
			XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
			XmlSerializer xmlSerializer = factory.newSerializer();
			xmlSerializer.setOutput(stringWriter);
			xmlSerializer.startDocument("utf-8", true);
			for (ShoppingCart shoppingCart : list) {
				xmlSerializer.startTag(null, "shoppingCart");

				xmlSerializer.startTag(null, "customerID");
				xmlSerializer.text(shoppingCart.getCustomerID() + "");
				xmlSerializer.endTag(null, "customerID");

				xmlSerializer.startTag(null, "productID");
				xmlSerializer.text(shoppingCart.getProductID() + "");
				xmlSerializer.endTag(null, "productID");

				xmlSerializer.startTag(null, "productCount");
				xmlSerializer.text(shoppingCart.getProductCount() + "");
				xmlSerializer.endTag(null, "productCount");

				xmlSerializer.endTag(null, "shoppingCart");
			}
			// xmlSerializer.endTag(null, "shoppingCarts");
			xmlSerializer.endDocument();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return stringWriter.toString();
	}

	public static String produceXmlOrderInsert(ArrayList<OrderInsert> list) {
		StringWriter stringWriter = new StringWriter();
		try {
			XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
			XmlSerializer xmlSerializer = factory.newSerializer();
			xmlSerializer.setOutput(stringWriter);
			xmlSerializer.startDocument("utf-8", true);
			for (OrderInsert orderInsert : list) {
				xmlSerializer.startTag(null, "orderInsert");

				xmlSerializer.startTag(null, "customerID");
				xmlSerializer.text(orderInsert.getCustomerID() + "");
				xmlSerializer.endTag(null, "customerID");

				xmlSerializer.startTag(null, "sum");
				xmlSerializer.text(orderInsert.getSum() + "");
				xmlSerializer.endTag(null, "sum");

				xmlSerializer.startTag(null, "addressID");
				xmlSerializer.text(orderInsert.getAddressID() + "");
				xmlSerializer.endTag(null, "addressID");

				xmlSerializer.endTag(null, "orderInsert");
			}
			// xmlSerializer.endTag(null, "shoppingCarts");
			xmlSerializer.endDocument();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return stringWriter.toString();
	}

	public static String produceXmlOrderInsertItem(ArrayList<OrderInsertItem> list) {
		StringWriter stringWriter = new StringWriter();
		try {
			XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
			XmlSerializer xmlSerializer = factory.newSerializer();
			xmlSerializer.setOutput(stringWriter);
			xmlSerializer.startDocument("utf-8", true);
			xmlSerializer.startTag(null, "orderInsertItems");
			for (OrderInsertItem orderInsertItem : list) {
				xmlSerializer.startTag(null, "orderInsertItem");

				xmlSerializer.startTag(null, "orderID");
				xmlSerializer.text(orderInsertItem.getOrderID() + "");
				xmlSerializer.endTag(null, "orderID");

				xmlSerializer.startTag(null, "productID");
				xmlSerializer.text(orderInsertItem.getProductID() + "");
				xmlSerializer.endTag(null, "productID");

				xmlSerializer.startTag(null, "productCount");
				xmlSerializer.text(orderInsertItem.getProductCount() + "");
				xmlSerializer.endTag(null, "productCount");

				xmlSerializer.startTag(null, "partSum");
				xmlSerializer.text(orderInsertItem.getPartSum() + "");
				xmlSerializer.endTag(null, "partSum");

				xmlSerializer.startTag(null, "farmID");
				xmlSerializer.text(orderInsertItem.getFarmID() + "");
				xmlSerializer.endTag(null, "farmID");

				xmlSerializer.endTag(null, "orderInsertItem");
			}
			xmlSerializer.endTag(null, "orderInsertItems");
			xmlSerializer.endDocument();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return stringWriter.toString();
	}

	public static String produceXmlOrderEvaluateAdd(ArrayList<OrderEvaluateAdd> list) {
		StringWriter stringWriter = new StringWriter();
		try {
			XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
			XmlSerializer xmlSerializer = factory.newSerializer();
			xmlSerializer.setOutput(stringWriter);
			xmlSerializer.startDocument("utf-8", true);
			// xmlSerializer.startTag(null, "orderEvaluateAdds");
			for (OrderEvaluateAdd orderEvaluateAdd : list) {
				xmlSerializer.startTag(null, "orderEvaluateAdd");

				xmlSerializer.startTag(null, "orderID");
				xmlSerializer.text(orderEvaluateAdd.getOrderID() + "");
				xmlSerializer.endTag(null, "orderID");

				xmlSerializer.startTag(null, "productID");
				xmlSerializer.text(orderEvaluateAdd.getProductID() + "");
				xmlSerializer.endTag(null, "productID");

				xmlSerializer.startTag(null, "star");
				xmlSerializer.text(orderEvaluateAdd.getStar() + "");
				xmlSerializer.endTag(null, "star");

				xmlSerializer.startTag(null, "orderEvaluateDescription");
				xmlSerializer.text(orderEvaluateAdd.getOrderEvaluateDescription() + "");
				xmlSerializer.endTag(null, "orderEvaluateDescription");

				xmlSerializer.endTag(null, "orderEvaluateAdd");
			}
			// xmlSerializer.endTag(null, "orderEvaluateAdds");
			xmlSerializer.endDocument();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return stringWriter.toString();
	}

	public static String produceXmlCustomerConfirm(ArrayList<CustomerConfirm> list) {
		StringWriter stringWriter = new StringWriter();
		try {
			XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
			XmlSerializer xmlSerializer = factory.newSerializer();
			xmlSerializer.setOutput(stringWriter);
			xmlSerializer.startDocument("utf-8", true);
			for (CustomerConfirm customerConfirm : list) {
				xmlSerializer.startTag(null, "customerConfirm");

				xmlSerializer.startTag(null, "customerName");
				xmlSerializer.text(customerConfirm.getCustomerName() + "");
				xmlSerializer.endTag(null, "customerName");

				xmlSerializer.startTag(null, "password");
				xmlSerializer.text(customerConfirm.getPassword() + "");
				xmlSerializer.endTag(null, "password");

				xmlSerializer.endTag(null, "customerConfirm");
			}
			xmlSerializer.endDocument();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return stringWriter.toString();
	}

	public static String produceXmlCustomerAdd(ArrayList<CustomerAdd> list) {
		StringWriter stringWriter = new StringWriter();
		try {
			XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
			XmlSerializer xmlSerializer = factory.newSerializer();
			xmlSerializer.setOutput(stringWriter);
			xmlSerializer.startDocument("utf-8", true);
			for (CustomerAdd customerAdd : list) {
				xmlSerializer.startTag(null, "customerAdd");

				xmlSerializer.startTag(null, "customerName");
				xmlSerializer.text(customerAdd.getCustomerName() + "");
				xmlSerializer.endTag(null, "customerName");

				xmlSerializer.startTag(null, "password");
				xmlSerializer.text(customerAdd.getPassword() + "");
				xmlSerializer.endTag(null, "password");

				xmlSerializer.startTag(null, "email");
				xmlSerializer.text(customerAdd.getEmail() + "");
				xmlSerializer.endTag(null, "email");

				xmlSerializer.endTag(null, "customerAdd");
			}
			xmlSerializer.endDocument();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return stringWriter.toString();
	}

	public static String produceXmlPropose(ArrayList<ExpertAdvisor> list) {
		StringWriter stringWriter = new StringWriter();
		try {
			XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
			XmlSerializer xmlSerializer = factory.newSerializer();
			xmlSerializer.setOutput(stringWriter);
			xmlSerializer.startDocument("utf-8", true);
			for (ExpertAdvisor propose : list) {
				xmlSerializer.startTag(null, "expertAdvisor");

				xmlSerializer.startTag(null, "question");
				xmlSerializer.text(propose.getQuestion() + "");
				xmlSerializer.endTag(null, "question");

				xmlSerializer.startTag(null, "telNumber");
				xmlSerializer.text(propose.getTelNumber() + "");
				xmlSerializer.endTag(null, "telNumber");

				xmlSerializer.startTag(null, "email");
				xmlSerializer.text(propose.getEmail() + "");
				xmlSerializer.endTag(null, "email");

				xmlSerializer.startTag(null, "customerID");
				xmlSerializer.text(propose.getCustomerID() + "");
				xmlSerializer.endTag(null, "customerID");

				xmlSerializer.endTag(null, "expertAdvisor");
			}
			xmlSerializer.endDocument();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return stringWriter.toString();
	}

	public static String produceXmlAddressItem(ArrayList<AddressItem> list) {
		StringWriter stringWriter = new StringWriter();
		try {
			XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
			XmlSerializer xmlSerializer = factory.newSerializer();
			xmlSerializer.setOutput(stringWriter);
			xmlSerializer.startDocument("utf-8", true);
			for (AddressItem addressItem : list) {
				xmlSerializer.startTag(null, "addressItem");

				xmlSerializer.startTag(null, "name");
				xmlSerializer.text(addressItem.getName() + "");
				xmlSerializer.endTag(null, "name");

				xmlSerializer.startTag(null, "telNumber");
				xmlSerializer.text(addressItem.getTelNumber() + "");
				xmlSerializer.endTag(null, "telNumber");

				xmlSerializer.startTag(null, "address");
				xmlSerializer.text(addressItem.getAddress() + "");
				xmlSerializer.endTag(null, "address");

				xmlSerializer.startTag(null, "customerID");
				xmlSerializer.text(addressItem.getCustomerID() + "");
				xmlSerializer.endTag(null, "customerID");

				if (addressItem.getAddressID() != 0) {
					xmlSerializer.startTag(null, "addressID");
					xmlSerializer.text(addressItem.getAddressID() + "");
					xmlSerializer.endTag(null, "addressID");
				}
				xmlSerializer.endTag(null, "addressItem");
			}
			xmlSerializer.endDocument();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return stringWriter.toString();
	}

}
