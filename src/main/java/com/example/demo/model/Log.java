package com.example.demo.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import com.example.demo.entity.Hotels;

@Component
@SessionScope
public class Log {

	// 商品リスト
	public List<Hotels> loghotels = new ArrayList<>();

	// 商品リストゲッター
	public List<Hotels> getHotels() {
		return loghotels;
	}


	public void add(Hotels newHotels) {
		Hotels existsHotels = null;
		// 現在のカートの商品から同一IDの商品を探す
		for (Hotels hotels : loghotels) {
			if (hotels.getId() == newHotels.getId()) {
				existsHotels = hotels;
				break;
			}
		}

		// カート内に商品が存在しなかった場合はカート追加
		// 存在した場合は、個数の更新を行う
		if (existsHotels == null) {
			loghotels.add(newHotels);
		}
	}
}
