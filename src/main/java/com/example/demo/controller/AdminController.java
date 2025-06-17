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

import com.example.demo.entity.Admin;
import com.example.demo.model.Account;
import com.example.demo.repository.AdminRepository;
import com.example.demo.repository.HotelsRepository;

@Controller
//コントローラーが必須に必要
public class AdminController {

	@Autowired
	HttpSession session;

	@Autowired
	HotelsRepository hotelsRepository;

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

		return "admin_home";
	}

}