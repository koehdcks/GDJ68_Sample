package com.iu.winter.bankBook;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.iu.main.member.MemberDTO;
import com.iu.main.util.Pager;
import com.iu.winter.bankBook.comment.CommentDTO;

import oracle.jdbc.proxy.annotation.Post;

@Controller
				//하나만 들어가는 것은()에 value 안써줘도 됨
@RequestMapping("/bankbook/*")//root밑에 bankbook으로 시작하는 것은 다 여기로 오면 된다는 뜻
public class BankBookController {
	
	@Autowired //BankBookService에 의존적
	private BankBookService bankBookService;
	
	//--- comment
		@GetMapping("commentList")
		public void getCommentList(CommentDTO commentDTO, Pager pager, Model model)throws Exception{
			pager.setPerPage(2L);
			List<CommentDTO> ar = bankBookService.getCommentList(pager, commentDTO);
			model.addAttribute("commentList", ar);
		}
		
		@PostMapping("commentAdd")
		public String setCommentAdd(CommentDTO commentDTO, HttpSession session, Model model)throws Exception{
			MemberDTO memberDTO = (MemberDTO)session.getAttribute("member"); 
			commentDTO.setId(memberDTO.getId());
			int result = bankBookService.setCommentAdd(commentDTO);
			model.addAttribute("result", result);
			return "commons/ajaxResult";
		}
	
	//클래스.상수 (안쓰면 기본이 GET)
	@RequestMapping(value="list", method = RequestMethod.GET) // 설명과 ()와 같이 실행해달라는 뜻
	public String getList(Pager pager, Model model) throws Exception {
		List<BankBookDTO> ar = bankBookService.getList(pager);
		model.addAttribute("list", ar);
		model.addAttribute("pager", pager);
		return "bankbook/list"; 
	}
	
	@RequestMapping(value="detail")
	public ModelAndView getDetail(BankBookDTO bankBookDTO, ModelAndView mv) throws Exception {
		bankBookDTO = bankBookService.getDetail(bankBookDTO);
		System.out.println(bankBookDTO.getBookName());
		mv.addObject("dto", bankBookDTO);
		mv.setViewName("bankbook/detail");
		return mv;
	}
	//form으로 이동
	@RequestMapping(value="add", method = RequestMethod.GET)
	public void setAdd() throws Exception {
		
	}
	//DB에 insert
	
	@RequestMapping(value="add", method = RequestMethod.POST)
	public String setAdd(BankBookDTO bankBookDTO, MultipartFile[] photos, HttpSession session)throws Exception{
		int result = bankBookService.setAdd(bankBookDTO, photos, session);
		return "redirect:./list";
	}
	
	
	//수정form
		@RequestMapping(value = "update", method = RequestMethod.GET)
		public ModelAndView setUpdate(BankBookDTO bankBookDTO,Model model)throws Exception{
//			bankBookDTO = bankBookService.getDetail(bankBookDTO);
//			model.addAttribute("dto", bankBookDTO);
			ModelAndView mv = new ModelAndView();
			mv.setViewName("bankBook/update");
			mv.addObject("dto", bankBookDTO);
			return mv;
		}
		
		//update
		@RequestMapping(value = "update", method = RequestMethod.POST)
		public String setUpdate(BankBookDTO bankBookDTO)throws Exception{
			int result = bankBookService.setUpdate(bankBookDTO);
			//return "redirect:./list";
			return "redirect:./detail?bookNum="+bankBookDTO.getBookNum();
		}
	
		@RequestMapping(value="delete", method = RequestMethod.GET)
		public String setDelete(Long bookNum)throws Exception{
			int result = bankBookService.setDelete(bookNum);
			return "redirect:./list";
		}
}
