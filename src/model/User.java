package model;

import java.util.LinkedList;

public class User {

	private String userID, email, password, name, phone, address, dateOfBirth, postCode, roleID;
	
	public User(String userID) {
		this.userID = userID;
	}

	public void setInfo(LinkedList<String> info) {
		email = info.get(1);
		password = info.get(2);
		name = info.get(3);
		phone = info.get(4);
		address = info.get(5);
		dateOfBirth = info.get(6);
		postCode = info.get(7);
		roleID = info.get(8);
	}
	
	public String getEmail() {
		return email;
	}
	
	public String getPassword() {
		return password;
	}
	
	public String getName() {
		return name;
	}
	
	public String getPhone() {
		return phone;
	}
	
	public String getAddress() {
		return address;
	}
	
	public String getDateOfBirth() {
		return dateOfBirth;
	}
	
	public String getPostCode() {
		return postCode;
	}
	
	public String getRoleID() {
		return roleID;
	}
	
}
