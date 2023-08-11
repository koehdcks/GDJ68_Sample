package com.iu.winter.board.notice;

import java.sql.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.iu.main.board.BoardDTO;
import com.iu.main.file.FileDTO;

public class NoticeDTO extends BoardDTO {
	private List<NoticeFileDTO> fileDTOs;

	public List<NoticeFileDTO> getFileDTOs() {
		return fileDTOs;
	}

	public void setFileDTOs(List<NoticeFileDTO> fileDTOs) {
		this.fileDTOs = fileDTOs;
	}
	
	
}
