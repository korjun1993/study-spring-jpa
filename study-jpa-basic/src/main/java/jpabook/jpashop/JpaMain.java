package jpabook.jpashop;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.Team;

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
            tx.begin();

            Team team = new Team();
            team.setName("TeamA");
            em.persist(team);

            Member member = new Member();
            member.setUsername("member1");
//            member.setTeamId(team.getId()); // 객체지향스럽지 않음 -> 변경:member.setTeam(team);
            member.setTeam(team);
            em.persist(member);

            em.flush();
            em.clear();

//            Long teamId = member.getTeamId(); // Member의 Team을 찾을때마다 member.getTeamId()를 호출해야한다.
//            Team findTeam = em.find(Team.class, teamId); // 객체지향 스럽지 않음 -> 변경:member.getTeam();
            Team findTeam = member.getTeam();

            tx.commit();

        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }
        emf.close();
    }
}
