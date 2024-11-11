package study.datajpa.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(of = {"id", "username", "age"}) // 연관관계 필드는 ToString 안하는게 좋음
public class Member extends JpaBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // jpa가 id를 만드는 전략
    @Column(name = "member_id")
    private Long id;

    private String username;

    private int age;

    @ManyToOne(fetch = FetchType.LAZY) // 성능 최적화 하기 쉽도록 LAZY로 세팅
    @JoinColumn(name = "team_id")
    private Team team;

    @CreatedBy
    @Column(updatable = false)
    private String createdBy;

    @LastModifiedBy
    private String lastModifiedBy;

    public Member(String username) {
        this.username = username;
    }

    public Member(String username, int age, Team team) {
        this.username = username;
        this.age = age;

        changeTeam(team);
    }

    public Member(String username, int age) {
        this.username = username;
        this.age = age;
    }

    // DB에서는 Member의 team_id만 변경되지만 객체에서는 Team도 변경해줘야함.
    public void changeTeam(Team team) {
        this.team = team;
        team.getMembers().add(this);
    }
}
