package com.iu.winter.bankBook;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.iu.main.util.FileManager;
import com.iu.main.util.Pager;
import com.iu.winter.bankBook.comment.CommentDTO;


@Service 
public class BankBookService {
	@Autowired //BankBookDAO 타입으로 만든 객체 찾아서 bankBookDAO에 대신 넣어달라는 것, 의존성주입
	private BankBookDAO bankBookDAO;
	
	@Autowired
	private FileManager fileManager;
	
	public List<CommentDTO> getCommentList(Pager pager, CommentDTO commentDTO)throws Exception{
		Map<String, Object> map = new HashMap<String, Object>();
		pager.makeRowNum();
		pager.makePageNum(bankBookDAO.getCommentTotal(commentDTO));
		map.put("pager", pager);
		map.put("comment", commentDTO);
		
		return bankBookDAO.getCommentList(map);
	}
	
	public int setCommentAdd(CommentDTO commentDTO)throws Exception{
		return bankBookDAO.setCommentAdd(commentDTO);
	}
	
	public List<BankBookDTO> getList(Pager pager) throws Exception{	
		Map<String, Integer> map = new HashMap<String, Integer>();
		//page		startRow		lastRow
		//  1 			1				10
		//	2			11				20
		//	3			21				30
		
//		int count = 10;
//		int startRow = (page-1)*count+1;
//		int lastRow = page*count;

//		map.put("startRow", startRow);
//		map.put("lastRow", lastRow);

		pager.makeRowNum();
		Long total = bankBookDAO.getTotal(pager); 
		pager.makePageNum(total);
		
		return bankBookDAO.getList(pager);
	}
	
	public BankBookDTO getDetail(BankBookDTO bankBookDTO)throws Exception{
		return bankBookDAO.getDetail(bankBookDTO);
	}
	
	public int setAdd(BankBookDTO bankBookDTO, MultipartFile[] files, HttpSession session)throws Exception{
		// /resources/upload/bankbook 폴더에 이미지 저장
	String path = "/resources/upload/bankbook/";
	
	//long num = bankBookDAO.getSequence();
	
	//bankBookDTO.setBookNum(num);
	
	int result = bankBookDAO.setAdd(bankBookDTO);
	
	for(MultipartFile multipartFile: files) {
		
		if(multipartFile.isEmpty()) {
			continue;
		}
		
		String fileName = fileManager.fileSave(path, multipartFile, session);
		BankBookFileDTO bankBookFileDTO = new BankBookFileDTO();
		bankBookFileDTO.setOriginalName(multipartFile.getOriginalFilename());
		bankBookFileDTO.setFileName(fileName);
		bankBookFileDTO.setBookNum(bankBookDTO.getBookNum());
		result = bankBookDAO.setFileAdd(bankBookFileDTO);
	}
		
		return result;
	}
	
	public int setDelete(Long num)throws Exception{
		return bankBookDAO.setDelete(num);
	}
	
	public int setUpdate(BankBookDTO bankBookDTO) throws Exception{
		return bankBookDAO.setUpdate(bankBookDTO);
	}
	
}
