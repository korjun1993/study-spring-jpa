package hello.jdbc.service;

import hello.jdbc.domain.Member;
import hello.jdbc.repository.MemberRepositoryV3;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * 트랜잭션 매니저 적용
 */
@Slf4j
@RequiredArgsConstructor
public class MemberServiceV3_1 {
    private final MemberRepositoryV3 memberRepository;
    private final PlatformTransactionManager transactionManager;

    public void accountTransfer(String fromId, String toId, int money) {

        // 트랜잭션 시작
        TransactionStatus status = transactionManager.getTransaction(new DefaultTransactionDefinition());

        try {
            // 비즈니스 로직
            businessLogic(fromId, toId, money);

            // 성공시 커밋
            transactionManager.commit(status);
        } catch (Exception e) {

            // 실패시 롤백
            transactionManager.rollback(status);
            throw new IllegalStateException(e);
        }

        // 트랜잭션 매니저가 알아서 커밋 또는 롤백시 connection 을 반납한다.
        // 우리가 직접할 반납할 필요가 없다.
//        finally {
//            release(con);
//        }
    }

    private void businessLogic(String fromId, String toId, int money) throws SQLException {
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
