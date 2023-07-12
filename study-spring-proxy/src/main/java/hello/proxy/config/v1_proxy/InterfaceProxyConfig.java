package hello.proxy.config.v1_proxy;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import hello.proxy.app.v1.OrderControllerV1;
import hello.proxy.app.v1.OrderControllerV1Impl;
import hello.proxy.app.v1.OrderRepositoryV1;
import hello.proxy.app.v1.OrderRepositoryV1Impl;
import hello.proxy.app.v1.OrderServiceV1;
import hello.proxy.app.v1.OrderServiceV1Impl;
import hello.proxy.config.v1_proxy.interface_proxy.OrderControllerInterfaceProxy;
import hello.proxy.config.v1_proxy.interface_proxy.OrderRepositoryInterfaceProxy;
import hello.proxy.config.v1_proxy.interface_proxy.OrderServiceInterfaceProxy;
import hello.proxy.trace.logtrace.LogTrace;

@Configuration
public class InterfaceProxyConfig {
	@Bean
	public OrderControllerV1 orderController(LogTrace logTrace, OrderServiceV1 orderService) {
		OrderControllerV1Impl controllerImpl = new OrderControllerV1Impl(orderService);
		return new OrderControllerInterfaceProxy(controllerImpl, logTrace);
	}

	@Bean
	public OrderServiceV1 orderService(LogTrace logTrace, OrderRepositoryV1 orderRepository) {
		OrderServiceV1Impl orderServiceImpl = new OrderServiceV1Impl(orderRepository);
		return new OrderServiceInterfaceProxy(orderServiceImpl, logTrace);
	}

	@Bean
	public OrderRepositoryV1 orderRepository(LogTrace logTrace) {
		OrderRepositoryV1Impl repositoryImpl = new OrderRepositoryV1Impl();
		return new OrderRepositoryInterfaceProxy(repositoryImpl, logTrace);
	}
}
