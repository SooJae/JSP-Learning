package chat;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/ChatSubmitServlet")
public class ChatListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		String listType = request.getParameter("listType");
		if (listType == null || listType.equals(""))
			response.getWriter().write("");
		else if (listType.equals("today"))
			response.getWriter().write(getToday());
		else if (listType.equals("ten"))
			response.getWriter().write(getTen());
		else {
			try {
//				listType이 int면 실행되고 int형이 아니면 에러가 뜨면서 공백문자가 나온다.
				Integer.parseInt(listType);
				response.getWriter().write(getID(listType));
			}catch(Exception e) {
				response.getWriter().write("");
			}
		}
	}

//	데이터베이스의 값을 가져와서 사용자한테 돌려줄때, JSON을 사용한다.
	public String getToday() {
		StringBuffer result = new StringBuffer("");
//		변수들의 이름을 지정하고 클라이언트에 보내면 클라이언트에서 파싱하여 화면에 출력해준다.
		result.append("{\"result\":[");
		ChatDAO chatDAO = new ChatDAO();
		ArrayList<Chat> chatList = chatDAO.getChatList(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
		for(int i=0; i<chatList.size();i++) {
			result.append("[{\"value\":\""+chatList.get(i).getChatName()+"\"},");
			result.append("{\"value\":\""+chatList.get(i).getChatContent()+"\"},");
//			마지막은 뒤에 , 찍는 대신에 i가 마지막 인덱스가 아닐때 ,를 찍는다. result.append(",");로 해준다.
			result.append("{\"value\":\""+chatList.get(i).getChatTime()+"\"}]");
			if(i != chatList.size() - 1) result.append(",");
		}
		result.append("], \"last\":\""+ chatList.get(chatList.size() -1 ).getChatID()+"\"}");
		return result.toString();
	}
	
	public String getTen() {
		StringBuffer result = new StringBuffer("");
//		변수들의 이름을 지정하고 클라이언트에 보내면 클라이언트에서 파싱하여 화면에 출력해준다.
		result.append("{\"result\":[");
		ChatDAO chatDAO = new ChatDAO();
		ArrayList<Chat> chatList = chatDAO.getChatListByRecent(10);
		for(int i=0; i<chatList.size();i++) {
			result.append("[{\"value\":\""+chatList.get(i).getChatName()+"\"},");
			result.append("{\"value\":\""+chatList.get(i).getChatContent()+"\"},");
//			마지막은 뒤에 , 찍는 대신에 i가 마지막 인덱스가 아닐때 ,를 찍는다. result.append(",");로 해준다.
			result.append("{\"value\":\""+chatList.get(i).getChatTime()+"\"}]");
			if(i != chatList.size() - 1) result.append(",");
		}

		result.append("], \"last\":\""+ chatList.get(chatList.size() -1 ).getChatID()+"\"}");
		return result.toString();
	}
	
	public String getID(String chatID) {
		StringBuffer result = new StringBuffer("");
//		변수들의 이름을 지정하고 클라이언트에 보내면 클라이언트에서 파싱하여 화면에 출력해준다.
		result.append("{\"result\":[");
		ChatDAO chatDAO = new ChatDAO();
		ArrayList<Chat> chatList = chatDAO.getChatListByRecent(chatID);
		for(int i=0; i<chatList.size();i++) {
			result.append("[{\"value\":\""+chatList.get(i).getChatName()+"\"},");
			result.append("{\"value\":\""+chatList.get(i).getChatContent()+"\"},");
//			마지막은 뒤에 , 찍는 대신에 i가 마지막 인덱스가 아닐때 ,를 찍는다. result.append(",");로 해준다.
			result.append("{\"value\":\""+chatList.get(i).getChatTime()+"\"}]");
			if(i != chatList.size() - 1) result.append(",");
		}

		result.append("], \"last\":\""+ chatList.get(chatList.size() -1 ).getChatID()+"\"}");
		return result.toString();
	}
		
}
