package com.springproject.springprojectlv5.repository;

import com.springproject.springprojectlv5.entity.Reply;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ReplyRepository extends JpaRepository<Reply, Long> {
    Optional<Reply> findByIdAndUserId(Long replyId, Long id);
}
