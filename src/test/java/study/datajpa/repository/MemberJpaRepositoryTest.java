package study.datajpa.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import study.datajpa.entity.Member;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class MemberJpaRepositoryTest {

    @Autowired
    MemberJpaRepository memberJpaRepository;

    @DisplayName("멤버를 저장할 수 있다.")
    @Test
    void createMember() {
        // given
        Member member = new Member("memberA");

        // when
        Member savedMember = memberJpaRepository.save(member);

        // then
        assertThat(savedMember).isNotNull();
        assertThat(savedMember.getId()).isNotNull();
        assertThat(savedMember.getUsername()).isEqualTo("memberA");
    }

    @DisplayName("멤버를 id로 찾을 수 있다.")
    @Test
    void findMember() {
        // given
        Member member = new Member("memberA");
        Member savedMember = memberJpaRepository.save(member);

        // when
        Member findMember = memberJpaRepository.find(savedMember.getId());

        // then
        assertThat(findMember.getUsername()).isEqualTo("memberA");
        assertThat(member).isEqualTo(findMember); // 같은 transactional 안에서 영속성 컨텍스트의 동일성을 보장한다.
        assertThat(savedMember).isEqualTo(findMember);
    }

}