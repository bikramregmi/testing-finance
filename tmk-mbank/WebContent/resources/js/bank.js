function init(contextPath) {
	$(".merchantServices").click(function(e){
		e.preventDefault();
		var test = $("#bankMerchantList").dialog();
	});
	
	$("#bankMerchantList").dialog({
		autoOpen: false,
		width: 500,
		resizable: false,
		modal: true,
	    buttons: {
        "OK": function(){
        	$("#bankMerchantList").dialog( "close" );
		 },
        Cancel: function() {
          $("#bankMerchantList").dialog( "close" );
        }
      },
      close: function() {
        $("#bankMerchantList").dialog("close");
      }
	});
}