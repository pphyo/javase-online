package com.jdc.app.service;

import java.sql.Connection;
import java.sql.PreparedStatement;

import com.jdc.app.entity.Author;
import com.jdc.app.util.ConnectionManager;

public class AuthorService {

	public void add(Author a) {
		try(Connection conn = ConnectionManager.getConnection();
				PreparedStatement stmt = conn.prepareStatement("")) {
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
