package com.company.shop.board.service;

import java.util.List;

import com.company.shop.board.dto.BoardDTO;
import com.company.shop.board.dto.PageDTO;

public interface BoardService {
	public long countProcess(); 
	public List<BoardDTO> listProcess(PageDTO pv);
	public void insertProcess(BoardDTO dto);
	public BoardDTO contentProcess(long num);
	public void updateProcess(BoardDTO dto, String urlpath);
	public void deleteProcess(long num, String urlpath);
	
}
