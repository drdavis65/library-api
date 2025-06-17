package com.dalto.library_api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dalto.library_api.model.Author;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {
    List<Author> findByLastName(String lastName); // Optional custom query
}
