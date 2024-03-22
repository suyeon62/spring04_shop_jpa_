package com.company.shop.members.dto;

import org.springframework.stereotype.Component;

import com.company.shop.common.exception.WrongEmailPasswordException;
import com.company.shop.members.entitiy.MembersEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Component
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MembersDTO {
	private String memberEmail;
	private String memberPass;
	private String memberName;
	private String memberPhone;
	private long memberType=1;
	private String authRole;
	
	public boolean matchPassword(String memberPass) {
		return this.memberPass.equals(memberPass);
	}

	public void changePassword(String oldPassword, String newPassword) {
		if (!this.memberPass.equals(oldPassword))
			throw new WrongEmailPasswordException("비밀번호 불일치");
		this.memberPass = newPassword;
	}
	
	// dto =>entity
	   public static MembersEntity  toEntity(MembersDTO dto) {
		   MembersEntity entity = new MembersEntity();
		   entity.setMemberEmail(dto.getMemberEmail());
		   entity.setMemberPass(dto.getMemberPass());
		   entity.setMemberName(dto.getMemberName());
		   entity.setMemberPhone(dto.getMemberPhone());
		   entity.setMemberType(dto.getMemberType());
		   return entity;	   
	   }
	   
	   
	   // entity => dto
	   public static MembersDTO toDto(MembersEntity entity) {
		   MembersDTO dto = new MembersDTO();
		   dto.setMemberEmail(entity.getMemberEmail());
		   dto.setMemberPass(entity.getMemberPass());
		   dto.setMemberName(entity.getMemberName());
		   dto.setMemberPhone(entity.getMemberPhone());
		   dto.setMemberType(entity.getMemberType());
		   return dto; 
	   }
	
}
