package com.example.demo.controller;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entity.Hotels;
import com.example.demo.entity.Review;
import com.example.demo.model.Account;
import com.example.demo.model.Log;
import com.example.demo.repository.AreaRepository;
import com.example.demo.repository.HotelsRepository;
import com.example.demo.repository.ReviewRepository;

@Controller
public class HotelsController {

	@Autowired
	private ReviewRepository reviewRepository;

	@Autowired
	private HotelsRepository hotelsRepository;

	@Autowired
	private AreaRepository areaRepository;

	@Autowired
	private Account account;

	@Autowired
	Log log;

	// ホテル一覧表示
	@GetMapping("/hotels")
	public String index(
			@RequestParam(name = "capacity", required = false) String capacity1,
			@RequestParam(name = "price", required = false) Integer price,
			@RequestParam(name = "keyword", required = false) String keyword,
			@RequestParam(name = "areaId", required = false, defaultValue = "0") Integer areaId,
			@RequestParam(name = "page", defaultValue = "0") Integer page,
			Model model) {

		// 先頭付近
		Integer capacity = null;
		if (capacity1 != null && !capacity1.isBlank()) {
			try {
				capacity = Integer.parseInt(capacity1);
			} catch (NumberFormatException e) {
				// 数字でなかった場合は無視（そのまま capacityNum = null）
			}
		}

		int pageSize = 8;
		Pageable pageable = PageRequest.of(page, pageSize);
		Page<Hotels> hotelsPage;
		Double i = (double) 0;
		//		List <Hotels> hotels = hotelsRepository.findAll();
		List<Integer> stars = new ArrayList<>();

		//		for(Hotels hotel:hotels) {
		//			Integer id = hotel.getId();
		//			List <Review> reviews = reviewRepository.findByHotelId(id);
		//			for(Review data: reviews) {
		//				i += data.getStar();
		//			}
		//			i /= reviews.size();
		//			stars.add(i);
		//		}

		if (areaId > 0 && capacity != null && price != null && keyword != null && !keyword.isEmpty()) {
			hotelsPage = hotelsRepository.findByAreaIdAndCapacityAndPriceLessThanEqualAndNameContaining(
					areaId, capacity, price, keyword, pageable);

		} else if (areaId > 0 && capacity != null && price != null) {
			hotelsPage = hotelsRepository.findByAreaIdAndCapacityAndPriceLessThanEqual(
					areaId, capacity, price, pageable);
		} else if (areaId > 0 && capacity != null && keyword != null && !keyword.isEmpty()) {
			hotelsPage = hotelsRepository.findByAreaIdAndCapacityAndNameContaining(
					areaId, capacity, keyword, pageable);
		} else if (areaId > 0 && price != null && keyword != null && !keyword.isEmpty()) {
			hotelsPage = hotelsRepository.findByAreaIdAndPriceLessThanEqualAndNameContaining(
					areaId, price, keyword, pageable);
		} else if (capacity != null && price != null && keyword != null && !keyword.isEmpty()) {
			hotelsPage = hotelsRepository.findByCapacityAndPriceLessThanEqualAndNameContaining(
					capacity, price, keyword, pageable);
		} else if (areaId > 0 && capacity != null) {
			hotelsPage = hotelsRepository.findByAreaIdAndCapacity(
					areaId, capacity, pageable);
		} else if (areaId > 0 && price != null) {
			hotelsPage = hotelsRepository.findByAreaIdAndPriceLessThanEqual(
					areaId, price, pageable);
		} else if (areaId > 0 && keyword != null && !keyword.isEmpty()) {
			hotelsPage = hotelsRepository.findByAreaIdAndNameContaining(
					areaId, keyword, pageable);
		} else if (capacity != null && price != null) {
			hotelsPage = hotelsRepository.findByCapacityAndPriceLessThanEqual(
					capacity, price, pageable);
		} else if (capacity != null && keyword != null && !keyword.isEmpty()) {
			hotelsPage = hotelsRepository.findByCapacityAndNameContaining(
					capacity, keyword, pageable);
		} else if (price != null && keyword != null && !keyword.isEmpty()) {
			hotelsPage = hotelsRepository.findByPriceLessThanEqualAndNameContaining(
					price, keyword, pageable);
		} else if (areaId > 0) {
			hotelsPage = hotelsRepository.findByAreaId(areaId, pageable);
		} else if (capacity != null) {
			hotelsPage = hotelsRepository.findByCapacity(capacity, pageable);
		} else if (price != null) {
			hotelsPage = hotelsRepository.findByPriceLessThanEqual(price, pageable);
		} else if (keyword != null && !keyword.isEmpty()) {
			hotelsPage = hotelsRepository.findByNameContaining(keyword, pageable);
		} else {
			hotelsPage = hotelsRepository.findAll(pageable);
		}

		for (Hotels hotel : hotelsPage) {
			Integer id = hotel.getId();
			List<Review> reviews = reviewRepository.findByHotelId(id);

			if (!reviews.isEmpty()) {
				double total = 0;
				for (Review data : reviews) {
					total += data.getStar();
				}

				// 平均を求める
				double average = total / reviews.size();

				// 小数第2位で四捨五入してdoubleに戻す
				BigDecimal bd = new BigDecimal(average);
				double rounded = bd.setScale(1, RoundingMode.HALF_UP).doubleValue();

				hotel.setStars(rounded); // setStars が double を受け取るようにする
				hotel.setStarVisual(getStarVisual(rounded));
			} else {
				hotel.setStars(0.0);
			}
			hotelsRepository.save(hotel);
		}

		PageRequest num = PageRequest.of(0, 2);
		List<Hotels> topHotels = hotelsRepository.findTop2OrderByStarsDescJPQL(num);

		// Modelに情報を追加
		model.addAttribute("areas", areaRepository.findAll());
		model.addAttribute("hotels", hotelsPage.getContent());
		model.addAttribute("page", hotelsPage);
		model.addAttribute("account", account);
		model.addAttribute("keyword", keyword);
		model.addAttribute("price", price);
		model.addAttribute("areaId", areaId);
		model.addAttribute("maxHotels", topHotels);

		return "hotels";
	}

