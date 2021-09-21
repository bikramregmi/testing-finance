package com.mobilebanking.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.mobilebanking.model.AccountType;

/**
 * @author bibek
 *
 */
@Entity
@Table(name="account")
public class Account extends AbstractEntity<Long> {
	
	private static final long serialVersionUID = 4333154450908384711L;

	@Column(nullable = false, unique=true)
	private String accountNumber;
	
	@Column(nullable = true)
	private Double balance;
	
	@Column(nullable = true)
	private AccountType accountType; 
	
	@OneToOne
	private User user;
	
	@OneToOne(fetch=FetchType.EAGER)
	private Bank bank;
	
	@OneToOne(fetch=FetchType.EAGER)
	private Merchant merchant;
	
	@OneToOne(fetch=FetchType.EAGER)
	private CardlessBank cardlessBank;
	
	public CardlessBank getCardlessBank() {
		return cardlessBank;
	}

	public void setCardlessBank(CardlessBank cardlessBank) {
		this.cardlessBank = cardlessBank;
	}

	@OneToOne(fetch=FetchType.EAGER)
	private MerchantService merchantService;
	
	@Column(nullable=false)
	private String accountHead;

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public Double getBalance() {
		return balance;
	}

	public void setBalance(Double balance) {
		this.balance = balance;
	}

	public AccountType getAccountType() {
		return accountType;
	}

	public void setAccountType(AccountType accountType) {
		this.accountType = accountType;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Bank getBank() {
		return bank;
	}

	public void setBank(Bank bank) {
		this.bank = bank;
	}

	public Merchant getMerchant() {
		return merchant;
	}

	public void setMerchant(Merchant merchant) {
		this.merchant = merchant;
	}

	public String getAccountHead() {
		return accountHead;
	}

	public void setAccountHead(String accountHead) {
		this.accountHead = accountHead;
	}

	public MerchantService getMerchantService() {
		return merchantService;
	}

	public void setMerchantService(MerchantService merchantService) {
		this.merchantService = merchantService;
	}

}
