package com.poc.spring.data.redis.repo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.poc.spring.data.redis.config.RedisConfig;
import com.poc.spring.data.redis.model.Product;
import com.poc.spring.data.redis.model.Student;
import com.poc.spring.data.redis.model.UserToken;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = RedisConfig.class)
public class StudentRepositoryIntegrationTest {

    @Autowired
    private StudentRepository studentRepository;
    
    @Autowired ProductCountTracker productCountTracker;
    
    @Autowired UserTokenRepository userTokenRepo;

    @Test()
    public void addToken(){
    	UserToken token = new UserToken();
    	token.setToken("11");
    	token.setClientId(1);
    	token.setExpiration(15l);
    	
    	userTokenRepo.createUserToken(token);
    	
    	UserToken token2 = new UserToken();
    	token2.setToken("22");
    	token2.setClientId(1);
    	token2.setExpiration(100l);
    	
    	userTokenRepo.createUserToken(token2);
    }
    
    @Test()
    public void hasToken(){
    	UserToken token = new UserToken();
    	token.setToken("11");    	    	
    	System.out.println("11 is valid ? "+userTokenRepo.hasUserToken(token));
    	
    	UserToken token1 = new UserToken();
    	token1.setToken("22");    	    	
    	System.out.println("22 is valid ? "+userTokenRepo.hasUserToken(token1));
    }
    
    @Test()
    public void getToken(){
    	UserToken token = new UserToken();
    	token.setToken("1");    	    	
    	System.out.println(userTokenRepo.getUserToken(token));
    }
    
    @Test()
    //@Rollback(false)
    public void whenUsingProductTracker() throws Exception {
        final Product product = new Product();        
        productCountTracker.updateTotalProductCountWithLong(product);
    }
    
    @Test()
    //@Rollback(false)
    public void whenUsingProductTrackerWithHash() throws Exception {
        final Product product = new Product();        
        productCountTracker.updateTotalProductCountWithHash(product);
    }
    
    
    @Test()
    @Rollback(false)
    public void whenSavingStudent_thenAvailableOnRetrieval() throws Exception {
        final Student student = new Student("Eng2015001", "John Doe", Student.Gender.MALE, 1);
        student.setExpiration(1l);
        studentRepository.saveStudent(student);
        final Student retrievedStudent = studentRepository.findStudent(student.getId());
        assertEquals(student.getId(), retrievedStudent.getId());
    }

    @Test
    public void whenUpdatingStudent_thenAvailableOnRetrieval() throws Exception {
        final Student student = new Student("Eng2015001", "John Doe", Student.Gender.MALE, 1);
       /* studentRepository.saveStudent(student);
        student.setName("Richard Watson");*/
        studentRepository.saveStudent(student);
        final Student retrievedStudent = studentRepository.findStudent(student.getId());
        System.out.println(retrievedStudent);
      //  assertEquals(student.getName(), retrievedStudent.getName());
    }

    @Test
    public void whenSavingStudents_thenAllShouldAvailableOnRetrieval() throws Exception {
        final Student engStudent = new Student("Eng2015001", "John Doe", Student.Gender.MALE, 1);
        final Student medStudent = new Student("Med2015001", "Gareth Houston", Student.Gender.MALE, 2);
        studentRepository.saveStudent(engStudent);
        studentRepository.saveStudent(medStudent);
        final Map<Object, Object> retrievedStudent = studentRepository.findAllStudents();
        assertEquals(retrievedStudent.size(), 2);
    }

    @Test
    public void whenDeletingStudent_thenNotAvailableOnRetrieval() throws Exception {
        final Student student = new Student("Eng2015001", "John Doe", Student.Gender.MALE, 1);
        studentRepository.saveStudent(student);
        studentRepository.deleteStudent(student.getId());
        final Student retrievedStudent = studentRepository.findStudent(student.getId());
        assertNull(retrievedStudent);
    }
}