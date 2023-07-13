package hello.proxy.config.v2_dynamic_proxy.handler;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import org.springframework.util.PatternMatchUtils;

import hello.proxy.trace.TraceStatus;
import hello.proxy.trace.logtrace.LogTrace;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class LogTraceFilterHandler implements InvocationHandler {

	private final Object target;
	private final LogTrace logTrace;
	private final String[] patterns;

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		String methodName = method.getName();
		// save, request, reque*, *est
		if (!PatternMatchUtils.simpleMatch(patterns, methodName)) {
			return method.invoke(target, args);
		}

		TraceStatus status = null;
		try {
			String message = method.getDeclaringClass().getSimpleName() + "." + method.getName() + "()";
			status = logTrace.begin(message);

			// 로직 호출
			Object result = method.invoke(target, args);
			logTrace.end(status);
			return result;
		} catch (Exception e) {
			logTrace.exception(status, e);
			throw e;
		}
	}
}
