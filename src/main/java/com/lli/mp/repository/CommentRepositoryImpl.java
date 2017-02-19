package com.lli.mp.repository;

import com.lli.mp.entity.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CommentRepositoryImpl {
	private static final String COLLECTION = "comment";
	private MongoTemplate mongoTemplate;

	@Autowired
	public CommentRepositoryImpl(MongoTemplate mongoTemplate) {
		this.mongoTemplate = mongoTemplate;
	}

	public long countByAudioId(String audioId) {
		Query query = new Query();
		query.addCriteria(Criteria.where("audioId").is(audioId));
		return mongoTemplate.count(query, COLLECTION);
	}

	public void updateShowHide(String audioId, List<String> commentIds) {
		Query query = new Query(Criteria.where("audioId").is(audioId));
		List<Comment> comments = mongoTemplate.find(query, Comment.class);
		comments.forEach(comment -> {
			Update update = new Update();
			if(commentIds.contains(comment.id)) {
				update.set("hidden", true);
			} else {
				update.set("hidden", false);
			}
			Query updateQuery = new Query(Criteria.where("id").is(comment.id));
			mongoTemplate.updateFirst(updateQuery, update, Comment.class);
		});
	}

	public List<Comment> findNonHiddenCommentsByAudioId(String audioId) {
		System.out.println("here!");
		Query query = new Query()
				.addCriteria(Criteria.where("audioId").is(audioId))
				.addCriteria(Criteria.where("hidden").is(false))
				.with(new Sort("time"));

		return mongoTemplate.find(query, Comment.class);
	}
}
