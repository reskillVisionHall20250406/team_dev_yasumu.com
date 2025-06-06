package com.example.demo.controller;

public class HotelsController {
    /*じょんへすん*/
    /*ヘスンです。*/
	/* 松本です */

		int pageSize = 8;
		Pageable pageable = PageRequest.of(page, pageSize);
		Page<Item> itemPage;

		// 条件に応じた検索処理
		if (areaId != null) {
			itemPage = itemRepository.findByCategoryId(categoryId, pageable);
		} else if (!keyword.isEmpty() && maxPrice != null) {
			itemPage = itemRepository.findByPriceLessThanEqualAndNameContaining(maxPrice, keyword, pageable);
		} else if (!keyword.isEmpty()) {
			itemPage = itemRepository.findByNameContaining(keyword, pageable);
		} else if (maxPrice != null) {
			itemPage = itemRepository.findByPriceLessThanEqual(maxPrice, pageable);
		} else {
			itemPage = itemRepository.findAll(pageable);
		}

		// 必要な情報をModelに追加
		model.addAttribute("categories", categoryRepository.findAll());
		model.addAttribute("items", itemPage.getContent()); // 商品リスト（1ページ分）
		model.addAttribute("page", itemPage); // Page情報（ページネーション用）
		model.addAttribute("account", account);
		model.addAttribute("keyword", keyword);
		model.addAttribute("maxPrice", maxPrice);
		model.addAttribute("categoryId", categoryId);

		return "items"; // 表示テンプレート
	}
}*/
