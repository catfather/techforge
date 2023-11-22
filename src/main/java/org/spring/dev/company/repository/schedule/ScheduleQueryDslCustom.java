package org.spring.dev.company.repository.schedule;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.spring.dev.company.dto.schedule.ScheduleDto;
import org.spring.dev.company.entity.schedule.QScheduleEntity;
import org.spring.dev.company.entity.schedule.ScheduleEntity;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

import static org.springframework.util.StringUtils.hasText;

@Repository
@RequiredArgsConstructor
public class ScheduleQueryDslCustom implements ScheduleQueryDsl {

    private final JPAQueryFactory jpaQueryFactory;

    QScheduleEntity qScheduleEntity = QScheduleEntity.scheduleEntity;

    @Override
    public List<ScheduleEntity> findScheduleSearch(ScheduleDto scheduleDto) {


        return jpaQueryFactory
                .select(qScheduleEntity)
                .from(qScheduleEntity)
                .where(
                        memberIdEq(scheduleDto.getMemberId()),
                        typeEq(scheduleDto.getType()),
                        StartDateTimeEq(scheduleDto.getStartDateTime()),
                        EndDateTimeEq(scheduleDto.getEndDateTime()),
                        ContentEq(scheduleDto.getContent()),
                        colorEq(scheduleDto.getColor()),
                        is_displayEq(scheduleDto.getIs_display())
                )
                .fetch();

    }

    @Override
    public ScheduleEntity insertSchedule(ScheduleDto scheduleDto) {
        return null;
    }

    private BooleanExpression is_displayEq(Integer isDisplay) {
        return isDisplay != null ? qScheduleEntity.is_display.eq(isDisplay) : null;
    }

    private BooleanExpression colorEq(String color) {
        return hasText(color) ? qScheduleEntity.color.eq(color) : null;
    }

    private BooleanExpression ContentEq(String content) {
        return hasText(content) ? qScheduleEntity.content.eq(content) : null;
    }

    private BooleanExpression EndDateTimeEq(LocalDateTime endDateTime) {
        return endDateTime != null ? qScheduleEntity.endDateTime.eq(endDateTime) : null;
    }

    private BooleanExpression StartDateTimeEq(LocalDateTime startDateTime) {
        return startDateTime != null ? qScheduleEntity.startDateTime.eq(startDateTime) : null;
    }

    private BooleanExpression typeEq(String type) {
        return hasText(type) ? qScheduleEntity.type.eq(type) : null;
    }

    private BooleanExpression memberIdEq(Long memberId) {
        return memberId != null ? qScheduleEntity.memberId.eq(memberId) : null;
    }
}