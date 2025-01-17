package com.revature.service;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.revature.dao.BankAccountRepo;
import com.revature.dao.RequestRepo;
import com.revature.dao.TransactionRepo;
import com.revature.dto.EmailData;
import com.revature.model.BankAccount;
import com.revature.model.BetweenUsers;
import com.revature.model.FundTransfer;
import com.revature.model.Transaction;
import com.revature.model.UserAccount;

@Service
public class BankAccountServiceImpl implements BankAccountService {

	private BankAccountRepo bankRepo;
	private TransactionRepo txRepo;
	private UserAccountService userAccServ;
	private RequestRepo reqRepo;

	@Autowired
	private EmailService emailServ;
	
	@Autowired
	protected BankAccountServiceImpl(BankAccountRepo bankRepo, TransactionRepo txRepo, UserAccountService userAccServ, RequestRepo reqRepo) {
		this.bankRepo = bankRepo;
		this.txRepo = txRepo;
		this.reqRepo = reqRepo;
		this.userAccServ = userAccServ;
	}

	/**
	 * @return BankAccount
	 *
	 * @author Parker Mace, Henry Harvil, Andre Long
	 */
	@Override
	public BankAccount createAccount(BankAccount newAccount) {
		// here we will be timestamping the acc creation and setting the balance to 0
		if (newAccount.getCreationDate() == null) {
			newAccount.setCreationDate(Instant.now());
		}
		if (newAccount.getBalance() == null) {
			newAccount.setBalance(0.0);
		}

		BankAccount check = bankRepo.findByUserAndName(newAccount.getUser(), newAccount.getName());

		// if this user has an acc with this name already, don't add a new one to the db
		if ((check != null) || (newAccount.getName() == null) || newAccount.getName().isEmpty()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "I can't read or write.");
		} else {
			return bankRepo.save(newAccount);
		}
	}

	/**
	 * @return BankAccount
	 *
	 * @author Parker Mace
	 */
	@Override
	public BankAccount getBankAccount(UserAccount user, String name) {
		return bankRepo.findByUserAndName(user, name);
	}

	/**
	 * @return FundTransfer
	 *
	 * @author Parker mace
	 */
	@Override
	public List<BankAccount> transferFunds(UserAccount user, FundTransfer fundTransfer) {
		BankAccount account1 = getBankAccount(user, fundTransfer.getTransferFromAccount());
		BankAccount account2 = getBankAccount(user, fundTransfer.getTransferToAccount());

		// we do this to bundle the db calls for both accounts
		List<BankAccount> accounts = Arrays.asList(account1, account2);

		// if they can't afford the tx or an acc is null, don't call the db, don't pass
		// go, don't collect $200

		if ((account1 == null) || (account2 == null) || (fundTransfer.getTransferAmount() == null)
				|| (account1.getBalance() < fundTransfer.getTransferAmount())
				|| (fundTransfer.getTransferAmount() <= 0)) {
			throw new ResponseStatusException(HttpStatus.I_AM_A_TEAPOT);
		}

		// if a user tries to be cheeky and enter fractional cents, we will round their
		// request
		fundTransfer.setTransferAmount(Math.round(fundTransfer.getTransferAmount() * 100.0) / 100.0);

		Transaction txExpense = new Transaction(0, fundTransfer.getTransferAmount(),
				"Money transfer from " + account1.getName() + " to " + account2.getName(), Instant.now(), 1,
				account1.getId());
		Transaction txIncome = new Transaction(0, fundTransfer.getTransferAmount(),
				"Money transfer from " + account1.getName() + " to " + account2.getName(), Instant.now(), 2,
				account2.getId());

		txRepo.saveAll(Arrays.asList(txExpense, txIncome));

		account1.setBalance(account1.getBalance() - fundTransfer.getTransferAmount());
		account2.setBalance(account2.getBalance() + fundTransfer.getTransferAmount());

		// we do this to bundle the db calls for both accounts
		bankRepo.saveAll(accounts);

		// redundant line for testing purposes
		return accounts;
	}

	@Override
	public void betweenUsers(UserAccount user, BetweenUsers between) {

		UserAccount otherUser = userAccServ.getUserFromUsername(between.getUser());
		BankAccount originBank = bankRepo.getById(between.getTransferAccount());
		if ((otherUser == null) || (otherUser.getId() == null)) {
			throw new ResponseStatusException(HttpStatus.NO_CONTENT);
		}

		if ((between.getSendOrReceive() == 1) && (originBank.getBalance() < between.getTransferAmount())) {
			throw new ResponseStatusException(HttpStatus.I_AM_A_TEAPOT);
		}
		if (between.getSendOrReceive() == 1) {
			emailServ.send(new EmailData(otherUser.getEmail(), user.getUsername() + " has sent money", user.getUsername() + " has sent $" + between.getTransferAmount()));
		} else {
			emailServ.send(new EmailData(otherUser.getEmail(), user.getUsername() + " requests money", user.getUsername() + " requests $" + between.getTransferAmount()));
		}
		between.setOriginUser(user.getUsername());
		reqRepo.save(between);

	}

	@Override
	public List<BankAccount> completeTransfer(BetweenUsers between) {
		BankAccount account1 = bankRepo.getById(between.getTransferAccount());
		BankAccount account2 = bankRepo.getById(between.getReceiveAccount());

		List<BankAccount> accounts = Arrays.asList(account1, account2);

		Transaction requestAcc = new Transaction(0, between.getTransferAmount(), null, Instant.now(), 1,
				account1.getId());
		Transaction recipient = new Transaction(0, between.getTransferAmount(), null, Instant.now(), 2,
				account2.getId());

		if (between.getSendOrReceive() == 1) {
			if (account1.getBalance() < between.getTransferAmount()) {
				throw new ResponseStatusException(HttpStatus.I_AM_A_TEAPOT);
			}
			account1.setBalance(account1.getBalance() - between.getTransferAmount());
			account2.setBalance(account2.getBalance() + between.getTransferAmount());

			requestAcc.setDescription("Money sent to " + between.getUser());
			recipient.setDescription("Money received from " + between.getOriginUser());
		} else if (between.getSendOrReceive() == 2) {
			if (account2.getBalance() < between.getTransferAmount()) {
				throw new ResponseStatusException(HttpStatus.I_AM_A_TEAPOT);
			}

			account2.setBalance(account2.getBalance() - between.getTransferAmount());
			account1.setBalance(account1.getBalance() + between.getTransferAmount());

			recipient.setDescription("Money sent to " + between.getUser());
			requestAcc.setDescription("Money received from " + between.getOriginUser());
		}


		txRepo.saveAll(Arrays.asList(requestAcc, recipient));
		reqRepo.deleteById(between.getId());

		bankRepo.saveAll(accounts);

		//redundant line for testing
		return accounts;
	}

	@Override
	public List<BetweenUsers> getBetweenUsers(UserAccount user) {
		return reqRepo.findAllByUser(user.getUsername());
	}

	@Override
	public void removeRequest(BetweenUsers between) {

		reqRepo.deleteById(between.getId());


	}

	/**
	 * @return List<BankAccount>
	 *
	 * @author Parker Mace, Henry Harvil, Andre Long
	 */
	@Override
	public List<BankAccount> getBankAccounts(int id) {

		return bankRepo.findAllByUserId(id);

	}

	/**
	 * @return AccountTransfer
	 *
	 * @author Caleb Kirschbaum
	 */
	@Override
	public List<BankAccount> transferAccount(UserAccount user, UserAccount socialUser) {
		if ((user == null) || (socialUser == null) || (user.getId() == null) || (socialUser.getId() == null)) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "I can't read or write.");
		}
		List<BankAccount> socialAccounts = getBankAccounts(socialUser.getId());
		for (BankAccount a : socialAccounts) {
			BankAccount fakeAccount = a;
			a.setUser(user);
			int i = 0;
			// Potentially breaks if the user has more than 2 Billion accounts with the same
			// name
			while (getBankAccount(user, fakeAccount.getName()) != null) {
				fakeAccount.setName(a.getName() + i);
			}
			createAccount(fakeAccount);
		}
		return getBankAccounts(user.getId());

	}

}
