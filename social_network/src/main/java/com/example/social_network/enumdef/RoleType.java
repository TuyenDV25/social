package com.example.social_network.enumdef;

public enum RoleType {
	ADMIN(2), USER(1);

	private int code;

	RoleType(int code) {
		this.code = code;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}
}
