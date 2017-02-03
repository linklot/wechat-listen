(function () {
	"use strict";

	LLI.audioPlayed = false;

	$(function() {
		$('audio').audioPlayer(); 
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
})();