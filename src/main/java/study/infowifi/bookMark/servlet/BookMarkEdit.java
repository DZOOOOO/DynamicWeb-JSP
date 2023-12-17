package study.infowifi.bookMark.servlet;

import study.infowifi.db.DB;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "BookMarkEdit", value = "/book-mark-edit")
public class BookMarkEdit extends HttpServlet {
    DB db = new DB();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String editName = request.getParameter("bookmark-name");
        String editTurn = request.getParameter("bookmark-turn");
        String bookMarkId = request.getParameter("id");
        db.updateBookMark(Long.parseLong(bookMarkId), editName, Long.parseLong(editTurn));
    }
}
