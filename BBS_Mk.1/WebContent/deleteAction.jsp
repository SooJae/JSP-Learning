<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!-- 유저의 정보를 가져옴 -->

<%@ page import="bbs.BbsDAO"%>
<%@ page import="bbs.Bbs"%>
<!-- 자바스크립트를 사용하기 위해 import -->
<%@ page import="java.io.PrintWriter"%>
<%
	request.setCharacterEncoding("UTF-8");
%>


<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>JSP 게시판</title>
</head>
<body>
	console.log(request.getParameter("bbsID"));
	<%
	String userID = null;
	if (session.getAttribute("userID") != null) {
		userID = (String) session.getAttribute("userID");
	}
	if (userID == null) {
		PrintWriter script = response.getWriter();
		script.println("<script>");
		script.println("alert('로그인 하세요.')");
		script.println("location.href='login.jsp'");
		script.println("</script>");
	}
	/* 위에 빈즈가 생겼기 때문에 DTO에서 값을 가져올 수 있다.  */
	/* 빈즈가 사라졌기 때문에 직접 파라미터를 받아와야한다. update.jsp에서 form태그(?)로 넘어온 것. bbs.getBbsTitle() => request.getParameter("bbsTitle") */
	int bbsID = 0;
	if (request.getParameter("bbsID") != null) {
		bbsID = Integer.parseInt(request.getParameter("bbsID"));
	}
	if (bbsID == 0) {
		PrintWriter script = response.getWriter();
		script.println("<script>");
		script.println("alert('유효하지 않은 글입니다.')");
		script.println("location.href='bbs.jsp'");
		script.println("</script>");
	}
	/* bbsID값을 가지고 해당 게시글을 객체로 가져온다. */
	Bbs bbs = new BbsDAO().getBbs(bbsID);
	/* 세션에 있는 userID와 게시물을 작성한 userID를 비교한다. */
	if (!userID.equals(bbs.getUserID())) {
		PrintWriter script = response.getWriter();
		script.println("<script>");
		script.println("alert('권한이 없습니다.')");
		script.println("location.href='bbs.jsp'");
		script.println("</script>");
	} else {
		BbsDAO bbsDAO = new BbsDAO();
		/* 각각의 변수를 입력받은 인스턴스(빈즈로 생성한)가 join함수를 수행하도록 매개변수로 들어간다. */
		int result = bbsDAO.delete(bbsID);
		if (result == -1) {
			PrintWriter script = response.getWriter();
			script.println("<script>");
			script.println("alert('글 삭제에 실패했습니다.')");
			script.println("history.back()");
			script.println("</script>");
		} else {
			PrintWriter script = response.getWriter();
			script.println("<script>");
			script.println("location.href ='bbs.jsp'");
			script.println("</script>");
		}
	}
%>
</body>
</html>