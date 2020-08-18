package com.mahi.dkmart;

//Please carefully read README file
//Read all the comments carefully...
///////////////////////////////////////////////////////////////////////////////////////////

public class CategoryItem {
    private String mImage;
    private String mName;
    private String mQuantity;
    private String mOldPrice;
    private String mOff;
    private String mPrice;
	
///////////////////////////////////////////////////////////////////////////////////////////
	//Create constructor of each member variable... and getter of rach member variable.

    public CategoryItem(String mImage, String mName, String mQuantity, String mOldPrice, String mOff, String mPrice) {
        this.mImage = mImage;
        this.mName = mName;
        this.mQuantity = mQuantity;
        this.mOldPrice = mOldPrice;
        this.mOff = mOff;
        this.mPrice = mPrice;
    }

///////////////////////////////////////////////////////////////////////////////////////////
	//These are the getter of each variable...!!

    public String getmImage() {
        return mImage;
    }

    public String getmName() {
        return mName;
    }

    public String getmQuantity() {
        return mQuantity;
    }

    public String getmOldPrice() {
        return mOldPrice;
    }

    public String getmOff() {
        return mOff;
    }

    public String getmPrice() {
        return mPrice;
    }
}
