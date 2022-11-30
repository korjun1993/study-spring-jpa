package studyspringmvc1.hello.basic;

import lombok.*;

@Data // @Getter, @Setter, @ToString, @EqualsAndHashCode, @RequiredArgsConstructor 자동 적용
public class HelloData {
    private String username;
    private int age;
}
