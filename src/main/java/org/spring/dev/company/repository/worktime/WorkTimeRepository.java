package org.spring.dev.company.repository.worktime;

import org.spring.dev.company.entity.worktime.WorkTimeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface WorkTimeRepository extends JpaRepository<WorkTimeEntity, Long> {
//    List<WorkTimeEntity> findByMemberEntity_MemberId(Long memberId);

    List<WorkTimeEntity> findByMemberEntity_Id(Long memberId);

    WorkTimeEntity findByWorkTimeStart(LocalDateTime workTime);


    @Query(value = "SELECT * " +
            "FROM c_work_time " +
            "WHERE DATE(work_time_in) = :date", nativeQuery = true)
    List<WorkTimeEntity> getDayTime(@Param("date") String date);

    @Query(value = "SELECT * " +
            "FROM c_work_time " +
            "WHERE DATE(work_time_in) = :date " +
            "AND member_entity = :memberId", nativeQuery = true)
    WorkTimeEntity getDayTimeMember(@Param("date") String date,@Param("memberId") Long memberId);


    @Modifying
    @Query(value = "UPDATE c_work_time " +
            "SET work_time_total = :workTimeTotal " +
            "WHERE DATE(work_time_in) = :date " +
            "AND id = :id", nativeQuery = true)
    void updateWorkTotalTime(@Param("workTimeTotal") Long workTimeTotal, @Param("date") String date,  @Param("id") Long id);


    //    달 검색
    @Query(value = "SELECT * " +
            "FROM c_work_time " +
            "WHERE MONTH(work_time_in) = :month " +
            "AND member_entity = :id " +
            "ORDER BY DATE(work_time_in) ASC", nativeQuery = true)
    List<WorkTimeEntity> findByWorkTimeMonth(@Param("id") Long memberId, @Param("month") String workMonth);


    @Query(value = "SELECT * " +
            "FROM c_work_time " +
            "WHERE work_time_type LIKE %:type% " +
            "AND member_entity = :id " +
            "ORDER BY DATE(work_time_in) ASC", nativeQuery = true)
    List<WorkTimeEntity> findByWorkTimeWorkType(@Param("id") Long MemberId, @Param("type") String workType);

    @Modifying
    @Query(value = "SELECT * " +
            "FROM c_work_time " +
            "WHERE DATE(work_time_in) = :date " +
            "AND member_entity = :id " +
            "ORDER BY DATE(work_time_in) ASC", nativeQuery = true)
    List<WorkTimeEntity> findByWorkTimeDate(@Param("date") String workDate, @Param("id") Long MemberId);


    @Query(value = "SELECT * " +
            "FROM c_work_time " +
            "WHERE member_entity = :id " +
            "ORDER BY DATE(work_time_in) ASC", nativeQuery = true)
    List<WorkTimeEntity> findByWorkTimeMemberId(@Param("id") Long memberId);

}
