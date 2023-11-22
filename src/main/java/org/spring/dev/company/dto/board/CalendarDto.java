package org.spring.dev.company.dto.board;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CalendarDto {

    private Integer id;

    private String content;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'")
    private Date start;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'")
    private Date end;

}
