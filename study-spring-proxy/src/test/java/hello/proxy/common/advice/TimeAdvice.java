package hello.proxy.common.advice;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TimeAdvice implements MethodInterceptor {
	@Override
	public Object invoke(MethodInvocation invocation) throws Throwable {
		log.info("TimeDecorator 실행");

		long startTime = System.currentTimeMillis();

		Object result = invocation.proceed(); // target의 메서드를 수행

		long endTime = System.currentTimeMillis();
		long resultTime = endTime - startTime;

		log.info("TimeDecorator 종료 resultTime={}ms", resultTime);

		return result;
	}
}
