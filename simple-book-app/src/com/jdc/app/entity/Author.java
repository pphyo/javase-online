package com.jdc.app.entity;

import lombok.Data;

@Data
public class Author {

	private int id;
	private String name;
	private int age;
	private String country;

	@Override
	public String toString() {
		return name;
	}
	
}