package org.spring.dev.company.controller.workTime;


import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.spring.dev.company.config.MyUserDetails;
import org.spring.dev.company.dto.workTime.WorkTimeDto;
import org.spring.dev.company.service.workTime.WorkTimeService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping("/work")
public class WorkTimeController {

    private final WorkTimeService workTimeService;


//    근태관리(출근/퇴근)
    @GetMapping("/commute")
    public String getCommute(@AuthenticationPrincipal MyUserDetails myUserDetails){

        return "workTime/commute";
    }


    @GetMapping("")
    public String mySchedule(){

        return "workTime/workTime";
    }

    /*`
    member 값을 받으면 해당 맴버에 대한 근무 기록을 보여주는 컨트롤러

    추후 search값을 받아서 해당기간동안 근무기록 등 추가하면 좋음!
     */
    @GetMapping("/{memberId}")
    @ResponseBody
    public Map<String, Object> getWorkTimeMemberDetail(
            @PathVariable("memberId") Long memberId
    ) {
        Map<String, Object> map = new HashMap<>();
        List<WorkTimeDto> workTimeDtoList = workTimeService.getWorkTimeMemberDetail(memberId);
        map.put("workTime", workTimeDtoList);
        return map;
    }

    /*
    근무 추가
     */
    @PostMapping("/{memberId}/add")
    @ResponseBody
    public Map<String, Object> postWorkTimeAdd(
            @PathVariable("memberId") Long memberId,
            @RequestBody WorkTimeDto workTimeDto
    ) {
        Long result = workTimeService.postWorkTimeAdd(memberId, workTimeDto);
        Map<String, Object> map = new HashMap<>();
        if (result == null) {
            map.put("result", 0);
        } else {
            map.put("result", result);
        }
        return map;
    }

    /*
    TODO 출근
     */
    @PostMapping("/workin/{memberId}")
    @ResponseBody
    public Map<String, Object> postWorkIn(
            @PathVariable(name = "memberId") Long memberId,
            @AuthenticationPrincipal MyUserDetails myUserDetails
    ) {
        Map<String, Object> map = new HashMap<>();
        Long result = workTimeService.postWorkTimeIn(memberId);
        if (result == null) {
            //이미 생성이 되어있음
            map.put("result", 0);
        } else {
            //생성 완료 됨
            map.put("result", 1);
        }
        return map;
    }

    /*
         TODO 퇴근
     */
    @PostMapping("/workout/{memberId}")
    @ResponseBody
    public Map<String, Object> postWorkOut(
            @PathVariable(name = "memberId") Long memberId,
            @AuthenticationPrincipal MyUserDetails myUserDetails
    ) {
        Map<String, Object> map = new HashMap<>();
        Long result = workTimeService.postWorkTimeOut(memberId);
        //퇴근 기록이 생성 되어있다면
        if (result == null) {
            map.put("result", 0);
        } else {
            //퇴근 기록 생성 된다면
            map.put("result", 1);
        }
        return map;
    }

    /*
    list
     */
    @GetMapping("/{memberId}/list")
    @ResponseBody
    public Map<String, Object> getWorkTimeWorklist(
            @PathVariable("memberId") Long memberId,
            @RequestParam(value = "workType", required = false) String workType) {
        // json 형태로 front에 넘기기
        Map<String, Object> map = new HashMap<>();

        // 근무기록 list로 가져오기(반환)
        List<WorkTimeDto> workTimeList = workTimeService.getWorkTimeWorkList(memberId,workType);

        map.put("worklist", workTimeList);
        return map;
    }

    // 원하는 날짜만 나오게 하기
    @GetMapping("/{memberId}/datelist")
    @ResponseBody
    public Map<String, Object> getWorkTimeDateList(
            @PathVariable("memberId") Long memberId,
            @RequestParam(value = "workDate", required = false) String workDate) {

        // 근무기록 List로 가져오기
        List<WorkTimeDto> workTimeDateList = workTimeService.getWorkTimeWorkDateList(memberId, workDate);
        Map<String, Object> map = new HashMap<>();
        map.put("workDate", workTimeDateList);
        return map;
    }

    /*
    출퇴근 시간 수정하고, total시간도 자동수정 worktype도 수정
     */
    @GetMapping("/{memberId}/update")
    @ResponseBody
    public Map<String, Object> getWorkTimeUpdate(@PathVariable("memberId") Long id, Model model) {
        WorkTimeDto workTimeDto = workTimeService.getWorkTimeUpdate(id);

        if (workTimeDto != null) {
            model.addAttribute("workTimeDto", workTimeDto);
        }

        Map<String, Object> map = new HashMap<>();
        map.put("workTimeDto", workTimeDto);
        return map;

    }


    /*

    근무 수정

        workTimeStart, workTimeEnd
        YYYY-MM-DD

        workType 필수 값
        VACATION, TARDY, ABSENT, EARLY, NORMAL;
        // 휴가, 지각, 결석, 조퇴, 정상
     */
    @PostMapping("/{memberId}/update")
    @ResponseBody
    public Map<String, Object> postWorkTimeUpdate(@PathVariable("memberId") Long id, Model model,
                                                  @RequestBody WorkTimeDto workTimeDto) {
        Map<String, Object> map = new HashMap<>();
        WorkTimeDto result = workTimeService.postWorkTimeUpdate(workTimeDto, id);
        map.put("workTimeDto", result);

        return map;
    }

    //근무 기록 추가

}
