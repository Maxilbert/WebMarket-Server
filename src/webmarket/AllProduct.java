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




public class AllProduct extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	String driver = "com.mysql.jdbc.Driver";
    String dbName = "WebMarket";
    String dbPwd = "EIOE2120110234NKU";
    String dbUserName = "Yuan";
    String url = "jdbc:mysql://128.235.40.165:3306/" + dbName;
    String sql = "select P.ProductID, PName, Price, PType, PQuantity, PDescription, OfferPrice from PRODUCT P left join OFFER_PRODUCT OP on P.ProductID = OP.ProductID;"; 
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

		response.setContentType("text/html;charset=GBK");
		request.setCharacterEncoding("GBK");
		
		System.out.println("in doPost");
		PrintWriter out = response.getWriter();
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
	        PreparedStatement ps = conn.prepareStatement(sql);
	        ResultSet rs = ps.executeQuery();
	        // 
	        while (rs.next()){
	        	//P.ProductID, PName, Price, PType, PQuantity, PDescription, OfferPrice
		        	Map<String,String> gridPoint = new HashMap<String,String>();
		        	gridPoint.put("ProductID", rs.getString("ProductID"));
		        	gridPoint.put("PName", rs.getString("PName"));
		        	gridPoint.put("Price", rs.getString("Price"));
		        	gridPoint.put("PType", rs.getString("PType"));
		        	gridPoint.put("PQuantity", rs.getString("PQuantity"));
		        	gridPoint.put("PDescription", rs.getString("PDescription"));
		        	gridPoint.put("OfferPrice", rs.getString("OfferPrice"));
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