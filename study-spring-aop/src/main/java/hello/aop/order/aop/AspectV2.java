package hello.aop.order.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Aspect
public class AspectV2 {

	// hello.aop.order 패키지와 하위 패키지
	@Pointcut("execution(* hello.aop.order..*(..))")
	private void allOrder() {} // pointcut signature: 포인트컷 시그니처로 분리하면 다른 곳에서 재사용하기 좋음

	@Around("allOrder()")
	public Object doLog(ProceedingJoinPoint joinPoint) throws Throwable {
		log.info("[log] {}", joinPoint.getSignature()); // join point 시그니처
		return joinPoint.proceed();
	}
}
