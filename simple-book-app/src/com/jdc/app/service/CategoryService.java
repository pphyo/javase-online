package com.jdc.app.service;

import static com.jdc.app.util.SqlHelper.getSql;

import java.io.File;
import java.nio.file.Files;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.jdc.app.entity.Category;
import com.jdc.app.util.ConnectionManager;

public class CategoryService {
	
	private static CategoryService INSTANCE;
	
	private CategoryService() {}
	
	public static CategoryService getInstance() {
		if(null == INSTANCE)
			INSTANCE = new CategoryService();
		return INSTANCE;
	}
	
	public void add(Category c) {
		try(Connection conn = ConnectionManager.getConnection();
				PreparedStatement stmt = conn.prepareStatement(getSql("cat.insert"), Statement.RETURN_GENERATED_KEYS)) {
			
			stmt.setString(1, c.getName());
			stmt.executeUpdate();
			
			ResultSet rs = stmt.getGeneratedKeys();
			while(rs.next()) {
				c.setId(rs.getInt(1));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void update(Category c) {
		try(Connection conn = ConnectionManager.getConnection();
				PreparedStatement stmt = conn.prepareStatement(getSql("cat.update"))) {
			
			stmt.setString(1, c.getName());
			stmt.setInt(2, c.getId());
			stmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void delete(Category c) {
		try(Connection conn = ConnectionManager.getConnection();
				PreparedStatement stmt = conn.prepareStatement(getSql("aut.delete"))) {
			
			stmt.setInt(1, c.getId());
			stmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public List<Category> findAll() {
//		List<Category> list = new ArrayList<>();
//		
//		try(Connection conn = ConnectionManager.getConnection();
//				PreparedStatement stmt = conn.prepareStatement(getSql("cat.find"))) {
//			
//			ResultSet rs = stmt.executeQuery();
//			while(rs.next())
//				list.add(getObject(rs));
//			
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return list;
		return findByName(null);
	}
	
	public List<Category> findByName(String name) {
		ArrayList<Category> list = new ArrayList<>();
		boolean isConcat = null != name && !name.isEmpty();
		
		String sql = getSql("cat.find");
		
		if(isConcat) {
			sql = sql.concat(" and name like ?");
		}
		
		try(Connection conn = ConnectionManager.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)) {

			if(isConcat) {
				stmt.setString(1, "%".concat(name).concat("%"));
			}
			
			ResultSet rs = stmt.executeQuery();
			while(rs.next())
				list.add(getObject(rs));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public Category getObject(ResultSet rs) throws SQLException {
		Category cat = new Category();
		cat.setId(rs.getInt("id"));
		cat.setName(rs.getString("name"));
		return cat;
	}
	
	public void upload(File file) {
		try {
			Files.readAllLines(file.toPath())
					.stream()
					.map(a -> new Category(a))
					.forEach(this::add);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
