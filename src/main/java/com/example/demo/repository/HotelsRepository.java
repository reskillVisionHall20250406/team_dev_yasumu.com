package com.example.demo.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Hotels;

public interface HotelsRepository extends JpaRepository<Hotels, Integer> {

	Page<Hotels> findByAreaIdAndCapacityAndPriceLessThanEqualAndNameContaining(Integer areaId, Integer capacity,
			Integer price, String keyword, Pageable pageable);

	Page<Hotels> findByAreaIdAndCapacityAndPriceLessThanEqual(Integer areaId, Integer capacity, Integer price,
			Pageable pageable);

	Page<Hotels> findByAreaIdAndCapacityAndNameContaining(Integer areaId, Integer capacity, String keyword,
			Pageable pageable);

	Page<Hotels> findByAreaIdAndPriceLessThanEqualAndNameContaining(Integer areaId, Integer price, String keyword,
			Pageable pageable);

	Page<Hotels> findByCapacityAndPriceLessThanEqualAndNameContaining(Integer capacity, Integer price, String keyword,
			Pageable pageable);

	Page<Hotels> findByAreaIdAndCapacity(Integer areaId, Integer capacity, Pageable pageable);

	Page<Hotels> findByAreaIdAndPriceLessThanEqual(Integer areaId, Integer price, Pageable pageable);

	Page<Hotels> findByAreaIdAndNameContaining(Integer areaId, String keyword, Pageable pageable);

	Page<Hotels> findByCapacityAndPriceLessThanEqual(Integer capacity, Integer price, Pageable pageable);

	Page<Hotels> findByCapacityAndNameContaining(Integer capacity, String keyword, Pageable pageable);

	Page<Hotels> findByPriceLessThanEqualAndNameContaining(Integer price, String keyword, Pageable pageable);

	Page<Hotels> findByAreaId(Integer areaId, Pageable pageable);

	Page<Hotels> findByCapacity(Integer capacity, Pageable pageable);

	Page<Hotels> findByPriceLessThanEqual(Integer price, Pageable pageable);

	Page<Hotels> findByNameContaining(String keyword, Pageable pageable);

}
