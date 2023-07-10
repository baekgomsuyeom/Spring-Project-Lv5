package com.springproject.springprojectlv5.repository;

import com.springproject.springprojectlv5.entity.CommentLike;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentLikeRepository extends JpaRepository<CommentLike, Long> {
    boolean existsByCommentIdAndUserId(Long cmtId, Long userId);
    void deleteByCommentIdAndUserId(Long cmtId, Long userId);
}
