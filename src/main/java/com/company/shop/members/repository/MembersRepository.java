package com.company.shop.members.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.company.shop.members.dto.MembersDTO;
import com.company.shop.members.entitiy.MembersEntity;


@Repository
public interface MembersRepository extends JpaRepository<MembersEntity, String>{
   
	
   //회원가입
   @Query(value=" INSERT INTO members2(member_email, member_pass, member_name, member_phone, member_type)"
         + " VALUES(:#{#entity.memberEmail}, :#{#entity.memberPass}, :#{#entity.memberName}, :#{#entity.memberPhone}, :#{#entity.memberType})", nativeQuery = true)   
   @Modifying
   public int insertMember(@Param("entity") MembersEntity entity);
  
   
   
   //회원정보 가져오기
   @Query(value="SELECT * FROM members2"
         + " WHERE member_email = :memberEmail", nativeQuery = true) //객체가 아니라 단일값은 :만 표시 
   public MembersEntity selectByEmail(@Param("memberEmail") String memberEmail);
   
   
   //회원정보수정
   @Modifying
   @Query(value = "UPDATE members2"
   		+ " SET  member_pass=:#{#entity.memberPass}, member_name=:#{#entity.memberName}, member_phone=:#{#entity.memberPhone}"
   		+ " WHERE member_email=:#{#entity.memberEmail}", nativeQuery = true)
   public void updateMember(@Param("entity") MembersEntity entity);
   
   
   
   
   
//   public void updateByPass(MembersDTO dto);
   
   //회원탈퇴
   @Modifying
   @Query(value = "DELETE FROM members2"
   		+ " WHERE member_email =:memberEmail", nativeQuery = true)
   public void deleteMember(@Param("memberEmail") String memberEmail);
   
   
   
   
   
   
   
   
   
}