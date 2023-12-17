<%@ page import="study.infowifi.wifi.SeoulWifi" %>
<%@ page import="study.infowifi.db.DB" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>와이파이 정보 구하기</title>
    <style>
        table {
            border-collapse: collapse;
        }

        th {
            width: 100%;
        }

        .info-name {
            color: white;
            background: mediumseagreen;
        }
    </style>
</head>
<body>
<%
    // 와이파이 관리번호로 WIFI 정보 조회
    String MGR = request.getParameter("MGR");
    DB db = new DB();
    SeoulWifi wifi = db.findWifi(MGR);
%>
<h1>와이파이 정보 구하기</h1>
<div class="menu-bar">
    <p>
        <a href="#">홈</a> | <a href="history.jsp">위치 히스토리 목록</a> | <a href="hello-servlet">Open API 와이파이 정보 가져오기</a> |
        <a href="#">북마크 보기</a> | <a href="bookmark-group-edit.jsp">북마크 그룹 관리</a>
    </p>
</div>
<div>
    <select name="북마크 그룹 이름 선택">
        <option value=""></option>
    </select>
</div>
<div>
    <button>북마크 추가하기</button>
</div>
<table border="1">
    <tr>
        <th class="info-name">관리번호</th>
        <th><%=wifi.getX_SWIFI_MGR_NO()%>
        </th>
    </tr>
    <tr>
        <th class="info-name">자치구</th>
        <th><%=wifi.getX_SWIFI_WRDOFC()%>
        </th>
    </tr>
    <tr>
        <th class="info-name">와이파이명</th>
        <th><%=wifi.getX_SWIFI_MAIN_NM()%>
        </th>
    </tr>
    <tr>
        <th class="info-name">도로명주소</th>
        <th><%=wifi.getX_SWIFI_ADRES1()%>
        </th>
    </tr>
    <tr>
        <th class="info-name">상세주소</th>
        <th><%=wifi.getX_SWIFI_ADRES2()%>
        </th>
    </tr>
    <tr>
        <th class="info-name">설치위치(층)</th>
        <th><%=wifi.getX_SWIFI_INSTL_FLOOR()%>
        </th>
    </tr>
    <tr>
        <th class="info-name">설치유형</th>
        <th><%=wifi.getX_SWIFI_INSTL_TY()%>
        </th>
    </tr>
    <tr>
        <th class="info-name">설치기관</th>
        <th><%=wifi.getX_SWIFI_INSTL_MBY()%>
        </th>
    </tr>
    <tr>
        <th class="info-name">서비스구분</th>
        <th><%=wifi.getX_SWIFI_SVC_SE()%>
        </th>
    </tr>
    <tr>
        <th class="info-name">망종류</th>
        <th><%=wifi.getX_SWIFI_CMCWR()%>
        </th>
    </tr>
    <tr>
        <th class="info-name">설치년도</th>
        <th><%=wifi.getX_SWIFI_CNSTC_YEAR()%>
        </th>
    </tr>
    <tr>
        <th class="info-name">실내외구분</th>
        <th><%=wifi.getX_SWIFI_INOUT_DOOR()%>
        </th>
    </tr>
    <tr>
        <th class="info-name">WIFI접속환경</th>
        <th><%=wifi.getX_SWIFI_REMARS3()%>
        </th>
    </tr>
    <tr>
        <th class="info-name">X좌표</th>
        <th><%=wifi.getLAT()%>
        </th>
    </tr>
    <tr>
        <th class="info-name">Y좌표</th>
        <th><%=wifi.getLNT()%>
        </th>
    </tr>
    <tr>
        <th class="info-name">작업일자</th>
        <th><%=wifi.getWORK_DTTM()%>
        </th>
    </tr>
</table>

</body>
</html>
