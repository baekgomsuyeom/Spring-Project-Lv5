package com.springproject.springprojectlv5.controller;

import com.springproject.springprojectlv5.dto.CommentRequestDto;
import com.springproject.springprojectlv5.dto.CommentResponseDto;
import com.springproject.springprojectlv5.dto.MsgResponseDto;
import com.springproject.springprojectlv5.security.UserDetailsImpl;
import com.springproject.springprojectlv5.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/comment")
public class CommentController {
    private final CommentService commentService;

    // 댓글 작성
    @PostMapping("/{boardId}")
    public ResponseEntity<CommentResponseDto> createComment(@PathVariable Long boardId, @RequestBody CommentRequestDto commentRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseEntity.ok(commentService.createComment(boardId, commentRequestDto, userDetails.getUser()));
    }

    // 댓글 수정
    @PutMapping("/{boardId}/{cmtId}")
    public ResponseEntity<CommentResponseDto> updateComment(@PathVariable Long boardId, @PathVariable Long cmtId, @RequestBody CommentRequestDto commentRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseEntity.ok(commentService.updateComment(boardId, cmtId, commentRequestDto, userDetails.getUser()));
    }

    // 댓글 삭제
    @DeleteMapping("/{boardId}/{cmtId}")
    public ResponseEntity<MsgResponseDto> deleteComment(@PathVariable Long boardId, @PathVariable Long cmtId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseEntity.ok(commentService.deleteComment(boardId, cmtId, userDetails.getUser()));
    }
}

