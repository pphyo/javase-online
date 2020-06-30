package com.jdc.app.view.custom;

import com.jdc.app.entity.Category;

import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class CategoryBox extends HBox{
	
	public CategoryBox(Category c) {
		getStyleClass().add("cat-box");
		Label name = new Label(c.getName());
		getChildren().add(name);
	}

}
