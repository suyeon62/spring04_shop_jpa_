package com.company.shop.members.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.shop.members.dto.AuthInfo;
import com.company.shop.members.dto.MembersDTO;
import com.company.shop.members.entitiy.MembersEntity;
import com.company.shop.members.repository.MembersRepository;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Transactional
@Slf4j
@Service
public class MembersServiceImp implements MembersService{

   @Autowired
   private MembersRepository membersRepository;
   
   public MembersServiceImp() {
   }

   @Override
   public AuthInfo addMemberProcess(MembersDTO dto) {
      membersRepository.insertMember(MembersDTO.toEntity(dto));
      log.info("entity {} {}", dto.getMemberEmail(), dto.getMemberName());
      return new AuthInfo(dto.getMemberEmail(), dto.getMemberName(), dto.getMemberPass());
   }

	

	@Override
	public AuthInfo loginProcess(MembersDTO dto) {
		// TODO Auto-generated method stub
		return null;
	}

   @Override
   public MembersDTO updateMemberProcess(String memberEmail) {
	   
      return MembersDTO.toDto(membersRepository.selectByEmail(memberEmail));
   }


	@Override
	public AuthInfo updateMemberProcess(MembersDTO dto) {
		membersRepository.updateMember(MembersDTO.toEntity(dto));
		return new AuthInfo(dto.getMemberEmail(), dto.getMemberName(), dto.getMemberPass());
	}
//
//	@Override
//	public void updatePassProcess(String memberEmail, ChangePwdCommand changePwd) {
//		// TODO Auto-generated method stub
//		
//	}
//	
	@Override
	public void deleteMemberProcess(String memberEmail) {
		membersRepository.deleteMember(memberEmail);
		
	}

}
