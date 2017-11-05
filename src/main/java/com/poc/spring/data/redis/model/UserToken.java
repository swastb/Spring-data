package com.poc.spring.data.redis.model;

import java.io.Serializable;

import org.springframework.data.redis.core.TimeToLive;

public class UserToken implements Serializable{
	private String token;
	private String userId;
	private int clientId;

	@TimeToLive
	private Long expiration;

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public int getClientId() {
		return clientId;
	}

	public void setClientId(int clientId) {
		this.clientId = clientId;
	}

	public Long getExpiration() {
		return expiration;
	}

	public void setExpiration(Long expiration) {
		this.expiration = expiration;
	}

	@Override
	public String toString() {
		return "UserToken [token=" + token + ", userId=" + userId + ", clientId=" + clientId + ", expiration="
				+ expiration + "]";
	}

	
	
}
