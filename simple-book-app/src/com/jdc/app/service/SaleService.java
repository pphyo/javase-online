package com.jdc.app.service;

import static com.jdc.app.util.SqlHelper.getSql;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Time;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.jdc.app.entity.Book;
import com.jdc.app.entity.Category;
import com.jdc.app.entity.Sale;
import com.jdc.app.entity.SaleDTO;
import com.jdc.app.entity.SaleDetail;
import com.jdc.app.util.ConnectionManager;

public class SaleService {
	
	private static SaleService INSTANCE;
	
	private SaleService() {}
	
	public static SaleService getInstance() {
		if(null == INSTANCE) {
			INSTANCE = new SaleService();
		}
		return INSTANCE;
	}
	
	public void save(SaleDTO dto) {
		if(dto.getSale().getId() == 0) {
			add(dto);
		} else {
			update(dto);
		}
	}
	
	public void add(SaleDTO saleDTO) {
		try(Connection conn = ConnectionManager.getConnection();
				PreparedStatement saleInsert = conn.prepareStatement(getSql("sale.insert"), Statement.RETURN_GENERATED_KEYS);
				PreparedStatement detailInsert = conn.prepareStatement(getSql("sd.insert"))) {
			
			Sale sale = saleDTO.getSale();
			saleInsert.setDate(1, Date.valueOf(sale.getSaleDate()));
			saleInsert.setTime(2, Time.valueOf(sale.getSaleTime()));
			saleInsert.setInt(3, sale.getTax());
			saleInsert.executeUpdate();
			
			ResultSet rs = saleInsert.getGeneratedKeys();
			
			while(rs.next()) {
				
				int saleId = rs.getInt(1);
				
				for(SaleDetail sd : saleDTO.getDetail()) {
					detailInsert.setInt(1, sd.getQuantity());
					detailInsert.setInt(2, sd.getUnitPrice());
					detailInsert.setInt(3, sd.getBook().getId());
					detailInsert.setInt(4, saleId);
					detailInsert.addBatch();
				}
				
				detailInsert.executeBatch();
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void update(SaleDTO saleDTO) {
		try(Connection conn = ConnectionManager.getConnection();
				PreparedStatement insert = conn.prepareStatement(getSql("sd.insert"));
				PreparedStatement update = conn.prepareStatement(getSql("sd.update"));
				PreparedStatement delete = conn.prepareStatement(getSql("sd.delete"))) {
			
			for(SaleDetail sd : saleDTO.getDetail()) {
				
				if(sd.getId() == 0) {
					insert.setInt(1, sd.getQuantity());
					insert.setInt(2, sd.getUnitPrice());
					insert.setInt(3, sd.getBook().getId());
					insert.setInt(4, sd.getSale().getId());
					insert.executeUpdate();
				} else {
					if(sd.isDelete()) {
						delete.setInt(1, sd.getId());
						delete.executeUpdate();
					} else {
						update.setInt(1, sd.getQuantity());
						update.setInt(2, sd.getUnitPrice());
						update.setInt(3, sd.getId());
						update.executeUpdate();
					}
				}
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public List<Sale> findSale(LocalDate from, LocalDate to) {
		List<Sale> result = new ArrayList<>();
		StringBuilder sb = new StringBuilder(getSql("sale.find"));
		List<Object> params = new LinkedList<>();
		
		if(null != from && from.isBefore(to)) {
			sb.append(" and sale_date >= ?");
			params.add(Date.valueOf(from));
		}
		
		if(null != to && to.isAfter(from)) {
			sb.append(" and sale_date <= ?");
			params.add(Date.valueOf(to));
		}
		
		try(Connection conn = ConnectionManager.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sb.toString())) {
			
			for(int i = 0; i < params.size(); i++) {
				stmt.setObject(i + 1, params.get(i));
			}
			
			ResultSet rs = stmt.executeQuery();
			while(rs.next()) {
				Sale s = new Sale();
				s.setId(rs.getInt(1));
				s.setSaleDate(rs.getDate(2).toLocalDate());
				s.setSaleTime(rs.getTime(3).toLocalTime());
				s.setTax(rs.getInt(4));
				s.setCount(rs.getInt(5));
				s.setSubTotal(rs.getInt(6));
				result.add(s);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public List<SaleDetail> findSaleDetail(Category category, String bookName, LocalDate from, LocalDate to) {
		List<SaleDetail> result = new ArrayList<>();
		StringBuilder sb = new StringBuilder(getSql("sd.find"));
		List<Object> params = new LinkedList<>();
		
		if(null != category) {
			sb.append(" and c.name like ?");
			params.add(category.getName());
		}
		
		if(null != bookName && !bookName.isEmpty()) {
			sb.append(" and b.name like ?");
			params.add("%".concat(bookName).concat("%"));
		}
		
		if(null != from && from.isBefore(to)) {
			sb.append(" and sale_date >= ?");
			params.add(Date.valueOf(from));
		}
		
		if(null != to && to.isAfter(from)) {
			sb.append(" and sale_date <= ?");
			params.add(Date.valueOf(to));
		}
		
		try(Connection conn = ConnectionManager.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sb.toString())) {
			
			for(int i = 0; i < params.size(); i++) {
				stmt.setObject(i + 1, params.get(i));
			}
			
			ResultSet rs = stmt.executeQuery();
			while(rs.next()) {
				SaleDetail sd = new SaleDetail();
				sd.setId(rs.getInt("id"));
				sd.setQuantity(rs.getInt("quantity"));
				sd.setUnitPrice(rs.getInt("unit_price"));
				sd.setDelete(true);
				
				Sale s = new Sale();
				s.setId(rs.getInt("sale_id"));
				s.setTax(rs.getInt("tax"));
				s.setSaleDate(rs.getDate("sale_date").toLocalDate());
				s.setSaleTime(rs.getTime("sale_time").toLocalTime());
				
				Book b = new Book();
				b.setId(rs.getInt("book_id"));
				b.setName(rs.getString("book_name"));

				sd.setBook(b);
				sd.setSale(s);
				
				result.add(sd);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

}