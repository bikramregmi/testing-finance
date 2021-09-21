function init(){
	
	$("#superagent-select").change(function(){
		var superAgentId = $(this).find("option:selected").val();
		updateAgentDetail(superAgentId);
	});
}

function updateAgentDetail(superAgentId){
	console.log("show Receiver Detail");
	getAgentDetail(superAgentId);
}

function  getAgentDetail(superAgentId){
	$.ajax({
		url:"/ajax/exchangerate/agent?superAgentName="+superAgentId,
		type:"POST",
		success:function(data){
			$("#agent-select").find("option").remove();
			console.log(data.agentList);
			$.each(data.agentList,function(index,agent){
			
				option = '<option value="'+agent.id + '">' +agent.agencyName+ '</option>';
				/*option.attr("value",agent.id);
				option.text(agent.agencyName);*/
				$("#agent-select").append(option);
			});
		}
	});
	
}



