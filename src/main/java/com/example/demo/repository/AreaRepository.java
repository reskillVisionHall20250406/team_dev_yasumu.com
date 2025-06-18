package com.example.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Area;

public interface AreaRepository extends JpaRepository<Area, Integer> {

    Optional<Area> findByName(String name);

}
