(function () {
	"use strict";

	LLI.audioPlayed = false;

	$(function() {
		$('audio').audioPlayer();

		$('#lli_btn_submit_comment').on('click', function (e) {
			LLI.submitComment();
		});

		LLI.fetchComments();
	});

	LLI.increasePlayTimes = function () {
		if(LLI.audioPlayed) {
			return false;
		}

		LLI.audioPlayed = true;
		var audioId = $('.lli_content').data('audio_id');
		$.get('/mp/audioPlayTimes/'+ audioId, function(data) {
			console.log('done!')
		});
	};

	LLI.submitComment = function () {
		var audioId = $('.lli_content').data('audio_id');
		var comment = $('#comment').val();
		if($.trim(comment) === '') {
			return false;
		}

		var data = {'audioId': audioId, 'comment': comment};
		$.ajax({
			url: '/mp/audioComment',
			method: 'POST',
			contentType: 'application/json',
			data: JSON.stringify(data)
		}).done(function (data) {
			$('#commentModal').modal('toggle');
			LLI.fetchComments();
		});
	};

	LLI.fetchComments = function () {
		var audioId = $('.lli_content').data('audio_id');
		$.ajax({
			url: '/mp/audio/'+ audioId +'/comments',
			method: 'GET',
			contentType: 'application/json'
		}).done(function (comments) {
			var html = '';
			comments.forEach(function (comment) {
				html += '<div class="lli_comment">';
				html += '<div class="lli_comment_title">';
				html += '<i class="fa fa-user" aria-hidden="true"></i> ';
				html += comment.userNickname;
				html += '<small class="lli_comment_time">';
				html += comment.friendlyTime;
				html += '</small>';
				html += '</div>';
				html += '<div class="lli_comment_body">';
				html += comment.comment;
				html += '</div>';
				html += '</div>';
			});

			$('#lli_comment_wrapper').html(html);
		});
	};

})();