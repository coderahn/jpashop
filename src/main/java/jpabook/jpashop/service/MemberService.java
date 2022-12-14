package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true) //JPA의 모든 로직 등은 트랜잭션 안에서 실행되어야 함
//@AllArgsConstructor
//@RequiredArgsConstructor //final있는 필드 가지고 생성자 만들어줌(best practice)
public class MemberService {
    //field injection
    @Autowired
    private final MemberRepository memberRepository;

    //setter injection : testCode 작성시 mock등 주입할 수 있음
    //단점 : 애플리케이션 돌아가는 시점에 누군가 바꿀 수도 있음(근데 그럴일이 거의 없지)
//    @Autowired
//    public void setMemberRepository(MemberRepository memberRepository) {
//        this.memberRepository = memberRepository;
//    }

    //constructor injection : 중간에 변경 안 됨
    //@Autowired 없어도 됨(생성자 1개일 때)
    //memberRepository에 final 넣어주면 체크 되고 좋음
    //@AllArgsConstructor로 대체 가능
    @Autowired
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    /**
     * 회원가입
     */
    @Transactional(readOnly = false) //따로 설정한 건 따로 우선권
    public Long join(Member member) {
        //동시성 이슈 : 요청이 2개 이상일 때 (김민수라는 이름 insert) a요청이 먼저 insert를 때릴 수 있음
        //Q.@Transactional에 isolation level을 read_commited로 주면?
        validateDuplicateMember(member); //중복 회원 검증
        memberRepository.save(member);

        return member.getId(); //위에서 em.persist시 항상 member.id에 값이 박혀 들어가있음
    }

    private void validateDuplicateMember(Member member) {
        List<Member> findMembers = memberRepository.findByName(member.getName());

        if (!findMembers.isEmpty()) {
            throw  new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    /**
     * 회원 전체 조회
     */
    @Transactional //읽기에만
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    @Transactional
    public Member findOne(Long memberId) {
        return memberRepository.findOne(memberId);
    }
}
