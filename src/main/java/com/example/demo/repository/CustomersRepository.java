package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Customers;

public interface CustomersRepository extends JpaRepository<Customers, String> {
    Customers findByEmail(String email);

    List<Customers> findByCardNo(Integer cardNo);

    List<Customers> findByEmailAndPassword(String email, String password);
}
