package org.spring.dev.company.repository.chatbot;

import org.spring.dev.company.entity.chatbot.AnswerEntity;
import org.spring.dev.company.entity.chatbot.IntentionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IntentionRepository extends JpaRepository<IntentionEntity, Long> {
    Optional<IntentionEntity> findByNameAndUpper(String token, IntentionEntity upper);
}
