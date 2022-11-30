package com.example.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.dao.AdminDAO;


@Controller
public class HomeController {
	
	@Autowired
	AdminDAO adao;
	
	
	@RequestMapping("/")
	public String tiles( Model model,HttpSession session) {
		model.addAttribute("login_info",adao.read((String) session.getAttribute("aid")));
		return "home.tiles";
	}
	

}
