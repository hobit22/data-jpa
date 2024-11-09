package study.datajpa.entity;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import study.datajpa.repository.MemberRepository;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback(value = false)
class MemberTest {

    @PersistenceContext
    EntityManager em;
    @Autowired
    private MemberRepository memberRepository;

    @DisplayName("멤버를 생성할 수 있다.")
    @Test
    void createMember() {
        // given
        Team teamA = new Team("teamA");
        Team teamB = new Team("teamB");
        em.persist(teamA);
        em.persist(teamB);

        Member member1 = new Member("member1", 10, teamA);
        Member member2 = new Member("member2", 10, teamA);
        Member member3 = new Member("member3", 10, teamB);
        Member member4 = new Member("member4", 10, teamB);

        em.persist(member1);
        em.persist(member2);
        em.persist(member3);
        em.persist(member4);

        em.flush();
        em.clear();

        // when
        List<Member> members = em.createQuery("select m from Member m", Member.class)
                .getResultList();

        // then
        for (Member member : members) {
            System.out.println("member = " + member);
            System.out.println("member.getTeam() = " + member.getTeam());
        }
    }


    @Test
    @DisplayName("jpa auditing test")
    void jpaAuditingTest() throws Exception {
        // given
        Member member = new Member("member1", 10, new Team("teamA"));
        memberRepository.save(member);

        Thread.sleep(1000);

        member.setUsername("aaaa");
        em.flush();
        em.clear();

        // when
        Member findMember = memberRepository.findById(member.getId()).get();

        // then
        assertThat(findMember.getUpdatedDate()).isNotNull();
        assertThat(findMember.getUsername()).isEqualTo("aaaa");
    }
}