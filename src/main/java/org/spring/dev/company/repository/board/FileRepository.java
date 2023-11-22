package org.spring.dev.company.repository.board;

import org.spring.dev.company.entity.board.FileEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FileRepository extends JpaRepository<FileEntity, Long> {
    Optional<FileEntity> findByBoardEntityId(Long id);
}
