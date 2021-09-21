function init(contextPath){

	$("#beneficiary-div").hide();
	$("#exchange-rate-div").hide();
	$("#receiving-agent").hide();
	
	$("#payoutCountry").change(function(){
		var selectedCountry = $(this).find("option:selected").val();
		showReceiverDetail(selectedCountry,contextPath);
	
		
	});
	
}

function showReceiverDetail(countryIso,contextPath){
	console.log("show Receiver Detail");
	getReceivingAgentDetail(countryIso,contextPath);
	getBeneficiaryDetail(countryIso,contextPath);
	
}

function  getReceivingAgentDetail(countryIso,contextPath){
	$.ajax({
		url:contextPath+"/online/ajax/transaction/receivingAgent?iso="+countryIso,
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

function  getBeneficiaryDetail(countryIso,contextPath){
	$.ajax({
		url:contextPath+"/online/ajax/transaction/beneficiary?iso="+countryIso,
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

function  getExchangeRateDetail(agencyName,contextPath){
	console.log("Get exchange rate detail");
	$.ajax({
		url:contextPath+"/online/ajax/txn/exchangerate",
		type:"POST",
		data:{
			"agencyName":agencyName
		},
		success:function(data){
			$("#ex-from-currency").html("");
			$("#ex-to-currency").html("");
			$("#ex-selling-rate").html("");
			$("#ex-buying-rate").html("");
			$("#exchangeRateId").val(data.exchangeRate.id);
			$("#ex-from-currency").html(data.exchangeRate.fromCurrency);
			$("#ex-to-currency").html(data.exchangeRate.toCurrency);
			$("#ex-selling-rate").html(data.exchangeRate.sellingRate);
			$("#ex-buying-rate").html(data.exchangeRate.buyingRate);
			
		}
	});
	$("#exchange-rate-div").show();
}


function  getCommissionDetail(sendingAgentId,receivingAgentId,contextPath){
	console.log("inside get commission detail");
	$.ajax({
		url:contextPath+"/online/ajax/transaction/commission",
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
