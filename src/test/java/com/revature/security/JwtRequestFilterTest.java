package com.revature.security;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockFilterChain;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.core.context.SecurityContextHolder;

import com.revature.service.JwtUserDetailsService;
import com.revature.util.JwtUtil;

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

		request.addHeader("Authorization", "Bearer this is a good token");
		filt.doFilter(request, response, new MockFilterChain());
	}

}
