# 실습 테이블 설계
![img.png](img/erd.png)

## 연관관계 매핑이 필요한 이유
연관관계 매핑이 필요한 이유를 알아보자.

- 다음은 위 ERD에서 ORDER 테이블을 Entity로 작성한 예이다.
- 연관관계 매핑을 하기 전이다.
```java
@Entity
@Table(name = "ORDERS")
public class Order {

    @Id
    @GeneratedValue
    @Column(name = "ORDER_ID")
    private Long id;

    @Column(name = "MEMBER_ID")
    private Long memberId;
    private LocalDateTime orderDate;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;
}
```
### 문제점
- `Member` 클래스 참조변수를 갖고있지 않고, `memberId` 를 갖고 있다.
- 현재 상황에서 orderId가 주어졌을 때, 주문자를 얻는 방법은 다음과 같다:
```java
Long orderId = 1L;
Order order = em.find(Order.class, orderId);
Long memberId = order.getMemberId();
Member member = em.find(Member.class, memberId);
```
- 우리가 바라는 것은 다음 코드처럼 객체지향스럽게 주문자를 얻고 싶다.
- 연관 관계 매핑을 통해 가능하다.
```java
Long orderId = 1L;
Order order = em.find(Order.class, orderId);
Member member = order.getMember();
```

## 단방향 연관관계
작성중...

# Reference
- 자바 ORM 표준 JPA 프로그래밍, 김영한 지음
