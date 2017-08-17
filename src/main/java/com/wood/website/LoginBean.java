package com.wood.website;

public class LoginBean {
	
	private String userName;
	private String password;
	private String oldPassword;
	private String newPassword;
	
	public String getUserName(){
		return userName;
	}
	
	public void setUserName(String userName){
		this.userName = userName;
	}
	
	public String getPassword(){
		return password;
	}
	
	public void setPassword(String password){
		this.password = password;
	}
	
	public String getOldPassword(){
		return oldPassword;
	}
	
	public void setOldPassword(String oldPassword){
		this.oldPassword = oldPassword;
	}
	
	public String getNewPassword(){
		return newPassword;
	}
	
	public void setNewPassword(String newPassword){
		this.newPassword = newPassword;
	}
}
