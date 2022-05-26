package com.revature.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.server.ResponseStatusException;

import com.revature.dao.SocialAccountRepo;
import com.revature.dao.UserAccountRepo;
import com.revature.dto.SocialAccountDto;
import com.revature.model.UserAccount;
import com.revature.model.UserSocialMedia;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class SocialAccountServiceImplTest {

	@Autowired
	private SocialAccountService serv;

	@Autowired
	private SocialAccountService socialServ;
	
	@Autowired
	private SocialAccountRepo repo;

	@Autowired
	private UserAccountRepo uaRepo;

	
	@Mock
	private SocialAccountRepo mockRepo;

	@Mock
	private UserAccountRepo mockUARepo;

	
	@BeforeEach
	void setUpEach() {
		
		socialServ = new SocialAccountServiceImpl(mockRepo);
		UserAccount person1 = new UserAccount();
		uaRepo.save(person1);
		repo.save(new UserSocialMedia(1, "user1", "SubSomething", person1));
		repo.save(new UserSocialMedia(2, "user2", "SubOther", person1));

	}

	@Test
	void createSocialTestSuccessful() {
		UserAccount person1 = new UserAccount();
		uaRepo.save(person1);
		UserSocialMedia social1 = new UserSocialMedia(3, "testSocialUsername", "SubSomething", person1);
		UserSocialMedia returnedSocial = serv.createSocial(social1);
		assertEquals(social1, returnedSocial);
	}

	@Test
	void createSocialTestUsnameIsNullFail() {
		UserAccount person1 = new UserAccount();
		uaRepo.save(person1);
		
		UserSocialMedia social1 = new UserSocialMedia(3, null, "SubSomething", person1);
		assertThrows(ResponseStatusException.class, () -> serv.createSocial(social1));
		
		UserSocialMedia social2 = new UserSocialMedia(3, "", "SubSomething", person1);
		assertThrows(ResponseStatusException.class, () -> serv.createSocial(social2));
		
		UserSocialMedia social3 = new UserSocialMedia(3, "user1", "SubSomething", person1);
		assertThrows(ResponseStatusException.class, () -> serv.createSocial(social3));
	}
	
	@Test
	void getSocialAccountTest(){
		UserSocialMedia mockUsrMedia = new UserSocialMedia();
		mockUsrMedia.setUsername("Rabia");
		mockUsrMedia.setId(123);
		mockUsrMedia.setProfileSub("profile sub string");
		mockUsrMedia.setOwner(null);
	
		when(mockRepo.findByUsername(mockUsrMedia.getUsername())).thenReturn(mockUsrMedia);
		UserSocialMedia result = socialServ.getSocialAccount(mockUsrMedia.getUsername());
		assertEquals(mockUsrMedia, result);
				
	}

	@Test
	void getSocialOwner() {
		UserAccount mockUserAccount = new UserAccount();
		mockUserAccount.setUsername("henda");
		mockUserAccount.setEmail("HENDAYAD");
		mockUserAccount.setId(0);
		
		UserSocialMedia mockUserSocialMedia = new UserSocialMedia();
		mockUserSocialMedia.setUsername("henda");
		mockUserSocialMedia.setOwner(mockUserAccount);
		
		when(mockRepo.findByUsername("henda")).thenReturn(mockUserSocialMedia);
		UserAccount result = socialServ.getSoicalOwner(mockUserSocialMedia);
		verify(mockRepo, times(1)).findByUsername("henda");
		assertEquals(result,mockUserAccount);
	}
	
	@Test
	void getSocialAccountsTest(){
		List<UserSocialMedia> mockSocialUserMedias = new ArrayList<>();
		UserSocialMedia mockUsrMedia = new UserSocialMedia();
		mockUsrMedia.setUsername("Rabia");
		mockUsrMedia.setId(123);
		mockUsrMedia.setProfileSub("profile sub string");
		mockUsrMedia.setOwner(null);
		mockSocialUserMedias.add(mockUsrMedia);
		
		when(mockRepo.getById(mockUsrMedia.getId())).thenReturn(mockUsrMedia);
		List<UserSocialMedia>  result =  socialServ.getSocialAccounts(mockUsrMedia.getId());
		verify(mockRepo, times(1)).getById(mockUsrMedia.getId());
		assertEquals(mockSocialUserMedias, result);

	}

}

