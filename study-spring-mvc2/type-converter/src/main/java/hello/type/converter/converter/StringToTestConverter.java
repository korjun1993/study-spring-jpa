package hello.type.converter.converter;

import hello.type.converter.type.Test;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;

@Slf4j
public class StringToTestConverter implements Converter<String, Test> {
    @Override
    public Test convert(String source) {
        log.info("convert source={}", source);
        return new Test(source);
    }
}
