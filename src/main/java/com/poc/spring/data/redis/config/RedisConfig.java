package com.poc.spring.data.redis.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.data.redis.serializer.GenericToStringSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import com.poc.spring.data.redis.queue.MessagePublisher;
import com.poc.spring.data.redis.queue.RedisMessagePublisher;
import com.poc.spring.data.redis.queue.RedisMessageSubscriber;

@Configuration
@ComponentScan("com.baeldung.spring.data.redis")
public class RedisConfig {

	private static final StringRedisSerializer STRING_SERIALIZER = new StringRedisSerializer();
	
	@Bean
	JedisConnectionFactory jedisConnectionFactory() {
		JedisConnectionFactory jedisConFactory = new JedisConnectionFactory();
		jedisConFactory.setHostName("localhost");
		jedisConFactory.setPort(6379);
		return jedisConFactory;
	}

	@Bean
	public RedisTemplate<String,String> redisStringTemplate() {
		final RedisTemplate<String,String> template = new RedisTemplate<String,String>();
		template.setConnectionFactory(jedisConnectionFactory());
		template.setValueSerializer(new GenericToStringSerializer<Object>(Object.class));
		template.setKeySerializer(STRING_SERIALIZER);
		template.setHashKeySerializer(STRING_SERIALIZER);
		template.setHashValueSerializer(STRING_SERIALIZER);
		return template;
	}
	
	@Bean
	public RedisTemplate<String, Object> redisTemplate() {
		final RedisTemplate<String, Object> template = new RedisTemplate<String, Object>();
		template.setConnectionFactory(jedisConnectionFactory());
		template.setValueSerializer(new GenericToStringSerializer<Object>(Object.class));
		return template;
	}

	@Bean
	public RedisTemplate<String, Long> longTemplate() {

		
		RedisTemplate<String, Long> tmpl = new RedisTemplate<String, Long>();
		tmpl.setConnectionFactory(jedisConnectionFactory());
		tmpl.setKeySerializer(STRING_SERIALIZER);
		tmpl.setValueSerializer(LongSerializer.INSTANCE);
		return tmpl;
	}

	@Bean
	MessageListenerAdapter messageListener() {
		return new MessageListenerAdapter(new RedisMessageSubscriber());
	}

	@Bean
	RedisMessageListenerContainer redisContainer() {
		final RedisMessageListenerContainer container = new RedisMessageListenerContainer();
		container.setConnectionFactory(jedisConnectionFactory());
		container.addMessageListener(messageListener(), topic());
		return container;
	}

	@Bean
	MessagePublisher redisPublisher() {
		return new RedisMessagePublisher(redisTemplate(), topic());
	}

	@Bean
	ChannelTopic topic() {
		return new ChannelTopic("pubsub:queue");
	}
}
