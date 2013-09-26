package org.lgg.lucassite.controller.forms;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.Map;

import javax.validation.Valid;

import org.lgg.lucassite.model.configuration.ConfigurationManager;
import org.lgg.lucassite.model.download.Download;
import org.lgg.lucassite.model.download.DownloadsManager;
import org.lgg.lucassite.model.upload.Upload;
import org.lgg.lucassite.model.user.UserSession;
import org.lgg.lucassite.validator.upload.UploadValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

@Controller
@Scope("session")
@RequestMapping("/PostDownload.html")
public class UploadController 
{
	final static Logger logger = LoggerFactory.getLogger(UploadController.class);
	
    @Autowired private ConfigurationManager configurationManager;
    @Autowired private UserSession userSession;				//Session scoped
    @Autowired private UploadValidator validator;
    @Autowired private DownloadsManager downloadsManager;
    
    private String downloadsPath;

    public void setConfigurationManager(ConfigurationManager configurationManager)
    {
        this.configurationManager = configurationManager;
    }

    public void setUserSession(UserSession userSession)
    {
        this.userSession = userSession;
    }
    
    public void setValidator(UploadValidator validator) {
    	this.validator = validator;
    }

    public void setDownloadManager(DownloadsManager downloadsManager)
    {
        this.downloadsManager = downloadsManager;
    }
    
    @Value("#{lucasSiteProps.downloadsPath}")
    public void setDownloadsPath(String downloadsPath) {
    	this.downloadsPath = downloadsPath;
    }

    @RequestMapping(method=RequestMethod.GET)
    public ModelAndView showUploadForm() {
        final ModelAndView mav = new ModelAndView("PostDownload");
        mav.addObject("hitcount", configurationManager.getHitCount());
        mav.addObject("isGuest", configurationManager.isUserLoggedIn(userSession.getId()) ? false : true);
        mav.addObject("user", configurationManager.getUser(userSession.getId()));
        mav.getModelMap().put("upload", new Upload());
        return mav;    	
    }	

	@RequestMapping(method = RequestMethod.POST)
	public String processUploadForm(@Valid Upload upload, BindingResult result, Map<String, Upload> model) {
		//Validate upload
		validator.validate(upload, result);
		if (result.hasErrors()) {
			String errorStr = "";
			for(ObjectError error : result.getAllErrors()) {
				errorStr += error.toString() + "; ";
			}
			logger.debug("Failed to upload a download:" + errorStr);
			return "PostDownload";
		}
		
		//Read the image
		byte[] image = null;
		if(upload.getImage() != null) {
			try {
				image = upload.getImage() != null ? upload.getImage().getBytes() : null;
			}
			catch(IOException ioe) {
				result.rejectValue("image", "image.io.error", "Failed to upload image: " + ioe.getMessage());
			}
		}
		
		//Read and save the file
		if(upload.getFile() != null) {
			try {
				saveFile(upload.getFile());
			}
			catch(IOException ioe) {
				result.rejectValue("file", "file.io.error", "Failed to upload file: " + ioe.getMessage());
			}
		}

		//Create the download
		Download download = new Download(
				upload.getId(),
				upload.getSubject(),
				new Date(),
				upload.getDescription(),
				upload.getUrl(),
				image);
		if(upload.getId() == null) {
			downloadsManager.createDownload(download);
		}
		else {
			//downloadsManager.updateDownload(download);
		}
		
		model.put("upload", upload);
		return "PostDownload";
	}
	
	private void saveFile(MultipartFile uploadedFile) throws IOException {
		String path  = downloadsPath + File.separator +  uploadedFile.getOriginalFilename();	
		BufferedInputStream inStreamFileBuf = new BufferedInputStream(uploadedFile.getInputStream());
		FileOutputStream file = new FileOutputStream(new File(path));
		int read;
		byte[] buffer = new byte[4096];
		while ( (read = inStreamFileBuf.read(buffer)) != -1) {
			file.write(buffer, 0, read);
		}
		file.close();
		inStreamFileBuf.close();
	}
}