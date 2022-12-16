package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional //이거 없으면 회원가입 테스트 실패 : 같은 트랜잭션으로 관리가 되어야 Member랑 findOne해온 엔티티가 같다고 나옴
class MemberServiceTest {
    @Autowired MemberService memberService;
    @Autowired MemberRepository memberRepository;
    @PersistenceContext EntityManager em;

    @Test
    @Rollback(false) //@Transactional이 테스트에 있으면 기본적으로 롤백(insert문 안나감. 굳이 나갈필요가 없기 때문 -> 영속성컨텍스트 플러시 안 함)
    public void 회원가입() throws Exception {
        //given
        Member member = new Member();
        member.setName("kim");

        //when
        Long savedId = memberService.join(member);

        //then
        //@Transcational : 같은 트랜잭션 안에서 같은 엔티티(아이디값동일)면 같은 영속성 컨텍스트에서 똑같은 애가 관리
        assertEquals(member, memberRepository.findOne(savedId));
    }

    @Test
    public void 중복_회원_예외() throws Exception {
        //given
        Member member1 = new Member();
        member1.setName("kim");

        Member member2 = new Member();
        member2.setName("kim");

        //when
        memberService.join(member1);

        //Junit4 @Test(expected='..')
        IllegalStateException thrown = assertThrows(IllegalStateException.class, () -> memberService.join(member2));

        //then
        System.out.println("thrown:" + thrown.getMessage());
        assertEquals("이미 존재하는 회원입니다.", thrown.getMessage());
        //fail("예외가 발생해야 한다.");
    }
}