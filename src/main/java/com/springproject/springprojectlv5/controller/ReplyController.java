package com.springproject.springprojectlv5.controller;

import com.springproject.springprojectlv5.dto.*;
import com.springproject.springprojectlv5.security.UserDetailsImpl;
import com.springproject.springprojectlv5.service.ReplyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/comment")
public class ReplyController {
    private final ReplyService replyService;

    // 대댓글 작성
    @PostMapping("/{boardId}/{cmtId}")
    public ResponseEntity<ReplyResponseDto> createReply(@PathVariable Long boardId, @PathVariable Long cmtId, @RequestBody ReplyRequestDto replyRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseEntity.ok(replyService.createReply(boardId, cmtId, replyRequestDto, userDetails.getUser()));
    }

    // 대댓글 수정
    @PutMapping("/{boardId}/{cmtId}/{replyId}")
    public ResponseEntity<ReplyResponseDto> updateReply(@PathVariable Long boardId, @PathVariable Long cmtId, @PathVariable Long replyId, @RequestBody ReplyRequestDto replyRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseEntity.ok(replyService.updateReply(boardId, cmtId, replyId, replyRequestDto, userDetails.getUser()));
    }

    // 대댓글 삭제
    @DeleteMapping("/{boardId}/{cmtId}/{replyId}")
    public ResponseEntity<MsgResponseDto> deleteReply(@PathVariable Long boardId, @PathVariable Long cmtId, @PathVariable Long replyId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseEntity.ok(replyService.deleteReply(boardId, cmtId, replyId, userDetails.getUser()));
    }

    // 대댓글 좋아요
    @PostMapping("/{boardId}/{cmtId}/like/{replyId}")
    public ResponseEntity<MsgResponseDto> saveReplyLike(@PathVariable Long boardId, @PathVariable Long cmtId, @PathVariable Long replyId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseEntity.ok(replyService.saveReplyLike(boardId, cmtId, replyId, userDetails.getUser()));
    }
}
