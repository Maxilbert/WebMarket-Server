package webmarket;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
public class SaveCard extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	String driver = "com.mysql.jdbc.Driver";
    String dbName = "WebMarket";
    String dbPwd = "EIOE2120110234NKU";
    String dbUserName = "Yuan";
    String url = "jdbc:mysql://128.235.40.165:3306/" + dbName;
    String sql = "replace into `WebMarket`.`CREDIT_CARD` (`CreditNumber`, `SecNumber`, `CardOwnerName`, `Email`, `BillingAddress`) VALUES ('%1$s','%2$s','%3$s','%4$s','%5$s');";
    String sql_1 = "Select CID from CUSTOMER WHERE email = '%1$s'";
    String sql_2 = "replace into `WebMarket`.`STORED_CARD` (`CreditNumber`, `CID`) VALUES ('%1$s','%2$s');";
    private String email;
    private String SecNumber;
    private String CardOwnerName;
    private String ccEmail;
    private String CreditNumber;
    private String BillingAddress;
    private String CID;
    
    
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

		
		System.out.println("in doPost");
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		email = request.getParameter("email");
//	    private String SecNumber;
//	    private String CardOwnerName;
//	    private String ccEmail;
//	    private String CreditNumber;
//	    private String BillingAddress;
		SecNumber = request.getParameter("SecNumber");
		CardOwnerName = request.getParameter("CardOwnerName");
		ccEmail = request.getParameter("ccEmail");
		CreditNumber = request.getParameter("CreditNumber");
		BillingAddress = request.getParameter("BillingAddress");
		email = request.getParameter("email");
		doInsert();
		findCID();
		out.println(doInsert1());
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
	        stmt.executeUpdate(String.format(sql, CreditNumber, SecNumber, CardOwnerName, ccEmail, BillingAddress));
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
	
	
	/**
	 * 
	 * @param username
	 * @return result code
	 */
	private String doInsert1(){
		String result = "1";
		try {
			//
	        Class.forName(driver);
	        Connection conn = DriverManager.getConnection(url, dbUserName, dbPwd);
	        Statement stmt = conn.createStatement();
	        stmt.executeUpdate(String.format(sql_2, CreditNumber, CID));
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
	
	
	
	private int findCID(){
		int code = 1;
		try {
			//
	        Class.forName(driver);
	        Connection conn = DriverManager.getConnection(url, dbUserName, dbPwd);
	        //
	        PreparedStatement ps = conn.prepareStatement(String.format(sql_1, email));
	        ResultSet rs = ps.executeQuery();
	        // 
	        while (rs.next()){
		        	CID = rs.getString("CID");
	        }            
	        // 关闭记录集
	        if (rs != null) {
	            try {
	                rs.close();
	            } catch (SQLException e) {
	                e.printStackTrace();
	            }
	        }
	        // 关闭声明
	        if (ps != null) {
	            try {
	                ps.close();
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
	    	code = 0;
	        e.printStackTrace();
	    }
		return code;
	}
	
}
