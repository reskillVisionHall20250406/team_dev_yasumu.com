package com.example.demo.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Hotels;

public interface HotelsRepository extends JpaRepository<Hotels, Integer> {

	Page<Hotels> findByAreaId(Integer areaId, Pageable pageable);

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

	Page<Hotels> findByPriceLessThanEqualAndNameContaining(Integer price, String keyword, Pageable pageable);

	Page<Hotels> findByPriceLessThanEqual(Integer price, Pageable pageable);

	Page<Hotels> findByNameContaining(String keyword, Pageable pageable);

	Page<Hotels> findByAreaIdAndCapacityAndPriceLessThanEqualAndNameContaining(Integer areaId, Integer capacity,
			Integer price, String keyword, Pageable pageable);

	Page<Hotels> findByAreaIdAndCapacityAndPriceLessThanEqual(Integer areaId, Integer capacity, Integer price,
			Pageable pageable);

	Page<Hotels> findByAreaIdAndCapacityAndNameContaining(Integer areaId, Integer capacity, String keyword,
			Pageable pageable);

	Page<Hotels> findByAreaIdAndCapacity(Integer areaId, Integer capacity, Pageable pageable);

	Page<Hotels> findByCapacityAndPriceLessThanEqualAndNameContaining(Integer capacity, Integer price, String keyword,
			Pageable pageable);

	Page<Hotels> findByCapacityAndPriceLessThanEqual(Integer capacity, Integer price, Pageable pageable);

	Page<Hotels> findByCapacityAndNameContaining(Integer capacity, String keyword, Pageable pageable);

	Page<Hotels> findByCapacity(Integer capacity, Pageable pageable);

}
