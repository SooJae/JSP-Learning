<%-- <%@ page import="java.io.File" %> --%>
<!-- 데이터베이스에 들어있는 파일만 갖고오게 하기 위해서 아래와 같이 수정한다. -->
<%@ page import="file.FileDTO" %>
<%@ page import="file.FileDAO" %>

<%@ page import ="java.util.ArrayList" %>

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <!-- C:\Projects\BBS\.metadata\.plugins\org.eclipse.wst.server.core\tmp0\wtpwebapps\File Upload 에 upload 폴더를 만들어준다. -->
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>JSP 파일 업로드</title>
</head>
<body>
	<%
		/* String directory = application.getRealPath("/upload/"); */
		/* String directory ="C:/JSP/upload";
		String files[]	= new File(directory).list();
		
		for(String file : files){
			out.write("<a href=\""+ request.getContextPath()+"/downloadAction?file=" + 
			java.net.URLEncoder.encode(file, "UTF-8")+"\">" +file +"</a><br>");
		} */
		
		ArrayList<FileDTO> fileList = new FileDAO().getList();
		for(FileDTO file : fileList){
			out.write("<a href=\""+request.getContextPath()+"/downloadAction?file="+
			java.net.URLEncoder.encode(file.getFileRealName(),"UTF-8")+"\">"+
					file.getFileName()+"(다운로드 횟수: "+file.getDownloadCount()+")</a><br>");
		}
	%>
	
</body>
</html>