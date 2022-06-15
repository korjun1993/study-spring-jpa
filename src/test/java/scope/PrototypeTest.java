package scope;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

public class PrototypeTest {

    @Test
    void prototypeBeanFind() {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(PrototypeBean.class);

        // 출력순서: find prototypeBean1 -> PrototypeBean.init
        // PrototypeBean을 요청했을 때, Bean이 생성됨을 의미
        System.out.println("find prototypeBean1");
        PrototypeBean bean1 = ac.getBean(PrototypeBean.class);

        System.out.println("find prototypeBean2");
        PrototypeBean bean2 = ac.getBean(PrototypeBean.class);

        System.out.println("bean1 = " + bean1);
        System.out.println("bean2 = " + bean2);

        Assertions.assertThat(bean1).isNotSameAs(bean2);
    }

    @Scope("prototype")
    static class PrototypeBean{
        @PostConstruct
        public void init() {
            System.out.println("PrototypeBean.init");
        }

        @PreDestroy
        public void destroy () {
            System.out.println("PrototypeBean.destroy");
        }

    }

}
