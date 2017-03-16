package sri.sample.vo;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @author Srinivas
 * @since 15 March 2017
 */

public class FileMetaData implements Serializable
{
	private static final long serialVersionUID = 1346005076756202424L;

	private String owner;
	private long size;
	private Timestamp lastAccessTime;
	private Timestamp creationTime;
	private Timestamp lastModifiedTime;
	
	public String getOwner()
	{
		return owner;
	}
	public void setOwner(String owner)
	{
		this.owner = owner;
	}
	public long getSize()
	{
		return size;
	}
	public void setSize(long size)
	{
		this.size = size;
	}
	public Timestamp getLastAccessTime()
	{
		return lastAccessTime;
	}
	public void setLastAccessTime(Timestamp lastAccessTime)
	{
		this.lastAccessTime = lastAccessTime;
	}
	public Timestamp getCreationTime()
	{
		return creationTime;
	}
	public void setCreationTime(Timestamp creationTime)
	{
		this.creationTime = creationTime;
	}
	public Timestamp getLastModifiedTime()
	{
		return lastModifiedTime;
	}
	public void setLastModifiedTime(Timestamp lastModifiedTime)
	{
		this.lastModifiedTime = lastModifiedTime;
	}
}
