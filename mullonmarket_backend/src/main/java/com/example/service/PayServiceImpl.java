package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.dao.PayDAO;
import com.example.dao.PboardDAO;
import com.example.domain.PayVO;

@Service
public class PayServiceImpl implements PayService{

	@Autowired
	PayDAO padao;
	
	@Autowired
	PboardDAO pdao;
	
	@Transactional
	@Override
	public void insert(PayVO insertVO) {
		padao.insert(insertVO);
		pdao.updateSold(insertVO.getPcode());
	}

}
