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
    private Integer CardNo;
    private Integer code;
    private Integer expiry;

    Costomers(){

    }
    Costomers(Integer id,String name,String address,Integer tel,String email,String password,String image,Integer CardNo,Integer code,Integer expiry){
        this.id=id;
        this.name=name;
        this.address=address;
        this.tel=tel;
        this.email=email;
        this.password=password;
        this.image=image;
        this.cardNo=cardNo;
        this.code=code;
        this.expiry=expiry;
    }

    public Integer getId(){
        return id;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name=name;
    }

    public String getAddress(){
        return address;
    }

    public void setAddress(String address){
        this.address=address;
    }

    public Integer getTel(){
        return tel;
    }

    public void setTel(Integer tel){
        this.tel=tel;
    }

    public String getEmail(){
        return email;
    }

    public void setEmail(String email){
        this.email=email;
    }

    public String getPassword(){
        return password;
    }

    public void setPassword(String password){
        this.password=password;
    }

    public String getImage(){
        return image;
    }

    public void setImage(String image){
        this.image=image;
    }

    public Integer getCardNo(){
        return cardNo;
    }

    public void setCardNo(Integer cardNo){
        this.cardNo=cardNo;
    }

    public Integer getCode(){
        return code;
    }

    public void setCode(Integer code){
        this.code=code;
    }

    public Integer getExpiry(){
        return expiry;
    }

    public void setExpiry(Integer expiry){
        this.expiry=expiry;
    }

}
