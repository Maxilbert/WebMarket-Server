package webmarket;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;

public class GetProfile extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
    String driver = "com.mysql.jdbc.Driver";
    String dbName = "WebMarket";
    String dbPwd = "EIOE2120110234NKU";
    String dbUserName = "Yuan";
    String url = "jdbc:mysql://128.235.40.165:3306/" + dbName;
    String sql = "select * from CUSTOMER where email='%s'";
    
    String email = null;
    String fName = null;
    String lName = null;
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

		response.setContentType("text/html;charset=GBK");
		request.setCharacterEncoding("GBK");
		
//		System.out.println("in doPost");
		PrintWriter out = response.getWriter();
		email = request.getParameter("email");
		password = request.getParameter("password");
		System.out.println(email);
		System.out.println(password);
		out.println(doQuery());
		out.flush();
		out.close();
	}
	
	
	/**
	 * 
	 * @param username
	 * @return result code
	 */
	private String doQuery(){
		JSONArray ja = new JSONArray();
		try {
			//
	        Class.forName(driver);
	        Connection conn = DriverManager.getConnection(url, dbUserName, dbPwd);
	        //
	        PreparedStatement ps = conn.prepareStatement(String.format(sql, email));
	        ResultSet rs = ps.executeQuery();
	        //
	        while (rs.next()){
	        	//ProductID, Price, PName, PDescription, A.PriceSold, A.Quantity
		        	Map<String,String> gridPoint = new HashMap<String,String>();
		        	gridPoint.put("Status", rs.getString("Status"));
		        	gridPoint.put("Address", rs.getString("Address"));
		        	gridPoint.put("FName", rs.getString("FName"));
		        	gridPoint.put("LName", rs.getString("LName"));
		        	gridPoint.put("Phone", rs.getString("Phone"));
			        ja.put(gridPoint);
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
	        e.printStackTrace();
	    }
		if (ja.length()!=0)
			return ja.toString();
		else 
			return "0";
	}

}