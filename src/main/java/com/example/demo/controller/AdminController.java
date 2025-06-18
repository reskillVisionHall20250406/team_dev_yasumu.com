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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

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
	public String index(Model model) {

		List<Hotels> hotelsPage = hotelsRepository.findByAdminId(account.getId());

		model.addAttribute("hotels", hotelsPage);
		model.addAttribute("account", account);

		return "admin_home";
	}

	@GetMapping("/admin/detail/{id}")
	public String showDetail(@PathVariable("id") Integer id,
			Model model) {

		//hotelsテーブルをID(主キー)で検索
		Hotels hotels = hotelsRepository.findById(id).get();
		List<Review> reviews = reviewRepository.findByHotelId(id);

		model.addAttribute("hotels", hotels);
		model.addAttribute("reviews", reviews);
		return "showHotels";
	}

	@GetMapping("/admin/edit/{id}")
	public String returnEditDetail(@PathVariable("id") Integer id,
			Model model) {
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
		return "admin_hotelsEdit";
	}

	@PostMapping("/admin/edit/hotels/{id}")
	public String edit(
			@RequestParam(name = "name", defaultValue = "") String name,
			@RequestParam(name = "address", defaultValue = "") String address,
			@RequestParam(name = "capacity", defaultValue = "") Integer capacity,
			@RequestParam(name = "price", defaultValue = "") Integer price,
			@RequestParam(name = "detail", defaultValue = "") String detail,
			@RequestParam(name = "file", defaultValue = "") MultipartFile file,
			@RequestParam(name = "file2", defaultValue = "") MultipartFile file2,
			@RequestParam(name = "file3", defaultValue = "") MultipartFile file3,
			@PathVariable("id") Integer id,
			Model model) {
		Hotels hotels = hotelsRepository.findById(id).get();
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
			try {
				String contentType = file.getContentType();
				String contentType2 = file2.getContentType();
				String contentType3 = file3.getContentType();
				if (contentType.startsWith("image/")) {
					String filename = file.getOriginalFilename();
					String filePath = "static/upload/" + filename;
					byte[] content = file.getBytes();
					Files.write(Paths.get(filePath), content);
					String imageUrl = "/upload/" + filename;
					hotels.setImage(imageUrl);

				}

				if (contentType2.startsWith("image/")) {
					String filename2 = file2.getOriginalFilename();
					String filePath2 = "static/upload/" + filename2;
					byte[] content2 = file2.getBytes();
					Files.write(Paths.get(filePath2), content2);
					String imageUrl2 = "/upload/" + filename2;
					hotels.setImage2(imageUrl2);
				}
				if (contentType3.startsWith("image/")) {
					String filename3 = file3.getOriginalFilename();
					String filePath3 = "static/upload/" + filename3;
					byte[] content3 = file3.getBytes();
					Files.write(Paths.get(filePath3), content3);
					String imageUrl3 = "/upload/" + filename3;
					hotels.setImage3(imageUrl3);
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
			hotels.setName(name);
			hotels.setAddress(address);
			hotels.setCapacity(capacity);
			hotels.setDetail(detail);
			hotelsRepository.save(hotels);
			return "redirect:/admin/edit/" + id;
		}

		model.addAttribute("hotels", hotels);
		model.addAttribute("errors", err);
		return "admin_hotelsEdit";
	}

	@GetMapping("/admin/register")
	public String showAdminRegister() {

		return "admin_register";
	}

	@PostMapping("/hotels/add")
	public String add(
			@RequestParam(name = "name", defaultValue = "") String name,
			@RequestParam(name = "areaId", defaultValue = "") Integer areaId,
			@RequestParam(name = "detail", defaultValue = "") String detail,
			@RequestParam(name = "address", defaultValue = "") String address,
			@RequestParam("file") MultipartFile file,
			@RequestParam("file2") MultipartFile file2,
			@RequestParam("file3") MultipartFile file3,
			@RequestParam(name = "capacity", required = false) Integer capacity,
			@RequestParam(name = "price", required = false) Integer price,
			Model model) {

		List<String> errorList = new ArrayList<>();
		//      여기 밑에줄에서 이메일을 DB에서 찾아와서 메일이 등록되이는지 확인된다 눌이 아닌 확인하도록 에러코드
		Hotels existingHotel = hotelsRepository.findByNameAndAddress(name, address);

		if (name.isEmpty()) {
			errorList.add("お名前を入力してください");
		}
		if (detail.isEmpty()) {
			errorList.add("説明文を入力してください");
		} else if (existingHotel != null) {
			errorList.add("このホテルは既に登録されています");
		}
		if (address.isEmpty()) {
			errorList.add("住所を入力してください");
		}
		if (file.isEmpty()) {
			errorList.add("画像を入力してください");
		}
		if (file2.isEmpty()) {
			errorList.add("サブ画像を入力してください");
		}
		if (file3.isEmpty()) {
			errorList.add("サブ画像2を入力してください");
		}
		if (capacity == null) {
			errorList.add("宿泊人数を入力してください");
		}
		if (price == null) {
			errorList.add("価格を入力してください");
		}

		Hotels hotels = new Hotels();
		if (!errorList.isEmpty()) {
			model.addAttribute("errors", errorList);
			model.addAttribute("name", name);
			model.addAttribute("detail", detail);
			model.addAttribute("address", address);
			return "admin_register"; // ログインページにエラーを表示
		}

		try {
			String filename = file.getOriginalFilename();
			String filePath = "static/upload/" + filename;
			byte[] content = file.getBytes();
			Files.write(Paths.get(filePath), content);

			String filename2 = file2.getOriginalFilename();
			String filePath2 = "static/upload/" + filename2;
			byte[] content2 = file2.getBytes();
			Files.write(Paths.get(filePath2), content2);

			String filename3 = file3.getOriginalFilename();
			String filePath3 = "static/upload/" + filename3;
			byte[] content3 = file3.getBytes();
			Files.write(Paths.get(filePath3), content3);

			String imageUrl = "/upload/" + filename;
			String imageUrl2 = "/upload/" + filename2;
			String imageUrl3 = "/upload/" + filename3;
			model.addAttribute("imageUrl", imageUrl);
			model.addAttribute("imageUrl2", imageUrl2);
			model.addAttribute("imageUrl3", imageUrl3);
			hotels.setImage(imageUrl);
			hotels.setImage2(imageUrl2);
			hotels.setImage3(imageUrl3);
		} catch (Exception e) {
			e.printStackTrace();
		}
		hotels.setName(name);
		hotels.setAddress(address);
		hotels.setCapacity(capacity);
		hotels.setPrice(price);
		hotels.setDetail(detail);
		hotels.setAreaId(areaId);
		hotelsRepository.save(hotels);
		return "redirect:/home";

	}

}
