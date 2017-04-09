package com.lyj.agriculture.model;

public class ProductDetail {
	private int productID;
	private String productName;
	private float productPrice;
	private String productImage;
	private String productSize;
	private String productPlace;
	private int productCount;
	private int farmID;
	private String farmName;
	private float avgStar;
	private int evaluateCount;
	private String productDescriptionString;

	public String getProductDescriptionString() {
		return productDescriptionString;
	}

	public void setProductDescriptionString(String productDescriptionString) {
		this.productDescriptionString = productDescriptionString;
	}

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

	public String getProductSize() {
		return productSize;
	}

	public void setProductSize(String productSize) {
		this.productSize = productSize;
	}

	public String getProductPlace() {
		return productPlace;
	}

	public void setProductPlace(String productPlace) {
		this.productPlace = productPlace;
	}

	public int getProductCount() {
		return productCount;
	}

	public void setProductCount(int productCount) {
		this.productCount = productCount;
	}

	public int getFarmID() {
		return farmID;
	}

	public void setFarmID(int farmID) {
		this.farmID = farmID;
	}

	public String getFarmName() {
		return farmName;
	}

	public void setFarmName(String farmName) {
		this.farmName = farmName;
	}

	public float getAvgStar() {
		return avgStar;
	}

	public void setAvgStar(float avgStar) {
		this.avgStar = avgStar;
	}

	public int getEvaluateCount() {
		return evaluateCount;
	}

	public void setEvaluateCount(int evaluateCount) {
		this.evaluateCount = evaluateCount;
	}

}
