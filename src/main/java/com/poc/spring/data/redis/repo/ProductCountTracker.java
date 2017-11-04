package com.poc.spring.data.redis.repo;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Repository;

import com.poc.spring.data.redis.model.Product;

@Repository
public class ProductCountTracker {

	private static final RedisSerializer<String> STRING_SERIALIZER = new StringRedisSerializer();

	@Autowired
	RedisTemplate<String, Long> redis;

	@Autowired
	RedisTemplate<String, String> stringRedis;

	public void updateTotalProductCountWithLong(Product p) {
		// Use a namespaced Redis key
		String productCountKey = "product-counts:" + p.getId();
		// Get the helper for getting and setting values
		ValueOperations<String, Long> values = redis.opsForValue();
		// Initialize the count if not present
		values.setIfAbsent(productCountKey, 0L);
		Long totalOfProductInAllCarts = values.increment(productCountKey, 1);
	}

	public void updateTotalProductCountWithHash(Product p) {

		// Use the standard String serializer for all keys and values

		HashOperations<String, String, String> hashOps = stringRedis.opsForHash();
		// Access the attributes for the Product
		String productAttrsKey = "products:attrs:" + p.getId();
		Map<String, String> attrs = new HashMap<String, String>();
		// Fill attributes
		attrs.put("name", "iPad");
		attrs.put("deviceType", "tablet");
		attrs.put("color", "black");
		attrs.put("price", "499.00");
		hashOps.putAll(productAttrsKey, attrs);
	}

}
