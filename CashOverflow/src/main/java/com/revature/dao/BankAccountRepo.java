package com.revature.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.revature.model.BankAccount;

/**
 * Repo for interacting with bank accounts in the database.
 * @author Colin Knox, Parker Mace, Tyler Rondeau
 */
@Repository
public interface BankAccountRepo extends JpaRepository<BankAccount, Integer> {

}