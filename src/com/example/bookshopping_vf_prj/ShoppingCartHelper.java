package com.example.bookshopping_vf_prj;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import android.content.res.Resources;
import android.util.Log;

public class ShoppingCartHelper {

	public static final String PRODUCT_INDEX = "PRODUCT_INDEX";
	public static final String CAT_INDEX = "CAT_INDEX";

	private static List<Product> catalog;
	private static List<String> categories;
	private static Map<String, Product> catlogMap = new HashMap<String, Product>();
	private static Map<String, ShoppingCartEntry> cartMap = new HashMap<String, ShoppingCartEntry>();

	public static List<String> getCategories() {

		if (categories == null) {
			// getCatFromServer(categories);
		}

		return categories;
	}

	public static List<Product> getCatalog(Resources res, int SpinPosition) {
		
		catalog = new Vector<Product>();

		switch (SpinPosition) {
	
		//Academic
		case 0:	
			catalog.add(new Product("Java Development", res
					.getDrawable(R.drawable.javafoundation), "David Flanagan",
					34.50, "ACA#0001"));
			catalog.add(new Product("Android Programming", res
					.getDrawable(R.drawable.android),
					"BILL PHILLIPS and BRIAN HARDY", 28.16, "ACA#0002"));
			catalog.add(new Product("Learn C++ for Game Development", res
					.getDrawable(R.drawable.c), "Bruce Sutherland", 40.10,
					"ACA#0003"));
			catalog.add(new Product("Civil Engineering Formulas", res
					.getDrawable(R.drawable.e4), "Tyler Hicks", 39.65,
					"ACA#0004"));
			catalog.add(new Product("Civil Engineering Reference Manual for the PE Exam", res
					.getDrawable(R.drawable.e5), "Michael R. Lindeburg PE", 402.80,
					"ACA#0005"));
			break;

		// Arts
		case 1:
			catalog.add(new Product("Java Art: A World History", res
					.getDrawable(R.drawable.a1), "Elke Linda Buchholz and Susanne Kaeppele",
					12.9, "ART#0001"));
			catalog.add(new Product("Art: Over 2,500 Works from Cave to Contemporary", res
					.getDrawable(R.drawable.a2),
					"Andrew Graham-Dixon", 29.00, "ART#0002"));
			catalog.add(new Product("Shut the Fuck Up and Create Your Fucking Art", res
					.getDrawable(R.drawable.a3), "Garrett Robinson", 14.10,
					"ART#0003"));
			catalog.add(new Product("The Art of Urban Sketching: Drawing On Location Around The World", res
					.getDrawable(R.drawable.a4), "Gabriel Campanario", 11.54,
					"ART#0004"));
			break;
		
		case 2:
			catalog.add(new Product("Pennyroyal Academy", res
					.getDrawable(R.drawable.c1), "M.A. Larson",
					12.16, "CHI#0001"));
			catalog.add(new Product("Pippi Longstocking", res
					.getDrawable(R.drawable.c2),
					"Astrid Lindgren, Louis S. Glanzman, Florence Lamborn", 3.94, "CHI#0002"));
			catalog.add(new Product("The Princess in Black", res
					.getDrawable(R.drawable.c3), "Shannon Hale, Dean Hale, LeUyen Pham", 9.48,
					"CHI#0003"));
			catalog.add(new Product("Emma and the Blue Genie", res
					.getDrawable(R.drawable.c4), "Cornelia Funke, Kerstin Meyer", 8.99,
					"CHI#0004"));
			catalog.add(new Product("The Day the Crayons Quit", res
					.getDrawable(R.drawable.c5), "Oliver Jeffers, Drew Daywalt", 10.75,
					"CHI#0005"));
			break;
		
		default:
			break;
		}

		return catalog;
	}

	/*
	 * public static Map<String, Product> getCatalogMap(Resources res) { if
	 * (catlogMap == null) { catlogMap = new HashMap<String, Product>();
	 * catlogMap.put("#0001", (new Product("iPhone 6",
	 * res.getDrawable(R.drawable.g1), "64GB White", 749.99, "#0001")));
	 * catlogMap.put("#0002", (new Product("iPhone 10",
	 * res.getDrawable(R.drawable.g2), "1024GB Black (Pre-order)", 999.99,
	 * "#0002"))); catlogMap.put("#0003", (new Product("iPhone 20",
	 * res.getDrawable(R.drawable.g3), "1024TB Black (Pre-order)", 9999.99,
	 * "#0003"))); }
	 * 
	 * return catlogMap; }
	 */

	public static void setQuantity(Product product, int quantity) {
		// Get the current cart entry
		ShoppingCartEntry curEntry = cartMap.get(product.itemID);

		// If the quantity is zero or less, remove the products
		if (quantity <= 0) {
			if (curEntry != null)
				removeProduct(product.itemID);
			return;
		}

		// If a current cart entry doesn't exist, create one
		if (curEntry == null) {
			curEntry = new ShoppingCartEntry(product, quantity);
			cartMap.put(product.itemID, curEntry);
			return;
		}

		// Update the quantity
		// Log.i("JohnnyLog", "HI");
		curEntry.setQuantity(quantity);
	}

	public static int getProductQuantity(Product product) {
		// Get the current cart entry
		ShoppingCartEntry curEntry = cartMap.get(product.itemID);

		if (curEntry != null)
			return curEntry.getQuantity();

		return 0;
	}

	public static void removeProduct(String productID) {
		cartMap.remove(productID);
	}

	public static List<Product> getCartList() {
		List<Product> cartList = new Vector<Product>(cartMap.keySet().size());
		for (String p : cartMap.keySet()) {
			cartList.add(cartMap.get(p).getProduct());
		}

		return cartList;
	}

}