package user;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserDAO {

	private Connection conn;
	
	public UserDAO() {
		try {
			String dbURL ="jdbc:mysql://localhost:3306/REGISTER?serverTimezone=UTC";
			String dbID ="root";
			String dbPassword="root";
			Class.forName("com.mysql.jdbc.Driver");
			conn=DriverManager.getConnection(dbURL, dbID, dbPassword);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	public int registerCheck(String userID) {
		PreparedStatement pstmt = null;
		ResultSet rs =null;
		String SQL = "SELECT * FROM USER WHERE userID= ?";
		try {
			pstmt= conn.prepareStatement(SQL);
			pstmt.setString(1, userID);
			rs=pstmt.executeQuery();
			if(rs.next()|| userID.equals("")) {
				return 0;// 이미 존재하는 회원, 사용자가 입력한 ID값이 비어있을때 오류
			}
			else {
				return 1; // 가입 가능한 회원
			}
		}catch(Exception e) {
			e.printStackTrace();;
		} finally {
			try {
				if(rs !=null) rs.close();
				if(pstmt !=null) pstmt.close();
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		return -1; //데이터베이스 오류
	}
	
	public int register(String userID,String userPassword,String userName, String userAge, String userGender,String userEmail) {
		PreparedStatement pstmt = null;
		ResultSet rs =null;
		String SQL = "INSERT INTO USER VALUES(?,?,?,?,?,?)";
		try {
			pstmt= conn.prepareStatement(SQL);
			pstmt.setString(1, userID);
			pstmt.setString(2, userPassword);
			pstmt.setString(3, userName);
			pstmt.setInt(4, Integer.parseInt(userAge));
			pstmt.setString(5, userGender);
			pstmt.setString(6, userEmail);
			
			
//			성공했다면 1이 뜬다.(하나의 회원) 실패했다면 0이하의 숫자가 출력된다.
			return pstmt.executeUpdate();
			
		
		}catch(Exception e) {
			e.printStackTrace();;
		} finally {
			try {
				if(rs !=null) rs.close();
				if(pstmt !=null) pstmt.close();
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		return -1; //데이터베이스 오류
	}
}
