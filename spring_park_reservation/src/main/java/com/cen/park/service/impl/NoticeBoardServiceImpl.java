package com.cen.park.service.impl;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.cen.park.dao.NoticeBoardDao;
import com.cen.park.service.NoticeBoardService;
import com.cen.park.vo.NoticeBoardVo;

import lombok.RequiredArgsConstructor;

@Service("noticeBoardService")
@RequiredArgsConstructor
class NoticeBoardServiceImpl implements NoticeBoardService{
	
	private final NoticeBoardDao noticeBoardDao;
	
	@Override
	public void write(NoticeBoardVo vo, Model model) {
		noticeBoardDao.insert(vo);
		model.addAttribute("msg", "공지가 등록되었습니다");
		model.addAttribute("url", "notice");
	}
	
	@Override
	public void findAll(Model model) {
		Map<String, Object> asMap = model.asMap();
		HttpServletRequest request = (HttpServletRequest) asMap.get("request");
		
		int pageSize = 15;
		String pageNum = request.getParameter("pageNum");
		if (pageNum == null) {
			pageNum = "1";
		}
		int currentPage = Integer.parseInt(pageNum);
		int startRow = (currentPage - 1) * pageSize + 1;
		int count = noticeBoardDao.getCountAll();
		List<NoticeBoardVo> vo = null;

		if (count > 0) {
			vo = noticeBoardDao.findAllList(startRow, pageSize);
		}

		request.setAttribute("noticeVo", vo);
		request.setAttribute("count", count);
		request.setAttribute("currentPage", currentPage);
		request.setAttribute("pageSize", pageSize);
	}
	
	@Override
	public void detailView(int nid, Model model) {
		NoticeBoardVo vo = noticeBoardDao.findOneById(nid);
		noticeBoardDao.addHit(nid);
		model.addAttribute("vo", vo);
	}
	
	@Override
	public void updateBoard(int id, String title, String content, Model model) {
		noticeBoardDao.updateBoard(id, title, content);
		model.addAttribute("msg", "수정이 완료되었습니다");
		model.addAttribute("url", "noticeDetail?nid="+id);
	}
	
	@Override
	public void deleteBoard(int id) {
		noticeBoardDao.deleteBoard(id);
	}
	
	@Override
	public void findSearch(Model model, String title, String parkName) {
		Map<String, Object> asMap = model.asMap();
		HttpServletRequest request = (HttpServletRequest) asMap.get("request");
		
		int pageSize = 15;
		String pageNum = request.getParameter("pageNum");
		if (pageNum == null) {
			pageNum = "1";
		}
		int currentPage = Integer.parseInt(pageNum);
		int startRow = (currentPage - 1) * pageSize + 1;
		int count = noticeBoardDao.getCountAll();
		List<NoticeBoardVo> vo = null;

		if (count > 0) {
			vo = noticeBoardDao.findSearch(title, parkName, startRow, pageSize);
		}

		request.setAttribute("noticeVo", vo);
		request.setAttribute("count", count);
		request.setAttribute("currentPage", currentPage);
		request.setAttribute("pageSize", pageSize);
	}
}
