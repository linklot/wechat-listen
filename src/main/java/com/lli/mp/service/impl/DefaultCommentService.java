package com.lli.mp.service.impl;

import com.lli.mp.controller.model.CommentRequestModel;
import com.lli.mp.controller.model.CommentResponseModel;
import com.lli.mp.entity.Comment;
import com.lli.mp.repository.CommentRepository;
import com.lli.mp.service.CommentService;
import org.joda.time.DateTime;
import org.joda.time.format.ISODateTimeFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DefaultCommentService implements CommentService {
	private CommentRepository commentRepository;

	@Autowired
	public DefaultCommentService(CommentRepository commentRepository) {
		this.commentRepository = commentRepository;
	}

	@Override
	public void createComment(String userId, String userNickname, CommentRequestModel commentModel) {
		Comment comment = new Comment();
		comment.userId = userId;
		comment.userNickname = userNickname;
		comment.audioId = commentModel.audioId;
		comment.comment = commentModel.comment;
		comment.time = DateTime.now().toString(ISODateTimeFormat.dateTime());

		commentRepository.save(comment);
	}

	@Override
	public List<CommentResponseModel> findComments(String audioId) {
		List<Comment> comments = commentRepository.findByAudioId(audioId, new Sort("time"));
		return comments.stream().map(comment -> {
			CommentResponseModel model = new CommentResponseModel();
			model.id = comment.id;
			model.userId = comment.userId;
			model.userNickname = comment.userNickname;
			model.audioId = comment.audioId;
			model.comment = comment.comment;
			model.time = comment.time;
			return model;
		}).collect(Collectors.toList());
	}
}
