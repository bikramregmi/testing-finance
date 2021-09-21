var mobileBanking = angular.module("tmkmobilebanking", [ 'ui.sortable' ]);

mobileBanking.directive('fileModel', [ '$parse', function($parse) {
	return {
		restrict : 'A',
		link : function(scope, element, attrs) {
			var model = $parse(attrs.fileModel);
			var modelSetter = model.assign;

			element.bind('change', function() {
				scope.$apply(function() {
					modelSetter(scope, element[0].files[0]);
				});
			});
		}
	};
} ]);

mobileBanking
		.config(function($httpProvider, $httpParamSerializerJQLikeProvider) {
			$httpProvider.defaults.transformRequest
					.unshift($httpParamSerializerJQLikeProvider.$get());
			$httpProvider.defaults.headers.post['Content-Type'] = 'application/x-www-form-urlencoded; charset=utf-8';
			$httpProvider.defaults.headers.post['Accept'] = 'application/json; charset=utf-8';
		});

mobileBanking
		.controller(
				"tmkmobilebankingController",
				function($scope, $http, $timeout, $parse, $compile, $filter,
						$window) {
					$scope.sendBulkSmsAlert = function(){
						$scope.sendLoading = true;
						$('#sendAlertButton').prop("disabled",true);
						$('#sendAlertForm').submit();
					}

					$scope.uploadBulkSmsAlert = function() {
						$scope.uploadedData = false;
						$scope.uploadDataLoading = true;
						var formData = new FormData();
						formData.append("file", $scope.fileModel);
						$http({
							method : 'POST',
							url : '/smsalert/bulk/upload',
							headers : {
								'Content-Type' : undefined
							},
							data : formData,
							transformRequest : angular.indetity
						}).then(function success(data) {
							$('#smsAlertTable').DataTable().destroy();
							$scope.smsAlertList = data.data.details;
							 $timeout(function(){
								$('#smsAlertTable').DataTable();
								$scope.uploadDataLoading = false;
								$scope.uploadedData = true;
							},1000);
							/*$('#smsAlertTable').DataTable({
								data : data.data.details,
								columns : [ {
									data : 'mobileNumber'
								}, {
									data : 'message'
								} ]
							});*/

						}, function error(data) {
							console.log(data);
						});
					}

					$scope.getInactiveBank = function() {
						$scope.inactiveBankLoad = true;
						$http.get('/bank/inactivecbs').then(
								function success(data) {
									$scope.inactiveBankList = data.data;
									$scope.inactiveBankLoad = false;
								}, function error(data) {
									console.error(data);
								})
					}

					$scope.checkBankCbsStatus = function(bankId) {
						$('.badgeDiv' + bankId).html(
								"<i class='fa fa-spinner fa-spin fa-2x'></fa>");
						$http
								.get("/bank/checkcbsstatus", {
									params : {
										bank : bankId
									}
								})
								.then(
										function success(data) {
											$('.badgeDiv' + bankId)
													.html(
															'<span class="badge success-badge">Active</span>');
										},
										function error(data) {
											$('.badgeDiv' + bankId)
													.html(
															'<span class="badge error-badge">Inactive</span>');
										});
					}

					$scope.getHomeScreenImages = function() {
						$http
								.get("/homescreenimage/getimages")
								.then(
										function success(data) {
											$scope.homeScreenImageList = data.data.detail;
										}, function error(data) {
											console.error("error-> ", data);
										});
					}
					$scope.homeScreenImageSortableOptions = {
						'update' : homeScreenImageUpdate
					}

					function homeScreenImageUpdate() {
						$scope.edited = true;
						$('#homeScreenAlert').remove();
					}

					$scope.updateImage = function() {
						let data = JSON.stringify($scope.homeScreenImageList);
						$http
								.get("/homescreenimage/update", {
									params : {
										homeScreenImageList : data
									}
								})
								.then(
										function success(data) {
											$scope.edited = false;
											$('#alertDiv')
													.html(
															'<div id="homeScreenAlert" class="alert alert-success alert-dismissable fade in" style="padding: 5px; margin-bottom: 0px;">'
																	+ '<a href="#" class="close" data-dismiss="alert" aria-label="close" style="right: 5px;">&times;</a>'
																	+ '<strong>Success!</strong> Homescreen Image Updated Successfully.'
																	+ '</div>');
										}, function error(data) {
											console.error("error-> ", data);
										})
					}

					$scope.deleteHomeScreenImageConfirm = function(id) {
						$scope.selectedId = id;
						angular.element(deleteModal).modal("show");
					}

					$scope.changeBranch = function(userId, branchId) {
						$scope.selectedBranchId = branchId;
						$scope.changeBranchUserId = userId;
						angular.element(changeBranchModal).modal("show");
					}

					$scope.hide = true;
					$scope.getCardlessbankToAdd = function(bankCode) {
						$http.get('/cardless/account/add/getcardlessbank', {
							params : {
								bank : bankCode
							}
						}).then(function success(data) {
							$scope.cardlessBankList = data.data;
						}, function failure(data) {
							console.log("error: ", data);
						});
					}

					$scope.cardlessBankSelected = function(cardless) {
						$scope.cardlessSelected = true;
						console.log(cardless);
						$scope.selectedCardlessBankName = cardless.bank;
						$('#cardlessBankId').val(cardless.id);
					}

					$scope.setRenewCustomer = function(name, uniqueId) {
						$scope.response = false;
						$scope.loading = false;
						$scope.customer = {};
						$scope.customer.name = name;
						$scope.customer.uniqueId = uniqueId;
					}

					$scope.reload = function() {
						$window.location.reload();
					}

					$scope.getRenewedCustomer = function() {
						$scope.renewedListLoad = true;
						$http.get("/customer/renewed/get", {
							params : {
								days : $scope.days
							}
						}).then(function success(data) {
							$scope.renewedCustomerList = data.data.detail;
							$scope.renewedListLoad = false;
						}, function error(data) {
							console.log(data);
						});
					}
					$scope.days = 7;
					$scope.getRenewedCustomer();

					$scope.renewCustomer = function() {
						$scope.loading = true;
						$http(
								{
									method : 'POST',
									url : '/customer/renew',
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
										customer : $scope.customer.uniqueId,
									}
								})
								.then(
										function success(data) {
											$scope.response = true;
											$scope.message = data.data.message;
											if (data.data.responseStatus === "SUCCESS") {
												$scope.success = true;
											} else {
												$scope.success = false;
											}

										},
										function error(data) {
											$scope.response = true;
											$scope.message = "Something went wrong. Please try again later.";
										});
					}

					$scope.exportReport = function() {
						$http
								.get('/transaction/report/financial/export')
								.success(
										function(data) {
											console.log(data);
											if (data.responseStatus == "SUCCESS") {
												window
														.open("/exportedpdf/"
																+ data.message
																+ ".pdf");
											}
										});
					}

					$scope.addSparrowSMSCredit = function() {

						$http(
								{
									method : 'POST',
									url : 'addsparrowsmscredit',
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
										smsCount : $scope.smscount,
									}
								})
								.success(
										function(data) {

											if (data.responseStatus == "SUCCESS") {
												$timeout(function() {
													angular
															.element(
																	document
																			.getElementsByClassName("close")[0])
															.trigger('click');
												});
												$scope.message = "SMS count updated Succesfully";
												$window.location.reload();
											}

										});
					}

					$scope.setTransactionIdentifier = function(
							transactionIdentifier) {
						$scope.transactionIdentifier = transactionIdentifier;
					}

					$scope.setCustomerBankAccount = function(uniqueId,
							currentCustomerBankAccountNo, accountMode, id) {
						$("#uniqueIdedit").val(uniqueId);
						$("#currentAccountNumberedit").val(
								currentCustomerBankAccountNo);
						$("#accountNumberedit").val(
								currentCustomerBankAccountNo);
						$("#uniqueIdadd").val(uniqueId);
						$scope.currentCustomerBankAccountNo = currentCustomerBankAccountNo;
						$scope.accountTypeScope = accountMode;
						$scope.accountId = id;
						$scope.setCustomerUniqueId(uniqueId);
					}

					$scope.setCustomerUniqueId = function(uniqueId) {
						$scope.customerUniqueId = uniqueId;
					}

					$scope.setCustomerUniqueIdAndMobileNumber = function(
							uniqueId, mobileNumber) {
						$scope.customerUniqueId = uniqueId;
						$scope.mobileNumber = mobileNumber;
					}

					$scope.loadUsername = function(username) {
						$scope.username = username;
					}

					// to load sms count
					$scope.loadBankId = function(id) {
						$scope.bankId = id;
					}

					$scope.setCustomerProfileId = function(id) {
						$scope.profileId = id;
					}
					$scope.getCustomerLog = function(uuid) {
						$http
								.get('getCustomerLog', {
									params : {
										uuid : uuid
									}
								})
								.success(
										function(data) {
											if (data.responseStatus == 'SUCCESS') {
												$scope.customerLogList = data.detail;
												setTimeout(function() {
													$('#customerLogList')
															.dataTable()
															.fnUpdate();
												}, 500);
												angular.element(
														customerLogModal)
														.modal("show");

											}
										})
								.error(
										function(data) {

											$scope.message = "Something went Wrong,Please try again Later";
											$scope.generalMessage = data.message;
											angular.element(customerLogModal)
													.modal("show");
										});
					}

					$scope.addSmsCount = function() {

						$http(
								{
									method : 'POST',
									url : 'addSmsCount',
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
										smsCount : $scope.smscount,
										remarks : $scope.remarks,
										bankId : $scope.bankId

									}
								})
								.success(
										function(data) {

											if (data.responseStatus == "SUCCESS") {
												$timeout(function() {
													angular
															.element(
																	document
																			.getElementsByClassName("close")[0])
															.trigger('click');
												});
												$scope.message = "SMS count updated Succesfully";
												$window.location.reload();
												// angular.element(messageModal).modal("show");

											}

										})
								.error(
										function(data) {
											$scope.message = "Error  while updating SMS Count";
											angular.element(messageModal)
													.modal("show");

										});
					}

					$scope.addLicenseCount = function() {

						$http(
								{
									method : 'POST',
									url : 'addLicenseCount',
									headers : {
										'Content-Type' : 'application/x-www-form-urlencoded'
									},
									// transformRequest : function(obj) {
									// var str = [];
									// for ( var p in obj)
									// str
									// .push(encodeURIComponent(p)
									// + "="
									// + encodeURIComponent(obj[p]));
									// return str.join("&");
									// },
									data : {
										licenseCount : $scope.licenseCount,
										remarks : $scope.remarks,
										bankId : $scope.bankId

									}
								})
								.success(
										function(data) {

											if (data.responseStatus == "SUCCESS") {
												$timeout(function() {
													angular
															.element(
																	document
																			.getElementsByClassName("close")[0])
															.trigger('click');
												});
												$scope.message = "License Count updated Succesfully";
												$window.location.reload();
												// angular.element(messageModal).modal("show");

											}

										})
								.error(
										function(data) {
											$scope.message = "Error  while updating SMS Count";
											angular.element(messageModal)
													.modal("show");

										});
					}

					$scope.showDialog = function(status) {
						if (status === "PROCESSING") {
							alert("The Customer	will be notified for the complition of his cheque Request");
						}

					}
					$scope.loadCategoryId = function(id) {
						$scope.categoryId = id;
					}

					$scope.loadUserId = function(id) {
						$scope.selectedUserId = id;
					}

					var state;

					$scope.getUserById = function(userId, userType) {
						$scope.userId = userId;

						if (userType == "ChannelPartner") {
							$scope.checkerHide = true;
						} else {
							$scope.checkerHide = false;

						}
						$http.get('getUser', {
							params : {
								userid : userId
							}
						}).success(function(data) {
							if (data.responseStatus == 'SUCCESS') {
								$scope.address = data.detail.address;
								$scope.city = data.detail.city;
								$scope.state = data.detail.state;
								if (data.detail.checker) {
									$scope.checker = true;
								}
								if (data.detail.maker) {
									$scope.maker = true;
								}
								getUser($scope.state, $scope.city);
							}
						});
					}

					function getUser(state, city) {
						$http.get('getCityList', {
							params : {
								state : state
							}
						}).success(function(data) {
							$scope.cityList = data.detail;
							for ( var key in data.detail) {
								if (data.detail[key].name === city) {
									$scope.city = data.detail[key].id;
								}
							}

						});
					}

					// to change password
					$scope.userError = {
						oldPassword : '',
						password : '',
						repassword : '',
					}
					$scope.passwordBlank = {
						oldPassword : '',
						password : '',
						repassword : '',
					}

					function clearPasswordError() {
						$scope.userError = $scope.passwordBlank;
					}

					function clearPasswordData() {

						$scope.oldPassword = ''
						$scope.password = ''
						$scope.repassword = ''
						$scope.username = ''
					}

					$scope.changePassword = function(url) {

						var userNameData = "";
						var oldPasswordData = "";
						var repasswordData = "";
						var passwordData = "";
						$scope.changePasswordLoading = true;
						if (!($scope.oldPassword === undefined)) {
							oldPasswordData = $scope.oldPassword;
						}
						if (!($scope.password === undefined)) {
							passwordData = $scope.password;
						}
						if (!($scope.repassword === undefined)) {
							repasswordData = $scope.repassword;
						}
						if (!($scope.password === $scope.repassword)) {
							$scope.changePasswordLoading = false;
							$scope.userError.repassword = "Password didn't match";
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
										userName : $scope.username,
										password : passwordData,
										oldPassword : oldPasswordData,
										repassword : repasswordData
									}
								})
								.success(
										function(data) {

											if (data.responseStatus == "SUCCESS") {
												clearPasswordError();
												clearPasswordData();
												if (url === 'changepasswordbyadmin') {
													angular.element(
															confirmPassword)
															.modal("show");
												} else {
													$scope.passwordChangeSuccess = true;
													$scope.logoutSecCounter = 5;
													var logoutTimer = setInterval(
															function() {
																$scope.logoutSecCounter--;
																$scope.$apply();
																if ($scope.logoutSecCounter == 0) {
																	$window.location.href = "/logout";
																	clearInterval(logoutTimer);
																}
															}, 1000);
												}

												$scope.myMessage = '';
											} else if (data.responseStatus == "INTERNAL_SERVER_ERROR") {
												$scope.myMessage = data.message;
												$scope.changePasswordLoading = false;
												clearPasswordError();

											} else if (data.responseStatus == "VALIDATION_FAILED") {
												$scope.myMessage = data.message
												$scope.userError = data.detail;
												$scope.changePasswordLoading = false;
											} else {
												$scope.myMessage = "Contact Operator";
												$scope.changePasswordLoading = false;
											}

										});
					}

					// to edit user
					$scope.userError = {
						address : '',
						city : '',
						state : '',
					}

					$scope.userEditBlank = {
						address : '',
						city : '',
						state : '',
					}

					function cleareditError() {
						$scope.userError = $scope.userEditBlank;
					}

					function clearEditUserData() {

						$scope.address = '', $scope.state = '',
								$scope.city = '', $scope.userId = '',
								$scope.maker = '', $scope.checker = ''
					}

					$scope.editUser = function(url) {
						console.log("userId", $scope.userId);
						var addressData = "";
						var stateData = "";
						var cityData = "";
						var userData = "";
						var makerData = false;
						var checkerData = false;

						if (!($scope.address === undefined))
							addressData = $scope.address;
						if (!($scope.state === undefined))
							stateData = $scope.state;
						if (!($scope.city === undefined))
							cityData = $scope.city;
						if ($scope.maker != undefined)
							makerData = $scope.maker;
						if ($scope.checker != undefined)
							checkerData = $scope.checker;

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
										id : $scope.userId,
										city : cityData,
										address : addressData,
										state : stateData,
										maker : makerData,
										checker : checkerData
									}
								})
								.success(
										function(data) {

											if (data.responseStatus == "SUCCESS") {
												$timeout(function() {
													angular
															.element(
																	document
																			.getElementsByClassName("close")[1])
															.trigger('click');
												});
												$window.location.reload();
												cleareditError();
												clearEditUserData();
												$scope.myMessage = '';
											} else if (data.responseStatus == "INTERNAL_SERVER_ERROR") {
												$scope.myMessage = data.message
												clearPasswordError();

											} else if (data.responseStatus == "VALIDATION_FAILED") {
												$scope.myMessage = data.message
												$scope.userError = data.detail;
											} else {
												$scope.myMessage = "Contact Operator";
											}

										});
					}

					// for bulk upload
					$scope.uploadBulkFile = function(url) {
						var uploadUrl = url;
						var formData = new FormData();
						formData.append("file", $scope.fileModel);
						$http({
							method : 'POST',
							url : uploadUrl,
							headers : {
								'Content-Type' : undefined
							},
							data : formData,
							transformRequest : angular.indetity
						})
								.success(
										function(data, status) {
											$scope.fileModel = '';
											angular
													.element(
															document
																	.getElementsByClassName("close")[0])
													.trigger('click');
											$scope.message = data.message;
											angular.element(errorModal).modal(
													"show");
											if (url === '/bulkBranchUpload') {
												$window.location.href = "/bank/branch/list";
											}
											if (url === '/bulkCustomerUpload') {
												$window.location.href = "/customer/list";
											} else {
												$window.location.href = "/listBank";
											}

										}).error(function(data) {

									$scope.message = data.message;
									angular.element(errorModal).modal("show");
								});

					}

					$scope.multiServiceSelected = function(serviceId,
							merchantId) {
						$http.get('/merchant/service/merchantpriority', {
							params : {
								serviceId : serviceId,
								merchantId : merchantId
							}
						}).success(function(data) {
							if (data.responseStatus == 'SUCCESS') {
								$window.location.reload();
							}
						});
					}

					// edit service category

					$scope.editServiceCategory = function(url) {
						serviceCategoryError = {};
						var nameData = "";

						if (!($scope.name === undefined)) {
							nameData = $scope.name;
						}

						$http(
								{
									method : 'POST',
									url : 'editServiceCategory',
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
										serviceCategoryId : $scope.categoryId,
										name : nameData
									}
								})
								.success(
										function(data) {

											if (data.responseStatus == "SUCCESS") {
												$timeout(function() {
													angular
															.element(
																	document
																			.getElementsByClassName("close")[1])
															.trigger('click');
												});
												$window.location.reload();
												$scope.myMessage = '';
											} else if (data.responseStatus == "INTERNAL_SERVER_ERROR") {
												$scope.myMessage = data.message
												clearPasswordError();

											} else if (data.responseStatus == "VALIDATION_FAILED") {
												$scope.myMessage = data.message
												$scope.serviceCategoryError = data.detail;
											} else {
												$scope.myMessage = "Contact Operator";
											}

										});
					}

					$scope.showTransactionDetails = function(transactionId) {
						$http
								.get('gettransactiondetail', {
									params : {
										transactionId : transactionId
									}
								})
								.success(
										function(data) {
											if (data.details != null) {
												$scope.transaction = data.details;
												$scope
														.getprintDetailForTransaction(transactionId);
											}
										});
					}

					$scope.getprintDetailForTransaction = function(
							transactionId) {
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

					$scope.reverseTransaction = function(transactionId) {
						$http
								.get('checktransaction', {
									params : {
										transactionId : transactionId
									}
								})
								.success(
										function(data) {
											if (data.status == 'success') {
												$scope.transactionmessage = data.message;
												angular.element(
														transactionComplete)
														.modal("show");
											} else {
												$scope.transactionmessage = data.message;
												angular
														.element(
																confirmReversal)
														.modal("show");
												$scope.transactionId = transactionId;
											}
										});

					}

					$scope.getSparrowSettings = function() {
						$http.get("/getSparrowSettings").then(
								function success(data) {
									$scope.sparrow = data.data.detail;
									angular.element(sparrowsmssettingsmodal)
											.modal("show");
									console.log($("#sparrowSuccessAlert"));
									$("#sparrowSuccessAlert").remove();
									$scope.editSparrowSettings = false;
								}, function failure(data) {
									console.log("error", data);
								});
					}
					$scope.updateSparrowSettings = function() {
						$http
								.post("/updateSparrowSettings", $scope.sparrow)
								.then(
										function success(data) {
											$scope.editSparrowSettings = false;
											$('#sparrowSettingsBody')
													.html(
															'<div id="sparrowSuccessAlert" class="alert alert-success alert-dismissable fade in">'
																	+ '<a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>'
																	+ '<strong>Success!</strong> Sparrow Setting updated Successfully.'
																	+ '</div>');
										}, function failure(data) {
											console.log("error", data);
										});
					}

					$scope.generateReportForTransaction = function() {
						$scope.getReversedTransactionReport();
						$scope.reportDataLoad = false;
						$scope.warnMessage = "";
						if ($('#toDateFilter').val() == ""
								|| $('#fromDateFilter').val() == "") {
							$scope.warnMessage = "Please Select a Date Range";
							$("#errorModal").modal("show");
							$("#errorModal").modal("show");
						} else {
							$http
									.get(
											"/getTransactionForreport",
											{
												params : {
													bank : $('#bankFilter')
															.val(),
													fromDate : $(
															'#fromDateFilter')
															.val(),
													toDate : $('#toDateFilter')
															.val(),
													identifier : $(
															'#identifierFilter')
															.val(),
													status : $('#statusFilter')
															.val(),
													mobileNo : $(
															'#mobileNumberFilter')
															.val(),
													serviceType : $(
															'#serviceTypeFilter')
															.val(),
													targer_no : $(
															'#targetNumberFilter')
															.val()
												}
											})
									.then(
											function success(data) {
												$scope.reportTransactionList = data.data.details;
												$scope.reportDataLoad = true;
											}, function failure(data) {

											});
						}
					}

					$scope.getReversedTransactionReport = function() {
						$scope.reversedReportDataLoad = false;
						$http.get("/getReversalReport", {
							params : {
								bank : $('#bankFilter').val(),
								fromDate : $('#fromDateFilter').val(),
								toDate : $('#toDateFilter').val(),
							}
						}).then(function success(data) {
							$scope.reversalReport = data.data.detail;
							$scope.reversedReportDataLoad = true;
						}, function failure(data) {

						});
					}

					$scope.generateReportForCommission = function() {

						$scope.reportDataLoad = false;
						$scope.hide = true;
						$scope.generateReportForRevenue();
						$http.get("/getCommissionForreport", {
							params : {
								bank : $('#bankFilter').val(),
								fromDate : $('#fromDateFilter').val(),
								toDate : $('#toDateFilter').val(),
								merchant : $('#merchantFilter').val()
							}
						}).then(function success(data) {
							$scope.reportCommissionList = data.data.details;
							$scope.reportDataLoad = true;
							$scope.hide = false;
							$scope.responseMessage = "";
						}, function failure(data) {

						});
					}

					$scope.generateReportForRevenue = function() {
						$scope.responseMessage = "Please Wait - Data Is Being Retrieved !!";
						$scope.hide = true;
						$scope.revenueDataLoad = false;
						$http.get("/getRevenueForreport", {
							params : {
								bank : $('#bankFilter').val(),
								fromDate : $('#fromDateFilter').val(),
								toDate : $('#toDateFilter').val(),
								merchant : $('#merchantFilter').val()
							}
						}).then(function success(data) {
							$scope.revenueReport = data.data.details;
							$scope.revenueDataLoad = true;
							$scope.hide = false;
							$scope.responseMessage = "";
						}, function failure(data) {
							console.log("error", data);
						});
					}

					// for fcm

					$scope.getNotificationGroup = function() {

						$http
								.get("/getgroup", {
									params : {
										bankCode : $scope.bankCode
									}
								})
								.then(
										function successCallback(response) {
											$scope.notificationGroupList = response.data.detail;
											console
													.log($scope.notificationGroupList.size);
										}, function errorCallback(response) {

										});

					}
					$scope.isEmpty = true;
					var firstUser = true;
					$scope.idList = [];

					$scope.findUser = function() {
						if ($scope.user === undefined || $scope.user === ""
								|| $scope.user === null) {
							$scope.isEmpty = true;
							$scope.userList = [];
							return;
						}
						$http.get("/findCustomer", {
							params : {
								data : $scope.user,
								bankCode : $scope.bankCode
							}
						}).then(function successCallback(response) {
							if (response.data.detail.length == 0) {
								$scope.isEmpty = true;
								$scope.userList = [];
							} else {
								$scope.userList = response.data.detail;
								$scope.isEmpty = false;
							}
						}, function errorCallback(response) {
							$scope.isEmpty = true;
						});
					}

					$scope.setUser = function(user) {
						$scope.idList.push(user.id);
						$('input[name=idList]').val($scope.idList);
						let newCustomerDiv = $("<div class='customer' ng-click='removeUser("
								+ user.id
								+ ")' id='divId"
								+ user.id
								+ "'>"
								+ user.fullName
								+ "<i class='fa fa-times close-button' aria-hidden='true'></i></div>");
						$("#customerDiv").append(newCustomerDiv);
						$compile(newCustomerDiv)($scope);
						$scope.userList = "";
						$scope.user = "";
						$("#dataInput").focus();
						$scope.isEmpty = true;
					}
					$scope.removeUser = function(id) {
						var index = $scope.idList.indexOf(id);
						if (index > -1) {
							$scope.idList.splice(index, 1);
						}
						$('#divId' + id).remove();
						$('input[name=idList]').val($scope.idList);
					}
					// start of the print function by amrit
					$scope.td_table = "transactionDetail_table";
					$scope.tableName = "reportTable";

					$scope.print = function(tableId) {
						var mode = 'iframe';
						var options = {
							mode : mode,
							popClose : close
						};
						$("#" + tableId).printArea(options);

					}

					$scope.getAllBank = function() {
						$http.get("/getAllBank").success(
								function success(data) {
									$scope.bankList = data.detail;
								})
					}

					$scope.getBranchByBank = function(bank) {
						$http.get("/getBranchByBank", {
							params : {
								bank : bank
							}
						}).success(function success(data) {
							$scope.branchList = data.detail;
						})
					}

				});
