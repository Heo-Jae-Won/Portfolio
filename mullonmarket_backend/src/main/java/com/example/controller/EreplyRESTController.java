package com.example.controller;

import java.util.HashMap;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.dao.EreplyDAO;
import com.example.domain.EreplyVO;
import com.example.service.EreplyService;

@RestController
@RequestMapping("/api/ereply")
public class EreplyRESTController {
	@Autowired
	EreplyDAO erdao;
	
	@Autowired
	EreplyService erservice;

	
	@RequestMapping("/list")
	public HashMap<String,Object> list(@Param("page") int page, @Param("num")int num, @Param("ecode")int ecode)throws Exception{
		HashMap<String,Object> map = new HashMap<String,Object>();
		map.put("list", erdao.list(page, num, ecode));
		map.put("total", erdao.getTotal(ecode));
		return map;
	}
	
	@RequestMapping(value="/insert",method=RequestMethod.POST)
	public void insert(@RequestBody EreplyVO vo){
		erdao.insert(vo);
	}
	
	@RequestMapping(value = "/delete/{ercode}", method = RequestMethod.POST)
	public void delete(@PathVariable int ercode) {
		erservice.userDelete(ercode);
	}

	@RequestMapping(value = "/adminDelete/{ercode}", method = RequestMethod.POST)
	public void adminDelete(@PathVariable int ercode) {
		erservice.adminDelete(ercode);
	}
	
	
	@RequestMapping(value="/update",method=RequestMethod.POST)
	public void update(@RequestBody EreplyVO vo){
		erdao.update(vo);
	}
	
	
}