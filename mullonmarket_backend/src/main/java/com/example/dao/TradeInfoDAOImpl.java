package com.example.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.domain.PayVO;
import com.example.domain.PboardVO;

@Repository
public class TradeInfoDAOImpl implements TradeInfoDAO {
	@Autowired
	SqlSession session;
	String namespace = "com.example.mapper.TradeInfoMapper";

	@Override
	public List<PayVO> buyList(String buyer, int page) {
		HashMap<String, Object> map = new HashMap<>();
		map.put("buyer", buyer);
		map.put("start", (page - 1) * 6);
		return session.selectList(namespace + ".buyList", map);
	}

	@Override
	public List<PboardVO> sellList(String seller, int page) {
		HashMap<String, Object> map = new HashMap<>();
		map.put("seller", seller);
		map.put("start", (page - 1) * 6);
		return session.selectList(namespace + ".sellList", map);
	}

	@Override
	public int buyTotal(String buyer) {
		return session.selectOne(namespace + ".buyTotal", buyer);
	}

	@Override
	public int sellTotal(String seller) {
		return session.selectOne(namespace + ".sellTotal", seller);
	}

	@Override
	public  List<HashMap<String,Integer>> sellchart(String seller) {
		return session.selectList(namespace + ".sellchart", seller);
	}

	@Override
	public  List<HashMap<String,Integer>> buychart(String buyer) {
		return session.selectList(namespace + ".buychart", buyer);
	}

}
