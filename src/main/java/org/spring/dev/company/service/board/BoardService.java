package org.spring.dev.company.service.board;

import lombok.RequiredArgsConstructor;
import org.spring.dev.company.config.MyUserDetails;
import org.spring.dev.company.dto.board.BoardDto;
import org.spring.dev.company.entity.board.BoardEntity;
import org.spring.dev.company.entity.board.FileEntity;
import org.spring.dev.company.entity.member.MemberEntity;
import org.spring.dev.company.repository.board.BoardRepository;
import org.spring.dev.company.repository.board.FileRepository;
import org.spring.dev.company.repository.member.MemberRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.File;
import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;
    private final FileRepository fileRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public String boardWrite(BoardDto boardDto, MyUserDetails myUserDetails) throws IOException {

        MemberEntity memberEntity = memberRepository.findByEmail(myUserDetails.getMemberEntity().getEmail()).orElseThrow(()->{
            throw new IllegalArgumentException("아이디가 존재하지 않습니다.");
        });


        if(boardDto.getBoardFile().isEmpty()){

            BoardEntity boardEntity = BoardEntity.builder()
                    .id(boardDto.getId())
                    .title(boardDto.getTitle())
                    .content(boardDto.getContent())
                    .writer(boardDto.getWriter())
                    .boardType(boardDto.getBoardType())
                    .startDate(boardDto.getStartDate())
                    .endDate(boardDto.getEndDate())
                    .hit(0)
                    .isFile(0)
                    .memberEntity(memberEntity)
                    .build();
            Long boardId = boardRepository.save(boardEntity).getId();
            BoardEntity boardEntity1 = boardRepository.findById(boardId).orElseThrow(()->{
                throw new IllegalArgumentException("게시글 작성을 실패했습니다.");
            });

            if(boardEntity1.getBoardType().toString().equals("GENERAL")){
                return "GENERAL";
            } else {
                return "INTERNAL";
            }

        }else {
            MultipartFile boardFile = boardDto.getBoardFile();
            String oldName = boardFile.getOriginalFilename();
            UUID uuid = UUID.randomUUID();
            String newName = uuid + "_" + oldName;
            String savePath = "C:/saveFiles/" + newName;
            boardFile.transferTo(new File(savePath));

            BoardEntity boardEntity = BoardEntity.builder()
                    .id(boardDto.getId())
                    .title(boardDto.getTitle())
                    .content(boardDto.getContent())
                    .writer(boardDto.getWriter())
                    .boardType(boardDto.getBoardType())
                    .startDate(boardDto.getStartDate())
                    .endDate(boardDto.getEndDate())
                    .hit(0)
                    .isFile(1)
                    .memberEntity(memberEntity)
                    .build();
            Long boardId = boardRepository.save(boardEntity).getId();
            BoardEntity boardEntity1 = boardRepository.findById(boardId).orElseThrow(()->{
                throw new IllegalArgumentException("게시글 작성을 실패했습니다.");
            });



            FileEntity fileEntity = FileEntity.builder()
                    .boardEntity(boardEntity)
                    .newName(newName)
                    .oldName(oldName)
                    .build();
            Long fileId = fileRepository.save(fileEntity).getId();
            fileRepository.findById(fileId).orElseThrow(()->{
                throw new IllegalArgumentException("파일 등록을 실패했습니다.");
            });

            if(boardEntity1.getBoardType().toString().equals("GENERAL")){
                return "GENERAL";
            } else {
                return "INTERNAL";
            }

        }

    }

    public Page<BoardDto> boardList(Pageable pageable) {
        return boardRepository.findAll(pageable).map(BoardDto :: toBoardDto);
    }

    @Transactional
    public void updateHit(Long id){
        boardRepository.hit(id);
    }

    @Transactional
    public BoardDto detail(Long id) {

        updateHit(id);

        BoardEntity boardEntity = boardRepository.findById(id).orElseThrow(()->{
            throw new IllegalArgumentException("조회할 게시글이 존재하지 않습니다.");
        });

        BoardDto boardDto = BoardDto.builder()
                .id(boardEntity.getId())
                .title(boardEntity.getTitle())
                .content(boardEntity.getContent())
                .writer(boardEntity.getWriter())
                .fileEntities(boardEntity.getFileEntities())
                .isFile(boardEntity.getIsFile())
                .boardType(boardEntity.getBoardType())
                .startDate(boardEntity.getStartDate())
                .endDate(boardEntity.getEndDate())
                .memberEntity(boardEntity.getMemberEntity())
                .createTime(boardEntity.getCreateTime())
                .updateTime(boardEntity.getUpdateTime())
                .hit(boardEntity.getHit())
                .replyEntities(boardEntity.getReplyEntities())
                .build();

        return boardDto;
    }

    @Transactional
    public void delete(Long id) {

        BoardEntity boardEntity = boardRepository.findById(id).orElseThrow(()->{
            throw new IllegalArgumentException("삭제할 게시글이 존재하지 않습니다.");
        });

        boardRepository.delete(boardEntity);

    }

    @Transactional
    public BoardDto update(Long id) {

        BoardEntity boardEntity = boardRepository.findById(id).orElseThrow(()->{
            throw new IllegalArgumentException("수정할 게시글이 존재하지 않습니다.");
        });

        return BoardDto.builder()
                .id(boardEntity.getId())
                .title(boardEntity.getTitle())
                .content(boardEntity.getContent())
                .writer(boardEntity.getWriter())
                .boardType(boardEntity.getBoardType())
                .startDate(boardEntity.getStartDate())
                .endDate(boardEntity.getEndDate())
                .fileEntities(boardEntity.getFileEntities())
                .isFile(boardEntity.getIsFile())
                .memberEntity(boardEntity.getMemberEntity())
                .hit(boardEntity.getHit())
                .replyEntities(boardEntity.getReplyEntities())
                .build();

    }


    @Transactional
    public void updateOk(BoardDto boardDto) throws IOException {

        BoardEntity boardEntity = boardRepository.findById(boardDto.getId())
                .orElseThrow(() -> new IllegalArgumentException("수정할 게시글이 존재하지 않습니다."));

        Optional<FileEntity> optionalFileEntity = fileRepository.findByBoardEntityId(boardDto.getId());

        if(optionalFileEntity.isPresent()) {
            String fileNewName = optionalFileEntity.get().getNewName();
            String filePath = "C:/saveFiles/" + fileNewName;
            File deleteFile = new File(filePath);

            if(deleteFile.exists()) {
                deleteFile.delete();
                System.out.println("파일을 삭제하였습니다.");
            } else {
                System.out.println("파일이 존재하지 않습니다.");
            }
            fileRepository.delete(optionalFileEntity.get());
        }


        boardEntity.setWriter(boardDto.getWriter());
        boardEntity.setTitle(boardDto.getTitle());
        boardEntity.setContent(boardDto.getContent());

        boardRepository.save(boardEntity);


        Optional<BoardEntity> optionalBoardEntity = boardRepository.findById(boardDto.getId());


        if (boardDto.getBoardFile().isEmpty()) {
            // 파일 없는 경우
            boardEntity.setWriter(boardDto.getWriter());
            boardEntity.setTitle(boardDto.getTitle());
            boardEntity.setContent(boardDto.getContent());
            boardEntity.setIsFile(0);
            boardRepository.save(boardEntity);

        } else {
            // 파일 있는 경우
            MultipartFile boardFile = boardDto.getBoardFile();
            String fileOldName = boardFile.getOriginalFilename();
            UUID uuid = UUID.randomUUID();
            String fileNewName = uuid + "_" + fileOldName;
            String savePath = "C:/saveFiles/" + fileNewName;
            boardFile.transferTo(new File(savePath));

            boardEntity.setWriter(boardDto.getWriter());
            boardEntity.setTitle(boardDto.getTitle());
            boardEntity.setContent(boardDto.getContent());
            boardEntity.setIsFile(1);
            boardRepository.save(boardEntity);

            FileEntity bFileEntity = FileEntity.builder()
                    .boardEntity(boardEntity)
                    .newName(fileNewName)
                    .oldName(fileOldName)
                    .build();
            Long fileId = fileRepository.save(bFileEntity).getId();
            fileRepository.findById(fileId).orElseThrow(()->{
                throw new IllegalArgumentException("파일등록에 실패하였습니다.");
            });
        }

        boardRepository.findById(boardDto.getId()).orElseThrow(() -> {
            throw new IllegalArgumentException("게시글 수정에 실패하였습니다.");
        });


    }
}
