package org.spring.dev.openApi.naver.repository;


import org.spring.dev.openApi.naver.entity.NaverTokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NaverRepository extends JpaRepository<NaverTokenEntity,Long> {
    NaverTokenEntity findFirstByOrderByIdDesc();
}
