package com.springproject.springprojectlv5.dto;

import com.springproject.springprojectlv5.entity.Comment;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CommentResponseDto {
    private Long id;
    private String username;
    private String comment;
    private int commentLikeCnt;
    private boolean commentLikeCheck;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    // 게시글과 함꼐 조회되는 댓글 및 댓글 작성, 수정
    public CommentResponseDto(Comment comment, boolean commentLikeCheck) {
        this.id = comment.getId();
        this.username = comment.getUsername();
        this.comment = comment.getComment();
        this.commentLikeCnt = comment.getCommentLikeList().size();
        this.commentLikeCheck = commentLikeCheck;
        this.createdAt = comment.getCreatedAt();
        this.modifiedAt = comment.getModifiedAt();
    }
}