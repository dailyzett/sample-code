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

import com.common.dto.ReservationDto;

public class ReservationDao {
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
	
	public void updateParkInfo(Date currentDate, String parkName, int re_people) {
		Connection connection = null;
		PreparedStatement pstmt = null;
		
		try {
			String query = "update park set enable_people = enable_people - ? where park_name=? and reservation_date = ?";
			connection = getConnection();
			pstmt = connection.prepareStatement(query);
			pstmt.setInt(1, re_people);
			pstmt.setString(2, parkName);
			pstmt.setDate(3, currentDate);
			
			pstmt.executeUpdate();
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		
	}
	
	
	public ArrayList<ReservationDto> getParkInfo(Date start, Date end, String parkName) {
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		ArrayList<ReservationDto> arr = new ArrayList<>();
		

		try {
			String query = "select * from park where reservation_date BETWEEN ? and ? and park_name = ?";
			connection = getConnection();
			pstmt = connection.prepareStatement(query);
			pstmt.setDate(1, start);
			pstmt.setDate(2, end);
			pstmt.setString(3, parkName);
			rs = pstmt.executeQuery();

			while(rs.next()) {
	
				ReservationDto tempDate = new ReservationDto();
				tempDate.setParkName(rs.getString("park_name"));
				tempDate.setEnablePeople(rs.getInt("enable_people"));
				tempDate.setMaxPeople(rs.getInt("max_people"));
				tempDate.setReservationDate(rs.getDate("reservation_date"));
				tempDate.setParkPrice(rs.getInt("park_price"));
				tempDate.setMemberFk(rs.getInt("member_fk"));
				arr.add(tempDate);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				pstmt.close();
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return arr;
	}
}
