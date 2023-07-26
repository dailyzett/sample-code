package com.cen.park.dao.impl;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.cen.park.dao.NoticeBoardDao;
import com.cen.park.vo.NoticeBoardVo;

import lombok.RequiredArgsConstructor;

@Repository("noticeBoardDao")
@RequiredArgsConstructor
public class NoticeBoardDaoImpl implements NoticeBoardDao{
	
	private final SqlSession session;
	
	@Override
	public void insert(NoticeBoardVo vo) {
		session.insert("NoticeBoardNs.insertBoard", vo);
	}

	@Override
	public List<NoticeBoardVo> findAllList(int seq, int rownum){
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("seq", seq);
		map.put("rownum", rownum);
		return session.selectList("NoticeBoardNs.selectAll", map);
	}
	
	@Override
	public int getCountAll() {
		return session.selectOne("NoticeBoardNs.selectCount");
	}
	
	@Override
	public NoticeBoardVo findOneById(int id) {
		return session.selectOne("NoticeBoardNs.selectOneById", id);
	}
	
	@Override
	public void addHit(int id) {
		session.update("NoticeBoardNs.updateHit", id);
	}
	
	@Override
	public void updateBoard(int id, String title, String content) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		map.put("title", title);
		map.put("content",content);
		session.update("NoticeBoardNs.updateBoard", map);
	}
	
	@Override
	public void deleteBoard(int id) {
		session.delete("NoticeBoardNs.deleteBoard", id);
	}
	
	@Override
	public List<NoticeBoardVo> findSearch(String title, String parkName, int seq, int rownum){
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("title", title);
		map.put("park_name", parkName);
		map.put("seq", seq);
		map.put("rownum", rownum);
		return session.selectList("NoticeBoardNs.selectSearchTitle", map);
	}
}
