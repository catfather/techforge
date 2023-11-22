package org.spring.dev.company.config;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Configuration
public class QueryDslConfig {

    @PersistenceContext
    private EntityManager entityManager;

    //빈 등록 해서 전역으로 쓸수 있게
    @Bean
    public JPAQueryFactory  jpaQueryFactory(){
        return new JPAQueryFactory(entityManager);
    }

}
