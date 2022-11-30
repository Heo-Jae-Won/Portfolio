package com.example.controller;

import java.util.HashMap;
import java.util.UUID;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.dao.PayDAO;
import com.example.dao.PboardDAO;
import com.example.dao.ReviewDAO;
import com.example.domain.ReviewVO;
import com.example.service.ReviewService;

@RestController
@RequestMapping("/api/review")
public class ReviewRestController {

	@Autowired
	ReviewDAO rdao;

	@Autowired
	ReviewService rservice;

	@Autowired
	PayDAO padao;

	@Autowired
	PboardDAO pdao;

	@RequestMapping("/list")
	public HashMap<String, Object> list(@Param("page") int page, @Param("num") int num,
			@Param("receiver") String receiver) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("list", rdao.list(page, num, receiver));
		map.put("total", rdao.total(receiver));
		return map;
	}

	@RequestMapping(value = "/insert", method = RequestMethod.POST)
	public int insert(ReviewVO InsertVO) {
		int result = 0;

		if (padao.read(InsertVO.getPaycode()) != null) {
			UUID code = UUID.randomUUID();
			InsertVO.setRvcode(code.toString());

			String pwriter = pdao.readPwriter(InsertVO.getPcode());

			if (InsertVO.getSender().equals(pwriter)) {
				rservice.insertSellerReview(InsertVO);
				result = 1;

			} else {
				rservice.insertBuyerReview(InsertVO);
				result = 1;
			}

		}
		return result;
	}

}
