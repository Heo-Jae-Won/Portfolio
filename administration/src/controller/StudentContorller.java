package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.SqlVO;
import model.StuDAO;
import model.StuVO;
@WebServlet(value={"/stu/list","/stu/list.json","/stu/insert","/stu/read","/stu/update"})
public class StudentContorller extends HttpServlet {

	private static final long serialVersionUID = 1L;
	StuDAO studao=new StuDAO();
	SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
	public StudentContorller() {
		super();
	}
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher dis=request.getRequestDispatcher("/home.jsp");
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out=response.getWriter();



		switch(request.getServletPath()) {
		case "/stu/list.json":
			SqlVO sqlvo=new SqlVO();
			sqlvo.setDesc(request.getParameter("desc"));
			sqlvo.setKey(request.getParameter("key"));
			sqlvo.setOrder(request.getParameter("order"));
			sqlvo.setPage(Integer.parseInt(request.getParameter("page")));
			sqlvo.setPer(Integer.parseInt(request.getParameter("per")));
			sqlvo.setWord(request.getParameter("word"));

			out.println(studao.list(sqlvo));

			break;

		case "/stu/list":
			request.setAttribute("pageName", "/stu/list.jsp");
			dis.forward(request, response);
			break;
		case "/stu/insert":
			request.setAttribute("pageName", "/stu/insert.jsp");
			request.setAttribute("code", studao.getCode());
			request.setAttribute("birthday", "2003-02-01");
			dis.forward(request, response);
			break;

		case "/stu/read":
			String scode=request.getParameter("scode");
			request.setAttribute("stuvo", studao.read(scode));
			request.setAttribute("pageName", "/stu/read.jsp");

			dis.forward(request, response);
			break;


		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		StuVO stuvo=new StuVO();
		stuvo.setAdvisor(request.getParameter("advisor"));
		stuvo.setScode(request.getParameter("scode"));
		stuvo.setSname(request.getParameter("sname"));
		stuvo.setDept(request.getParameter("dept"));
		stuvo.setBirthday(request.getParameter("birthday"));
		stuvo.setYear(request.getParameter("year"));

		switch(request.getServletPath()) {
		case "/stu/insert":

			studao.insert(stuvo);
			response.sendRedirect("/stu/list");
			break;

		case "/stu/update":
			studao.update(stuvo);
			response.sendRedirect("/stu/list");
			break;
		}


	}

}
