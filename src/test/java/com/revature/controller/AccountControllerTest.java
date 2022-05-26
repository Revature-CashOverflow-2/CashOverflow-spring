package com.revature.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import com.revature.dao.BankAccountRepo;
import com.revature.dto.TransactionDto;
import com.revature.model.Transaction;
import com.revature.service.TransactionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;

import com.revature.dto.BankAccountDto;
import com.revature.dto.BetweenUsersDto;
import com.revature.model.BankAccount;
import com.revature.model.BetweenUsers;
import com.revature.model.UserAccount;
import com.revature.service.BankAccountService;
import com.revature.service.UserAccountService;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class AccountControllerTest {

	@Mock
	private BankAccountService bankServ;

	@Mock
	private UserAccountService accServ;

	@Mock
	private Authentication auth;

	@Mock
	private ModelMapper mapper;

	private AccountController cont;

	@BeforeEach
	void setUp() throws Exception {
		cont = new AccountController(bankServ, mapper, accServ);
	}

	@Test
	void createBankAccountTest() {
		BankAccount initialAccount = new BankAccount();
		initialAccount.setName("Awoo");
		initialAccount.setDescription("backend test account");
		initialAccount.setAccountTypeId(1);
		BankAccountDto dtoAccount = new BankAccountDto();
		dtoAccount.setName("Awoo");
		dtoAccount.setDescription("backend test account");
		dtoAccount.setAccountTypeId(1);
		BankAccountDto expectedAccount = new BankAccountDto();
		expectedAccount.setName("Awoo");
		expectedAccount.setDescription("backend test account");
		expectedAccount.setAccountTypeId(1);
		UserAccount mockUser = new UserAccount("Awoo", "hasdf");

		when(mapper.map(dtoAccount, BankAccount.class)).thenReturn(initialAccount);
		when(auth.getName()).thenReturn("^_^");
		when(accServ.getUserFromUsername("^_^")).thenReturn(mockUser);
		when(bankServ.createAccount(initialAccount)).thenReturn(initialAccount);
		when(mapper.map(initialAccount, BankAccountDto.class)).thenReturn(dtoAccount);

		BankAccountDto actualUser = cont.createBankAccount(auth, dtoAccount);

		verify(mapper, times(1)).map(dtoAccount, BankAccount.class);
		verify(auth, times(1)).getName();
		verify(accServ, times(1)).getUserFromUsername("^_^");
		verify(bankServ, times(1)).createAccount(initialAccount);
		verify(mapper, times(1)).map(initialAccount, BankAccountDto.class);

		assertEquals(actualUser, expectedAccount);
	}
	@Test
	void removeRequestTest() {
		BankAccount initialAccount = new BankAccount();
		initialAccount.setName("Awoo");
		initialAccount.setDescription("backend test account");
		initialAccount.setAccountTypeId(1);
		BankAccountDto dtoAccount = new BankAccountDto();
		dtoAccount.setName("Awoo");
		dtoAccount.setDescription("backend test account");
		dtoAccount.setAccountTypeId(1);
		BankAccountDto expectedAccount = new BankAccountDto();
		expectedAccount.setName("Awoo");
		expectedAccount.setDescription("backend test account");
		expectedAccount.setAccountTypeId(1);
		UserAccount mockUser = new UserAccount("Awoo", "hasdf");

		BetweenUsersDto betweenTest = new BetweenUsersDto();
		
		Authentication auth = Mockito.mock(Authentication.class);
		
		BankAccountService bankAccServ = Mockito.mock(BankAccountService.class);
		
		ModelMapper mapper = Mockito.mock(ModelMapper.class);
		
		UserAccountService userAccServ = Mockito.mock(UserAccountService.class);
		
		AccountController accControl = new AccountController(bankAccServ, mapper, userAccServ);
		
		accControl.removeRequests(auth, betweenTest);
		
		List<BetweenUsers> checker = accControl.retrieveRequests(auth);
		
		boolean result = false;
		
		for(int i = 0; i < checker.size(); i++)
		{
			if (checker.get(i) == accControl.convertToBetweenUsers(betweenTest))
			{
				result = true;
			}
		}
		
		assertFalse(result);
	}

	@Test
	void addTransaction() {
		TransactionDto transDto = Mockito.mock(TransactionDto.class);
		TransactionService tranServ = Mockito.mock(TransactionService.class);
		IncomeExpenseController inExpCont = new IncomeExpenseController(tranServ);

		when(transDto.getAccountId()).thenReturn(1);
		when(transDto.getAmount()).thenReturn(1.0);
		when(transDto.getDescription()).thenReturn("test");

		inExpCont.addTransaction(transDto);
	}

	@Test
	void getTransactions() {
		TransactionService tranServ = Mockito.mock(TransactionService.class);
		IncomeExpenseController inExpCont = new IncomeExpenseController(tranServ);
		List<Transaction> transaction = new ArrayList();
		int id = 1;

		List<Transaction> transactions = inExpCont.getTransactions(id);
		assertEquals(transactions, transaction);

	}
}
