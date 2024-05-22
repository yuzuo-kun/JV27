<%@ page contentType="text/html;charset=UTF-8" %>
<%
	response.setContentType("text/html;charset=UTF-8");
	String gakunen = request.getAttribute("gakunen").toString();
	String namae = request.getAttribute("namae").toString();
	String gozen = request.getAttribute("gozen").toString();
	String gogo = request.getAttribute("gogo").toString();
	String gokei = request.getAttribute("gokei").toString();
	String hantei = request.getAttribute("hantei").toString();
%>
<html>
<head>
	<title>国家試験判定</title>
</head>
<body>
	kokushi2.jsp
	<center>
		<h1>国家試験判定</h1>
		<br>
		<h1>
			<font color="deeppink"><%=gakunen%></font>
			の
			<font color="deeppink"><%=namae%></font>
			さん
		</h1>
		<br>
		<h1>あなたの得点は</h1>
		<br>
		<h1>
			午前
			<font color="deeppink"><%=gozen%></font>
			点　午後
			<font color="deeppink"><%=gogo%></font>
			点　合計
			<font color="deeppink"><%=gokei%></font>
			点
		</h1>
		<br>
		<h1>
			判定結果は
			<font color="deeppink"><%=hantei%></font>
			です
		</h1>
	</center>
	<br>
	<a href="/JV27/kokushi2.html">戻る</a>
</body>
</html>
