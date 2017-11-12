package com.poc.spring.data.redis;

import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.poc.spring.data.redis.config.RedisConfig;
import com.poc.spring.data.redis.controller.StudentInformationController;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = RedisConfig.class)
public class StudentControllerTest {

	@Autowired
	private StudentInformationController studentController;

	@Test
	public void testOnMessage() throws Exception {

		Date before = new java.util.Date();
		studentController.calculateResult();
		Date after = new java.util.Date();
		System.out.println("Execution time " + (after.getTime() - before.getTime()));

	}
}