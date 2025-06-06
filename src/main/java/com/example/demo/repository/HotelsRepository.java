package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Hotels;

public interface HotelsRepository extends JpaRepository<Hotels, String> {
    Hotels findByAreaId(Integer areaId);

    List<Hotels> findByAreaIdAndLessThanCapacity(Integer areaId, Integer capacity);

    List<Hotels> findByAreaIdAndLessThanPrice(Integer areaId, Integer price);

    List<Hotels> findByAreaIdAndNameContaining(Integer areaId, String name);

    List<Hotels> findByAreaIdAndLessThanCapacityAndLessThanPrice(Integer areaId, Integer capacity, Integer price);

    List<Hotels> findByAreaIdAndLessThanCapacityAndNameContaining(Integer areaId, Integer capacity, String name);

    List<Hotels> findByAreaIdAndGreaterThanPriceAndNameContaining(Integer areaId, Integer price, String name);

    List<Hotels> findByAreaIdAndLessThanCapacityAndLessThanPriceAndNameContaining(Integer areaId, Integer price,
            Integer capacity, String name);

    List<Hotels> findByLessThanCapacity(Integer capacity);

    List<Hotels> findByLessThanCapacityAndLessThanPrice(Integer capacity, Integer price);

    List<Hotels> findByLessThanCapacityAndNameContaining(Integer capacity, String name);

    List<Hotels> findByLessThanCapacityAndLessThanPriceAndNameContaining(Integer capacity, Integer price, String name);

    List<Hotels> findByLessThanPrice(Integer price);

    List<Hotels> findByLessThanPriceAndNameContaining(Integer price, String name);

    List<Hotels> findByNameContaining(String name);

}
