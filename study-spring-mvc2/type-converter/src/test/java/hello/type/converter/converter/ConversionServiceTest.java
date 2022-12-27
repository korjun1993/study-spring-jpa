package hello.type.converter.converter;

import hello.type.converter.type.IpPort;
import org.junit.jupiter.api.Test;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.converter.ConverterRegistry;
import org.springframework.core.convert.support.DefaultConversionService;

import static org.assertj.core.api.Assertions.assertThat;

public class ConversionServiceTest {

    @Test
    void conversionService() {
        //등록 (ConverterRegistry 에만 의존, 사용에는 관심없고 등록에만 관심이 있다.)
        ConverterRegistry converterRegistry = new DefaultConversionService();
        converterRegistry.addConverter(new StringToIntegerConverter());
        converterRegistry.addConverter(new IntegerToStringConverter());
        converterRegistry.addConverter(new StringToIpPortConverter());
        converterRegistry.addConverter(new IpPortToStringConverter());


        //사용 (ConversionService 에만 의존, 등록에는 관심없고 사용에만 관심이 있다.)
        //StringToIntegerConverter 호출
        ConversionService conversionService = (ConversionService) converterRegistry;
        Integer result = conversionService.convert("10", Integer.class);
        System.out.println("result = " + result);
        assertThat(result).isEqualTo(10);

        assertThat(conversionService.convert(10, String.class)).isEqualTo("10");
        assertThat(conversionService.convert("127.0.0.1:8080", IpPort.class))
                .isEqualTo(new IpPort("127.0.0.1", 8080));
        assertThat(conversionService.convert(new IpPort("127.0.0.1", 8080), String.class))
                .isEqualTo("127.0.0.1:8080");
    }
}
