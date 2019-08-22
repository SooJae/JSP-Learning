package util;

import java.sql.Connection;
import java.sql.DriverManager;

//데이터베이스 접근 함수는 외부 util패키지에 작성해줌으로써 안정적인 "모듈화"를 했다.

public class DatabaseUtil {
	
	public static Connection getConnection() {
		try {
			String dbURL = "jdbc:mysql://localhost:3306/LectureEvaluation?serverTimezone=UTC";
			String dbID ="root";
			String dbPassword="root";
			Class.forName("com.mysql.jdbc.Driver");
			return DriverManager.getConnection(dbURL, dbID, dbPassword);
		} catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
