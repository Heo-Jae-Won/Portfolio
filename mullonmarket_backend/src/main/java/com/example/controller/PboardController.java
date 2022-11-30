package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.dao.PboardDAO;

@Controller
@RequestMapping("/pboard")
public class PboardController {
	
	@Autowired
	PboardDAO pdao;

	
	@RequestMapping(value="/list")
	public String list(Model model,int page,int num, String searchType, String keyword) throws Exception {
		int last=(pdao.getTotal(searchType,keyword)%6)==0 ?(pdao.getTotal(searchType,keyword)/6):(pdao.getTotal(searchType,keyword)/6)+1;
		model.addAttribute("last",last);
		model.addAttribute("page",page);
		model.addAttribute("num",6);
		model.addAttribute("keyword",keyword);
		model.addAttribute("list", pdao.list(page, 6,searchType, keyword));
		return "pboard/list";
	}
	
	   @RequestMapping(value = "/read/{pcode}", method = RequestMethod.GET)
	   public String read(Model model, @PathVariable String pcode) {
	      model.addAttribute("vo", pdao.read(pcode));
	      return "pboard/read";
	   }
	
	

	

}
