package com.company.shop.board.entity;

import java.sql.Date;

import org.springframework.stereotype.Component;

import com.company.shop.members.entitiy.MembersEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Component
@Entity
@Table(name="board2")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BoardEntity {

	@Id
	@Column
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long num;
	@Column
	private long  readcount, ref, re_step, re_level;
	@Column
	private String  subject, content, ip;
	@Column(insertable = false)	
	private Date reg_date;
	@Column
	//board테이블의 파일 첨부를 처리해주는 멤버변수
	private String upload;

	@ManyToOne
	@JoinColumn( name="member_email"  )
	private MembersEntity membersEntity;
	
}
