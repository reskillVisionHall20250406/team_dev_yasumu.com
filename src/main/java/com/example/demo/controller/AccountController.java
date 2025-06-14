package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entity.Customers;
import com.example.demo.model.Account;
import com.example.demo.repository.CustomersRepository;

@Controller
//コントローラーが必須に必要
public class AccountController {
	@Autowired
	HttpSession session;

	@Autowired
	CustomersRepository customersRepository;

	@Autowired
	Account account;

	@GetMapping("/user/add")
	public String create() {
		return "user";
	}

	@PostMapping("/user/add")
	public String add(
			@RequestParam(name = "name", defaultValue = "") String name,
			@RequestParam(name = "address", defaultValue = "") String address,
			@RequestParam(name = "tel", defaultValue = "") Integer tel,
			@RequestParam(name = "email", defaultValue = "") String email,
			@RequestParam(name = "password", defaultValue = "") String password,
			@RequestParam(value = "image") String image,
			Model model) {

		List<String> errorList = new ArrayList<>();

		if (name.isEmpty()) {
			errorList.add("お名前を入力してください");
		}
		if (email.isEmpty()) {
			errorList.add("メールを入力してください");
		}
		if (address.isEmpty()) {
			errorList.add("住所を入力してください");
		}
		if (tel == null) {
			errorList.add("電話番号を入力してください");
		}
		if (password.isEmpty()) {
			errorList.add("パスワードを入力してください");
		} else if (password.length() < 8) {
			errorList.add("パスワードは8文字以上で入力してください");
		} else if (!password.matches("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]+$")) {
			errorList.add("パスワードは英字と数字の両方を含めてください");
		}
		if (!errorList.isEmpty()) {
			model.addAttribute("errors", errorList);
			return "user"; // ログインページにエラーを表示
		}

		Customers customers = new Customers(name, address, tel, email, password, image);
		customersRepository.save(customers);
		return "redirect:/login";
	}

	@GetMapping({ "/", "/login", "/logout" })
	public String index() {
		session.invalidate();
		return "login";
	}

	@PostMapping("/login")
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

		Customers Customers = customersRepository.findByEmail(email);

		if (!email.isEmpty() && !password.isEmpty()) {
			if (Customers != null && Customers.getPassword().equals(password)) {
				session.setAttribute("currentUser", Customers);
			} else {
				errorList.add("メールアドレスまたはパスワードが正しくありません");
			}
		}

		if (!errorList.isEmpty()) {
			model.addAttribute("errors", errorList);
			return "login"; // ログインページにエラーを表示
		}

		account.setName(Customers.getName());
		//        account.setId(Customers.getId());

		return "redirect:/tasks";
	}

	@GetMapping("/mypage")
	public String mypage() {
		return "mypage";
	}

	@GetMapping("/mypage/edit")
	public String edit() {
		return "edit";
	}

	@PostMapping("/mypage/edit")
	public String infoEdit(
			@RequestParam(name = "name", defaultValue = "") String name,
			@RequestParam(name = "address", defaultValue = "") String address,
			@RequestParam(name = "tel", defaultValue = "") Integer tel,
			@RequestParam(name = "email", defaultValue = "") String email,
			@RequestParam(name = "password", defaultValue = "") String password,
			@RequestParam(name = "image", defaultValue = "") String image,
			Model model) {

		List<String> errorList = new ArrayList<>();

		if (name.isEmpty()) {
			errorList.add("お名前を入力してください");
		}
		if (email.isEmpty()) {
			errorList.add("メールを入力してください");
		}
		if (address.isEmpty()) {
			errorList.add("住所を入力してください");
		}
		if (tel == null) {
			errorList.add("電話番号を入力してください");
		}
		if (password.isEmpty()) {
			errorList.add("パスワードを入力してください");
		}
		if (!errorList.isEmpty()) {
			model.addAttribute("errors", errorList);
			return "edit";

		}
		Customers customers = new Customers(name, address, tel, email, password, image);
		customersRepository.save(customers);
		return "redirect:/mypage";

	}

	@GetMapping("yado/history")
	public String history() {
		return "history";
	}
}
