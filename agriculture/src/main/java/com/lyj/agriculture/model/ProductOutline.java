package com.lyj.agriculture.model;

public class ProductOutline {
	private int productID;
	private String productName;
	private float productPrice;
	private String productImage;
	private int recentSellCount;

	public int getProductID() {
		return productID;
	}

	public void setProductID(int productID) {
		this.productID = productID;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public float getProductPrice() {
		return productPrice;
	}

	public void setProductPrice(float productPrice) {
		this.productPrice = productPrice;
	}

	public String getProductImage() {
		return productImage;
	}

	public void setProductImage(String productImage) {
		this.productImage = productImage;
	}

	public int getRecentSellCount() {
		return recentSellCount;
	}

	public void setRecentSellCount(int recentSellCount) {
		this.recentSellCount = recentSellCount;
	}

}
