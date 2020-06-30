package com.jdc.app.entity;

import java.time.LocalDate;
import java.time.LocalTime;

import lombok.Data;

@Data
public class SaleDetail {

	private int id;
	private Sale sale;
	private Book book;
	private int unitPrice;
	private int quantity;
	private boolean delete;
	
	public LocalDate getSaleDate() {
		return sale.getSaleDate();
	}
	
	public LocalTime getSaleTime() {
		return sale.getSaleTime();
	}
	
	public String getBookName() {
		return book.getName();
	}
	
	public int getTax() {
		return (int) (getSubTotal() * 0.05);
	}
	
	public int getSubTotal() {
		return getQuantity() * getUnitPrice();
	}
	
	public int getTotal() {
		return getTax() + getSubTotal();
	}

}