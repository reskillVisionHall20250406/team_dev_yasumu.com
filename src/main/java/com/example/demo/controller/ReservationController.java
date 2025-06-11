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

	@GetMapping("/user/add/reservation")
	public String create() {
		return "user_reservation";
	}

	@PostMapping("/user/add/reservation")
	public String add(
			@RequestParam(name = "name", defaultValue = "") String name,
			@RequestParam(name = "address", defaultValue = "") String address,
			@RequestParam(name = "tel", defaultValue = "") String tel,
			@RequestParam(name = "email", defaultValue = "") String email,
			@RequestParam(name = "password", defaultValue = "") String password,
			@RequestParam(name = "cardNo", defaultValue = "") String cardNo,
			@RequestParam(name = "code", defaultValue = "") String code,
			@RequestParam(name = "expiry", defaultValue = "") String expiry,
			Model model) {
		String expirys = expiry.replace(",", "");
		//        , 이라는 단어를 뒤에 공란으로 만들어주는 replace라는 메소드
		List<String> errorList = new ArrayList<>();
		//      여기 밑에줄에서 이메일을 DB에서 찾아와서 메일이 등록되이는지 확인된다 눌이 아닌 확인하도록 에러코드
		Customers existingCustomer = customersRepository.findByEmail(email);

		if (name.isEmpty()) {
			errorList.add("お名前を入力してください");
		}
		if (email.isEmpty()) {
			errorList.add("メールを入力してください");
		} else if (existingCustomer != null) {
			errorList.add("このメールアドレスは既に登録されています");
		}
		if (address.isEmpty()) {
			errorList.add("住所を入力してください");
		}
		if (tel.isEmpty()) {
			errorList.add("電話番号を入力してください");
		}
		if (password.isEmpty()) {
			errorList.add("パスワードを入力してください");
		} else if (password.length() < 8) {
			errorList.add("パスワードは8文字以上で入力してください");
		} else if (!password.matches("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]+$")) {
			errorList.add("パスワードは半角英字と数字の両方を含めてください");
		}

		if (!errorList.isEmpty()) {
			model.addAttribute("errors", errorList);
			model.addAttribute("name", name);
			model.addAttribute("email", email);
			model.addAttribute("address", address);
			model.addAttribute("tel", tel);
			return "user"; // ログインページにエラーを表示
		}

		Customers customers = new Customers(name, address, tel, email, password, cardNo, code, expirys);
		customersRepository.save(customers);
		return "redirect:/login";
	}

	@PostMapping("/login/reservation")
	public String login(
			@RequestParam(name = "email", defaultValue = "") String email,
			@RequestParam(name = "password", defaultValue = "") String password,
			Model model) {

		List<String> errorList = new ArrayList<>();

		if (email.isEmpty()) {
			errorList.add("メールを入力してください");
		}
		if (password.isEmpty()) {
			errorList.add("パスワードを入力してください");
		}

		Customers customers = customersRepository.findByEmail(email);

		if (!email.isEmpty() && !password.isEmpty()) {
			if (customers != null && customers.getPassword().equals(password)) {
				session.setAttribute("currentUser", customers);
			} else {
				errorList.add("メールアドレスまたはパスワードが正しくありません");
			}
		}

		if (!errorList.isEmpty()) {
			model.addAttribute("errors", errorList);
			model.addAttribute("email", email);
			return "login"; // ログインページにエラーを表示
		}

		account.setEmail(customers.getEmail());
		account.setName(customers.getName());
		account.setId(customers.getId());

		return "redirect:/hotels";
	}

	@PostMapping("/reservation/{id}")
	public String reservation(
			@PathVariable("id") Integer id,
			Model model) {

		if (account.getEmail() == null) {
			return "login_reservation";
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
		}

		if (code.equals("")) {
			errList.add("セキュリティコードを入力してください");
		}

		if (expiry.equals("")) {
			errList.add("有効期限を入力してください");
		}

		if (errList.isEmpty()) {
			customer.setCardNo(cardNo);
			customer.setCode(code);
			customer.setExpiry(expiry);

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