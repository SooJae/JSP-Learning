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
//			The server time zone value '��???��?���� ???����?' is unrecognized or represents more than one time zone. ������ �ڿ� ?serverTimezone=UTC�� �ٿ��ָ� �ȴ�. 
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
//		LIKE: '��'�� �Է��ϴ��� '�̼���'�� ���������ش�.
		 String SQL ="SELECT * from USER WHERE userName LIKE ?";
		 ArrayList<User> userList = new ArrayList<User>();
		 try {
			 pstmt = conn.prepareStatement(SQL);
//			 wildcard Ư���� ���ڸ� �Է��ص� �� ���´�.
			 pstmt.setString(1, "%"+userName+"%");
			 rs=pstmt.executeQuery();
			 while(rs.next()) {
				//rs�� �ش��ϴ� �͵��� �����ͼ� User ��ü�� set���ش�.
				 User user = new User();
				 user.setUserName(rs.getString(1)); //�̸�
				 user.setUserAge(rs.getInt(2)); //����
				 user.setUserGender(rs.getString(3)); //����
				 user.setUserEmail(rs.getString(4)); //�̸���
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
		return -1; //�����ͺ��̽� ����
	}
}
