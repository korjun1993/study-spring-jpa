package studyspringmvc1.hello.basic.request;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;
import java.nio.charset.StandardCharsets;

@Slf4j
@Controller
public class RequestBodyStringController {

    @PostMapping("/request-body-string-v1")
    public void requestBodyStringV1(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ServletInputStream inputStream = request.getInputStream();
        String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8); // byte code 를 문자열로 바꿀 때는 인코딩 방식을 지정해준다.
        log.info("messageBody={}", messageBody);
        response.getWriter().write("ok");
    }

    @PostMapping("/request-body-string-v2")
    public void requestBodyStringV2(InputStream inputStream, Writer responseWriter) throws IOException { // 파라미터로 InputStream, Writer 를 제공받을 수 있다.
        String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);
        log.info("messageBody={}", messageBody);
        responseWriter.write("ok");
    }

    /**
     * HttpEntity : HTTP Header, body 정보를 편리하게 조회
     * Get-QueryParameter방식, Post-Form방식 > @RequestParam, @ModelAttribute 방식으로 값을 꺼내온다.
     * 그외의 경우는 직접 꺼내와야야하고, HttpEntity를 사용하면 편리하다.
     * HttpEntity는 응답에도 사용 가능
     * 메시지 바디 정보를 직접 반환
     * 헤더 정보 포함시킬 수 있음
     * view 를 조회할 수 없음
     * 참고) 스프링 MVC 내부에서 HTTP 메시지 바디를 읽어서 문자나 객체로 변환해서 전달해주는데, 이때 HTTP 메시지 컨버터라는 기능을 사용한다.
     */
    @PostMapping("/request-body-string-v3")
    public HttpEntity<String> requestBodyStringV3(HttpEntity<String> httpEntity) throws IOException {
        String messageBody = httpEntity.getBody();
        log.info("messageBody={}", messageBody);
        return new HttpEntity<>("ok");
    }

    /**
     * HttpEntity 를 상속받은 다음 객체들도 같은 기느을 제공한다.
     * RequestEntity : HttpMethod, Url 정보를 얻어오는 기능이 추가됨.
     * ResponseEntity : HTTP 상태 코드 설정이 가능함.
     */
    @PostMapping("/request-body-string-v4")
    public HttpEntity<String> requestBodyStringV4(RequestEntity<String> requestEntity) throws IOException {
        String messageBody = requestEntity.getBody();
        log.info("messageBody={}", messageBody);
        return new ResponseEntity<String>("ok", HttpStatus.CREATED);
    }

    /**
     * 어노테이션으로 Request Body 부분만 파라미터로 제공받을 수 있음.
     * 요청 파라미터 vs HTTP 메시지 바디
     * 요청 파라미터를 조회하는 기능 : @RequestParam, @ModelAttribute
     * HTTP 메시지 바디를 조회하는 기능 : @RequestBody
     */
    @ResponseBody // Response Body 부분에 반환값을 써주겠다.
    @PostMapping("/request-body-string-v5")
    public String requestBodyStringV5(@RequestBody String messageBody) throws IOException {
        log.info("messageBody={}", messageBody);
        return "ok";
    }
}