package org.spring.dev.company.scheduler.workTime;

import lombok.RequiredArgsConstructor;
import org.spring.dev.company.entity.worktime.WorkTimeEntity;
import org.spring.dev.company.repository.worktime.WorkTimeRepository;
import org.spring.dev.company.service.workTime.WorkTimeService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class WorkTimeTotalScheduler {

    private final WorkTimeService workTimeService;

    /*
    일일 근무시간을 컨트롤러 호출을 통해서 매번 계산할수는 없으니 스케줄러를 통해서
    매일 오전 9시에  전날 근무기록을 조회해서 출 퇴근 시간을 조회해서 total을 계산해서 저장하는
    스케줄러를 만듬
     */

    //cron 표현식 매일 오전 09:00 실행
//        @Scheduled(cron = "0 00 09 * * ?")
    @Scheduled(fixedRate = 500000) //메소드 종료 시점 부터 5분 후에 다시 시작
    public void myScheduledTask() {
        workTimeService.doWorkTotalcal();
    }

}
