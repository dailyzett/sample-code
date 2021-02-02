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

	
	
	public ArrayList<MemberReservationDto> findByMemberId(int mId) {
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		
		ArrayList<MemberReservationDto> dtos = new ArrayList<MemberReservationDto>();
		
		try {
			String query = "select * from reservation where m_id_fk=? order by reservation_date desc";
			connection = getConnection();
			pstmt = connection.prepareStatement(query);
			pstmt.setInt(1, mId);
			
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
