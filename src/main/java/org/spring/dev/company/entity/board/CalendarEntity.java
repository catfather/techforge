package org.spring.dev.company.entity.board;

import lombok.*;
import org.spring.dev.company.entity.member.MemberEntity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "c_calendar")
public class CalendarEntity {

    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String content;

    //일정 시작시간
    @Column(nullable = false)
    private Date start;

    //일정 종료시간
    @Column(nullable = false)
    private Date end;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private MemberEntity memberEntity;

}
