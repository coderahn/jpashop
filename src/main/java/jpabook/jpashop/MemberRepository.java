package jpabook.jpashop;

import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository //내부에 @Component
public class MemberRepository {

    //스프링부트가 이 어노테이션 붙으면 엔티티매니저 주입해줌
    //스프링부트가 엔티티매니저팩토리 역할을 해줌
    @PersistenceContext
    private EntityManager em;

    public Long save(Member member) {
        em.persist(member);
        return member.getId();
    }

    public Member find(Long id) {
        return em.find(Member.class, id);
    }
}
