package com.example.dao;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.domain.PayVO;

@Repository
public class PayDAOImpl implements PayDAO {

	@Autowired
	SqlSession session;

	String namespace = "com.example.mapper.PayMapper";

	@Override
	public void insert(PayVO payVO) {
		session.insert(namespace + ".insert",payVO);
	}

	@Override
	public void updateSellerCondition(String paycode) {
		session.update(namespace+".updateSellerCondition",paycode);
		
	}

	@Override
	public void updateBuyerCondition(String paycode) {
		session.update(namespace+".updateBuyerCondition",paycode);
		
	}

	@Override
	public PayVO read(String paycode) {
		return session.selectOne(namespace+".read",paycode);
	}



}
