package com.example.social_network.enumdef;

public enum PostType {
	ONLY_ME(3), PUBLIC(1);

	private int code;

	PostType(int code) {
		this.code = code;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}
}
