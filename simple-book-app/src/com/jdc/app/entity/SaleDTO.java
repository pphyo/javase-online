package com.jdc.app.entity;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class SaleDTO {

	private Sale sale;
	private List<SaleDetail> detail;
	
	public SaleDTO() {
		sale = new Sale();
		detail = new ArrayList<>();
	}
	
}
