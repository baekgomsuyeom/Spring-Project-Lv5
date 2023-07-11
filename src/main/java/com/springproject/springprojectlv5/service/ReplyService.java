package com.springproject.springprojectlv5.service;

import com.springproject.springprojectlv5.dto.*;
import com.springproject.springprojectlv5.entity.*;
import com.springproject.springprojectlv5.exception.CustomException;
import com.springproject.springprojectlv5.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.springproject.springprojectlv5.exception.ErrorCode.*;

@Service
@RequiredArgsConstructor
public class ReplyService {
    private final BoardRepository boardRepository;
    private final CommentRepository commentRepository;
    private final UserService userService;
    private final ReplyRepository replyRepository;
    private final ReplyLikeRepository replyLikeRepository;

    // 대댓글 작성
    public ReplyResponseDto createReply(Long boardId, Long cmtId, ReplyRequestDto replyRequestDto, User user) {
        // 게시글이 있는지
        Board board = boardRepository.findById(boardId).orElseThrow (
                () -> new CustomException(NOT_FOUND_BOARD)
        );

        // 댓글이 있는지
        Comment comment = commentRepository.findById(cmtId).orElseThrow(
                () -> new CustomException(NOT_FOUND_COMMENT)
        );

        Reply reply = new Reply(replyRequestDto, board, comment, user);
        Reply saveReply = replyRepository.save(reply);

        return new ReplyResponseDto(saveReply, checkReplyLike(reply.getId(), user));
    }

    // 대댓글 수정
    @Transactional
    public ReplyResponseDto updateReply(Long boardId, Long cmtId, Long replyId, ReplyRequestDto replyRequestDto, User user) {
        // 게시글이 있는지
        Board board = boardRepository.findById(boardId).orElseThrow (
                () -> new CustomException(NOT_FOUND_BOARD)
        );

        // 댓글이 있는지
        Comment comment = commentRepository.findById(cmtId).orElseThrow (
                () -> new CustomException(NOT_FOUND_COMMENT)
        );

        // 대댓글이 있는지 & 사용자의 권한 확인
        Reply reply = userService.findByReplyIdAndUser(replyId, user);

        reply.update(replyRequestDto);

        return new ReplyResponseDto(reply, checkReplyLike(reply.getId(), user));
    }

    // 대댓글 삭제
    public MsgResponseDto deleteReply(Long boardId, Long cmtId, Long replyId, User user) {
        // 게시글이 있는지
        Board board = boardRepository.findById(boardId).orElseThrow (
                () -> new CustomException(NOT_FOUND_BOARD)
        );

        // 댓글이 있는지
        Comment comment = commentRepository.findById(cmtId).orElseThrow (
                () -> new CustomException(NOT_FOUND_COMMENT)
        );

        // 대댓글이 있는지 & 사용자의 권한 확인
        Reply reply = userService.findByReplyIdAndUser(replyId, user);

        replyRepository.delete(reply);

        return new MsgResponseDto("대댓글을 삭제했습니다.", HttpStatus.OK.value());
    }

    // 대댓글 좋아요 유/무 (false 면 좋아요 취소, true 면 좋아요)
    @Transactional
    public boolean checkReplyLike(Long replyId, User user) {
        return replyLikeRepository.existsByReplyIdAndUserId(replyId, user.getId());
    }

    // 대댓글 좋아요 개수
    @Transactional
    public MsgResponseDto saveReplyLike(Long boardId, Long cmtId, Long replyId, User user) {
        // 게시글이 있는지
        Board board = boardRepository.findById(boardId).orElseThrow (
                () -> new CustomException(NOT_FOUND_BOARD)
        );

        // 댓글이 있는지
        Comment comment = commentRepository.findById(cmtId).orElseThrow(
                () -> new CustomException(NOT_FOUND_COMMENT)
        );

        // 대댓글이 있는지
        Reply reply = replyRepository.findById(replyId).orElseThrow(
                () -> new CustomException(NOT_FOUND_REPLY)
        );

        if (!checkReplyLike(cmtId, user)) {
            ReplyLike replyLike = new ReplyLike(board, comment, reply, user);
            replyLikeRepository.save(replyLike);
            return new MsgResponseDto("대댓글 좋아요", HttpStatus.OK.value());
        } else {
            replyLikeRepository.deleteByReplyIdAndUserId(replyId, user.getId());
            return new MsgResponseDto("대댓글 좋아요 취소", HttpStatus.OK.value());
        }
    }
}
