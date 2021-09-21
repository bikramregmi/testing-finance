function init(contextPath) {
	
	//hiding table , input field and row adding button
	$("#addCommissionAmountDiv").hide();
	$("#first").hide();
	$("#feeCommissionTable").hide();
	$("#feeTable").hide();
	$("#commissionTable").hide();
	$("#second").hide();
	
	//disabling the second div
	$('div#second input').prop("disabled",true);
	$("input[name='commissionAmounts[0].flat']").slice(0,1).prop("disabled", false);
	$("input[name='commissionAmounts[0].percentage']").slice(0,1).prop("disabled", false);
	
	function dissableDublicateInputField(){
		$('div#first input').prop("disabled",false);
		$('div#second input').prop("disabled",false);
		if($("#sameForAll").is(':checked')){
			$('div#first input').prop("disabled",true);
			$("input[name='commissionAmounts[0].flat']").slice(1).prop("disabled", false);
			$("input[name='commissionAmounts[0].percentage']").slice(1).prop("disabled", false);
		}else{
			$('div#second input').prop("disabled",true);
			$("input[name='commissionAmounts[0].flat']").slice(0,1).prop("disabled", false);
			$("input[name='commissionAmounts[0].percentage']").slice(0,1).prop("disabled", false);
		}
	}
	
	$("#bankSelect").change(function(){
		var selectedBank = $(this).find("option:selected").val();
		$("#serviceFrom").text('');
		$("#serviceTo").text('')
		showMerchant(selectedBank, contextPath);
	});

	$("#merchantSelect").change(function(){
		var selectedMerchant = $(this).find("option:selected").val();
		showServices(selectedMerchant, contextPath);
	});
	
	$('#serviceFrom').click(function() {  
		return !$('#serviceFrom option:selected').remove().appendTo('#serviceTo');  
	});  
	$('#serviceTo').click(function() { 
		return !$('#serviceTo option:selected').remove().appendTo('#serviceFrom');  
	}); 
	
	$("#sameForAll").change(function(){
		dissableDublicateInputField();
		if($("#commissionType").val() === null){
			return;
		}
		if($("#sameForAll").is(':checked')){
			$("#sameForAll").attr('value', 'true');
			$("#first").hide();
			$("#addCommissionAmountDiv").hide();
			$("#second").show();

		} else {
			$("#sameForAll").attr('value', 'false');
			$("#second").hide();
			$("#addCommissionAmountDiv").show();
			$("#first").show();
		}
	});
	

	
	function showMerchant(selectedBank, contextpath) {
		
		$.ajax({
			url: contextPath+"/ajax/merchant/getByBank?bank="+selectedBank,
			type: "GET",
			success: function(data) {
				$("#merchantSelect").find("option").remove();
				var option = '';
				option+= '<option>Select Merchant</option>';
				$.each(data.merchantList, function(index, merchant){
					option+= '<option value="'+merchant.id + '">' +merchant.name+ '</option>';
					
				});
				$("#merchantSelect").append(option);
			}
		});
	}
	
	function showServices(selectedMerchant, contextPath) {
		$.ajax({
			url: contextPath+"/ajax/service/getServicesByMerchant?merchant="+selectedMerchant,
			type: "GET",
			success: function(data) {
				$("#service").find("option").remove();
				var option = '';
				option+='<option>Select Service</option>'
				$.each(data.serviceList, function(index, service){
					option+='<option value="'+service.id+'">'+service.service+'</option>';
				});
				$("#service").append(option);
			}
		});
		$("#serviceSelect").show();
	}
}

$(document).ready(function(){
	/*var rowCount = $('table#commissionAmountList tr:last').index() +1;
	console.log("Last Row No : "+rowCount);*/

	$("#addCommissionAmount").click(function(){
		var rowCount = $('#commissionAmountList tr').length - 1;
		console.log(rowCount);
		createRow(rowCount);
		rowCount=rowCount+1;
	});
	var feeVar = false;
	var commissionVar = false;
	$("#commissionType").click(function(){
		if ($("#commissionType").val() == 'FEE') {
			if($("#sameForAll").is(':checked')){
				$("#addCommissionAmountDiv").hide();
				$("#first").hide();
				$("#second").show();
			}else{
				$("#addCommissionAmountDiv").show();
				$("#first").show();
				$("#second").hide();
			}
			$("#feeCommissionTable").show();
			feeVar = true;
			$("#commissionTable").hide();
			$("#commissionFlatTd").hide();
			$("#commissionPercentageTd").hide();
			
			$("#feeFlatTd").show();
			$("#feePercentageTd").show();
			$("#feeTable").show();
		} else if ($("#commissionType").val() == 'COMMISSION') {
			if($("#sameForAll").is(':checked')){
				$("#addCommissionAmountDiv").hide();
				$("#first").hide();
				$("#second").show();
			}else{
				$("#addCommissionAmountDiv").show();
				$("#first").show();
				$("#second").hide();
			}
			$("#feeCommissionTable").show();
			$("#feeFlatTd").hide();
			$("#feePercentageTd").hide();
			$("#feeTable").hide();
			
			$("#commissionFlatTd").show();
			$("#commissionPercentageTd").show();
			$("#commissionTable").show();
			commissionVar = true;
		} else {
			commissionVar = true;
		}
	});
	function createRow(num){
		var row=$("<tr class='commission-row'>");
		var cell=$("<td class='commission-col'>");
		var fromAmount=createAmountInput("fromAmount",num);
		cell.append(fromAmount);
		row.append(cell);

		var cell=$("<td class='commission-col'>");
		var toAmount=createAmountInput("toAmount",num);
		cell.append(toAmount);
		row.append(cell);
		
		var cell=$("<td class='commission-col'>");
		var toAmount=createAmountInput("flat",num);
		cell.append(toAmount);
		row.append(cell);
		
		var cell=$("<td class='commission-col'>");
		var toAmount=createAmountInput("percentage",num);
		cell.append(toAmount);
		row.append(cell);
		
		$("#commissionAmountList").append(row);
	}
	
	function createAmountInput(name,num){
		var inputElement=$("<input type='text' class='form-control input-sm' name='commissionAmounts["+num+"]."+name+"' value='0'/>");
		return inputElement;
	}
	
});

