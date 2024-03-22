package com.company.shop.board.controller;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.company.shop.board.dto.BoardDTO;
import com.company.shop.board.dto.PageDTO;
import com.company.shop.board.service.BoardService;
import com.company.shop.common.file.FileUpload;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@CrossOrigin("*")
@RestController
public class BoardController {

	@Autowired
	private BoardService boardService;
	@Autowired
	private PageDTO pdto;
	private int currentPage;

	@Value("${spring.servlet.multipart.location}")
	private String filePath;

	public BoardController() {
	}

	// http://localhost:8090/board/list/1

	// 페이지 입력받아서 페이지당 리스트 가져오기 3페이지 (0 5 10)
	@GetMapping("/board/list/{currentPage}")
	public ResponseEntity<Map<String, Object>> listExecute(@PathVariable("currentPage") int currentPage) {
		Map<String, Object> map = new HashMap<>();
		// 전체 리스트갯수
		long totalRecord = boardService.countProcess();
		log.info("totalRecord:{}", totalRecord);
		// 전체 리스트 갯수가 1보다크면(보유한 리스트가 있으면) 그걸 가져와서 map객체에 넣고
		if (totalRecord >= 1) {
			this.currentPage = currentPage;
			//총페이지 수를 계산할때 사용
			this.pdto = new PageDTO(this.currentPage,totalRecord);
			
			map.put("boardList", boardService.listProcess(pdto));
			map.put("pv", this.pdto);

		}
		log.info("boardList:{}", map.get("boardList"));
		// 객체map에 put한 리스트 출력
		return ResponseEntity.ok(map);
	}

	// 첨부파일이있을때 @RequestBody X
	// 상세페이지 save
	// 답변글이 있을때 ref값을 re_step, re_level 꼭 넝어 줘야한다
	@PostMapping("/board/write")
	public ResponseEntity<String> writeProExecute(BoardDTO dto, PageDTO pv, HttpServletRequest req,
			HttpSession session) {
		MultipartFile file = dto.getFilename();
		log.info("subject:{}, content:{}", dto.getSubject(), dto.getContent());
		// 첨부파일 넣기 싫으면 이거 주석
		// log.info("file:{}", file.getOriginalFilename());

		// 파일 첨부가있으면
		if (file != null && !file.isEmpty()) {
			UUID random = FileUpload.saveCopyFile(file, filePath);
			dto.setUpload(random + "_" + file.getOriginalFilename());
		}
		dto.setIp(req.getRemoteAddr());
		boardService.insertProcess(dto);
		return ResponseEntity.ok(String.valueOf(1));
	}

	@GetMapping("/board/view/{num}")
	public ResponseEntity<BoardDTO> viewExecute(@PathVariable("num") int num) {
		BoardDTO DTo = boardService.contentProcess(num);

		return ResponseEntity.ok(DTo);
	}
//
	// 수정
	@PutMapping("/board/update")
	public ResponseEntity<Object> updateExecute(BoardDTO dto, HttpServletRequest req) {
		MultipartFile file = dto.getFilename();
		log.info(":{}", dto);
		if (file != null && !file.isEmpty()) {
			UUID random = FileUpload.saveCopyFile(file, filePath);
			dto.setUpload(random + "_" + file.getOriginalFilename());
		}
		boardService.updateProcess(dto, filePath);
		return ResponseEntity.ok(null);
	}

	// 삭제
	@DeleteMapping("/board/delete/{num}")
	public ResponseEntity<Object> deleteExecute(@PathVariable("num") int num) {
		boardService.deleteProcess(num, filePath);

		return ResponseEntity.ok(null);
	}

	@GetMapping("board/contentdownload/{filename}")
	public ResponseEntity<Resource> downloadExecute(@PathVariable("filename") String filename) throws IOException {
		// 실제 파일명만 가져오도록 문자로 제거
		String fileName = filename.substring(filename.indexOf("_") + 1);

		// 파일명이 한글일때 인코딩 작업을 한다
		String str = URLEncoder.encode(fileName, "UTF-8");

		// 원본파일명에 공백이 있을 때, "+" 표시가 되므로 공백으로 처리해줌
		str = str.replaceAll("\\+", "%20");

		Path path = Paths.get(filePath + "\\" + filename);
		Resource resource = new InputStreamResource(Files.newInputStream(path));
		log.info("resource:{}", resource);
		log.info(":{}", resource);
		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_TYPE, "application/octet-stream")
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + str + ";")
				.body(resource);
	}

}// class

/*
 * @autowired => <bean id="boardService"
 * class="com.company.shop.board.service.BoardService"> <bean
 * id="boardController" class="com.company.shop.board.controller"> <construct
 * ref="boardService"/> </bean>
 */