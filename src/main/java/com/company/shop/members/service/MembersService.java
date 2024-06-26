package com.company.shop.members.service;

import com.company.shop.members.dto.AuthInfo;
import com.company.shop.members.dto.ChangePwdCommand;
import com.company.shop.members.dto.MembersDTO;

public interface MembersService {
	public AuthInfo addMemberProcess(MembersDTO dto);
	public AuthInfo loginProcess(MembersDTO dto);
	
	public MembersDTO updateMemberProcess(String memberEmail);
	public AuthInfo updateMemberProcess(MembersDTO dto);
//	public void updatePassProcess(String memberEmail, ChangePwdCommand changePwd);
	public void deleteMemberProcess(String memberEmail);
}
