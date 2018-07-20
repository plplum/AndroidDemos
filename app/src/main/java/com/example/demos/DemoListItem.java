package com.example.demos;

public class DemoListItem {

	private String name;
	private String description;
	private int iconResId;

	public DemoListItem(int iconResId, String title, String description) {
		this.iconResId = iconResId;
		this.name = title;
		this.description = description;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getIconResId() {
		return iconResId;
	}

	public void setIconResId(int iconResId) {
		this.iconResId = iconResId;
	}
	
	

}
