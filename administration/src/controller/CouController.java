package controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.CouDAO;
import model.CouVO;
import model.SqlVO;

@WebServlet(value={"/cou/insert","/cou/list","/cou/list.json","/cou/read"})
public class CouController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	CouDAO coudao =new CouDAO();
	public CouController() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher dis=request.getRequestDispatcher("/home.jsp");
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out=response.getWriter();

		switch(request.getServletPath()) {
		case "/cou/list.json":
			SqlVO sqlvo=new SqlVO();
			sqlvo.setDesc(request.getParameter("desc"));
			sqlvo.setKey(request.getParameter("key"));
			sqlvo.setOrder(request.getParameter("order"));
			sqlvo.setPage(Integer.parseInt(request.getParameter("page")));
			sqlvo.setPer(Integer.parseInt(request.getParameter("per")));
			sqlvo.setWord(request.getParameter("word"));

			out.println(coudao.list(sqlvo));

			break;

		case "/cou/list":
			request.setAttribute("pageName", "/cou/list.jsp");
			dis.forward(request, response);
			break;
		case "/cou/read":
			String lcode=request.getParameter("lcode");
			request.setAttribute("couvo", coudao.read(lcode));
			request.setAttribute("pageName", "/cou/read.jsp");
			dis.forward(request, response);
			break;
		case "/cou/insert":
			request.setAttribute("pageName", "/cou/insert.jsp");
			request.setAttribute("code", coudao.getCode());
			dis.forward(request, response);
			break;



		}


	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");

		switch(request.getServletPath()) {
		case "/cou/insert":
			CouVO couvo=new CouVO();
			couvo.setCapacity(Integer.parseInt(request.getParameter("capacity")));
			couvo.setDept(request.getParameter("dept"));
			couvo.setInstructor(request.getParameter("instructor"));
			couvo.setLcode(request.getParameter("lcode"));
			couvo.setLname(request.getParameter("lname"));
			couvo.setRoom(request.getParameter("room"));
			couvo.setHours(Integer.parseInt(request.getParameter("hours")));
			coudao.insert(couvo);
			response.sendRedirect("/cou/list");

			break;
		}


	}

}
