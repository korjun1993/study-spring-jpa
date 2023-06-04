package hello.jdbc.service;

import hello.jdbc.domain.Member;
import hello.jdbc.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

/**
 * 트랜잭션 AOP 적용
 */
@Slf4j
@RequiredArgsConstructor
public class MemberServiceV4 {
    private final MemberRepository memberRepository;

    @Transactional
    public void accountTransfer(String fromId, String toId, int money) {
        businessLogic(fromId, toId, money);
    }

    private void businessLogic(String fromId, String toId, int money) {
        Member fromMember = memberRepository.findById(fromId);
        Member toMember = memberRepository.findById(toId);

        memberRepository.update(fromId, fromMember.getMoney() - money);
        validation(toMember);
        memberRepository.update(toId, toMember.getMoney() + money);
    }

    private static void validation(Member toMember) {
        // 학습을 위한 인위적인 예외 발생
        if (toMember.getMemberId().equals("ex")) {
            throw new IllegalStateException("계좌이체 예외 발생");
        }
    }
}
