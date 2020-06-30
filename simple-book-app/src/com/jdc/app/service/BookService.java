package com.jdc.app.service;

import static com.jdc.app.util.SqlHelper.*;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.jdc.app.entity.Author;
import com.jdc.app.entity.Book;
import com.jdc.app.entity.Category;
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
		try(Connection conn = ConnectionManager.getConnection();
				PreparedStatement stmt = conn.prepareStatement(getSql("book.insert"))) {
			
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
	
	public void update(Book book) {
		try(Connection conn = ConnectionManager.getConnection();
				PreparedStatement stmt = conn.prepareStatement(getSql("book.update"))) {
			
			stmt.setString(1, book.getName());
			stmt.setInt(2, book.getPrice());
			stmt.setDate(3, Date.valueOf(book.getReleaseDate()));
			stmt.setString(4, book.getRemark());			
			stmt.setInt(5, book.getCategory().getId());
			stmt.setInt(6, book.getAuthor().getId());
			stmt.setInt(7, book.getId());
			stmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void delete(Book book) {
		try(Connection conn = ConnectionManager.getConnection();
				PreparedStatement stmt = conn.prepareStatement(getSql("book.delete"))) {
			
			stmt.setInt(1, book.getId());
			stmt.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public List<Book> findAll() {
		return findByParams(null, null, null, null);
	}
	
	public List<Book> findByParams(Category category, Author authorName, String bookName, LocalDate releaseDate) {
		List<Book> list = new ArrayList<>();
		StringBuilder sb = new StringBuilder(getSql("book.find"));
		List<Object> params = new LinkedList<>();
		
		if(null != category) {
			sb.append(" and c.name like ?");
			params.add(category.getName());
		}
		
		if(null != authorName) {
			sb.append(" and a.name like ?");
			params.add(authorName.getName());
		}
		
		if(null != bookName && !bookName.isEmpty()) {
			sb.append(" and b.name like ?");
			params.add("%".concat(bookName).concat("%"));
		}
		
		if(null != releaseDate && releaseDate.isBefore(LocalDate.now())) {
			sb.append(" and release_date >= ?");
			params.add(Date.valueOf(releaseDate));
		}
		
		try(Connection conn = ConnectionManager.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sb.toString())) {
			
			for (int i = 0; i < params.size(); i++) {
				stmt.setObject(i + 1, params.get(i));
			}
			
			ResultSet rs = stmt.executeQuery();
			while(rs.next())
				list.add(getObject(rs));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return list;
	}
	
	public Book getObject(ResultSet rs) throws SQLException {
		Book book = new Book();
		book.setId(rs.getInt("id"));
		book.setName(rs.getString("book_name"));
		book.setPrice(rs.getInt("price"));
		book.setReleaseDate(rs.getDate("release_date").toLocalDate());
		book.setRemark(rs.getString("remark"));
		
		Category cat = new Category();
		cat.setName(rs.getString("category_name"));
		
		Author auth = new Author();
		auth.setName(rs.getString("author_name"));
		auth.setAge(rs.getInt("age"));
		auth.setCountry(rs.getString("country"));
		
		book.setCategory(cat);
		book.setAuthor(auth);
		
		return book;
	}
	
	public void imageUpload(Book book) {
		try(Connection conn = ConnectionManager.getConnection();
				PreparedStatement stmt = conn.prepareStatement(getSql("book.img"))) {
			
			stmt.setString(1, book.getImage());
			stmt.setInt(2, book.getId());
			stmt.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public String findImage(Book book) {
		try(Connection conn = ConnectionManager.getConnection();
				PreparedStatement stmt = conn.prepareStatement(getSql("book.img.find"))) {
			
			stmt.setInt(1, book.getId());
			ResultSet rs = stmt.executeQuery();
			while(rs.next())
				return rs.getString("book_image");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
}