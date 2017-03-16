package sri.sample.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import sri.sample.dao.FileDao;
import sri.sample.util.FileIdGenerator;
import sri.sample.util.FileMetaDataCapture;
import sri.sample.vo.FileInfo;

/**
 * @author Srinivas
 * @since 15 March 2017
 */

public class FileService
{
	public static final Logger logger = LoggerFactory.getLogger(FileService.class);
	
	FileDao fileDao = new FileDao();
	
	public FileInfo getFileInfo(String fileId)
	{
		return fileDao.retrieveFileInfo(fileId);
	}
	
	public List<String> retrieveFilesList(String fileName)
	{
		return fileDao.retrieveFilesList(fileName);
	}
	
	public String saveFile(MultipartFile file, String fileUploadPath)
	{
		if (null == file || file.isEmpty()) 
		{
			return "Input file is not valid or is empty.";
		}
		
		try
		{
            String uniqueFileId = FileIdGenerator.generateFileId();
            String filePath = fileUploadPath + uniqueFileId;
            File dest = new File(filePath);
            file.transferTo(dest);
            logger.info("File uploaded to folder (with path and name): " + filePath);
            
            // save file info to database
            FileInfo fileInfo = new FileInfo();
            fileInfo.setFileName(file.getOriginalFilename());
            fileInfo.setFileId(uniqueFileId);
            fileInfo.setFileMetaData(new FileMetaDataCapture().getFileMetaData(filePath));
            fileDao.saveFileInfo(fileInfo);
            
			return "File uploaded successfully. Uploaded file id: " + uniqueFileId;
		} catch (IOException e)
		{
			logger.error("Exception caught saving file: " + e);
		}
		return "File upload was unsuccessful. Please check with support team to resolve the same.";
	}
	
	public String getFileContents(String fileIdWithPath) 
	{
		try
		{
			logger.info("Reading file at: " + fileIdWithPath);
			return new String(Files.readAllBytes(Paths.get(fileIdWithPath)));
		} catch(Exception e)
		{
			logger.error("Exception caught retrieving file contents: " + e);
			return null;
		}
	}

}
