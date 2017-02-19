package com.lli.mp.service.impl;

import com.lli.mp.controller.model.CommentRequestModel;
import com.lli.mp.controller.model.CommentResponseModel;
import com.lli.mp.entity.Comment;
import com.lli.mp.repository.CommentRepository;
import com.lli.mp.repository.CommentRepositoryImpl;
import com.lli.mp.service.CommentService;
import com.lli.mp.utils.DateTimeUtils;
import org.apache.commons.lang3.StringUtils;
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
	private CommentRepositoryImpl commentRepositoryImpl;

	@Autowired
	public DefaultCommentService(CommentRepository commentRepository, CommentRepositoryImpl commentRepositoryImpl) {
		this.commentRepository = commentRepository;
		this.commentRepositoryImpl = commentRepositoryImpl;
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
			model.friendlyTime = StringUtils.isEmpty(comment.time) ? "" : DateTimeUtils.toCommentFriendlyString(comment.time);
			model.hidden = comment.hidden;
			return model;
		}).collect(Collectors.toList());
	}

	@Override
	public void updateShowHide(String audioId, List<String> commentIds) {
		commentRepositoryImpl.updateShowHide(audioId, commentIds);
	}

	@Override
	public List<CommentResponseModel> findNonHiddenComments(String audioId) {
		List<Comment> comments = commentRepositoryImpl.findNonHiddenCommentsByAudioId(audioId);
		return comments.stream().map(comment -> {
			CommentResponseModel model = new CommentResponseModel();
			model.id = comment.id;
			model.userId = comment.userId;
			model.userNickname = comment.userNickname;
			model.audioId = comment.audioId;
			model.comment = comment.comment;
			model.time = comment.time;
			model.friendlyTime = StringUtils.isEmpty(comment.time) ? "" : DateTimeUtils.toCommentFriendlyString(comment.time);
			model.hidden = comment.hidden;
			return model;
		}).collect(Collectors.toList());
	}
}
