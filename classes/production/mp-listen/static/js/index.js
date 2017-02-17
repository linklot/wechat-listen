(function () {
	"use strict";
	
	var LLI = {};

	$(function() {
	  	LLI.loadAudios();
	});

	LLI.initEventHandlers = function () {
		$('.lli_clickable').off();
		$(window).off('scroll', LLI.detectWindowScroll);

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

	  	$(window).scroll(LLI.detectWindowScroll);
	};

	LLI.detectWindowScroll = function () { //detect page scroll
		if($(window).scrollTop() + $(window).height() >= $(document).height()) { //if user scrolled to bottom of the page
	        LLI.loadAudios();
	    }
	};

	LLI.onMediaClicked = function (id) {
		location.href = '/mp/mediaDetail?id='+ id;
	};

	/*
	 * Load paginated audio data as JSON.
	*/
	LLI.loadAudios = function () {
		var isPageLast = $('#lli_page_last').val();
		if('false' === isPageLast) {
			$('.lli_loading_bar').removeClass('hidden');
			var pageNumber = parseInt($('#lli_page_number').val()) + 1;
			var pageSize = $('#lli_page_size').val();
			$.ajax({
				url: '/mp/audios/'+ pageSize +'/'+ pageNumber,
				type: 'get',
				contentType: 'application/json'
			}).done(function (pagedAudios) {
				LLI.appendNewlyLoadedAudios(pagedAudios);
			});
		}
	};

	LLI.appendNewlyLoadedAudios = function (pagedAudios) {
		var contentWrapper = $('.lli_content');
		var audios = pagedAudios.audioModels;
		$('#lli_page_last').val(pagedAudios.isLast);
		$('#lli_page_number').val(pagedAudios.pageNumber);
		$('#lli_page_size').val(pagedAudios.pageSize);

		var html = '';

		for(var i=0; i<audios.length; i++) {
			var audio = audios[i];
			html += '<div>';
			html += '    <div class="lli_clickable" data-id="'+ audio.id +'">';
			html += '        <div class="media lli_media">';
			html += '            <div class="media-left">';
			html += '                <img class="lli_audio_list_img" src="/mp/coverImg/'+ audio.id +'" alt="" height="55" width="55"/>';
			html += '                <div class="lli_audio_play_times">';
			html += '                    <i class="fa fa-bell-o" aria-hidden="true"></i> '+ audio.playTimes;
			html += '                </div>';
			html += '            </div>';
			html += '            <div class="media-body">';
			html += '                <h4 class="media-heading">';
			html += '                    '+ audio.title;
			html += '                    <small class="lli_pub_datetime">'+ audio.friendlyDateTime +'</small>';
			html += '                </h4>';
			html += '                <span>'+ audio.description +'</span>';
			html += '            </div>';
			html += '        </div>';
			html += '    </div>';
			html += '</div>';
		}

		$('.lli_loading_bar').addClass('hidden');
		contentWrapper.html(contentWrapper.html() + html);
		LLI.initEventHandlers();
	};

})();