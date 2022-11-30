package com.example.controller;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.json.simple.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.dao.TradeInfoDAO;

@RestController
@RequestMapping("/api/tradeinfo")
public class TradeInfoRestController {
	@Autowired
	TradeInfoDAO tdao;

	@RequestMapping("/buylist")
	public HashMap<String, Object> buyList(@Param("buyer") String buyer, @Param("page") int page) throws Exception {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("list", tdao.buyList(buyer, page));
		map.put("total", tdao.buyTotal(buyer));
		return map;
	};

	@RequestMapping("/selllist")
	public HashMap<String, Object> sellList(@Param("seller") String seller, @Param("page") int page) throws Exception {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("list", tdao.sellList(seller, page));
		map.put("total", tdao.sellTotal(seller));
		return map;
	};
	
	@RequestMapping("/sell/chart")
	public  List<HashMap<String,Integer>> sellchart(@Param("seller") String seller) throws Exception {
		 List<HashMap<String,Integer>> list=tdao.sellchart(seller);
		return list;
	};
	
	@RequestMapping("/buy/chart")
	public List<HashMap<String,Integer>> buyChart(@Param("buyer") String buyer) throws Exception {
		List<HashMap<String,Integer>> list=tdao.buychart(buyer);
		return list;
	};
	
	
}
