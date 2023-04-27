package hello.jdbc.service;

import hello.jdbc.domain.Member;
import hello.jdbc.repository.MemberRepositoryV1;
import lombok.RequiredArgsConstructor;

import java.sql.SQLException;

@RequiredArgsConstructor
public class MemberServiceV1 {

    private final MemberRepositoryV1 memberRepository;

    public void accountTransfer(String fromId, String toId, int money) throws SQLException {
        Member fromMember = memberRepository.findById(fromId);
        Member toMember = memberRepository.findById(toId);

        memberRepository.update(fromId, fromMember.getMoney() - money);

        // 학습을 위한 인위적인 예외 발생
        if (toMember.getMemberId().equals("ex")) {
            throw new IllegalStateException("계좌이체 예외 발생");
        }

        memberRepository.update(toId, toMember.getMoney() + money);
    }
}
