package com.example.demo.entity;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="reservations")
public class Reservation {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Integer id;
private Integer hotelId;
private Integer customerId;
private Localdate date;
private LocalDate orderedOn;
private Integer totalPrice;

Reservation(){

}
Reservation(Integer customerId,LocalDate orderedOn,Integer totalPrice){
    this.customerId=customerId;
    this.orderedOn=orderedOn;
    this.totalPrice=totalPrice;
}

public Integer getId(){
    return id;
}

public Integer getHotelId(){
    return hotelId;
}

public void setHotelId(Integer hotelId){
    this.hotelId=hotelId;
}

public Integer getCustomerId(){
    return customerId;
}

public void setCustomerId(Integer customerId){
    this.customerId=customerId;
}

public LocalDate getDate(){
    return date;
}

public void setDate(LocalDate date){
    this.date=date;
}
}
