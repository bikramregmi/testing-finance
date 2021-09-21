/**
 * 
 */
package com.mobilebanking.api.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mobilebanking.api.ICommissionApi;
import com.mobilebanking.converter.BankCommissionConverter;
import com.mobilebanking.converter.CommissionAmountConverter;
import com.mobilebanking.converter.CommissionConverter;
import com.mobilebanking.entity.BankCommission;
import com.mobilebanking.entity.Commission;
import com.mobilebanking.entity.CommissionAmount;
import com.mobilebanking.entity.Merchant;
import com.mobilebanking.entity.MerchantService;
import com.mobilebanking.model.BankCommissionDTO;
import com.mobilebanking.model.CommissionAmountDTO;
import com.mobilebanking.model.CommissionDTO;
import com.mobilebanking.model.CommissionType;
import com.mobilebanking.model.Status;
import com.mobilebanking.repositories.BankCommissionRepository;
import com.mobilebanking.repositories.BankRepository;
import com.mobilebanking.repositories.CommissionAmountRepository;
import com.mobilebanking.repositories.CommissionRepository;
import com.mobilebanking.repositories.MerchantRepository;
import com.mobilebanking.repositories.MerchantServiceRepository;

/**
 * @author bibek
 *
 */
@Service
public class CommissionApi implements ICommissionApi {

	@Autowired
	private CommissionRepository commissionRepository;

	@Autowired
	private BankRepository bankRepository;

	@Autowired
	private MerchantRepository merchantRepository;

	@Autowired
	private MerchantServiceRepository serviceRepository;

	@Autowired
	private CommissionAmountRepository commissionAmountRepository;
	@Autowired
	private CommissionConverter commissionConverter;
	@Autowired
	private CommissionAmountConverter commissionAmountConverter;
	@Autowired
	private BankCommissionRepository bankCommissionRepository;
	
