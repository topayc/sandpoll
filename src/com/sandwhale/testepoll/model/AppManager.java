package com.sandwhale.testepoll.model;

import java.util.ArrayList;

import com.sandwhale.testepoll.R;

import android.content.Context;

public class AppManager {

	private static AppManager appManager;

	public ArrayList<Category> categories;

	private AppManager() {
	}

	public static AppManager getInstance() {
		if (appManager == null) {
			appManager = new AppManager();
		}
		return appManager;
	}

	public void init(Context context) {
		String[] leftCategoryTitles = context.getResources().getStringArray(
				R.array.left_menu_category);
		int[] leftCategoryIcons = new int[] { R.drawable.all, R.drawable.enter,
				R.drawable.com, R.drawable.fashion, R.drawable.life,
				R.drawable.travle, R.drawable.soc, R.drawable.relation,
				R.drawable.sports, R.drawable.etc };

		categories = new ArrayList<Category>();

		for (int i = 0; i < leftCategoryTitles.length; i++) {
			Category category = new Category();
			category.setName(leftCategoryTitles[i]);
			category.setIconResId(leftCategoryIcons[i]);
			category.setId(i);
			categories.add(category);
		}
	}

	public ArrayList<Category> getCategories() {
		return categories;
	}

	public void setCategories(ArrayList<Category> categories) {
		this.categories = categories;
	}

}
