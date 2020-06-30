package com.jdc.app.entity;

import java.time.LocalDate;

import lombok.Data;

@Data
public class Book {

	private int id;
	private String name;
	private int price;
	private String image;
	private String remark;
	private LocalDate releaseDate;

	private Author author;
	private Category category;
	
	public String getCategoryName() {
		return category.getName();
	}
	
	public String getAuthorName() {
		return author.getName();
	}

}