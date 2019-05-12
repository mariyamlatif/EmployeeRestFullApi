package com.letsstartcoding.springbootrestapiexample.utility;

import org.springframework.hateoas.Link;

public class Response {
	
	private Object data;
	private long timestamp;
	private int status;
	private String error;
	private String exception;
	private String message;
	private Link path;
	
	public Response(Object data, long timestamp, int status, String error, String exception, String message, Link path) 
	{
		this.data = data;
		this.timestamp = timestamp;
		this.status = status;
		this.error = error;
		this.exception = exception;
		this.message = message;
		this.path = path;
	}

	public Object getData() {
		return data;
	}
	
	public void setData(Object data) {
		this.data = data;
	}
	
	public long getTimestamp() {
		return timestamp;
	}
	
	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}
	
	public int getStatus() {
		return status;
	}
	
	public void setStatus(int status) {
		this.status = status;
	}
	
	public String getError() {
		return error;
	}
	
	public void setError(String error) {
		this.error = error;
	}
	
	public String getException() {
		return exception;
	}
	
	public void setException(String exception) {
		this.exception = exception;
	}
	
	public String getMessage() {
		return message;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}
	
	public Link getPath() {
		return path;
	}
	
	public void setPath(Link path) {
		this.path = path;
	}

	

}
