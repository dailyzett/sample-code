package com.cen.park.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.cen.park.dao.QuestionBoardDao;
import com.cen.park.service.QuestionBoardService;
import com.cen.park.vo.QuestionBoardVo;
import com.cen.park.vo.ReplyVo;

import lombok.RequiredArgsConstructor;

@Service("questionBoardService")
@RequiredArgsConstructor
public class QuestionBoardServiceImpl implements QuestionBoardService{

	private final QuestionBoardDao questionBoardDao;

	@Override
	public List<QuestionBoardVo> questionBoardListView(int startRow, int maxContent) {
		return questionBoardDao.findAllQuestionBoardPaging(startRow, maxContent);
	}

	@Override
	public int getCount() {
		return questionBoardDao.questionBoardCount();
	}

	@Override
	public void boardWrite(QuestionBoardVo vo) {
		questionBoardDao.insertQuestionBoard(vo);
	}

	@Override
	public QuestionBoardVo boardDetailView(int qb_id) {
		questionBoardDao.updateHit(qb_id);
		return questionBoardDao.findDetailById(qb_id);
	}

	@Override
	public int getStatus(int qb_id) {
		return questionBoardDao.findStatusById(qb_id);
	}

	@Override
	public void deleteBoard(int qb_id) {
		questionBoardDao.deleteBoard(qb_id);
	}

	@Override
	public void update(QuestionBoardVo vo) {
		questionBoardDao.updateboard(vo);
	}

	@Override
	public void addReply(ReplyVo vo) {
		changeStatus(vo.getRe_fk_id());
		questionBoardDao.insertReplyBoard(vo);
	}

	@Override
	public void changeStatus(int qb_id) {
		questionBoardDao.updateStatus(qb_id);
	}

	@Override
	public ReplyVo findReplyViewByQId(int qb_id) {
		return questionBoardDao.findReplyByQId(qb_id);
	}
	
	@Override
	public List<QuestionBoardVo> searchTitle(String title, String park_name, int seq, int rownum){
		return questionBoardDao.findTitleSearchQuestionBoard(title, park_name, seq, rownum);
	}
	
	@Override
	public List<QuestionBoardVo> searchWriter(String writer_name, String park_name, int seq, int rownum){
		return questionBoardDao.findWriterSearchQuestionBoard(writer_name, park_name, seq, rownum);
	}
	
	@Override
	public int getCountWriter(String writer_name) {
		return questionBoardDao.findCountBySearchWriter(writer_name);
	}
	
	@Override
	public int getCountTitle(String qb_title) {
		return questionBoardDao.findCountBySearchTitle(qb_title);
	}
}