package user;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class UserSearchServlet
 */
@WebServlet("/UserSearchServlet")
public class UserSearchServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		String userName = request.getParameter("userName");
		response.getWriter().write(getJSON(userName));
	}
// 파싱하기 쉬운 하나의 형태, 우리가 특정회원을 검색했을때, JSON형태가 나오는데 그것을 다시 파싱을 한 뒤에 우리에게 보여줌.
	//Index->요청->JSON->응답
	public String getJSON(String userName) {
		if(userName==null) userName="";
		StringBuffer result = new StringBuffer("");
		result.append("{\"result\":[");
		UserDAO userDAO = new UserDAO();
		ArrayList<User> userList = userDAO.search(userName);
		for(int i=0; i<userList.size();i++) {
			result.append("[{\"value\":\""+ userList.get(i).getUserName()+"\"},");
			result.append("{\"value\":\""+ userList.get(i).getUserAge()+"\"},");
			result.append("{\"value\":\""+ userList.get(i).getUserGender()+"\"},");
			result.append("{\"value\":\""+ userList.get(i).getUserEmail()+"\"}],");
		}
		result.append("]}");
		return result.toString();
	}
}
