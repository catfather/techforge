package org.spring.dev.company.repository.member;

import org.spring.dev.company.entity.member.MemberEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<MemberEntity, Long> {


    @Query("SELECT COUNT(*) FROM MemberEntity m WHERE m.email = :email")
    int findByEmail1(String email);


    @Query("SELECT COUNT(*) FROM MemberEntity m WHERE m.phone = :phone")
    int findByPhone(String phone);

    @Modifying
    @Query("UPDATE MemberEntity m SET m.password = :pw WHERE m.id = :id")
    void updateUserPassword(Long id, String pw);


    MemberEntity findUserByEmail(String email);


    @Query("SELECT m FROM MemberEntity m where m.email =:email ")
    Optional<MemberEntity> findByMemberEmail(String email);

    MemberEntity findByEmailAndName(String email, String name);

    @Query("SELECT m.password FROM MemberEntity m where m.email = :email")
    String findByPassword(String email);


    @Modifying
    @Query("UPDATE MemberEntity m SET m.password = :password WHERE m.email = :email")
    void updateUserPassword1(String email, String password);

    Optional<MemberEntity> findByEmail(String email);

    @Query("SELECT COUNT(*) FROM MemberEntity m WHERE m.companyName = :companyName")
    int findByCompanyName(String companyName);
    @Query("SELECT COUNT(*) FROM MemberEntity m WHERE m.businessNumber = :businessNumber")
    int findByBusinessNumber(String businessNumber);



    //  프리랜서 리스트, search
    @Query("select m from MemberEntity m where m.is_display = 1 and m.grade = 'FREELANCER' ")
    Page<MemberEntity> findFreeAll(Pageable pageable);
    @Query("select m from MemberEntity m where m.is_display = 1 and m.grade = 'FREELANCER' and m.name LIKE CONCAT('%', :search, '%') ")
    Page<MemberEntity> findByNameContains(Pageable pageable, String search);

    @Query("select m from MemberEntity m where m.is_display = 1 and m.grade = 'FREELANCER' and m.email LIKE CONCAT('%', :search, '%') ")
    Page<MemberEntity> findByEmailContains(Pageable pageable, String search);

    @Query("select m from MemberEntity m where m.is_display = 1 and m.grade = 'FREELANCER' and m.phone LIKE CONCAT('%', :search, '%') ")
    Page<MemberEntity> findByPhoneContains(Pageable pageable, String search);

    // 회사 리스트, search
    @Query("select m from MemberEntity m where m.is_display = 1 and m.grade = 'COMPANY' ")
    Page<MemberEntity> findCompanyAll(Pageable pageable);

    @Query("select m from MemberEntity m where m.is_display = 1 and m.grade = 'COMPANY' and m.name LIKE CONCAT('%', :search, '%') ")
    Page<MemberEntity> findByNameCompany(Pageable pageable, String search);

    @Query("select m from MemberEntity m where m.is_display = 1 and m.grade = 'COMPANY' and m.email LIKE CONCAT('%', :search, '%') ")
    Page<MemberEntity> findByEmailCompany(Pageable pageable, String search);

    @Query("select m from MemberEntity m where m.is_display = 1 and m.grade = 'COMPANY' and m.phone LIKE CONCAT('%', :search, '%') ")
    Page<MemberEntity> findByPhoneCompany(Pageable pageable, String search);

    // 직원 리스트, search
    @Query("select m from MemberEntity m where m.is_display = 1 and m.grade = 'STAFF' ")
    Page<MemberEntity> findStaffAll(Pageable pageable);

    @Query("select m from MemberEntity m where m.is_display = 1 and m.grade = 'STAFF' and m.name LIKE CONCAT('%', :search, '%') ")
    Page<MemberEntity> findByNameStaff(Pageable pageable, String search);

    @Query("select m from MemberEntity m where m.is_display = 1 and m.grade = 'STAFF' and m.email LIKE CONCAT('%', :search, '%') ")
    Page<MemberEntity> findByEmailStaff(Pageable pageable, String search);

    @Query("select m from MemberEntity m where m.is_display = 1 and m.grade = 'STAFF' and m.phone LIKE CONCAT('%', :search, '%') ")
    Page<MemberEntity> findByPhoneStaff(Pageable pageable, String search);


    Optional<MemberEntity> findByName(String name);

    @Query("SELECT m FROM MemberEntity m WHERE m.companyName = :name")
    Optional<MemberEntity> findByCompanyName2(@Param("name") String name);

}
