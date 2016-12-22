package com.capgemini.service;

import com.capgemini.exception.InsufficientBalanceException;
import com.capgemini.exception.InsufficientInitialBalanceException;
import com.capgemini.exception.InvalidAccountNumberException;
import com.capgemini.model.Account;

public interface AccountService {

	Account createAccount(int accountNumber, int amount) throws InsufficientInitialBalanceException;
   int showBalance(int accountNumber) throws InvalidAccountNumberException;
   int withDrawAmount(int accountNumber, int amount) throws InsufficientBalanceException,InvalidAccountNumberException;
}