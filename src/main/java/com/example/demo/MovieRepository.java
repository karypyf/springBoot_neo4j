package com.example.demo;

import org.springframework.data.repository.CrudRepository;

public interface MovieRepository extends CrudRepository<Movie, Long> {

    Movie findByTitle(String title);
}