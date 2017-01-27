(function () {
	var LLI = {};

	$(function() {
    	$('.lli_clickable').on('click', LLI.onMediaClicked);
	});

	LLI.onMediaClicked = function () {
		console.info('media clicked');
	};

})();