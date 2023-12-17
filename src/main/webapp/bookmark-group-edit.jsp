<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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
<form action="book-mark-edit?<%=request.getParameter("id")%>" method="post">
    <table border="1">
        <tr>
            <td>북마크 이름</td>
            <td>
                <input type="text" id="bookmark-name">
            </td>
        </tr>
        <tr>
            <td>순서</td>
            <td>
                <input type="text" id="bookmark-turn">
            </td>
        </tr>
        <tr>
            <td>
                <a href="/">돌아가기</a> | <button>수정</button>
            </td>
        </tr>
    </table>
</form>

</body>
</html>
