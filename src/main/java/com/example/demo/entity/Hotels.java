package com.example.demo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "hotels")
public class Hotels {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(name = "area_id")
	private Integer areaId;
	private String name;
	private String detail;
	private String address;
	private String image;
	private String image2;
	private String image3;
	private Integer capacity;
	private Integer price;

	Hotels() {
	}

	Hotels(Integer areaId, String name, String detail, String address, String image, String image2, String image3,
			Integer capacity, Integer price) {
		this.areaId = areaId;
		this.name = name;
		this.detail = detail;
		this.address = address;
		this.image = image;
		this.image2 = image2;
		this.image3 = image3;
		this.capacity = capacity;
		this.price = price;
	}
	
	public Integer getId() {
		return id;
	}

	public Integer getAreaId() {
		return areaId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
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

	public String getImage2() {
		return image2;
	}

	public void setImage2(String image2) {
		this.image2 = image2;
	}

	public String getImage3() {
		return image3;
	}

	public void setImage3(String image3) {
		this.image3 = image3;
	}

	public Integer getCapacity() {
		return capacity;
	}

	public void setCapacity(Integer capacity) {
		this.capacity = capacity;
	}

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

}
