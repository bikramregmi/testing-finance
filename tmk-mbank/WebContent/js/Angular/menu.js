angular.module("tmkmobilebanking").controller("menuController",menuController);
function menuController($http,$scope){
	$scope.remove=function(){
		alert($scope.menuUrl);
	
	}
	
}