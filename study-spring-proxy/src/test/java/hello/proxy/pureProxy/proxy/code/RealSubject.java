package hello.proxy.pureProxy.proxy.code;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RealSubject implements Subject {
	@Override
	public String operation() {
		log.info("실제 객체 호출");
		sleep(1000); // 데이터를 조회하는데 1초가 걸림
		return "data";
	}

	private void sleep(int millis) {
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
