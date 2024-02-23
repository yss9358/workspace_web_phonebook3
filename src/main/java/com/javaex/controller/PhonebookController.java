package com.javaex.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.javaex.dao.PhoneDao;
import com.javaex.util.WebUtil;
import com.javaex.vo.PersonVo;

@WebServlet("/pbc")
public class PhonebookController extends HttpServlet {
	private static final long serialVersionUID = 1L;


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String action = request.getParameter("action");
		
		if("wform".equals(action)) {
			/*System.out.println("wform.등록폼")*/
			WebUtil.forward(request, response, "/WEB-INF/writeForm.jsp");
		} else if("insert".equals(action)) {
			/*System.out.println("insert:등록")*/
			String name = request.getParameter("name");
			String hp = request.getParameter("hp");
			String company = request.getParameter("company");
			
			// 저장을 위해 Vo로 묶어서 사용
			PersonVo personVo = new PersonVo(name, hp, company);
			
			// db관련 업무
			PhoneDao personDao = new PhoneDao();
			
			// db에 저장
			personDao.personInsert(personVo);
			
			WebUtil.redirect(request, response, "/phonebook3/pbc?action=list");
		} else if("delete".equals(action)) {
			/*System.out.println("delete");*/
			int no = Integer.parseInt(request.getParameter("no"));
			
			PhoneDao personDao = new PhoneDao();
			personDao.personDelete(no);
			
			WebUtil.redirect(request, response, "/phonebook3/pbc?action=list");
		} else if("uform".equals(action)) {
			/*System.out.println("uform 수정폼");*/
			int no = Integer.parseInt(request.getParameter("no"));
			
			PhoneDao personDao = new PhoneDao();
			PersonVo personVo = personDao.selectOne(no);
			
			request.setAttribute("personVo", personVo);
			
			WebUtil.forward(request, response, "/WEB-INF/updateForm.jsp");
			
		} else if("update".equals(action)) {
			/*System.out.println("update");*/
			int id = Integer.parseInt(request.getParameter("no"));
			String name = request.getParameter("name");
			String hp = request.getParameter("hp");
			String company = request.getParameter("company");
			PersonVo personVo = new PersonVo(id, name, hp, company);
			
			PhoneDao personDao = new PhoneDao();
			personDao.personUpdate(personVo);
			
			WebUtil.redirect(request, response, "/phonebook3/pbc?action=list");
		} else { // 아무것도 안쳐도 list로 가게끔
			/*System.out.println("list:리스트")*/
			PhoneDao personDao = new PhoneDao();
			List<PersonVo> personList = personDao.personSelect();
			/*System.out.println(personList);*/
			request.setAttribute("personList", personList);
			
			WebUtil.forward(request, response, "/WEB-INF/list.jsp");
		
		}
	} // doGet

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	} // doPost

}
