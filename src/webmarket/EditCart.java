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




public class EditCart extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	String driver = "com.mysql.jdbc.Driver";
    String dbName = "WebMarket";
    String dbPwd = "EIOE2120110234NKU";
    String dbUserName = "Yuan";
    String url = "jdbc:mysql://128.235.40.165:3306/" + dbName;
    
    String email;
    String productID;
    String CID;
    String date;
    String cartID;
    String level;
    String quantity;
    String sql_1 = "SELECT * FROM CUSTOMER WHERE email = '%1$s'";
    String sql_2_0 = "SELECT CartID FROM CART where CID = '%1$s' and TStatus = 'incomplete'";
    String sql_2 = "INSERT INTO `WebMarket`.`CART` (`TStatus`, `TDate`, `CID`) VALUES ('incomplete', '%1$s', '%2$s');";
    String sql_3 = "select @@IDENTITY;";
    String sql_4 = "select ProductID from PRODUCT;";
    String sql_5_0 = "DELETE FROM `WebMarket`.`APPEAR_IN` WHERE `CartID`='%1$s';";
    String sql_5 = "INSERT INTO `WebMarket`.`APPEAR_IN` (`ProductID`, `CartID`, `Quantity`) VALUES ('%1$s', '%2$s', '%3$s');";
    String username = null;
    String password = null;
    
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
		
		code *= findCID(); //得到CID
		System.out.println(code);
		
        java.text.DateFormat format1 = new java.text.SimpleDateFormat("yyyy-MM-dd");
        date = format1.format(new Date());
        
        System.out.println(date);
        code *= newCart(); //新建Cart, CartID得到
        System.out.println(code);
        
        code *= insertAppearIn(request);
        System.out.println(code);
		
		System.out.println("in doPost");
		PrintWriter out = response.getWriter();
		
		out.println(code);
		email = null;
		cartID = null;
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
	private int newCart(){
		int code = 1;
		try {
			//
	        Class.forName(driver);
	        Connection conn = DriverManager.getConnection(url, dbUserName, dbPwd);
	        
	        PreparedStatement ps1 = conn.prepareStatement(String.format(sql_2_0, CID));
	        ResultSet rs1 = ps1.executeQuery();
	        while (rs1.next()){
	        	cartID = rs1.getString("CartID");
	        }
	        if(ps1 != null){
	        	ps1.close();
	        }
	        if(cartID==null){
		        Statement stmt = conn.createStatement();
		        stmt.executeUpdate(String.format(sql_2, date, CID));
		        if (stmt != null) {
		            try {
		                stmt.close();
		            } catch (SQLException e) {
		                e.printStackTrace();
		            }
		        }
		        PreparedStatement ps = conn.prepareStatement(sql_3);
		        ResultSet rs = ps.executeQuery();
		        while (rs.next()){
		        	cartID = rs.getString("@@IDENTITY");
		        }
		        if (ps != null) {
		            try {
		                ps.close();
		            } catch (SQLException e) {
		                e.printStackTrace();
		            }
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
	        //e.printStackTrace();
	    }
		return code;
	}
	
	

	
	
	private int insertAppearIn (HttpServletRequest request){
		int code = 1;
		try {
			//
	        Class.forName(driver);
	        Connection conn = DriverManager.getConnection(url, dbUserName, dbPwd);
	        
	        PreparedStatement delete = conn.prepareStatement(String.format(sql_5_0, cartID));
	        delete.executeUpdate();
	        
	        
	        PreparedStatement ps = conn.prepareStatement(sql_4);
	        ResultSet rs = ps.executeQuery();
	        while (rs.next()){
	        	productID = rs.getString("ProductID");
	        	String q = request.getParameter(productID);
	        	if(q!=null && Integer.parseInt(q)>0){
	        	    Statement stmt = conn.createStatement();
	        	    stmt.executeUpdate(String.format(sql_5, productID, cartID, q));
	        	    if (stmt != null) {
	        	        try {
	        	            stmt.close();
	        	        } catch (SQLException e) {
	        	            e.printStackTrace();
	        	        }
	        	    }
	        	}
	        }
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