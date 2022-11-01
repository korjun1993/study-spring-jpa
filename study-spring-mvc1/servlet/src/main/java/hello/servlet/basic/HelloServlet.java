package hello.servlet.basic;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "helloServlet", urlPatterns = "/hello") // name과 urlPatterns 겹치면 안된다.
public class HelloServlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("HelloServlet.service");
        System.out.println("request = " + request);
        System.out.println("response = " + response);
        
        String username = request.getParameter("username");
        System.out.println("username = " + username);

        // header 정보
        response.setContentType("text/plain");
        response.setCharacterEncoding("utf-8");

        // body에 써짐
        response.getWriter().write("hello " + username);
    }
}
