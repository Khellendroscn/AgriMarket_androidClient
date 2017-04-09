package com.lyj.agriculture.model;

public class MyProductEvaluate {
	private String productImage;
	private String productName;
	private int orderID;
	private int productID;
	private String evaluateOrNot;

	public String getProductImage() {
		return productImage;
	}

	public void setProductImage(String productImage) {
		this.productImage = productImage;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public int getOrderID() {
		return orderID;
	}

	public void setOrderID(int orderID) {
		this.orderID = orderID;
	}

	public int getProductID() {
		return productID;
	}

	public void setProductID(int productID) {
		this.productID = productID;
	}

	public String getEvaluateOrNot() {
		return evaluateOrNot;
	}

	public void setEvaluateOrNot(String evaluateOrNot) {
		this.evaluateOrNot = evaluateOrNot;
	}

}
