package org.spring.dev.company.repository.board;

import org.spring.dev.company.entity.board.BoardEntity;
import org.spring.dev.company.entity.board.ReplyEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReplyRepository extends JpaRepository<ReplyEntity, Long> {
    List<ReplyEntity> findAllByBoardEntity(BoardEntity boardEntity);

    List<ReplyEntity> findAllByBoardEntityOrderByIdDesc(BoardEntity boardEntity);
}
