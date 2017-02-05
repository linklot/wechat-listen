(function () {
	"use strict";

	$(function () {
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

})();