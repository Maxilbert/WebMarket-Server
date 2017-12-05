package webmarket;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * 
 * @author Maxilbert
 *
 */
public class Register extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	String driver = "com.mysql.jdbc.Driver";
    String dbName = "WebMarket";
    String dbPwd = "EIOE2120110234NKU";
    String dbUserName = "Yuan";
    String url = "jdbc:mysql://128.235.40.165:3306/" + dbName;
    String sql = "insert into CUSTOMER (email, password, fname, lname, phone, address) values ('%1$s','%2$s','%3$s','%4$s','%5$s','%6$s')";
    
    String email = null;
    String lName = null;
    String fName = null;
    String phone = null;
    String address = null;
    String password = null;
    
    
	/**
	 * The doGet method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		System.out.println("in doGet");
		doPost(request, response);
	}

	
	/**
	 * The doPost method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to post.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		email = request.getParameter("email");
		fName = request.getParameter("fName");
		lName = request.getParameter("lName");
		phone = request.getParameter("phone");
		address = request.getParameter("address");
		password = request.getParameter("password");
		if ((!email.equals("")) && (!password.equals(""))) {
			out.println(doInsert());
		} else {
			out.println("0");
		}
		out.flush();
		out.close();
	}


	/**
	 * 
	 * @param username
	 * @return result code
	 */
	private String doInsert(){
		String result = "1";
		try {
			//
	        Class.forName(driver);
	        Connection conn = DriverManager.getConnection(url, dbUserName, dbPwd);
	        Statement stmt = conn.createStatement();
	        stmt.executeUpdate(String.format(sql, email, password, fName, lName, phone, address));
	        //email, password, fname, lname, phone, address
	        if (stmt != null) {
	            try {
	                stmt.close();
	            } catch (SQLException e) {
	                e.printStackTrace();
	            }
	        }
	        // 关闭链接对象	        
	        if (conn != null) {
	            try {
	                conn.close();
	            } catch (SQLException e) {
	                e.printStackTrace();
	            }
	        }
	    } catch (Exception e) {
	        result = "0";
	        return result;
	        //e.printStackTrace();
	    }
		return result;
	}
	
}
