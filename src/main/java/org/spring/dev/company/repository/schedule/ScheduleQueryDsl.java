package org.spring.dev.company.repository.schedule;

import org.spring.dev.company.dto.schedule.ScheduleDto;
import org.spring.dev.company.entity.schedule.ScheduleEntity;

import java.util.List;


public interface ScheduleQueryDsl{

    List<ScheduleEntity> findScheduleSearch(ScheduleDto scheduleDto);

    ScheduleEntity insertSchedule(ScheduleDto scheduleDto);

}
