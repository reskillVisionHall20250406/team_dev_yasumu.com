package com.example.demo.entity;

import jakarta.persistence.Entity;

@Entity
@Table(name="area")
public class Area {
    private Integer id;
    private String area;

    public String getArea() {
	return area;
}

}