package com.springproject.springprojectlv5.dto;

import com.springproject.springprojectlv5.entity.Comment;
import com.springproject.springprojectlv5.entity.Reply;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
public class CommentResponseDto {
    private Long id;
    private String username;
    private String comment;
    private int commentLikeCnt;
    private boolean commentLikeCheck;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private List<ReplyResponseDto> replyList = new ArrayList<>();       // 게시글 조회 시, 댓글 목록도 함께 조회

    // 댓글 작성, 수정
    public CommentResponseDto(Comment comment, boolean commentLikeCheck) {
        this.id = comment.getId();
        this.username = comment.getUsername();
        this.comment = comment.getComment();
        this.commentLikeCnt = comment.getCommentLikeList().size();
        this.commentLikeCheck = commentLikeCheck;
        this.createdAt = comment.getCreatedAt();
        this.modifiedAt = comment.getModifiedAt();
    }

    // 게시글과 함께 조회되는 댓글 & 대댓글
    public CommentResponseDto(Comment comment, List<ReplyResponseDto> replyList, boolean commentLikeCheck) {
        this.id = comment.getId();
        this.username = comment.getUsername();
        this.comment = comment.getComment();
        this.commentLikeCnt = comment.getCommentLikeList().size();
        this.commentLikeCheck = commentLikeCheck;
        this.createdAt = comment.getCreatedAt();
        this.modifiedAt = comment.getModifiedAt();
        this.replyList = replyList;
    }
}