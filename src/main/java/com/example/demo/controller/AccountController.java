package com.example.demo.controller;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.entity.Customers;
import com.example.demo.entity.Hotels;
import com.example.demo.entity.Reservation;
import com.example.demo.model.Account;
import com.example.demo.repository.CustomersRepository;
import com.example.demo.repository.HotelsRepository;
import com.example.demo.repository.ReservationRepository;

@Controller
//コントローラーが必須に必要
public class AccountController {

    @Autowired
    HttpSession session;

    @Autowired
    CustomersRepository customersRepository;

    @Autowired
    ReservationRepository reservationRepository;

    @Autowired
    HotelsRepository hotelsRepository;

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
            @RequestParam(name = "tel", defaultValue = "") String tel,
            @RequestParam(name = "email", defaultValue = "") String email,
            @RequestParam(name = "password", defaultValue = "") String password,
            @RequestParam(name = "cardNo", defaultValue = "") String cardNo,
            @RequestParam(name = "code", defaultValue = "") String code,
            @RequestParam(name = "expiry", defaultValue = "") String expiry,
            Model model) {
        time();
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
        if (!(cardNo.equals("")) || !(code.equals("")) || !(expiry.equals(""))) {
            System.out.println(cardNo);
            System.out.println(code);
            System.out.println(expiry);
            if (cardNo.equals("")) {
                errorList.add("クレジットカード番号を入力してください");
            } else if (cardNo.length() > 16 || cardNo.length() < 14) {
                errorList.add("クレジットカード番号は14～16桁で入力してください");
            }
            if (code.equals("")) {
                errorList.add("セキュリティ番号を入力してください");
            } else if (code.length() != 3) {
                errorList.add("セキュリティ番号は3桁で入力してください");
            }
            String newString = "";
            Integer newExpiry = 0;
            if (expiry.equals("")) {
                errorList.add("有効期限を入力してください");
            } else if (!(expiry.equals(""))) {
                if (expiry.length() < 4) {
                    errorList.add("有効期限はMM(01～12)/YYの4桁で入力してください");
                } else {
                    newString = expiry.substring(0, 2);
                    newExpiry = Integer.parseInt(newString);
                    if (newExpiry < 1 || newExpiry > 12) {
                        errorList.add("有効期限はMM(01～12)/YYで入力してください");
                    }
                }
            }
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

    @GetMapping({ "/", "/logout" })
    public String index() {
        session.invalidate();
        return "login";
    }

    @GetMapping("/login")
    public String login() {
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

        String redirectUrl = (String) session.getAttribute("reservationLogin");
        Integer redirectId = (Integer) session.getAttribute("id");
        System.out.println(redirectUrl);
        System.out.println(redirectId);
        if (redirectUrl != null && redirectId != null) {
            return "redirect:" + redirectUrl + redirectId;
        }
        return "redirect:/hotels";
    }

    @GetMapping("/mypage")
    public String mypage(Model model) {
        Customers currentUser = (Customers) session.getAttribute("currentUser");
        if (currentUser == null) {
            return "redirect:/login";
        }

        Customers customers = customersRepository.findByEmail(account.getEmail());
        model.addAttribute("customers", customers);
        model.addAttribute("account", account);
        return "mypage";
    }

    @GetMapping("/mypage/edit")
    public String edit(Model model) {
        Customers customers = customersRepository.findByEmail(account.getEmail());
        model.addAttribute("customers", customers);
        return "edit";
    }

    @PostMapping("/mypage/edit")
    public String infoEdit(
            @RequestParam(name = "name", defaultValue = "") String name,
            @RequestParam(name = "address", defaultValue = "") String address,
            @RequestParam(name = "tel", defaultValue = "") String tel,
            @RequestParam(name = "email", defaultValue = "") String email,
            @RequestParam(name = "password", defaultValue = "") String password,
            @RequestParam(name = "imgname", defaultValue = "") String image,
            @RequestParam("file") MultipartFile file,
            @RequestParam(name = "cardNo", defaultValue = "") String cardNo,
            @RequestParam(name = "code", defaultValue = "") String code,
            @RequestParam(name = "expiry", defaultValue = "") String expiry,
            @RequestParam(name = "imgBtn", defaultValue = "") String imgBtn,
            Model model) {
        time();

        if (imgBtn.equals("")) {
            List<String> errorList = new ArrayList<>();

            Customers existingCustomer = customersRepository.findByEmail(email);
            Customers customers = customersRepository.findByName(account.getName());

            if (name.isEmpty()) {
                errorList.add("お名前を入力してください");
            }
            if (email.isEmpty()) {
                errorList.add("メールを入力してください");
            }
            if (customers.getEmail().equals(email)) {

            } else if (existingCustomer != null) {
                errorList.add("このメールアドレスは既に登録されています");
            }

            if (address.isEmpty()) {
                errorList.add("住所を入力してください");
            }
            if (tel.equals("")) {
                errorList.add("電話番号を入力してください");
            }
            if (password.isEmpty()) {
                errorList.add("パスワードを入力してください");
            } else if (password.length() < 8) {
                errorList.add("パスワードは8文字以上で入力してください");
            } else if (!password.matches("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]+$")) {
                errorList.add("パスワードは半角英字と数字の両方を含めてください");
            }
            if (!(cardNo.equals("")) || !(code.equals("")) || !(expiry.equals(""))) {
                System.out.println(cardNo);
                System.out.println(code);
                System.out.println(expiry);
                if (cardNo.equals("")) {
                    errorList.add("クレジットカード番号を入力してください");
                } else if (cardNo.length() > 16 || cardNo.length() < 14) {
                    errorList.add("クレジットカード番号は14～16桁で入力してください");
                }
                if (code.equals("")) {
                    errorList.add("セキュリティ番号を入力してください");
                } else if (code.length() != 3) {
                    errorList.add("セキュリティ番号は3桁で入力してください");
                }
                String newString = "";
                Integer newExpiry = 0;
                if (expiry.equals(",")) {
                    errorList.add("有効期限を入力してください");
                } else if (!(expiry.equals(","))) {
                    if (expiry.length() < 4) {
                        errorList.add("有効期限はMM(01～12)/YYの4桁で入力してください");
                    } else {
                        newString = expiry.substring(0, 2);
                        newExpiry = Integer.parseInt(newString);
                        if (newExpiry < 1 || newExpiry > 12) {
                            errorList.add("有効期限はMM(01～12)/YYで入力してください");
                        }
                    }
                }
            }
            if (!errorList.isEmpty()) {
                customers.setName(name);
                customers.setAddress(address);
                customers.setTel(tel);
                customers.setEmail(email);
                customers.setPassword(password);
                model.addAttribute("customers", customers);
                model.addAttribute("errors", errorList);
                return "edit";

            }

            customers = new Customers();
            customers = customersRepository.findById(account.getId()).get();

            try {
                String filename = file.getOriginalFilename();
                String filePath = "static/upload/" + filename;
                byte[] content = file.getBytes();
                Files.write(Paths.get(filePath), content);

                String imageUrl = "/upload/" + filename;
                model.addAttribute("imageUrl", imageUrl);
                customers.setImage(imageUrl);
            } catch (Exception e) {
                e.printStackTrace();
            }

            customersRepository.save(customers);
            customers.setName(name);
            customers.setAddress(address);
            customers.setTel(tel);
            customers.setEmail(email);
            customers.setPassword(password);
            customers.setCardNo(cardNo);
            customers.setCode(code);
            customers.setExpiry(expiry);
            customersRepository.save(customers);

            account.setName(name);
            return "redirect:/mypage";

        }
        Customers customers = new Customers();
        customers = customersRepository.findById(account.getId()).get();
        try {
            String filename = file.getOriginalFilename();
            String filePath = "static/upload/" + filename;
            byte[] content = file.getBytes();
            Files.write(Paths.get(filePath), content);

            String imageUrl = "/upload/" + filename;
            model.addAttribute("imageUrl", imageUrl);
            customers.setImage(imageUrl);
        } catch (Exception e) {
            e.printStackTrace();
        }
        model.addAttribute("customers", customers);
        return "edit";
    }

    @GetMapping("yado/history")
    public String history(Model model) {
        List<Reservation> reservations = new ArrayList<>();
        List<Hotels> hotels = new ArrayList<>();
        reservations = reservationRepository.findByCustomerId(account.getId());

        for (Reservation reservation : reservations) {
            Hotels hotel = hotelsRepository.findById(reservation.getHotelId()).get();
            hotels.add(hotel);
        }
        model.addAttribute("hotel", hotels);
        model.addAttribute("reservations", reservations);
        model.addAttribute("account", account);

        return "history";
    }

    public void time() {
        try {
            Thread.sleep(500); // 0.5秒(500ミリ秒)間だけ処理を止める
        } catch (InterruptedException e) {
        }
    }

}