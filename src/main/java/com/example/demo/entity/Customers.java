package com.example.demo.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="customers")
public class Customers {
@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String address;
    private Integer tel;
    private String email;
    private String password;
    private String image;
    private Integer cardNo;
    private Integer code;
    private Integer expiry;

    Customers(){

    }
    Customers(Integer id,String name,String address,Integer tel,String email,String password,String image,Integer CardNo,Integer code,Integer expiry){
        this.id=id;
        this.name=name;
        this.address=address;
        this.tel=tel;
        this.email=email;
        this.password=password;
        this.image=image;
        this.CardNo=CardNo;
        this.code=code;
    }

    public Integer getExpiry(){
        return expiry;
    }

    public void setExpiry(Integer expiry){
        this.expiry=expiry;
    }

}
