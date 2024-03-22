package com.company.shop.board.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.company.shop.board.dto.BoardDTO;
import com.company.shop.board.dto.PageDTO;
import com.company.shop.board.entity.BoardEntity;

@Repository
public interface BoardRepository extends JpaRepository<BoardEntity, Long> {
   // boardEntity에 컬럼에 있는 데이터 타입. Jpa는 Long을 사용

   // 전체 레코드 수
   @Query(value = "SELECT count(*) FROM board2", nativeQuery = true)
   public long count();

   @Query(value = "SELECT b.* FROM board2 b" + " ORDER BY b.ref DESC, b.re_step ASC"
         + " LIMIT :#{#pv.startRow}, :#{#pv.blockCount}", nativeQuery = true)
   public List<BoardEntity> list(@Param("pv") PageDTO pv);

   @Query(value = "UPDATE board2 SET readcount = readcount + 1 WHERE num=:num", nativeQuery = true)
   @Modifying
   public void readCount(@Param("num") long num);

   @Query(value = "SELECT b.*, m.member_name" + " FROM board2 b"
	         + " LEFT JOIN members2 m ON b.member_email = m.member_email" + " WHERE b.num = :num", nativeQuery = true)
	   public BoardEntity content(@Param("num") long num);

   @Modifying
   @Query(value = "UPDATE board2" + " SET re_step=re_step+1"
         + " WHERE ref=:#{#entity.ref} AND re_step > :#{#entity.re_step}", nativeQuery = true)
   public void reStepCount(@Param("entity") BoardEntity entity);

   // num이 null값일 경우 0으로 대체
   @Query(value = "SELECT ifnull (max(num),0)+1 FROM board2", nativeQuery = true)
   public int refPlus();

   @Modifying
   @Query(value = "INSERT INTO board2"
         + "   (subject, reg_date, readcount, ref, re_step, re_level, content, ip, upload, member_email)"
         + " VALUES (:#{#entity.subject}, NOW(), 0, :#{#entity.ref}, :#{#entity.re_step}, :#{#entity.re_level}, :#{#entity.content}, :#{#entity.ip}, :#{#entity.upload}, :memberEmail)", nativeQuery = true)
   public void saveMember(@Param("entity") BoardEntity entity, @Param("memberEmail") String memberEmail);

   @Modifying
   @Query(value = "UPDATE board2 SET subject=:#{#entity.subject},content=:#{#entity.content},"  		 		
   		+ " upload=:#{#entity.upload}"
   		+ " WHERE num=:#{#entity.num}",nativeQuery = true)
   public void update(@Param("entity") BoardEntity entity);

   @Modifying
   @Query(value = "DELETE FROM board2 WHERE num=:num", nativeQuery = true)
   public void deleteMember(@Param("num") long num);
//
   
   @Query(value = "SELECT upload FROM board2 WHERE num=:num",nativeQuery = true)
   public String getFile(@Param("num") long num);

}