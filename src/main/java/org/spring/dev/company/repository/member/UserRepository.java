package org.spring.dev.company.repository.member;

import org.spring.dev.company.entity.member.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<MemberEntity, Long> {


}