	private String getStarVisual(double stars) {
		int full = (int) stars;
		boolean half = (stars - full) >= 0.5;
		int empty = 5 - full - (half ? 1 : 0);

		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < full; i++)
			sb.append("★");
		if (half)
			sb.append("☆");
		for (int i = 0; i < empty; i++)
			sb.append("☆");

		return sb.toString();
	}

	@GetMapping("/hotelsdetail/{id}")
	public String showDetail(@PathVariable("id") Integer id,
			Model model) {

		//hotelsテーブルをID(主キー)で検索
		Hotels hotels = hotelsRepository.findById(id).get();
		List<Review> reviews = reviewRepository.findByHotelId(id);

		if (!reviews.isEmpty()) {
			double total = 0;
			for (Review data : reviews) {
				total += data.getStar();
			}

			// 平均を求める
			double average = total / reviews.size();

			// 小数第2位で四捨五入してdoubleに戻す
			BigDecimal bd = new BigDecimal(average);
			double rounded = bd.setScale(1, RoundingMode.HALF_UP).doubleValue();

			hotels.setStars(rounded); // setStars が double を受け取るようにする
			hotels.setStarVisual(getStarVisual(rounded));
		} else {
			hotels.setStars(0.0);
		}

		model.addAttribute("account", account);
		model.addAttribute("hotels", hotels);
		model.addAttribute("reviews", reviews);
		return "hotelsdetail";
	}

	@GetMapping("/hotelsdetail/comment/{id}")
	public String comment(@PathVariable("id") Integer id,
			@RequestParam(name = "star", defaultValue = "1") Integer star,
			@RequestParam(name = "comment", defaultValue = "") String comment,
			Model model) {
		Review review = new Review(star, comment, id);
		reviewRepository.save(review);
		return "redirect:/hotelsdetail/" + id;
	}
}
