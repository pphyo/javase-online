package com.jdc.app.view;

import com.jdc.app.entity.User;
import com.jdc.app.service.UserService;
import com.jdc.app.util.AppException;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class Login {
	
	@FXML
	private Label info;
	@FXML
	private TextField userName;
	@FXML
	private PasswordField password;
	
	private UserService usrService;
	
	public void login() {
		try {
			
			if(userName.getText().isEmpty())
				throw new AppException("Please enter user name!");
			if(password.getText().isEmpty())
				throw new AppException("Please enter password!");
			
			User user = usrService.login(userName.getText(), password.getText());

			if(null == user)
				throw new AppException("User not found!");
			
			if(!user.getUserName().equals(userName.getText()))
				throw new AppException("Please enter correct user name!");
			if(!user.getPassword().equals(password.getText()))
				throw new AppException("Please enter correct password!");
			
			MainFrame.show();
			close();
			
		} catch (Exception e) {
			e.printStackTrace();
			info.setText(e.getMessage());
		}
	}
	
	public void close() {
		info.getScene().getWindow().hide();
	}
	
	@FXML
	private void initialize() {
		usrService = UserService.getInstance();
	}

}