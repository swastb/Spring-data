package com.poc.spring.data.redis.repo;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import com.poc.spring.data.redis.model.Student;
import com.poc.spring.data.redis.model.Student.Gender;

@Repository
public class StudentRepositoryImpl implements StudentRepository {

	private static final String KEY = "Student";

	private RedisTemplate<String, Object> redisTemplate;
	private HashOperations hashOperations;

	@Autowired
	public StudentRepositoryImpl(RedisTemplate<String, Object> redisTemplate) {
		this.redisTemplate = redisTemplate;
	}

	@PostConstruct
	private void init() {
		hashOperations = redisTemplate.opsForHash();
	}

	public void saveStudent(final Student student) {
		hashOperations.put(KEY, student.getId(), student);
	}

	public void updateStudent(final Student student) {
		hashOperations.put(KEY, student.getId(), student);
	}

	public Student findStudent(final String id) {
		return (Student) hashOperations.get(KEY, id);
	}

	public Map<Object, Object> findAllStudents() {
		return hashOperations.entries(KEY);
	}

	public void deleteStudent(final String id) {
		hashOperations.delete(KEY, id);
	}

	@Cacheable(value = "studentCacheValue", cacheManager = "codetable", unless = "#result == null")
	public List<Student> findAllStudentsFromDatabase() {
		System.out.println("Creating Dummy data as we do nothave database");

		Student student1 = new Student("1", "Rob", Gender.MALE, 16);
		Student student2 = new Student("2", "John", Gender.MALE, 14);
		Student student3 = new Student("3", "Sansa", Gender.FEMALE, 13);
		Student student4 = new Student("4", "Arya", Gender.FEMALE, 11);
		Student student5 = new Student("5", "Bryan", Gender.MALE, 10);
		Student student6 = new Student("6", "Rickon", Gender.MALE, 9);

		List<Student> studentList = Stream.of(student1, student2, student3, student4, student5, student6)
				.collect(Collectors.toList());

		return studentList;

	}

}
