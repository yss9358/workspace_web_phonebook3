package com.javaex.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.javaex.vo.PersonVo;

public class PhoneDao {

	// 필드
	private Connection conn = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs = null;
	private String driver = "com.mysql.cj.jdbc.Driver";
	private String url = "jdbc:mysql://localhost:3306/phone_db";
	private String id = "phone";
	private String pw = "phone";

	// 생성자

	// 메소드 g/s

	// 메소드 일반

	protected void getConnection() {
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url, id, pw);
		} catch(ClassNotFoundException e) {
			System.out.println("error: 드라이버 로딩 실패 - " + e);
		} catch(SQLException e) {
			System.out.println("error:" + e);
		}
	} // getConnection();

	protected void close() {
		try {
			if (rs != null) {
				rs.close();
			}
			if (pstmt != null) {
				pstmt.close();
			}
			if (conn != null) {
				conn.close();
			}
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
	} // close();
	
	public List<PersonVo> personSelect() {
		this.getConnection();
		List<PersonVo> personList = new ArrayList<PersonVo>();
		try {
			//sql문 준비
			String query = "";
			query +=" select	person_id, ";
			query +=" 			name, ";
			query +="  			hp, ";
			query +=" 			company ";
			query +=" from person ";
			//바인딩
			pstmt =conn.prepareStatement(query);
			//실행
			rs = pstmt.executeQuery();
			//결과처리
			while(rs.next()) {
				int personId = rs.getInt("person_id");
				String name = rs.getString("name");
				String hp = rs.getString("hp");
				String company = rs.getString("company");
				
				PersonVo personVo = new PersonVo(personId, name, hp, company);
				// 리스트에 주소 추가
				personList.add(personVo);
			}
		} catch(SQLException e) {
			System.out.println("error:" + e );
		}
		this.close();
		return personList;		
	} // personSelect
	
	public void personInsert(PersonVo personVo) {
		this.getConnection();
		try {
			String query = "";
			query += " insert into person ";
			query += " value(null, ?, ?, ?) ";

			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, personVo.getName());
			pstmt.setString(2, personVo.getHp());
			pstmt.setString(3, personVo.getCompany());

			pstmt.executeUpdate();

		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
		this.close();
	} // personInsert 
	
	public void personDelete(int no){
		this.getConnection();
		try {
			String query = "";
			query +=" delete from person ";
			query +=" where person_id = ? ";
			
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, no);
			pstmt.executeUpdate();
			
		}catch(SQLException e) {
			System.out.println("error:" + e);
		}
		this.close();
	} // personDelete()

	public PersonVo selectOne(int no) {
		this.getConnection();
		PersonVo personVo =null;
		try {
			String query = "";
			query += " select	person_id, ";
			query += " 			name, ";
			query += "  		hp, ";
			query += "  		company ";
			query += " from person ";
			query += " where person_id = ? ";
			
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, no);			
			rs = pstmt.executeQuery();
			
			while(rs.next()){
				int id = rs.getInt("person_id");
				String name = rs.getString("name");
				String hp = rs.getString("hp");
				String company = rs.getString("company");
				
				personVo = new PersonVo(id, name, hp, company);
			}
			
		}catch(SQLException e) {
			System.out.println("error:" + e);
		}
		this.close();
		return personVo;
	}
	
	public void personUpdate(PersonVo personVo) {
		this.getConnection();
		try {
			String query = "";
			query += " update person ";
			query += " set name = ?, ";
			query += " 	   hp = ?, ";
			query += "     company = ? ";
			query += " where person_id = ? ";
			
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, personVo.getName());
			pstmt.setString(2, personVo.getHp());
			pstmt.setString(3, personVo.getCompany());
			pstmt.setInt(4, personVo.getPersonId());
			pstmt.executeUpdate();
			
			
		}catch(SQLException e) {
			System.out.println("error:" + e);
		}
		this.close();
		
	}
	
	
}//phoneDao
