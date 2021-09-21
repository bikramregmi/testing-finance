angular.module("tmkmobilebanking").controller("smsModeController",
		function($scope,$http){
	$scope.hide=true;
	$scope.onBankChange=function(){
		 $scope.smsModeList={};
		var bank = $('#bank').val();
		
		if(bank==undefined || bank==null || bank==""){
			bank= $scope.selectedBank;
			
		}
		
		if(bank !=null || bank !='')
			{
			 $scope.hide=false;
			 try{
				 let parameter = {
						bank : bank 
						
				 };
				    get("/sms-type-list-for-bank",
						function(data){
				    				    
					         $scope.smsModeList=data;
					        
				           }//end of function(data)
				    ,parameter
						);//end of get
			}//end try
			catch(message){
				
			}//end catch
			 
			}
	}
	
	
	function get(url, callback, parameter) {
		$http.get(url, {
			params : parameter
		}).then(function success(data) {
			if (callback)
				callback(data.data.detail);
		}, function error(data) {
			console.log("error", data);
			throw data.message;
		});
	}
}//end of function(scope, http)
);//end of the controller

