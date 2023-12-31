package com.springproject.springprojectlv5.service;

import com.springproject.springprojectlv5.dto.*;
import com.springproject.springprojectlv5.entity.*;
import com.springproject.springprojectlv5.exception.CustomException;
import com.springproject.springprojectlv5.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static com.springproject.springprojectlv5.exception.ErrorCode.NOT_FOUND_BOARD;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;
    private final UserService userService;
    private final BoardLikeRepository boardLikeRepository;
    private final CommentService commentService;
    private final ReplyService replyService;

    // 게시글 작성
    public BoardResponseDto createBoard(BoardRequestDto requestDto, User user) {
        Board board = new Board(requestDto, user);
        Board saveBoard = boardRepository.save(board);

        return new BoardResponseDto(saveBoard);
    }

    // 게시글 전체 조회
    @Transactional(readOnly = true)
    public List<BoardResponseDto> getBoardList(User user) {
        List<Board> boardList = boardRepository.findAllByOrderByCreatedAtDesc();

        // 게시글
        List<BoardResponseDto> boardResponseDtoList = new ArrayList<>();
        for (Board board : boardList) {
            // 댓글
            List<CommentResponseDto> commentList = new ArrayList<>();
            for (Comment comment : board.getCommentList()) {
                // 대댓글
                List<ReplyResponseDto> replyResponseDtoList = new ArrayList<>();
                for (Reply reply : comment.getReplyList()) {
                    replyResponseDtoList.add(new ReplyResponseDto(reply, replyService.checkReplyLike(reply.getId(), user)));
                }

                commentList.add(new CommentResponseDto(comment, replyResponseDtoList, commentService.checkCommentLike(comment.getId(), user)));
            }

            boardResponseDtoList.add(new BoardResponseDto(board, commentList, checkBoardLike(board.getId(), user)));
        }

        return boardResponseDtoList;
    }

    // 게시글 선택 조회
    public BoardResponseDto getBoard(Long boardId, User user) {
        // 게시글이 있는지
        Board board = boardRepository.findById(boardId).orElseThrow (
                () -> new CustomException(NOT_FOUND_BOARD)
        );

        // 댓글
        List<CommentResponseDto> commentList = new ArrayList<>();
        for (Comment comment : board.getCommentList()) {
            // 대댓글
            List<ReplyResponseDto> replyResponseDtoList = new ArrayList<>();
            for (Reply reply : comment.getReplyList()) {
                replyResponseDtoList.add(new ReplyResponseDto(reply, replyService.checkReplyLike(reply.getId(), user)));
            }

            commentList.add(new CommentResponseDto(comment, replyResponseDtoList, commentService.checkCommentLike(comment.getId(), user)));
        }

        return new BoardResponseDto(board, commentList, checkBoardLike(board.getId(), user));
    }

    // 게시글 수정
    @Transactional
    public BoardResponseDto updateBoard(Long boardId, BoardRequestDto requestDto, User user) {
        // 게시글이 있는지 & 사용자의 권한 확인
        Board board = userService.findByBoardIdAndUser(boardId, user);

        board.update(requestDto);

        // 댓글
        List<CommentResponseDto> commentList = new ArrayList<>();
        for (Comment comment : board.getCommentList()) {
            // 대댓글
            List<ReplyResponseDto> replyResponseDtoList = new ArrayList<>();
            for (Reply reply : comment.getReplyList()) {
                replyResponseDtoList.add(new ReplyResponseDto(reply, replyService.checkReplyLike(reply.getId(), user)));
            }

            commentList.add(new CommentResponseDto(comment, replyResponseDtoList, commentService.checkCommentLike(comment.getId(), user)));
        }

        return new BoardResponseDto(board, commentList, checkBoardLike(board.getId(), user));
    }

    // 게시글 삭제
    public MsgResponseDto deleteBoard(Long boardId, User user) {
        // 게시글이 있는지 & 사용자의 권한 확인
        Board board = userService.findByBoardIdAndUser(boardId, user);

        boardRepository.delete(board);

        return new MsgResponseDto("게시글을 삭제했습니다.", HttpStatus.OK.value());
    }

    // 게시글 좋아요 유/무 (false 면 좋아요 취소, true 면 좋아요)
    @Transactional
    public boolean checkBoardLike(Long boardId, User user) {
        return boardLikeRepository.existsByBoardIdAndUserId(boardId, user.getId());
    }

    // 게시글 좋아요 개수
    @Transactional
    public MsgResponseDto saveBoardLike(Long boardId, User user) {
        // 게시글이 있는지
        Board board = boardRepository.findById(boardId).orElseThrow (
                () -> new CustomException(NOT_FOUND_BOARD)
        );

        if (!checkBoardLike(boardId, user)) {
            BoardLike boardLike = new BoardLike(board, user);
            boardLikeRepository.save(boardLike);
            return new MsgResponseDto("게시글 좋아요", HttpStatus.OK.value());
        } else {
            boardLikeRepository.deleteByBoardIdAndUserId(boardId, user.getId());
            return new MsgResponseDto("게시글 좋아요 취소", HttpStatus.OK.value());
        }
    }
}