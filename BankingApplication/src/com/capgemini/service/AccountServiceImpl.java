package com.capgemini.service;

import com.capgemini.exception.InsufficientBalanceException;
import com.capgemini.exception.InsufficientInitialBalanceException;
import com.capgemini.exception.InvalidAccountNumberException;
import com.capgemini.model.Account;
import com.capgemini.repository.AccountRepository;

public class AccountServiceImpl implements AccountService {
	
	/* (non-Javadoc)
	 * @see com.capgemini.service.AccountService#createAccount(int, int)
	 */
	
	AccountRepository accountRepository;
	
	public AccountServiceImpl(AccountRepository accountRepository)
	{
		this.accountRepository=accountRepository;
	}
	@Override
	public Account createAccount(int accountNumber,int amount)throws InsufficientInitialBalanceException
	{
		if(amount<500)
		{
			throw new InsufficientInitialBalanceException();
		}
		
		Account account = new Account();
		account.setAccountNumber(accountNumber);
		account.setAmount(amount);
		
		if(accountRepository.save(account))
		{
			return account;
		}
		
		return null;
		
	}
	@Override
	public int withDrawAmount(int accountNumber, int amount) throws InsufficientBalanceException, InvalidAccountNumberException {
	Account account=accountRepository.searchAccount(accountNumber);
	
	if(account == null){
		throw new InvalidAccountNumberException();
	}
	if((account.getAmount()-amount)>=0)
	{
		account.setAmount(account.getAmount()-amount);
		return account.getAmount();
	}
	
	throw new InsufficientBalanceException();
	}
	@Override
	public int showBalance(int accountNumber) throws InvalidAccountNumberException {
		Account account=accountRepository.searchAccount(accountNumber);
		if(account == null)
		{
			throw new InvalidAccountNumberException();
		}
		return account.getAmount();
		
	}
	
	
}
