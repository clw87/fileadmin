package com.zaiwai.backup.db;


public class DBConnector {
	
//	public static Connection getConnection(){
//		Connection con = null;
//		try {
//			Class.forName("com.mysql.jdbc.Driver");
//			String url = "jdbc:mysql://192.168.16.106:3306/data_name?user=xxx&password=xxx";
//			con = DriverManager.getConnection(url);
//		} catch (ClassNotFoundException e) {
//			e.printStackTrace();
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		return con;
//	}
//	
//	/**
//	 * 获取某个时间结点之后的图片
//	 * @param type
//	 * @param offset
//	 * @param limit
//	 * @param date
//	 * @return
//	 * @throws Exception
//	 */
//	public static Map<String, String> getDataFromDB() throws Exception{
//		String sql = "SELECT city, latitude, longitude FROM location";
//
//		Connection con = getConnection();
//		Statement state = con.createStatement();
//		ResultSet res = state.executeQuery(sql);
//		Map<String, String> result = new HashMap<String, String>();
//		if(res != null){
//			while(res.next()){
//				String cityName = res.getString("city");
//				cityName = cityName.toLowerCase();
//				String lon = res.getString("longitude");
//				String lat = res.getString("latitude");
//				
//				if(result.containsKey(cityName)){
//					if(result.get(cityName) == null || result.get(cityName).trim().equals("null")){
//						System.out.println(cityName);
//					}
//					result.put(cityName, result.get(cityName) + "\t" + lon + "\t" + lat);
//					System.out.println("名字重复2: " + cityName + "\t" + result.get(cityName));
//				}
//				else{
//					if(lon==null || lat == null){
//						System.out.println(cityName);
//					}else{
//						result.put(cityName, lon+"\t"+lat);
//					}
//				}
//			}
//		}
//		con.close();
//		return result;
//	}
//	
//	
//	public static ResultSet getPOIDataFromDB(Integer offset, Integer limit) throws Exception{
//		String sql = "SELECT * FROM t_poi_info ORDER BY id DESC LIMIT " + offset + "," + limit;
//		Connection con = getConnection();
//		Statement state = con.createStatement();
//		ResultSet res = state.executeQuery(sql);
//		return res;
//		
//	}
	
}
