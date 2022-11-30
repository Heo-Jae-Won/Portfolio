package com.example.dao;

import java.util.HashMap;
import java.util.List;

import com.example.domain.PayVO;
import com.example.domain.PboardVO;

public interface TradeInfoDAO {
	public List<PayVO> buyList(String buyer,int page);
	public List<PboardVO> sellList(String seller,int page);
	public int buyTotal(String buyer);
	public int sellTotal(String seller);
	public List<HashMap<String,Integer>> sellchart(String seller);
	public List<HashMap<String,Integer>> buychart(String buyer);
}
