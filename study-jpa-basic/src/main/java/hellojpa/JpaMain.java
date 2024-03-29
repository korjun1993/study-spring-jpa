package hellojpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class JpaMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin(); //[트랜잭션]-시작

            //=== 로직 ===//
            Member member = new Member();
            member.setId(1L);
            member.setName("HelloA");
            em.persist(member);
            tx.commit(); //[트랜잭션]-커밋
        } catch (Exception e) {
            tx.rollback(); //[트랜잭션]-롤백
        } finally {
            em.close(); // [엔티티 매니저]-종료
        }
        emf.close(); //[엔티티 매니저 팩토리]-종료
    }
}
