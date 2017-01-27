(function () {
	var LLI = {};

	$(function() {
    	$('.lli_clickable').on('click', function () {
    		var id = $(this).data('id');
    		LLI.onMediaClicked(id);
    	});
	});

	LLI.onMediaClicked = function (id) {
		location.href = '/mp/mediaDetail?id='+ id;
	};

})();