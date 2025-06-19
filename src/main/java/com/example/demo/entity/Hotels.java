// Hotels.java

package com.example.demo.entity;

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
import jakarta.persistence.ManyToMany; 
import jakarta.persistence.JoinTable;  
import jakarta.persistence.JoinColumn; 

import java.util.HashSet; 
import java.util.Set;     
// import java.time.LocalDateTime; 
// import java.math.BigDecimal;
// import jakarta.persistence.PrePersist; 
// import jakarta.persistence.PreUpdate; 
import jakarta.persistence.ManyToMany; 
import jakarta.persistence.JoinTable;  
import jakarta.persistence.JoinColumn; 

import java.util.HashSet; 
import java.util.Set;     
// import java.time.LocalDateTime; 
// import java.math.BigDecimal;
// import jakarta.persistence.PrePersist; 
// import jakarta.persistence.PreUpdate; 

@Entity
@Table(name = "hotels")
public class Hotels {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@ManyToOne
	@JoinColumn(name = "area_id", referencedColumnName = "id")
	private Area area;

	private Integer capacity;
	private String name;
	private String detail;
	private String address;
	private String image;
	private String image2;
	private String image3;

	@Column(name = "area_id", insertable = false, updatable = false)
	private Integer areaId;
	private Integer price;
	private double stars;

    @Column(name = "admin_id")
    private Integer adminId;

    // @Column(name = "created_at", updatable = false) // SQL 스키마에 따라 추가
    // private LocalDateTime createdAt;

    // @Column(name = "updated_at") // SQL 스키마에 따라 추가
    // private LocalDateTime updatedAt;
	

    @Transient
    private String starVisual;



	// Many-to-Many relationship with Tags
	@ManyToMany
	@JoinTable(name = "hotel_tags", joinColumns = @JoinColumn(name = "hotel_id"), inverseJoinColumns = @JoinColumn(name = "tag_id", referencedColumnName = "id"))
	private Set<Tags> tags = new HashSet<>();

	// Getters and Setters
	public String getStarVisual() {
		return starVisual;
	}

	public void setStarVisual(String starVisual) {
		this.starVisual = starVisual;
	}

    // @PrePersist
    // protected void onCreate() {
    //     this.createdAt = LocalDateTime.now();
    //     this.updatedAt = LocalDateTime.now();
    // }

    // @PreUpdate
    // protected void onUpdate() {
    //     this.updatedAt = LocalDateTime.now();
    // }

	//	Hotels(Integer areaId, String name, String detail, String address, String image, String image2, String image3,
	//			Integer capacity, Integer price) {
	//		this.areaId = areaId;
	//		this.name = name;
	//		this.detail = detail;
	//		this.address = address;
	//		this.image = image;
	//		this.image2 = image2;
	//		this.image3 = image3;
	//		this.capacity = capacity;
	//		this.price = price;
	//	}
    public Integer getId() {
        return id;
    }

	public void setId(Integer id) {
		this.id = id;
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

	public Integer getAreaId() {
		return area != null ? area.getId() : null;
	}

	public void setAreaId(Integer areaId) {
		this.areaId = areaId;
	}
}