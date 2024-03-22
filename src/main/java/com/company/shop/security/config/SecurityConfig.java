package com.company.shop.security.config;

import javax.security.sasl.AuthorizeCallback;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.company.shop.members.repository.MembersRepository;
import com.company.shop.security.jwt.JwtAuthenticationFilter;
import com.company.shop.security.jwt.JwtAuthorizationFilter;
import com.mysql.cj.Session;
//[1] POSTMAN에서 테스트
//POST http://localhost:8090/login
//body, raw , json  => {"memberEmail":"min@daum.net", "memberPass":"1234"}



@Configuration // 해당 클래스를 환경설정(configuration)으로 등록
@EnableWebSecurity
public class SecurityConfig {
	
	@Autowired
	private MembersRepository membersRepository;
	
   @Bean
   public BCryptPasswordEncoder encodePwd() {
      return new BCryptPasswordEncoder();
   }
   
   @Bean
   public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
	   
	   //csrf() : Cross Site Request Forgery로 사이트간 위조 요청으로 정상적인 사용자가 의도치 않은
	   //위조 요청을 보내는 것을 의미한다.
//	   http.csrf((csrf) -> csrf.disable());
	   http.csrf(AbstractHttpConfigurer::disable);
	   
	   //인증사용, Security Filter에 등록 , @CrossOrigin (인증X)
	   //세션끄기 : JWT를 사용하기 때문에 세션을 사용하지 않는다.
	   http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
	   
	   //인증사용, security Filter에 등록
	   http.apply((new MyCustomerFilter()));
	   
	   //요청에 의한 인가(권한) 검사 시작
	   http.authorizeHttpRequests(authorize -> authorize 
			   						.requestMatchers("/","/images/**","/member/signup","/board/list/**").permitAll() // 로그인 없이 접근 허용한다
			   						.anyRequest().authenticated());// 그외 모든 요청에 대해서 인증(로그인)이 되어야 한다.
	   //.anyRequest().permitAll());// 그외 모든 요청에 대해서 인증(로그인)이 되어야 한다. 모든요쳉에대하여 굿
	   return http.build();
   }
   
   public class MyCustomerFilter extends AbstractHttpConfigurer<MyCustomerFilter, HttpSecurity>{
	   @Override
	   public void configure(HttpSecurity http) throws Exception{
		   AuthenticationManager authenticationManager = http.getSharedObject(AuthenticationManager.class);
		   
		   //addFilter() : FilterComparator에 등록되어 있는 Filter들을 활성화할 때 사용
		   //adddFilterBefore(), addFilterAfter() : CustomFilter를 등록할 때 사용
		   
		   //인증 필터 등록
		   http.addFilter(new JwtAuthenticationFilter(authenticationManager));
		   
		   //인가 필터 등록
		   http.addFilter(new JwtAuthorizationFilter(authenticationManager,membersRepository));
	   }
   }
}//end class
