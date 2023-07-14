package hello.proxy.postprocessor;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lombok.extern.slf4j.Slf4j;

public class BasicTest {

	@Test
	void basicConfig() {
		ApplicationContext ac = new AnnotationConfigApplicationContext(BasicConfig.class);

		// A는 빈으로 등록된다.
		A beanA = ac.getBean("beanA", A.class);
		beanA.helloA();

		// B는 빈으로 등록되지 않는다.
		assertThatThrownBy(() -> ac.getBean("beanB", B.class))
			.isInstanceOf(NoSuchBeanDefinitionException.class);
	}

	@Slf4j
	@Configuration
	static class BasicConfig {
		@Bean(name = "beanA")
		public A a() {
			return new A();
		}
	}

	@Slf4j
	static class A {
		public void helloA() {
			log.info("hello A");
		}
	}

	@Slf4j
	static class B {
		public void helloB() {
			log.info("hello B");
		}
	}
}
