package com.example.demo.entity;

public class Hotels {
/*松本が変更・更新しますtest*/
private Interger id;
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

}
