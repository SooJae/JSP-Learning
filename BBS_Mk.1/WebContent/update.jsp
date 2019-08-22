<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.io.PrintWriter"%>
<%@ page import="bbs.Bbs"%>
<%@ page import="bbs.BbsDAO"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<!-- 반응형 웹에 사용되는 메타태그 -->
<meta name="viewport" content="width=device-width" , initial-scale="1">
<!-- CSS 링크 -->
<link rel="stylesheet" href="css/bootstrap.css">
<link rel="stylesheet" href="css/custom.css">
<title>JSP 게시판</title>
</head>
<body>
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
		}
	%>

	<nav class="navbar navbar-default">
		<div class="navbar-header">
			<button type="button" class="navbar-toggle collapsed"
				data-toggle="collapse" data-target="#bs-example-navbar-collapse-1"
				aria-expanded="false">
				<span class="icon-bar"></span> <span class="icon-bar"></span> <span
					class="icon-bar"></span>
			</button>
			<!-- 로고 기능 달기 -->
			<a class="navbar-brand" href="main.jsp">JSP 게시판 웹사이트</a>
		</div>
		<div class="collapse navbar-collapse"
			id="bs-example-navbar-collapse-1">
			<ul class="nav navbar-nav">
				<!-- 메인 페이지 이기때문에 class="active"를 달아준다. -->
				<li><a href="main.jsp">메인</a></li>
				<li class="active"><a href="bbs.jsp">게시판</a></li>
			</ul>

			<ul class="nav navbar-nav navbar-right">
				<li class="dropdown"><a href="#" class="dropdown-toggle"
					data-toggle="dropdown" role="button" aria-haspopup="true"
					aria-expanded="false">회원관리<span class="caret"></span></a>
					<ul class="dropdown-menu">
						<!-- 로그인 상태일시 로그아웃 기능만 추가해준다. -->
						<li><a href="logoutAction.jsp">로그아웃</a></li>
					</ul>
			</ul>
		</div>
	</nav>
	<!-- 테이블을 만들어 디자인 해준다. -->
	<div class="container">
		<div class="row">
		<!-- 해당 게시글을 클릭하면 updateAction.jsp 페이지로 이동하면서 bbsID값을 넘겨준다. -->
			<form method="post" action="updateAction.jsp?bbsID=<%= bbsID %>">
				<!-- 홀수와 짝수가 번갈아가며 색상이 바뀌는 테이블 -->
				<table class="table table-striped"
					style="text-align: center; border: 1px solid #dddddd">
					<!-- 테이블의 제목부분, 속성들을 알려줌. -->
					<thead>
						<tr>
							<th colspan="2"
								style="background-color: #eeeeee; text-align: center;">게시판
								글 수정 양식</th>
						</tr>
					</thead>
					<tbody>
					<!-- 글 내용을 표시할때 제목부분은 value로 사용하고 글 내용은 태그 사이에 넣는다.-->
						<tr>
							<td><input type="text" class="form-control"
								placeholder="글 제목" name="bbsTitle" maxlength="50" value="<%=bbs.getBbsTitle()%>"></td>
						</tr>
						<tr>
							<td><textarea class="form-control" placeholder="글 내용"
									name="bbsContent" maxlength="2048" style="height: 350px;"><%=bbs.getBbsContent()%></textarea></td>
						</tr>
					</tbody>
				</table>
				<!-- 글쓰기 버튼 만들기 -->
				<!-- 	<a href="write.jsp" class="btn btn-primary pull-right" >글쓰기</a> -->
				<input type="submit" class="btn btn-primary pull-right" value="글 수정" />
			</form>
		</div>
	</div>


	<!-- 애니메이션 자바스크립트 -->
	<script src="https://code.jquery.com/jquery-3.1.1.min.js"></script>
	<!-- 부트스트랩 자바스크립트 -->
	<script src="js/bootstrap.js"></script>
</body>
</html>