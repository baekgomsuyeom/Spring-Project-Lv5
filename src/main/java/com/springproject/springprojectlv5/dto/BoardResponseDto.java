package com.springproject.springprojectlv5.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.springproject.springprojectlv5.entity.Board;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BoardResponseDto {
    private Long id;
    private String title;
    private String username;
    private String contents;
    private int boardLikeCnt;
    private boolean boardLikeCheck;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private List<CommentResponseDto> commentList = new ArrayList<>();       // 게시글 조회 시, 댓글 목록도 함께 조회

    // 게시글 작성
    public BoardResponseDto(Board board) {
        this.id = board.getId();
        this.title = board.getTitle();
        this.username = board.getUsername();
        this.contents = board.getContents();
        this.createdAt = board.getCreatedAt();
        this.modifiedAt = board.getModifiedAt();
    }

    // 게시글 전체/선택 조회, 수정
    public BoardResponseDto(Board board, List<CommentResponseDto> commentList, boolean boardLikeCheck) {
        this.id = board.getId();
        this.title = board.getTitle();
        this.username = board.getUsername();
        this.contents = board.getContents();
        this.boardLikeCnt = board.getBoardLikeList().size();
        this.boardLikeCheck = boardLikeCheck;
        this.createdAt = board.getCreatedAt();
        this.modifiedAt = board.getModifiedAt();
        this.commentList = commentList;
    }
}