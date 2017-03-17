(function () {
	'use strict';

	$(function () {
		LLI.loadAudios();

		$('#lli-btn-createAudio').on('click', function () {
			location.href = '/admin/createAudio';
		});

		$('#lli-btn-abortCreateAudio').on('click', function (event) {
			event.preventDefault();
			location.href = '/admin';
		});

		$('#lli-btn-confirmCreateAudio').on('click', function (event) {
			event.preventDefault();

			if($('#title').val() != '') {
				$('#createAudioForm').submit();
			}
		});

		$('#lli_chk_all').on('click', function(e) {
			var checked = this.checked;
			if(checked) {
				$('.lli_chk_item').prop('checked', true);
			} else {
				$('.lli_chk_item').prop('checked', false);
			}

			LLI.showHideDelBtn();
		});

		$('.lli_chk_item').on('click', function(e) {
			LLI.showHideDelBtn();
		});

		$('#lli-btn-deleteAudio').on('click', function () {
			$('#delAudioModal').modal();
		});

		$('#lli_btn_confirm_delete_audio').on('click', function () {
			LLI.deleteAudios();
		});

		$('.lli_btn_audio_comments').on('click', LLI.loadCommentsData);

		$('#lli_btn_hideShowComment').on('click', LLI.hideShowComments);

		$('.table-fixed').scroll(LLI.detectWindowScroll);
	});

	LLI.showHideDelBtn = function () {
		var showBtn = false;
		$('.lli_chk_item').each(function(idx, elem) {
			if($(elem).prop('checked')) {
				showBtn = true;
				return false;
			}
		});

		if(showBtn) {
			$('#lli-btn-deleteAudio').removeClass('hidden');
		} else {
			$('#lli-btn-deleteAudio').addClass('hidden');
		}
	};

	LLI.deleteAudios = function () {
		var audioIds = [];
		$('.lli_chk_item').each(function(idx, elem) {
			if($(elem).prop('checked')) {
				audioIds.push($(elem).data('audio-id'));
			}
		});

		$.ajax({
			url: '/admin/delAudios',
			type: 'POST',
			contentType: 'application/json',
			data: JSON.stringify(audioIds)
		}).done(function (data) {
			location.reload();
		});
	};

	LLI.loadCommentsData = function () {
		var audioId = $(this).data('audio-id');

		// Load comments as JSON
		$.ajax({
			url: '/admin/audios/audio/'+ audioId +'/comments',
			type: 'get',
			contentType: 'application/json'
		}).done(function (comments) {
			LLI.showCommentsModal(audioId, comments);
		});

		
	};

	LLI.showCommentsModal = function (audioId, comments) {
		var targetModal = $("#commentsModal");
		var contentWrapper = $('#lli_modal_comments_wrapper');

		var html = '<table class="table">';
		html += '<thead>';
		html += '<tr>';
		html += '<th class="col-sm-1">隐藏</th>';
		html += '<th class="col-sm-2">用户名</th>';
		html += '<th class="col-sm-9">内容</th>';
		html += '</tr>';
		html += '</thead>';
		html += '<tbody>';

		_.each(comments, function (comment) {
			var strChecked = comment.hidden ? ' checked="checked"' : '';
			html += '<tr>';
			html += '<td class="col-sm-1">';
			html += '<input type="checkbox" class="lli_comment_checkbox" data-comment-id="'+ comment.id +'"'+ strChecked +'/>';
			html += '</td>';
			html += '<td class="col-sm-2">';
			html += comment.userNickname;
			html += '</td>';
			html += '<td class="col-sm-9">';
			html += comment.comment;
			html += '</td>';
			html += '</tr>';
		});

		html += '</tbody>';
		html += '</table>';

		contentWrapper.html(html);

		targetModal.attr('data-audio-id', audioId);
		targetModal.modal('toggle');
	};

	LLI.hideShowComments = function () {
		var idsToHide = [];
		$('.lli_comment_checkbox').each(function () {
			var elem = $(this);
			var commentId = elem.data('comment-id');
			if(elem.prop('checked')) {
				idsToHide.push(commentId);
			}
		});

		if(idsToHide.length > 0) {
			$('#lli_btn_hideShowComment').addClass('disabled');
			LLI.submitHideShowComments(idsToHide);
		}
	};

	LLI.submitHideShowComments = function (idsToHide) {
		var audioId = $("#commentsModal").data('audio-id');
		var json = {"audioId": audioId, "idsToHide": idsToHide};
		$.ajax({
			url: '/admin/comments',
			type: 'post',
			contentType: 'application/json',
			data: JSON.stringify(json)
		}).done(function (data) {
			$('#lli_btn_hideShowComment').removeClass('disabled');
			$("#commentsModal").modal('toggle');
		});
	};

	LLI.detectWindowScroll = function () { //detect page scroll
		if ($(this).scrollTop() + $(this).innerHeight() >= $(this)[0].scrollHeight) {
            LLI.loadAudios();
        }
	};

	LLI.loadAudios = function () {
		var isPageLast = $('#lli_page_last').val();
		if('false' === isPageLast) {
			var pageNumber = parseInt($('#lli_page_number').val()) + 1;
			var pageSize = $('#lli_page_size').val();
			$.ajax({
				url: '/admin/audios/'+ pageSize +'/'+ pageNumber,
				type: 'get',
				contentType: 'application/json'
			}).done(function (pagedAudios) {
				LLI.appendNewlyLoadedAudios(pagedAudios);
			});
		}
	};

	LLI.appendNewlyLoadedAudios = function (pagedAudios) {
		var contentWrapper = $('#lli_admin_audios_wrapper');
		var audios = pagedAudios.audioModels;
		$('#lli_page_last').val(pagedAudios.isLast);
		$('#lli_page_number').val(pagedAudios.pageNumber);

		var html = '';
		for(var i=0; i<audios.length; i++) {
			var audio = audios[i];
			html += '<tr class="lli_audio_row">';
			html += '<td class="col-sm-1"><input type="checkbox" class="lli_chk_item" data-audio-id="'+ audio.id +'"/></td>';
			html += '<td class="col-sm-1">'+ audio.playTimes +'</td>';
			html += '<td class="col-sm-5"><a href="/admin/audios/audio/'+ audio.id +'">'+ audio.title +'</a></td>';
			html += '<td class="col-sm-2">'+ audio.friendlyDateTime +'</td>';
			html += '<td class="col-sm-1"><a href="/mp/converImg/'+ audio.id +'" target="_blank"><i class="fa fa-file-image-o" aria-hidden="true"></i></a></td>';
			html += '<td class="col-sm-1"><a href="/mp/audioPreview/'+ audio.id +'" target="_blank"><i class="fa fa-file-audio-o" aria-hidden="true"></i></a></td>';
			html += '<td class="col-sm-1"><button type="button" class="btn btn-info btn-xs lli_btn_audio_comments" data-audio-id="'+ audio.id +'">'+ audio.commentCount +'</span></td>';
			html += '</tr>';
		}
		contentWrapper.append(html);

		LLI.bindCommentBtnClickHandler();
	};

	LLI.bindCommentBtnClickHandler = function () {
		$('.lli_btn_audio_comments').off();
		$('.lli_btn_audio_comments').on('click', LLI.loadCommentsData);

		$('.lli_chk_item').off();
		$('.lli_chk_item').on('click', function(e) {
			LLI.showHideDelBtn();
		});
	};

})();