import java.sql.*;
import java.util.ArrayList;
public class test{ 
	// 定义数据库驱动程序 
	
	public static void main(String args[]) throws Exception { // 所有异常抛出 
		ArrayList<Shape> data = new ArrayList<Shape>();
		data = getregion();
		for(int k = 0; k < data.size();k++){
			for(int i = 0; i < 4 && k < 7; i++){
	    			System.out.println(data.get(k).id+""+" "+data.get(k).points[i][0]+""+" "+data.get(k).points[i][1]);
	    	}
			
		}
	}
	public static Connection ConnectOracleXE()throws Exception{
		String DBDRIVER = "oracle.jdbc.driver.OracleDriver" ; 
		// 定义数据库的连接地址 
		String DBURL = "jdbc:oracle:thin:@localhost:1521:XE" ; 
		//端口号后标识符可以通过在doc下运行lsnrctl status查看 default:1521
		// 数据库的连接用户名 
		String DBUSER = "system" ; 
		// 数据库的连接密码 
		String DBPASS = "admin" ; 
		Connection conn = null ; // 数据库连接 
		Class.forName(DBDRIVER) ; // 加载驱动程序 
		conn = DriverManager.getConnection(DBURL,DBUSER,DBPASS) ;
		if(conn != null)
			return conn;  
		else
			return null;
	}
	public static ArrayList<Shape> getregion() throws Exception{
		ArrayList<Shape> res = new ArrayList<Shape>();
		Connection conn = ConnectOracleXE();
		Statement stat = null;
		ResultSet rs = null;  
		stat = conn.createStatement();  
	    rs = stat.executeQuery("select * from region_b");
	    while (rs.next()) {
	    	Shape temp = new region();
	    	temp.id = rs.getString("region_id");
	    	String mediate = rs.getString("coordinate");
	    	mediate = mediate.trim();
	    	String[] point = mediate.split(",");
	    	int k = 0;
	    	for(int i = 0; i < 4 && k < point.length; i++){
	    		for(int j = 0; j < 2 && k < point.length; j++){
	    			temp.points[i][j] = Integer.parseInt(point[k]);
	    			k++;
	    		}
	    	}
	    	res.add(temp);
	     }  
		return res;
	}
	public static ArrayList<Shape> getlion() throws Exception{
		ArrayList<Shape> res = new ArrayList<Shape>();
		Connection conn = ConnectOracleXE();
		Statement stat = null;
		ResultSet rs = null;  
		stat = conn.createStatement();  
	    rs = stat.executeQuery("select * from lion_b");
	    while (rs.next()) {
	    	lion temp = new lion();
	    	temp.id = rs.getString("lion_id");
	    	temp.x = Integer.parseInt(rs.getString("point_x"));
	    	temp.y = Integer.parseInt(rs.getString("point_y"));
	    	res.add(temp);
	     }  
		return res;
	}
	public static ArrayList<Shape> getpond() throws Exception{
		ArrayList<Shape> res = new ArrayList<Shape>();
		Connection conn = ConnectOracleXE();
		Statement stat = null;
		ResultSet rs = null;  
		stat = conn.createStatement();  
	    rs = stat.executeQuery("select * from pond_b");
	    while (rs.next()) {
	    	pond temp = new pond();
	    	temp.id = rs.getString("pond_id");
	    	temp.x = Integer.parseInt(rs.getString("center_x"));
	    	temp.y = Integer.parseInt(rs.getString("center_y"));
	    	temp.radius = Integer.parseInt(rs.getString("radius"));
	    	res.add(temp);
	     }  
		return res;
	}
	public static String getRegionID(int x, int y) throws Exception{
		Connection conn = ConnectOracleXE();
		Statement stat = null;
		ResultSet rs = null;
		stat = conn.createStatement();  
	    rs = stat.executeQuery("select * from region c where SDO_CONTAINS(c.AREA,SDO_GEOMETRY(2001,NULL,SDO_POINT_TYPE("+x+","+y+", NULL),NULL,NULL))='TRUE'");
	    rs.next();
	    String res = rs.getString(1);
	    return res;
	    
	}
	public static ArrayList<Shape> getInsideLion(String region_id) throws Exception{
		ArrayList<Shape> res = new ArrayList<Shape>();
		ArrayList<String> lid = new ArrayList<String>();
		Connection conn = ConnectOracleXE();
		Statement stat = null;
		ResultSet rs = null;
		stat = conn.createStatement();  
	    rs = stat.executeQuery("select * from region c,lion b where SDO_CONTAINS(c.area,b.position)='TRUE' AND c.region_id = "+"'"+region_id+"'");
	    while(rs.next()){
	    	String temp = rs.getString(3);
	    	lid.add(temp);
	    }
	    for(int i = 0; i < lid.size(); i++){
	    	stat = conn.createStatement(); 
	    	lion temp = new lion();
	    	rs = stat.executeQuery("select * from lion_b where lion_id = "+"'"+lid.get(i)+"'");
	    	rs.next();
	    	temp.id = rs.getString("lion_id");
	    	temp.x = Integer.parseInt(rs.getString("point_x"));
	    	temp.y = Integer.parseInt(rs.getString("point_y"));
	    	res.add(temp);
	    }
	    return res;
	}
	public static ArrayList<Shape> getInsidePond(String region_id) throws Exception{
		ArrayList<Shape> res = new ArrayList<Shape>();
		ArrayList<String> lid = new ArrayList<String>();
		Connection conn = ConnectOracleXE();
		Statement stat = null;
		ResultSet rs = null;
		stat = conn.createStatement();  
	    rs = stat.executeQuery("select * from region c,pond b where SDO_CONTAINS(c.area,b.position)='TRUE' AND c.region_id = "+"'"+region_id+"'");
	    while(rs.next()){
	    	String temp = rs.getString(3);
	    	lid.add(temp);
	    }
	    for(int i = 0; i < lid.size(); i++){
	    	stat = conn.createStatement(); 
	    	pond temp = new pond();
	    	rs = stat.executeQuery("select * from pond_b where pond_id = "+"'"+lid.get(i)+"'");
	    	rs.next();
	    	temp.id = rs.getString("pond_id");
	    	temp.x = Integer.parseInt(rs.getString("center_x"));
	    	temp.y = Integer.parseInt(rs.getString("center_y"));
	    	temp.radius = Integer.parseInt(rs.getString("radius"));
	    	res.add(temp);
	    }
	    return res;
	}
}

