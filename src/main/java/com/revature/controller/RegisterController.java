package com.revature.controller;

import java.util.regex.Pattern;

import com.google.common.annotations.VisibleForTesting;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ResponseStatusException;

import com.revature.dto.BankAccountDto;
import com.revature.dto.RegUserAccountDto;
import com.revature.dto.SocialAccountDto;
import com.revature.model.BankAccount;
import com.revature.model.UserAccount;
import com.revature.model.UserSocialMedia;
import com.revature.service.BankAccountService;
import com.revature.service.RegisterService;
import com.revature.service.SocialAccountService;
import com.revature.service.UserAccountService;

@CrossOrigin(value = { "http://localhost:4200", "http://dostz94b44kp0.cloudfront.net", "https://44.200.39.202" })
@Controller
public class RegisterController {

	private RegisterService regServ;
	private ModelMapper mapper;
	private PasswordEncoder enc;
	private UserAccountService serv;
	private SocialAccountService userSocialServ;
	private BankAccountService bankAccServ;




	@Autowired
	public RegisterController(RegisterService regServ, ModelMapper mapper, PasswordEncoder enc,
			UserAccountService serv, SocialAccountService userSocialServ, BankAccountService bankAccServ) {
		this.regServ = regServ;
		this.mapper = mapper;
		this.enc = enc;
		this.serv = serv;
		this.userSocialServ = userSocialServ;
		this.bankAccServ = bankAccServ;
	}

	/**
	 * Takes the data from angular puts it into a dto object, throws a 400 exception
	 * if any values are null. Then passes the data to the service layer to write to
	 * the database.
	 *
	 * @authors Cameron, Amir, Chandra
	 */
	@PostMapping("/register")
	@ResponseStatus(HttpStatus.CREATED)
	public void newUser(@RequestBody RegUserAccountDto dto) {

		// Might be better to instead of caling a new function, to just run the if and
		// set passwords inside the if statement. But this should be fine
		if (dto.isAuth0User()) {
			newAuthUser(dto);
			return;
		}
		Pattern ptr = Pattern.compile("^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$");
		if ((dto.getEmail() == null) || dto.getEmail().isEmpty() || (dto.getUsername() == null)
				|| dto.getUsername().isEmpty() || (dto.getFirstName() == null) || dto.getFirstName().isEmpty()
				|| (dto.getLastName() == null) || dto.getLastName().isEmpty() || (dto.getPassword() == null)
				|| dto.getPassword().isEmpty() || !ptr.matcher(dto.getEmail()).matches()) {

			throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
		}
		dto.setPassword(enc.encode(dto.getPassword()));
		UserAccount user = convertToEntity(dto);
		regServ.insertUserAccount(user);
	}

	public void newAuthUser(@RequestBody RegUserAccountDto dto) {

		// The only difference here from the top is it doesn't check the password
		// Might be better to spread this out for more accurate responses.
		Pattern ptr = Pattern.compile("^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$");
		if ((dto.getEmail() == null) || dto.getEmail().isEmpty() || (dto.getUsername() == null)
				|| dto.getUsername().isEmpty() || (dto.getFirstName() == null) || dto.getFirstName().isEmpty()
				|| (dto.getLastName() == null) || dto.getLastName().isEmpty()
				|| !ptr.matcher(dto.getEmail()).matches()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
		}

		// We encode this to allow bcrypt to check a value so we can have a token
		// This is the same value as in login controller for auth users.
		dto.setPassword(enc.encode("auth0User"));
		UserAccount user = convertToEntity(dto);
		regServ.insertUserAccount(user);
	}

	@PostMapping("/api/account/addSocial")
	@ResponseStatus(HttpStatus.CREATED)
	public void addSocial(Authentication auth, @RequestBody SocialAccountDto dtoAccount) {
		if ((auth == null) || (dtoAccount == null)) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
		}
		String username = auth.getName();

		UserAccount user = serv.getUserFromUsername(username);
		// This is done because we can't login a user without knowing their password
		// So non Authusers are banned from connecting auth accounts
		if (!user.isAuthAccount()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
					"Can't attach auth accounts to a separate account");
		}
		UserAccount socialUser = serv.getUserFromUsername(dtoAccount.getUsername());
		bankAccServ.transferAccount(user, socialUser);
		dtoAccount.setOwner(user);
		UserSocialMedia account = this.convertToDto(dtoAccount);
		account.setOwner(serv.getUserFromUsername(auth.getName()));
		userSocialServ.createSocial(account);
	}

	protected UserSocialMedia convertToDto(SocialAccountDto account) {
		return mapper.map(account, UserSocialMedia.class);
	}

	protected BankAccountDto convertToDto(BankAccount account) {
		return mapper.map(account, BankAccountDto.class);
	}

	private UserAccount convertToEntity(RegUserAccountDto dto) {
		return mapper.map(dto, UserAccount.class);
	}

	protected UserSocialMedia convertToEntity(UserSocialMedia dtoAccount) {
		return mapper.map(dtoAccount, UserSocialMedia.class);
	}

}
