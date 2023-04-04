package com.hrmgr.responses;

public class GenericMessage {
	
	public GenericMessage(String message) {
		this.msg = message;
	}

	String msg;

	public String getMessage() {
		return msg;
	}

	public void setMessage(String message) {
		this.msg = message;
	}
	
}
