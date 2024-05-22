import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns={"/servlet/HelloWorld"})

public class HelloWorld extends HttpServlet{
	
	public void doGet(HttpServletRequest req, HttpServletResponse res) 
			throws ServletException, IOException {
		PrintWriter out;
		req.setCharacterEncoding("UTF-8");
		res.setContentType("text/html;charset=UTF-8");
		out = res.getWriter();
		out.println("<html>");
		out.println("<head>");
		out.println("<title>Hello World</title>");
		out.println("</head>");
		out.println("<body>");
		out.println("<center>");
		out.println("<h1>Hello World!!</h1>");
		out.println("<br><br>");
		out.println("ようこそJAVAの世界へ");
		out.println("</center>");
		out.println("<br>");
		out.println("<a href='/JV27/helloworld.html'>トップへ戻る</a>");
		out.println("</body>");
		out.println("</html>");
	}
	
}
