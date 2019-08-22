<%@ page import="file.FileDAO" %>
<%@ page import="java.io.File" %>
<%@ page import="java.util.Enumeration" %>
<!-- 사용자가 올린 파일의 이름이 중복되면 자동으로 파일 이름을 바꿔서 오류가 안생기게 해준다.-->
<%@ page import="com.oreilly.servlet.multipart.DefaultFileRenamePolicy" %>
<!-- multipartrequst 클래스를 갖고온다. -->
<%@ page import="com.oreilly.servlet.MultipartRequest" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <!-- C:\Projects\BBS\.metadata\.plugins\org.eclipse.wst.server.core\tmp0\wtpwebapps\File Upload 에 upload 폴더를 만들어준다. -->
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>JSP 파일 업로드</title>
</head>
<body>
	<%
		/* String directory = application.getRealPath("/upload/"); */
		//하드 코딩으로 직접 경로를 써준다.
		String directory ="C:/JSP/upload/";
	/* 100MB */	
	int maxSize=1024*1024*100;
		String encoding ="UTF-8";
		/* 사용자가 전송한 request(파일정보를) 토대로 우리가 만든 upload폴더에다가 100MB만큼 UTF-8의 인코딩 형식으로 */
		MultipartRequest multipartRequest = new MultipartRequest(request, directory, maxSize, encoding, 
				new DefaultFileRenamePolicy());
		
		Enumeration fileNames = multipartRequest.getFileNames();
		while(fileNames.hasMoreElements()){
			String parameter = (String)fileNames.nextElement();
			//file에서 파라미터 값을 받아 fileName에 넣는다.
			String fileName = multipartRequest.getOriginalFileName(parameter);
			// 실제로 서버에 업로드가 된 파일 시스템 Name을 갖고옴
			String fileRealName = multipartRequest.getFilesystemName(parameter);
			
			// 파일 1, 2, 3 중에 1,3 에만 파일을 넣었을 경우 2가 Null값 이기 때문에 continue로 건너 뛰어준다.
			if(fileName==null) continue;
			
			// Web shell 공격 방지(서버에 jsp파일을 올려놓고 실행시킬수도 있기 때문에) 시큐어 코딩
			// 그러나 race Condition 취약점이 생긴다. 우선 업로드가 된 이후에 삭제가 되기때문에. 
			// 계속 업로드를 시키면 경쟁상태를 유발하게 된다.(하나의 스레드는 지우고, 하나의 스레드는 생성시키려고 하기때문에) 그렇게 되면 한번쯤은 실행하게 된다.
			// 이를 원천봉쇄하는 방법은 루트 디렉토리 밖에 업로드 폴더를 위치시키는 것이다.
			if(!fileName.endsWith(".jpg") && !fileName.endsWith(".png") && !fileName.endsWith(".pdf") && !fileName.endsWith(".xls")){
				File file = new File(directory+fileRealName);
				//파일을 삭제시켜버린다.
				file.delete();
				out.write("업로드할 수 없는 확장자 입니다.");
			} else {
				new FileDAO().upload(fileName,fileRealName);
				out.write("파일명: " +fileName+"<br>");
				out.write("실제 파일명: "+fileRealName+"<br>");
			}
		}
		
		
		
	%>
</body>
</html>