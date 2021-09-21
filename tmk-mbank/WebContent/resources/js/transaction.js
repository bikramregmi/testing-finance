function init(){
	$("#bank-div").hide();
	$("#receiving-agent").hide();
	$("#beneficiary-div").hide();
	$("#exchange-rate-div").hide();
	/*$("#exchange-rate-div").hide();
	$("#commission-div").hide();*/
	
	$("#payoutCountry").change(function(){
		var selectedCountry = $(this).find("option:selected").val();
		showReceiverDetail(selectedCountry);
		/*var sendingAgentId= $("#sendingAgentId").find("option:selected").val();
		var receivingAgentId= $("#receivingAgent").find("option:selected").val();
		var agencyName =$("#sendingAgentId").find("option:selected").text();
		getCommissionDetail(sendingAgentId,receivingAgentId);
		
		
	});
	
	$("#receivingAgent").change(function(){
		console.log("inside receiving agent");
		var sendingAgentId= $("#sendingAgentId").find("option:selected").val();
		var receivingAgentId= $("#receivingAgent").find("option:selected").val();
		var agencyName =$("#sendingAgentId").find("option:selected").text();
		getCommissionDetail(sendingAgentId,receivingAgentId);
		
	});
	
	$("#payoutChannel").change(function(){
		var channel = $(this).find("option:selected").val();
		
		if(channel=="bankAccount"){
			$("#bank-div").show();
		}
		
		if(channel=="agent"){
			$("#bank-div").hide();
		}
	});
}

function showReceiverDetail(countryIso){
	console.log("show Receiver Detail");
	getReceivingAgentDetail(countryIso);
	getBeneficiaryDetail(countryIso);
	
}

function  getReceivingAgentDetail(countryIso){
	$.ajax({
		url:"/ajax/transaction/receivingAgent?iso="+countryIso,
		type:"GET",
		success:function(data){
			$("#receivingAgent").find("option").remove();
			console.log(data.agentList);
			$.each(data.agentList,function(index,agent){
			
				option = '<option value="'+agent.id + '">' +agent.agencyName+ '</option>';
				/*option.attr("value",agent.id);
				option.text(agent.agencyName);*/
				$("#receivingAgent").append(option);
			});
		}
	});
	$("#receiving-agent").show();
}

function  getBeneficiaryDetail(countryIso){
	$.ajax({
		url:"/ajax/transaction/beneficiary?iso="+countryIso,
		type:"GET",
		success:function(data){
			$("#beneficiary").find("option").remove();
			console.log(data.agentList);
			$.each(data.beneficiaryList,function(index,beneficiary){
			
				option = '<option value="'+beneficiary.firstName + '">' +beneficiary.firstName+ ' '+beneficiary.middleName+''+beneficiary.lastName+ '</option>';
				/*option.attr("value",agent.id);
				option.text(agent.agencyName);*/
				$("#beneficiary").append(option);
			});
		}
	});
	$("#beneficiary-div").show();
}




function  getCommissionDetail(sendingAgentId,receivingAgentId){
	console.log("inside get commission detail");
	$.ajax({
		url:"/ajax/transaction/commission",
		type:"POST",
		data:{
			"sendingAgentId":sendingAgentId,
			"receivingAgentId":receivingAgentId
		},
		success:function(data){
			$("#sending-commission-select").find("option").remove();
			console.log("Data "+data.commissionList);
			$.each(data.commissionList,function(index,commission){ 
				option = '<option value="'+commission.id + '">' +commission.overallCommission+ '</option>';
				/*option.attr("value",agent.id);
				option.text(agent.agencyName);*/
				$("#sending-commission-select").append(option);
			});
			
		},
		fail:function(data){
			console.log("failure");
		}
		
	});
	$("#commission-div").show();
}

function emptyCombo(id){
	$('#remitAgent').find('option').remove();
}
