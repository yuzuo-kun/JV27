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

@WebServlet(urlPatterns={"/servlet/class_insert"})

public class ClassInsert extends HttpServlet{
	
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
		
		PrintWriter out;
		req.setCharacterEncoding("UTF-8");
		res.setContentType("text/html;charset=UTF-8");
		out = res.getWriter();

		String classNo = req.getParameter("class_no");
		String syusseki = "";
		String nendo = req.getParameter("nendo");
		String gakuseki = "";
		String simei1 = req.getParameter("simei_1");
		String simei2 = req.getParameter("simei_2");
		String kana1 = req.getParameter("kana_1");
		String kana2 = req.getParameter("kana_2");
		String birth = req.getParameter("birth");
		
		try {
			Class.forName(DRIVER);
			con = DriverManager.getConnection(URL, USER, PASS);
			stmt = con.createStatement();
			
			StringBuffer query = new StringBuffer();
			
			query.append("select gakuseki_no from class_table where gakuseki_no = '");
			query.append(gakuseki);
			query.append("'");
			rs = stmt.executeQuery(query.toString());
			String resultMessage = "";
			if(rs.next()) {
				resultMessage = "学籍番号「" + gakuseki + "」は既に登録されています。";
			} else {
				query = new StringBuffer();
				query.append("select coalesce(max(syusseki_no), 0) + 1 as syusseki from class_table where class_no = '");
				query.append(classNo);
				query.append("'");
				rs = stmt.executeQuery(query.toString());
				rs.next();
				if(rs.getInt("syusseki") < 10) {
					syusseki = "0" + rs.getInt("syusseki");
				} else {
					syusseki = String.valueOf(rs.getInt("syusseki"));
				}
				
				query = new StringBuffer();
				query.append("select coalesce(max(gakuseki_no), ");
				query.append(nendo + "0000");
				query.append(") + 1 as gakuseki from class_table where gakuseki_no like '");
				query.append(nendo);
				query.append("%'");
				rs = stmt.executeQuery(query.toString());
				rs.next();
				gakuseki = String.format("%05d", rs.getInt("gakuseki"));
				
				query = new StringBuffer();
				query.append("insert into class_table() values('");
				query.append(classNo);
				query.append("', '");
				query.append(syusseki);
				query.append("', '");
				query.append(gakuseki);
				query.append("', '");
				query.append(simei1);
				query.append("', '");
				query.append(simei2);
				query.append("', '");
				query.append(kana1);
				query.append("', '");
				query.append(kana2);
				query.append("', '");
				query.append(birth);
				query.append("')");
				
				int resultInt = stmt.executeUpdate(query.toString());
				if(resultInt == 1) {
					resultMessage = "名簿登録完了しました。";
				}
			}
			StringBuffer sb = new StringBuffer();
			sb.append("<html>");
			sb.append("<head>");
			sb.append("<title>名簿登録</title>");
			sb.append("</head>");
			sb.append("<body>");
			sb.append("ClassInsert.java");
			sb.append("<center>");
			sb.append("<caption><font size='+3' color='#0000ff'><b>＜＜名簿登録＞＞</b></font></caption>");
			sb.append("<br><br><br>");
			sb.append("<center><font size='+2'>");
			sb.append(resultMessage);
			sb.append("</font></center>");
			sb.append("<br><br>");
			sb.append("<hr>");
			sb.append("<a href='/JV27/class_insert.html'>名簿登録に戻る</a><span> </span><a href='/JV27/class_index.html'>ホームへ戻る</a>");
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
