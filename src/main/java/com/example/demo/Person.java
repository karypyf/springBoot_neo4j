package com.example.demo;

import java.util.HashSet;
import java.util.Set;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

@NodeEntity//这是数据库中的node
public class Person {

    @Id 
    @GeneratedValue
    private Long id;
    
    private String name;
    
    private String born;
       

    public Person() {// 从 Neo4j API 2.0.5开始需要无参构造函数

    }
    
    public Person(String name, String born) {
        this.name = name;
        this.born = born;
    }
    
    
    @Relationship(type = "ACTED_IN", direction = Relationship.OUTGOING)
    public Set<Movie> actors;

    public void addActor(Movie movie) {
        if (actors == null) {
            actors = new HashSet<>();
        }
        actors.add(movie);
    }
    
    
    @Relationship(type = "DIRECTED", direction = Relationship.OUTGOING)
    public Set<Movie> directors;

    public void addDirector(Movie movie) {
        if (directors == null) {
            directors = new HashSet<>();
        }
        directors.add(movie);
    }
    
   
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBorn() {
        return born;
    }

    public void setBorn(String born) {
        this.born = born;
    }
    
    
}