function editInit(contextPath) {
	$("input[name='commissionAmounts[0].flat']").slice(1).prop("disabled", true);
	$("input[name='commissionAmounts[0].percentage']").slice(1).prop("disabled", true);
	if(isSameForAll==='true'){
		$('#sameForAll').prop('checked', true);
		$("#addCommissionAmountDiv").hide();
		$("#first").hide();
		$('div#first input').prop("disabled",true);
		$("input[name='commissionAmounts[0].flat']").slice(1).prop("disabled", false);
		$("input[name='commissionAmounts[0].percentage']").slice(1).prop("disabled", false);
	}else{
		$("#second").hide();
		$('div#second input').prop("disabled",true);
		$("input[name='commissionAmounts[0].flat']").slice(0,1).prop("disabled", false);
		$("input[name='commissionAmounts[0].percentage']").slice(0,1).prop("disabled", false);
	}
	
	function dissableDublicateInputField(){
		$('div#first input').prop("disabled",false);
		$('div#second input').prop("disabled",false);
		if($("#sameForAll").is(':checked')){
			$('div#first input').prop("disabled",true);
			$("input[name='commissionAmounts[0].flat']").slice(1).prop("disabled", false);
			$("input[name='commissionAmounts[0].percentage']").slice(1).prop("disabled", false);
		}else{
			$('div#second input').prop("disabled",true);
			$("input[name='commissionAmounts[0].flat']").slice(0,1).prop("disabled", false);
			$("input[name='commissionAmounts[0].percentage']").slice(0,1).prop("disabled", false);
		}
	}
	
	$("#sameForAll").change(function(){
		dissableDublicateInputField();
		if($("#sameForAll").is(':checked')){
			$("#sameForAll").attr('value', 'true');
			$("#first").hide();
			$("#addCommissionAmountDiv").hide();
			$("#second").show();

		} else {
			$("#sameForAll").attr('value', 'false');
			$("#second").hide();
			$("#addCommissionAmountDiv").show();
			$("#first").show();
		}
	});
	
	if(isSameForAll==="true"){
		$("input[name='commissionAmounts[0].flat']").slice(1).val(commissionAmountList[0].flat);
		$("input[name='commissionAmounts[0].percentage']").slice(1).val(commissionAmountList[0].percentage);
	}else{
		$("input[name='commissionAmounts[0].flat']").slice(0,1).val(commissionAmountList[0].flat);
		$("input[name='commissionAmounts[0].percentage']").slice(0,1).val(commissionAmountList[0].percentage);
		$("input[name='commissionAmounts[0].fromAmount']").slice(0,1).val(commissionAmountList[0].fromAmount);
		$("input[name='commissionAmounts[0].toAmount']").slice(0,1).val(commissionAmountList[0].toAmount);
		console.log("length : "+commissionAmountList.length);
		for(i = 1; i < commissionAmountList.length; i++){
			createRow(i);
			$("input[name='commissionAmounts["+i+"].flat']").val(commissionAmountList[i].flat);
			$("input[name='commissionAmounts["+i+"].percentage']").val(commissionAmountList[i].percentage);
			$("input[name='commissionAmounts["+i+"].fromAmount']").val(commissionAmountList[i].fromAmount);
			$("input[name='commissionAmounts["+i+"].toAmount']").val(commissionAmountList[i].toAmount);
		}
	}
	
	function createRow(num){
		var row=$("<tr class='commission-row'>");
		var cell=$("<td class='commission-col'>");
		var fromAmount=createAmountInput("fromAmount",num);
		cell.append(fromAmount);
		row.append(cell);

		var cell=$("<td class='commission-col'>");
		var toAmount=createAmountInput("toAmount",num);
		cell.append(toAmount);
		row.append(cell);
		
		var cell=$("<td class='commission-col'>");
		var toAmount=createAmountInput("flat",num);
		cell.append(toAmount);
		row.append(cell);
		
		var cell=$("<td class='commission-col'>");
		var toAmount=createAmountInput("percentage",num);
		cell.append(toAmount);
		row.append(cell);
		
		$("#commissionAmountList").append(row);
	}
	
	function createAmountInput(name,num){
		var inputElement=$("<input type='text' class='form-control input-sm' name='commissionAmounts["+num+"]."+name+"' value='0'/>");
		return inputElement;
	}
	
}
