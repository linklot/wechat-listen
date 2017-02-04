package com.lli.mp.repository;

import com.lli.mp.entity.Comment;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface CommentRepository extends MongoRepository<Comment, String> {
	List<Comment> findByAudioId(String audioId, Sort sort);
}
