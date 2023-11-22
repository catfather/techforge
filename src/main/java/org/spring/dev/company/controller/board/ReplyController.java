package org.spring.dev.company.controller.board;

import lombok.RequiredArgsConstructor;
import org.spring.dev.company.config.MyUserDetails;
import org.spring.dev.company.dto.board.ReplyDto;
import org.spring.dev.company.dto.member.MemberDto;
import org.spring.dev.company.service.board.BoardService;
import org.spring.dev.company.service.board.ReplyService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/reply")
@RequiredArgsConstructor
public class ReplyController {

    private final ReplyService replyService;

    @PostMapping("/write")
    public ResponseEntity<Integer> write(@AuthenticationPrincipal MyUserDetails myUserDetails,
                                         @RequestBody ReplyDto replyDto){
        int replyRs = replyService.write(replyDto, myUserDetails);
        return new ResponseEntity<>(replyRs, HttpStatus.OK);
    }

    @GetMapping("/list/{boardId}")
    public ResponseEntity<List<ReplyDto>> memberList(@PathVariable("boardId") Long boardId){
        List<ReplyDto> replyDtos = replyService.replyFindAll(boardId);
        return new ResponseEntity<>(replyDtos,HttpStatus.OK);
    }

    @PatchMapping("/update/{id}")
    public ResponseEntity<String> update(@PathVariable("id") Long id, @RequestBody ReplyDto replyDto)  {
        replyService.update(id, replyDto);
        return ResponseEntity.ok("success");
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteReply(@PathVariable("id") Long id) {
        replyService.delete(id);
        return ResponseEntity.ok("success");
    }

}
