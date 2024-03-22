##############################################
게시판 만들기
##############################################

1. 테이블생성 
CREATE TABLE   board2(
    num number CONSTRAINT board_num PRIMARY KEY,
    subject varchar2(50),
    reg_date date,
    readcount number default 0, 
    ref number, 
    re_step number, 
    re_level number, 
    content varchar2(100),
    ip varchar2(20),
    upload varchar2(300),
    member_email varchar2(50),
   CONSTRAINT board_member_email FOREIGN KEY(member_email) REFERENCES members2(member_email)
   ON DELETE CASCADE
);

CREATE SEQUENCE board_num_seq
START WITH 1 
INCREMENT BY 1
NOCACHE
NOCYCLE;

INSERT INTO board 
VALUES(board_num_seq.nextval,'제목1',sysdate,0,board_num_seq.nextval,
0,0,'내용 테스트.......','127.0.0.1','sample.txt', null);

commit;


select b.* 
from (select rownum as rm, a.*
	  from (select *
	 	    from board
            order by ref desc, re_step asc) a)b
where b.rm>=? and b.rm<=?           



-- 컬럼 추가
/*
ALTER TABLE board
ADD memberEmail varchar2(50);
*/

-- 제약조건 
/*
ALTER TABLE board
ADD CONSTRAINT board_memberEmail FOREIGN KEY(memberEmail) REFERENCES members(memberEmail);
*/




--  MYSQL JPA====================================================================================================================
CREATE TABLE board2 (
    num INT AUTO_INCREMENT,
    subject VARCHAR(50),
    reg_date DATETIME DEFAULT CURRENT_TIMESTAMP,
    readcount INT DEFAULT 0,
    ref INT,
    re_step INT,
    re_level INT,
    content VARCHAR(100),
    ip VARCHAR(20),
    upload VARCHAR(300),
    member_email VARCHAR(50),
    PRIMARY KEY (num),
    CONSTRAINT board_member_email FOREIGN KEY(member_email) REFERENCES members2(member_email)
    ON DELETE CASCADE
);

INSERT INTO board 
(subject, reg_date, readcount, ref, re_step, re_level, content, ip, upload, memberEmail)
VALUES (?, NOW(), 0, ?, 0, 0, ?, ?, ?, NULL);






















