package com.example.controller;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.dao.AdminDAO;
import com.example.dao.PboardDAO;
import com.example.domain.AdminVO;

@RestController
@RequestMapping("/admin")
public class AdminLoginRestController {

	@Autowired
	AdminDAO adao;

	@Autowired
	PboardDAO pdao;

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public int loginPost(AdminVO loginVO, HttpSession session) {

		int result = 0;

		AdminVO ReadVO = adao.read(loginVO.getAid());

		if (ReadVO != null) {

			// Login success
			if (ReadVO.getApass().equals(loginVO.getApass())) {
				result = 1;
				session.setAttribute("aid", loginVO.getAid());

				// Login pass not same
			} else {
				result = 2;
			}
		}
		return result;
	}

	@RequestMapping(value = "/logout", method = RequestMethod.POST)
	public void logout(HttpSession session) {
		session.removeAttribute("aid");
	}



}
