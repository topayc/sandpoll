package com.sandwhale.testepoll.model;

public class Category {
	public int id;
	public String name;
	public int iconResId;
	
	public Category(){}
	
	public void loadCategory(){
		
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getIconResId() {
		return iconResId;
	}

	public void setIconResId(int iconResId) {
		this.iconResId = iconResId;
	}
}
