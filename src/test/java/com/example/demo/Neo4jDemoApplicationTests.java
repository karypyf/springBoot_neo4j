package com.example.demo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.demo.JsonSimple;
import com.example.demo.Movie;
import com.example.demo.MovieRepository;
import com.example.demo.Person;
import com.example.demo.PersonRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class Neo4jDemoApplicationTests {
    @Autowired
    MovieRepository movieRepo;
    @Autowired
    PersonRepository personRepo;

    @Test
    public void contextLoads() {}

    @Test
    public void testSaveMovie() {
        
     movieRepo.deleteAll();
     personRepo.deleteAll();
     Movie m1 = new Movie("无问西东", "2018");
     Movie m2 = new Movie("罗曼蒂克消亡史", "2016");
     movieRepo.save(m1);
     movieRepo.save(m2);

    }

    @Test
    public void testSavePerson() {
    Person p1 = new Person("章子怡", "1979");
    Person p2 = new Person("李芳芳", "1976");
    Person p3 = new Person("程耳", "1970");
    Movie m1 = movieRepo.findByTitle("罗曼蒂克消亡史");
    Movie m2 = movieRepo.findByTitle("无问西东");   
    if (m1!=null) {
        p1.addActor(m1);
        p3.addDirector(m1);
    }
    if (m2!=null) {
        p1.addActor(m2);
        p2.addDirector(m2);
    }
    personRepo.save(p1);
    personRepo.save(p2);
    personRepo.save(p3);
    }
  
    @Test
    public void testfindByTitle() {
        Movie movie = movieRepo.findByTitle("罗曼蒂克消亡史");
        System.out.println(JsonSimple.toJson(movie));

    }
    
    @Test
    public void testfindByName() {
        Person person = personRepo.findByName("章子怡");
        System.out.println(JsonSimple.toJson(person));

    }

}

