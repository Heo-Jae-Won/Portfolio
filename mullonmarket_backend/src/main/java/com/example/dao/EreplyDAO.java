package com.example.dao;

import java.util.List;

import com.example.domain.EreplyVO;

public interface EreplyDAO {
	public List<EreplyVO> list(int page, int num,int ecode);
	public void update(EreplyVO vo);
	public void insert(EreplyVO vo);
	public void delete(int ercode);
	public void adminDelete(int ercode);
	public int getTotal(int ecode);
	public void allDelete(int ecode);
	public void updateUserCondition(int ercode);
	public void updateAdminCondition(int ercode);
}
