package user;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;



public class UserDAO {
	
		private Connection conn;
		private PreparedStatement pstmt;
		private ResultSet rs;
		
		public UserDAO() {
			try {
//				?serverTimezone=UTC 이 새로 추가됨.
				String dbURL="jdbc:mysql://localhost:3306/BBS?serverTimezone=UTC";
				
				String dbID="root";
				String dbPassword="root";
				Class.forName("com.mysql.jdbc.Driver");
				conn = DriverManager.getConnection(dbURL, dbID, dbPassword);
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
		public int login(String userID, String userPassword) {
			String SQL = "SELECT userPassword FROM USER WHERE userID=?";
			try {
				pstmt=conn.prepareStatement(SQL);
				pstmt.setString(1, userID);
				rs=pstmt.executeQuery();
				if(rs.next()) {
					if(rs.getString(1).equals(userPassword)) { //rs.getString(1)은 Select로 찾아본 userPassword값.
						return 1; //로그인 성공
					}
					else
						return 0;//비밀번호 불일치
				}
				return -1; //아이디가 없음
			} catch (Exception e) {
				e.printStackTrace();
			}
			return -2; //데이터베이스 오류
		}
		
		public int join(User user) {
			String SQL = "insert into user values(?,?,?,?,?)";
			try {
				pstmt= conn.prepareStatement(SQL);
//				User user로 넘어온 객체를 하나씩 세팅해준다.
				pstmt.setString(1, user.getUserID());
				pstmt.setString(2, user.getUserPassword());
				pstmt.setString(3, user.getUserName());
				pstmt.setString(4, user.getUserGender());
				pstmt.setString(5, user.getUserEmail());
				return pstmt.executeUpdate();
			} catch(Exception e) {
				e.printStackTrace();
			}
			return -1; //데이터베이스 오류
		}
}
