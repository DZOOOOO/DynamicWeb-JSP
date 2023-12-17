package study.infowifi.bookMark.servlet;

import study.infowifi.bookMark.BookMark;
import study.infowifi.db.DB;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "BookMarkServlet", value = "/book-mark")
public class BookMarkServlet extends HttpServlet {

    DB db = new DB();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id = request.getParameter("id");
        db.deleteBookMark(Long.parseLong(id));
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String bookMarkName = request.getParameter("bookmark-name");
        String bookMarkTurn = request.getParameter("bookmark-turn");
        BookMark bookMark = new BookMark();
        bookMark.setBookMarkName(bookMarkName);
        bookMark.setTurn(Long.valueOf(bookMarkTurn));
        db.createBookMark(bookMark);
    }
}
