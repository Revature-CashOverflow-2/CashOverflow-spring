package com.revature.security;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.core.AuthenticationException;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class JwtAuthenticationEntryPointTest {

//	private final CloseableHttpClient httpClient = HttpClients.createDefault();

	JwtAuthenticationEntryPoint enPoint;

	@BeforeEach
	void setUp() throws Exception {
		enPoint = new JwtAuthenticationEntryPoint();
	}

	// for this test the project must be running locally
	@Test
	void test() throws ClientProtocolException, IOException {
		HttpServletRequest request = new MockHttpServletRequest();
		HttpServletResponse response = new MockHttpServletResponse();
		AuthenticationException authException = null;
		try {
			enPoint.commence(request, response, authException);
		} catch (Exception e) {
			e.printStackTrace();
		}
		assertEquals(401, response.getStatus());
	}

}
