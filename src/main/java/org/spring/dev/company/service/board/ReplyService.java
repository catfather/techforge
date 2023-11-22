package org.spring.dev.company.service.board;

import lombok.RequiredArgsConstructor;
import org.spring.dev.company.config.MyUserDetails;
import org.spring.dev.company.dto.board.ReplyDto;
import org.spring.dev.company.entity.board.BoardEntity;
import org.spring.dev.company.entity.board.ReplyEntity;
import org.spring.dev.company.entity.member.MemberEntity;
import org.spring.dev.company.repository.board.BoardRepository;
import org.spring.dev.company.repository.board.ReplyRepository;
import org.spring.dev.company.repository.member.MemberRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReplyService {

    private final BoardRepository boardRepository;
    private final ReplyRepository replyRepository;
    private final MemberRepository memberRepository;


    public Integer write(ReplyDto replyDto, MyUserDetails myUserDetails) {

        BoardEntity boardEntity = boardRepository.findById(replyDto.getBoardId()).orElseThrow(()->{
            throw new IllegalArgumentException("게시글 아이디가 존재하지 않습니다.");
        });

        MemberEntity memberEntity = memberRepository.findByEmail(myUserDetails.getMemberEntity().getEmail()).orElseThrow(()->{
            throw new IllegalArgumentException("아이디가 존재하지 않습니다.");
        });

        ReplyEntity replyEntity = ReplyEntity.builder()
                .id(replyDto.getId())
                .content(replyDto.getContent())
                .boardEntity(boardEntity)
                .memberEntity(memberEntity)
                .build();

        Long replyId = replyRepository.save(replyEntity).getId();
        Optional<ReplyEntity> replyEntityOptional = replyRepository.findById(replyId);
        if(replyEntityOptional.isPresent()){
            return 0;
        } return 1;

    }


    public List<ReplyDto> list(Long id) {

        BoardEntity boardEntity = boardRepository.findById(id).orElseThrow(() -> {
            throw new IllegalArgumentException("게시글이 존재하지 않습니다.");
        });

        List<ReplyDto> replyDtoList = new ArrayList<>();
        List<ReplyEntity> replyEntityList = replyRepository.findAllByBoardEntity(boardEntity);

        for (ReplyEntity replyEntity : replyEntityList) {
            ReplyDto replyDto = ReplyDto.builder()
                    .id(replyEntity.getId())
                    .content(replyEntity.getContent())
                    .memberEntity(replyEntity.getMemberEntity())
                    .createTime(replyEntity.getCreateTime())
                    .boardId(replyEntity.getBoardEntity().getId())
                    .build();
            replyDtoList.add(replyDto);
        }

        return replyDtoList;


    }

    public ResponseEntity<ReplyEntity> update(Long id, ReplyDto replyDto) {

        ReplyEntity replyEntity = replyRepository.findById(replyDto.getId())
                .orElseThrow(() -> new IllegalArgumentException("댓글을 찾을 수 없습니다."));

        ReplyDto replyDto1 = ReplyDto.builder()
                .id(replyEntity.getId())
                .content(replyEntity.getContent())
                .createTime(replyEntity.getCreateTime())
                .updateTime(replyEntity.getUpdateTime())
                .build();

        replyEntity.setContent(replyDto.getContent());

        return ResponseEntity.ok(replyRepository.save(replyEntity));

    }


    public void delete(Long id) {

        ReplyEntity replyEntity = replyRepository.findById(id).orElseThrow(() -> {
            throw new IllegalArgumentException("댓글이 존재하지 않습니다.");
        });

        replyRepository.delete(replyEntity);

    }

    public List<ReplyDto> replyFindAll(Long boardId) {

        BoardEntity boardEntity = boardRepository.findById(boardId).orElseThrow(() -> {
            throw new IllegalArgumentException("게시글이 존재하지 않습니다.");
        });

        List<ReplyDto> replyDtoList = new ArrayList<>();
        List<ReplyEntity> replyEntityList = replyRepository.findAllByBoardEntity(boardEntity);

        for (ReplyEntity replyEntity : replyEntityList) {
            ReplyDto replyDto = ReplyDto.builder()
                    .id(replyEntity.getId())
                    .content(replyEntity.getContent())
                    .approType(replyEntity.getMemberEntity().getGrade().toString())
                    .name(replyEntity.getMemberEntity().getName())
                    .createTime(replyEntity.getCreateTime())
                    .boardId(replyEntity.getBoardEntity().getId())
                    .build();
            replyDtoList.add(replyDto);
        }

        return replyDtoList;
    }
}
