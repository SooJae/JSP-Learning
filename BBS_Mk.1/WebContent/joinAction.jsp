<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!-- 유저의 정보를 가져옴 -->
<%@ page import="user.UserDAO"%>
<!-- 자바스크립트를 사용하기 위해 import -->
<%@ page import="java.io.PrintWriter"%>
<%
	request.setCharacterEncoding("UTF-8");
%>
<!-- join에서 보내는 변수들을 받아서 user(DTO)에 저장한다. -->
<jsp:useBean id="user" class="user.User" scope="page" />
<%-- <jsp:setProperty name="user" property="userID" />
<jsp:setProperty name="user" property="userPassword" />
<jsp:setProperty name="user" property="userName" />
<jsp:setProperty name="user" property="userGender" />
<jsp:setProperty name="user" property="userEmail" /> --%>
<jsp:setProperty name="user" property="*" />

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>JSP 게시판</title>
</head>
<body>
	<%
		String userID = null;
		if (session.getAttribute("userID") != null) {
			userID = (String) session.getAttribute("userID");
		}
		if (userID != null) {
			PrintWriter script = response.getWriter();
			script.println("<script>");
			script.println("alert('이미 로그인이 되어있습니다.')");
			script.println("location.href='main.jsp'");
			script.println("</script>");
		}
		/* 위에 빈즈가 생겼기 때문에 DTO에서 값을 가져올 수 있다.  */
		if (user.getUserID() == null || user.getUserPassword() == null || user.getUserName() == null
				|| user.getUserGender() == null || user.getUserEmail() == null) {
			PrintWriter script = response.getWriter();
			script.println("<script>");
			script.println("alert('입력이 안된 사항이 있습니다.')");
			script.println("history.back()");
			script.println("</script>");
		} else {
			UserDAO userDAO = new UserDAO();
			/* 각각의 변수를 입력받은 인스턴스(빈즈로 생성한)가 join함수를 수행하도록 매개변수로 들어간다. */
			int result = userDAO.join(user);
			if (result == -1) {
				PrintWriter script = response.getWriter();
				script.println("<script>");
				script.println("alert('이미 존재하는 아이디 입니다.')");
				script.println("history.back()");
				script.println("</script>");
			} else {
				PrintWriter script = response.getWriter();
				session.setAttribute("userID", user.getUserID());
				script.println("<script>");
				script.println("location.href ='main.jsp'");
				script.println("</script>");
			}
		}
	%>
</body>
</html>