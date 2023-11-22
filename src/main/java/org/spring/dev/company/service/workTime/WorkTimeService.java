package org.spring.dev.company.service.workTime;

import lombok.RequiredArgsConstructor;
import org.hibernate.jdbc.Work;
import org.spring.dev.company.dto.workTime.WorkTimeDto;
import org.spring.dev.company.entity.member.MemberEntity;
import org.spring.dev.company.entity.util.WorkType;
import org.spring.dev.company.entity.worktime.WorkTimeEntity;
import org.spring.dev.company.repository.member.MemberRepository;
import org.spring.dev.company.repository.worktime.WorkTimeRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class WorkTimeService {

    private final WorkTimeRepository workTimeRepository;

    //    근무 추가 시 memberId에 해당하는 근무자에게만 추가하기 위해서 memberRepository
    private final MemberRepository memberRepository;


    public List<WorkTimeDto> getWorkTimeMemberDetail(Long memberId) {
        List<WorkTimeDto> workTimeDtoList = new ArrayList<WorkTimeDto>();
        List<WorkTimeEntity> workTimeList = workTimeRepository.findByMemberEntity_Id(memberId);

        for (WorkTimeEntity workTime : workTimeList) {
            workTimeDtoList.add(WorkTimeDto.toDto(workTime));
        }

        return workTimeDtoList;
    }

    public Long postWorkTimeIn(Long memberId) {
        Long result = null;

        String date = LocalDate.now().toString();
        WorkTimeEntity workTimeEntity = new WorkTimeEntity();
        // 출근 기록이 없다면 생성
        if (workTimeRepository.getDayTimeMember(date,memberId) == null) {
            MemberEntity memberEntity = new MemberEntity();
            memberEntity.setId(memberId);

            // WorkTimeEntity에 MemberEntity 설정
            workTimeEntity.setMemberEntity(memberEntity);
            workTimeEntity.setWorkTimeStart(LocalDateTime.now());
            workTimeEntity.setIs_display(1);
            workTimeEntity.setWorkType(WorkType.NORMAL);

            // WorkTimeEntity 저장 및 결과 저장
            WorkTimeEntity savedWorkTime = workTimeRepository.save(workTimeEntity);
            result = savedWorkTime.getId();
        }
        // 출근 기록이 이미 생성되어 있다면 .save에 의해 업데이트 되기 때문에
        // result == null 로 리턴
        return result;
    }


    /*
    퇴근
     */
    public Long postWorkTimeOut(Long memberId) {
        Long result = null;
        String date = LocalDate.now().toString();
        //출근 기록이있다면
        WorkTimeEntity workTimeEntity = workTimeRepository.getDayTimeMember(date,memberId);
        //퇴근 기록이 아직 없을때 생성
        if (workTimeEntity.getWorkTimeEnd() == null) {
            workTimeEntity.setWorkTimeEnd(LocalDateTime.now());
            result = workTimeRepository.save(workTimeEntity).getId();
            return result;
        }
        return result;

    }

    /*
    list
     */
    public List<WorkTimeDto> getWorkTimeWorkList(Long memberId, String workType) {
        List<WorkTimeDto> workTimeDtoList = new ArrayList<>(); // 반환값이 list이므로 list생성
        List<WorkTimeEntity> workTimeEntityList;

        if (workType == null) {
            // 달만 선택
            workTimeEntityList = workTimeRepository.findByWorkTimeMemberId(memberId);
        } else {
            workTimeEntityList = workTimeRepository.findByWorkTimeWorkType(memberId, workType);
        }

        // 달&유형 선택
        if (!workTimeEntityList.isEmpty()) {
            for (WorkTimeEntity workTimeEntity : workTimeEntityList) {
                WorkTimeDto workTimeDto = WorkTimeDto.toDto(workTimeEntity);
                if (workTimeDto.getWorkType() == WorkType.NORMAL) {
                    workTimeDto.setTitle("근무");
                } else if (workTimeDto.getWorkType() == WorkType.ABSENT) {
                    workTimeDto.setTitle("결석");
                } else if (workTimeDto.getWorkType() == WorkType.EARLY) {
                    workTimeDto.setTitle("조퇴");
                } else if (workTimeDto.getWorkType() == WorkType.TARDY) {
                    workTimeDto.setTitle("지각");
                } else if (workTimeDto.getWorkType() == WorkType.VACATION) {
                    workTimeDto.setTitle("휴가");
                }
                workTimeDtoList.add(workTimeDto);
            }
        }
        return workTimeDtoList;
    }

    //    listdate
    public List<WorkTimeDto> getWorkTimeWorkDateList(Long memberId, String workDate) {
        List<WorkTimeDto> workTimeDtoList = new ArrayList<>();
        List<WorkTimeEntity> workTimeEntityList = workTimeRepository.findByWorkTimeDate(workDate, memberId);

        if (!workTimeEntityList.isEmpty()) {
            for (WorkTimeEntity workTimeEntity : workTimeEntityList) {
                WorkTimeDto workTimeDto = WorkTimeDto.toDto(workTimeEntity);
                workTimeDtoList.add(workTimeDto);
            }
        }
        return workTimeDtoList;
    }

    /*
    수정
     */
    public WorkTimeDto getWorkTimeUpdate(Long id) {
        Optional<WorkTimeEntity> optionalWorkTimeEntity
                = Optional.ofNullable(workTimeRepository.findById(id).orElseThrow(() -> {
            return new IllegalArgumentException("수정할 사항이 없습니다.");
        }));
        if (optionalWorkTimeEntity.isPresent()) {
            WorkTimeDto workTimeDto = WorkTimeDto.toDto(optionalWorkTimeEntity.get());
            return workTimeDto;
        }
        return null;
    }

    @Transactional
    public WorkTimeDto postWorkTimeUpdate(WorkTimeDto workTimeDto, Long memberId) {

        MemberEntity memberEntity = new MemberEntity();
        memberEntity.setId(memberId);
        workTimeDto.setMemberEntity(memberEntity);

//        WorkTimeEntity를 업데이트
        WorkTimeEntity updatedWorkTimeEntity = WorkTimeEntity.toEntity(workTimeDto);
//        업데이트 WorkTimeEntity를 저장
        Long workTimeId = workTimeRepository.save(updatedWorkTimeEntity).getId();

        // total설정
        Integer total
                = (updatedWorkTimeEntity.getWorkTimeEnd().getHour() - updatedWorkTimeEntity.getWorkTimeStart().getHour()) * 60;
        updatedWorkTimeEntity.setTotal(total);

        WorkTimeEntity workTimeEntity1 = workTimeRepository.findById(workTimeId).orElseThrow(() -> {
            throw new IllegalArgumentException("수정할 사항이 없습니다.");
        });

//      업데이트된 WorkTimeEntity를 사용하여 WorkTimeDto를 생성하고 반환
        WorkTimeDto updatedWorkTimeDto = WorkTimeDto.toDto(workTimeEntity1);

        return updatedWorkTimeDto;

    }

    //    근무추가 service
    @Transactional
    public Long postWorkTimeAdd(Long memberId, WorkTimeDto workTimeDto) {
        Long result = null;
//        memberId확인
        MemberEntity memberEntity = new MemberEntity();
        memberEntity.setId(memberId);
//        workTimeDto에 있는 memberEntity를 가져와
        workTimeDto.setMemberEntity(memberEntity);

//        builder
        WorkTimeEntity addWorkTimeEntity = WorkTimeEntity.toEntity(workTimeDto);

//        total 자동 설정
        Integer total
                = (addWorkTimeEntity.getWorkTimeEnd().getHour() - addWorkTimeEntity.getWorkTimeStart().getHour()) * 60;
        addWorkTimeEntity.setTotal(total);

        if (memberId != null) {
            result = workTimeRepository.save(addWorkTimeEntity).getId();
            return result;
        }

        return null;
    }

    /*
     * workTime스케줄러에서 사용하는 메서드 입니다
     *
     * cron 표현식으로 매일 오전 09:00에 작동합니다.
     *
     * 오늘 09:00시에 작동해서 어제 출퇴근 기록을 가져와서 total근무 시간을 계산후 update 합니다.
     *
     * 스케줄러 이기에 void 처리 했습니다
     */
    @Transactional
    public void doWorkTotalcal() {
        String date = LocalDate.now().minusDays(1).toString();
        List<WorkTimeEntity> workTimeEntityList = workTimeRepository.getDayTime(date);
        if (workTimeEntityList != null) {
            for (WorkTimeEntity workTimeEntity: workTimeEntityList) {
                if (workTimeEntity.getWorkTimeStart() != null && workTimeEntity.getWorkTimeEnd() != null) {
                    Duration total = Duration.between(workTimeEntity.getWorkTimeStart(), workTimeEntity.getWorkTimeEnd());
                    long workTimeTotal = total.toMinutes();
                    workTimeRepository.updateWorkTotalTime(workTimeTotal, date, workTimeEntity.getId());
                }
            }
        }
    }
}
