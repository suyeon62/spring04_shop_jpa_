CREATE TABLE   members(
  memberEmail varchar2(50) ,  --이메일
  memberPass varchar2(30),  --비밀번호
  memberName varchar2(30), --이름
  memberPhone char(11),  --전화번호  
  memberType number(1),  --회원구분 일반회원 1, 관리자 2
  constraint members_email primary key(memberEmail)
);

-- MYSQL ===================================================================================
CREATE TABLE members2 (
  member_email VARCHAR(50),
  member_pass VARCHAR(100),
  member_name VARCHAR(30),
  member_phone CHAR(11),
  member_type INT DEFAULT 1,
  PRIMARY KEY (member_email)
);

SELECT * FROM board;
SELECT * FROM members;

DELETE FROM members
WHERE memberType=0;

-- memberyType 컬럼 삭제
ALTER TABLE members
DROP COLUMN memberType;

-- memberType 컬럼 추가
ALTER TABLE members
ADD COLUMN memberType int DEFAULT 1;

INSERT INTO members(memberEmail, memberPass, MemberName, MemberPhone)
VALUES('kkk@daum.net','1234','홍길동','0000000');


ALTER TABLE members
DROP COLUMN memberType;

ALTER TABLE members
ADD COLUMN memberType int DEFAULT 1;

DELETE FROM members;

alter table board
drop constraint board_memberEmail;

alter table board
add constraint board_memberEmail FOREIGN KEY(memberEmail) REFERENCES members(memberEmail)
ON DELETE CASCADE ;

-- ON UPDATE CASCADE;