package com.lyj.agriculture.model;

public class OrderEvaluateAdd {
	private int orderID;
	private int productID;
	private int star;
	private String orderEvaluateDescription;

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

	public int getStar() {
		return star;
	}

	public void setStar(int star) {
		this.star = star;
	}

	public String getOrderEvaluateDescription() {
		return orderEvaluateDescription;
	}

	public void setOrderEvaluateDescription(String orderEvaluateDescription) {
		this.orderEvaluateDescription = orderEvaluateDescription;
	}

}
