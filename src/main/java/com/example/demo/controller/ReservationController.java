package com.example.demo.controller;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entity.Customers;
import com.example.demo.entity.Hotels;
import com.example.demo.entity.Reservation;
import com.example.demo.model.Account;
import com.example.demo.repository.CustomersRepository;
import com.example.demo.repository.HotelsRepository;
import com.example.demo.repository.ReservationRepository;

public class ReservationController {

	@Autowired
	Account account;

	@Autowired
	HotelsRepository hotelsRepository;

	@Autowired
	CustomersRepository customersRepository;

	@Autowired
	ReservationRepository reservationRepository;

	@PostMapping("/reservation/{id}")
	public String reservation(
			@PathVariable("id") Integer id,
			@RequestParam("date") LocalDate date, Model model) {
		Hotels hotels = hotelsRepository.findById(id).get();
		model.addAttribute("hotels", hotels);
		return "reservation";
	}

	@PostMapping("/reservation/approval")
	public String approval(
			@PathVariable("id") Integer id,
			@RequestParam("orderedOn") LocalDate orderedOn, Model model) {
		Customers customer = customersRepository.findByEmail(account.getEmail());
		Reservation reservation = new Reservation(id, customer.getId(), orderedOn);
		reservationRepository.save(reservation);
		return "completed";
	}
}