package com.iu.winter.board.qna;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.iu.main.board.BoardDTO;
import com.iu.main.board.BoardService;
import com.iu.main.util.FileManager;
import com.iu.main.util.Pager;

@Service
public class QnaService implements BoardService {
	@Autowired
	private QnaDAO qnaDAO;
	@Autowired
	private FileManager fileManager;
	
	public List<BoardDTO> getList(Pager pager) throws Exception{
		pager.makeRowNum();
		Long total = qnaDAO.getTotal(pager);
		pager.makePageNum(total);
		
		return qnaDAO.getList(pager);
	}
	
	public BoardDTO getDetail(BoardDTO boardDTO) throws Exception{
		return qnaDAO.getDetail(boardDTO);
	}

	@Override
	public int setAdd(BoardDTO boardDTO, MultipartFile[] files, HttpSession session) throws Exception {
		int result = qnaDAO.setAdd(boardDTO);
		
		if(files.length!=0) {
			for(int i=0;i<files.length;i++) {
				String fileName = fileManager.fileSave("qna", files[i], session);
				QnaFileDTO qnaFileDTO = new QnaFileDTO();
				qnaFileDTO.setOriginalName(files[i].getOriginalFilename());
				qnaFileDTO.setFileName(fileName);
				qnaFileDTO.setNum(boardDTO.getNum());
				result = qnaDAO.setFileAdd(qnaFileDTO);
			}
		}
		
		return result;
	}

	@Override
	public int setUpdate(BoardDTO boardDTO,MultipartFile[] files, HttpSession session) throws Exception {
		
		return qnaDAO.setUpdate(boardDTO);
	}

	@Override
	public int setDelete(BoardDTO boardDTO) throws Exception {
		
		return qnaDAO.setDelete(boardDTO);
	}
	
	public int setReplyAdd(QnaDTO qnaDTO,MultipartFile[] files,HttpSession session) throws Exception{
		BoardDTO parentDTO = new QnaDTO();
		parentDTO.setNum(qnaDTO.getNum());
		
		parentDTO = qnaDAO.getDetail(parentDTO);
		QnaDTO p = (QnaDTO)parentDTO;
		qnaDTO.setRef(p.getRef());
		qnaDTO.setStep(p.getStep()+1);
		qnaDTO.setDepth(p.getDepth()+1);
		
		int result = qnaDAO.setStepUpdate(p);
		
		result = qnaDAO.setReplyAdd(qnaDTO);
		
		return result;
	}
	
	
}
