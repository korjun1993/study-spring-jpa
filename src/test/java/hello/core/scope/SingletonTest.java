package hello.core.scope;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

public class SingletonTest {
    @Test
    void singletonBeanFind() {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(SingletonBean.class);

        // 출력순서: SingletonBean.init -> find singletonBean1
        // SingletonBean1을 요청하기 전에 Bean이 생성됨을 의미
        System.out.println("find singletonBean1");
        SingletonBean bean1 = ac.getBean(SingletonBean.class);

        System.out.println("find singletonBean2");
        SingletonBean bean2 = ac.getBean(SingletonBean.class);

        System.out.println("bean1 = " + bean1);
        System.out.println("bean2 = " + bean2);

        Assertions.assertThat(bean1).isSameAs(bean2);
        ac.close(); // @PreDestory 호출 안됨

        bean1.destroy(); // @PresDestory 필요할시, 직접 호출

    }

    @Scope("singleton")
    static class SingletonBean {
        @PostConstruct
        public void init() {
            System.out.println("SingletonBean.init");
        }

        @PreDestroy
        public void destroy () {
            System.out.println("SingletonBean.destroy");
        }
    }
}
