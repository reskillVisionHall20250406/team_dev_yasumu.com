package com.example.demo.entity;

import jakarta.persistence.Entity;

@Entity
public class Area {
    private Integer id;
    private String area;

    public String getArea() {
	return area;
}

}