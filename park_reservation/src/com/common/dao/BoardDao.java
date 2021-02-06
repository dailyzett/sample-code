package com.common.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import com.common.dto.BoardDto;
import com.common.dto.ReplyBoardDto;

public class BoardDao {

	public BoardDao() {

	}

	private Connection getConnection() {
		Context context = null;
		DataSource dataSource = null;
		Connection connection = null;
		try {
			context = new InitialContext();
			dataSource = (DataSource) context.lookup("java:comp/env/jdbc/Oracle11g");
			connection = dataSource.getConnection();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return connection;
	}
	
	public int getSubSearchCount(String name) {
		int count = 0;
		
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String query = "select count(*) from question_board where UPPER(writer_name) like UPPER('%' || ? || '%')";
		
		try {
			connection = getConnection();
			pstmt = connection.prepareStatement(query);
			pstmt.setString(1, name);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				count = rs.getInt(1);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			try {
				rs.close();
				pstmt.close();
				connection.close();
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		return count;
	}
	
	
	public int getTitleSearchCount(String name) {
		int count = 0;
		
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String query = "select count(*) from question_board where UPPER(qb_title) like UPPER('%' || ? || '%')";
		
		try {
			connection = getConnection();
			pstmt = connection.prepareStatement(query);
			pstmt.setString(1, name);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				count = rs.getInt(1);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			try {
				rs.close();
				pstmt.close();
				connection.close();
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		return count;
	}
	
	
	public ArrayList<BoardDto> searchQBoardSub(int startRow, int maxContent, String park_name, String titleName) {
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		ArrayList<BoardDto> dtos = new ArrayList<BoardDto>();

		String sql = "SELECT qb_id, qb_title, qb_date, qb_hit, writer_name, park_name " + "FROM " + "("
				+ " SELECT SEQ, qb_id, qb_title, qb_date, qb_hit, writer_name, park_name " + "FROM " + "("
				+ " SELECT ROWNUM AS SEQ, qb_id, qb_title, qb_date, qb_hit, writer_name, park_name " + "FROM " + "("
				+ " SELECT * " + "FROM question_board where UPPER(writer_name) like UPPER('%' || ? || '%') and park_name = ? " + "ORDER BY qb_id DESC " + ")" + ")" + " WHERE SEQ >= ? " + ")"
				+ " WHERE ROWNUM <= ?";

		try {
//			String query = "select qb_id, qb_title, qb_date, qb_hit, writer_name, park_name from question_board";
			connection = getConnection();
			pstmt = connection.prepareStatement(sql);
			pstmt.setString(1, titleName);
			pstmt.setString(2, park_name);
			pstmt.setInt(3, startRow);
			pstmt.setInt(4, maxContent);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				int id = rs.getInt("qb_id");
				String title = rs.getString("qb_title");
				Timestamp date = rs.getTimestamp("qb_date");
				int hit = rs.getInt("qb_hit");
				String writerName = rs.getString("writer_name");
				String parkName = rs.getString("park_name");
				int status = rs.getInt("status");

				BoardDto dto = new BoardDto();
				dto.setId(id);
				dto.setTitle(title);
				dto.setStatus(status);
				dto.setWriteDate(date);
				dto.setHit(hit);
				dto.setWriterName(writerName);
				dto.setParkName(parkName);

				dto.setStringFormatDate(date);
				dtos.add(dto);
			}
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			try {
				rs.close();
				pstmt.close();
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return dtos;
	}
	
	
	public ArrayList<BoardDto> searchQBoardTitle(int startRow, int maxContent, String park_name, String titleName) {
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		ArrayList<BoardDto> dtos = new ArrayList<BoardDto>();

		String sql = "SELECT qb_id, qb_title, qb_date, qb_hit, writer_name, park_name " + "FROM " + "("
				+ " SELECT SEQ, qb_id, qb_title, qb_date, qb_hit, writer_name, park_name " + "FROM " + "("
				+ " SELECT ROWNUM AS SEQ, qb_id, qb_title, qb_date, qb_hit, writer_name, park_name " + "FROM " + "("
				+ " SELECT * " + "FROM question_board where UPPER(qb_title) like UPPER('%' || ? || '%') and park_name = ? " + "ORDER BY qb_id DESC " + ")" + ")" + " WHERE SEQ >= ? " + ")"
				+ " WHERE ROWNUM <= ?";

		try {
//			String query = "select qb_id, qb_title, qb_date, qb_hit, writer_name, park_name from question_board";
			connection = getConnection();
			pstmt = connection.prepareStatement(sql);
			pstmt.setString(1, titleName);
			pstmt.setString(2, park_name);
			pstmt.setInt(3, startRow);
			pstmt.setInt(4, maxContent);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				int id = rs.getInt("qb_id");
				String title = rs.getString("qb_title");
				Timestamp date = rs.getTimestamp("qb_date");
				int hit = rs.getInt("qb_hit");
				String writerName = rs.getString("writer_name");
				String parkName = rs.getString("park_name");
				int status = rs.getInt("status");

				BoardDto dto = new BoardDto();
				dto.setId(id);
				dto.setStatus(status);
				dto.setTitle(title);
				dto.setWriteDate(date);
				dto.setHit(hit);
				dto.setWriterName(writerName);
				dto.setParkName(parkName);

				dto.setStringFormatDate(date);
				dtos.add(dto);
			}
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			try {
				rs.close();
				pstmt.close();
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return dtos;
	}
	
	
	public void deleteQBoard(int id) {
		Connection connection = null;
		PreparedStatement pstmt = null;
		
		try {
			String query = "delete from question_board where qb_id = ?";
			connection = getConnection();
			pstmt = connection.prepareStatement(query);
			pstmt.setInt(1, id);
			pstmt.executeUpdate();

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			try {
				pstmt.close();
				connection.close();
			} catch (Exception e2) {
				// TODO: handle exception
			}
		}
	}
	
	public void updateQBoard(String title, String content, String parkName, int id) {
		Connection connection = null;
		PreparedStatement pstmt = null;
		
		try {
			String query = "update question_board set qb_title = ?, qb_content = ?, park_name = ? where qb_id = ?";
			connection = getConnection();
			pstmt = connection.prepareStatement(query);
			pstmt.setString(1, title);
			pstmt.setString(2, content);
			pstmt.setString(3, parkName);
			pstmt.setInt(4, id);
			
			pstmt.executeUpdate();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			try {
				pstmt.close();
				connection.close();
			} catch (Exception e2) {
				// TODO: handle exception
			}
		}
	}
	
	public int getStatus(int id) {
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		int res = 0;
		try {
			String query = "select status from question_board where qb_id= ?";
			connection = getConnection();
			pstmt = connection.prepareStatement(query);
			pstmt.setInt(1, id);
			rs = pstmt.executeQuery();
			
			
			if(rs.next()) {
				res = rs.getInt("status");
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				pstmt.close();
				connection.close();
			} catch (Exception e2) {
				// TODO: handle exception
			}
		}
		return res;
	}
	
	public void setStatus(int id) {
		Connection connection = null;
		PreparedStatement pstmt = null;

		try {
			String query = "update question_board set status = 1 where qb_id = ?";
			connection = getConnection();
			pstmt = connection.prepareStatement(query);
			pstmt.setInt(1, id);
			
			pstmt.executeUpdate();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			try {
				pstmt.close();
				connection.close();
			} catch (Exception e2) {
				// TODO: handle exception
			}
		}
	}

	public void reply(int id, String content) {
		setStatus(id);
		
		Connection connection = null;
		PreparedStatement pstmt = null;

		try {
			String query = "insert into re_board values (re_board_seq.NEXTVAL,?,?)";
			connection = getConnection();
			pstmt = connection.prepareStatement(query);
			pstmt.setString(1, content);
			pstmt.setInt(2, id);
			
			pstmt.executeUpdate();
		} catch (Exception e) {
			
		} finally {
			try {
				pstmt.close();
				connection.close();
			} catch (Exception e2) {
			}
		}
	}

	public ReplyBoardDto findByReplyBoardId(int id) {
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		ReplyBoardDto dto = new ReplyBoardDto();
		
		try {
			String query = "select * from re_board where re_fk_id=?";
			connection = getConnection();
			pstmt = connection.prepareStatement(query);
			pstmt.setInt(1, id);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				dto.setId(rs.getInt("re_board_id"));
				dto.setContent(rs.getString("content"));
				dto.setFk_id(rs.getInt("re_fk_id"));
			}
			
		} catch (Exception e) {
			
		} finally {
			try {
				pstmt.close();
				connection.close();
			} catch (Exception e2) {
			}
		}
		return dto;
	}

	public int getCount() {
		int count = 0;

		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String query = "select count(*) from question_board";

		try {
			connection = getConnection();
			pstmt = connection.prepareStatement(query);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				count = rs.getInt(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				pstmt.close();
				connection.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return count;
	}

	public void addHit(int id) {
		Connection connection = null;
		PreparedStatement pstmt = null;

		try {
			String query = "update question_board set qb_hit = qb_hit + 1 where qb_id=?";
			connection = getConnection();

			pstmt = connection.prepareStatement(query);
			pstmt.setInt(1, id);
			pstmt.executeUpdate();
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			try {
				pstmt.close();
				connection.close();
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
	}

	public BoardDto findById(int id) {
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		BoardDto dto = new BoardDto();
		try {
			String query = "select * from question_board where qb_id=?";
			connection = getConnection();
			pstmt = connection.prepareStatement(query);
			pstmt.setInt(1, id);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				dto.setId(id);
				dto.setWriterName(rs.getString("writer_name"));
				dto.setWriterEmail(rs.getString("writer_email"));
				dto.setWriterId(rs.getString("writer_id"));
				dto.setContent(rs.getString("qb_content"));
				dto.setTitle(rs.getString("qb_title"));
				dto.setWriteDate(rs.getTimestamp("qb_date"));

				dto.setStringFormatDate(rs.getTimestamp("qb_date"));
			}
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			try {
				rs.close();
				pstmt.close();
				connection.close();
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		return dto;

	}

	public ArrayList<BoardDto> findAllQBoard(int startRow, int maxContent) {
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		ArrayList<BoardDto> dtos = new ArrayList<BoardDto>();

		String sql = "SELECT qb_id, qb_title, qb_date, qb_hit, writer_name, park_name,status " + "FROM " + "("
				+ " SELECT SEQ, qb_id, qb_title, qb_date, qb_hit, writer_name, park_name, status " + "FROM " + "("
				+ " SELECT ROWNUM AS SEQ, qb_id, qb_title, qb_date, qb_hit, writer_name, park_name, status " + "FROM " + "("
				+ " SELECT * " + "FROM question_board " + "ORDER BY qb_id DESC " + ")" + ")" + " WHERE SEQ >= ? " + ")"
				+ " WHERE ROWNUM <= ?";

		try {
//			String query = "select qb_id, qb_title, qb_date, qb_hit, writer_name, park_name from question_board";
			connection = getConnection();
			pstmt = connection.prepareStatement(sql);
			pstmt.setInt(1, startRow);
			pstmt.setInt(2, maxContent);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				int id = rs.getInt("qb_id");
				String title = rs.getString("qb_title");
				Timestamp date = rs.getTimestamp("qb_date");
				int hit = rs.getInt("qb_hit");
				String writerName = rs.getString("writer_name");
				String parkName = rs.getString("park_name");
				int status = rs.getInt("status");

				BoardDto dto = new BoardDto();
				dto.setId(id);
				dto.setStatus(status);
				dto.setTitle(title);
				dto.setWriteDate(date);
				dto.setHit(hit);
				dto.setWriterName(writerName);
				dto.setParkName(parkName);

				dto.setStringFormatDate(date);
				dtos.add(dto);
			}
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			try {
				rs.close();
				pstmt.close();
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return dtos;
	}

	public void writeBoard(BoardDto boardDto) {
		Connection connection = null;
		PreparedStatement pstmt = null;

		try {
			String query = "insert into question_board(qb_id, qb_fk_id, qb_title, qb_content, qb_date, qb_group, qb_step, qb_indent, qb_hit, writer_id, writer_name, writer_email, park_name, status) values "
					+ "(qboard_seq.nextval, ?, ?, ?, ?, qboard_seq.currval, 0, 0, 0, ?, ?, ?, ?, 0)";
			connection = getConnection();
			pstmt = connection.prepareStatement(query);
			pstmt.setInt(1, boardDto.getMemberFkId());
			pstmt.setString(2, boardDto.getTitle());
			pstmt.setString(3, boardDto.getContent());
			pstmt.setTimestamp(4, boardDto.getWriteDate());
			pstmt.setString(5, boardDto.getWriterId());
			pstmt.setString(6, boardDto.getWriterName());
			pstmt.setString(7, boardDto.getWriterEmail());
			pstmt.setString(8, boardDto.getParkName());
			pstmt.executeUpdate();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			try {
				pstmt.close();
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

}
