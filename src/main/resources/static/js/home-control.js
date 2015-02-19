
function healthCheck() {
	$.ajax(
			{
				type : "GET",
				url  : "/example",
				data : {
				},
				success : function(result) {
					$('#status').text(result);
				},
				error: function (jqXHR, exception) {
					$('#status').text("Failed to get the status");
				}
			});
}


function deletePhoto(photoId) {
	$.ajax(
			{
				type : "DELETE",
				url  : "/cs480/user/" + photoId,
				data : {
				},
				success : function(result) {
					location.reload();
				},
				error: function (jqXHR, exception) {
					alert("Failed to delete the photo.");
				}
			});
}

function addPhoto() {

	var photoId = $('#input_id').val();
	var userName = $('#input_name').val();
	var userOwner = $('#input_owner').val();

	if (photoId) {
		$.ajax(
				{
					type : "POST",
					url  : "/cs480/user/" + photoId,
					data : {
						"name" : userName,
						"owner" : userOwner
					},
					success : function(result) {
						location.reload();
					},
					error: function (jqXHR, exception) {
						alert("Failed to add the photo. Please check the inputs.");
					}
				});
	} else {
		alert("Invalid photo Id");
	}
}

function getPhoto(photoId) {
	var photoId = $('#query_id').val();
	if (photoId) {
		$.ajax(
				{
					type : "GET",
					url  : "/cs480/user/" + photoId,
					data : {
					},
					success : function(result) {
						$('#result_id').text(result.id);
						$('#result_name').text(result.name);
						$('#result_owner').text(result.owner);
					},
					error: function (jqXHR, exception) {
						alert("Failed to get the photo.");
					}
				});
	} else {
		alert("Invalid photo Id");
	}
}