package com.flipperx.ddive;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.flipperx.domain.BoardVO;
import com.flipperx.service.BoardService;

@Controller
@RequestMapping("/board/")
public class BoardController {

	@Inject
	BoardService service;

	// 날짜 타입 데이터 변환
	@InitBinder
	protected void initBinder(WebDataBinder wdb) {
	    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	    wdb.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	}
	
	// 게시물 목록
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public void list(Model model) throws Exception {

		List<BoardVO> list = null;
		list = service.list();

		model.addAttribute("list", list);

	}

	// 게시물 작성
	@RequestMapping(value = "/write", method = RequestMethod.GET)
	public void getWrite() throws Exception {

	}

	// 게시물 작성
	@RequestMapping(value = "/write", method = RequestMethod.POST)
	public String postWrite(BoardVO vo) throws Exception {

		service.write(vo);

		return "redirect:/board/list";

	}

	// 게시물 조회
	@RequestMapping(value = "/view", method = RequestMethod.GET)
	public void getView(@RequestParam("bno") int bno, Model model) throws Exception {

		BoardVO view = null;
		view = service.view(bno);

		model.addAttribute("view", view);
	}

	// 게시물 수정
	@RequestMapping(value = "/update", method = RequestMethod.GET)
	public void getUpdate(@RequestParam("bno") int bno, Model model) throws Exception {
		BoardVO view = null;
		view = service.view(bno);

		model.addAttribute("view", view);
	}

	// 게시물 수정
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String postUpdate(BoardVO vo) throws Exception {

		try {
			service.update(vo);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "redirect:/board/list";
	}
	
	// 게시물 삭제
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public void getDelete(@RequestParam("bno") int bno, Model model) throws Exception {
	  BoardVO view = null;
	  view =  service.view(bno);

	  model.addAttribute("view", view);
	}
	
	// 게시물 삭제
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public String postDelete(@RequestParam("bno") int bno) throws Exception {

	  service.delete(bno);

	  return "redirect:/board/list";
	}

}
