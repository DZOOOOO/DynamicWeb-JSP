package study.infowifi;

import com.google.gson.Gson;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import study.infowifi.db.DB;
import study.infowifi.wifi.WifiData;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

@WebServlet(name = "helloServlet", value = "/hello-servlet")
public class HelloServlet extends HttpServlet {
    OkHttpClient client = new OkHttpClient();
    public String run(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .build();
        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        }
    }

    public void init() {

    }

    // 가져오기 버튼을 누르면 화면과 데이터 저장을 해주는 메서드
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // db 연결
        DB db = new DB();
        WifiData wifiData = null;

        // 한번에 1000개 까지 가능
        for (int i = 0; i < 24; i++) {
            int start = 1 + (1000 * i);
            int end = 1000 * (i + 1);
            String data = run("http://openapi.seoul.go.kr:8088/5474586571647a703131377862576e55/json/TbPublicWifiInfo/" + start + "/" + end + "/");

            // 데이터 정제후 DB INSERT
            Gson gson = new Gson();
            String json = gson.toJson(data);
            wifiData = gson.fromJson(json, WifiData.class);
            db.dataInsert(wifiData);
        }

        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        out.println("<h1>" + wifiData.getTbPublicWifiInfo().getList_total_count() + "개의 데이터가 저장되었습니다." + "</h1>");
        out.println("<a href='/'>홈 으로 가기</a>");
    }

    // WIFI 정보 저장 API
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("== doPost ==");
    }

    protected void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println(getBody(request));
    }

    public static String getBody(HttpServletRequest request) throws IOException {
        String body = null;
        StringBuilder stringBuilder = new StringBuilder();
        BufferedReader bufferedReader = null;

        try {
            InputStream inputStream = request.getInputStream();
            if (inputStream != null) {
                bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                char[] charBuffer = new char[128];
                int bytesRead = -1;
                while ((bytesRead = bufferedReader.read(charBuffer)) > 0) {
                    stringBuilder.append(charBuffer, 0, bytesRead);
                }
            }
        } catch (IOException ex) {
            throw ex;
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException ex) {
                    throw ex;
                }
            }
        }

        body = stringBuilder.toString();
        return body;
    }

    public void destroy() {
    }
}