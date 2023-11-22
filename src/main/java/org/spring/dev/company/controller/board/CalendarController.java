package org.spring.dev.company.controller.board;

import lombok.RequiredArgsConstructor;
import org.spring.dev.company.dto.board.CalendarDto;
import org.spring.dev.company.service.board.CalendarService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/calendar", produces = "application/json")
@RequiredArgsConstructor
public class CalendarController {

    private final CalendarService calendarService;

    @GetMapping("/list")
    public List<CalendarDto> listCalendar(@ModelAttribute CalendarDto calendarDto){
       List<CalendarDto> calendarDtoList = calendarService.calendarListAll();
       return calendarDtoList;
    }

    @PostMapping("/save")
    public List<CalendarDto> setCalendar(@ModelAttribute CalendarDto calendarDto){
        calendarService.setCalendar(calendarDto);
        return calendarService.calendarListAll();
    }

    @GetMapping("/save")
    public List<CalendarDto> setCalendar(){
        return calendarService.calendarListAll();
    }

}
