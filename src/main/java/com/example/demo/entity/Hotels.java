// Hotels.java
package com.example.demo.entity; // Adjust package as needed

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@Entity
@Table(name = "hotels")
public class Hotels {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Transient
    private Integer areaId;

    @ManyToOne
    @JoinColumn(name = "area_id", referencedColumnName = "id")
    private Area area;

    private String name;
    private String detail;
    private String address;
    private String image;
    private String image2;
    private String image3;
    private Integer capacity;
    private Integer price;
    private double stars;

    @Column(name = "admin_id")
    private Integer adminId;

    @Transient
    private String starVisual;

    // Many-to-Many relationship with Tags
    @ManyToMany
    @JoinTable(
        name = "hotel_tags", // The name of your join table
        joinColumns = @JoinColumn(name = "hotel_id"), // Column in hotel_tags that references hotels.id
        // 🔽 This is the crucial line to fix! 🔽
        inverseJoinColumns = @JoinColumn(name = "tag_id", referencedColumnName = "id") // Column in hotel_tags that references tags.id
        // 🔼 Make sure 'referencedColumnName' matches the actual PK column name in your 'tags' table 🔼
    )
    private Set<Tags> tags = new HashSet<>();

    // ... (rest of your Hotels entity: constructors, getters, setters) ...

    public String getStarVisual() {
        return starVisual;
    }

    public void setStarVisual(String starVisual) {
        this.starVisual = starVisual;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAreaId() {
        return areaId;
    }

    public void setAreaId(Integer areaId) {
        this.areaId = areaId;
    }

    public Area getArea() {
        return area;
    }

    public void setArea(Area area) {
        this.area = area;
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

    public double getStars() {
        return stars;
    }

    public void setStars(double rounded) {
        this.stars = rounded;
    }

    public Integer getAdminId() {
        return adminId;
    }

    public void setAdminId(Integer adminId) {
        this.adminId = adminId;
    }

    public Set<Tags> getTags() {
        return tags;
    }

    public void setTags(Set<Tags> tags) {
        this.tags = tags;
    }
}