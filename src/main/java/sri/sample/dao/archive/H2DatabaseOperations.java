//package sri.sample.dao.archive;
//
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.ResultSet;
//import java.sql.Statement;
//
//import org.h2.tools.DeleteDbFiles;
//
///**
// * @author Srinivas
// * @since 15 March 2017
// */
//public class H2DatabaseOperations
//{
//	/**
//	 * to create the initial table
//	 * @param args
//	 * @throws Exception
//	 */
//	
//	public static void main(String... args) throws Exception
//	{
//		DeleteDbFiles.execute("~", "test", true);
//
//		Class.forName("org.h2.Driver");
//		try 
//		{
//			Connection conn = DriverManager.getConnection("jdbc:h2:file:F:/MyWork/h2db/h2database"); 
//			Statement stmt = conn.createStatement();
//			
////			stmt.execute("create table fileinfo("
////					+ "fileId varchar(128) primary key, "
////					+ "fileName varchar(255), "
////					+ "owner varchar(255), "
////					+ "size BIGINT, "
////					+ "lastAccessTime TIMESTAMP, "
////					+ "creationTime TIMESTAMP, "
////					+ "lastModifiedTime TIMESTAMP )" );
//			
//			ResultSet rs = stmt.executeQuery("SELECT COUNT(*) AS COUNTS FROM FILEINFO");
//			
//			if (rs.next())
//				System.out.println("----------------------- : " + rs.getInt(1)); 
//		} catch (Exception e)
//		{
//			e.printStackTrace();
//		}
//	}
//}
