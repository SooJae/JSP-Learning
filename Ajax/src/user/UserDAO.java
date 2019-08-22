package user;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;


public class UserDAO {
	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet rs;
	
	public UserDAO() {
		try {
//			The server time zone value '´???¹?±¹ ???Ø½?' is unrecognized or represents more than one time zone. 오류는 뒤에 ?serverTimezone=UTC를 붙여주면 된다. 
			String dbURL="jdbc:mysql://localhost:3306/AJAX?serverTimezone=UTC";
			String dbID ="root";
			String dbPassword ="root";
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(dbURL, dbID, dbPassword);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	public ArrayList<User> search(String userName){
//		LIKE: '이'만 입력하더라도 '이수재'가 나오게해준다.
		 String SQL ="SELECT * from USER WHERE userName LIKE ?";
		 ArrayList<User> userList = new ArrayList<User>();
		 try {
			 pstmt = conn.prepareStatement(SQL);
//			 wildcard 특정한 문자만 입력해도 다 나온다.
			 pstmt.setString(1, "%"+userName+"%");
			 rs=pstmt.executeQuery();
			 while(rs.next()) {
				//rs에 해당하는 것들을 가져와서 User 객체에 set해준다.
				 User user = new User();
				 user.setUserName(rs.getString(1)); //이름
				 user.setUserAge(rs.getInt(2)); //나이
				 user.setUserGender(rs.getString(3)); //성별
				 user.setUserEmail(rs.getString(4)); //이메일
				 userList.add(user);
			 }
		 }catch(Exception e) {
			 e.printStackTrace();
		 }
		 return userList;
	}
	public int register(User user) {
		String SQL="INSERT INTO USER VALUES(?,?,?,?)";
		try {
			pstmt=conn.prepareStatement(SQL);
			pstmt.setString(1, user.getUserName());
			pstmt.setInt(2, user.getUserAge());
			pstmt.setString(3, user.getUserGender());
			pstmt.setString(4, user.getUserEmail());
			return pstmt.executeUpdate();
		}catch(Exception e) {
			e.printStackTrace();
		}
		return -1; //데이터베이스 오류
	}
}
