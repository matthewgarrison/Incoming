package com.matthewgarrison.user;

public class Skin {

	private String name;
	private int idNumber;
	private boolean isAvailable;

	public Skin(String name, int idNumber, boolean isAvailable) {
		this.name = name;
		this.idNumber = idNumber;
		this.isAvailable = isAvailable;
	}

	public boolean getIsAvailable() {
		return isAvailable;
	}

	public void setIsAvailable(boolean available) {
		isAvailable = available;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getIdNumber() {
		return idNumber;
	}

	public void setIdNumber(int idNumber) {
		this.idNumber = idNumber;
	}
}