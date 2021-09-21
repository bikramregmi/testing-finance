function init(contextPath) {
	
	$("#dialogForm").dialog({
		autoOpen: false,
		width: 500,
		resizable: false,
		modal: true,
		
		show: {
	        effect: "blind",
	        duration: 1000
	    },
	    hide: {
	        effect: "explode",
	        duration: 1000
	    },
	    buttons: {
        "Update": function(){ 
        	 $.ajax({
        	        type: "GET",
        	        headers: { 
        	            'Accept': 'application/json', 
        	            'Content-Type': 'application/json' 
        	        },
        	        url: contextPath+"/ajax/customer/update",
        	        data: {
        	        	"remarks": $("#remarks").val(),
        	        	"forUser":$("#forUser").val(),
        	        	"action": $("#action").val(),
        	        	"forUserType": $("#userType").val()
        	        	},
        	        
        	        dataType: 'json',
        	        timeout: 100000,
        	        success: function(data) {
        	        	console.log(data);
	        	        if (data.status.toLowerCase = "success") {
		        	        
	        	        	$("#dialogForm").dialog("close");
	        	        	$("#remarks").html("");
	        	        	location.reload();
			        	} else {

							alert("Could Not Add Note");
							$("#dialogForm").dialog("close");
					    }
        	        	
        	           
        	        },
        	        error: function(e) {
	        	        console.log(e);
	        	        console.log(e);
	  
        	            $("#dialogForm").dialog("close");
        	            
        	        },
        	        done: function(e) {
						console.log(e);
				
        	            $("#dialogFrom").dailog("close");
        	            
        	        } 
        	    });
			
		 },
        Cancel: function() {
          $("#dialogForm").dialog( "close" );
        }
      },
      close: function() {
        $("#dialogForm").dialog("close");
      }
	});
	
	$(".statusUpdate").click(function(e){
		$("#dialogForm").dialog("open");
	});
	
	
}