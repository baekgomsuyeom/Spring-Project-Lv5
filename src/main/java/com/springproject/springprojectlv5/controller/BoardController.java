package com.springproject.springprojectlv5.controller;

import com.springproject.springprojectlv5.dto.BoardRequestDto;
import com.springproject.springprojectlv5.dto.BoardResponseDto;
import com.springproject.springprojectlv5.dto.MsgResponseDto;
import com.springproject.springprojectlv5.security.UserDetailsImpl;
import com.springproject.springprojectlv5.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class BoardController {
    private final BoardService boardService;

    // 게시글 작성
    @PostMapping("/board")
    public ResponseEntity<BoardResponseDto> createBoard(@RequestBody BoardRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseEntity.ok(boardService.createBoard(requestDto, userDetails.getUser()));
    }

    // 게시글 전체 조회
    @GetMapping("/board")
    public ResponseEntity<List<BoardResponseDto>> getBoardList(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseEntity.ok(boardService.getBoardList(userDetails.getUser()));
    }

    // 게시글 선택 조회
    @GetMapping("/board/{boardId}")
    public ResponseEntity<BoardResponseDto> getBoard(@PathVariable Long boardId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseEntity.ok(boardService.getBoard(boardId, userDetails.getUser()));
    }

    // 게시글 수정
    @PutMapping("/board/{boardId}")
    public ResponseEntity<BoardResponseDto> updateBoard(@PathVariable Long boardId, @RequestBody BoardRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseEntity.ok(boardService.updateBoard(boardId, requestDto, userDetails.getUser()));
    }

    // 게시글 삭제
    @DeleteMapping("/board/{boardId}")
    public ResponseEntity<MsgResponseDto> deleteBoard(@PathVariable Long boardId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseEntity.ok(boardService.deleteBoard(boardId, userDetails.getUser()));
    }

    // 게시글 좋아요
    @PostMapping("/board/like/{boardId}")
    public ResponseEntity<MsgResponseDto> saveBoardLike(@PathVariable Long boardId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseEntity.ok(boardService.saveBoardLike(boardId, userDetails.getUser()));
    }
}