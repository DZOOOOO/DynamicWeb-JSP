<%@ page import="study.infowifi.db.DB" %>
<%@ page import="java.util.List" %>
<%@ page import="study.infowifi.wifi.SeoulWifi" %>
<%@ page import="java.io.PrintWriter" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>와이파이 정보 구하기</title>
    <link rel="stylesheet" href="./css/css.css">
</head>
<body>
<h1>와이파이 정보 구하기</h1>
<div class="menu-bar">
    <p>
        <a href="#">홈</a> | <a href="history.jsp">위치 히스토리 목록</a> | <a href="hello-servlet">Open API 와이파이 정보 가져오기</a>
    </p>
</div>
<div class="input-bar">
    <p>
        LAT : <input id="LAT" value="0.0"> , LNT : <input id="LNT" value="0.0">
        <button onclick="findMyLocation()">내 위치 가져오기</button>
        <button onclick="findWifi(LAT, LNT)">근처 WIFI 정보 보기</button>
    </p>
</div>

<table class="info-table" border="1">
    <tr>
        <th>관리번로</th>
        <th>자치구</th>
        <th>와이파이명</th>
        <th>도로명주소</th>
        <th width="300">상세주소</th>
        <th>설치위치(층)</th>
        <th>설치유형</th>
        <th>설치기관</th>
        <th>서비스구분</th>
        <th>망종류</th>
        <th>설치년도</th>
        <th>실내외구분</th>
        <th>WIFI접속환경</th>
        <th>X좌표</th>
        <th>Y좌표</th>
        <th>작업일자</th>
    </tr>
    <%
        DB db = new DB();
        String lat = request.getParameter("lat");
        String lnt = request.getParameter("lnt");
        List<SeoulWifi> list = db.wifiSearch(lat, lnt);
        PrintWriter print = response.getWriter();
        for (SeoulWifi seoulWifi : list) {
            print.write("<tr>");
            print.write("<th>" + seoulWifi.getX_SWIFI_MGR_NO() + "</th>");
            print.write("<th>" + seoulWifi.getX_SWIFI_WRDOFC() + "</th>");
            // 관리 번호로 디테일 페이지 접속
            print.write("<th>" + "<a href=/detail.jsp?" + seoulWifi.getX_SWIFI_MGR_NO() + ">" + seoulWifi.getX_SWIFI_MAIN_NM() + "</a>" + "</th>");
            print.write("<th>" + seoulWifi.getX_SWIFI_ADRES1() + "</th>");
            print.write("<th>" + seoulWifi.getX_SWIFI_ADRES2() + "</th>");
            print.write("<th>" + seoulWifi.getX_SWIFI_INSTL_FLOOR() + "</th>");
            print.write("<th>" + seoulWifi.getX_SWIFI_INSTL_TY() + "</th>");
            print.write("<th>" + seoulWifi.getX_SWIFI_INSTL_MBY() + "</th>");
            print.write("<th>" + seoulWifi.getX_SWIFI_SVC_SE() + "</th>");
            print.write("<th>" + seoulWifi.getX_SWIFI_CMCWR() + "</th>");
            print.write("<th>" + seoulWifi.getX_SWIFI_CNSTC_YEAR() + "</th>");
            print.write("<th>" + seoulWifi.getX_SWIFI_INOUT_DOOR() + "</th>");
            print.write("<th>" + seoulWifi.getX_SWIFI_REMARS3() + "</th>");
            print.write("<th>" + seoulWifi.getLAT() + "</th>");
            print.write("<th>" + seoulWifi.getLNT() + "</th>");
            print.write("<th>" + seoulWifi.getWORK_DTTM() + "</th>");
            print.write("</tr>");
        }
    %>
</table>

<script src="https://cdn.jsdelivr.net/npm/axios/dist/axios.min.js"></script>
<script src="./script/index.js"></script>
</body>
</html>