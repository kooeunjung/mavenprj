package com.newlecture.web.data.mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.newlecture.web.data.dao.MemberDao;
import com.newlecture.web.data.entity.Member;
import com.newlecture.web.data.view.NoticeView;

public class MySQLMemberDao implements MemberDao {

	
	public List<Member> getList(String query) {
		String sql = "SELECT * FROM MEMBER WHERE ID LIKE '%" + query + "%'";
		List<Member> list = new ArrayList();

		try {
			Class.forName("com.mysql.jdbc.Driver");
			
			String url = "jdbc:mysql://211.238.142.84/newlecture?autoReconnect=true&amp;useSSL=false"; // DB�뿰寃�
			Connection con = DriverManager.getConnection(url, "newlec", "sclass"); // �뱶�씪�씠?���? 濡쒕�?
			PreparedStatement st = con.prepareStatement(sql);
			ResultSet rs = st.executeQuery();
			
			Member member = null;

			while (rs.next()) {
				member = new Member();
				member.setId(rs.getString("ID"));
				member.setPwd(rs.getString("PWD"));

				list.add(member);
			}

			rs.close();
			st.close();
			con.close();
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return list;
	}

	@Override
	public int add(Member member) {
		
		String sql = "INSERT INTO MEMBER(MID,PWD,NAME,PHONE,REGDATE) VALUES(?,?,?,?,SYSDATE)"; // Member媛� 媛뽮?? �엳�뒗寃껋?�� ?��귥븘�꽔�뒗 �옉�뾽
		List<Member> list = new ArrayList<>();
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
			String url = "jdbc:oracle:thin:@211.238.142.251:1521:orcl"; 
			Connection con = DriverManager.getConnection(url, "c##sist", "dclass"); 
			// Statement st = con.createStatement(); // ?��귥븘�꽔�뒗 �뒫�젰�� �뾾?�� �떎�뻾留� 媛��뒫
			PreparedStatement st = con.prepareStatement(sql);			
			st.setString(1, member.getId());
			st.setString(2, member.getPwd());
			st.setString(3, member.getName());
			st.setString(4, member.getPhone());

			// 寃곌?��媛� �엳�뒗 ?��?��?�� executeQuery()
			// SELECT
			// 寃곌?��媛� �뾾�뒗 ?��?��?�� executeUpdate()
			// INSERT, UPDATE, DELETE
			int result = st.executeUpdate();
			
			st.close();
			con.close();
			
			return result;
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return 0;
	}

	@Override
	public Member get(String id) {
		String sql = "SELECT * FROM MEMBER WHERE ID = ?";

		Member member = null;
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			
			String url = "jdbc:mysql://211.238.142.84/newlecture?autoReconnect=true&amp;useSSL=false&characterEncoding=UTF-8"; // DB?��?��?��?��?��?��
			Connection con = DriverManager.getConnection(url, "newlec", "sclass"); // ?��?��?��?��?��?��?��?���? ?��?��?��?��
			PreparedStatement st = con.prepareStatement(sql);
			
			st.setString(1,id);
		
			
			ResultSet rs = st.executeQuery();
			
		

			if (rs.next()) {
				
				member = new Member();
				member.setId(rs.getString("ID"));
				member.setPwd(rs.getString("PWD"));

			}

			rs.close();
			st.close();
			con.close();
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return member;
	}

}
