package com.cen.park.dao.impl;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.cen.park.dao.QuestionBoardDao;
import com.cen.park.vo.QuestionBoardVo;
import com.cen.park.vo.ReplyVo;

import lombok.RequiredArgsConstructor;

@Repository("questionBoardDao")
@RequiredArgsConstructor
public class QuestionBoardDaoImpl implements QuestionBoardDao{

	private final SqlSession session;
	
	@Override
	public int questionBoardCount() {
		return session.selectOne("questionBoardNS.selectBoardCount");
	}

	@Override
	public List<QuestionBoardVo> findAllQuestionBoardPaging(int startRow, int maxContent) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("seq", startRow);
		map.put("rownum", maxContent);
		return session.selectList("questionBoardNS.selectAllBoardPaging", map);
	}

	@Override
	public void insertQuestionBoard(QuestionBoardVo questionBoardVo) {
		session.update("questionBoardNS.insertBoard", questionBoardVo);
	}

	@Override
	public void updateHit(int qb_id) {
		session.update("questionBoardNS.updateHit", qb_id);
	}

	@Override
	public QuestionBoardVo findDetailById(int qb_id) {
		return session.selectOne("questionBoardNS.selectAllById", qb_id);
	}

	@Override
	public int findStatusById(int qb_id) {
		return session.selectOne("questionBoardNS.selectStatusById", qb_id);
	}

	@Override
	public void deleteBoard(int qb_id) {
		session.delete("questionBoardNS.deleteBoard", qb_id);
	}

	@Override
	public void updateboard(QuestionBoardVo vo) {
		session.update("questionBoardNS.updateBoard", vo);
	}

	@Override
	public void insertReplyBoard(ReplyVo vo) {
		session.insert("questionBoardNS.insertReplyBoard", vo);
	}

	@Override
	public void updateStatus(int qb_id) {
		session.update("questionBoardNS.updateBoardStatus", qb_id);
	}

	@Override
	public ReplyVo findReplyByQId(int qb_id) {
		return session.selectOne("questionBoardNS.selectReplyById", qb_id);
	}
	
	@Override
	public List<QuestionBoardVo> findWriterSearchQuestionBoard(String writerName, String parkName, int seq, int rownum) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("writer_name", writerName);
		map.put("park_name", parkName);
		map.put("seq", seq);
		map.put("rownum", rownum);
		return session.selectList("questionBoardNS.selectWriterSearchBoardPaging", map);
	}
	
	@Override
	public List<QuestionBoardVo> findTitleSearchQuestionBoard(String title, String parkName, int seq, int rownum) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("qb_title", title);
		map.put("park_name", parkName);
		map.put("seq", seq);
		map.put("rownum", rownum);
		return session.selectList("questionBoardNS.selectTitleSearchBoardPaging", map);
	}
	
	@Override
	public int findCountBySearchTitle(String qb_title) {
		return session.selectOne("questionBoardNS.selectTitleSearchCount", qb_title);
	}
	
	@Override
	public int findCountBySearchWriter(String writer_name) {
		return session.selectOne("questionBoardNS.selectWriterSearchCount", writer_name);
	}
	
	
}
