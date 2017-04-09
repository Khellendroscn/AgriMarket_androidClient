package com.lyj.agriculture.model;

public class Exhibition {
	private String dateCreated;
	private int productID;
	private int exhibitionID;
	private String productImage;
	private String productName;

	public String getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(String dateCreated) {
		this.dateCreated = dateCreated;
	}

	public int getProductID() {
		return productID;
	}

	public void setProductID(int productID) {
		this.productID = productID;
	}

	public int getExhibitionID() {
		return exhibitionID;
	}

	public void setExhibitionID(int exhibitionID) {
		this.exhibitionID = exhibitionID;
	}

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
}
