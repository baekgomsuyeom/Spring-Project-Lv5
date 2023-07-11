package com.springproject.springprojectlv5.entity;

import com.springproject.springprojectlv5.dto.ReplyRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(name = "reply")
@NoArgsConstructor
public class Reply extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "reply", nullable = false)
    private String reply;

    @ManyToOne
    @JoinColumn(name = "board_id", nullable = false)
    private Board board;

    @OneToMany(mappedBy = "reply", cascade = CascadeType.REMOVE)
    private List<ReplyLike> replyLikeList = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "comment_id", nullable = false)
    private Comment comment;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // 대댓글 작성
    public Reply(ReplyRequestDto replyRequestDto, Board board, Comment comment, User user) {
        this.reply = replyRequestDto.getReply();
        this.username = user.getUsername();
        this.comment = comment;
        this.board = board;
        this.user = user;
    }

    // 대댓글 수정
    public void update(ReplyRequestDto replyRequestDto) {
        this.reply = replyRequestDto.getReply();
    }
}
