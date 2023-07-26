package com.cen.park.service;

import org.springframework.ui.Model;

import com.cen.park.vo.NoticeBoardVo;

public interface NoticeBoardService {

	public void write(NoticeBoardVo vo, Model model);

	public void findAll(Model model);

	public void detailView(int nid, Model model);

	public void updateBoard(int id, String title, String content, Model model);

	public void deleteBoard(int id);

	public void findSearch(Model model, String title, String parkName);
	

}
