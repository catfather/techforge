package org.spring.dev.company.dto.workTime;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.spring.dev.company.entity.member.MemberEntity;
import org.spring.dev.company.entity.util.WorkType;
import org.spring.dev.company.entity.worktime.WorkTimeEntity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class WorkTimeDto {

    private Long id;

    private LocalDateTime workTimeStart;

    private LocalDateTime workTimeEnd;

    private Integer total;

    @JsonIgnore
    private MemberEntity memberEntity;

    private LocalDateTime CreateTime;

    private LocalDateTime UpdateTime;

    private Integer is_display;

    private WorkType workType;

    private String title;

    public static WorkTimeDto toDto(WorkTimeEntity workTimeEntity) {
        return WorkTimeDto.builder()
                .id(workTimeEntity.getId())
                .workTimeStart(workTimeEntity.getWorkTimeStart())
                .workTimeEnd(workTimeEntity.getWorkTimeEnd())
                .total(workTimeEntity.getTotal())
                .memberEntity(workTimeEntity.getMemberEntity())
                .CreateTime(workTimeEntity.getCreateTime())
                .UpdateTime(workTimeEntity.getUpdateTime())
                .is_display(workTimeEntity.getIs_display())
                .workType(workTimeEntity.getWorkType())
                .build();
    }

}
