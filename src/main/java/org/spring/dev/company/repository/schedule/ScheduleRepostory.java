package org.spring.dev.company.repository.schedule;

import org.spring.dev.company.entity.schedule.ScheduleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScheduleRepostory extends JpaRepository<ScheduleEntity,Long> {

}
