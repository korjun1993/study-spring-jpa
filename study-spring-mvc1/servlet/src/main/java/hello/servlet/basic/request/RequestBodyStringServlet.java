package hello.servlet.basic.request;

import org.springframework.util.StreamUtils;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@WebServlet(name = "requestBodyStringServlet", urlPatterns = "/request-body-string")
public class RequestBodyStringServlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ServletInputStream inputStream = request.getInputStream(); // message body 내용을 byte 로 얻을 수 있다.
        String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8); // byte <-> 문자열 변환시 인코딩 방법을 명시해야함.
        System.out.println("messageBody = " + messageBody);
        response.getWriter().write("ok");
    }
}