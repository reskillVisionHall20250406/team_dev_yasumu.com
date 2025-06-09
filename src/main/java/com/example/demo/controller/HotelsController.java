package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
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
            @RequestParam(name = "capacity", required = false) String capacity,
            @RequestParam(name = "price", required = false) Integer price,
            @RequestParam(name = "keyword", required = false) String keyword,
            @RequestParam(name = "areaId", required = false, defaultValue = "0") Integer areaId,
            @RequestParam(name = "page", defaultValue = "0") Integer page,
            Model model) {

        int pageSize = 8;
        Pageable pageable = PageRequest.of(page, pageSize);
        Page<Hotels> hotelsPage;

        // 条件に応じた検索処理
       /* if (areaId != null && areaId > 0) {
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
        */
        hotelsPage = hotelsRepository.findAll(pageable);

        // Modelに情報を追加
        model.addAttribute("areas", areaRepository.findAll());
        model.addAttribute("hotels", hotelsPage.getContent());
        model.addAttribute("page", hotelsPage);
        model.addAttribute("account", account);
        model.addAttribute("keyword", keyword);
        model.addAttribute("price", price);
        model.addAttribute("areaId", areaId);

        return "hotels"; 
    }
}
