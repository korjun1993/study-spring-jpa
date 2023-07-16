package hello.aop;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import hello.aop.order.OrderRepository;
import hello.aop.order.OrderService;
import hello.aop.order.aop.AspectV1;
import hello.aop.order.aop.AspectV3;
import hello.aop.order.aop.AspectV5Order;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
@Import({AspectV5Order.LogAspect.class, AspectV5Order.TxAspect.class})
public class AopTest {

	@Autowired
	OrderService orderService;

	@Autowired
	OrderRepository orderRepository;

	@Test
	void appInfo() {
		log.info("isAopProxy, orderService={}", AopUtils.isAopProxy(orderService));
		log.info("isAopProxy, orderRepository={}", AopUtils.isAopProxy(orderRepository));
	}

	@Test
	void success() {
		orderService.orderItem("itemA");
	}

	@Test
	void exception() {
		Assertions.assertThatThrownBy(() -> orderService.orderItem("ex"))
			.isInstanceOf(IllegalStateException.class);
	}
}
