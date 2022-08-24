package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

@RunWith(SpringRunner.class) // 스프링으로 테스트 실시
@SpringBootTest // 스프링부트 컨테이너 띄워서 테스트 실시. 없으면 Autowired 전부 실패함
@Transactional // 기본적으로 Rollback 처리를 함
public class MemberServiceTest {

    @Autowired
    MemberService memberService;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    EntityManager em;

    @Test
//    @Rollback(false) // 롤백X
    public void 회원가입() throws Exception {
        // given
        Member member = new Member();
        member.setName("kim");

        // when
        Long saveId = memberService.join(member);

        // then
//        em.flush(); // Transactional은 기본적으로 rollback함. 어차피 rollback하기 때문에 insert문이 나가지 않음.
//        // 이로 인해 안보이는 insert문 console창에 보이도게하는 방법 => em.flush()
        assertEquals(member, memberRepository.findOne(saveId));
    }

    @Test(expected = IllegalStateException.class) // 하기 메서드에서 IllegalStateException이 잡히면 정상 통과
    public void 중복_회원_예외() throws Exception {
        // given
        Member member1 = new Member();
        member1.setName("kim");

        Member member2 = new Member();
        member2.setName("kim");

        // when
        memberService.join(member1);
        memberService.join(member2);
        // @Test(expected = IllegalStateException.class)로 하기 코드 대체 가능
//        try {
//            memberService.join(member2);
//        } catch (IllegalStateException e) {
//            return;
//        }
        // then
        fail("예외가 발생해야 한다.");
    }
}