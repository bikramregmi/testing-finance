package com.mobilebanking.api.impl;
/*package com.wallet.api.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wallet.api.IBankAccountApi;
import com.wallet.entity.Bank;
import com.wallet.entity.BankAccount;
import com.wallet.entity.Branch;
import com.wallet.entity.User;
import com.wallet.model.BankAccountDTO;
import com.wallet.repositories.BankAccountRepository;
import com.wallet.repositories.BankRepository;
import com.wallet.repositories.BranchRepository;
import com.wallet.repositories.UserRepository;
import com.wallet.util.ConvertUtil;

@Service
public class BankAccountApi implements IBankAccountApi{
	
	private Logger logger=LoggerFactory.getLogger(BankAccountApi.class );
	
	@Autowired
	private BankAccountRepository bankAccountRepository;
	@Autowired
	private BankRepository bankRepository;
	@Autowired
	private BranchRepository branchRepository;
	@Autowired
	private UserRepository userRepository;
	public BankAccountApi(BankAccountRepository bankAccountRepository,
			BankRepository bankRepository,BranchRepository branchRepository,UserRepository userRepository){
		this.bankAccountRepository=bankAccountRepository;
		this.bankRepository=bankRepository;
		this.branchRepository=branchRepository;
		this.userRepository=userRepository;
		}
	@Override
	public void saveBankAccount(BankAccountDTO bankAccountDTO){
		Bank bank=bankRepository.findByBank(bankAccountDTO.getBank());
		Branch branch=branchRepository.findByBranch(bankAccountDTO.getBranch());
		User user=userRepository.findByUsername(bankAccountDTO.getUser());
		BankAccount bankAccount=new BankAccount();
		//bankAccount.setBank(bank);
		bankAccount.setBranch(branch);
		bankAccount.setAccountNumber(bankAccountDTO.getAccountNumber());
		bankAccount.setUser(user);
		bankAccountRepository.save(bankAccount);
		logger.debug("BankAccount Saved");
		
	}
	
	@Override
	public List<BankAccountDTO> getAllBankAccount() {
		List<BankAccount> bankAccountList=ConvertUtil.convertIterableToList(bankAccountRepository.findAll());
		return ConvertUtil.ConvertBankAccountDTOtoBankAccount(bankAccountList);
	}
	@Override
	public BankAccount getBankAccountById(long bankAccountId) {
		// TODO Auto-generated method stub
		return bankAccountRepository.findOne(bankAccountId);
	}

	@Override
	public void editBankAccount(BankAccountDTO bankAccountDTO) {
		BankAccount bankAccount=getBankAccountById(bankAccountDTO.getId());
		Bank bank=bankRepository.findByBank(bankAccountDTO.getBank());
		Branch branch=branchRepository.findByBranch(bankAccountDTO.getBranch());
		User user=userRepository.findByUsername(bankAccountDTO.getUser());
		//bankAccount.setBank(bank);
		bankAccount.setBranch(branch);
		bankAccount.setAccountNumber(bankAccountDTO.getAccountNumber());
		bankAccount.setUser(user);
		bankAccountRepository.save(bankAccount);
		// TODO Auto-generated method stub
	}
}
*/