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
    CustomersRepository CustomersRepository;

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
        if (address.isEmpty()) {
            errorList.add("住所を入力してください");
        }
        if (tel == null) {
            errorList.add("電話番号を入力してください");
        }
        if (email.isEmpty()) {
            errorList.add("メールを入力してください");
        }
        if (password.isEmpty()) {
            errorList.add("パスワードを入力してください");
        }
        if (!errorList.isEmpty()) {
            model.addAttribute("errors", errorList);
            return "user"; // ログインページにエラーを表示
        }

        Customers customers = new Customers(name, address, tel, email, password, image);
        CustomersRepository.save(customers);
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

        Customers Customers = CustomersRepository.findByEmail(email);

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
}
