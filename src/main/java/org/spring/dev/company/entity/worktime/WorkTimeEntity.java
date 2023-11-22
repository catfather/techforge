package org.spring.dev.company.entity.worktime;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.spring.dev.company.dto.workTime.WorkTimeDto;
import org.spring.dev.company.entity.member.MemberEntity;
import org.spring.dev.company.entity.util.BaseEntity;
import org.spring.dev.company.entity.util.WorkType;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "c_work_time")
public class WorkTimeEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "work_time_in")
    private LocalDateTime workTimeStart;

    @Column(name = "work_time_out")
    private LocalDateTime workTimeEnd;

    @Column(name = "work_time_total")
    private Integer total;

    // 근무형태
    @Enumerated(EnumType.STRING)
    @Column(name = "work_time_type")
    private WorkType workType;

    @JoinColumn(name = "memberEntity")
    @ManyToOne(fetch = FetchType.LAZY)
    private MemberEntity memberEntity;

    //    update
    public static WorkTimeEntity toEntity(WorkTimeDto workTimeDto) {
        return WorkTimeEntity.builder()
                .id(workTimeDto.getId())
                .workTimeStart(workTimeDto.getWorkTimeStart())
                .workTimeEnd(workTimeDto.getWorkTimeEnd())
                .total(workTimeDto.getTotal())
                .workType(workTimeDto.getWorkType())
                .memberEntity(workTimeDto.getMemberEntity())
                .build();
    }
}
