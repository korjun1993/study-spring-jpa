package hello.proxy.config.v3_proxyfactory.advice;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import hello.proxy.trace.TraceStatus;
import hello.proxy.trace.logtrace.LogTrace;

public class LogTraceAdvice implements MethodInterceptor {

	private final LogTrace logTrace;

	public LogTraceAdvice(LogTrace logTrace) {
		this.logTrace = logTrace;
	}

	@Override
	public Object invoke(MethodInvocation invocation) throws Throwable {
		TraceStatus status = null;
		try {
			String message = invocation.getMethod().getName() + "," + invocation.getClass().getName() + "()";
			status = logTrace.begin(message);

			// 로직 호출
			Object result = invocation.proceed();
			logTrace.end(status);
			return result;
		} catch (Exception e) {
			logTrace.exception(status, e);
			throw e;
		}
	}
}
