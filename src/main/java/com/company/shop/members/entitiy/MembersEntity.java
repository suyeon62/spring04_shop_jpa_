package com.company.shop.members.entitiy;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Entity //DB테이블과 매핑 대상
@Table(name="members2") //member2테이블과 매핑
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class MembersEntity {
	@Id //primary key
	@Column(name="member_email")
	private String memberEmail; //이메일
	
	@Column(name="member_pass", length=100, nullable = false)  //default 255
	private String memberPass; //비밀번호
	
	@Column(name="member_name", nullable = false)
	private String memberName; //이름
	
	@Column(name="member_phone", nullable=false)
	private String memberPhone; //전화번호
	
	@Column(name="member_type")
	private long memberType;  //회원구분  일반회원 1, 관리자 2
}
