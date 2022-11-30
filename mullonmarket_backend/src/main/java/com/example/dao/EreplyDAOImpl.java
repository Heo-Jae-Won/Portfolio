package com.example.dao;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.domain.EreplyVO;

@Repository
public class EreplyDAOImpl implements EreplyDAO{
	@Autowired
	SqlSession session;
	String namespace="com.example.mapper.EreplyMapper";
	
	@Override
	public List<EreplyVO> list(int page, int num,int ecode) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("start", (page - 1) * num);
		map.put("num", num);
		map.put("ecode",ecode);
		return session.selectList(namespace + ".list", map);
	}

	@Override
	public void update(EreplyVO vo) {
		session.update(namespace+".update",vo);
		
	}

	@Override
	public void insert(EreplyVO vo) {
		session.insert(namespace+".insert",vo);
		
	}

	@Override
	public void delete(int ercode) {
		session.update(namespace+".delete",ercode);
	}

	@Override
	public int getTotal(int ecode) {
		return session.selectOne(namespace+".getTotal",ecode);
		
	}

	@Override
	public void adminDelete(int ercode) {
		session.delete(namespace+".adminDelete",ercode);
		
	}

	@Override
	public void allDelete(int ecode) {
		session.delete(namespace+".allDelete",ecode);
		
	}

	@Override
	public void updateUserCondition(int ercode) {
		session.update(namespace + ".updateUserCondition", ercode);
	}

	@Override
	public void updateAdminCondition(int ercode) {
		session.update(namespace + ".updateAdminCondition", ercode);
	}

	
}
