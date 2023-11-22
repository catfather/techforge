package org.spring.dev.company.dto.board;

import lombok.*;
import org.spring.dev.company.entity.board.BoardEntity;
import org.spring.dev.company.entity.board.FileEntity;
import org.spring.dev.company.entity.board.ReplyEntity;
import org.spring.dev.company.entity.member.MemberEntity;
import org.spring.dev.company.entity.util.BoardType;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.Column;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;


@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BoardDto {
    private Long id;
    private String title;
    private String content;
    private String writer;
    private int isFile;
    private MultipartFile boardFile;
    private int hit;
    private BoardType boardType;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date startDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date endDate;

    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private MemberEntity memberEntity;
    private List<ReplyEntity> replyEntities;
    private List<FileEntity> fileEntities;

    public static BoardDto toBoardDto(BoardEntity boardEntity) {
        return BoardDto.builder()
                .id(boardEntity.getId())
                .title(boardEntity.getTitle())
                .content(boardEntity.getContent())
                .writer(boardEntity.getWriter())
                .boardType(boardEntity.getBoardType())
                .hit(boardEntity.getHit())
                .memberEntity(boardEntity.getMemberEntity())
                .createTime(boardEntity.getCreateTime())
                .build();
    }
}
