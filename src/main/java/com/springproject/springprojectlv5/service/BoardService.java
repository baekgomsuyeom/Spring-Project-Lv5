package com.springproject.springprojectlv5.service;

import com.springproject.springprojectlv5.dto.BoardRequestDto;
import com.springproject.springprojectlv5.dto.BoardResponseDto;
import com.springproject.springprojectlv5.dto.CommentResponseDto;
import com.springproject.springprojectlv5.dto.MsgResponseDto;
import com.springproject.springprojectlv5.entity.Board;
import com.springproject.springprojectlv5.entity.BoardLike;
import com.springproject.springprojectlv5.entity.Comment;
import com.springproject.springprojectlv5.entity.User;
import com.springproject.springprojectlv5.exception.CustomException;
import com.springproject.springprojectlv5.repository.BoardLikeRepository;
import com.springproject.springprojectlv5.repository.BoardRepository;
import com.springproject.springprojectlv5.repository.CommentLikeRepository;
import com.springproject.springprojectlv5.repository.UserRepository;
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
    private final UserRepository userRepository;
    private final BoardLikeRepository boardLikeRepository;
//    private final CommentLikeRepository commentLikeRepository;
    private final CommentService commentService;

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

        List<BoardResponseDto> boardResponseDtoList = new ArrayList<>();

        for (Board board : boardList) {
            List<CommentResponseDto> commentList = new ArrayList<>();
            for (Comment comment : board.getCommentList()) {
                commentList.add(new CommentResponseDto(comment, commentService.checkCommentLike(comment.getId(), user)));
            }

            boardResponseDtoList.add(new BoardResponseDto(board, commentList, checkBoardLike(board.getId(), user)));
        }

        return boardResponseDtoList;
    }

    // 게시글 선택 조회
    public BoardResponseDto getBoard(Long boardId, User user) {
        Board board = boardRepository.findById(boardId).orElseThrow (
                () -> new CustomException(NOT_FOUND_BOARD)
        );

        List<CommentResponseDto> commentList = new ArrayList<>();
        for (Comment comment : board.getCommentList()) {
            commentList.add(new CommentResponseDto(comment, commentService.checkCommentLike(comment.getId(), user)));
        }

        return new BoardResponseDto(board, commentList, checkBoardLike(board.getId(), user));
    }

    // 게시글 수정
    @Transactional
    public BoardResponseDto updateBoard(Long boardId, BoardRequestDto requestDto, User user) {

        // 게시글이 있는지 & 사용자의 권한 확인
        Board board = userService.findByBoardIdAndUser(boardId, user);

        board.update(requestDto);

        List<CommentResponseDto> commentList = new ArrayList<>();
        for (Comment comment : board.getCommentList()) {
            commentList.add(new CommentResponseDto(comment, commentService.checkCommentLike(comment.getId(), user)));
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