import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns={"/servlet/bmi"})

public class bmi extends HttpServlet{
	
	public void doPost(HttpServletRequest req, HttpServletResponse res) 
			throws ServletException, IOException {
		PrintWriter out;
		req.setCharacterEncoding("UTF-8");
		res.setContentType("text/html;charset=UTF-8");
		out = res.getWriter();
		
		// 	値の取得
		String nameStr = req.getParameter("name");
		String heightStr = req.getParameter("height");
		String weightStr = req.getParameter("weight");
		
		// 計算
		float height = Float.parseFloat(heightStr) / 100;
		float weight = Float.parseFloat(weightStr);
		float bmi = weight / (height * height);
		String result = "やせている";
		if(bmi >= 25.0) {
			result = "太っている";
		} else if(bmi >= 18.5) {
			result = "標準";
		}
		float baseBMI = 22;
		float baseWeight = baseBMI * height * height;
		
		StringBuffer sb = new StringBuffer();
		sb.append("<html>");
		sb.append("<head>");
		sb.append("<title>BMI測定結果</title>");
		sb.append("</head>");
		sb.append("<body>");
		sb.append("bmi.java");
		sb.append("<center>");
		sb.append("<h1>BMI測定結果</h1>");
		sb.append("<br>");
		sb.append("<h1>");
		sb.append(nameStr);
		sb.append("さん");
		sb.append("</h1>");
		sb.append("<h1>あなたのBMI値は</h1>");
		sb.append("<h1>身長：");
		sb.append(heightStr);
		sb.append("cm</h1>");
		sb.append("<h1>体重：");
		sb.append(weight);
		sb.append("kg</h1>");
		sb.append("<h1>BMI値：");
		sb.append(String.format("%.2f", bmi));
		sb.append("</h1>");
		sb.append("<br>");
		sb.append("<br>");
		sb.append("<h1>判定結果は\"");
		sb.append(result);
		sb.append("\"です</h1>");
		if(!result.equals("標準")) {
			sb.append("<h1>標準体重まであと");
			sb.append(String.format("%.2f", baseWeight - weight));
			sb.append("kg</h1>");
		}
		sb.append("</center>");
		sb.append("<br>");
		sb.append("<a href='/JV27/bmi.html'>戻る</a>");
		sb.append("</body>");
		sb.append("</html>");
		
		out.println(sb.toString());
	}
	
}
