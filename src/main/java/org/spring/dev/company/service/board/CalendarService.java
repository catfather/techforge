package org.spring.dev.company.service.board;

import lombok.RequiredArgsConstructor;
import org.spring.dev.company.dto.board.CalendarDto;
import org.spring.dev.company.entity.board.CalendarEntity;
import org.spring.dev.company.repository.board.CalendarRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CalendarService {

    private final CalendarRepository calendarRepository;

    public List<CalendarDto> calendarListAll() {

        List<CalendarDto> calendarDtoList = new ArrayList<>();
        List<CalendarEntity> calendarEntityList = calendarRepository.findAll();

        for (CalendarEntity calendarEntity : calendarEntityList){
            CalendarDto calendarDto = CalendarDto.builder()
                    .id(calendarEntity.getId())
                    .start(calendarEntity.getStart())
                    .end(calendarEntity.getEnd())
                    .content(calendarEntity.getContent())
                    .build();
            calendarDtoList.add(calendarDto);
        }

        return calendarDtoList;
    }


    public void setCalendar(CalendarDto calendarDto) {

        CalendarEntity calendarEntity =CalendarEntity.builder()
                .content(calendarDto.getContent())
                .start(calendarDto.getStart())
                .end(calendarDto.getEnd())
                .build();

        CalendarEntity calendarEntity1 =calendarRepository.save(calendarEntity);

    }
}
