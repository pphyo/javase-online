package com.jdc.app.view;

import java.time.LocalDate;
import java.util.List;

import com.jdc.app.entity.Category;
import com.jdc.app.entity.SaleDetail;
import com.jdc.app.service.CategoryService;
import com.jdc.app.service.SaleService;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class SaleHistory {
	
	@FXML
	private ComboBox<Category> category;
	@FXML
	private TextField bookName;
	@FXML
	private DatePicker from;
	@FXML
	private DatePicker to;
	@FXML
	private TableView<SaleDetail> tblList;
	
	private CategoryService catService;
	private SaleService saleService;
	
	public void search() {
		tblList.getItems().clear();
		List<SaleDetail> sdList = saleService.findSaleDetail(category.getValue(), bookName.getText(), from.getValue(), to.getValue());
		tblList.getItems().addAll(sdList);
	}
	
	public void clear() {
		category.setValue(null);
		bookName.clear();
		from.setValue(LocalDate.now().minusMonths(1));
		to.setValue(LocalDate.now());
	}
	
	@FXML
	private void initialize() {
		catService = CategoryService.getInstance();
		saleService = SaleService.getInstance();
		
		category.getItems().addAll(catService.findAll());
		clear();
		search();
	}

}