package file;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/downloadAction")
public class downloadAction extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String fileName = request.getParameter("file");


//		String directory = this.getServletContext().getRealPath("/upload/");
		String directory ="C:/JSP/upload/";
		File file = new File(directory + "/" + fileName);
//		어떠한 데이터를 주고받을지를 담는 것
		String mimeType = getServletContext().getMimeType(file.toString());
		if (mimeType != null) {
//			파일 관련한 데이터를 주고 받을때는 octet-stream을 사용한다. 
			// 사용자 입장에선 자기가 받을 데이터가 파일 정보라는 것을 response를 통해 알 수 있다. 아! 내가 받아야 되는게 파일이구나
			// (정확히는 2진 데이터 파일)
			response.setContentType("application/octet-stream");

			String downloadName = null;
//			인터넷 익스플로러로 접속한 사용자가 아니라면

			if (request.getHeader("user-agent").indexOf("MSIE") == -1) {
				downloadName = new String(fileName.getBytes("UTF-8"), "8859_1");

			} else {
//				익스플로러라면
				downloadName = new String(fileName.getBytes("EUC-KR"), "8859_1");

			}
			response.setHeader("Content-Disposition", "attachment;filename=\"" + downloadName + "\";");
			FileInputStream fileInputStream = new FileInputStream(file);
			ServletOutputStream servletOutputStream = response.getOutputStream();

			// 바이트단위로 쪼개서 보냄
			byte b[] = new byte[1024];
			int data = 0;

			while ((data = (fileInputStream.read(b, 0, b.length))) != -1) {
				servletOutputStream.write(b, 0, data);
			}
			
			new FileDAO().hit(fileName);
			
//			남아있는 데이터까지 모두 비워서 보내준다.
			servletOutputStream.flush();
			servletOutputStream.close();
			fileInputStream.close();

		}
	}

}
