package org.spring.dev.company.repository.chatbot;

import org.spring.dev.company.entity.chatbot.AnswerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnswerRepository extends JpaRepository<AnswerEntity, Long> {
}
