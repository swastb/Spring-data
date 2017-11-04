package com.poc.spring.data.redis.repo;

import java.util.Map;

import com.poc.spring.data.redis.model.Student;

public interface StudentRepository {

    void saveStudent(Student person);

    void updateStudent(Student student);

    Student findStudent(String id);

    Map<Object, Object> findAllStudents();

    void deleteStudent(String id);
}
