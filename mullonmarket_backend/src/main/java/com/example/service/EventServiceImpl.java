package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.dao.EreplyDAO;
import com.example.dao.EventDAO;

@Service
public class EventServiceImpl implements EventService {

	@Autowired
	EventDAO edao;

	@Autowired
	EreplyDAO erdao;

	@Transactional
	@Override
	public void eventDelete(int ecode) {
		erdao.allDelete(ecode);
		edao.delete(ecode);
	}

}
