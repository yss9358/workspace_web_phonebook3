package com.javaex.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.javaex.dao.PhoneDao;
import com.javaex.vo.PersonVo;

@WebServlet("/pbc")
public class PhonebookController extends HttpServlet {
	private static final long serialVersionUID = 1L;


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String action = request.getParameter("action");
		
		if("wform".equals(action)) {
			// jsp한테 html그리기 응답 -> 포워드
			RequestDispatcher rd = request.getRequestDispatcher("/writeForm.jsp");
			rd.forward(request, response);
		} else if("insert".equals(action)) {
			String name = request.getParameter("name");
			String hp = request.getParameter("hp");
			String company = request.getParameter("company");
			
			// 저장을 위해 Vo로 묶어서 사용
			PersonVo personVo = new PersonVo(name, hp, company);
			
			// db관련 업무
			PhoneDao personDao = new PhoneDao();
			// db에 저장
			personDao.personInsert(personVo);
			
			//db에서 전체 데이터 가져오기
			List<PersonVo> personList = personDao.personSelect();
			// request에 담기
			request.setAttribute("personList", personList);
			// 포워드
			RequestDispatcher rd = request.getRequestDispatcher("/list.jsp");
			rd.forward(request, response);
		} else if("list".equals(action)) {
			PhoneDao personDao = new PhoneDao();
			personDao.personSelect();
			List<PersonVo> personList = personDao.personSelect();
			request.setAttribute("personList", personList);
			RequestDispatcher rd = request.getRequestDispatcher("/list.jsp");
			rd.forward(request, response);
			
		}
	} // doGet

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	} // doPost

}
