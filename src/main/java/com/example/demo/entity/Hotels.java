package com.example.demo.entity;

@Entity
@Table(name="hotels")
public class Hotels {
/*松本が変更・更新しますtest*/

@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Integer id;
private Interger areaId;
private String name;
private String detail;
private String address;
private String image;
private Interger capacity;
private Interger price;

Hotels(){}

Hotels(Interger areaId,String name,String detail,String address,String image,Interger capacity,Interger price){
this.areaId = areaId;
this.name = name;
this.detail = detail;
this.address = address;
this.image = image;
this.capacity = capacity;
this.price = price;
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

public Integer getCapacity() {
	return capacity;
}

public void setAddress(String capacity) {
	this.capacity = capacity;
}
public Integer getPrice() {
	return price;
}

public void setPrice(Integer price) {
	this.price = price;
}

}
