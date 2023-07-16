package hello.aop.member;

import org.springframework.stereotype.Component;

import hello.aop.member.annotation.ClassApp;
import hello.aop.member.annotation.MethodApp;

@ClassApp
@Component
public class MemberServiceImpl implements MemberService {

	@Override
	@MethodApp("test value")
	public String hello(String param) {
		return "ok";
	}

	public String internal(String param) {
		return "ok";
	}
}
