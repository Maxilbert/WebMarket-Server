package webmarket;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;

public class GetAddr extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	String driver = "com.mysql.jdbc.Driver";
    String dbName = "WebMarket";
    String dbPwd = "EIOE2120110234NKU";
    String dbUserName = "Yuan";
    String url = "jdbc:mysql://128.235.40.165:3306/" + dbName;
    String sql = "select * from SHIP_ADDRESS WHERE CID in (Select CID from CUSTOMER where CUSTOMER.email='%1$s')"; 
    String email = null;
    
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
		email = request.getParameter("email");
		
		System.out.println("in doPost");
		PrintWriter out = response.getWriter();
		out.println(doQuery());
		email = null;
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
	        	//CreditNumber, C.CardType, C.Email, C.BillingAddress, C.CardOwnerName, C.ExpDate
		        	Map<String,String> gridPoint = new HashMap<String,String>();
		        	gridPoint.put("SAName", rs.getString("SAName"));
		        	gridPoint.put("Street", rs.getString("Street"));
		        	gridPoint.put("City", rs.getString("City"));
		        	gridPoint.put("State", rs.getString("State"));
		        	gridPoint.put("Country", rs.getString("Country"));
		        	gridPoint.put("RecipientName", rs.getString("RecipientName"));
		        	gridPoint.put("ZIP", rs.getString("ZIP"));
		        	gridPoint.put("SNumber", rs.getString("SNumber"));
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
		if (ja.length()!=0){
			return ja.toString();
		} else 
			return "0";
	}
}