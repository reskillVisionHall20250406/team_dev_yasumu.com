package com.example.demo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "review")
public class Review {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private Integer star;
	private String comment;
	@Column(name = "hotel_id")
	private Integer hotelId;

	Review() {
	}

	public Review(Integer star, String comment, Integer hotelId) {
		this.star = star;
		this.comment = comment;
		this.hotelId = hotelId;
	}

	public Integer getId() {
		return id;
	}

	public Integer getStar() {
		return star;
	}

	public void setStar(Integer star) {
		this.star = star;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Integer getHotel_id() {
		return hotelId;
	}

	public void setHotel_id(Integer hotelId) {
		this.hotelId = hotelId;
	}
}
