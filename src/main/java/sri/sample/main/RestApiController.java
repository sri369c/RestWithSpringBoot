package sri.sample.main;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import sri.sample.service.FileService;
import sri.sample.util.ConstantsFile;
import sri.sample.vo.FileInfo;

/**
 * @author Srinivas
 * @since 15 March 2017
 */

@RestController
@EnableAutoConfiguration
public class RestApiController
{
	public static final Logger logger = LoggerFactory.getLogger(RestApiController.class);
	
	@Autowired
	private HttpServletRequest request;

	FileService fileService = new FileService();

	@RequestMapping("/upload")
	public String upload(@RequestParam("file") MultipartFile file)
	{
		if (null == file || file.isEmpty()) 
		{
			return "Input file is not valid or is empty.";
		}
		
        String realPathtoUploads =  request.getServletContext().getRealPath(ConstantsFile.FILE_UPLOAD_FOLDER);
        
        if(! new File(realPathtoUploads).exists())
        {
        	logger.info("File upload folder not present. Created the folder: " + realPathtoUploads);
            if (! new File(realPathtoUploads).mkdir())
            {
            	logger.error("File upload folder does not exist, and unable to create it either.");
            	return "File upload was unsuccessful. Please check with support team to resolve the same.";
            }
        }
        
        return fileService.saveFile(file, realPathtoUploads);
	}
	
	@RequestMapping(value = "/getFileInfo/{fileId}", method = RequestMethod.GET)
	public ResponseEntity<FileInfo> getFileInfo(@PathVariable("fileId") String fileId)
	{
		logger.info("Entered getFileInfo.");
		FileInfo fileInfo = fileService.getFileInfo(fileId);
		if (null == fileInfo)
		{
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<FileInfo>(fileInfo, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/getFileContent/{fileId}", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<InputStreamResource> getFileContent(@PathVariable("fileId") String fileId) throws FileNotFoundException
	{
		if (null == fileId || StringUtils.isEmpty(fileId.trim())) 
			return null;
		else
		{
			FileInfo fileInfo = fileService.getFileInfo(fileId);
			
			String realPathtoUploads =  request.getServletContext().getRealPath(ConstantsFile.FILE_UPLOAD_FOLDER);
		    File file = new File(realPathtoUploads + fileId);
		    
		    HttpHeaders respHeaders = new HttpHeaders();
		    respHeaders.setContentDispositionFormData("attachment", fileInfo.getFileName());

		    InputStreamResource isr = new InputStreamResource(new FileInputStream(file));
		    return new ResponseEntity<InputStreamResource>(isr, respHeaders, HttpStatus.OK);
		}
	}
}
