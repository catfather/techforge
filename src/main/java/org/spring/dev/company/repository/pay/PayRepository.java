package org.spring.dev.company.repository.pay;

import org.spring.dev.company.entity.pay.PayEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PayRepository extends JpaRepository<PayEntity, Long> {

    @Modifying
    @Query(value = "SELECT * FROM c_pay " +
            "WHERE pay_monthly = :month " +
            "AND member_id = :id", nativeQuery = true)
    List<PayEntity> findByPayMonth(@Param("id") Long memberId, @Param("month") String workMonth);

    List<PayEntity> findBymemberEntity_Id(Long memberId);

    @Modifying
    @Query(value = "SELECT * "+
            "FROM c_pay "+
            "WHERE YEAR(pay_day) = :year "+
            "AND member_id = :id", nativeQuery = true)
    List<PayEntity> findByPayYear(@Param("id") Long memberId, @Param("year") String workYear);
}
