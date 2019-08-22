package bbs;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;



public class BbsDAO {
	
		private Connection conn;
		private ResultSet rs;
		
		public BbsDAO() {
			try {
//				?serverTimezone=UTC �� ���� �߰���.
				String dbURL="jdbc:mysql://localhost:3306/BBS?serverTimezone=UTC";
				
				String dbID="root";
				String dbPassword="root";
				Class.forName("com.mysql.jdbc.Driver");
				conn = DriverManager.getConnection(dbURL, dbID, dbPassword);
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
		public String getDate() {
			String SQL ="SELECT NOW()";
			try {
				PreparedStatement pstmt = conn.prepareStatement(SQL);
				rs = pstmt.executeQuery();
				if(rs.next()) {
					return rs.getString(1); //��¥�� �����
				}
			}catch(Exception e) {
				e.printStackTrace();
			}
			return ""; //�����ͺ��̽� ����
		}
		
		
		public int getNext() {
			String SQL ="SELECT bbsID FROM BBS ORDER BY bbsID DESC";
			try {
				PreparedStatement pstmt = conn.prepareStatement(SQL);
				rs = pstmt.executeQuery();
				if(rs.next()) {
					return rs.getInt(1) +1; //�Խñ��� �ϳ� �̾Ƴ��� �� ������ �Խñ��� �����
				}
				return 1; //ù��° �Խù��� ���
			}catch(Exception e) {
				e.printStackTrace();
			}
			return -1; //�����ͺ��̽� ����
		}
		
		public int write(String bbsTitle, String userID, String bbsContent) {
			String SQL ="insert into bbs values(?,?,?,?,?,?)";
			try {
				PreparedStatement pstmt = conn.prepareStatement(SQL);
				pstmt.setInt(1, getNext());
				pstmt.setString(2, bbsTitle);
				pstmt.setString(3, userID);
				pstmt.setString(4, getDate());
				pstmt.setString(5, bbsContent);
				pstmt.setInt(6, 1);	//���� �ִٴ� �濡�� 1
				
				return pstmt.executeUpdate();
				
			}catch(Exception e) {
				e.printStackTrace();
			}
			return -1; //�����ͺ��̽� ����
		}
		
		public ArrayList<Bbs> getList(int pageNumber){
			String SQL = "SELECT * FROM BBS WHERE bbsID<? AND bbsAvailable = 1 ORDER BY bbsID DESC LIMIT 10";
			ArrayList<Bbs> list = new ArrayList<Bbs>();
			try {
				PreparedStatement pstmt = conn.prepareStatement(SQL);
				//5���ΰ�� getNext() �� 6�� �ȴ�. pageNumber�� 1�� �ǰ�, �� ������� 6�̵ȴ�. bbsID<6�̱� ������ 5���� ���� ���´�.
				pstmt.setInt(1, getNext()-(pageNumber -1)*10);
				rs = pstmt.executeQuery();
				while(rs.next()) {
					Bbs bbs =new Bbs();
					bbs.setBbsID(rs.getInt(1));
					bbs.setBbsTitle(rs.getString(2));
					bbs.setUserID(rs.getString(3));
					bbs.setBbsDate(rs.getString(4));
					bbs.setBbsContent(rs.getString(5));
					bbs.setBbsAvailable(rs.getInt(6));
					list.add(bbs);
				}
			}catch(Exception e) {
				e.printStackTrace();
			}
			return list;
		}
//		����¡ ó���� ���� �Լ�
		public boolean nextPage(int pageNumber) {
			String SQL = "SELECT * FROM BBS WHERE bbsID<? AND bbsAvailable = 1 ORDER BY bbsID DESC LIMIT 10";
			try {
				PreparedStatement pstmt = conn.prepareStatement(SQL);
				//5���ΰ�� getNext() �� 6�� �ȴ�. pageNumber�� 1�� �ǰ�, �� ������� 6�̵ȴ�. bbsID<6�̱� ������ 5���� ���� ���´�.
				pstmt.setInt(1, getNext()-(pageNumber -1)*10);
				rs = pstmt.executeQuery();
				if(rs.next()) {
					return true;
				}
			}catch(Exception e) {
				e.printStackTrace();
			}
			return false;
		}
		
		public Bbs getBbs(int bbsID) {
			String SQL ="select * from bbs where bbsID=?";
			try {
				PreparedStatement pstmt = conn.prepareStatement(SQL);
				pstmt.setInt(1, bbsID);
				rs=pstmt.executeQuery();
				if(rs.next()) {
					Bbs bbs = new Bbs();
					bbs.setBbsID(rs.getInt(1));
					bbs.setBbsTitle(rs.getString(2));
					bbs.setUserID(rs.getString(3));
					bbs.setBbsDate(rs.getString(4));
					bbs.setBbsContent(rs.getString(5));
					bbs.setBbsAvailable(rs.getInt(6));
					return bbs;
				}
			}catch(Exception e) {
				e.printStackTrace();
			}
			return null;
		}
		
		public int update(int bbsID, String bbsTitle, String bbsContent) {
			String SQL ="UPDATE bbs SET bbsTitle=?, bbsContent=? WHERE bbsID=?";
			try {
				PreparedStatement pstmt = conn.prepareStatement(SQL);
				pstmt.setString(1, bbsTitle);
				pstmt.setString(2, bbsContent);
				pstmt.setInt(3, bbsID);
				return pstmt.executeUpdate();
			}catch(Exception e) {
				e.printStackTrace();
			}
			return -1; //�����ͺ��̽� ����
		}
		
		public int delete(int bbsID) {
			String SQL ="UPDATE bbs SET bbsAvailable=0 WHERE bbsID=?";
			try {
				PreparedStatement pstmt = conn.prepareStatement(SQL);
				pstmt.setInt(1, bbsID);
				return pstmt.executeUpdate();
			}catch(Exception e) {
				e.printStackTrace();
			}
			return -1; //�����ͺ��̽� ����
		}
}
