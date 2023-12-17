package study.infowifi;

import study.infowifi.db.DB;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "FindWifi", value = "/find-wifi")
public class FindWifi extends HttpServlet {
    private static long historyId = 1;
    private DB db = new DB();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String reqLAN = request.getParameter("LAT");
        String reqLNT = request.getParameter("LNT");
        // 위치 가져오기
        db.wifiSearch(reqLAN, reqLNT);
        // 위치 조회 기록 저장
        db.writeHistory(historyId, reqLAN, reqLNT);
        historyId += 1;
    }
}
