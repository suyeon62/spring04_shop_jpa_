package  com.company.shop.board.dto;

import java.sql.Date;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.company.shop.board.entity.BoardEntity;
import com.company.shop.members.dto.MembersDTO;
import com.company.shop.members.entitiy.MembersEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

//import members.dto.MembersDTO;

@Component
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder //기본 생성자 매서드 생성 안됨
public class BoardDTO {
   private long num, readcount, ref, re_step, re_level;
   private String  subject, content, ip, memberEmail;
   private Date reg_date;
//   private MembersDTO membersDTO;

   //board테이블의 파일 첨부를 처리해주는 멤버변수
   private String upload;

   //form페이지에서 파일첨부를 받아 처리해주는 멤버변수
   private MultipartFile filename;
   
   //@Builder 때문에 강제로 객체를 생성해서 사용
   private MembersDTO membersDTO=new MembersDTO();

   public static BoardDTO toDto(BoardEntity entity) {      
      MembersDTO membersDTO= new MembersDTO();      
      membersDTO.setMemberEmail(entity.getMembersEntity().getMemberEmail());      
      membersDTO.setMemberName(entity.getMembersEntity().getMemberName());   
      return BoardDTO.builder()
            .num(entity.getNum())
            .readcount(entity.getReadcount())
            .ref(entity.getRef())
            .re_step(entity.getRe_step())
            .re_level(entity.getRe_level())
            .subject(entity.getSubject())
            .content(entity.getContent())
            .ip(entity.getIp())            
            .reg_date(entity.getReg_date())
            .upload(entity.getUpload())
            .memberEmail(entity.getMembersEntity().getMemberEmail())
            .membersDTO(membersDTO)
            .build();
   }
   
   public static BoardEntity toEntity(BoardDTO dto) {
      MembersEntity membersEntity= new MembersEntity();
      membersEntity.setMemberEmail(dto.getMembersDTO().getMemberEmail());
      membersEntity.setMemberName(dto.getMembersDTO().getMemberName());
      return BoardEntity.builder()
            .num(dto.getNum())
            .readcount(dto.getReadcount())
            .ref(dto.getRef())
            .re_step(dto.getRe_step())
            .re_level(dto.getRe_level())
            .subject(dto.getSubject())
            .content(dto.getContent())
            .ip(dto.getIp())            
            .reg_date(dto.getReg_date())
            .upload(dto.getUpload())
            .membersEntity(membersEntity)
            .build();      
   }

}
