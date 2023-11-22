package org.spring.dev.company.scheduler.board;

import lombok.RequiredArgsConstructor;
import org.spring.dev.company.entity.board.BoardEntity;
import org.spring.dev.company.repository.board.BoardRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
@RequiredArgsConstructor
public class BoardScheduler {

    private final BoardRepository boardRepository;

    @Scheduled(cron = "0 0 0 * * ?")
    public void deleteExpiredBoard() {
        Date currentDate = new Date();
        List<BoardEntity> expiredBoards = boardRepository.findByEndDateBefore(currentDate);
        boardRepository.deleteAll(expiredBoards);
    }

}
