package com.springproject.springprojectlv5.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "replyLike")
@NoArgsConstructor
public class ReplyLike {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "board_id", nullable = false)
    private Board board;

    @ManyToOne
    @JoinColumn(name = "comment_id", nullable = false)
    private Comment comment;

    @ManyToOne
    @JoinColumn(name = "reply_id", nullable = false)
    private Reply reply;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public ReplyLike(Board board, Comment comment, Reply reply, User user) {
        this.board = board;
        this.comment = comment;
        this.reply = reply;
        this.user = user;
    }
}
