$(document).ready(function() {
	$("#titleBar").click(function() {
		$("#channelSideBar").addClass("active");
	});

	$("#sidebarClose").click(function() {
		$("#channelSideBar").removeClass("active");
	});
	
	$(".menu").click(function(){
		location.replace($(this).find("a").attr("href"));
	});
});