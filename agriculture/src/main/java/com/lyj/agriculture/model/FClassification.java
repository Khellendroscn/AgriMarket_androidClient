package com.lyj.agriculture.model;

public class FClassification extends BaseModel {
	private int fClassificationID;
	private String fClassificationName;
	private String fClassificationImage;
	private String fClassificationDescription;
	public int getfClassificationID() {
		return fClassificationID;
	}
	public void setFClassificationID(int fClassificationID) {
		this.fClassificationID = fClassificationID;
	}
	public String getFClassificationName() {
		return fClassificationName;
	}
	public void setFClassificationName(String fClassificationName) {
		this.fClassificationName = fClassificationName;
	}
	public String getFClassificationImage() {
		return fClassificationImage;
	}
	public void setFClassificationImage(String fClassificationImage) {
		this.fClassificationImage = fClassificationImage;
	}
	public String getFClassificationDescription() {
		return fClassificationDescription;
	}
	public void setFClassificationDescription(String fClassificationDescription) {
		this.fClassificationDescription = fClassificationDescription;
	}

}
