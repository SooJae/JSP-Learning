<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.io.PrintWriter"%>
<!-- 실제 데이터베이스를 사용하기위해 Bbs 클래스를 가져온다. -->
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
		Bbs bbs = new BbsDAO().getBbs(bbsID);
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

			<%
				if (userID == null) {
			%>
			<ul class="nav navbar-nav navbar-right">
				<li class="dropdown"><a href="#" class="dropdown-toggle"
					data-toggle="dropdown" role="button" aria-haspopup="true"
					aria-expanded="false">접속하기<span class="caret"></span></a>
					<ul class="dropdown-menu">
						<li><a href="login.jsp">로그인</a></li>
						<li><a href="join.jsp">회원가입</a></li>
					</ul>
			</ul>
			<%
				} else {
			%>
			<ul class="nav navbar-nav navbar-right">
				<li class="dropdown"><a href="#" class="dropdown-toggle"
					data-toggle="dropdown" role="button" aria-haspopup="true"
					aria-expanded="false">회원관리<span class="caret"></span></a>
					<ul class="dropdown-menu">
						<!-- 로그인 상태일시 로그아웃 기능만 추가해준다. -->
						<li><a href="logoutAction.jsp">로그아웃</a></li>
					</ul>
			</ul>
			<%
				}
			%>
		</div>
	</nav>

	<!-- 테이블을 만들어 디자인 해준다. -->
	<div class="container">
		<div class="row">
			<!-- 홀수와 짝수가 번갈아가며 색상이 바뀌는 테이블 -->
			<table class="table table-striped"
				style="text-align: center; border: 1px solid #dddddd">
				<!-- 테이블의 제목부분, 속성들을 알려줌. -->
				<thead>
					<tr>
						<!-- 3개만큼의 열을 차지함 -->
						<th colspan="3"
							style="background-color: #eeeeee; text-align: center;">게시판 글
							보기</th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<td style="width: 20%">글 제목</td>
						<td colspan="2"><%=bbs.getBbsTitle().replaceAll(" ","&nbsp;").replaceAll("<","&lt").replaceAll(">","&gt").replaceAll("\n","<br>")%></td>
					</tr>
					<tr>
						<td>작성자</td>
						<td colspan="2"><%=bbs.getUserID()%></td>
					</tr>
					<tr>
						<td>작성일자</td>
						<td colspan="2"><%=bbs.getBbsDate().substring(0, 11) + "시" + bbs.getBbsDate().substring(11, 13) + "분"%></td>
					</tr>
					<tr>
						<td>내용</td>
						<td colspan="2" style="min-height:200px; text-align:left;"><%=bbs.getBbsContent().replaceAll(" ","&nbsp;").replaceAll("<","&lt").replaceAll(">","&gt").replaceAll("\n","<br>")%></td>
					</tr>
				</tbody>
			</table>
			<a href="bbs.jsp" class="btn btn-primary">목록</a>
			<%
				/* userID가 해당 글의 userID와(getUserID) 같다면. */
				if(userID !=null && userID.equals(bbs.getUserID())){
					%>
					<a href="update.jsp?bbsID=<%= bbsID%>" class="btn btn-primary">수정</a>
					<a href="deleteAction.jsp?bbsID=<%= bbsID%>" class="btn btn-primary" onclick="return confirm('정말로 삭제하시겠습니까?')">삭제</a>
					<%
				}
				
			%>
			
			<!-- 글쓰기 버튼 만들기 -->
			<!-- 	<a href="write.jsp" class="btn btn-primary pull-right" >글쓰기</a> -->
			<a href="write.jsp" class="btn btn-primary pull-right">글쓰기</a>
		</div>
	</div>


	<!-- 애니메이션 자바스크립트 -->
	<script src="https://code.jquery.com/jquery-3.1.1.min.js"></script>
	<!-- 부트스트랩 자바스크립트 -->
	<script src="js/bootstrap.js"></script>
</body>
</html>