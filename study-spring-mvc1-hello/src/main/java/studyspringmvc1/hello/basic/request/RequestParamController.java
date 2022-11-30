package studyspringmvc1.hello.basic.request;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import studyspringmvc1.hello.basic.HelloData;

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

    /**
     *  ModelAttribute 실행 순서
     *  1. HelloData 객체를 생성
     *  2. 요청 파라미터의 이름으로 HelloData 객체의 프로퍼티를 찾는다.
     *  3. 해당 프로퍼티의 setter를 호출해서 파라미터의 값을 바인딩한다.
     *  예) localhost:8080/model-attribute-v1?username=kim
     *  파라미터 이름(=username)을 바탕으로 setter(=setUsername())를 찾아서 호출하면서 값(=kim)을 입력한다.
     */
    @ResponseBody
    @RequestMapping("/model-attribute-v1")
    public String modelAttributeV1(@ModelAttribute HelloData helloData) {
        log.info("username={}, age={}", helloData.getUsername(), helloData.getAge());
        log.info("helloData={}", helloData);
        return "ok";
    }

    /**
     *  ModelAttribute 생략이 가능하다.
     *  RequestParam 도 생략이 가능하므로 혼란이 발생할 수 있다.
     *  스프링은 다음과 같은 규칙을 적용한다.
     *  String, int, Integer 같은 단순 타입 = @RequestParam 생략됐다고 여김
     *  나머지 = @ModelAttribute 생략됐다고 여 (argument resolver 로 지정해둔 타입 외, 예) HttpServletResponse)
     */
    @ResponseBody
    @RequestMapping("/model-attribute-v2")
    public String modelAttributeV2(HelloData helloData) { // @ModelAttribute 생략 가능
        log.info("username={}, age={}", helloData.getUsername(), helloData.getAge());
        log.info("helloData={}", helloData);
        return "ok";
    }
}
