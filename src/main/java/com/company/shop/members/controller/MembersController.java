package com.company.shop.members.controller;

import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.company.shop.members.dto.AuthInfo;
import com.company.shop.members.dto.MembersDTO;
import com.company.shop.members.service.MembersService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Tag(name="회원 관련", description="사용자 관련 API")
@Slf4j
//@CrossOrigin(origin={"http://localhost:3000"})
@CrossOrigin("*")
@RestController
public class MembersController {

   @Autowired
   private MembersService membersService;

   @Autowired
   private BCryptPasswordEncoder encodePassword;

// @Autowired을 사용하지 않을 경우, 생성자함수를 구현하여 해당 객체를 받아서 상용했어야 함.
//    public MembersController(MembersService membersService, BCryptPasswordEncoder encodePassword) {
//        this.membersServic e = membersService;
//        this.encodePassword = encodePassword;
//    }
   
   public MembersController() {
   }

   @Operation(summary="회원가입하기", description="회원가입 API")
   // 회원가입 처리
   @PostMapping("/member/signup")
   public ResponseEntity<AuthInfo> addmember(@RequestBody MembersDTO membersDTO) {
      log.info("membersDTO:{}", membersDTO);
      membersDTO.setMemberPass(encodePassword.encode(membersDTO.getMemberPass()));
      AuthInfo authInfo = membersService.addMemberProcess(membersDTO);
      return ResponseEntity.ok(authInfo);
   }
   
   @Operation(summary="회원정보 가져오기", description="회원정보 가져오기 API")
   //회원정보 가져오기
   @GetMapping("/member/editinfo/{memberEmail}")
   public ResponseEntity<MembersDTO> getMember(@PathVariable("memberEmail") String memberEmail){
        log.info("memberEmail", memberEmail);
      return ResponseEntity.ok(membersService.updateMemberProcess(memberEmail));
   }

   //회원정보 수정
   @Operation(summary="회원정보 수정", description="회원정보 수정 API")
   @PutMapping("/member/update")
   public ResponseEntity<AuthInfo> updateMember(@RequestBody MembersDTO membersDTO) {
	   //log.info("membersDTO", membersDTO);
	  membersDTO.setMemberPass(encodePassword.encode(membersDTO.getMemberPass()));
      return ResponseEntity.ok(membersService.updateMemberProcess(membersDTO));
      
   }
   
   //회원 탈퇴
   @Operation(summary="회원탈퇴", description="회원탈퇴 API")
   @DeleteMapping("/member/delete/{memberEmail}")
   public ResponseEntity<Object> deleteMember(@PathVariable("memberEmail") String memberEmail){
      membersService.deleteMemberProcess(memberEmail);
      return ResponseEntity.ok(null);
   }

   
   
//   //회원탈퇴
//   @DeleteMapping("/member/delete{memberEmail}")
//   public ResponseEntity<Object> deleteMember(@PathVariable("memberEmail") String memberEmail){
//	  membersService.deleteMemberProcess(memberEmail);
//	   return ResponseEntity.ok(null);
//   }
   
   

}