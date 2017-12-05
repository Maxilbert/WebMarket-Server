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

public class Manager extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	String driver = "com.mysql.jdbc.Driver";
    String dbName = "WebMarket";
    String dbPwd = "EIOE2120110234NKU";
    String dbUserName = "Yuan";
    String url = "jdbc:mysql://128.235.40.165:3306/" + dbName;
    
    String sql1 = "select A.ProductID, PName, count(*) as cnt from CART C, APPEAR_IN A, PRODUCT P Where C.CartID = A.CartID AND P.ProductID = A.ProductID AND C.TStatus = 'complete'  AND C.TDate between '%1$s' and '%2$s' group by A.ProductID, P.PName order by cnt desc limit 1;";
    String sql2 = "select T.ProductID, P.PName, count(*) from(	select distinct S.CID, A.ProductID	from CUSTOMER S, CART C , APPEAR_IN A	where S.CID = C.CID and C.CartID = A.CartID AND C.TDate between '%1$s' and '%2$s') T,  PRODUCT P where T.ProductID = P.ProductID group by T.ProductID, P.PName order by count(*) desc limit 1;";
    String sql3 = "select C.CID, sum(A.PriceSold * Quantity) as cost from CART C, APPEAR_IN A where C.CartID = A.CartID AND C.TDate between '%1$s' and '%2$s' group by C.CID order by cost desc limit 10";
    String sql4 = "select S.ZIP, count(*) as cnt from CART C, SHIP_ADDRESS S where C.CID = S.CID AND C.TStatus = 'complete' AND C.TDate between '%1$s' and '%2$s' group by S.ZIP order by cnt desc limit 5";
    String sql5 = "select avg (A.PriceSold), P.PType from APPEAR_IN A, PRODUCT P, CART C where A.ProductID = P.ProductID AND C.CartID = A.CartID AND C.TStatus = 'complete' AND C.TDate between '%1$s' and '%2$s' group by P.PType";
    
    String mode;
    String start;
    String end;
    
   
    
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
		
		mode = request.getParameter("mode");
		start = request.getParameter("start");
		end = request.getParameter("end");
		
		System.out.println("in doPost");
		PrintWriter out = response.getWriter();
		out.println(doQuery());
		mode = null;
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
	        PreparedStatement ps =  null; ResultSet rs = null;
	        if(mode.equals("1")){
	        	ps = conn.prepareStatement(String.format(sql1, start, end));
	        	rs = ps.executeQuery();
		        while (rs.next()){
		        	//CreditNumber, C.CardType, C.Email, C.BillingAddress, C.CardOwnerName, C.ExpDate
			        Map<String,String> gridPoint = new HashMap<String,String>();
			        gridPoint.put("ProductID", rs.getString("ProductID"));
			        gridPoint.put("PName", rs.getString("PName"));
			        gridPoint.put("Count", rs.getString("cnt"));
				    ja.put(gridPoint);
		        } 
	        } else if(mode.equals("2")){
	        	ps = conn.prepareStatement(String.format(sql2, start, end));
	        	rs = ps.executeQuery();
		        while (rs.next()){
		        	//CreditNumber, C.CardType, C.Email, C.BillingAddress, C.CardOwnerName, C.ExpDate
			        Map<String,String> gridPoint = new HashMap<String,String>();
			        gridPoint.put("ProductID", rs.getString("ProductID"));
			        gridPoint.put("PName", rs.getString("PName"));
			        gridPoint.put("Count", rs.getString("count(*)"));
				    ja.put(gridPoint);
		        }
	        } else if(mode.equals("3")){
	        	ps = conn.prepareStatement(String.format(sql3, start, end));
	        	rs = ps.executeQuery();
		        while (rs.next()){
		        	//CreditNumber, C.CardType, C.Email, C.BillingAddress, C.CardOwnerName, C.ExpDate
			        Map<String,String> gridPoint = new HashMap<String,String>();
			        gridPoint.put("CID", rs.getString("CID"));
			        gridPoint.put("Cost", rs.getString("cost"));
				    ja.put(gridPoint);
		        }
	        } else if(mode.equals("4")){
	        	ps = conn.prepareStatement(String.format(sql4, start, end));
	        	rs = ps.executeQuery();
		        while (rs.next()){
		        	//CreditNumber, C.CardType, C.Email, C.BillingAddress, C.CardOwnerName, C.ExpDate
			        Map<String,String> gridPoint = new HashMap<String,String>();
			        gridPoint.put("ZIP", rs.getString("ZIP"));
			        gridPoint.put("Count", rs.getString("cnt"));
				    ja.put(gridPoint);
		        }
	        } else if(mode.equals("5")){
	        	ps = conn.prepareStatement(String.format(sql5, start, end));
	        	rs = ps.executeQuery();
		        while (rs.next()){
		        	//CreditNumber, C.CardType, C.Email, C.BillingAddress, C.CardOwnerName, C.ExpDate
			        Map<String,String> gridPoint = new HashMap<String,String>();
			        gridPoint.put("Avg(PriceSold)", rs.getString("avg (A.PriceSold)"));
			        gridPoint.put("PType", rs.getString("PType"));
				    ja.put(gridPoint);
		        }
	        }
	        // 
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