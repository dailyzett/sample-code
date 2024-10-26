package com.cen.park.service;

import java.util.List;

import com.cen.park.vo.QuestionBoardVo;
import com.cen.park.vo.ReplyVo;

public interface QuestionBoardService {
	public List<QuestionBoardVo> questionBoardListView(int startRow, int maxContent);
	public int getCount();
	public void boardWrite(QuestionBoardVo vo);
	public QuestionBoardVo boardDetailView(int qb_id);
	public int getStatus(int qb_id);
	public void deleteBoard(int qb_id);
	
	public void update(QuestionBoardVo vo);
	public void addReply(ReplyVo vo);
	
	public void changeStatus(int qb_id);
	
	public ReplyVo findReplyViewByQId(int qb_id);
	public int getCountTitle(String qb_title);
	public int getCountWriter(String writer_name);
	public List<QuestionBoardVo> searchTitle(String title, String park_name, int seq, int rownum);
	public List<QuestionBoardVo> searchWriter(String writer_name, String park_name, int seq, int rownum);
}
