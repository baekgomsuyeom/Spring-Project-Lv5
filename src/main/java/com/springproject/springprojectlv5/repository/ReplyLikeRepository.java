package com.springproject.springprojectlv5.repository;

import com.springproject.springprojectlv5.entity.ReplyLike;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReplyLikeRepository extends JpaRepository<ReplyLike, Long> {

    boolean existsByReplyIdAndUserId(Long replyId, Long id);

    void deleteByReplyIdAndUserId(Long replyId, Long id);
}
