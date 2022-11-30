package controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;

import model.EnrollDAO;

@WebServlet(value={"/enroll/ecoulist.json","/enroll/alist.json","/enroll/check","/enroll/insert","/enroll/delete","/enroll/slist.json","/enroll/update"})
public class EnrollController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	EnrollDAO enrolldao=new EnrollDAO();
    public EnrollController() {
        super();
    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out=response.getWriter();
		
		switch(request.getServletPath()) {
		case "/enroll/ecoulist.json":
			String scode=request.getParameter("scode");
			out.println(enrolldao.ecoulist(scode));
			break;
			
		case "/enroll/alist.json":
			out.println(enrolldao.alist());
			
			break;
			
		case "/enroll/check":
			JSONObject obj=new JSONObject();
			scode=request.getParameter("scode");
			String lcode=request.getParameter("lcode");
			obj.put("count", enrolldao.count(lcode, scode));
			out.println(obj);
			break;
		case "/enroll/slist.json":
			lcode=request.getParameter("lcode");
			out.println(enrolldao.slist(lcode));
			break;
			

		}
		
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("UTF-8");
		String lcode=request.getParameter("lcode");
		String scode=request.getParameter("scode");
		switch(request.getServletPath()) {
		case "/enroll/insert":
			enrolldao.insert(lcode, scode);
			break;
			
		case "/enroll/delete":
			enrolldao.delete(lcode, scode);
			break;
			
		case "/enroll/update":
			int grade=Integer.parseInt(request.getParameter("grade"));
			enrolldao.update(lcode, scode, grade);
			break;
		}
	}

}
