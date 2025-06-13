package com.example.demo.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
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

@Controller
public class ReservationController {

	@Autowired
	Account account;

	@Autowired
	HttpSession session;

	@Autowired
	HotelsRepository hotelsRepository;

	@Autowired
	CustomersRepository customersRepository;

	@Autowired
	ReservationRepository reservationRepository;

	@GetMapping("/kari")
	public String kari() {
		return "reservation_kari";
	}

	@PostMapping("/kari")
	public String kripost() {
		return "reservation";
	}

	@GetMapping("/reservation/{id}")
	public String reservation(
			@PathVariable("id") Integer id,
			Model model) {

		if (account.getEmail() == null) {
			session.setAttribute("reservationLogin", "/reservation/");
			session.setAttribute("id", id);
			return "redirect:/login";
		} else {
			Hotels hotels = hotelsRepository.findById(id).get();//クリックされた宿のIDから宿情報を取得

			Customers customer = customersRepository.findByEmail(account.getEmail());
			//ログインされているアカウントからクレジットカードの情報を取得
			List<String> card = new ArrayList<>();
			card.add(customer.getCardNo());
			card.add(customer.getCode());
			card.add(customer.getExpiry());

			model.addAttribute("hotels", hotels);
			model.addAttribute("customers", customer);
			model.addAttribute("account", account);
			//		@RequestParam("date") LocalDate date,
			//		model.addAttribute("date", date);
			return "reservation";
		}
	}

	@PostMapping("/reservation/approval/{id}")
	public String approval(
			@PathVariable("id") Integer id,
			@RequestParam("orderedOn") LocalDate orderedOn,
			@RequestParam("cardno") String cardNo,
			@RequestParam("code") String code,
			@RequestParam("expiry") String expiry, Model model) {
		Hotels hotels = hotelsRepository.findById(id).get();
		Customers customer = customersRepository.findByEmail(account.getEmail());
		List<String> errList = new ArrayList<>();

		if (cardNo.equals("")) {
			errList.add("クレジットカード番号を入力してください");
		} else if (cardNo.length() > 16 || cardNo.length() < 14) {
			errList.add("クレジットカード番号は14～16桁で入力してください");
		}

		if (code.equals("")) {
			errList.add("セキュリティ番号を入力してください");
		} else if (code.length() != 3) {
			errList.add("セキュリティ番号は3桁で入力してください");
		}
		String newString = "";
		Integer newExpiry = 0;
		if (expiry.equals("")) {
			errList.add("有効期限を入力してください");
		} else if (!(expiry.equals(""))) {
			if (expiry.length() < 4) {
				errList.add("有効期限はMM(01～12)/YYの4桁で入力してください");
			} else {
				newString = expiry.substring(0, 2);
				newExpiry = Integer.parseInt(newString);
				if (newExpiry < 1 || newExpiry > 12) {
					errList.add("有効期限はMM(01～12)/YYで入力してください");
				}
			}
		}

		customer.setCardNo(cardNo);
		customer.setCode(code);
		customer.setExpiry(expiry);
		if (errList.isEmpty()) {

			Reservation reservation = new Reservation(id, customer.getId(), orderedOn);
			reservationRepository.save(reservation);
			model.addAttribute("hotels", hotels);
			model.addAttribute("reservation", reservation);

			return "completed";
		}

		model.addAttribute("orderedOn", orderedOn);
		model.addAttribute("hotels", hotels);
		model.addAttribute("customers", customer);
		model.addAttribute("account", account);
		model.addAttribute("err", errList);
		return "reservation";

	}
}