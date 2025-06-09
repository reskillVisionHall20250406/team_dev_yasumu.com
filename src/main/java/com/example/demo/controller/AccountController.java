package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entity.Customers;
import com.example.demo.model.Account;
import com.example.demo.repository.CustomersRepository;

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
            @RequestParam(name = "email", defaultValue = "") String email,
            @RequestParam(name = "name", defaultValue = "") String name,
            @RequestParam(name = "password", defaultValue = "") String password,
            @RequestParam(name = "password_confirm", defaultValue = "") String password_confirm,
            @RequestParam(name = "tell", defaultValue = "") Integer tell,
            Model model) {

        List<String> errorList = new ArrayList<>();

        if (email.isEmpty()) {
            errorList.add("メールを入力してください");
        }
        if (name.isEmpty()) {
            errorList.add("お名前を入力してください");
        }
        if (password.isEmpty()) {
            errorList.add("パスワードを入力してください");
        }
        if (!password.equals(password_confirm)) {
            errorList.add("パスワードが一致しません");
        }

        if (!errorList.isEmpty()) {
            model.addAttribute("errors", errorList);
            return "user"; // ログインページにエラーを表示
        }

        Customers Cusomers = new Customers(email, name, password, tell);
        CustomersRepository.save(Customers);
        return "redirect:/login";
    }

    //로그인 화면 
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
        //로그인화면 미입력시 에러리스트
        List<String> errorList = new ArrayList<>();

        if (email.isEmpty()) {
            errorList.add("メールを入力してください");
        }
        if (password.isEmpty()) {
            errorList.add("パスワードを入力してください");
        }

        //로그인 성공시 타스크로 이동, 실패시 에러
        User user = userRepository.findByEmail(email);

        if (!email.isEmpty() && !password.isEmpty()) {
            if (user != null && user.getPassword().equals(password)) {
                session.setAttribute("currentUser", user);
            } else {
                errorList.add("メールアドレスまたはパスワードが正しくありません");
            }
        }

        if (!errorList.isEmpty()) {
            model.addAttribute("errors", errorList);
            return "login"; // ログインページにエラーを表示
        }
        //        List<User> users = userRepository.findByEmailAndPassword(email, password);

        //        User u = users.get(0);
        account.setName(user.getName());
        account.setId(user.getId());
        //여기서 이름 받아서 홈페이지에 표시가능하게 날려줌
        return "redirect:/tasks";
    }
}
