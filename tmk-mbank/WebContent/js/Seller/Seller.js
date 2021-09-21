var baseUrl=$(location).attr('protocol')+"//"+$(location).attr('host');

//Table
$(document).ready(function(){
	$('#table').dataTable();
});

function toggleCheckBox(id){
	if($('#'+id).is(':checked')){
		$('#'+id).val("true");
	}
	else{
		$('#'+id).val("false");
	}
}


//Category List
function GetCategoryList() {
	var responseDetail=[];
	$.ajax({ 
		type: "GET",
		url: baseUrl+"/Api/Category",
		success: function (response) {
			if(response.details.length==0){
				$("#category").typeahead("destroy");
				$("#category").val("");
				$("#category").attr("readonly","readonly");
			}
			else{
				$("#category").removeAttr("readonly");
				for(i=0;i<response.details.length;i++){
					responseDetail.push(response.details[i].name);
				}
				$("#category").typeahead("destroy");
				$('#category').typeahead({
						source: responseDetail,
						scrollBar:true
				});
			}
		},
		error: function (response) {
		}
    });
}

//Sub-Category List
function GetSubCategoryList() {
	var responseDetail=[];
	$.ajax({ 
		type: "GET",
		url: baseUrl+"/Api/SubCategory?categoryName="+$('#category').val(),
		success: function (response) {
			if(response.details.length==0){
				$("#subCategory").typeahead("destroy");
				$("#subCategory").val("");
				$("#subCategory").attr("readonly","readonly");
			}
			else{
				$("#subCategory").removeAttr("readonly");
				for(i=0;i<response.details.length;i++){
					responseDetail.push(response.details[i].name);
				}
				$("#subCategory").typeahead("destroy");
				$('#subCategory').typeahead({
						source: responseDetail,
						scrollBar:true
				});
			}
		},
		error: function (response) {
		}
    });
}