package studyspringmvc1.hello.basic.request;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@Slf4j
@Controller
public class RequestParamController {

    @RequestMapping("/request-param-v1")
    public void requestParamV1(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String username = request.getParameter("username");
        int age = Integer.parseInt(request.getParameter("age"));
        log.info("username={}, age={}", username, age);
        response.getWriter().write("ok");
    }

    @ResponseBody // 리턴값이 ViewName 이 아니라 Body Message 임.
    @RequestMapping("/request-param-v2")
    public String requestParamV2(
            @RequestParam("username") String memberName,
            @RequestParam("age") String memberAge) {
        log.info("username={}, age={}", memberName, memberAge);
        return "ok";
    }

    @ResponseBody
    @RequestMapping("/request-param-v3")
    public String requestParamV3(
            @RequestParam String username, // Request 파라미터 이름과 변수 이름이 같으면 @RequestParam("xx) 생략 가능
            @RequestParam String age) {
        log.info("username={}, age={}", username, age);
        return "ok";
    }

    @ResponseBody
    @RequestMapping("/request-param-v4")
    public String requestParamV4(String username, int age) { // Request 파라미터 이름과 변수 이름이 같고
        log.info("username={}, age={}", username, age); // 단순타입(String, int, Integer) 이면 @RequestParam 생략 가능
        return "ok";
    }

    @ResponseBody
    @RequestMapping("/request-param-required")
    public String requestParamRequired(
            @RequestParam(required = true) String username, // 필수 여부를 지정할 수 있음. true이면 꼭 있어야하고, false이면 없어도됨. 기본값은 true임.
            @RequestParam(required = false) Integer age) { // 기본값이 null 이므로 타입 변경필요 (int -> Integer), int 형으로 하면 500 error 발생
        log.info("username={}, age={}", username, age);
        return "ok";
    }

    @ResponseBody
    @RequestMapping("/request-param-default")
    public String requestParamDefault(
            @RequestParam(required = true, defaultValue = "guest") String username, // 값이 없을 때 항상 defaultValue가 됨.
            @RequestParam(required = false, defaultValue = "1") Integer age) { // 항상 기본값이 있기 떄문에 required의 의미가 없음. 값이 빈문자로 들어올때도 defaultValue가됨.
        log.info("username={}, age={}", username, age);
        return "ok";
    }

    @ResponseBody
    @RequestMapping("/request-param-map")
    public String requestParamParamMap(
            @RequestParam Map<String, Object> paramMap) { // Map, MultiValueMap, 으로 파라미터값 받는 것 가능
        log.info("username={}, age={}", paramMap.get("username"), paramMap.get("age"));
        return "ok";
    }
}
