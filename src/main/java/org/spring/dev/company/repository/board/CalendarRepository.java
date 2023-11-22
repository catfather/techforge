package org.spring.dev.company.repository.board;

import org.spring.dev.company.entity.board.CalendarEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CalendarRepository extends JpaRepository<CalendarEntity, Integer> {

}
