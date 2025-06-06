package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Area;

public interface AreaRepository extends JpaRepository<Area, String> {
    Area findByAreaName();
}
