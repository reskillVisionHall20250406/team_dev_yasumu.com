package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Hotels;

public interface HotelsRepository extends JpaRepository<Hotels, Integer> {

	Hotels findByAreaId(Integer areaId);

	List<Hotels> findByAreaIdAndCapacityLessThan(Integer areaId, Integer capacity);

	List<Hotels> findByAreaIdAndPriceLessThan(Integer areaId, Integer price);

	List<Hotels> findByAreaIdAndNameContaining(Integer areaId, String name);

	List<Hotels> findByAreaIdAndCapacityLessThanAndPriceLessThan(Integer areaId, Integer capacity, Integer price);

	List<Hotels> findByAreaIdAndCapacityLessThanAndNameContaining(Integer areaId, Integer capacity, String name);

	List<Hotels> findByAreaIdAndPriceGreaterThanAndNameContaining(Integer areaId, Integer price, String name);

	List<Hotels> findByAreaIdAndCapacityLessThanAndPriceLessThanAndNameContaining(Integer areaId, Integer capacity,
			Integer price, String name);

	List<Hotels> findByCapacityLessThan(Integer capacity);

	List<Hotels> findByCapacityLessThanAndPriceLessThan(Integer capacity, Integer price);

	List<Hotels> findByCapacityLessThanAndNameContaining(Integer capacity, String name);

	List<Hotels> findByCapacityLessThanAndPriceLessThanAndNameContaining(Integer capacity, Integer price,
			String name);

	List<Hotels> findByPriceLessThan(Integer price);

	List<Hotels> findByPriceLessThanAndNameContaining(Integer price, String name);

	List<Hotels> findByNameContaining(String name);
}
