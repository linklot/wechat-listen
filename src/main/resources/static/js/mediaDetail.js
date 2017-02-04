(function () {
	"use strict";

	LLI.audioPlayed = false;

	$(function() {
		$('audio').audioPlayer();

		$('#lli_btn_submit_comment').on('click', function (e) {
			LLI.submitComment();
		});
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
		var data = {'audioId': audioId, 'comment': comment};
		$.ajax({
			url: '/mp/audioComment',
			method: 'POST',
			contentType: 'application/json',
			data: JSON.stringify(data)
		}).done(function (data) {
			console.log('done');
		});
	};

})();