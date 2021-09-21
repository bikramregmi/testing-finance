angular
		.module("tmkmobilebanking")
		.controller(
				"reportController",
				function($scope, $http) {
					$scope.message = "";
					$scope.getDataForReport = function(reportFor) {
						$scope.reportDataLoad = false;
						if (reportFor === "CustomerRegestration") {
							getCustomerRegestrationReport();
						} else if (reportFor === "loadFund") {
							getLoadFundReport();
						} else if (reportFor === "businessSummary") {
							getBusinessSummaryReport();
						} else if (reportFor === "smsLogReport") {
							getSMSLogReport();

						} else if (reportFor === "creditLimit") {

							getCreditLimitReport();
							getCreditLimitLedgerReport()

						}
					}

					// credit-limit-ledger-report

					function getCreditLimitLedgerReport() {

						$scope.creditLimitLedger = {};
						$scope.today = new Date();
						$scope.fromDate = $('#from_date').val();
						$scope.toDate = $('#to_date').val();
						try {

							let parameter = {
								from_date : $('#from_date').val(),
								to_date : $('#to_date').val(),
							};

							get(
									"/credit-limit-ledger-report",
									function(data) {
										if (data == null) {
											alert("No Data Found for the given date range !!");
										} else {
											$scope.creditLimitLedger = data;
											$scope.reportDataLoad = true;
										}
									}, parameter);// end of get
						}// end try
						catch (message) {

						}// end catch
					}

					function getCreditLimitReport() {
						$scope.creditLimitReport = {};
						$scope.today = new Date();

						try {
							let parameter = {

							};

							get("/credit-limit-report",

							function(data) {

								$scope.creditLimitReport = data;

							}, parameter

							);// end of get
						}// end try
						catch (message) {
							// alert(message);
							console.log(message);
						}// end catch
					} // end of function

					function getSMSLogReport() {

						$scope.hide = true;
						$scope.smsReport = {};

						var fDate = "";
						var tDate = "";
						$scope.today = new Date();
						if ($scope.fromDate != null
								|| $scope.fromDate != 'undefined') {
							fDate = $scope.fromDate;
						}
						if ($scope.toDate != null
								|| $scope.toDate != 'undefined') {
							tDate = $scope.toDate;
						}
						if ($scope.fromDate == "" || $scope.fromDate == null) {
							$scope.message = "Please select date filter.";
						} else {
							$scope.message = "";
							$scope.responseMessage = "Please Wait - Data Is Being Retrieved !!";
							try {
								let parameter = {
									from_date : fDate,
									to_date : tDate

								};// end of let parameter
								get("/sms-log-report-data", function(data) {

									$scope.smsReport = data;
									$scope.reportDataLoad = true;
									$scope.hide = false;
									$scope.responseMessage = "";
								}, parameter);
							}// end of try
							catch (message) {
							}
						}
					}// end of getSMSReport
					function getCustomerRegestrationReport() {
						$scope.registrationReport = {};

						$scope.hide = true;
						if ($('#from_date').val() == ""
								|| $('#from_date').val() == null) {
							$scope.message = "Please select date filter.";
						} else {
							$scope.message = "";
							$scope.responseMessage = "Please Wait - Data Is Being Retrieved !!";
							try {
								let parameter = {
									bank : $('#bankFilter option:selected').attr('bankId'),
									branch : $('#branchFilter option:selected').attr('branchId'),
									fromDate : $('#from_date').val(),
									toDate : $('#to_date').val(),
								};
								get("/customer/regestrationreport", function(
										data) {
									$scope.registrationReport = data;
									$scope.hide = false;
									$scope.responseMessage = "";
									$scope.reportDataLoad = true;
								}, parameter);
							} catch (message) {
							}
						}
					}

					function getLoadFundReport() {
						$scope.loadFundReport = {};

						try {
							let parameter = {
								fromDate : $('#from_date').val(),
								toDate : $('#to_date').val(),
							};
							get("/ledger/loadfundreport", function(data) {
								$scope.loadFundReport = data;
								$scope.reportDataLoad = true;

							}, parameter);
						} catch (message) {
						}
					}

					function getBusinessSummaryReport() {
						$scope.businessSummaryReport = {};
						$scope.hide = true;
						$scope.responseMessage = "Please Wait - Data Is Being Retrieved !!";
						try {
							get("/businessSummaryReport", function(data) {
								$scope.businessSummaryReport = data;
								$scope.reportDataLoad = true;
								$scope.hide = false;
								$scope.responseMessage = "";
							});
						} catch (message) {
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

					$scope.downloadReport = function(fileType, reportType,
							tableName) {
						var date = new Date();
						if (fileType === "PDF") {
							$('#' + tableName).tableExport({
								fileName : reportType + date.getTime(),
								type : 'pdf',
								jspdf : {
									orientation : 'l',
									format : 'a2',
									margins : {
										left : 10,
										right : 10,
										top : 20,
										bottom : 20
									},
									autotable : {
										styles : {
											fillColor : 'inherit',
											textColor : 'inherit'
										},
										tableWidth : 'auto'
									}
								}
							});
						} else if (fileType === "EXCEL") {
							$("#" + tableName).tableExport({
								fileName : reportType + date.getTime(),
								worksheetName : 'Report',
								exportHiddenCells : true,
								type : "excel"
							});
						}
					};

					// added for the electronic reports
					$scope.getReport = function(bank, date, reportType) {

						var url = "";
						$scope.message = "";
						$scope.responseMessage = "";
						$scope.datePara = $("#nepaliDate").val();
						// alert($scope.datePara);
						$scope.bankPara = bank;
						$scope.ebankingReport = {};
						$scope.etransactionReport = {};
						if (reportType === "ebanking") {
							url = "/e_banking_report";
							// $scope.datePara=monthYear;
						}
						if (reportType === "etransaction") {
							url = "/e_transaction_report";
							// $scope.datePara=date;
						}

						if (url === "") {
							// alert("Please Select the Report Type")
							$scope.message = "Please Select the Report Type";
						}

						if (!($scope.datePara == null || $scope.datePara == "")) {
							// if(!($scope.message=="")){
							try {
								let parameter = {
									date : $scope.datePara,
									bank : $scope.bankPara
								};

								get(
										url,
										function(data) {
											// alert(data);
											if (reportType === "ebanking") {
												$scope.ebankingReport = data;
												// alert($scope.ebankingReport);
												// $scope.downloadReport('EXCEL','e-bankingReport','ebanking_report');
												setTimeout(function() {
													$scope.downloadReport(
															'EXCEL',
															'e-bankingReport',
															'ebanking_report')
												}, 2000);
												$scope.responseMessage = "E-Banking Report Downloaded";

											} else if (reportType === "etransaction") {
												$scope.etransactionReport = data;
												// $scope.downloadReport('EXCEL','e-transactionReport','etransaction_report');

												setTimeout(
														function() {
															$scope
																	.downloadReport(
																			'EXCEL',
																			'e-transactionReport',
																			'etransaction_report')
														}, 2000);
												$scope.responseMessage = "E-Transaction Report Downloaded";
											}

										}, parameter);

								angular.element(filterModal).modal("hide");
							} catch (message) {

							}

						} else {
							$scope.message = "please select Date ";
						}
					}

					$scope.generateRevenueReport = function() {

						$scope.revenueDataLoad = false;
						$scope.hide = true;
						if ($('#fromDateFilter').val() == ""
								|| $('#fromDateFilter').val() == null) {
							$scope.message = "Please select date filter.";
						} else {
							$scope.message = "";
							$scope.responseMessage = "Please Wait - Data Is Being Retrieved !!";
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
					}

					$scope.reversedTransactionReport = function() {
						$scope.reversedReportDataLoad = false;
						$scope.hide = true;
						if ($('#fromDateFilter').val() == ""
								|| $('#fromDateFilter').val() == null) {
							$scope.message = "Please select date filter.";
						} else {
							$scope.message = "";
							$scope.responseMessage = "Please Wait - Data Is Being Retrieved !!";
							$http.get("/getReversalReport", {
								params : {
									bank : $('#bankFilter').val(),
									fromDate : $('#fromDateFilter').val(),
									toDate : $('#toDateFilter').val(),
								}
							}).then(function success(data) {
								$scope.reversalReport = data.data.detail;
								$scope.reversedReportDataLoad = true;
								$scope.hide = false;
								$scope.responseMessage = "";
							}, function failure(data) {
								$scope.message = "unable to load data !!";
							});
						}
					}

					$scope.getCustomerList = function(fromDate, toDate, bank,
							branch) {

						var fromDatePara = "";
						var toDatePara = "";
						var bankPara = "";
						var branchPara = "";
						var url = "/customerLogReport"
						$scope.customerList = {};
						if (fromDate != null) {

							fromDatePara = fromDate;

						}
						if (toDate != null) {
							toDatePara = toDate;
						}
						if (bank != null) {
							bankPara = bank;
						}
						if (branch != null) {
							branchPara = branch;
						}
						try {
							let parameter = {
								from_date : fromDatePara,
								to_date : toDatePara,
								bank : bankPara,
								branch : branchPara
							};
							get(url, function(data) {
								$scope.customerList = data;

							}, parameter);

						} catch (error) {

						}
					}

					$scope.getCustomerLogReport = function() {
						$scope.hide = true;

						$scope.customerLogList = {};
						if ($('#customerFilter').val() == null
								|| $('#customerFilter').val() == "") {
							$scope.responseMessage = "";
							$scope.message = "Please Select a customer to view log";
						} else {
							$scope.message = "";
							$scope.responseMessage = "Data is loading !!";
							$http
									.get('/customer/getCustomerLog', {
										params : {
											uuid : $('#customerFilter').val()
										}
									})
									.success(
											function(data) {
												if (data.responseStatus == 'SUCCESS') {
													$scope.customerLogList = data.detail;
													$scope.message = "";
													$scope.responseMessage = "";
													$scope.hide = false;

												}
											})
									.error(
											function(data) {
												$scope.responseMessage = "";
												$scope.message = "Something went Wrong,Please try again Later";
												$scope.generalMessage = data.message;

											});
						}
					}

				});