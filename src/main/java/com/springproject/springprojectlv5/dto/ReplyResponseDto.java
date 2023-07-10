package com.springproject.springprojectlv5.dto;

import com.springproject.springprojectlv5.entity.Comment;
import com.springproject.springprojectlv5.entity.Reply;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ReplyResponseDto {
    private Long id;
    private String username;
    private String reply;
    private int replyLikeCnt;
    private boolean replyLikeCheck;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    // 게시글과 함께 조회되는 댓글 및 댓글 작성, 수정
    public ReplyResponseDto(Reply reply, boolean replyLikeCheck) {
        this.id = reply.getId();
        this.username = reply.getUsername();
        this.reply = reply.getReply();
        this.replyLikeCnt = reply.getReplyLikeList().size();
        this.replyLikeCheck = replyLikeCheck;
        this.createdAt = reply.getCreatedAt();
        this.modifiedAt = reply.getModifiedAt();
    }
}
