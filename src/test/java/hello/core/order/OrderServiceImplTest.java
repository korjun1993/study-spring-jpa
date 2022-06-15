package hello.core.order;

import hello.core.discount.DiscountPolicy;
import hello.core.discount.RateDiscountPolicy;
import hello.core.member.Grade;
import hello.core.member.Member;
import hello.core.member.MemoryMemberRepository;
import org.junit.jupiter.api.Test;

public class OrderServiceImplTest {
    @Test
    void createOrder() {
        MemoryMemberRepository memberRepository = new MemoryMemberRepository();
        DiscountPolicy discountPolicy = new RateDiscountPolicy();
        memberRepository.save(new Member(1L, "name", Grade.VIP));

        OrderServiceImpl orderService = new OrderServiceImpl(memberRepository, discountPolicy);
        Order order = orderService.createOrder(1L, "itemA", 10000);
        //Assertions.assertThat(order.getDiscountPrice()).isEqualTo(1000);
    }
}
