/**
 * 
 */
package com.mobilebanking.converter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mobilebanking.entity.Bank;
import com.mobilebanking.entity.BankBranch;
import com.mobilebanking.entity.Customer;
import com.mobilebanking.entity.SettlementLog;
import com.mobilebanking.entity.Transaction;
import com.mobilebanking.model.SettlementType;
import com.mobilebanking.model.TransactionDTO;
import com.mobilebanking.model.TransactionResponse;
import com.mobilebanking.model.TransactionStatus;
import com.mobilebanking.model.TransactionType;
import com.mobilebanking.model.UserType;
import com.mobilebanking.repositories.BankBranchRepository;
import com.mobilebanking.repositories.BankRepository;
import com.mobilebanking.repositories.CustomerRepository;
import com.mobilebanking.repositories.SettlementLogRepository;

/**
 * @author bibek
 *
 */
@Component
public class TransactionConverter
		implements IListConverter<Transaction, TransactionDTO>, IConverter<Transaction, TransactionDTO> {

	@Autowired
	private CustomerRepository customerRepository;

	@Autowired
	private BankRepository bankRepository;

	@Autowired
	private BankBranchRepository bankBranchRepository;

	@Autowired
	private SettlementLogRepository settlementLogRepository;

	@Override
	public Transaction convertToEntity(TransactionDTO dto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TransactionDTO convertToDto(Transaction entity) {
		TransactionDTO dto = new TransactionDTO();
		dto.setTransactionIdentifier(entity.getTransactionIdentifier());
		dto.setSourceOwnerName(entity.getOriginatingUser().getUserName());
		dto.setDestination(entity.getDestination());
		dto.setAmount(entity.getAmount());
		dto.setTransactionStatus(entity.getStatus());
		dto.setCreatedDate(entity.getCreated().toString().substring(0, 19));
		dto.setTransactionType(entity.getTransactionType());
		if (entity.getMerchantManager() != null) {
			dto.setService(entity.getMerchantManager().getMerchantsAndServices().getMerchantService().getService());
		}
		if (entity.getTransactionType().equals(TransactionType.Transfer)) {
			dto.setService("Fund Transfer");
		}
		dto.setSourceAccount(entity.getSourceBankAccount());
		dto.setRemarks(entity.getRemarks());
		if (entity.getResponseDetail() != null) {
			dto.setResponseMessage(entity.getResponseDetail().get("Result Message"));
			if (entity.getResponseDetail().get("RefStan") != null) {
				dto.setMerchantRefstan(entity.getResponseDetail().get("RefStan"));
			}
			if (entity.getResponseDetail().get("isoCode") != null) {
				dto.setIsoCode(entity.getResponseDetail().get("isoCode"));
			}
		}
		if (entity.getBank() != null) {
			dto.setBankCode(entity.getBank().getSwiftCode());
		}
		if (entity.getBankBranch() != null) {
			dto.setBranchCode(entity.getBankBranch().getBranchCode());
		}
		if (entity.getStatus() == TransactionStatus.CancelledWithoutRefund) {
			dto.setReversed(entity.getAmount());
		} else {
			dto.setReversed(0);
		}
		if (entity.getStatus() == TransactionStatus.ReversalWithRefund && entity.getPreviousTransactionId() != null) {
			dto.setPreviousTranId(entity.getPreviousTransactionId());
		}
		if (entity.getOriginatingUser().getUserType() == UserType.Customer) {
			Customer customer = customerRepository.findOne(entity.getOriginatingUser().getAssociatedId());
			if (customer.getMiddleName() == null || customer.getMiddleName().trim().equals("")) {
				dto.setOriginatorName(customer.getFirstName() + " " + customer.getLastName());
			} else {
				dto.setOriginatorName(
						customer.getFirstName() + " " + customer.getMiddleName() + " " + customer.getLastName());
			}
			dto.setOriginatorMobileNo(customer.getMobileNumber());
		} else if (entity.getOriginatingUser().getUserType() == UserType.Bank) {
			Bank bank = bankRepository.findOne(entity.getOriginatingUser().getAssociatedId());
			dto.setOriginatorName(bank.getName());
			dto.setOriginatorMobileNo(bank.getMobileNumber());
		} else if (entity.getOriginatingUser().getUserType() == UserType.BankBranch) {
			BankBranch branch = bankBranchRepository.findOne(entity.getOriginatingUser().getAssociatedId());
			dto.setOriginatorName(branch.getName());
			dto.setOriginatorMobileNo(branch.getMobileNumber());
		}
		if (entity.getStatus().equals(TransactionStatus.Complete)) {
			dto.setTotalCommission(String.valueOf(entity.getTotalCommission()));
			if (entity.getBankCommissionAmount() != null) {
				dto.setBankCommission(entity.getBankCommissionAmount().toString());
			} else {
				dto.setBankCommission("0");
			}
			if (entity.getOperatorCommissionAmount() != null) {
				dto.setOperatorCommissionAmount(entity.getOperatorCommissionAmount().toString());
			} else {
				dto.setOperatorCommissionAmount("0");
			}
			if (entity.getChannelPartnerCommissionAmount() != null) {
				dto.setChannelPartnerCommissionAmount(entity.getChannelPartnerCommissionAmount().toString());
			} else {
				dto.setChannelPartnerCommissionAmount("0");
			}
			if (entity.getSettlementStatus() != null) {
				dto.setSettlementStatus(entity.getSettlementStatus());
			}
			if (entity.getAgencyCommission() != null) {
				dto.setAgencyCommission(entity.getAgencyCommission());
			}

		}

		return dto;
	}

	@Override
	public List<TransactionDTO> convertToDtoList(List<Transaction> entityList) {
		if (entityList != null) {
			List<TransactionDTO> dtoList = new ArrayList<>();
			for (Transaction entity : entityList) {
				dtoList.add(convertToDto(entity));
			}
			return dtoList;
		}
		return new ArrayList<TransactionDTO>();
	}

	public Object convertForReport(List<Transaction> transactionList) {
		List<TransactionDTO> dtoList = new ArrayList<>();
		for (Transaction transaction : transactionList) {
			TransactionDTO transactionDto = convertToDto(transaction);
			SettlementLog log = settlementLogRepository.findByTransactionAndSettlementType(transaction.getId(),
					SettlementType.OPERATOR);
			if (log != null)
				transactionDto.setOperatorSettlement(log.getSettlementStatus().toString());
			else
				transactionDto.setOperatorSettlement("FAILURE");
			log = settlementLogRepository.findByTransactionAndSettlementType(transaction.getId(), SettlementType.BANK);
			if (log != null)
				transactionDto.setBankSettlement(log.getSettlementStatus().toString());
			else
				transactionDto.setBankSettlement("FAILURE");
			log = settlementLogRepository.findByTransactionAndSettlementType(transaction.getId(),
					SettlementType.CHANNELPARTNER);
			if (log != null)
				transactionDto.setChannelPartnerSettlement(log.getSettlementStatus().toString());
			else
				transactionDto.setChannelPartnerSettlement("FAILURE");
			dtoList.add(transactionDto);
		}
		return dtoList;
	}

	public List<TransactionResponse> convertForCustomer(List<Transaction> transactionList) {
		List<TransactionResponse> transactionResponseList = new ArrayList<>();
		for (Transaction transaction : transactionList) {
			transactionResponseList.add(convertForCustomer(transaction));
		}
		return transactionResponseList;
	}

	public TransactionResponse convertForCustomer(Transaction transaction) {
		TransactionResponse transactionResponse = new TransactionResponse();
		transactionResponse.setAmount(transaction.getAmount());
		if (transaction.getService() != null)
			transactionResponse.setService(transaction.getService().getService());
		if (transaction.getTransactionType().equals(TransactionType.Transfer)) {
			transactionResponse.setService("Fund Transfer");
		}
		else if (transaction.getMerchantManager() != null)
			transactionResponse.setService(
					transaction.getMerchantManager().getMerchantsAndServices().getMerchantService().getService());
		transactionResponse.setServiceTo(transaction.getDestination());
		transactionResponse.setAccountNumber(transaction.getSourceBankAccount());
		transactionResponse.setTransactionIdentifier(transaction.getTransactionIdentifier());
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		transactionResponse.setDate(dateFormat.format(transaction.getCreated()));
		transactionResponse.setStatus(transaction.getStatus());
		return transactionResponse;
	}

}
