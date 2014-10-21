package com.example.bookshopping_vf_prj;

import android.graphics.drawable.Drawable;

public class Product {

	public String title;
	public Drawable productImage;
	public String description;
	public double price;
	public boolean selected;
	public String itemID;
	
	
	public int quantity;

	public Product(String title, Drawable productImage, String description,
			double price, String id) {
		this.title = title;
		this.productImage = productImage;
		this.description = description;
		this.price = price;
		this.itemID = id;
	}

}