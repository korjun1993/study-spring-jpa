package hello.type.converter;

import hello.type.converter.converter.*;
import hello.type.converter.formatter.MyNumberFormatter;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addFormatters(FormatterRegistry registry) {
/**
 *         주석처리: 포맷터보다 컨버터가 우선순위가 높다.
 *         아래 두개 컨버터는 MyNumberFormatter와 같은 대상을 변환하는 컨버터이다.
 *         때문에 MyNumberFormatter를 테스트하기 위해 주석처리
 */
//        registry.addConverter(new StringToIntegerConverter()); //문자를 숫자로
//        registry.addConverter(new IntegerToStringConverter()); //숫자를 문자로
        registry.addConverter(new StringToIpPortConverter());
        registry.addConverter(new IpPortToStringConverter());

        //추가 문자<->숫자
        registry.addFormatter(new MyNumberFormatter());
    }
}
