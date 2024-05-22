import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns={"/servlet/class_select"})

public class ClassSelect extends HttpServlet{
	
	public void doPost(HttpServletRequest req, HttpServletResponse res) 
			throws ServletException, IOException {
		
		/**
		 * DB: URL
		 */
		final String URL = "jdbc:mysql://localhost/nhs30061db?useUnicode=true&characterEncoding=UTF-8";
		/**
		 * DB: ユーザ
		 */
		final String USER = "root";
		/**
		 * DB: パスワード
		 */
		final String PASS = "root";
		/**
		 * DB: ドライバ
		 */
		final String DRIVER = "com.mysql.jdbc.Driver";
		
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		
		String class_no = "", syusseki, gakuseki_no, simei_1, simei_2, kana_1, kana_2, umare, tannin = "", class_name = "";
		PrintWriter out;
		req.setCharacterEncoding("UTF-8");
		res.setContentType("text/html;charset=UTF-8");
		out = res.getWriter();
		
		String sentakuStr = req.getParameter("select_id");
		String kubun = "";
		String kensakuStr = "";
		switch(sentakuStr) {
			case "1":
				kubun = "クラス";
				kensakuStr = req.getParameter("class_no");
				break;
			case "2":
				kubun = "学年";
				kensakuStr = req.getParameter("gakunen_no");
				break;
			case "3":
				kubun = "氏名検索ワード";
				kensakuStr = req.getParameter("simei");
				break;
			case "4":
				kubun = "担任";
				kensakuStr = req.getParameter("tannin");
				break;
			default:
				break;
		}
		
		try {
			Class.forName(DRIVER);
			con = DriverManager.getConnection(URL, USER, PASS);
			stmt = con.createStatement();
			
			StringBuffer query = new StringBuffer();
			
			if(sentakuStr.equals("4")) {
				query.append("select simei_1, simei_2 from teacher_table where teacher_no = '");
				query.append(kensakuStr);
				query.append("'");
				rs = stmt.executeQuery(query.toString());
				rs.next();
				tannin = rs.getString(1) + rs.getString(2);
				query = new StringBuffer();
			}
			
			if(sentakuStr.equals("1")) {
				query.append("select class_name from class_name_table where class_no = '");
				query.append(kensakuStr);
				query.append("'");
				rs = stmt.executeQuery(query.toString());
				rs.next();
				tannin = rs.getString(1);
				query = new StringBuffer();
			}
			
			query.append("select * from class_table c1 ");
			
			switch(sentakuStr) {
				case "1":
					query.append("where ");
					query.append("class_no = '");
					query.append(kensakuStr);
					query.append("' order by syusseki_no");
					break;
				case "2":
					query.append(", class_name_table c2 where c1.class_no = c2.class_no and ");
					query.append("c2.class_name like '%1");
					query.append(kensakuStr);
					query.append("%' order by substring(c2.class_name, 3, 2), c2.class_name, c1.syusseki_no");
					break;
				case "3":
					query.append(", class_name_table c2 where c1.class_no = c2.class_no and ");
					query.append("simei_1 like '%");
					query.append(kensakuStr);
					query.append("%' or (simei_2 like '%");
					query.append(kensakuStr);
					query.append("%' and simei_1 not like '%");
					query.append(kensakuStr);
					query.append("%') order by substring(c2.class_name, 3, 2), c2.class_name, c1.syusseki_no");
					break;
				case "4":
					query.append(", class_name_table c2 where c1.class_no = c2.class_no and ");
					query.append("c2.class_name in (select class_no from tannin_table where teacher_no = '");
					query.append(kensakuStr);
					query.append("') order by substring(c2.class_name, 3, 2), c2.class_name, c1.syusseki_no");
					break;
				default:
					break;
			}
			
			rs = stmt.executeQuery(query.toString());
			
			StringBuffer sb = new StringBuffer();
			sb.append("<html>");
			sb.append("<head>");
			sb.append("<title>名簿検索</title>");
			sb.append("</head>");
			sb.append("<body>");
			sb.append("ClassSelect.java");
			sb.append("<center>");
			sb.append("<caption><font size='+3' color='#0000ff'><b>＜＜名簿検索＞＞</b></font></caption>");
			sb.append("<br><br><br>");
			sb.append("<p>検索結果　");
			sb.append(kubun);
			sb.append("：");
			if(sentakuStr.equals("4") || sentakuStr.equals("1")) {
				sb.append(tannin);
			} else {
				sb.append(kensakuStr);
				if(sentakuStr.equals("2")) {
					sb.append("年");			
				}
			}
			sb.append("</p>");
			sb.append("<br>");
			sb.append("<table border='1' bordercolor='darkblue'>");
			sb.append("<tr>");
			if(!sentakuStr.equals("1")) {
				sb.append("<td bgcolor='darkblue'><font color='white'>クラス</font></td>");			
			}
			sb.append("<td bgcolor='darkblue'><font color='white'>出席</font></td>");
			sb.append("<td bgcolor='darkblue'><font color='white'>学籍</font></td>");
			sb.append("<td bgcolor='darkblue'><font color='white'>氏名（姓）</font></td>");
			sb.append("<td bgcolor='darkblue'><font color='white'>氏名（名）</font></td>");
			sb.append("<td bgcolor='darkblue'><font color='white'>カナ（姓）</font></td>");
			sb.append("<td bgcolor='darkblue'><font color='white'>カナ（姓）</font></td>");
			sb.append("<td bgcolor='darkblue'><font color='white'>生年月日</font></td>");
			sb.append("</tr>");
			
			while(rs.next()) {

				if(!sentakuStr.equals("1")) {
					class_name = rs.getString("c2.class_name");
				}
				syusseki = rs.getString("syusseki_no");
				gakuseki_no = rs.getString("gakuseki_no");
				simei_1 = rs.getString("simei_1");
				simei_2 = rs.getString("simei_2");
				kana_1 = rs.getString("kana_1");
				kana_2 = rs.getString("kana_2");
				umare = rs.getString("umare");
				
				sb.append("<tr>");
				if(!sentakuStr.equals("1")) {
					sb.append("<td>");
					sb.append(class_name);
					sb.append("</td>");
				}
				sb.append("<td>");
				sb.append(syusseki);
				sb.append("</td>");
				sb.append("<td>");
				sb.append(gakuseki_no);
				sb.append("</td>");
				sb.append("<td>");
				sb.append(simei_1);
				sb.append("</td>");
				sb.append("<td>");
				sb.append(simei_2);
				sb.append("</td>");
				sb.append("<td>");
				sb.append(kana_1);
				sb.append("</td>");
				sb.append("<td>");
				sb.append(kana_2);
				sb.append("</td>");
				sb.append("<td>");
				sb.append(umare);
				sb.append("</td>");
				sb.append("</tr>");
			}
			sb.append("</table></center>");
			sb.append("<br><br>");
			sb.append("<hr>");
			sb.append("<a href='/JV27/class_select.html'>名簿検索に戻る</a><span> </span><a href='/JV27/class_index.html'>ホームへ戻る</a>");
			sb.append("</body>");
			sb.append("</html>");
			
			out.println(sb.toString());
		} catch(SQLException e) {
			out.println(" --- SQL Exception -- <BR>");
			out.println("message: <BR>");
			while(e != null) {
				out.println(e.getMessage() + "<BR>");
				e = e.getNextException();
			}
		} catch(Exception e) {
			e.printStackTrace(out);
		} finally { 
			try {
				if (rs != null) {
					rs.close();
				}
				if (stmt != null) {
					stmt.close();
				}
				if (con != null) {
					con.close();
				}
			} catch (SQLException e) {
				e.printStackTrace(out);
			}
		}
	}
	
}