	@Autowired
	private BankCommissionConverter bankCommissionConverter;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.mobilebanking.api.ICommissionApi#editCommission(com.mobilebanking.
	 * model.CommissionDTO)
	 */
	@Override
	public CommissionDTO editCommission(CommissionDTO commissionDto, List<CommissionAmountDTO> commissionAmounts)
			throws Exception {
		Commission commission = commissionRepository.findOne(commissionDto.getId());
		commission.setSameForAll(commissionDto.isSameForAll());
		commission = commissionRepository.save(commission);
		inActiveAllCommissionAmount(commission.getId());
		createCommissionAmount(commission, commissionAmounts);
		return null;
	}

	private void inActiveAllCommissionAmount(long commissionId) {
		List<CommissionAmount> commissionAmountList = commissionAmountRepository
				.findCommissionAmountsOfCommission(commissionId, Status.Active);
		for (CommissionAmount commissionAmount : commissionAmountList) {
			commissionAmount.setStatus(Status.Inactive);
			commissionAmountRepository.save(commissionAmount);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.mobilebanking.api.ICommissionApi#getCommissionById(long)
	 */
	@Override
	public CommissionDTO getCommissionById(long id) {
		Commission commission = commissionRepository.findCommissionById(id);
		if (commission != null) {
			return commissionConverter.convertToDto(commission);
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.mobilebanking.api.ICommissionApi#getCommissionByStatus(com.
	 * mobilebanking.model.Status)
	 */
	@Override
	public List<CommissionDTO> getCommissionByStatus(Status status) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.mobilebanking.api.ICommissionApi#getAllCommission()
	 */
	@Override
	public List<CommissionDTO> getAllCommission() {
		List<Commission> commissionList = commissionRepository.findAllCommission(Status.Active);
		if (!commissionList.isEmpty()) {
			return commissionConverter.convertToDtoList(commissionList);
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.mobilebanking.api.ICommissionApi#getCommissionByBank(long)
	 */
	@Override
	public List<CommissionDTO> getCommissionByBank(long bankId) {
		List<Commission> commissionList = commissionRepository.findCommissionByBank(bankId);
		if (!commissionList.isEmpty()) {
			return commissionConverter.convertToDtoList(commissionList);
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.mobilebanking.api.ICommissionApi#getCommissionByBankAndStatus(long,
	 * com.mobilebanking.model.Status)
	 */
	@Override
	public List<CommissionDTO> getCommissionByBankAndStatus(long bankId, Status status) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.mobilebanking.api.ICommissionApi#updateCommission(long)
	 */
	@Override
	public CommissionDTO updateCommission(long commissionId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CommissionDTO saveCommission(CommissionDTO commissionDto, List<CommissionAmountDTO> commissionAmountDto)
			throws Exception {
		Commission commission = new Commission();
		Merchant merchant = merchantRepository.findOne(Long.parseLong(commissionDto.getMerchant()));
		MerchantService merchantService = serviceRepository.findOne(Long.parseLong(commissionDto.getService()));
		commission.setMerchant(merchant);
		commission.setService(merchantService);
		commission.setSameForAll(commissionDto.isSameForAll());
		commission.setTransactionType(commissionDto.getTransactionType());
		commission.setStatus(Status.Active);
		commission.setCommissionType(commissionDto.getCommissionType());
		commission = commissionRepository.save(commission);
		createCommissionAmount(commission, commissionAmountDto);
		// create commissino converter and convert those data and send to
		// controller (if necessary then)
		return null;
	}

	public void createCommissionAmount(Commission commission, List<CommissionAmountDTO> commissionAmounts)
			throws Exception {
		for (int i = 0; i < commissionAmounts.size(); i++) {
			CommissionAmount commissionAmount = new CommissionAmount();
			commissionAmount.setCommission(commission);
			if (commission.getCommissionType() == CommissionType.COMMISSION) {
				commissionAmount.setCommissionFlat(commissionAmounts.get(i).getFlat());
				commissionAmount.setCommissionPercentage(commissionAmounts.get(i).getPercentage());
			} else if (commission.getCommissionType() == CommissionType.FEE) {
				commissionAmount.setFeeFlat(commissionAmounts.get(i).getFlat());
				commissionAmount.setFeePercentage(commissionAmounts.get(i).getPercentage());
			}
			commissionAmount.setStatus(Status.Active);
			if (commissionAmounts.get(i).getFromAmount() != null) {
				commissionAmount.setFromAmount(commissionAmounts.get(i).getFromAmount());
			}
			if (commissionAmounts.get(i).getToAmount() != null) {
				commissionAmount.setToAmount(commissionAmounts.get(i).getToAmount());
			}

			commissionAmountRepository.save(commissionAmount);
		}
	}

	@Override
	public List<CommissionAmountDTO> getCommissionAmountByCommission(long commissionId) {
		List<CommissionAmount> commissionAmounts = commissionAmountRepository
				.findCommissionAmountsOfCommission(commissionId, Status.Active);
		if (!commissionAmounts.isEmpty()) {
			return commissionAmountConverter.convertToDtoList(commissionAmounts);
		}
		return null;
	}

	@Override
	public void saveCommissionAmount(CommissionAmountDTO commissionAmounts) {
		CommissionAmount commissionAmount = new CommissionAmount();
		Commission commission = commissionRepository.findCommissionById(commissionAmounts.getCommissionId());
		commissionAmount.setCommission(commission);
		commissionAmount.setCommissionFlat(commissionAmounts.getCommissionFlat());
		commissionAmount.setCommissionPercentage(commissionAmounts.getCommissionPercentage());
		commissionAmount.setFeeFlat(commissionAmounts.getFeeFlat());
		commissionAmount.setFeePercentage(commissionAmounts.getFeePercentage());
		commissionAmount.setStatus(Status.Active);
		commissionAmountRepository.save(commissionAmount);
	}

	@Override
	public void saveBankCommission(BankCommissionDTO commissionAmount,Long bankId) {
		BankCommission bankCommission = bankCommissionRepository.findByCommissionAmountAndStatus(commissionAmount.getCommissionAmountId(), Status.Active,bankId);
		if(bankCommission!=null){
			bankCommission.setStatus(Status.Inactive);
			bankCommissionRepository.save(bankCommission);
		}
		bankCommission = new BankCommission();
		bankCommission.setBank(bankRepository.findOne(commissionAmount.getBankId()));
		bankCommission.setCommissionAmount(commissionAmountRepository.findOne(commissionAmount.getCommissionAmountId()));
		bankCommission.setCommissionFlat(commissionAmount.getCommissionFlat());
		bankCommission.setCommissionPercentage(commissionAmount.getCommissionPercentage());
		bankCommission.setFeeFlat(commissionAmount.getFeeFlat());
		bankCommission.setFeePercentage(commissionAmount.getFeePercentage());
		bankCommission.setOperatorCommissionFlat(commissionAmount.getOperatorCommissionFlat());
		bankCommission.setOperatorCommissionPercentage(commissionAmount.getOperatorCommissionPercentage());
		bankCommission.setChannelPartnerCommissionFlat(commissionAmount.getChannelPartnerCommissionFlat());
		bankCommission.setChannelPartnerCommissionPercentage(commissionAmount.getChannelPartnerCommissionPercentage());
		bankCommission.setCashBack(commissionAmount.getCashBack());
		bankCommission.setStatus(Status.Active);
		bankCommissionRepository.save(bankCommission);
	}

	@Override
	public BankCommissionDTO getBankCommissionByCommissionAmountIdAndBank(long id,long bankId) {
		BankCommission bankCommission = bankCommissionRepository.findByCommissionAmountAndStatus(id,Status.Active,bankId);
		if(bankCommission!=null){
			return bankCommissionConverter.convertToDto(bankCommission);
		}
		return null;
	}

}
