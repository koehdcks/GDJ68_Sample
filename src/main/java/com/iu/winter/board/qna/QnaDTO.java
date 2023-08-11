package com.iu.winter.board.qna;

import java.util.List;

import com.iu.main.board.BoardDTO;

public class QnaDTO extends BoardDTO {

	private Long ref;
	private Long step;
	private Long depth;
	private List<QnaFileDTO> fileDTOs;
	
	
	
	
	public List<QnaFileDTO> getFileDTOs() {
		return fileDTOs;
	}
	public void setFileDTOs(List<QnaFileDTO> fileDTOs) {
		this.fileDTOs = fileDTOs;
	}
	public Long getRef() {
		return ref;
	}
	public Long getStep() {
		return step;
	}
	public Long getDepth() {
		return depth;
	}
	public void setRef(Long ref) {
		this.ref = ref;
	}
	public void setStep(Long step) {
		this.step = step;
	}
	public void setDepth(Long depth) {
		this.depth = depth;
	}
	
	
	
	

	
	
	
}
