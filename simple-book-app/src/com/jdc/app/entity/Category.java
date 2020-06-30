package com.jdc.app.entity;

import lombok.Data;

@Data
public class Category {

	private int id;
	private String name;

	public Category() {}
	
	public Category(String name) {
		this.name = name;
	}
	
	@Override
	public String toString() {
		return name;
	}
}