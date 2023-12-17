<%@ page import="study.infowifi.db.DB" %>
<%@ page import="study.infowifi.history.History" %>
<%@ page import="java.util.List" %>
<%@ page import="java.io.PrintWriter" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>와이파이 정보 구하기</title>
</head>
<body>
<h1>위치 히스토리 목록</h1>
<p>
    <a href="#">홈</a> | <a href="history.jsp">위치 히스토리 목록</a> | <a href="hello-servlet">Open API 와이파이 정보 가져오기</a>
</p>
<table>
    <tr>
        <th>ID</th>
        <th>X좌표</th>
        <th>Y좌표</th>
        <th>조회일자</th>
        <th>비고</th>
    </tr>
    <%
        DB db = new DB();
        List<History> historyList = db.getHistory();
        PrintWriter print = response.getWriter();
        for (History history : historyList) {
            print.write("<tr>");
            print.write("<th>" + history.getId() + "</th>");
            print.write("<th>" + history.getX() + "</th>");
            print.write("<th>" + history.getY() + "</th>");
            print.write("<th>" + history.getCreatedAt() + "</th>");
            print.write("</tr>");
        }

    %>
</table>
</body>
</html>
