package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import dto.MemberDto;
import external.LoggableStatement;

public class MemberDao {
	
	public static final int MEMBER_NONEXISTENT = 0;
	public static final int MEMBER_EXISTENT = 1;
	public static final int MEMBER_JOIN_FAIL = 0;
	public static final int MEMBER_JOIN_SUCCESS = 1;
	public static final int MEMBER_LOGIN_SUCCESS = 1;
	public static final int MEMBER_LOGIN_PW_NO_GOOD = 0;
	public static final int MEMBER_LOGIN_IS_NOT = -1;
	
	private static MemberDao instance = new MemberDao();

	public static MemberDao getInstance() {
		return instance;
	}
	
	public MemberDao() {
		
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
	
	
	public int loginMember(String id, String pw) {
		
		int r = 0;
		String dbPw;
		
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		String query = "select pw from member where username=?";
		
		try {
			connection = getConnection();
			pstmt = connection.prepareStatement(query);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				dbPw = rs.getString("pw");
				if(dbPw.equals(pw)) {
					r = MemberDao.MEMBER_JOIN_SUCCESS;
				}else {
					r = MemberDao.MEMBER_LOGIN_PW_NO_GOOD;
				}
			}else {
				r = MemberDao.MEMBER_LOGIN_IS_NOT;
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			try {
				rs.close();
				pstmt.close();
				connection.close();
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		return r;
	}
	
	
	public int joinMember(MemberDto dto) {
		
		int r = 0;
		
		Connection connection = null;
		PreparedStatement pstmt = null;
		String query = "insert into member(m_id, username, pw, name, email, phone_1, phone_2, phone_3, regit_date) values "
				+ "(member_seq.nextval, ?, ?, ?, ? ,? ,?, ?, ?)";
		
		try {
			connection = getConnection();
			pstmt = connection.prepareStatement(query);
			pstmt.setString(1, dto.getUsername());
			pstmt.setString(2, dto.getPassword());
			pstmt.setString(3, dto.getName());
			pstmt.setString(4, dto.getEmail());
			pstmt.setString(5, dto.getPhone1());
			pstmt.setString(6, dto.getPhone2());
			pstmt.setString(7, dto.getPhone3());
			pstmt.setTimestamp(8, dto.getRegit_date());

			pstmt.executeUpdate();
			r = MemberDao.MEMBER_JOIN_SUCCESS;
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			try {
				pstmt.close();
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return r;
	}
	
	public int confirmUsername(String username) {
		int r = 0;
		
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String query = "select username from member where username=?";
		
		
		try {
			connection = getConnection();
			pstmt = connection.prepareStatement(query);
			pstmt.setString(1, username);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				r = MEMBER_EXISTENT;
			}else {
				r = MEMBER_NONEXISTENT;
			}
			
		}catch(Exception e) {
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
		return r;
	}
	
	public MemberDto findByMemberId(int id) {
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		MemberDto dto = null;
		
		try {
			String query = "select * from member where m_id=?";
			connection = getConnection();
			pstmt = connection.prepareStatement(query);
			pstmt.setInt(1, id);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				 dto = new MemberDto
						.Builder()
						.username(rs.getString("username"))
						.password(rs.getString("pw"))
						.email(rs.getString("email"))
						.name(rs.getString("name"))
						.phone1(rs.getString("phone_1"))
						.phone2(rs.getString("phone_2"))
						.phone3(rs.getString("phone_3"))
						.build();
			}
		}catch(Exception e) {
			
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
	
	
	public MemberDto listOneMember(String id) {
		
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		MemberDto dto = null;
		
		try {
			String query = "select * from member where username=?";
			connection = getConnection();
			pstmt = connection.prepareStatement(query);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				 dto = new MemberDto
						.Builder()
						.username(rs.getString("username"))
						.password(rs.getString("pw"))
						.email(rs.getString("email"))
						.name(rs.getString("name"))
						.phone1(rs.getString("phone_1"))
						.phone2(rs.getString("phone_2"))
						.phone3(rs.getString("phone_3"))
						.build();
			}
		}catch(Exception e) {
			
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
	
	public ArrayList<MemberDto> listAllMember(int startRow, int maxContent){
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		ArrayList<MemberDto> dtos = new ArrayList<MemberDto>();
		
		String query = "SELECT m_id, username, name, email, phone_1, phone_2, phone_3, regit_date " + 
				"FROM " +
				  "(" +
				  " SELECT SEQ, m_id, username, name, email, phone_1, phone_2, phone_3, regit_date " + 
				  "FROM " +
				  "(" +
				    " SELECT ROWNUM AS SEQ, m_id, username, name, email, phone_1, phone_2, phone_3, regit_date " +
				    "FROM " +
				      "(" +
				        " SELECT * " +
				        "FROM member " +
				        "ORDER BY m_id DESC " +
				      ")" +
				  ")" +
				" WHERE SEQ >= ? " +
				")" +
				" WHERE ROWNUM <= ?";
		
		try {
			connection = getConnection();
			pstmt = connection.prepareStatement(query);
			pstmt.setInt(1, startRow);
			pstmt.setInt(2, maxContent);
			rs = pstmt.executeQuery();
			

			while(rs.next()) {
				int m_id = rs.getInt("m_id");
				String username = rs.getString("username");
				String name = rs.getString("name");
				String email = rs.getString("email");
				String phone1 = rs.getString("phone_1");
				String phone2 = rs.getString("phone_2");
				String phone3 = rs.getString("phone_3");
				Timestamp date = rs.getTimestamp("regit_date");
				MemberDto dto = new MemberDto
						.Builder()
						.m_id(m_id)
						.username(username)
						.name(name)
						.email(email)
						.phone1(phone1)
						.phone2(phone2)
						.phone3(phone3)
						.regit_date2(date)
						.build();
				
				dtos.add(dto);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			try {
				rs.close();
				pstmt.close();
				connection.close();
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		return dtos;
	}
	
	
	public int updateMember(MemberDto dto) {
		int r = 0;
		Connection connection = null;
		PreparedStatement pstmt = null;
		String query = "update member set name=?, email=?, phone_1=?, phone_2=?, phone_3=?, pw=? where username=?";
		
		
		try {
			connection=getConnection();
			pstmt = connection.prepareStatement(query);
			pstmt.setString(1, dto.getName());
			pstmt.setString(2, dto.getEmail());
			pstmt.setString(3, dto.getPhone1());
			pstmt.setString(4, dto.getPhone2());
			pstmt.setString(5, dto.getPhone3());
			pstmt.setString(6, dto.getPassword());
			pstmt.setString(7, dto.getUsername());
			r = pstmt.executeUpdate();
		}catch(Exception e) {
			
		}finally {
			try {
				pstmt.close();
				connection.close();
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		return r;
	}
	
	public int updateAdminMember(MemberDto dto, int m_id) {
		int r = 0;
		Connection connection = null;
		PreparedStatement pstmt = null;
		String query = "update member set username=?, name=?, email=?, phone_1=?, phone_2=?, phone_3=?, pw=? where m_id=?";
		
		
		try {
			connection=getConnection();
			pstmt = connection.prepareStatement(query);
			pstmt.setString(1, dto.getUsername());
			pstmt.setString(2, dto.getName());
			pstmt.setString(3, dto.getEmail());
			pstmt.setString(4, dto.getPhone1());
			pstmt.setString(5, dto.getPhone2());
			pstmt.setString(6, dto.getPhone3());
			pstmt.setString(7, dto.getPassword());
			pstmt.setInt(8, m_id);
			r = pstmt.executeUpdate();
		}catch(Exception e) {
			
		}finally {
			try {
				pstmt.close();
				connection.close();
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		return r;
	}
	
	
	public int getM_id(String username) {
		int id = 0;
		
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String query = "select m_id from member where username=?";
		
		try {
			connection = getConnection();
			pstmt = connection.prepareStatement(query);
			pstmt.setString(1, username);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				id = rs.getInt("m_id");
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
		return id;
	}
	
	public int getCount() {
		int count = 0;
		
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String query = "select count(*) from member";
		
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
	
	public void deleteMember(String id) {
		Connection connection = null;
		PreparedStatement pstmt = null;
		
		try {
			String query = "delete from member where username=?";
			connection = getConnection();
			pstmt = connection.prepareStatement(query);
			pstmt.setString(1, id);
			pstmt.executeUpdate();
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			try {
				pstmt.close();
				connection.close();
			}catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
	}
	
	public int getSearchCount(String name) {
		int count = 0;
		
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String query = "select count(*) from member where UPPER(name) like UPPER('%' || ? || '%')";
		
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
	
	public ArrayList<MemberDto> searchMemberName(int startRow, int maxContent, String search) {
		
		System.out.println("dao에서 " + search);
		
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		ArrayList<MemberDto> dtos = new ArrayList<MemberDto>();
		MemberDto dto = null;
		

		
		String q = "SELECT m_id, username, name, email, phone_1, phone_2, phone_3, regit_date " + 
				"FROM " +
				  "(" +
				  " SELECT SEQ, m_id, username, name, email, phone_1, phone_2, phone_3, regit_date " + 
				  "FROM " +
				  "(" +
				    " SELECT ROWNUM AS SEQ, m_id, username, name, email, phone_1, phone_2, phone_3, regit_date " +
				    "FROM " +
				      "(" +
				        " SELECT * " +
				        "FROM member where UPPER(name) like UPPER('%' || ? || '%')" +
				        "ORDER BY m_id DESC " +
				      ")" +
				  ")" +
				" WHERE SEQ >= ? "+
				")" +
				" WHERE ROWNUM <= ?";
		
		try {
			connection = getConnection();
			pstmt = connection.prepareStatement(q);
			pstmt.setString(1, search);
			pstmt.setInt(2, startRow);
			pstmt.setInt(3, maxContent);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				dto = new MemberDto.Builder()
						.m_id(rs.getInt("m_id"))
						.username(rs.getString("username"))
						.name(rs.getString("name"))
						.email(rs.getString("email"))
						.phone1(rs.getString("phone_1"))
						.phone2(rs.getString("phone_2"))
						.phone3(rs.getString("phone_3"))
						.regit_date2(rs.getTimestamp("regit_date"))
						.build();
				
				dtos.add(dto);
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			try {
				rs.close();
				pstmt.close();
				connection.close();
			}catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
		return dtos;
	}
}

