package com.common.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import com.common.dto.MemberReservationDto;

public class MemberReservationDao {
	
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
	
	public int getMemberFkId(int rId) {
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		int mFkId = 0;
		
		try {
			String query = "select m_id_fk from reservation where r_id = ?";
			connection = getConnection();
			pstmt = connection.prepareStatement(query);
			pstmt.setInt(1, rId);
			rs = pstmt.executeQuery();
			
			
			if(rs.next()) {
				mFkId = rs.getInt("m_id_fk");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				rs.close();
				pstmt.close();
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return mFkId;
	}
	
	public int getReservationCount(int rId) {
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		int count = 0;
		
		try {
			String query = "select count from reservation where r_id = ?";
			connection = getConnection();
			pstmt = connection.prepareStatement(query);
			pstmt.setInt(1, rId);
			rs = pstmt.executeQuery();
			
			
			if(rs.next()) {
				count = rs.getInt("count");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				rs.close();
				pstmt.close();
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return count;
	}
	
	public void deleteReservation(int rId) {
		Connection connection = null;
		PreparedStatement pstmt = null;
		
		try {
			String query = "delete from reservation where r_id = ?";
			connection = getConnection();
			pstmt = connection.prepareStatement(query);
			pstmt.setInt(1, rId);
			pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				pstmt.close();
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public int findByRId(int rId) {
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		
		int res = 0;
		
		try {
			String query = "select m_id_fk from reservation where r_id = ?";
			connection = getConnection();
			pstmt = connection.prepareStatement(query);
			pstmt.setInt(1, rId);
			
			rs = pstmt.executeQuery();
			if(rs.next()) {
				res = rs.getInt("m_id_fk");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				rs.close();
				pstmt.close();
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return res;
	}
	
	public MemberReservationDto findByOneMemberId(int mId, int rId) {
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		MemberReservationDto dto = new MemberReservationDto();
				
		try {
			String query = "select * from reservation where m_id_fk=? and r_id = ? order by reservation_date desc";
			connection = getConnection();
			pstmt = connection.prepareStatement(query);
			pstmt.setInt(1, mId);
			pstmt.setInt(2, rId);
			
			rs = pstmt.executeQuery();
			while(rs.next()) {
				
				
				dto.setrId(rs.getInt("r_id"));
				dto.setmIdFk(rs.getInt("m_id_fk"));
				dto.setReservationDate(rs.getDate("reservation_date"));
				dto.setStatus(rs.getInt("status"));
				dto.setCount(rs.getInt("count"));
				dto.setPrice(rs.getInt("price"));
				dto.setParkName(rs.getString("park_name"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				rs.close();
				pstmt.close();
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return dto;
		
	}
	
	
	public int getCount(int mId) {
		int count = 0;
		
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String query = "select count(*) from reservation where m_id_fk = ?";
		
		try {
			connection = getConnection();
			pstmt = connection.prepareStatement(query);
			pstmt.setInt(1, mId);
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
	
	public int getCountByRId(int rId) {
		int count = 0;
		
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String query = "select count(*) from reservation where r_id = ?";
		
		try {
			connection = getConnection();
			pstmt = connection.prepareStatement(query);
			pstmt.setInt(1, rId);
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
	
	public int getCountAll() {
		int count = 0;
		
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String query = "select count(*) from reservation";
		
		try {
			connection = getConnection();
			pstmt = connection.prepareStatement(query);
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

	
	
	public ArrayList<MemberReservationDto> findByMemberId(int mId, int seq, int row) {
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		
		ArrayList<MemberReservationDto> dtos = new ArrayList<MemberReservationDto>();
		String query = "SELECT r_id, m_id_fk, reservation_date, status, count, price, park_name " + 
		"FROM " +
		  "(" +
		  " SELECT SEQ, r_id, m_id_fk, reservation_date, status, count, price, park_name " + 
		  "FROM " +
		  "(" +
		    " SELECT ROWNUM AS SEQ, r_id, m_id_fk, reservation_date, status, count, price, park_name " +
		    "FROM " +
		      "(" +
		        " SELECT * " +
		        "FROM reservation where m_id_fk = ? " +
		        "ORDER BY reservation_date DESC " +
		      ")" +
		  ")" +
		" WHERE SEQ >= ? " +
		")" +
		" WHERE ROWNUM <= ?";
		try {
			
			connection = getConnection();
			pstmt = connection.prepareStatement(query);
			pstmt.setInt(1, mId);
			pstmt.setInt(2, seq);
			pstmt.setInt(3, row);
			
			
			rs = pstmt.executeQuery();
			while(rs.next()) {
				
				MemberReservationDto dto = new MemberReservationDto();
				
				dto.setrId(rs.getInt("r_id"));
				dto.setmIdFk(rs.getInt("m_id_fk"));
				dto.setReservationDate(rs.getDate("reservation_date"));
				dto.setStatus(rs.getInt("status"));
				dto.setCount(rs.getInt("count"));
				dto.setPrice(rs.getInt("price"));
				dto.setParkName(rs.getString("park_name"));
				
				dtos.add(dto);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
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
	
	
	public ArrayList<MemberReservationDto> findByRId(int rId, int seq, int row) {
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		ArrayList<MemberReservationDto> dtos = new ArrayList<MemberReservationDto>();
		String query = "SELECT r_id, m_id_fk, reservation_date, status, count, price, park_name " + 
		"FROM " +
		  "(" +
		  " SELECT SEQ, r_id, m_id_fk, reservation_date, status, count, price, park_name " + 
		  "FROM " +
		  "(" +
		    " SELECT ROWNUM AS SEQ, r_id, m_id_fk, reservation_date, status, count, price, park_name " +
		    "FROM " +
		      "(" +
		        " SELECT * " +
		        "FROM reservation where r_id=? " +
		        "ORDER BY reservation_date DESC " +
		      ")" +
		  ")" +
		" WHERE SEQ >= ? " +
		")" +
		" WHERE ROWNUM <= ?";
		try {
			
			connection = getConnection();
			pstmt = connection.prepareStatement(query);
			pstmt.setInt(1, rId);
			pstmt.setInt(2, seq);
			pstmt.setInt(3, row);
			
			
			rs = pstmt.executeQuery();
			while(rs.next()) {
				
				MemberReservationDto dto = new MemberReservationDto();
				
				dto.setrId(rs.getInt("r_id"));
				dto.setmIdFk(rs.getInt("m_id_fk"));
				dto.setReservationDate(rs.getDate("reservation_date"));
				dto.setStatus(rs.getInt("status"));
				dto.setCount(rs.getInt("count"));
				dto.setPrice(rs.getInt("price"));
				dto.setParkName(rs.getString("park_name"));
				
				dtos.add(dto);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
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
	
	
	public ArrayList<MemberReservationDto> findByAll(int seq, int row) {
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		ArrayList<MemberReservationDto> dtos = new ArrayList<MemberReservationDto>();
		String query = "SELECT r_id, m_id_fk, reservation_date, status, count, price, park_name " + 
		"FROM " +
		  "(" +
		  " SELECT SEQ, r_id, m_id_fk, reservation_date, status, count, price, park_name " + 
		  "FROM " +
		  "(" +
		    " SELECT ROWNUM AS SEQ, r_id, m_id_fk, reservation_date, status, count, price, park_name " +
		    "FROM " +
		      "(" +
		        " SELECT * " +
		        "FROM reservation " +
		        "ORDER BY reservation_date DESC " +
		      ")" +
		  ")" +
		" WHERE SEQ >= ? " +
		")" +
		" WHERE ROWNUM <= ?";
		try {
			
			connection = getConnection();
			pstmt = connection.prepareStatement(query);
			pstmt.setInt(1, seq);
			pstmt.setInt(2, row);
			
			
			rs = pstmt.executeQuery();
			while(rs.next()) {
				
				MemberReservationDto dto = new MemberReservationDto();
				
				dto.setrId(rs.getInt("r_id"));
				dto.setmIdFk(rs.getInt("m_id_fk"));
				dto.setReservationDate(rs.getDate("reservation_date"));
				dto.setStatus(rs.getInt("status"));
				dto.setCount(rs.getInt("count"));
				dto.setPrice(rs.getInt("price"));
				dto.setParkName(rs.getString("park_name"));
				
				dtos.add(dto);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
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
	
	
	
	public void setMemberReservation(int mId, Date rDate, int status, int price, int count, String parkName) {
		Connection connection = null;
		PreparedStatement pstmt = null;
		
		try {
			String query = "insert into reservation (r_id, m_id_fk, reservation_date, status, price, count,park_name) "
					+ "values (reserve_seq.NEXTVAL, ?, ?, ?, ?, ?, ?)";
			
			connection = getConnection();
			pstmt = connection.prepareStatement(query);
			

			
			pstmt.setInt(1, mId);
			pstmt.setDate(2, rDate);
			pstmt.setInt(3, status);
			pstmt.setInt(4, price);
			pstmt.setInt(5, count);
			
			if(parkName.equals("kaya")) {
				pstmt.setString(6, "가야산");
			}else if(parkName.equals("kelong")) {
				pstmt.setString(6, "계룡산");
			}else if(parkName.equals("naejang")) {
				pstmt.setString(6, "내장산");
			}if(parkName.equals("seolak")) {
				pstmt.setString(6, "설악산");
			}
			pstmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				pstmt.close();
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
}
