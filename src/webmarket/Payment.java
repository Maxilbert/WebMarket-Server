package webmarket;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;




public class Payment extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	String driver = "com.mysql.jdbc.Driver";
    String dbName = "WebMarket";
    String dbPwd = "EIOE2120110234NKU";
    String dbUserName = "Yuan";
    String url = "jdbc:mysql://128.235.40.165:3306/" + dbName;
    
    String date;
    String email;
    String CID;
    String creditNumber;
    String sql_1 = "SELECT * FROM CUSTOMER WHERE email = '%1$s'";
    String sql_2 = "UPDATE `WebMarket`.`CART` SET `TStatus`='complete', `CreditNumber`='%1$s', `TDate`='%2$s' WHERE `TStatus`='incomplete' and `CID`='%3$s';";
    
    /**
     * 
     */
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
		
		int code = 1;
		
		response.setContentType("text/html;charset=GBK");
		request.setCharacterEncoding("GBK");
		
		
		email = request.getParameter("email");
		creditNumber = request.getParameter("CreditNumber");
		
		code *= findCID(); //得到CID
		System.out.println(code);
		
        java.text.DateFormat format1 = new java.text.SimpleDateFormat("yyyy-MM-dd");
        date = format1.format(new Date());
        
        System.out.println(date);
        code *= completeCart(); //新建Cart, CartID得到
        System.out.println(code);
        		
		System.out.println("in doPost");
		PrintWriter out = response.getWriter();
		
		out.println(code);
		
		email = null;
		out.flush();
		out.close();
	}
	
	
	/**
	 * 
	 * @param username
	 * @return result code
	 */
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
	
	
	/**
	 * 
	 * @param username
	 * @return result code
	 */
	private int completeCart(){
		int code = 1;
		try {
			//
	        Class.forName(driver);
	        Connection conn = DriverManager.getConnection(url, dbUserName, dbPwd);
	        
	        PreparedStatement ps = conn.prepareStatement(String.format(sql_2, creditNumber, date, CID));
	        ps.executeUpdate();
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