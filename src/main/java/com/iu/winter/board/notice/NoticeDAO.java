package com.iu.winter.board.notice;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import com.iu.main.board.BoardDAO;
import com.iu.main.board.BoardDTO;
import com.iu.main.util.Pager;


@Repository
public class NoticeDAO implements BoardDAO{
	
	@Autowired
	private SqlSession sqlSession;
	
	private final String NAMESPACE="com.iu.main.board.notice.NoticeDAO.";
	
	
	//total | Service 받을 때 Long 타입
	@Override
	public Long getTotal(Pager pager) throws Exception{
		return sqlSession.selectOne(NAMESPACE+"getTotal", pager);
	}	
	
	// List	
	@Override
	public List<BoardDTO> getList(Pager pager) throws Exception {
		// TODO Auto-generated method stub
		return sqlSession.selectList(NAMESPACE+"getList", pager);
	}
	

	// Detail
	@Override
	public NoticeDTO getDetail(BoardDTO boardDTO) throws Exception{
		return sqlSession.selectOne(NAMESPACE+"getDetail", boardDTO);
	}
	
	// Add(insert)
	@Override
	public int setAdd(BoardDTO boardDTO) throws Exception{
		return sqlSession.insert(NAMESPACE+"setAdd", boardDTO);
	}
	
	// file Add
	public int setFileAdd(NoticeFileDTO noticeFileDTO) {
		return sqlSession.insert(NAMESPACE+"setFileAdd",noticeFileDTO);
	}
	
	//update
	@Override
	public int setUpdate(BoardDTO boardDTO) throws Exception{
		return sqlSession.update(NAMESPACE+"setUpdate", boardDTO);
	}
	// 수정 중 파일 삭제
	// 1. 폴더 파일 삭제
	public NoticeFileDTO getFileDetail(NoticeFileDTO noticeFileDTO)throws Exception{
		return sqlSession.selectOne(NAMESPACE+"getFileDetail", noticeFileDTO);
	}
	// 2. DB 삭제
	public int setFileDelete(NoticeFileDTO noticeFileDTO)throws Exception{
		return sqlSession.delete(NAMESPACE+"setFileDelete", noticeFileDTO);
	}
	
	// Delete
	@Override
	public int setDelete(BoardDTO boardDTO) throws Exception{
		return sqlSession.delete(NAMESPACE+"setDelete", boardDTO);
	}
	
	// 조회수 업데이트
	@Override
	public int setHitUpdate(BoardDTO boardDTO) throws Exception {
		return 0;
	}
}
