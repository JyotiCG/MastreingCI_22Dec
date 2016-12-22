package com.capgemini.test;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.capgemini.exception.InsufficientBalanceException;
import com.capgemini.exception.InsufficientInitialBalanceException;
import com.capgemini.exception.InvalidAccountNumberException;
import com.capgemini.model.Account;
import com.capgemini.repository.AccountRepository;
import com.capgemini.service.AccountService;
import com.capgemini.service.AccountServiceImpl;
public class AccountTest {

	@Mock
	AccountRepository accountRepository;
	
	AccountService accountService;
	
	
	@Before
	public void setUp() throws Exception {
		
		MockitoAnnotations.initMocks(this);
		accountService = new AccountServiceImpl(accountRepository);
	}

	/*
	 * create account
	 * 1. when the amount is less than 500 system should throw exception
	 * 2. when the valid info is passed account should be created successfully
	 */
	
	@Test(expected=com.capgemini.exception.InsufficientInitialBalanceException.class)
	public void whenTheAmountIsLessThan500SystemShouldThrowException() throws InsufficientInitialBalanceException
	{
		accountService.createAccount(101, 400);
	}
	
	@Test
	public void whenTheValidInfoIsPassedAccountShouldBeCreatedSuccessfully() throws InsufficientInitialBalanceException
	{
		Account account = new Account();
		
		account.setAccountNumber(101);
		account.setAmount(5000);
		
		when(accountRepository.save(account)).thenReturn(true);
		
		assertEquals(account, accountService.createAccount(101, 5000));
		
	}
	@Test(expected = com.capgemini.exception.InvalidAccountNumberException.class)
	public void whenInvalidAccountnumberIsPassedTochkBalance() throws InvalidAccountNumberException 
	{
		when(accountRepository.searchAccount(101)).thenReturn(null);
		accountService.showBalance(101);
	}
	
	@Test
	public void whenvalidAccountNumberPassedtoChkBalance() throws InvalidAccountNumberException
	{
		Account account = new Account();
		account.setAccountNumber(102);
		account.setAmount(5000);
		when(accountRepository.searchAccount(102)).thenReturn(account);
		assertEquals(account.getAmount(), accountService.showBalance(102));
	}
	
	@Test(expected = com.capgemini.exception.InsufficientBalanceException.class)
	public void whenInvalidAmountPassedToWithdrawAmount() throws InsufficientBalanceException, InvalidAccountNumberException
	{
		Account account = new Account();
		account.setAccountNumber(101);
		account.setAmount(500);
		when(accountRepository.searchAccount(101)).thenReturn(account);
		accountService.withDrawAmount(101,600);
		
	}
	@Test(expected = com.capgemini.exception.InvalidAccountNumberException.class)
	public void whenInvalidAccountNumberPassedToWithdrawAmount() throws InsufficientBalanceException, InvalidAccountNumberException
	{
		Account account = new Account();
		account.setAccountNumber(102);
		account.setAmount(500);
		when(accountRepository.searchAccount(102)).thenReturn(null);
		accountService.withDrawAmount(102,600);
		
	}
	
	@Test
	public void whenvalidAccountNumberAndValidBalancePrsntPassedtoChkBalance() throws InvalidAccountNumberException, InsufficientBalanceException
	{
		Account account = new Account();
		account.setAccountNumber(102);
		account.setAmount(5000);
		when(accountRepository.searchAccount(102)).thenReturn(account);
		assertEquals(account.getAmount()-2000, accountService.withDrawAmount(102, 2000));
	}
	
}
