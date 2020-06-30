package com.jdc.app.entity;

import java.time.LocalDate;
import java.time.LocalTime;

import lombok.Data;

@Data
public class Sale {

	private int id;
	private LocalDate saleDate;
	private LocalTime saleTime;
	private int tax;
	private int count;
	private int subTotal;

}