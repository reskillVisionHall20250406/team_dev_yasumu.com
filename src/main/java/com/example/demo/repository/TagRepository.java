package com.example.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Tags; // Tag 엔티티 임포트

@Repository
public interface TagRepository extends JpaRepository<Tags, Integer> { // Tag 엔티티, ID 타입은 Integer

    Optional<Tags> findByName(String name);

    List<Tags> findByNameIn(List<String> names);
}