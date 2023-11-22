package org.spring.dev.company.repository.member;

import org.spring.dev.company.entity.member.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LoginRepository extends JpaRepository<MemberEntity, Long> {
    Optional<MemberEntity> findByEmail(String email);
//    MemberEntity findByEmail(String email);
}
