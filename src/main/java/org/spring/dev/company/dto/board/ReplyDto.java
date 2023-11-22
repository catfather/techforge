package org.spring.dev.company.dto.board;

import lombok.*;
import org.spring.dev.company.entity.board.BoardEntity;
import org.spring.dev.company.entity.board.ReplyEntity;
import org.spring.dev.company.entity.member.MemberEntity;
import org.spring.dev.company.entity.util.ApproType;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;


@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReplyDto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reply_id")
    private Long id;
    private String content;
    private String writer;
    private Long boardId;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private BoardEntity boardEntity;
    private MemberEntity memberEntity;

    // 회원정보
    private String name;
    private String approType;

    public static ReplyDto toReplyDto(ReplyEntity replyEntity) {
        ReplyDto replyDto = new ReplyDto();
        replyDto.setId(replyEntity.getId());
        replyDto.setContent(replyEntity.getContent());
        replyDto.setBoardEntity(replyEntity.getBoardEntity());
        replyDto.setMemberEntity(replyEntity.getMemberEntity());
        replyDto.setCreateTime(replyEntity.getCreateTime());
        replyDto.setUpdateTime(replyEntity.getUpdateTime());

        return  replyDto;
    }
}
