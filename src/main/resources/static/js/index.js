(function () {
	"use strict";
	
	var LLI = {};

	$(function() {
    	$('.lli_clickable').on('click', function () {
    		var id = $(this).data('id');
    		LLI.onMediaClicked(id);
    	});

    	$('.lli_clickable').on('mouseover', function () {
    		$(this).addClass('tap-bg-color');
  		});
	  	$(".lli_clickable").on('mouseout', function (event) {
	    	$(this).removeClass('tap-bg-color');
	  	});

    	$('.lli_clickable').on('touchstart', function () {
    		$(this).addClass('tap-bg-color');
  		});

	  	$(".lli_clickable").on('touchend', function (event) {
	    	$(this).removeClass('tap-bg-color');
	  	});
	});

	LLI.onMediaClicked = function (id) {
		location.href = '/mp/mediaDetail?id='+ id;
	};

})();