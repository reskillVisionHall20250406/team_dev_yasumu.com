package com.example.demo.entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "reservation")
public class Reservation {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "hotel_id")
	private Integer hotelId;

	@Column(name = "customer_id")
	private Integer customerId;
	private LocalDate date;

	private String name;
	private String address;
	private String image;

	Reservation() {

	}

	public Reservation(Integer hotelId, Integer customerId, LocalDate orderedOn) {
		this.hotelId = hotelId;
		this.customerId = customerId;
		this.date = orderedOn;
	}

	public Reservation(Integer hotelId, Integer customerId, LocalDate orderedOn, String name, String address,
			String image) {
		this.hotelId = hotelId;
		this.customerId = customerId;
		this.date = orderedOn;
		this.name = name;
		this.address = address;
		this.image = image;
	}

	public Integer getId() {
		return id;
	}

	public Integer getHotelId() {
		return hotelId;
	}

	public void setHotelId(Integer hotelId) {
		this.hotelId = hotelId;
	}

	public Integer getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

}
