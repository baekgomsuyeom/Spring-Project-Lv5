package com.springproject.springprojectlv5.service;

import com.springproject.springprojectlv5.dto.CommentRequestDto;
import com.springproject.springprojectlv5.dto.CommentResponseDto;
import com.springproject.springprojectlv5.dto.MsgResponseDto;
import com.springproject.springprojectlv5.entity.Board;
import com.springproject.springprojectlv5.entity.Comment;
import com.springproject.springprojectlv5.entity.User;
import com.springproject.springprojectlv5.exception.CustomException;
import com.springproject.springprojectlv5.repository.BoardRepository;
import com.springproject.springprojectlv5.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.springproject.springprojectlv5.exception.ErrorCode.NOT_FOUND_BOARD;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final BoardRepository boardRepository;
    private final CommentRepository commentRepository;
    private final UserService userService;


    // 댓글 작성
    public CommentResponseDto createComment(Long boardId, CommentRequestDto commentRequestDto, User user) {

        // 게시글이 있는지
        Board board = boardRepository.findById(boardId).orElseThrow (
                () -> new CustomException(NOT_FOUND_BOARD)
        );

        Comment comment = new Comment(commentRequestDto, board, user);
        Comment saveComment = commentRepository.save(comment);

        return new CommentResponseDto(saveComment);
    }

    // 댓글 수정
    @Transactional
    public CommentResponseDto updateComment(Long boardId, Long cmtId, CommentRequestDto commentRequestDto, User user) {

        // 게시글이 있는지
        Board board = boardRepository.findById(boardId).orElseThrow(
                () -> new CustomException(NOT_FOUND_BOARD)
        );

        // 댓글이 있는지 & 사용자의 권한 확인
        Comment comment = userService.findByCmtIdAndUser(cmtId, user);

        comment.update(commentRequestDto);

        return new CommentResponseDto(comment);
    }

    // 댓글 삭제
    public MsgResponseDto deleteComment(Long boardId, Long cmtId, User user) {

        // 게시글이 있는지
        Board board = boardRepository.findById(boardId).orElseThrow (
                () -> new CustomException(NOT_FOUND_BOARD)
        );

        // 댓글이 있는지 & 사용자의 권한 확인
        Comment comment = userService.findByCmtIdAndUser(cmtId, user);

        commentRepository.delete(comment);

        return new MsgResponseDto("댓글을 삭제했습니다.", HttpStatus.OK.value());
    }
}