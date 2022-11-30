package com.example.dao;

import com.example.domain.PayVO;

public interface PayDAO {
	public void insert(PayVO payVO);
	public void updateSellerCondition(String paycode);
	public void updateBuyerCondition(String paycode);
	public PayVO read(String paycode);
}
