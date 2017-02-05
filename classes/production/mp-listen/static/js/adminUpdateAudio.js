(function () {
	"use strict";

	$(function () {
		$('#lli-btn-cancel').on('click', function () {
			location.href = '/admin';
		});

		$('#lli-btn-submit').on('click', function () {
			$('#updateAudioForm').submit();
		});
	});

})();