import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns={"/servlet/kokushi1"})

public class kokushi1 extends HttpServlet{
	
	public void doPost(HttpServletRequest req, HttpServletResponse res) 
			throws ServletException, IOException {
		PrintWriter out;
		req.setCharacterEncoding("UTF-8");
		res.setContentType("text/html;charset=UTF-8");
		out = res.getWriter();
		
		// 	値の取得
		String namaeStr = req.getParameter("namae");
		String gakunenStr = req.getParameter("gakunen");
		String gozenStr = req.getParameter("gozen");
		String gogoStr = req.getParameter("gogo");
		
		// 判定
		int gozen = Integer.parseInt(gozenStr);
		int gogo = Integer.parseInt(gogoStr);
		int gokei = gozen + gogo;
		String hantei = "不合格";
		if(gozen >= 65 && gogo >= 65 && gokei >= 140) {
			hantei = "合格";
		}
		
		StringBuffer sb = new StringBuffer();
		sb.append("<html>");
		sb.append("<head>");
		sb.append("<title>国家試験判定</title>");
		sb.append("</head>");
		sb.append("<body>");
		sb.append("kokushi1.java");
		sb.append("<center>");
		sb.append("<h1>国家試験判定</h1>");
		sb.append("<br>");
		sb.append("<h1>");
		sb.append(gakunenStr);
		sb.append("の");
		sb.append(namaeStr);
		sb.append("さん");
		sb.append("</h1>");
		sb.append("<br>");
		sb.append("<h1>あなたの得点は</h1>");
		sb.append("<br>");
		sb.append("<h1>午前");
		sb.append(gozen);
		sb.append("点　午後");
		sb.append(gogo);
		sb.append("点　合計");
		sb.append(gokei);
		sb.append("点");
		sb.append("</h1>");
		sb.append("<br>");
		sb.append("<h1>判定結果は");
		sb.append(hantei);
		sb.append("です</h1>");
		sb.append("</center>");
		sb.append("<br>");
		sb.append("<a href='/JV27/kokushi1.html'>戻る</a>");
		sb.append("</body>");
		sb.append("</html>");
		
		out.println(sb.toString());
	}
	
}
