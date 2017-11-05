package com.poc.spring.data.redis.repo;

import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import com.poc.spring.data.redis.model.UserToken;

@Repository
public class UserTokenRepository {

	@Autowired
	private RedisTemplate<String, Object> redisTemplate;
	private HashOperations<String, String, UserToken> hashOperations;
	private static final String KEY = "Token";

	@PostConstruct
	private void init() {
		hashOperations = redisTemplate.opsForHash();
	}

	public UserToken createUserToken(UserToken token) {
		hashOperations.put(KEY+token.getToken(),token.getToken(), token);
		redisTemplate.expire(KEY+token.getToken(),token.getExpiration(),TimeUnit.SECONDS);
		return token;
	}

	public boolean hasUserToken(UserToken token) {
		boolean isValid = hashOperations.hasKey(KEY+token.getToken(), token.getToken());
		return isValid;
	}
	
	public UserToken getUserToken(UserToken token) {
		return (UserToken)hashOperations.get(KEY+token.getToken(), token.getToken());		
	}

	public UserToken updateUserToken(UserToken token) {
		hashOperations.delete(KEY+token.getToken(), token.getToken());
		hashOperations.put(KEY+token.getToken(), token.getToken(), token);
		return token;
	}

}
