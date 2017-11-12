package com.poc.spring.data.redis.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.poc.spring.data.redis.model.Student;
import com.poc.spring.data.redis.repo.StudentRepository;

@Controller
public class StudentInformationController {

	@Autowired
	StudentRepository studentRepo;

	/*@RequestMapping(value = "/", method = RequestMethod.GET)
	@ResponseBody*/
	public List<Student> calculateResult() {
		System.out.println("Performing expensive calculation...");
		// perform computationally expensive calculation
		return studentRepo.findAllStudentsFromDatabase();
	}

}
