var cartApp = angular.module("tmkmobilebanking", []);

cartApp
		.controller(
				"tmkmobilebankingController",
				function($scope, $http, $window) {
					$scope.show = '1';
					$scope.reload = function() {
						$window.location.reload();
					}
					
					$scope.toggleMerchantAccount = function(){
						if($scope.merchantaccounthide){
							$scope.merchantaccounthide = false;
						}else{
							$scope.merchantaccounthide = true;
						}
					}
					
					$scope.toggleCardlessBankAccount = function(){
						if($scope.cardlessbankaccounthide){
							$scope.cardlessbankaccounthide = false;
						}else{
							$scope.cardlessbankaccounthide = true;
						}
					}
					
					$scope.tableName="reportTable";
					$scope.printReport=function(table_id){
						//alert("hi");
						        var divToPrint = document.getElementById(tableId);
						      //alert(divToPrint);
						     /*  newWin = window.open("","Print","");
						        newWin.document.write(divToPrint.outerHTML);
						        newWin.print();
						        newWin.close();*/
						     
				//This is by using the jquery plugin- jquery.PrintArea.js
				  var mode = 'iframe'; 
				 //var mode ='popup'
                    //popupvar close = mode == "popup";  
                     var options = { mode : mode, popClose : close};
                     $("#"+table_id).printArea( options );
                     
                   
						   
					}//end of print function

					$scope.loadId = function(packages) {
						$scope.packageId = packages.id;
						$scope.generalpackageamount = packages.amount;
					}

					$scope.addDueAmounts = function(dueDate) {
						$scope.hash.generalamount = 0;
						var date = new Date(dueDate._billDate);
						for (var i = 0; i < $scope.payment.length; i++) {
							$("." + $scope.payment[i]._billDate).removeClass(
									"hightlight");
							if (date.getTime() >= new Date(
									$scope.payment[i]._billDate)) {
								$("." + $scope.payment[i]._billDate).addClass(
										"hightlight");
								$scope.hash.generalamount = Number($scope.hash.generalamount)
										+ Number($scope.payment[i]._amount);
							}
						}
					}

					$scope.Math = window.Math;
					$scope.cncoCodeOperation = function() {

						$http(
								{
									method : 'POST',
									url : '/confirmationcode',
									headers : {
										'Content-Type' : 'application/x-www-form-urlencoded'
									},
									transformRequest : function(obj) {
										var str = [];
										for ( var p in obj)
											str
													.push(encodeURIComponent(p)
															+ "="
															+ encodeURIComponent(obj[p]));
										return str.join("&");
									},
									data : {
										cashtype : $scope.casType,
										generatedTo : $scope.cicowalletId,
										cicoAmount : $scope.cicoamount,
										cid : $scope.cid,
										cicoCode : $scope.cicoCode

									}
								}).success(function(data) {

							console.log(data);
							if (data.responseStatus == "SUCCESS") {
								alert(data.message);
							}

						});
					}

					$scope.cncoOperation = function() {
						$http(
								{
									method : 'POST',
									url : '/cashincashout',
									headers : {
										'Content-Type' : 'application/x-www-form-urlencoded'
									},
									transformRequest : function(obj) {
										var str = [];
										for ( var p in obj)
											str
													.push(encodeURIComponent(p)
															+ "="
															+ encodeURIComponent(obj[p]));
										return str.join("&");
									},
									data : {
										cashtype : $scope.casType,
										generatedTo : $scope.cicowalletId,
										cicoAmount : $scope.cicoamount

									}
								}).success(function(data) {
							if (data.responseStatus == "SUCCESS") {
								$scope.CICOfirst = true;
								$scope.cid = data.detail.id;
							} else {

							}
							$scope.cncomessage = data.message;

						});
					}

					function getbalance() {
						$http.post('/getBalance').success(function(data) {
							if (data.status == 'success') {
								$scope.myBalance = data.detail.balance;
							}
						});
					}
					getbalance();

					$http.post('/getMyName').success(function(data) {
						if (data.status == 'success') {
							if (data.message == 'customer') {
								$scope.myName = data.detail.fullName;
							} else {
								$scope.myName = data.detail.agencyName;
							}
						}
					});

					$scope.loadFundFromVouchure = function() {

						$http(
								{
									method : 'POST',
									url : '/loadfundfromvoucher',
									headers : {
										'Content-Type' : 'application/x-www-form-urlencoded'
									},
									transformRequest : function(obj) {
										var str = [];
										for ( var p in obj)
											str
													.push(encodeURIComponent(p)
															+ "="
															+ encodeURIComponent(obj[p]));
										return str.join("&");
									},
									data : {
										vouchureId : $scope.vouchureId
									}
								}).success(function(data) {
							getbalance();

							$scope.vouchureloadMessage = data.message;

						});
					};

					$scope.peerTransfer = function() {

						$http(
								{
									method : 'POST',
									url : '/peertransfer',
									headers : {
										'Content-Type' : 'application/x-www-form-urlencoded'
									},
									transformRequest : function(obj) {
										var str = [];
										for ( var p in obj)
											str
													.push(encodeURIComponent(p)
															+ "="
															+ encodeURIComponent(obj[p]));
										return str.join("&");
									},
									data : {
										p2pwalletid : $scope.p2pwalletid,
										p2pamount : $scope.p2pamount,
										owner : $scope.owner
									}
								}).success(function(data) {
							getbalance();
							if (data.status == 'validationError') {
								$scope.p2pmessage = data.detail.message;
							} else {
								$scope.p2pmessage = data.message;
							}
							if (data.status == 'success') {
								$scope.p2pwalletid = '';
								$scope.p2pamount = '';
								$scope.owner = '';
							}
						});
					};

					
					//added on 20 may 2018
					$scope.confirm=function(){
						
						angular.element(confirmationModal).modal("show");
						
						
					}
					
					
					$scope.loadFund = function() {
						
				
						angular.element(confirmationModal).modal("hide");
						
					
						$http(
								{
									method : 'POST',
									url : '/ledger/loadbalance',
									headers : {
										'Content-Type' : 'application/x-www-form-urlencoded'
									},
									transformRequest : function(obj) {
										var str = [];
										for ( var p in obj)
											str
													.push(encodeURIComponent(p)
															+ "="
															+ encodeURIComponent(obj[p]));
										return str.join("&");
									},
									data : {
										bank : $scope.bank,
										balance : $scope.balance,
										remarks : $scope.remarks
									}
								}).success(function(data) {
							/* getbalance(); */
							$scope.p2pmessage = data.message;

							if (data.status == 'success') {
								angular.element(successModal).modal("show");
								$scope.bank = '';
								$scope.balance = '';
								$scope.remarks='';
								$window.location.href = "/";
							}
						});
				
					};

					$scope.loadCredits = function() {
						angular.element(confirmationModal).modal("hide");
						$http(
								{
									method : 'POST',
									url : '/ledger/creditlimits',
									headers : {
										'Content-Type' : 'application/x-www-form-urlencoded'
									},
									transformRequest : function(obj) {
										var str = [];
										for ( var p in obj)
											str
													.push(encodeURIComponent(p)
															+ "="
															+ encodeURIComponent(obj[p]));
										return str.join("&");
									},
									data : {
										bank : $scope.bank,
										amount : $scope.amount,
										remarks : $scope.remarks
									}
								}).success(function(data) {
						     	getbalance();

							if (data.status == 'success') {
								angular.element(successModal).modal("show");
								$scope.bank = '';
								$scope.balance = '';
								$scope.remarks='';
								$window.location.href = "/";
							}
						});
					};
					$scope.generalMerchantPayment = function(url) {
						$scope.exporting = true;
						$http(
								{
									method : 'POST',
									url : url,
									headers : {
										'Content-Type' : 'application/x-www-form-urlencoded'
									},
									transformRequest : function(obj) {
										var str = [];
										for ( var p in obj)
											str
													.push(encodeURIComponent(p)
															+ "="
															+ encodeURIComponent(obj[p]));
										return str.join("&");
									},
									data : {

										generalserviceUnique : $scope.generalserviceUnique,
										generaltransactionId : $scope.generaltransactionId,
										generalamount : $scope.generalamount,
									// hashMap:JSON.stringify($scope.hash)
									// hashMap:$scope.hash.userId;
									}
								})
								.success(
										function(data) {
											getbalance();

											if (data.message == 'Package') {
												$scope.hash = {};

												$scope.pakageList = data.detail.packages;
												if (data.detail.packages == null) {
													$scope.message1 = "Please Confirm your Payment";
													$scope.nopackage = true;
													$scope.generalamount = data.detail.hashResponse["Amount"] / 100;
												} else {
													$scope.toggled = true;
													$scope.message = "Please Select your Package";
													$scope.nopackage = false;
												}
												// $scope.hash = {data
												// :data.detail.hashResponse};
												$scope.exporting = false;
												$scope.hash.serviceCode = data.detail.hashResponse["serviceCode"];
												$scope.hash.utilityCode = data.detail.hashResponse["Utility Code"];
												$scope.hash.commissionValue = data.detail.hashResponse["Commission Value"];
												$scope.hash.billNumber = data.detail.hashResponse["Bill Number"];
												$scope.hash.refStan = data.detail.hashResponse["RefStan"];
												$scope.userId = data.detail.hashResponse.userId;
												
											} else if (data.status == 'M0001') {
												$scope.message = data.message;
											} else if (data.status == 'M0000') {
												
												$scope.generalpMessage = data.message;
												$scope.message = "Transaction Completed";
												$scope.generalamount = '';
												$scope.transactionIdentifier = data.transactionIdentifier;
												//$scope.date = data.date;
												$scope.date =new Date();
												$scope.serviceTo = data.serviceTo;
												//added by amrit 
												$scope.bank_name=data.bankName;
												$scope.branch_name=data.branchName;
												$scope.service_icon=data.icon;
											
												//end of added 
												$scope.show = !$scope.show;
												
												$scope.getprintDetailForTransaction(data.transactionIdentifier);
											}
										})
								.error(
										function(data) {

											if (data.status == 'M0026') {
												
												$scope.generalMessage = data.message;
												$scope.message = data.message;
												$scope.generalamount = '';
												$scope.transactionIdentifier = data.transactionIdentifier;
												$scope.date = data.date;
												$scope.serviceTo = data.serviceTo;
												//added by amrit 
												$scope.bank_name=data.bankName;
												$scope.branch_name=data.branchName;
												$scope.service_icon=data.icon;
												alert(data.bankName);
												alert(data.branchName);
												alert(data.icon);
												//end of added 	
												$scope.show = !$scope.show;
											} else {
												$scope.exporting = false;
												$scope.message = "Transaction Failed";
												$scope.generalMessage = data.message;
											}
										});
					};
					
					$scope.getprintDetailForTransaction = function(transactionId){
						$http.get('/getprintdetail', {
							params : {
								transactionId : transactionId
							}
						}).success(function(data) {
							if (data.detail != null) {
								$scope.printDetail = data.detail;
							}
						});
					}
					$scope.print=function(tableId){
				        var mode = 'iframe'; 
				        var options = { mode : mode, popClose : close};
				        $("#"+tableId).printArea( options );
						   
					}

					// subishu online payment
					$scope.subishuMerchantPayment = function() {
						$scope.exporting = true;
						$http(
								{
									method : 'POST',
									url : 'subishuPayment',
									headers : {
										'Content-Type' : 'application/x-www-form-urlencoded'
									},
									transformRequest : function(obj) {
										var str = [];
										for ( var p in obj)
											str
													.push(encodeURIComponent(p)
															+ "="
															+ encodeURIComponent(obj[p]));
										return str.join("&");
									},
									data : {

										generalserviceUnique : $scope.generalserviceUnique,
										generaltransactionId : $scope.generaltransactionId,
										merchantUsername : $scope.generalcustomerId,
										generalamount : $scope.generalamount
									// hashMap:JSON.stringify($scope.hash)
									// hashMap:$scope.hash.userId;
									}
								})
								.success(
										function(data) {
											getbalance();

											if (data.status == 'M0000') {
												$scope.generalpMessage = data.message;
												$scope.message = "Transaction Completed";
												$scope.generalamount = '';
												$scope.transactionIdentifier = data.transactionIdentifier;
												$scope.date = data.date;
												$scope.serviceTo = data.serviceTo;
												$scope.show = !$scope.show;
											}
										})
								.error(
										function(data) {

											if (data.status == 'M0026') {
												$scope.generalMessage = data.message;
												$scope.message = data.message;
												$scope.generalamount = '';
												$scope.transactionIdentifier = data.transactionIdentifier;
												$scope.date = data.date;
												$scope.serviceTo = data.serviceTo;
												$scope.show = !$scope.show;
											} else {
												$scope.exporting = false;
												$scope.message = "Transaction Failed";
												$scope.generalMessage = data.message;
											}
										});
					};

					// recharge card generation online payment
					$scope.rechargePin = function() {
						$scope.exporting = true;
						$http(
								{
									method : 'POST',
									url : 'rechargePin',
									headers : {
										'Content-Type' : 'application/x-www-form-urlencoded'
									},
									transformRequest : function(obj) {
										var str = [];
										for ( var p in obj)
											str
													.push(encodeURIComponent(p)
															+ "="
															+ encodeURIComponent(obj[p]));
										return str.join("&");
									},
									data : {

										generalserviceUnique : $scope.generalserviceUnique,
										generalamount : $scope.generalamount
									}
								})
								.success(
										function(data) {

											if (data.status == 'M0000') {
												$scope.generalpMessage = data.message;
												$scope.message = "Transaction Completed, Please note the Pin Number for furthur use.";
												$scope.generalamount = '';
												$scope.transactionIdentifier = data.transactionIdentifier;
												$scope.date = data.date;
												$scope.pinNo = data.pinNo;
												$scope.serialNo = data.serialNo;
												$scope.show = !$scope.show;
											}
										})
								.error(
										function(data) {

											if (data.status == 'M0026') {
												$scope.generalMessage = data.message;
												$scope.message = data.message;
												$scope.generalamount = '';
												$scope.transactionIdentifier = data.transactionIdentifier;
												$scope.date = data.date;
												$scope.serviceTo = data.serviceTo;
												$scope.show = !$scope.show;
											} else {
												$scope.exporting = false;
												$scope.message = "Transaction Failed";
												$scope.generalMessage = data.message;
											}
										});
					};

					// nea online payment
					$scope.neaGetPayment = function() {
						$scope.exporting = true;
						$http(
								{
									method : 'POST',
									url : 'getNeaPayment',
									headers : {
										'Content-Type' : 'application/x-www-form-urlencoded'
									},
									transformRequest : function(obj) {
										var str = [];
										for ( var p in obj)
											str
													.push(encodeURIComponent(p)
															+ "="
															+ encodeURIComponent(obj[p]));
										return str.join("&");
									},
									data : {

										generalserviceUnique : $scope.generalserviceUnique,
										generaltransactionId : $scope.customerId,
										officecode : $scope.officeCode,
										scno : $scope.scno
									// hashMap:JSON.stringify($scope.hash)
									// hashMap:$scope.hash.userId;
									}
								})
								.success(
										function(data) {

											if (data.status == 'M0000') {

												$scope.hash = {};
												$scope.exporting = false;
												$scope.hash.utilityCode = data.detail.hashResponse["Utility Code"];
												$scope.hash.serviceCharge = data.detail.hashResponse["Service Charge"];
												$scope.hash.billNumber = data.detail.hashResponse["Bill Number"];
												$scope.hash.refStan = data.detail.hashResponse["RefStan"];
												$scope.hash.generalamount = data.detail.hashResponse["Amount"];
												$scope.hash.billableAmount = data.detail.hashResponse["Billable Amount"];
												$scope.hash.penalty = data.detail.hashResponse["Penalty"];
												$scope.hash.billamount = data.detail.hashResponse["Bill Amount"];
												$scope.hash.days = data.detail.hashResponse["Days"];
												$scope.hash.payment = data.detail.hashResponse["payment"];
												$scope.hash.customerName = data.detail.hashResponse["Customer Name"];
												$scope.payment = data.detail.payment;
												$scope.message = "Please Confirm the Payment";
												$scope.toggled = true;
											} else {
												$scope.exporting = false;
												$scope.message = "Transaction Failed";
												$scope.generalMessage = data.message;
											}
										}).error(function(data) {

									$scope.exporting = false;
									$scope.message = "Transaction Failed";
									$scope.generalMessage = data.message;
								});

					};

					// Nea online payment
					$scope.onlinePaymentNea = function() {
						$scope.exporting = true;
						$scope.generalMessage = {};
						$http(
								{
									method : 'POST',
									url : 'neapayment',
									headers : {
										'Content-Type' : 'application/x-www-form-urlencoded'
									},
									transformRequest : function(obj) {
										var str = [];
										for ( var p in obj)
											str
													.push(encodeURIComponent(p)
															+ "="
															+ encodeURIComponent(obj[p]));
										return str.join("&");
									},
									data : {
										generalserviceUnique : $scope.generalserviceUnique,
										generaltransactionId : $scope.customerId,
										officecode : $scope.officeCode,
										scno : $scope.scno,
										amount : $scope.hash.generalamount,
										utilityCode : $scope.hash.utilityCode,
										commissionValue : $scope.hash.penalty,
										billBumber : $scope.hash.billNumber,
										refStan : $scope.hash.refStan,
										payment : $scope.hash.payment

									// neaParameter:$scope.hash
									}
								})
								.success(
										function(data) {

											$scope.generalMessage = data.message;
											if (data.status == 'M0000') {
												$scope.toggled = false;
												$scope.success = true;
												$scope.generalpMessage = data.message;
												$scope.message = "Transaction Completed";
												$scope.generalamount = '';
												$scope.transactionIdentifier = data.detail["transactionIdentifier"];
												$scope.date = data.detail["transactionDate"];
												$scope.dueDate = data.detail["dueBillDate"];
												$scope.officeName = data.detail["officeName"];
												$scope.serviceTo = data.detail["customerName"];
												$scope.show = !$scope.show;

											}

										})
								.error(
										function(data) {

											if (data.status == 'M0026') {
												$scope.generalMessage = data.message;
												$scope.message = data.message;
												$scope.generalamount = '';
												$scope.transactionIdentifier = data.transactionIdentifier;
												$scope.date = data.date;
												$scope.serviceTo = data.serviceTo;
												$scope.show = !$scope.show;
											} else {
												$scope.exporting = false;
												$scope.message = "Transaction Failed";
												$scope.generalMessage = data.message;
											}
										});
					};

					// wlink online payment
					$scope.onlinePaymentWlink = function(url) {
						$scope.exporting = true;
						if ($scope.nopackage === true) {
							$scope.generalpackageamount = $scope.generalamount * 100;

						}
						// $scope.hash.amount = $scope.generalamount;
						// $scope.hash.serviceId = $scope.generalserviceUnique;
						// $scope.hash.serviceId=$scope.generaltransactionId
						$scope.generalMessage = {};
						$http(
								{
									method : 'POST',
									url : url,
									headers : {
										'Content-Type' : 'application/x-www-form-urlencoded'
									},
									transformRequest : function(obj) {
										var str = [];
										for ( var p in obj)
											str
													.push(encodeURIComponent(p)
															+ "="
															+ encodeURIComponent(obj[p]));
										return str.join("&");
									},
									data : {
										generalserviceUnique : $scope.generalserviceUnique,
										generaltransactionId : $scope.generaltransactionId,
										generalamount : $scope.generalpackageamount,
										serviceCode : $scope.hash.serviceCode,
										utilityCode : $scope.hash.utilityCode,
										commissionValue : $scope.hash.commissionValue,
										billBumber : $scope.hash.billNumber,
										refStan : $scope.hash.refStan,
										packageId : $scope.packageId,
										nopackage : $scope.nopackage
									// hashMap:$scope.hash
									}
								})
								.success(
										function(data) {

											$scope.generalMessage = data.message;
											if (data.status == 'M0000') {
												$scope.generalpMessage = data.message;
												$scope.message = "Transaction Completed";
												$scope.generalamount = '';
												$scope.transactionIdentifier = data.transactionIdentifier;
												$scope.date = data.date;
												$scope.serviceTo = data.serviceTo;
												$scope.show = !$scope.show;

											}

										})
								.error(
										function(data) {

											if (data.status == 'M0026') {
												$scope.generalMessage = data.message;
												$scope.message = data.message;
												$scope.generalamount = '';
												$scope.transactionIdentifier = data.transactionIdentifier;
												$scope.date = data.date;
												$scope.serviceTo = data.serviceTo;
												$scope.show = !$scope.show;
											} else {
												$scope.exporting = false;
												$scope.message = "Transaction Failed";
												$scope.generalMessage = data.message;
											}
										});
					};

					$scope.withdrawFund = function() {

						$http(
								{
									method : 'POST',
									url : '/withdrawfund',
									headers : {
										'Content-Type' : 'application/x-www-form-urlencoded'
									},
									transformRequest : function(obj) {
										var str = [];
										for ( var p in obj)
											str
													.push(encodeURIComponent(p)
															+ "="
															+ encodeURIComponent(obj[p]));
										return str.join("&");
									},
									data : {
										financialIncId : $scope.financialIncId,
										finamount : $scope.finamount
									}
								}).success(function(data) {
							getbalance();

							$scope.p2pmessage = data.message;

							if (data.status == 'success') {
								$scope.financialIncId = '';
								$scope.finamount = '';
							}
						});
					};

					$scope.companyInfo = function(url) {

						$http(
								{
									method : 'POST',
									url : url,
									headers : {
										'Content-Type' : 'application/x-www-form-urlencoded'
									},
									transformRequest : function(obj) {
										var str = [];
										for ( var p in obj)
											str
													.push(encodeURIComponent(p)
															+ "="
															+ encodeURIComponent(obj[p]));
										return str.join("&");
									},
									data : {
										generalserviceUnique : $scope.generalserviceUnique
									}
								}).success(function(data) {
							getbalance();

							$scope.generalpMessage = data.message;

						});

					};

					// for dish home balance Enqury
					$scope.balanceEnquiry = function(url) {
						$scope.generalMessage = {};
						$http(
								{
									method : 'POST',
									url : url,
									headers : {
										'Content-Type' : 'application/x-www-form-urlencoded'
									},
									transformRequest : function(obj) {
										var str = [];
										for ( var p in obj)
											str
													.push(encodeURIComponent(p)
															+ "="
															+ encodeURIComponent(obj[p]));
										return str.join("&");
									},
									data : {
										generalserviceUnique : $scope.generalserviceUnique

									}
								}).success(function(data) {

							$scope.generalMessage = data.details;

						});
					};

					// for dish home balance Recharge
					$scope.dishHomeRecharge = function(url) {
						$scope.generalMessage = {};
						$http(
								{
									method : 'POST',
									url : url,
									headers : {
										'Content-Type' : 'application/x-www-form-urlencoded'
									},
									transformRequest : function(obj) {
										var str = [];
										for ( var p in obj)
											str
													.push(encodeURIComponent(p)
															+ "="
															+ encodeURIComponent(obj[p]));
										return str.join("&");
									},
									data : {
										generalserviceUnique : $scope.generalserviceUnique,
										generalamount : $scope.generalamount
									}
								}).success(function(data) {

							$scope.generalMessage = data.message;
							$scope.generalamount = "";

						});
					};

					// for dish home balance Recharge by cas Id
					$scope.rechargeByCasId = function(url) {
						$scope.generalMessage = {};
						$scope.error = {};
						var amountData = -1;
						if ($scope.casId === undefined) {
							$scope.error.casId = "Invalid CasId";
							return;
						}

						if (!$scope.generalamount === undefined
								&& !isNaN($scope.generalamount)
								&& angular.isNumber(+$scope.generalamount)) {
							amountData = $scope.generalamount;

						} else {
							$scope.error.amount = "Invalid amount";
							return;
						}
						$http(
								{
									method : 'POST',
									url : url,
									headers : {
										'Content-Type' : 'application/x-www-form-urlencoded'
									},
									transformRequest : function(obj) {
										var str = [];
										for ( var p in obj)
											str
													.push(encodeURIComponent(p)
															+ "="
															+ encodeURIComponent(obj[p]));
										return str.join("&");
									},
									data : {
										generalserviceUnique : $scope.generalserviceUnique,
										generalamount : amountData,
										casId : $scope.casId
									}
								}).success(function(data) {

							$scope.generalMessage = data.message;
							$scope.generalamount = "";
							$scope.casId = "";

						});
					};

					// united solutions balance check
					$scope.unitedSolutionBalancecheck = function(url) {
						$scope.generalMessage = {};
						$http(
								{
									method : 'POST',
									url : url,
									headers : {
										'Content-Type' : 'application/x-www-form-urlencoded'
									},
									transformRequest : function(obj) {
										var str = [];
										for ( var p in obj)
											str
													.push(encodeURIComponent(p)
															+ "="
															+ encodeURIComponent(obj[p]));
										return str.join("&");
									},
									data : {
										generalserviceUnique : $scope.generalserviceUnique
									}
								}).success(function(data) {

							$scope.generalMessage = data.details;

						});
					};

					// epay online payment
					$scope.onlinePaymentEpay = function(url) {
						$scope.generalMessage = {};
						$http(
								{
									method : 'POST',
									url : url,
									headers : {
										'Content-Type' : 'application/x-www-form-urlencoded'
									},
									transformRequest : function(obj) {
										var str = [];
										for ( var p in obj)
											str
													.push(encodeURIComponent(p)
															+ "="
															+ encodeURIComponent(obj[p]));
										return str.join("&");
									},
									data : {
										generalserviceUnique : $scope.generalserviceUnique,
										generalamount : $scope.generalamount
									}
								}).success(function(data) {

							$scope.generalMessage = data.message;
							$scope.generalamount = "";

						});
					};
					
					

					$scope.getCardlessBankBalance = function() {
						if ($scope.bank != undefined
								&& $scope.cardlessBank != undefined) {
							$http.get('/cardlessbank/getbalance', {
								params : {
									bank : $scope.bank,
									cardlessbank : $scope.cardlessBank
								}
							}).success(function(data) {
								$scope.currentBalance = data;
							});
						}
					}

					$scope.loadCardlessBalance = function() {
						var valid = true;
						$scope.bankError = "";
						if ($scope.bank == undefined) {
							$scope.bankError = "Select a Bank";
							valid = false;
						}
						$scope.cardlessBankError = "";
						if ($scope.cardlessBank == undefined) {
							$scope.cardlessBankError = "Select a cardlessBank";
							valid = false;
						}
						$scope.amountError = "";
						if ($scope.amount == undefined || isNaN($scope.amount)) {
							$scope.amountError = "Invalid Amount";
							valid = false;
						}
						if (!valid) {
							return;
						}
						$http(
								{
									method : 'POST',
									url : '/ledger/cardlessbankcreditlimits',
									headers : {
										'Content-Type' : 'application/x-www-form-urlencoded'
									},
									transformRequest : function(obj) {
										var str = [];
										for ( var p in obj)
											str
													.push(encodeURIComponent(p)
															+ "="
															+ encodeURIComponent(obj[p]));
										return str.join("&");
									},
									data : {
										bank : $scope.bank,
										cardlessBank : $scope.cardlessBank,
										amount : $scope.amount
									}
								}).success(function(data) {
									if(data.status === "success"){
										angular.element(successModal).modal("show");
									}else{
										angular.element(errorModal).modal("show");
									}
						});
					}
					
					//added for the decrease balance
					
					$scope.decreasebalance = function() {
						
						$scope.responseMessage="";
						angular.element(confirmationModal).modal("hide");
						
					
						$http(
								{
									method : 'POST',
									url : '/ledger/decrease_balance',
									headers : {
										'Content-Type' : 'application/x-www-form-urlencoded'
									},
									transformRequest : function(obj) {
										var str = [];
										for ( var p in obj)
											str
													.push(encodeURIComponent(p)
															+ "="
															+ encodeURIComponent(obj[p]));
										return str.join("&");
									},
									data : {
										bank : $scope.bank,
										balance : $scope.balance,
										remarks : $scope.remarks
									}
								}).success(function(data) {
							/* getbalance(); */
							
                        
							if (data.status == 'success') {
								$scope.errorMessage = "";
								angular.element(successModal).modal("show");
								$scope.bank = '';
								$scope.balance = '';
								$scope.remarks='';
								$window.location.href = "/";
							}
							else{
								$scope.errorMessage = data.message;
							}
						});
				
					};
			
					//for dynamic office code of nea
					$scope.getOfficeCode=function(){
				
				
						if($scope.isFirstHit!=true){
							$scope.neaOfficeCodeList={};
					
						$scope.message="Nea Offices Loading !!";
						$http.get("get/neaofficecode").then(function success(data){
							$scope.neaOfficeCodeList=data.data.details;
							$scope.message="";
							$scope.isFirstHit=true;
							alert("success data : "+neaOfficeCodeList);
							
						},
						function error(data){
							alert("error data : "+data);
							$scope.message="Failed to load nea offices";
						});//end of then
						}//end of if
					}
					 
				});
