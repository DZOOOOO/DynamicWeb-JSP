<%@ page import="java.io.PrintWriter" %>
<%@ page import="study.infowifi.db.DB" %>
<%@ page import="study.infowifi.bookMark.BookMark" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    PrintWriter print = response.getWriter();
    // 저장된 북마크 불러오기
    DB db = new DB();
    List<BookMark> bookMarkList = db.bookMarkList();
%>
<html>
<head>
    <title>와이파이 정보 구하기</title>
    <style>
        table {
            border-collapse: collapse;
        }

        th {
            background: mediumseagreen;
            color: white;
        }

        th, td {
            border: 1px solid black;
            text-align: center;
            height: 45px;
            padding: 10px;
        }
    </style>
</head>
<body>
<h1>북마크 그룹 수정</h1>
<div class="menu-bar">
    <p>
        <a href="#">홈</a> | <a href="history.jsp">위치 히스토리 목록</a> | <a href="hello-servlet">Open API 와이파이 정보 가져오기</a> |
        <a href="#">북마크 보기</a> | <a href="bookmark-group-edit.jsp">북마크 그룹 관리</a>
    </p>
</div>
<table border="1">
    <%
        print.write("<tr>");
        print.write("<th>ID</th>");
        print.write("<th>북마크 이름</th>");
        print.write("<th>순서</th>");
        print.write("<th>등록일자</th>");
        print.write("<th>수정일자</th>");
        print.write("<th>비고</th>");
        print.write("</tr>");
        for (BookMark bookMark : bookMarkList) {
            print.write("<tr>");
            print.write("<th>" + bookMark.getId() + "</th>");
            print.write("<th>" + bookMark.getBookMarkName() + "</th>");
            print.write("<th>" + bookMark.getTurn() + "</th>");
            print.write("<th>" + bookMark.getCreatedAt() + "</th>");
            print.write("<th>" + bookMark.getUpdatedAt() + "</th>");
            print.write("<th><a href='bookmark-group-edit.jsp?id='" + bookMark.getId() + ">수정</a> <a href='book-mark?id=' " + bookMark.getId() + ">삭제</a></th>");
            print.write("</tr>");
        }
    %>
</table>
</body>
</html>
