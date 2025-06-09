package com.example.demo.controller;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entity.Hotels;
import com.example.demo.entity.Reservation;
import com.example.demo.repository.HotelsRepository;
import com.example.demo.repository.ReservationRepository;

public class ReservationController {

	@Autowired
	Account account;

	@Autowired
	HotelsRepository hotelsRepository;

    @Autowired
	ReservationRepository reservationRepository;

    @PostMapping("/reservation")
	    public String index(
			@PathVariable("id") Integer id,
			@RequestParam("date") String date,Model model) {
		    Hotels hotels = hotelsRepository.findById(id).get();
		    model.addAttribute("hotels", hotels);
		    return "reservation";
	}

	@PostMapping("/reservation/approval"){
		 public String index(
			@PathVariable("id") Integer id,
			@RequestParam("orderedOn") LocalDate orderedOn,Model model) {
			Reservation reservation = new Reservation(id,account.getId(),orderedOn);
			reservationRepository.saveAll(reservation);
	}
}
}