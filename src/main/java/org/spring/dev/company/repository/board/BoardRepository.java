package org.spring.dev.company.repository.board;

import org.spring.dev.company.entity.board.BoardEntity;
import org.spring.dev.company.entity.util.BoardType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface BoardRepository extends JpaRepository<BoardEntity, Long> {

    @Modifying
    @Query(value = "update BoardEntity b set b.hit=b.hit+1 where b.id=:id")
    void hit(@Param("id") Long id);


    Page<BoardEntity> findByTitleContaining(String search, Pageable pageable);

    Page<BoardEntity> findByContentContaining(String search, Pageable pageable);

    Page<BoardEntity> findByWriterContaining(String search, Pageable pageable);

    Page<BoardEntity> findByBoardType(BoardType boardType, Pageable pageable);

    Page<BoardEntity> findByBoardTypeAndTitleContaining(BoardType boardType, String search, Pageable pageable);

    Page<BoardEntity> findByBoardTypeAndContentContaining(BoardType boardType, String search, Pageable pageable);

    Page<BoardEntity> findByBoardTypeAndWriterContaining(BoardType boardType, String search, Pageable pageable);

    List<BoardEntity> findByEndDateBefore(Date currentDate);
}
