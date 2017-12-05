package webmarket;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Call extends HttpServlet {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	String number = null;
	
	/**
	 * Constructor of the object.
	 */
	public Call() {
		super();
	}

	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		System.out.println("in doGet");
		doPost(request, response);
	}

	/**
	 * 
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html;charset=GBK");
		request.setCharacterEncoding("GBK");
		number = request.getParameter("number");
		
		System.out.println("call test doPost");
		PrintWriter out = response.getWriter();
		out.println(number);
		number = null;
		out.flush();
		out.close();
	}
	

}
