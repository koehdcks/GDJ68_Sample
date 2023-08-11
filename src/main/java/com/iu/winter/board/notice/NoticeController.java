package com.iu.winter.board.notice;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.iu.main.bankBook.BankBookDTO;
import com.iu.main.board.BoardDTO;
import com.iu.main.util.Pager;

@Controller
@RequestMapping(value="/notice/*")
public class NoticeController {
	
	@Autowired
	private NoticeService noticeService;
	

	// jsp H1의 이름
	// @ModelAttribute : @RequestMapping이 실행되기 전에(list, add, ...) 실행되고, board(키)로 notice(value)를 담아 보낸다.
	@ModelAttribute("board")
	public String getBoardName() {
		return "notice";
	}
	
	// 이미지 업로드 시 : 위지위그
	@PostMapping("setContentsImg")
	public String setContentsImg(MultipartFile files, HttpSession session, Model model)throws Exception{
		System.out.println("setContentsImg");
		System.out.println(files.getOriginalFilename());  
		String path = noticeService.setContentsImg(files, session);
		
		// 결과값 path를 호출한 ajax로 보내준다.
		model.addAttribute("result", path);
		return "commons/ajaxResult";
	}
	// 이미지 삭제 시 : 위지위그
	@PostMapping("setContentsImgDelete")
	public String setContentsImgDelete(String path, HttpSession session, Model model)throws Exception{
		
		boolean check = noticeService.setContentsImgDelete(path, session);
		model.addAttribute("result", check);
		return "commons/ajaxResult";
	}
	
	
	
	@RequestMapping(value="list")
	public String getList(Pager pager, Model model) throws Exception {
		List<BoardDTO> ar = noticeService.getList(pager);

		model.addAttribute("list", ar);
		model.addAttribute("pager", pager);
		
		return "board/list";
	}
	
	@RequestMapping(value="detail", method = RequestMethod.GET)
	public String getDetail(NoticeDTO noticeDTO, Model model) throws Exception {
		// 만들어진 것 : NoticeDTO | 담아오는 것 : BoardDTO
		BoardDTO boardDTO = noticeService.getDetail(noticeDTO);
		
		// boardDTO의 여부에 따라 jsp 선택
		if(boardDTO != null) {
			// jsp에서 실행할 변수의 키(notice)의 이름을 jsp에서 정확하게 작성하자!
			model.addAttribute("dto", boardDTO);
			return "board/detail";
		}else {
			model.addAttribute("message","없는 내용입니다!");
			model.addAttribute("url", "list");
			return "commons/result";
		}
	}

	
	
	// Form Add(insert)
	@RequestMapping(value="add", method = RequestMethod.GET)
	public String setAdd()throws Exception{
		
		return "board/add";
	}
	
	// DB Add(insert)
	@RequestMapping(value="add", method = RequestMethod.POST)
	public String setAdd(NoticeDTO noticeDTO, MultipartFile [] photos, HttpSession session, Model model) throws Exception {
		int result = noticeService.setAdd(noticeDTO, photos, session);
		
		String message = "등록실패";
		if(result>0) {
			message="등록성공";
		}
		
		// 1. 등록 성공여부를 message에 담아 jsp로 보내기
		model.addAttribute("message", message);
		// 2. 등록 시 원하는 url로 이동하기
		model.addAttribute("url", "list");
		// alert 창에 띄울거니 jsp로 값을 보낸다.
		return "commons/result";
	}
	
	
	// Update
	@RequestMapping(value="update", method = RequestMethod.GET)
	public String setUpdate(NoticeDTO noticeDTO, Model model) throws Exception {
		BoardDTO boardDTO = noticeService.getDetail(noticeDTO);
		model.addAttribute("dto", boardDTO);
		
		return "board/update";
	}
	
	@RequestMapping(value="update", method = RequestMethod.POST)
	public String setUpdate(NoticeDTO noticeDTO, MultipartFile [] photos, HttpSession session)throws Exception{
		int result = noticeService.setUpdate(noticeDTO, photos, session);
		
		
		// detail에는 하나의 내용을 보여주기 때문에 URL 자체에 detail?noticeNum=원하는번호(변수=noticeDTO.getNoticeNum())로 해당 상세내용을 본다!!
		return "redirect:./detail?num="+noticeDTO.getNum();
	}
	
	// 수정 중 파일 삭제
	@GetMapping("fileDelete")
	public String setFileDelete(NoticeFileDTO noticeFileDTO,HttpSession session, Model model)throws Exception{
		int result = noticeService.setFileDelete(noticeFileDTO, session);
		model.addAttribute("result", result);
		
		return "commons/ajaxResult";
	}
	
	
	
	// Delete
	@RequestMapping(value="delete", method = RequestMethod.POST)
	public String setDelete(NoticeDTO noticeDTO) throws Exception{
		int result = noticeService.setDelete(noticeDTO);
		
		// redirect로 리턴하지 않으면 해당 num의 삭제된 list만 보여준다.
		return "redirect:./list";
	}
	
	
	
	
	
	
}
