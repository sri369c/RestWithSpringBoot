package sri.sample.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileOwnerAttributeView;
import java.sql.Timestamp;
import java.util.Date;

import sri.sample.vo.FileMetaData;

/**
 * @author Srinivas
 * @since 15 March 2017
 */

public class FileMetaDataCapture
{
	public FileMetaData getFileMetaData(String fileWithPath)
	{
		try
		{
			FileMetaData fileMetaData = new FileMetaData();
			
			Path path = Paths.get(fileWithPath).toAbsolutePath();

			FileOwnerAttributeView fileOwnerAttributeView = Files.getFileAttributeView(path, FileOwnerAttributeView.class);
			fileMetaData.setOwner(fileOwnerAttributeView.getOwner().getName());
			
			BasicFileAttributes attrs = Files.readAttributes(path, BasicFileAttributes.class, LinkOption.NOFOLLOW_LINKS);
			fileMetaData.setSize(attrs.size());
			Date date = new Date(attrs.lastAccessTime().toMillis());
			fileMetaData.setLastAccessTime(new Timestamp(date.getTime()));
			date = new Date(attrs.creationTime().toMillis());
			fileMetaData.setCreationTime(new Timestamp(date.getTime()));
			date = new Date(attrs.lastModifiedTime().toMillis());
			fileMetaData.setLastModifiedTime(new Timestamp(date.getTime()));
			
			return fileMetaData;
		}catch(Exception e)
		{
			System.out.println("Exception: " + e);
			return null;
		}
	}
	
	public static void main(String[] args) throws IOException
	{
		FileMetaData metaData = new FileMetaDataCapture().getFileMetaData("C:\\Users\\Srinivas\\Desktop\\Srinivas C.docx");
		
		System.out.println(metaData.getOwner());
		System.out.println(metaData.getSize());
		System.out.println(metaData.getCreationTime());
		System.out.println(metaData.getLastModifiedTime());
		System.out.println(metaData.getLastAccessTime());
	}
}
