package com.example.social_network.jwt;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.io.IOException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import com.example.social_network.service.impl.UserInfoServiceImpl;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@ExtendWith(MockitoExtension.class)
public class JwtAuthFilterTest {

	@Test
	void testDoFilterInternal() throws ServletException, IOException {
		// Mock objects
		HttpServletRequest request = mock(HttpServletRequest.class);
		HttpServletResponse response = mock(HttpServletResponse.class);
		FilterChain filterChain = mock(FilterChain.class);
		JwtUtils jwtTokenUtil = mock(JwtUtils.class);
		UserInfoServiceImpl userDetailsService = mock(UserInfoServiceImpl.class);
		UserDetails userDetails = mock(UserDetails.class);

		when(request.getHeader("Authorization")).thenReturn("Bearer token");
		when(jwtTokenUtil.validateToken("token", userDetails)).thenReturn(true);
		when(jwtTokenUtil.extractUsername("token")).thenReturn("user");
		when(userDetailsService.loadUserByUsername("user")).thenReturn(userDetails);
		when(userDetails.getAuthorities()).thenReturn(null);

		JwtAuthFilter authenticationFilter = new JwtAuthFilter(jwtTokenUtil, userDetailsService);

		authenticationFilter.doFilterInternal(request, response, filterChain);

		verify(filterChain).doFilter(request, response);
		verifyNoMoreInteractions(filterChain);
		verify(request).getHeader("Authorization");
		verify(jwtTokenUtil).validateToken("token", userDetails);
		verify(jwtTokenUtil).extractUsername("token");
		verify(userDetailsService).loadUserByUsername("user");
		verify(userDetails).getAuthorities();
	}
}
