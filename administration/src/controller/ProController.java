package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.ProDAO;
import model.ProVO;
import model.SqlVO;

@WebServlet(value={"/pro/slist.json","/pro/clist.json","/pro/list","/pro/list.json","/pro/insert","/pro/read","/pro/update","/pro/count","/pro/delete"})
public class ProController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	ProDAO prodao=new ProDAO();
	SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
	
    public ProController() {
        super();
    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher dis=request.getRequestDispatcher("/home.jsp");
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out=response.getWriter();
		
		
		switch(request.getServletPath()) {
		case "/pro/list":
			request.setAttribute("pageName", "/pro/list.jsp");
			dis.forward(request, response);
			break;
		case "/pro/list.json":
			SqlVO sqlvo=new SqlVO();
			sqlvo.setDesc(request.getParameter("desc"));
			sqlvo.setKey(request.getParameter("key"));
			sqlvo.setOrder(request.getParameter("order"));
			sqlvo.setPage(Integer.parseInt(request.getParameter("page")));
			sqlvo.setPer(Integer.parseInt(request.getParameter("per")));
			sqlvo.setWord(request.getParameter("word"));
			
			out.println(prodao.list(sqlvo));
			
			break;
		case "/pro/slist.json":
			String pcode=request.getParameter("pcode");
			out.println(prodao.slist(pcode));
			break;
			
		case "/pro/clist.json":
			pcode=request.getParameter("pcode");
			out.println(prodao.clist(pcode));
			break;
		case "/pro/insert":
			request.setAttribute("pageName", "/pro/insert.jsp");
			request.setAttribute("code", prodao.getCode());
			request.setAttribute("now", sdf.format(new Date()));
			dis.forward(request, response);
			break;
			
			
		case "/pro/read":
			pcode=request.getParameter("pcode");
			request.setAttribute("provo", prodao.read(pcode));
			request.setAttribute("pageName", "/pro/read.jsp");
			dis.forward(request, response);
			break;
			
		case "/pro/count":
			pcode=request.getParameter("pcode");
			out.println(prodao.count(pcode));
			break;
		}
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");

		
		switch(request.getServletPath()) {
		case "/pro/insert":
			ProVO provo=new ProVO();
			provo.setDept(request.getParameter("dept"));
			provo.setHiredate(request.getParameter("hiredate"));
			provo.setPcode(request.getParameter("pcode"));
			provo.setPname(request.getParameter("pname"));
			int salary=request.getParameter("salary")=="" ? 0: Integer.parseInt(request.getParameter("salary"));
			provo.setSalary(salary);
			provo.setTitle(request.getParameter("title"));
			prodao.insert(provo);
			response.sendRedirect("/pro/list");
			break;
			
		case "/pro/update":
			provo=new ProVO();
			provo.setDept(request.getParameter("dept"));
			provo.setHiredate(request.getParameter("hiredate"));
			provo.setPcode(request.getParameter("pcode"));
			provo.setPname(request.getParameter("pname"));
			salary=request.getParameter("salary")=="" ? 0: Integer.parseInt(request.getParameter("salary"));
			provo.setSalary(salary);
			provo.setTitle(request.getParameter("title"));
			prodao.update(provo);
			response.sendRedirect("/pro/list");
			break;
			
		case "/pro/delete":
			String sab=request.getParameter("pcode");
			prodao.delete(sab);
			response.sendRedirect("/pro/list");
			break;
			
		
		}

	}

}
