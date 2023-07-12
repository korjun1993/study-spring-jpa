package hello.proxy.pureProxy.proxy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import hello.proxy.pureProxy.proxy.code.CacheProxy;
import hello.proxy.pureProxy.proxy.code.ProxyPatternClient;
import hello.proxy.pureProxy.proxy.code.RealSubject;

public class ProxyPatternTest {

	@Test
	@DisplayName("데이터를 조회하는데 3초가 걸린다")
	void noProxyTest() {
		RealSubject realSubject = new RealSubject();
		ProxyPatternClient client = new ProxyPatternClient(realSubject);
		client.execute();
		client.execute();
		client.execute();
	}

	@Test
	@DisplayName("첫번째 데이터를 조회하는데 1초가 걸리고, 다음부터는 캐시에 있는 값을 사용해서 총 1초가 걸린다")
	void cacheProxyTest() {
		RealSubject realSubject = new RealSubject();
		CacheProxy cacheProxy = new CacheProxy(realSubject);
		ProxyPatternClient client = new ProxyPatternClient(cacheProxy);
		client.execute(); // 1초:RealSubject
		client.execute(); // 0초:캐시
		client.execute(); // 0초:캐시
	}
}
