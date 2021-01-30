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
	
	
	public ArrayList<ReservationDto> getDates(Date start, Date end) {
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		ArrayList<ReservationDto> arr = new ArrayList<>();
		

		try {
			String query = "select * from kaya_park where reserve_date BETWEEN ? and ?";
			connection = getConnection();
			pstmt = connection.prepareStatement(query);
			pstmt.setDate(1, start);
			pstmt.setDate(2, end);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				ReservationDto tempDate = new ReservationDto();
				tempDate.setReserveDate(rs.getDate("reserve_date"));
				tempDate.setReserveEnable(rs.getInt("reserve_enable"));
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
