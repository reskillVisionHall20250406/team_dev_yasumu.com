package com.example.demo.entity;

import jakarta.persistence.Entity;

@Entity
@Table(name="area")
public class Area {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String area;

    public String getArea() {
	return area;
}

}