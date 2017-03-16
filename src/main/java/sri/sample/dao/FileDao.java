package sri.sample.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import sri.sample.vo.FileInfo;
import sri.sample.vo.FileMetaData;

/**
 * Database operations are not optimized.
 * Including JPA was causing some issues guess due to some incompatible versions of JAR. 
 * Didn't want to spend too much time on it.
 * 
 * @author Srinivas
 * @since 15 March 2017
 */

public class FileDao
{
	public int saveFileInfo(FileInfo fileInfo)
	{
		Connection con = null;
		Statement stmt = null;
		try
		{
			Class.forName("org.h2.Driver");
			con = DriverManager.getConnection("jdbc:h2:file:F:/MyWork/h2db/h2database"); 
			stmt = con.createStatement();
			
			System.out.println("Saving file info to database...");
			
			String sql = "INSERT INTO FILEINFO "
					+ "(fileId, fileName, owner, size, lastAccessTime, creationTime, lastModifiedTime)"
					+ " VALUES ("
					+ "'" + fileInfo.getFileId() + "' , "
					+ "'" + fileInfo.getFileName() + "' , "
					+ "'" + fileInfo.getFileMetaData().getOwner() + "' , "
					+ fileInfo.getFileMetaData().getSize() + ", "
					+ "'" + fileInfo.getFileMetaData().getCreationTime() + "', "
					+ "'" + fileInfo.getFileMetaData().getLastModifiedTime() + "', "
					+ "'" + fileInfo.getFileMetaData().getLastAccessTime() + "'"
					+ ")";
			stmt.execute(sql);
			con.commit();
			
			System.out.println("Saving file info to database... done.");
			
			return 0;
		} catch (Exception e)
		{
			System.out.println("Exception saving file info to database: " + e.getMessage());
			return -1; 
		} finally
		{
			closeConnections(con, stmt);
		}
	}
	
	public FileInfo retrieveFileInfo(String fileId)
	{
		Connection con = null;
		Statement stmt = null;
		try
		{
			Class.forName("org.h2.Driver");
			con = DriverManager.getConnection("jdbc:h2:file:F:/MyWork/h2db/h2database"); 
			stmt = con.createStatement();
			
			System.out.println("Retrieving file info from database...");
			
			String sql = "SELECT * FROM FILEINFO WHERE FILEID = '" + fileId + "'";
			ResultSet rs = stmt.executeQuery(sql);
			
			FileInfo fileInfo = new FileInfo();
			if (rs.next())
			{
				fileInfo.setFileId(fileId);
				fileInfo.setFileName(rs.getString("fileName"));
				
				FileMetaData metaData = new FileMetaData();
				metaData.setOwner(rs.getString("owner"));
				metaData.setSize(rs.getLong("size"));
				metaData.setLastAccessTime(rs.getTimestamp("lastAccessTime"));
				metaData.setCreationTime(rs.getTimestamp("creationTime"));
				metaData.setLastModifiedTime(rs.getTimestamp("lastModifiedTime"));
				
				fileInfo.setFileMetaData(metaData);
			}
			System.out.println("Retrieving file info from database... done.");
			
			System.out.println("Returning info for the file: " + fileId + " -- " + fileInfo.getFileName());
			return fileInfo;
		} catch (Exception e)
		{
			System.out.println("Exception retrieving file info from database: " + e.getMessage());
			return null;
		} finally
		{
			// TODO log message
			closeConnections(con, stmt);
		}
	}
	
	public FileMetaData retrieveFileMetaData(String fileId)
	{
		return new FileMetaData();
	}
	
	private void closeConnections(Connection con, Statement stmt)
	{
		try
		{
			if (null != con)
				con.close();
			if (null != stmt)
				stmt.close();
		} catch(Exception e)
		{
			// let it pass
		}
	}
}