package com.lyj.agriculture.model;

public class OrderEvaluate {
	private int star;
	private String orderEvaluateDescription;
	private String dateCreate;
	private String orderEvaluateDateCreate;
	private String customerName;

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

	public String getDateCreate() {
		return dateCreate;
	}

	public void setDateCreate(String dateCreate) {
		this.dateCreate = dateCreate;
	}

	public String getOrderEvaluateDateCreate() {
		return orderEvaluateDateCreate;
	}

	public void setOrderEvaluateDateCreate(String orderEvaluateDateCreate) {
		this.orderEvaluateDateCreate = orderEvaluateDateCreate;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
}
