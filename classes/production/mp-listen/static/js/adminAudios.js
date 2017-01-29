(function () {
	"use strict";

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

})();