package com.example.demo.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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

	@GetMapping("/reservation/days/{id}")
	public String kari(@PathVariable("id") Integer id, Model model) {
		if (account.getName() == null) {
			session.setAttribute("reservationLogin", "/hotelsdetail/");
			session.setAttribute("id", id);
			return "redirect:/login";
		}
		Hotels hotels = hotelsRepository.findById(id).get();
		model.addAttribute("hotels", hotels);
		return "reservation_days";
	}

	@PostMapping("/reservation/days/{id}")
	public String kripost(
			@PathVariable("id") Integer id,
			@RequestParam("days") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate days,
			RedirectAttributes redirectAttributes, Model model) {
		redirectAttributes.addFlashAttribute("selectedDate", days);
		session.setAttribute("date", days);
		return "redirect:/reservation/" + id;
	}

	@GetMapping("/reservation/{id}")
	public String reservation(
			@ModelAttribute("selectedDate") LocalDate selectedDate,
			@PathVariable("id") Integer id,
			Model model) {

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
		model.addAttribute("selectedDate", selectedDate);
		//		@RequestParam("date") LocalDate date,

		return "reservation";
	}

	@PostMapping("/reservation/approval/{id}")
	public String approval(
			@PathVariable("id") Integer id,
			@RequestParam("cardno") String cardNo,
			@RequestParam("code") String code,
			@RequestParam("expiry") String expiry, Model model) {
		Hotels hotels = hotelsRepository.findById(id).get();
		Customers customer = customersRepository.findByEmail(account.getEmail());
		String err1 = "";
		String err2 = "";
		String err3 = "";
		List<String> err = new ArrayList<>();
		if (cardNo.equals("")) {
			err1 = ("クレジットカード番号を入力してください");
			err.add(err1);
		} else if (cardNo.length() > 16 || cardNo.length() < 14) {
			err1 = ("クレジットカード番号は14～16桁で入力してください");
			err.add(err1);
		}

		if (code.equals("") || code == null) {
			err2 = ("セキュリティ番号を入力してください");
			err.add(err2);
		} else if (code.length() != 3) {
			err2 = ("セキュリティ番号は3桁で入力してください");
			err.add(err2);
		}
		String newString = "";
		Integer newExpiry = 0;
		if (expiry.equals("")) {
			err3 = ("有効期限を入力してください");
			err.add(err3);
		} else if (!(expiry.equals(""))) {
			if (expiry.length() < 4) {
				err3 = ("有効期限はMM(01～12)/YYの4桁で入力してください");
				err.add(err3);
			} else {
				newString = expiry.substring(0, 2);
				newExpiry = Integer.parseInt(newString);
				if (newExpiry < 1 || newExpiry > 12) {
					err3 = ("有効期限はMM(01～12)/YYで入力してください");
					err.add(err3);
				}
			}
		}

		customer.setCardNo(cardNo);
		customer.setCode(code);
		customer.setExpiry(expiry);
		LocalDate date = (LocalDate) session.getAttribute("date");
		if (err.isEmpty()) {

			Reservation reservation = new Reservation(id, customer.getId(), date, hotels.getName(), hotels.getAddress(),
					hotels.getImage(), hotels.getCapacity(), hotels.getPrice());
			reservationRepository.save(reservation);
			model.addAttribute("hotels", hotels);
			model.addAttribute("reservation", reservation);
			model.addAttribute("selectedDate", date);

			return "completed";

		}

		model.addAttribute("orderedOn", date);
		model.addAttribute("hotels", hotels);
		model.addAttribute("customers", customer);
		model.addAttribute("account", account);
		if (!(err1.equals(""))) {
			model.addAttribute("err1", err1);
		}

		if (!(err2.equals(""))) {
			model.addAttribute("err2", err2);
		}

		if (!(err3.equals(""))) {
			model.addAttribute("err3", err3);
		}

		return "reservation";

	}
}