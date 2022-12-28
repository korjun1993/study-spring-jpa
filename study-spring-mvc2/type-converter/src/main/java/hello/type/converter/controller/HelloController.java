package hello.type.converter.controller;

import hello.type.converter.type.IpPort;
import hello.type.converter.type.Test;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class HelloController {

    @GetMapping("/hello-v1")
    public String helloV1(HttpServletRequest request) {
        String data = request.getParameter("data"); //문자 타입 조회
        Integer intValue = Integer.valueOf(data); //숫자 타입으로 변경
        System.out.println("intValue = " + intValue);
        return "ok";
    }

    @GetMapping("/hello-v2")
    public String helloV2(@RequestParam Integer data) {
        System.out.println("data = " + data);
        return "ok";
    }

    @GetMapping("/ip-port")
    // http://localhost:8080/ip-port?ipPort=127.0.0.1:8080
    public String helloV3(@RequestParam IpPort ipPort) {
        System.out.println("ipPort IP = " + ipPort.getIp());
        System.out.println("ipPort PORT = " + ipPort.getPort());
        return "ok";
    }

    // http://localhost:8080/ip-port-model-attribute?ip=127.0.0.1&port=8080
    // http://localhost:8080/ip-port?ipPort=127.0.0.1:8080
    @GetMapping("/ip-port-model-attribute")
    public String helloV4(@ModelAttribute IpPort ipPort) {
        System.out.println("ipPort IP = " + ipPort.getIp());
        System.out.println("ipPort PORT = " + ipPort.getPort());
        return "ok";
    }

    // http://localhost:8080/test?test=AAA
    // Converter vs 프로퍼티접근을 통한 객체 생성
    @GetMapping("/test")
    public String helloV5(@ModelAttribute Test test) {
        System.out.println("test test = " + test.getTest());
        return "ok";
    }
}
