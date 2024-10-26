package com.cen.park.dao;

import java.util.List;

import com.cen.park.vo.NoticeBoardVo;

public interface NoticeBoardDao {

	public void insert(NoticeBoardVo vo);

	public List<NoticeBoardVo> findAllList(int seq, int rownum);

	public int getCountAll();

	public NoticeBoardVo findOneById(int nid);

	public void addHit(int id);

	public void updateBoard(int id, String title, String content);

	public void deleteBoard(int id);

	public List<NoticeBoardVo> findSearch(String title, String parkName, int seq, int rownum);

}
