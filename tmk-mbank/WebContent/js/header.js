function ActiveHeader(id){
	$('#'+id).addClass("active");
}

// Show popup
function showPopup(id){
	if ($('#'+id).length) {
     $('#'+id).fadeIn();
	}	
}

// Hide popup
function hidePopup(id){
	if ($('#'+id).length) {
     $('#'+id).fadeOut();
	}	
}
