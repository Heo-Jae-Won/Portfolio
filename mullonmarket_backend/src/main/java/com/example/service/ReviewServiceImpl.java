package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.dao.PayDAO;
import com.example.dao.ReviewDAO;
import com.example.dao.UserDAO;
import com.example.domain.ReviewVO;

@Service
public class ReviewServiceImpl implements ReviewService {

	@Autowired
	ReviewDAO rdao;

	@Autowired
	UserDAO udao;

	@Autowired
	PayDAO padao;

	@Transactional
	@Override
	public void insertSellerReview(ReviewVO SellerVO) {
		rdao.insert(SellerVO);
		udao.updateUpoint(SellerVO.getReceiver());
		padao.updateSellerCondition(SellerVO.getPaycode());
	}

	@Transactional
	@Override
	public void insertBuyerReview(ReviewVO BuyerVO) {
		rdao.insert(BuyerVO);
		udao.updateUpoint(BuyerVO.getReceiver());
		padao.updateBuyerCondition(BuyerVO.getPaycode());
	}

}
