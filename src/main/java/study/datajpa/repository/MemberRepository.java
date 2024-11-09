package study.datajpa.repository;

import jakarta.persistence.QueryHint;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.query.Param;
import study.datajpa.entity.Member;

import java.util.List;

public interface MemberRepository extends JpaRepository<Member, Long>, MemberRepositoryCustom {
    List<Member> findByUsernameAndAgeGreaterThan(String username, int age);

//    @Query(value = "select m from Member m left join m.team t",
//            countQuery = "select count(m) from Member m") // 성능 최적화를 위한 countQuery 분리 가능
    Page<Member> findByAge(int age, PageRequest pageRequest);

    // 수정하는 쿼리일 경우 Modifying 어노테이션 필요함
    // clearAutomatically 쿼리 실행 후 영속성 컨텍스트 초기화 하는 옵션 default false
    @Modifying(clearAutomatically = true)
    @Query("update Member m set m.age = m.age + 1 where m.age >= :age")
    int bulkAgePlus(@Param("age") int age);

    @QueryHints(@QueryHint(name = "org.hibernate.readOnly", value = "true"))
    Member findQueryHintByUsername(String username);
}
