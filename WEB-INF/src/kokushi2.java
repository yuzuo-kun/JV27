import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns={"/servlet/kokushi2"})

public class kokushi2 extends HttpServlet{
	
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

		req.setAttribute("namae", namaeStr);
		req.setAttribute("gakunen", gakunenStr);
		req.setAttribute("gozen", gozenStr);
		req.setAttribute("gogo", gogoStr);
		req.setAttribute("gokei", String.valueOf(gokei));
		req.setAttribute("hantei", hantei);
		
		ServletContext sc = getServletContext();
		sc.getRequestDispatcher("/kokushi2.jsp").forward(req, res);
	}
	
}
