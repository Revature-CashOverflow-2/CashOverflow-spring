package com.revature.service;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.revature.dao.SocialAccountRepo;
import com.revature.model.UserAccount;
import com.revature.model.UserSocialMedia;

@Service
public class SocialAccountServiceImpl implements SocialAccountService {

	private SocialAccountRepo socialRepo;

	@Autowired
	protected SocialAccountServiceImpl(SocialAccountRepo socialRepo) {
		this.socialRepo = socialRepo;
	}

	@Override
	public UserSocialMedia createSocial(UserSocialMedia newAccount) {
		UserSocialMedia check = socialRepo.findByUsername(newAccount.getUsername());

		// if this user has an acc with this name already, don't add a new one to the db
		if ((check != null) || (newAccount.getUsername() == null) || newAccount.getUsername().isEmpty()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "I can't read or write.");
		} else {
			return socialRepo.save(newAccount);
		}
	}

	@Override
	public UserAccount getSoicalOwner(UserSocialMedia social) {
		return socialRepo.findByUsername(social.getUsername()).getOwner();
	}

	@Override
	public List<UserSocialMedia> getSocialAccounts(int id) {
		return  Arrays.asList (socialRepo.getById(id));
	}

	@Override
	public UserSocialMedia getSocialAccount(String username) {
		return socialRepo.findByUsername(username);
	}

}
