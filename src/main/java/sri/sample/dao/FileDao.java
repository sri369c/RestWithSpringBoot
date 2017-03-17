package sri.sample.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
	public static final Logger logger = LoggerFactory.getLogger(FileDao.class);
	
	public int saveFileInfo(FileInfo fileInfo)
	{
		Connection con = null;
		PreparedStatement ps = null;
		try
		{
			Class.forName("org.h2.Driver");
			con = DriverManager.getConnection("jdbc:h2:file:F:/MyWork/h2db/h2database"); 
			
			logger.info("Saving file info to database...");
			
			String sql = "INSERT INTO FILEINFO "
					+ "(fileId, fileName, owner, size, lastAccessTime, creationTime, lastModifiedTime)"
					+ " VALUES ( ?, ?, ?, ?, ?, ?, ?) ";
			ps = con.prepareStatement(sql);
			ps.setString(1, fileInfo.getFileId());
			ps.setString(2, fileInfo.getFileName());
			ps.setString(3, fileInfo.getFileMetaData().getOwner());
			ps.setLong(4, fileInfo.getFileMetaData().getSize());
			ps.setTimestamp(5, fileInfo.getFileMetaData().getCreationTime());
			ps.setTimestamp(6, fileInfo.getFileMetaData().getLastModifiedTime());
			ps.setTimestamp(7, fileInfo.getFileMetaData().getLastAccessTime());
			
			ps.execute();
			con.commit();
			
			logger.info("Saving file info to database... done.");
			
			return 0;
		} catch (Exception e)
		{
			logger.error("Exception saving file info to database: " + e.getMessage());
			return -1; 
		} finally
		{
			closeConnections(con, ps);
		}
	}
	
	public FileInfo retrieveFileInfo(String fileId)
	{
		Connection con = null;
		PreparedStatement ps = null;
		try
		{
			Class.forName("org.h2.Driver");
			con = DriverManager.getConnection("jdbc:h2:file:F:/MyWork/h2db/h2database"); 
			
			logger.info("Retrieving file info from database...");
			
			String sql = "SELECT * FROM FILEINFO WHERE FILEID = ?";
			ps = con.prepareStatement(sql);
			ps.setString(1, fileId);
			ResultSet rs = ps.executeQuery();
			
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
			logger.info("Retrieving file info from database... done.");
			
			logger.info("Returning info for the file: " + fileId + " -- " + fileInfo.getFileName());
			return fileInfo;
		} catch (Exception e)
		{
			logger.error("Exception retrieving file info from database: " + e.getMessage());
			return null;
		} finally
		{
			closeConnections(con, ps);
		}
	}
	
	public List<String> retrieveFilesList(String fileName)
	{
		Connection con = null;
		PreparedStatement ps = null;
		List<String> fileNamesList = null;
		try
		{
			Class.forName("org.h2.Driver");
			con = DriverManager.getConnection("jdbc:h2:file:F:/MyWork/h2db/h2database"); 
			
			logger.info("Retrieving file info from database...");
			
			String sql = "SELECT FILEID, FILENAME FROM FILEINFO WHERE FILENAME LIKE ? ESCAPE '!' ";
			ps = con.prepareStatement(sql);
			ps.setString(1, "%" + fileName + "%");
			ResultSet rs = ps.executeQuery();
			
			fileNamesList = new ArrayList<String>();
			while (rs.next())
			{
				fileNamesList.add(rs.getString("fileId") + " : " + rs.getString("fileName"));
			}
			logger.error("Retrieving files list from database... done.");
			return fileNamesList;
		} catch (Exception e)
		{
			logger.error("Exception retrieving files list from database: " + e.getMessage());
			return null;
		} finally
		{
			closeConnections(con, ps);
		}
	}
	
	private void closeConnections(Connection con, PreparedStatement ps)
	{
		try
		{
			if (null != con)
				con.close();
			if (null != ps)
				ps.close();
		} catch(Exception e)
		{
			// let it pass
		}
	}
}