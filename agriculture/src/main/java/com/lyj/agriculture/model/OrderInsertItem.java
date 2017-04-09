package com.lyj.agriculture.model;

public class OrderInsertItem {
	private int orderID;
	private int productID;
	private int productCount;
	private float partSum;
	private int farmID;

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

	public int getProductCount() {
		return productCount;
	}

	public void setProductCount(int productCount) {
		this.productCount = productCount;
	}

	public float getPartSum() {
		return partSum;
	}

	public void setPartSum(float partSum) {
		this.partSum = partSum;
	}

	public int getFarmID() {
		return farmID;
	}

	public void setFarmID(int farmID) {
		this.farmID = farmID;
	}

}
