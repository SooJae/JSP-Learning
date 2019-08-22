package chat;

import java.io.IOException;
import java.net.URLDecoder;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/*@WebServlet("/ChatSubmitServlet")*/
public class ChatSubmitServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
  
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		String chatName = URLDecoder.decode(request.getParameter("chatName"), "UTF-8");
		String chatContent = URLDecoder.decode(request.getParameter("chatContent"), "UTF-8");
		if(chatName==null || chatName.equals("")||chatContent==null||chatContent.equals("")) {
//			오류가 발생한 경우 0을 반환한다.
			response.getWriter().write("0");
		} 
		else {
//			성공할 경우 1을 반환한다.
			response.getWriter().write(new ChatDAO().submit(chatName, chatContent)+"");
		}
			
		
	}

}
