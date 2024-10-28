package study.datajpa.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import study.datajpa.entity.Member;

import java.util.List;

public interface MemberRepository extends JpaRepository<Member, Long> {
    List<Member> findByUsernameAndAgeGreaterThan(String username, int age);

//    @Query(value = "select m from Member m left join m.team t",
//            countQuery = "select count(m) from Member m") // 성능 최적화를 위한 countQuery 분리 가능
    Page<Member> findByAge(int age, PageRequest pageRequest);
}
