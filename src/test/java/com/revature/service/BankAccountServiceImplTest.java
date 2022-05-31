package com.revature.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.server.ResponseStatusException;

import com.revature.dao.BankAccountRepo;
import com.revature.dao.RequestRepo;
import com.revature.dao.TransactionRepo;
import com.revature.dto.EmailData;
import com.revature.model.BankAccount;
import com.revature.model.BetweenUsers;
import com.revature.model.FundTransfer;
import com.revature.model.UserAccount;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class BankAccountServiceImplTest {

	@Mock
	private BankAccountRepo dao;

	@Mock
	private TransactionRepo txDao;

	private BankAccountService serv;

	@Mock
	private UserAccountService userAccServ;

	@Mock
	private RequestRepo reqRepo;

	@Mock
	private EmailData emaildata;

	@Mock
	private EmailService	emailServ;

	@BeforeEach
	void setUp() throws Exception {
		serv = new BankAccountServiceImpl(dao, txDao, userAccServ, reqRepo);
	}

	/**
	 * This method tests the BankAccountService method createAccount. It should
	 * return a BankAccount object with balance set to zero.
	 */
	@Test
	void createAccountTest() {
		UserAccount initialTestUser = new UserAccount(1000, "testuseremail@emailprovider.com", "testUserUsername",
				"testUserFirstName", "testUserLastName", "testUserPassword", null);

		BankAccount initialTestBankAccount = new BankAccount("myBankAccountName", "myBankAccountDescription", null, 1,
				initialTestUser);
		initialTestBankAccount.setBalance(0.0);
		UserAccount expectedTestUser = new UserAccount(1000, "testuseremail@emailprovider.com", "testUserUsername",
				"testUserFirstName", "testUserLastName", "testUserPassword", null);

		BankAccount expectedTestBankAccount = new BankAccount("myBankAccountName", "myBankAccountDescription", null, 1,
				expectedTestUser);
		expectedTestBankAccount.setBalance(0.0);
		// sending BankAccount object with balance US$2,000,000.00
		when(dao.save(initialTestBankAccount)).thenReturn(initialTestBankAccount);
		BankAccount test = serv.createAccount(initialTestBankAccount);
		verify(dao, times(1)).save(initialTestBankAccount);
		// verifying if we got a saved to the database an account with balance US$0.00
		assertEquals(test.getBalance(), expectedTestBankAccount.getBalance());
	}

	/**
	 * This method tests the BankAccountService method getBankAccounts. This method
	 * should be returning a list of BankAccounts
	 */
	@Test
	void selectAllBankAccountsByUser() {

		UserAccount initialTestUser = new UserAccount(1, "testuseremail@emailprovider.com", "testUserUsername",
				"testUserFirstName", "testUserLastName", "testUserPassword", Instant.now());

		BankAccount initialTestBankAccount = new BankAccount("myBankAccountName", "myBankAccountDescription",
				Instant.now(), 1, initialTestUser);

		List<BankAccount> initialList = new ArrayList<>();
		initialList.add(initialTestBankAccount);
		List<BankAccount> expectedList = new ArrayList<>(initialList);
		when(dao.findAllByUserId(1)).thenReturn(initialList);

		List<BankAccount> test = serv.getBankAccounts(1);

		verify(dao, times(1)).findAllByUserId(initialTestBankAccount.getUser().getId());
		assertEquals(expectedList, test);
	}

	/**
	 * This tests that transactions with fractional cents are being handled properly
	 * as well as transactions that exceed the account's balance
	 */
	@Test
	void transferFundsTest() {
		BankAccount initialAccount1 = new BankAccount();
		initialAccount1.setBalance(100.00);
		BankAccount initialAccount2 = new BankAccount();
		initialAccount2.setBalance(0.0);
		FundTransfer fundTransfer = new FundTransfer();
		fundTransfer.setTransferFromAccount("account1");
		fundTransfer.setTransferToAccount("account2");
		fundTransfer.setTransferAmount(12.3456);
		BankAccount expectedAccount1 = new BankAccount();
		expectedAccount1.setBalance(87.65);
		BankAccount expectedAccount2 = new BankAccount();
		expectedAccount2.setBalance(12.35);
		UserAccount user = new UserAccount();
		List<BankAccount> expectedAccounts = new ArrayList<>();
		expectedAccounts.add(expectedAccount1);
		expectedAccounts.add(expectedAccount2);

		when(dao.saveAll(expectedAccounts)).thenReturn(null);
		when(serv.getBankAccount(user, "account1")).thenReturn(initialAccount1);
		when(serv.getBankAccount(user, "account2")).thenReturn(initialAccount2);

		List<BankAccount> test = serv.transferFunds(user, fundTransfer);

		verify(dao, times(1)).saveAll(expectedAccounts);
		assertEquals(expectedAccounts, test);

		fundTransfer.setTransferAmount(101.101);
		assertThrows(ResponseStatusException.class, () -> serv.transferFunds(user, fundTransfer));
	}

	/**
	 * This method tests the BankAccountService method betweenUsers. This method
	 * should be throwing an Exception due to invalid User information
	 */
	@Test
	void betweenUsersTest() {
		BetweenUsers betweenUsers = new BetweenUsers();
		UserAccount otherUser = new UserAccount();
		BankAccount originBank = new BankAccount();

		when(userAccServ.getUserFromUsername(betweenUsers.getUser())).thenReturn(otherUser);
		when(dao.getById(betweenUsers.getTransferAccount())).thenReturn(originBank);
		
		assertThrows(ResponseStatusException.class, () -> serv.betweenUsers(null, betweenUsers));
		
		emailServ.send(emaildata);
		verify(emailServ, times(1)).send(emaildata);
		
	}

	
	@Test
	void betweenUsersTest2() {
		UserAccount otherUser = new UserAccount();
		otherUser.setId(1000);
		BankAccount originBank = new BankAccount();
		originBank.setBalance(1.00);
		BetweenUsers between = new BetweenUsers();
		between.setSendOrReceive(1);
		between.setOriginUser("account1");
		between.setUser("account2");
		between.setTransferAccount(1);
		between.setTransferAmount(2.00);

		when(userAccServ.getUserFromUsername(between.getUser())).thenReturn(otherUser);
		when(dao.getById(between.getTransferAccount())).thenReturn(originBank);
		
		assertThrows(ResponseStatusException.class, () -> serv.betweenUsers(null, between));
		
		emailServ.send(emaildata);
		verify(emailServ, times(1)).send(emaildata);
		
	}
	
	@Test
	void betweenUsersTest3() {
		UserAccount otherUser = new UserAccount();
		otherUser.setId(1000);
		UserAccount user = new UserAccount();
		user.setEmail("testemail@email.com");
		user.setUsername("test");
		BankAccount originBank = new BankAccount();
		originBank.setBalance(1.00);
		BetweenUsers between = new BetweenUsers();
		between.setSendOrReceive(1);
		between.setOriginUser("account1");
		between.setUser("account2");
		between.setTransferAccount(1);
		between.setTransferAmount(0.01);

		when(userAccServ.getUserFromUsername(between.getUser())).thenReturn(otherUser);
		when(dao.getById(between.getTransferAccount())).thenReturn(originBank);
		
		assertThrows(NullPointerException.class, () -> serv.betweenUsers(user, between));
		
		emailServ.send(emaildata);
		verify(emailServ, times(1)).send(emaildata);
		
	}
	
	@Test
	void betweenUsersTest4() {
		UserAccount otherUser = new UserAccount();
		otherUser.setId(1000);
		UserAccount user = new UserAccount();
		user.setEmail("testemail@email.com");
		user.setUsername("test");
		BankAccount originBank = new BankAccount();
		originBank.setBalance(1.00);
		BetweenUsers between = new BetweenUsers();
		between.setSendOrReceive(2);
		between.setOriginUser("account1");
		between.setUser("account2");
		between.setTransferAccount(1);
		between.setTransferAmount(0.01);

		when(userAccServ.getUserFromUsername(between.getUser())).thenReturn(otherUser);
		when(dao.getById(between.getTransferAccount())).thenReturn(originBank);
		
		assertThrows(NullPointerException.class, () -> serv.betweenUsers(user, between));
		
		emailServ.send(emaildata);
		verify(emailServ, times(1)).send(emaildata);
		
	}
	
	@Test
	void getBetweenUsersTest() {
		List<BetweenUsers> betweenUsers = new ArrayList();
		UserAccount otherUser = new UserAccount();

		List<BetweenUsers> between = new ArrayList();
		
		
		
		when(reqRepo.findAllByUser(otherUser.getUsername())).thenReturn(between);
		
		
		assertEquals(between, betweenUsers);
	

		otherUser.setUsername("test");
		
		when(reqRepo.findAllByUser(otherUser.getUsername())).thenReturn(betweenUsers);
		
		
		between = serv.getBetweenUsers(otherUser);
		
		assertEquals(between, betweenUsers);
		
	}


	/**
	 * This method tests the BankAccountService method completeTransfer. This test
	 * should verify money was sent to another user
	 */
	@Test
	void completeSendTransfer() {

		BankAccount initialAccount1 = new BankAccount();
		initialAccount1.setBalance(50.00);

		BankAccount initialAccount2 = new BankAccount();
		initialAccount2.setBalance(0.0);

		BetweenUsers between = new BetweenUsers();
		between.setSendOrReceive(1);
		between.setOriginUser("account1");
		between.setUser("account2");
		between.setTransferAccount(1);
		between.setTransferAmount(10.00);

		BankAccount expectedAccount1 = new BankAccount();
		expectedAccount1.setBalance(40.00);

		BankAccount expectedAccount2 = new BankAccount();
		expectedAccount2.setBalance(10.00);

		UserAccount user = new UserAccount();

		List<BankAccount> expectedAccounts = new ArrayList<>();
		expectedAccounts.add(expectedAccount1);
		expectedAccounts.add(expectedAccount2);

		when(dao.saveAll(expectedAccounts)).thenReturn(null);
		when(dao.getById(between.getTransferAccount())).thenReturn(initialAccount1);
		when(dao.getById(between.getReceiveAccount())).thenReturn(initialAccount2);

		List<BankAccount> test = serv.completeTransfer(between);

		verify(dao, times(1)).saveAll(expectedAccounts);
		assertEquals(expectedAccounts, test);

		between.setTransferAmount(80.00);
		assertThrows(ResponseStatusException.class, () -> serv.betweenUsers(user, between));
	}

	/**
	 * This method tests the BankAccountService method completeTransfer. This test
	 * should verify money was received from another user
	 */
	@Test
	void completeRequestTransfer() {
		BankAccount initialAccount1 = new BankAccount();
		initialAccount1.setBalance(0.00);

		BankAccount initialAccount2 = new BankAccount();
		initialAccount2.setBalance(50.0);

		BetweenUsers between = new BetweenUsers();
		between.setSendOrReceive(2);
		between.setOriginUser("account1");
		between.setUser("account2");
		between.setTransferAccount(1);
		between.setTransferAmount(10.00);

		BankAccount expectedAccount1 = new BankAccount();
		expectedAccount1.setBalance(10.00);

		BankAccount expectedAccount2 = new BankAccount();
		expectedAccount2.setBalance(40.00);

		UserAccount user = new UserAccount();
		List<BankAccount> expectedAccounts = new ArrayList<>();
		expectedAccounts.add(expectedAccount1);
		expectedAccounts.add(expectedAccount2);

		when(dao.saveAll(expectedAccounts)).thenReturn(null);
		when(dao.getById(between.getTransferAccount())).thenReturn(initialAccount1);
		when(dao.getById(between.getReceiveAccount())).thenReturn(initialAccount2);

		List<BankAccount> test = serv.completeTransfer(between);

		verify(dao, times(1)).saveAll(expectedAccounts);
		assertEquals(expectedAccounts, test);

		between.setTransferAmount(80.00);
		assertThrows(ResponseStatusException.class, () -> serv.betweenUsers(user, between));
	}

	/**
	 * This method tests the BankAccountService method completeTransfer.
	 */
	@Test
	void completeTransferAccountTestSuccess() {
		UserAccount newOwner = new UserAccount();
		newOwner.setId(1);
		newOwner.setUsername("newOwner");
		UserAccount socialOwner = new UserAccount();
		socialOwner.setId(2);
		socialOwner.setUsername("SocialOwner");

		BankAccount initialTestBankAccount = new BankAccount("myBankAccountName", "myBankAccountDescription", null, 1,
				socialOwner);
		initialTestBankAccount.setBalance(0.0);

		BankAccount expectedTestBankAccount = new BankAccount("myBankAccountName", "myBankAccountDescription", null, 1,
				newOwner);
		initialTestBankAccount.setBalance(0.0);

		List<BankAccount> initialList = new ArrayList<>();
		initialList.add(initialTestBankAccount);
		List<BankAccount> expectedList = new ArrayList<>();
		expectedList.add(expectedTestBankAccount);

		when(dao.findAllByUserId(2)).thenReturn(initialList);
		when(dao.findAllByUserId(1)).thenReturn(expectedList);

		serv.transferAccount(newOwner, socialOwner);

		List<BankAccount> test = serv.getBankAccounts(1);

		verify(dao, times(2)).findAllByUserId(initialTestBankAccount.getUser().getId());
		assertEquals(expectedList, test);

	}

	/**
	 * This method tests the BankAccountService method completeTransfer.
	 */
	@Test
	void completeTransferAccountTestNullUserUsernameFailure() {
		UserAccount newOwner = new UserAccount();
		UserAccount socialOwner = new UserAccount();
		socialOwner.setId(2);
		socialOwner.setUsername("SocialOwner");

		BankAccount initialTestBankAccount = new BankAccount("myBankAccountName", "myBankAccountDescription", null, 1,
				socialOwner);
		initialTestBankAccount.setBalance(0.0);
		assertThrows(ResponseStatusException.class, () -> serv.transferAccount(newOwner, socialOwner));
	}

	/**
	 * This method tests the BankAccountService method completeTransfer.
	 */
	@Test
	void completeTransferAccountTestNullUserFailure() {
		UserAccount newOwner = null;
		UserAccount socialOwner = new UserAccount();
		socialOwner.setId(2);
		socialOwner.setUsername("SocialOwner");

		BankAccount initialTestBankAccount = new BankAccount("myBankAccountName", "myBankAccountDescription", null, 1,
				socialOwner);
		initialTestBankAccount.setBalance(0.0);
		assertThrows(ResponseStatusException.class, () -> serv.transferAccount(newOwner, socialOwner));
	}

	/**
	 * This method tests the BankAccountService method completeTransfer.
	 */
	@Test
	void completeTransferAccountTestNullSocialUsernameFailure() {
		UserAccount newOwner = new UserAccount();
		newOwner.setId(1);
		newOwner.setUsername("NewOwner");
		UserAccount socialOwner = new UserAccount();

		BankAccount initialTestBankAccount = new BankAccount("myBankAccountName", "myBankAccountDescription", null, 1,
				socialOwner);
		initialTestBankAccount.setBalance(0.0);
		assertThrows(ResponseStatusException.class, () -> serv.transferAccount(newOwner, socialOwner));
	}

	/**
	 * This method tests the BankAccountService method completeTransfer.
	 */
	@Test
	void completeTransferAccountTestNullSocialFailure() {
		UserAccount newOwner = new UserAccount();
		newOwner.setId(1);
		newOwner.setUsername("NewOwner");
		UserAccount socialOwner = null;

		BankAccount initialTestBankAccount = new BankAccount("myBankAccountName", "myBankAccountDescription", null, 1,
				socialOwner);
		initialTestBankAccount.setBalance(0.0);
		assertThrows(ResponseStatusException.class, () -> serv.transferAccount(newOwner, socialOwner));
	}

	// Mfaydali
	/**
	 * This method tests the BankAccountService method getBankAccountTest.
	 */
	@Test
	public void getBankAccountTest() {
		UserAccount testUser = new UserAccount(1, "testuseremail@emailprovider.com", "testUserUsername",
				"testUserFirstName", "testUserLastName", "testUserPassword", Instant.now());

		BankAccount testBankAccount = new BankAccount("myBankAccountName", "myBankAccountDescription", Instant.now(), 1,
				testUser);

		when(dao.findByUserAndName(testUser, "Mehmet")).thenReturn(testBankAccount);

		BankAccount test = serv.getBankAccount(testUser, "Mehmet");

		verify(dao, times(1)).findByUserAndName(testUser, "Mehmet");
		assertEquals(testBankAccount, test);

	}

	/**
	 * This method tests the BankAccountService method RemoveRequest.
	 */
	@Test
	void RemoveRequest() {
		BetweenUsers mockBetweenUsers = new BetweenUsers();
		mockBetweenUsers.setId(1);
		
		doNothing().when(reqRepo).deleteById(mockBetweenUsers.getId());
		serv.removeRequest(mockBetweenUsers); 
		verify(reqRepo, times(1)).deleteById(mockBetweenUsers.getId());
	}


}
