package com.cen.park.dao;

import java.util.List;

import com.cen.park.vo.QuestionBoardVo;
import com.cen.park.vo.ReplyVo;

public interface QuestionBoardDao {
	public int questionBoardCount();
	public List<QuestionBoardVo> findAllQuestionBoardPaging(int startRow, int maxContent);
	
	public void insertQuestionBoard(QuestionBoardVo questionBoardVo);
	
	public void updateHit(int qb_id);
	public QuestionBoardVo findDetailById(int qb_id);
	public int findStatusById(int qb_id);
	
	public void deleteBoard(int qb_id);
	
	public void updateboard(QuestionBoardVo vo);
	
	public void insertReplyBoard(ReplyVo vo);
	
	public void updateStatus(int qb_id);
	
	public ReplyVo findReplyByQId(int qb_id);
	
	List<QuestionBoardVo> findWriterSearchQuestionBoard(String writerName, String parkName, int seq, int rownum);
	List<QuestionBoardVo> findTitleSearchQuestionBoard(String title, String parkName, int seq, int rownum);
	public int findCountBySearchTitle(String qb_title);
	public int findCountBySearchWriter(String writer_name);
	
	
}
