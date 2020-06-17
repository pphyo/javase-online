package com.jdc.app.service;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.jdc.app.entity.Book;
import com.jdc.app.util.ConnectionManager;

public class BookService {
	
	private static BookService INSTANCE;
	
	private BookService() {}
	
	public static BookService getInstance() {
		if(null == INSTANCE)
			INSTANCE = new BookService();
		return INSTANCE;
	}
	
	public void add(Book book) {
		String sql = "insert into book (name, price, released_date, remark, category_id, author_id) values (?, ?, ?, ?, ?, ?)";
	
		try(Connection conn = ConnectionManager.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)) {
			
			stmt.setString(1, book.getName());
			stmt.setInt(2, book.getPrice());
			stmt.setDate(3, Date.valueOf(book.getReleaseDate()));
			stmt.setString(4, book.getRemark());
			stmt.setInt(5, book.getCategory().getId());
			stmt.setInt(6, book.getAuthor().getId());
			stmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	public void delete(Book book) {
		String sql = "delete from book where id = ?";
		
		try(Connection conn = ConnectionManager.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)) {
			
			stmt.setInt(1, book.getId());
			stmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public Book getObject(ResultSet rs) throws SQLException {
		Book b = new Book();
		b.setId(rs.getInt("id"));
		b.setName(rs.getString(""));
		
		return b;
	}
	
}
