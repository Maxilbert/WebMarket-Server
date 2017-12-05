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




public class GetCart extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	String driver = "com.mysql.jdbc.Driver";
    String dbName = "WebMarket";
    String dbPwd = "EIOE2120110234NKU";
    String dbUserName = "Yuan";
    String url = "jdbc:mysql://128.235.40.165:3306/" + dbName;
    String sql_1 = "select CartID from CUSTOMER, CART where CART.TStatus='incomplete' and CART.CID = CUSTOMER.CID and CUSTOMER.email = '%1$s'";
    String sql_2 = "select P.ProductID, Price, PName, PDescription, A.PriceSold, A.Quantity from PRODUCT P, APPEAR_IN A where P.ProductID = A.ProductID and A.CartID = '%1$s'"; 
    String cartID = null;
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

		System.out.println("in doPost");
		PrintWriter out = response.getWriter();
		
		email = request.getParameter("email");
		System.out.println(email);
		findCartID();
		
		if(cartID==null || cartID.equals("")){
			out.print("2");
		} else {
			out.println(doQuery());
		}
		email = null;
		cartID = null;
		out.flush();
		out.close();
	}
	
	
	private void findCartID(){
		try {
			//
	        Class.forName(driver);
	        Connection conn = DriverManager.getConnection(url, dbUserName, dbPwd);
	        //
	        PreparedStatement ps = conn.prepareStatement(String.format(sql_1, email));
	        ResultSet rs = ps.executeQuery();
	        // 
	        while (rs.next()){
	        	cartID = rs.getString("CartID");
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
	        PreparedStatement ps = conn.prepareStatement(String.format(sql_2,cartID));
	        ResultSet rs = ps.executeQuery();
	        // 
	        while (rs.next()){
	        	//ProductID, Price, PName, PDescription, A.PriceSold, A.Quantity
		        	Map<String,String> gridPoint = new HashMap<String,String>();
		        	gridPoint.put("ProductID", rs.getString("ProductID"));
		        	gridPoint.put("PName", rs.getString("PName"));
		        	gridPoint.put("Price", rs.getString("Price"));
		        	gridPoint.put("Quantity", rs.getString("Quantity"));
		        	gridPoint.put("PDescription", rs.getString("PDescription"));
		        	gridPoint.put("PriceSold", rs.getString("PriceSold"));
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