package study.infowifi.db;

import study.infowifi.bookMark.BookMark;
import study.infowifi.history.History;
import study.infowifi.wifi.SeoulWifi;
import study.infowifi.wifi.WifiData;

import java.awt.print.Book;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class DB {
    // 1. 접속에 필요한 데이터 준비
    String url = "jdbc:mysql://localhost:3306/seoul_wifi";
    String dbUserId = "root";
    String dbPassword = "ehdwn123!@";

    Connection connection = null;
    PreparedStatement preparedStatement = null;
    ResultSet rs = null;

    // 서울시 공공 와이파이 위치 정보 저장 (중복은 무시됨)
    public void dataInsert(WifiData wifiData) {
        try {
            // 2. 드라이버 로드
            Class.forName("com.mysql.cj.jdbc.Driver");

            // 3. 커넥션 객체 생성
            connection = DriverManager.getConnection(url, dbUserId, dbPassword);
            if (connection != null) {
                System.out.println("DB 연결 성공");
            }
            // PK 중복시 에러없이 데이터를 넣지 않는다.
            String sql = "INSERT IGNORE INTO wifi () VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

            // 4. 스테이트먼트 객체 생성
            preparedStatement = connection.prepareStatement(sql);
            for (int i = 0; i < 1000; i++) {
                for (int j = 1; j <= 16; j++) {
                    preparedStatement.setString(j, wifiData.getTbPublicWifiInfo().getRow()[i].getX_SWIFI_MGR_NO());
                    preparedStatement.setString(j, wifiData.getTbPublicWifiInfo().getRow()[i].getX_SWIFI_WRDOFC());
                    preparedStatement.setString(j, wifiData.getTbPublicWifiInfo().getRow()[i].getX_SWIFI_MAIN_NM());
                    preparedStatement.setString(j, wifiData.getTbPublicWifiInfo().getRow()[i].getX_SWIFI_ADRES1());
                    preparedStatement.setString(j, wifiData.getTbPublicWifiInfo().getRow()[i].getX_SWIFI_ADRES2());
                    preparedStatement.setString(j, wifiData.getTbPublicWifiInfo().getRow()[i].getX_SWIFI_INSTL_FLOOR());
                    preparedStatement.setString(j, wifiData.getTbPublicWifiInfo().getRow()[i].getX_SWIFI_INSTL_TY());
                    preparedStatement.setString(j, wifiData.getTbPublicWifiInfo().getRow()[i].getX_SWIFI_INSTL_MBY());
                    preparedStatement.setString(j, wifiData.getTbPublicWifiInfo().getRow()[i].getX_SWIFI_SVC_SE());
                    preparedStatement.setString(j, wifiData.getTbPublicWifiInfo().getRow()[i].getX_SWIFI_CMCWR());
                    preparedStatement.setString(j, wifiData.getTbPublicWifiInfo().getRow()[i].getX_SWIFI_CNSTC_YEAR());
                    preparedStatement.setString(j, wifiData.getTbPublicWifiInfo().getRow()[i].getX_SWIFI_INOUT_DOOR());
                    preparedStatement.setString(j, wifiData.getTbPublicWifiInfo().getRow()[i].getX_SWIFI_REMARS3());
                    preparedStatement.setString(j, wifiData.getTbPublicWifiInfo().getRow()[i].getLAT());
                    preparedStatement.setString(j, wifiData.getTbPublicWifiInfo().getRow()[i].getLNT());
                    preparedStatement.setString(j, wifiData.getTbPublicWifiInfo().getRow()[i].getWORK_DTTM());
                }
            }

            // 5. 쿼리 실행
            int affected = preparedStatement.executeUpdate();
            if (affected > 0) {
                System.out.println("저장 성공");
            } else {
                System.out.println("저장 실패");
            }

        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        } finally {
            // 6. 객채 연결 해제 (close)
            try {
                if (preparedStatement != null && !preparedStatement.isClosed()) {
                    preparedStatement.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                if (connection != null && !connection.isClosed()) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // 내 위치 근처에 있는 공공 Wifi 위치 조회
    public List<SeoulWifi> wifiSearch(String reqLAT, String reqLNT) {
        List<SeoulWifi> seoulWifiList = new ArrayList<>();

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            connection = DriverManager.getConnection(url, dbUserId, dbPassword);
            if (connection != null) {
                System.out.println("DB 연결 성공");
            }

            int lat = Integer.parseInt(reqLAT);
            int lnt = Integer.parseInt(reqLNT);

            // LAT 와 LNT 를 이용한 근처 위치 측정 (오차 +1)
            String sql = "SELECT * FROM wifi WHERE LAT BETWEEN" + lat + "AND" + (lat + 1) + "AND LNT BETWEEN" + lnt + "AND" + (lnt + 1);
            preparedStatement = connection.prepareStatement(sql);
            rs = preparedStatement.executeQuery();

            // 데이터를 받고 페이지 서버에 넘기기.
            while (rs.next()) {
                String X_SWIFI_MGR_NO = rs.getNString("X_SWIFI_MGR_NO");
                String X_SWIFI_WRDOFC = rs.getNString("X_SWIFI_WRDOFC");
                String X_SWIFI_MAIN_NM = rs.getNString("X_SWIFI_MAIN_NM");
                String X_SWIFI_ADRES1 = rs.getNString("X_SWIFI_ADRES1");
                String X_SWIFI_ADRES2 = rs.getNString("X_SWIFI_ADRES2");
                String X_SWIFI_INSTL_FLOOR = rs.getNString("X_SWIFI_INSTL_FLOOR");
                String X_SWIFI_INSTL_TY = rs.getNString("X_SWIFI_INSTL_TY");
                String X_SWIFI_INSTL_MBY = rs.getNString("X_SWIFI_INSTL_MBY");
                String X_SWIFI_SVC_SE = rs.getNString("X_SWIFI_SVC_SE");
                String X_SWIFI_CMCWR = rs.getNString("X_SWIFI_CMCWR");
                String X_SWIFI_CNSTC_YEAR = rs.getNString("X_SWIFI_CNSTC_YEAR");
                String X_SWIFI_INOUT_DOOR = rs.getNString("X_SWIFI_INOUT_DOOR");
                String X_SWIFI_REMARS3 = rs.getNString("X_SWIFI_REMARS3");
                String LAT = rs.getNString("LAT");
                String LNT = rs.getNString("LNT");
                String WORK_DTTM = rs.getNString("WORK_DTTM");

                SeoulWifi seoulWifi = new SeoulWifi();
                seoulWifi.setX_SWIFI_MGR_NO(X_SWIFI_MGR_NO);
                seoulWifi.setX_SWIFI_WRDOFC(X_SWIFI_WRDOFC);
                seoulWifi.setX_SWIFI_MAIN_NM(X_SWIFI_MAIN_NM);
                seoulWifi.setX_SWIFI_ADRES1(X_SWIFI_ADRES1);
                seoulWifi.setX_SWIFI_ADRES2(X_SWIFI_ADRES2);
                seoulWifi.setX_SWIFI_INSTL_FLOOR(X_SWIFI_INSTL_FLOOR);
                seoulWifi.setX_SWIFI_INSTL_TY(X_SWIFI_INSTL_TY);
                seoulWifi.setX_SWIFI_INSTL_MBY(X_SWIFI_INSTL_MBY);
                seoulWifi.setX_SWIFI_SVC_SE(X_SWIFI_SVC_SE);
                seoulWifi.setX_SWIFI_CMCWR(X_SWIFI_CMCWR);
                seoulWifi.setX_SWIFI_CNSTC_YEAR(X_SWIFI_CNSTC_YEAR);
                seoulWifi.setX_SWIFI_INOUT_DOOR(X_SWIFI_INOUT_DOOR);
                seoulWifi.setX_SWIFI_REMARS3(X_SWIFI_REMARS3);
                seoulWifi.setX_SWIFI_REMARS3(X_SWIFI_REMARS3);
                seoulWifi.setLAT(LAT);
                seoulWifi.setLNT(LNT);
                seoulWifi.setWORK_DTTM(WORK_DTTM);

                seoulWifiList.add(seoulWifi);
            }

        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (preparedStatement != null && !preparedStatement.isClosed()) {
                    preparedStatement.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                if (rs != null && !rs.isClosed()) {
                    rs.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            try {
                if (connection != null && !connection.isClosed()) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return seoulWifiList;
    }

    // 와이파이 단건 조회
    public SeoulWifi findWifi(String MGR_NO) {
        SeoulWifi find = new SeoulWifi();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            connection = DriverManager.getConnection(url, dbUserId, dbPassword);
            if (connection != null) {
                System.out.println("DB 연결 성공");
            }

            // 관리번호로 데이터 불러오기
            String sql = "SELECT * FROM wifi where X_SWIFI_MGR_NO =" + MGR_NO;
            preparedStatement = connection.prepareStatement(sql);
            rs = preparedStatement.executeQuery();

            // 데이터를 받고 페이지 서버에 넘기기.
            while (rs.next()) {
                String X_SWIFI_MGR_NO = rs.getNString("X_SWIFI_MGR_NO");
                String X_SWIFI_WRDOFC = rs.getNString("X_SWIFI_WRDOFC");
                String X_SWIFI_MAIN_NM = rs.getNString("X_SWIFI_MAIN_NM");
                String X_SWIFI_ADRES1 = rs.getNString("X_SWIFI_ADRES1");
                String X_SWIFI_ADRES2 = rs.getNString("X_SWIFI_ADRES2");
                String X_SWIFI_INSTL_FLOOR = rs.getNString("X_SWIFI_INSTL_FLOOR");
                String X_SWIFI_INSTL_TY = rs.getNString("X_SWIFI_INSTL_TY");
                String X_SWIFI_INSTL_MBY = rs.getNString("X_SWIFI_INSTL_MBY");
                String X_SWIFI_SVC_SE = rs.getNString("X_SWIFI_SVC_SE");
                String X_SWIFI_CMCWR = rs.getNString("X_SWIFI_CMCWR");
                String X_SWIFI_CNSTC_YEAR = rs.getNString("X_SWIFI_CNSTC_YEAR");
                String X_SWIFI_INOUT_DOOR = rs.getNString("X_SWIFI_INOUT_DOOR");
                String X_SWIFI_REMARS3 = rs.getNString("X_SWIFI_REMARS3");
                String LAT = rs.getNString("LAT");
                String LNT = rs.getNString("LNT");
                String WORK_DTTM = rs.getNString("WORK_DTTM");

                find.setX_SWIFI_MGR_NO(X_SWIFI_MGR_NO);
                find.setX_SWIFI_WRDOFC(X_SWIFI_WRDOFC);
                find.setX_SWIFI_MAIN_NM(X_SWIFI_MAIN_NM);
                find.setX_SWIFI_ADRES1(X_SWIFI_ADRES1);
                find.setX_SWIFI_ADRES2(X_SWIFI_ADRES2);
                find.setX_SWIFI_INSTL_FLOOR(X_SWIFI_INSTL_FLOOR);
                find.setX_SWIFI_INSTL_TY(X_SWIFI_INSTL_TY);
                find.setX_SWIFI_INSTL_MBY(X_SWIFI_INSTL_MBY);
                find.setX_SWIFI_SVC_SE(X_SWIFI_SVC_SE);
                find.setX_SWIFI_CMCWR(X_SWIFI_CMCWR);
                find.setX_SWIFI_CNSTC_YEAR(X_SWIFI_CNSTC_YEAR);
                find.setX_SWIFI_INOUT_DOOR(X_SWIFI_INOUT_DOOR);
                find.setX_SWIFI_REMARS3(X_SWIFI_REMARS3);
                find.setX_SWIFI_REMARS3(X_SWIFI_REMARS3);
                find.setLAT(LAT);
                find.setLNT(LNT);
                find.setWORK_DTTM(WORK_DTTM);
            }

        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (preparedStatement != null && !preparedStatement.isClosed()) {
                    preparedStatement.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                if (rs != null && !rs.isClosed()) {
                    rs.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            try {
                if (connection != null && !connection.isClosed()) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return find;
    }

    // 히스토리 데이터
    public void writeHistory(long historyId, String reqLAT, String reqLNT) {
        try {
            // 2. 드라이버 로드
            Class.forName("com.mysql.cj.jdbc.Driver");

            // 3. 커넥션 객체 생성
            connection = DriverManager.getConnection(url, dbUserId, dbPassword);
            if (connection != null) {
                System.out.println("DB 연결 성공");
            }
            // PK 중복시 에러없이 데이터를 넣지 않는다.
            String sql = "INSERT IGNORE INTO history () VALUES (?,?, ?, ?)";

            // 4. 스테이트먼트 객체 생성
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, String.valueOf(historyId));
            preparedStatement.setString(2, reqLAT);
            preparedStatement.setString(3, reqLNT);
            preparedStatement.setString(4, LocalDateTime.now().toString());
            // 5. 쿼리 실행
            int affected = preparedStatement.executeUpdate();
            if (affected > 0) {
                System.out.println("저장 성공");
            } else {
                System.out.println("저장 실패");
            }

        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        } finally {
            // 6. 객채 연결 해제 (close)
            try {
                if (preparedStatement != null && !preparedStatement.isClosed()) {
                    preparedStatement.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                if (connection != null && !connection.isClosed()) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // 히스토리 데이터 조회
    public List<History> getHistory() {
        List<History> historyList = new ArrayList<>();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            connection = DriverManager.getConnection(url, dbUserId, dbPassword);
            if (connection != null) {
                System.out.println("DB 연결 성공");
            }

            // LAT 와 LNT 를 이용한 근처 위치 측정 (오차 +1)
            String sql = "SELECT '*' FROM history";
            preparedStatement = connection.prepareStatement(sql);
            rs = preparedStatement.executeQuery();

            // 데이터를 받고 페이지 서버에 넘기기.
            while (rs.next()) {
                String LAT = rs.getNString("LAT");
                String LNT = rs.getNString("LNT");
                String createdAt = rs.getNString("createdAt");

                History history = new History();
                history.setX(LAT);
                history.setY(LNT);
                history.setCreatedAt(LocalDateTime.parse(createdAt));

                historyList.add(history);
            }

        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (preparedStatement != null && !preparedStatement.isClosed()) {
                    preparedStatement.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                if (rs != null && !rs.isClosed()) {
                    rs.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            try {
                if (connection != null && !connection.isClosed()) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return historyList;
    }

    // 저장된 북마크 전체 조회
    public List<BookMark> bookMarkList() {
        List<BookMark> bookMarkList = new ArrayList<>();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(url, dbUserId, dbPassword);
            if (connection != null) {
                System.out.println("DB 연결 성공");
            }
            String sql = "SELECT * FROM book_mark";
            preparedStatement = connection.prepareStatement(sql);
            rs = preparedStatement.executeQuery();

            // 수정일자는 따로 안받아도 된다.
            while (rs.next()) {
                String ID = rs.getNString("id");
                String bookMarkName = rs.getNString("book_mark_name");
                String turn = rs.getNString("turn");
                String createdAt = rs.getNString("createdAt");

                BookMark bookMark = new BookMark();
                bookMark.setId(Long.parseLong(ID));
                bookMark.setBookMarkName(bookMarkName);
                bookMark.setTurn(Long.valueOf(turn));
                bookMark.setCreatedAt(LocalDateTime.parse(createdAt));

                bookMarkList.add(bookMark);
            }

        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (preparedStatement != null && !preparedStatement.isClosed()) {
                    preparedStatement.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                if (rs != null && !rs.isClosed()) {
                    rs.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                if (connection != null && !connection.isClosed()) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return bookMarkList;
    }

    // 북마크 생성
    public void createBookMark(BookMark bookMark) {
        try {
            // 2. 드라이버 로드
            Class.forName("com.mysql.cj.jdbc.Driver");

            // 3. 커넥션 객체 생성
            connection = DriverManager.getConnection(url, dbUserId, dbPassword);
            if (connection != null) {
                System.out.println("DB 연결 성공");
            }

            // 북마크 정보
            long id = bookMark.getId();
            String bookMarkName = bookMark.getBookMarkName();
            long turn = bookMark.getTurn();

            String sql = "INSERT INTO book_mark () VALUES (?,?,?)";

            // 4. 스테이트먼트 객체 생성
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, String.valueOf(id));
            preparedStatement.setString(2, bookMarkName);
            preparedStatement.setString(3, String.valueOf(turn));

            // 5. 쿼리 실행
            int affected = preparedStatement.executeUpdate();
            if (affected > 0) {
                System.out.println("저장 성공");
            } else {
                System.out.println("저장 실패");
            }

        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        } finally {
            // 6. 객채 연결 해제 (close)
            try {
                if (preparedStatement != null && !preparedStatement.isClosed()) {
                    preparedStatement.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                if (connection != null && !connection.isClosed()) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // 북마크 수정
    public void updateBookMark(long id,String editName, long editturn) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(url, dbUserId, dbPassword);
            if (connection != null) {
                System.out.println("DB 연결 성공");
            }
            String sql = "UPDATE book_mark set book_mark_name =" + editName + "turn = " + editturn + "where = " + id;
            preparedStatement = connection.prepareStatement(sql);
            int affected = preparedStatement.executeUpdate();

            if (affected > 0) {
                System.out.println("수정 완료");
            } else {
                System.out.println("수정 실패");
            }

        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (preparedStatement != null && !preparedStatement.isClosed()) {
                    preparedStatement.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                if (connection != null && !connection.isClosed()) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // 북마크 삭제
    public void deleteBookMark(long id) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(url, dbUserId, dbPassword);
            if (connection != null) {
                System.out.println("DB 연결 성공");
            }
            String sql = "DELETE FROM book_mark where =" + id;
            preparedStatement = connection.prepareStatement(sql);
            int affected =preparedStatement.executeUpdate();
            if (affected > 0) {
                System.out.println("삭제 성공");
            } else {
                System.out.println("삭제 실패");
            }

        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (preparedStatement != null && !preparedStatement.isClosed()) {
                    preparedStatement.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                if (connection != null && !connection.isClosed()) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
