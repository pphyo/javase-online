package com.jdc.app.service;

import static com.jdc.app.util.SqlHelper.getSql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.jdc.app.entity.Author;
import com.jdc.app.util.ConnectionManager;

public class AuthorService {
	
	private static AuthorService INSTANCE;
	
	private AuthorService() {}
	
	public static AuthorService getInstance() {
		if(null == INSTANCE)
			INSTANCE = new AuthorService();
		return INSTANCE;
	}
	
	public void add(Author a) {
		
		try(Connection conn = ConnectionManager.getConnection();
				PreparedStatement stmt = conn.prepareStatement(getSql("aut.insert"), Statement.RETURN_GENERATED_KEYS)) {
			
			stmt.setString(1, a.getName());
			stmt.setInt(2, a.getAge());
			stmt.setString(3, a.getCountry());
			stmt.executeUpdate();

			ResultSet rs = stmt.getGeneratedKeys();
			while(rs.next())
				a.setId(rs.getInt(1));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void update(Author a) {
		try(Connection conn = ConnectionManager.getConnection();
				PreparedStatement stmt = conn.prepareStatement(getSql("aut.update"))) {
			
			stmt.setString(1, a.getName());
			stmt.setInt(2, a.getAge());
			stmt.setString(3, a.getCountry());
			stmt.setInt(4, a.getId());
			stmt.executeUpdate();			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void delete(Author a) {
		try(Connection conn = ConnectionManager.getConnection();
				PreparedStatement stmt = conn.prepareStatement(getSql("aut.delete"))) {
			
			stmt.setInt(1, a.getId());
			stmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public List<Author> findAll() {
		return findByParam(null, 0, null);
	}
	
	public List<Author> findByParam(String name, int age, String country) {
		List<Author> list = new ArrayList<>();
		StringBuffer sb = new StringBuffer(getSql("aut.find"));
		List<Object> params = new LinkedList<>();
		
		if(null != name && !name.isEmpty()) {
			sb.append(" and name like ?");
			params.add("%".concat(name).concat("%"));
		}
		
		if(age > 0) {
			sb.append(" and age >= ?");
			params.add(age);
		}
		
		if(null != country && !country.isEmpty()) {
			sb.append(" and country like ?");
			params.add("%".concat(country).concat("%"));
		}
		
		try(Connection conn = ConnectionManager.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sb.toString())) {
			
			for(int i = 0; i < params.size(); i++) {
				stmt.setObject(i + 1, params.get(i));
			}
			
			ResultSet rs = stmt.executeQuery();
			while(rs.next()) {
				list.add(getObject(rs));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public Author getObject(ResultSet rs) throws SQLException {
		Author aut = new Author();
		aut.setId(rs.getInt(1));
		aut.setName(rs.getString(2));
		aut.setAge(rs.getInt(3));
		aut.setCountry(rs.getString(4));
		return aut;
	}

}