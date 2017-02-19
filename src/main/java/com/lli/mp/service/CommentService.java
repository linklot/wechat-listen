package com.lli.mp.service;

import com.lli.mp.controller.model.CommentRequestModel;
import com.lli.mp.controller.model.CommentResponseModel;

import java.util.List;

public interface CommentService {
	void createComment(String userId, String userNickname, CommentRequestModel commentModel);
	List<CommentResponseModel> findComments(String audioId);
	void updateShowHide(String audioId, List<String> commentIds);
	List<CommentResponseModel> findNonHiddenComments(String audioId);
}
