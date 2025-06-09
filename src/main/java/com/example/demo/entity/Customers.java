package com.example.demo.entity;

import jakarta.persistence.Entity;

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

}
