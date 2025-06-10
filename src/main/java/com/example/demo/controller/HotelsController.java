package com.example.demo.controller;

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
import com.example.demo.model.Account;
import com.example.demo.repository.AreaRepository;
import com.example.demo.repository.HotelsRepository;

@Controller
public class HotelsController {

	@Autowired
	private HotelsRepository hotelsRepository;

	@Autowired
	private AreaRepository areaRepository;

	@Autowired
	private Account account;

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

		// 検索条件の組み合わせに応じて処理を分岐
		if (areaId > 0 && capacity != null && price != null && keyword != null && !keyword.isEmpty()) {
			hotelsPage = hotelsRepository.findByAreaIdAndCapacityAndPriceLessThanEqualAndNameContaining(
					areaId, capacity, price, keyword, pageable);
		} else if (areaId > 0 && capacity != null && price != null) {
			hotelsPage = hotelsRepository.findByAreaIdAndCapacityAndPriceLessThanEqual(
					areaId, capacity, price, pageable);
		} else if (areaId > 0 && capacity != null && keyword != null && !keyword.isEmpty()) {
			hotelsPage = hotelsRepository.findByAreaIdAndCapacityAndNameContaining(
					areaId, capacity, keyword, pageable);
		} else if (areaId > 0 && capacity != null) {
			hotelsPage = hotelsRepository.findByAreaIdAndCapacity(
					areaId, capacity, pageable);
		} else if (capacity != null && price != null && keyword != null && !keyword.isEmpty()) {
			hotelsPage = hotelsRepository.findByCapacityAndPriceLessThanEqualAndNameContaining(
					capacity, price, keyword, pageable);
		} else if (capacity != null && price != null) {
			hotelsPage = hotelsRepository.findByCapacityAndPriceLessThanEqual(
					capacity, price, pageable);
		} else if (capacity != null && keyword != null && !keyword.isEmpty()) {
			hotelsPage = hotelsRepository.findByCapacityAndNameContaining(
					capacity, keyword, pageable);
		} else if (capacity != null) {
			hotelsPage = hotelsRepository.findByCapacity(
					capacity, pageable);
		} else if (areaId > 0) {
			hotelsPage = hotelsRepository.findByAreaId(areaId, pageable);
		} else if (keyword != null && !keyword.isEmpty() && price != null) {
			hotelsPage = hotelsRepository.findByPriceLessThanEqualAndNameContaining(price, keyword, pageable);
		} else if (keyword != null && !keyword.isEmpty()) {
			hotelsPage = hotelsRepository.findByNameContaining(keyword, pageable);
		} else if (price != null) {
			hotelsPage = hotelsRepository.findByPriceLessThanEqual(price, pageable);
		} else {
			hotelsPage = hotelsRepository.findAll(pageable);
		}

		// Modelに情報を追加
		model.addAttribute("areas", areaRepository.findAll());
		model.addAttribute("hotels", hotelsPage.getContent());
		model.addAttribute("page", hotelsPage);
		model.addAttribute("account", account);
		model.addAttribute("keyword", keyword);
		model.addAttribute("price", price);
		model.addAttribute("areaId", areaId);
		model.addAttribute("account", account);

		return "hotels";
	}

	@GetMapping("/hotelsdetail/{id}")
	public String showDetail(@PathVariable("id") Integer id,
			Model model) {

		//itemsテーブルをID(主キー)で検索
		Hotels hotels = hotelsRepository.findById(id).get();

		model.addAttribute("account", account);
		model.addAttribute("hotels", hotels);
		return "hotelsdetail";
	}
}
