package com.example.demo.entity;

@Entity
@Table(name="reservations")
public class Reservation {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Integer id;
private Integer hotelId;
private Integer customerId;
private Localdate date;

Order(){

}
Order(Integer customerId,LocalDate orderedOn,Integer totalPrice){
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

public String getCustomerId(){
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
