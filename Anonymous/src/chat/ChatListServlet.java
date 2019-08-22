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
//				listType�� int�� ����ǰ� int���� �ƴϸ� ������ �߸鼭 ���鹮�ڰ� ���´�.
				Integer.parseInt(listType);
				response.getWriter().write(getID(listType));
			}catch(Exception e) {
				response.getWriter().write("");
			}
		}
	}

//	�����ͺ��̽��� ���� �����ͼ� ��������� �����ٶ�, JSON�� ����Ѵ�.
	public String getToday() {
		StringBuffer result = new StringBuffer("");
//		�������� �̸��� �����ϰ� Ŭ���̾�Ʈ�� ������ Ŭ���̾�Ʈ���� �Ľ��Ͽ� ȭ�鿡 ������ش�.
		result.append("{\"result\":[");
		ChatDAO chatDAO = new ChatDAO();
		ArrayList<Chat> chatList = chatDAO.getChatList(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
		for(int i=0; i<chatList.size();i++) {
			result.append("[{\"value\":\""+chatList.get(i).getChatName()+"\"},");
			result.append("{\"value\":\""+chatList.get(i).getChatContent()+"\"},");
//			�������� �ڿ� , ��� ��ſ� i�� ������ �ε����� �ƴҶ� ,�� ��´�. result.append(",");�� ���ش�.
			result.append("{\"value\":\""+chatList.get(i).getChatTime()+"\"}]");
			if(i != chatList.size() - 1) result.append(",");
		}
		result.append("], \"last\":\""+ chatList.get(chatList.size() -1 ).getChatID()+"\"}");
		return result.toString();
	}
	
	public String getTen() {
		StringBuffer result = new StringBuffer("");
//		�������� �̸��� �����ϰ� Ŭ���̾�Ʈ�� ������ Ŭ���̾�Ʈ���� �Ľ��Ͽ� ȭ�鿡 ������ش�.
		result.append("{\"result\":[");
		ChatDAO chatDAO = new ChatDAO();
		ArrayList<Chat> chatList = chatDAO.getChatListByRecent(10);
		for(int i=0; i<chatList.size();i++) {
			result.append("[{\"value\":\""+chatList.get(i).getChatName()+"\"},");
			result.append("{\"value\":\""+chatList.get(i).getChatContent()+"\"},");
//			�������� �ڿ� , ��� ��ſ� i�� ������ �ε����� �ƴҶ� ,�� ��´�. result.append(",");�� ���ش�.
			result.append("{\"value\":\""+chatList.get(i).getChatTime()+"\"}]");
			if(i != chatList.size() - 1) result.append(",");
		}

		result.append("], \"last\":\""+ chatList.get(chatList.size() -1 ).getChatID()+"\"}");
		return result.toString();
	}
	
	public String getID(String chatID) {
		StringBuffer result = new StringBuffer("");
//		�������� �̸��� �����ϰ� Ŭ���̾�Ʈ�� ������ Ŭ���̾�Ʈ���� �Ľ��Ͽ� ȭ�鿡 ������ش�.
		result.append("{\"result\":[");
		ChatDAO chatDAO = new ChatDAO();
		ArrayList<Chat> chatList = chatDAO.getChatListByRecent(chatID);
		for(int i=0; i<chatList.size();i++) {
			result.append("[{\"value\":\""+chatList.get(i).getChatName()+"\"},");
			result.append("{\"value\":\""+chatList.get(i).getChatContent()+"\"},");
//			�������� �ڿ� , ��� ��ſ� i�� ������ �ε����� �ƴҶ� ,�� ��´�. result.append(",");�� ���ش�.
			result.append("{\"value\":\""+chatList.get(i).getChatTime()+"\"}]");
			if(i != chatList.size() - 1) result.append(",");
		}

		result.append("], \"last\":\""+ chatList.get(chatList.size() -1 ).getChatID()+"\"}");
		return result.toString();
	}
		
}
