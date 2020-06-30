package com.jdc.app.service;

import static com.jdc.app.util.SqlHelper.getSql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.jdc.app.entity.User;
import com.jdc.app.util.ConnectionManager;

public class UserService {
	
	private static UserService INSTANCE;
	
	private UserService() {}
	
	public static UserService getInstance() {
		if(null == INSTANCE) {
			INSTANCE = new UserService();
		}
		
		return INSTANCE;
	}
	
	public User login(String userName, String password) {
		
		try(Connection conn = ConnectionManager.getConnection();
				PreparedStatement stmt = conn.prepareStatement(getSql("user.find").concat(" and name like ? and password like ?"))) {
			
			stmt.setString(1, userName);
			stmt.setString(2, password);
			
			ResultSet rs = stmt.executeQuery();
			while(rs.next())
				return getObject(rs);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public User getObject(ResultSet rs) throws SQLException {
		User user = new User();
		user.setUserName(rs.getString("name"));
		user.setPassword(rs.getString("password"));
		return user;
	}

}
