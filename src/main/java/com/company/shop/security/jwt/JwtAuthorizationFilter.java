package com.company.shop.security.jwt;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.company.shop.members.dto.MembersDTO;
import com.company.shop.members.repository.MembersRepository;
import com.company.shop.security.service.PrincipalDetails;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

	
	private MembersRepository membersRepository;
	
	public JwtAuthorizationFilter(AuthenticationManager authManager,MembersRepository membersRepository) {
		super(authManager);
		this.membersRepository = membersRepository;
		
	}
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		log.info("인가가 필요한 주소 요청이 실행되는 메소드 : doFilterInternal()");
		
		//1. 권한이나 인가가 필요한 요청이 전달된다.
		String jwtHeader = request.getHeader("Authorization");
		log.info("jwtHeader:{}",jwtHeader);
		
		//2. header확인
		//Header가 비어있거나, 비어있지만 Bearer방식이 아니면 반환한다
		//JWT토큰 검증을 해서 정상적인 사용자인 확인 +> 정상적인 요청이아니경우
		if(jwtHeader == null || !jwtHeader.startsWith("Bearer")) {
			chain.doFilter(request, response);
			return;
		}
		
		//3. JWT토큰을 검증해서 정상적인 사용자인지, 권한이 맞는지 확인 
		//JWT토큰 검증을 해서 정상적인 사용자인 확인 => 정상적인 요청인 경우
		String jwtToken = request.getHeader("Authorization").replace("Bearer ", "");
		String username = JWT.require(Algorithm.HMAC512("mySecurityCos"))
				.build().verify(jwtToken).getClaim("memberEmail").asString();
		log.info("username:{}", username);
		
		
		if(username != null) {
			//spring secyrity 가 수행해주는 권한 처리를 위해 아래와 같이 토큰을 만들어
			//Authentication객체를 강제로 만들고 세션에 넣어준다
			MembersDTO membersDTO = MembersDTO.toDto(membersRepository.selectByEmail(username));
			
			PrincipalDetails principalDetails = new PrincipalDetails(membersDTO);
			
			Authentication authentication
			= new UsernamePasswordAuthenticationToken(principalDetails, null, principalDetails.getAuthorities());
			
			//강제로 시큐리티의 세션에 접근하여 값 저장한다
			SecurityContextHolder.getContext().setAuthentication(authentication);
		}
		
		chain.doFilter(request, response);
	}

	
	
	
	
}
