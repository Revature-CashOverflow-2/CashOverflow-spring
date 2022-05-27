package com.revature.security;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockFilterChain;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import com.revature.service.JwtUserDetailsService;
import com.revature.util.JwtUtil;

import io.jsonwebtoken.ExpiredJwtException;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class JwtRequestFilterTest {

	JwtRequestFilter filt;
	
	@Mock
	JwtUserDetailsService serv;
	
	@Mock
	JwtUtil util;
	
	
	
	@BeforeEach
	void setUp() throws Exception {
		filt = new JwtRequestFilter(serv, util);
	}
	@Test
	void filterTestNoHeader() throws ServletException, IOException {
		HttpServletRequest request = new MockHttpServletRequest();
		HttpServletResponse response = new MockHttpServletResponse();
		filt.doFilter(request, response, new MockFilterChain());
	}
	
	@Test
	void filterTestGoodToken() throws ServletException, IOException {
		MockHttpServletRequest request = new MockHttpServletRequest();
		HttpServletResponse response = new MockHttpServletResponse();
		
		UserDetails userDetails = Mockito.mock(UserDetails.class);
		
		String jwtToken = "this is a test token";
		String username = "test";
		
		when(util.getUsernameFromToken(jwtToken)).thenReturn(username);
		when(serv.loadUserByUsername(username)).thenReturn(userDetails);
		when(util.validateToken(jwtToken, userDetails)).thenReturn(true);

		request.addHeader("Authorization", "Bearer this is a test token");
		filt.doFilter(request, response, new MockFilterChain());
	}
	
	@Test
	void filterTestBadToken() throws ServletException, IOException {
		MockHttpServletRequest request = new MockHttpServletRequest();
		HttpServletResponse response = new MockHttpServletResponse();
		
		UserDetails userDetails = Mockito.mock(UserDetails.class);
		
		String jwtToken = "this is a test token";
		String username = "test";
		
		when(util.getUsernameFromToken(jwtToken)).thenReturn(username);
		when(serv.loadUserByUsername(username)).thenReturn(userDetails);
		when(util.validateToken(jwtToken, userDetails)).thenReturn(false);

		request.addHeader("Authorization", "Bearer this is a test token");
		filt.doFilter(request, response, new MockFilterChain());
	}
	
	@Test
	void filterExceptionTest1() throws ServletException, IOException {
		MockHttpServletRequest request = new MockHttpServletRequest();
		HttpServletResponse response = new MockHttpServletResponse();
		
		String jwtToken = "this is a test token";
		
		when(util.getUsernameFromToken(jwtToken)).thenThrow(new IllegalArgumentException());

		request.addHeader("Authorization", "Bearer this is a test token");
		filt.doFilter(request, response, new MockFilterChain());
	}
	
	@Test
	void filterExceptionTest2() throws ServletException, IOException {
		MockHttpServletRequest request = new MockHttpServletRequest();
		HttpServletResponse response = new MockHttpServletResponse();
		ExpiredJwtException e = Mockito.mock(ExpiredJwtException.class);
		
		String jwtToken = "this is a test token";
		
		when(util.getUsernameFromToken(jwtToken)).thenThrow(e);

		request.addHeader("Authorization", "Bearer this is a test token");
		filt.doFilter(request, response, new MockFilterChain());
	}

}
