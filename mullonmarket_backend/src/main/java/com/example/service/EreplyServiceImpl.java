package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.dao.EreplyDAO;

@Service
public class EreplyServiceImpl implements	EreplyService{

	@Autowired
	EreplyDAO erdao;
	
	@Override
	public void userDelete(int ercode) {
		erdao.delete(ercode);
		erdao.updateUserCondition(ercode);
	}

	@Override
	public void adminDelete(int ercode) {
		erdao.adminDelete(ercode);
		erdao.updateAdminCondition(ercode);
	}
	

}
