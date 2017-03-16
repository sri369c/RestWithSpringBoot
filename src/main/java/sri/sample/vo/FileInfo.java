package sri.sample.vo;

import java.io.Serializable;

/**
 * @author Srinivas
 * @since 15 March 2017
 */

public class FileInfo implements Serializable
{
	private static final long serialVersionUID = -26598678332875092L;

	private String fileId;
	
	private String fileName;
	private FileMetaData fileMetaData;
	
	public String getFileId()
	{
		return fileId;
	}
	public void setFileId(String fileId)
	{
		this.fileId = fileId;
	}
	public String getFileName()
	{
		return fileName;
	}
	public void setFileName(String fileName)
	{
		this.fileName = fileName;
	}
	public FileMetaData getFileMetaData()
	{
		return fileMetaData;
	}
	public void setFileMetaData(FileMetaData fileMetaData)
	{
		this.fileMetaData = fileMetaData;
	}
}
