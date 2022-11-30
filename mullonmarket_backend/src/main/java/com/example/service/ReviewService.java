package com.example.service;

import com.example.domain.ReviewVO;

public interface ReviewService {
	public void insertSellerReview(ReviewVO vo);
	public void insertBuyerReview(ReviewVO vo);

}
