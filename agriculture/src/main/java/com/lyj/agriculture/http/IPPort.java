package com.lyj.agriculture.http;

/**
 * 
 * @author LiYajun 服务器常用地址
 * 
 */
public class IPPort {
	public static final String URL_BASE_SERVERCE = "http://192.168.137.1:8080/services/";
	public static final String URL_FCLASSIFICATION = URL_BASE_SERVERCE + "fclassification";
	public static final String URL_PRODUCTOUTLINE = URL_BASE_SERVERCE
			+ "productoutline/query?fClassificationID=";
	public static final String URL_PORPSEADD = URL_BASE_SERVERCE + "expertadvisor/add";
	public static final String URL_ADDRESSADD = URL_BASE_SERVERCE + "address/add";
	
	public static final String URL_ADDRESSEDIT = URL_BASE_SERVERCE + "address/edit";
	public static final String URL_PRODUCTDETAIL = URL_BASE_SERVERCE + "productdetail/query?productID=";
	public static final String URL_SHOPPINGCARDGET = URL_BASE_SERVERCE + "shoppingcart/query?customerID=";
	public static final String URL_SHOPPINGCARDDELETE = URL_BASE_SERVERCE + "shoppingcart/delete/query?customerID=";
	
	public static final String URL_SHOPPINGCARDPOST = URL_BASE_SERVERCE + "shoppingcart/add";
	public static final String URL_DEFAULTADDRESS = URL_BASE_SERVERCE + "defaultaddress/query?customerID=";
	public static final String URL_PRODUCTDESCRIPTION = URL_BASE_SERVERCE + "productdescription/query?productID=";
	public static final String URL_PRODUCTEVALUATE = URL_BASE_SERVERCE + "orderevaluate/query?productID=";
	public static final String URL_ORDERITEMPOST = URL_BASE_SERVERCE + "orderinsertitem/add";
	public static final String URL_ORDERPOST = URL_BASE_SERVERCE + "orderinsert/add";
	public static final String URL_ORDEROUTLINE = URL_BASE_SERVERCE + "orderoutline/query?customerID=";
	public static final String URL_ORDEREVALUATEADD = URL_BASE_SERVERCE + "orderevaluateadd";
	public static final String URL_MYPRODUCTOEDEREVAUATE = URL_BASE_SERVERCE + "myproductevaluate/query?customerID=";

	public static final String URL_CUSTOMERADD = URL_BASE_SERVERCE + "customeradd";
	public static final String URL_CUSTOMERCONFIRM = URL_BASE_SERVERCE + "customerconfirm";
	
	public static final String URL_ADDRESS = URL_BASE_SERVERCE + "address/query?customerID=";

	public static final String URL_NEWSOUTLINE = URL_BASE_SERVERCE + "newoutline";
	public static final String URL_NEWOUTLINEDETAIL = URL_BASE_SERVERCE +"newoutline/newdetail/query?newID=";
	public static final String URL_PRODUCTOUTLINESEARCH =URL_BASE_SERVERCE+ "productoutline/search/query?fClassificationID=";

	public static final String URL_CHARAGIEXHIBITION = URL_BASE_SERVERCE +"exhibition";
}
