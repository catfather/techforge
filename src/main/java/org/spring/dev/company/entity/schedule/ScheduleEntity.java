package org.spring.dev.company.entity.schedule;


import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.spring.dev.company.dto.schedule.ScheduleDto;
import org.spring.dev.company.entity.util.BaseEntity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "c_personal_schedule")
public class ScheduleEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private LocalDateTime startDateTime;

    @NotNull
    private LocalDateTime endDateTime;

    private String content;

    // 일정 타입에 따라 컬러 지정
    @ColumnDefault("'#0000ff'")
    private String color;

    // 일정 타입
    @ColumnDefault("'NORMAL'")
    private String type;

    @NotNull
    private Long memberId;
    // 개인 일정 테이블

    public static ScheduleEntity toEntity(ScheduleDto scheduleDto){
        return ScheduleEntity.builder()
                .id(scheduleDto.getId())
                .startDateTime(scheduleDto.getStartDateTime())
                .endDateTime(scheduleDto.getEndDateTime())
                .content(scheduleDto.getContent())
                .color(scheduleDto.getColor())
                .type(scheduleDto.getType())
                .memberId(scheduleDto.getMemberId())
                .build();
    }

}
