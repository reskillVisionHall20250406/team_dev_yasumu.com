package com.example.demo.controller;

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

import com.example.demo.entity.Admin;
import com.example.demo.entity.Hotels;
import com.example.demo.entity.Review;
import com.example.demo.model.Account;
import com.example.demo.repository.AdminRepository;
import com.example.demo.repository.AreaRepository;
import com.example.demo.repository.HotelsRepository;
import com.example.demo.repository.ReviewRepository;

@Controller
//コントローラーが必須に必要
public class AdminController {

	@Autowired
	HttpSession session;

	@Autowired
	AreaRepository areaRepository;

	@Autowired
	HotelsRepository hotelsRepository;

	@Autowired
	ReviewRepository reviewRepository;

	@Autowired
	AdminRepository adminRepository;

	@Autowired
	Account account;

	@GetMapping("/admin")
	public String index() {
		session.invalidate();
		return "adminLogin";
	}

	@PostMapping("/admin")
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

		Admin admin = adminRepository.findByEmail(email);

		if (!email.isEmpty() && !password.isEmpty()) {
			if (admin != null && admin.getPassword().equals(password)) {
				session.setAttribute("currentUser", admin);
			} else {
				errorList.add("メールアドレスまたはパスワードが正しくありません");
			}
		}

		if (!errorList.isEmpty()) {
			model.addAttribute("errors", errorList);
			model.addAttribute("email", email);
			return "adminLogin"; // ログインページにエラーを表示
		}

		account.setEmail(admin.getEmail());
		account.setName(admin.getName());
		account.setId(admin.getId());

		return "redirect:/home";
	}

	@GetMapping("/home")
	public String index(
			@RequestParam(name = "areaId", required = false, defaultValue = "0") Integer areaId,
			Model model) {

		List<Hotels> hotelsPage = hotelsRepository.findByAdminId(account.getId());

		// Modelに情報を追加
		model.addAttribute("areas", areaRepository.findAll());
		model.addAttribute("hotels", hotelsPage);
		model.addAttribute("account", account);

		return "admin_home";
	}

	@GetMapping("/admin/edit/{id}")
	public String showDetail(@PathVariable("id") Integer id,
			Model model) {

		//hotelsテーブルをID(主キー)で検索
		Hotels hotels = hotelsRepository.findById(id).get();
		List<Review> reviews = reviewRepository.findByHotelId(id);

		model.addAttribute("hotels", hotels);
		model.addAttribute("reviews", reviews);
		return "showHotels";
	}

	@PostMapping("/admin/edit/{id}")
	public String editDetail(@PathVariable("id") Integer id,
			Model model) {

		//hotelsテーブルをID(主キー)で検索
		Hotels hotels = hotelsRepository.findById(id).get();

		model.addAttribute("hotels", hotels);
		return "hotelsEdit";
	}

	@PostMapping("/admin/edit/hotels/{id}")
	public String edit(
			@RequestParam(name = "name", defaultValue = "") String name,
			@RequestParam(name = "address", defaultValue = "") String address,
			@RequestParam(name = "capacity", defaultValue = "") Integer capacity,
			@RequestParam(name = "price", defaultValue = "") Integer price,
			@PathVariable("id") Integer id,
			Model model) {

		List<String> err = new ArrayList<>();
		if (name.equals("")) {
			err.add("宿名を入力してください");
		}

		if (address.equals("")) {
			err.add("住所を入力してください");
		}

		if (capacity == null) {
			err.add("宿泊人数を入力してください");
		} else if (capacity == 0) {
			err.add("宿泊人数は１以上を入力してください");
		}

		if (price == null) {
			err.add("料金を入力してください");
		} else if (price == 0) {
			err.add("料金は１以上を入力してください");
		}
		if (err.isEmpty()) {
			Hotels hotels = hotelsRepository.findById(id).get();

			model.addAttribute("hotels", hotels);
			return "hotelsEdit";
		}
		Hotels hotels = hotelsRepository.findById(id).get();
		model.addAttribute("hotels", hotels);
		model.addAttribute("errors", err);
		return "hotelsEdit";
	}
}