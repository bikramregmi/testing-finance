package com.wallet.serviceprovider.paypoint;

public class OperationsHttpPost implements OperationsHttpPostInterface{

	@Override
	public String checkPayment(String companyCode, String serviceCode, String account, String special1, String special2,
			String transactionDate, String transactionId, String userId, String userPassword, String salePointType) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String executePayment(String companyCode, String serviceCode, String account, String special1,
			String special2, String transactionDate, String transactionId, String refStan, String amount,
			String billNumber, String userId, String userPassword, String salePointType) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String executePaymentEx(String companyCode, String serviceCode, String account, String special1,
			String special2, String transactionDate, String transactionId, String refStan, String amount,
			String billNumber, String userId, String userPassword, String salePointType, String paySourceType,
			String currencyCode) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String addCancelRequest(String companyCode, String refStan, String transactionDate, String transactionId,
			String userId, String userPassword, String salePointType) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String addCancelRequestEx(String companyCode, String refStan, String transactionDate, String transactionId,
			String userId, String userPassword, String salePointType, String note) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getCompanyInfo(String companyCode, String serviceCode, String userId, String userPassword,
			String salePointType) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getTransactionReport(String userLogin, String userPassword, String dealerName, String userName,
			String startDate, String endDate, String refStan, String salePointType, String status) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getTransactionReportSummary(String userLogin, String userPassword, String dealerName, String userName,
			String startDate, String endDate, String refStan, String salePointType, String status) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getTransaction(String userLogin, String userPassword, String stan, String refStan, String key,
			String billNumber) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getTransactionEx(String userLogin, String userPassword, String stan, String refStan, String key,
			String billNumber) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getTransactionByExternal(String userLogin, String userPassword, String stanEx, String date) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String dailyReconciliation(String userLogin, String userPassword, String dealerId, String companyId,
			String day, String totalSum, String totalCount) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String retransmit(String companyCode, String account, String transactionDate, String transactionId,
			String refStan, String userId, String userPassword, String salePointType, String note) {
		// TODO Auto-generated method stub
		return null;
	}

}
