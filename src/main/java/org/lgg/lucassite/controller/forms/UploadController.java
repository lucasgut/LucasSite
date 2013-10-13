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
import org.springframework.web.bind.annotation.RequestParam;
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
    public ModelAndView showUploadForm(@RequestParam(value = "downloadId", required = false) Long downloadId) {
    	Upload upload = new Upload();
    	if(downloadId != null) {
	    	Download download = downloadsManager.getDownload(downloadId);
	    	if(download != null) {
	    		upload.setId(download.getId());
	    		upload.setSubject(download.getSubject());
	    		upload.setDescription(download.getDescription());
	    	}
    	}    	
        final ModelAndView mav = new ModelAndView("PostDownload");
        mav.addObject("hitcount", configurationManager.getHitCount());
        mav.addObject("isGuest", configurationManager.isUserLoggedIn(userSession.getId()) ? false : true);
        mav.addObject("user", configurationManager.getUser(userSession.getId()));
        mav.getModelMap().put("upload", upload);
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
		byte[] image = getImage(upload, result);
		
		//Read and save the file
		String url = saveFile(upload, result);

		//Persist download
		if(upload.getId() == null) {
			//Create the download
			Download download = new Download(
					upload.getId(),
					upload.getSubject(),
					new Date(),
					upload.getDescription(),
					url,
					image);
			downloadsManager.createDownload(download);
		}
		else {
			//Update existing download
	    	Download download = downloadsManager.getDownload(upload.getId());
	    	if(download == null) {
				result.rejectValue("download", "download.error", "Failed to find download [" + upload.getId() + "].");
	    	}
	    	download.setSubject(upload.getSubject());
	    	download.setDescription(upload.getDescription());
	    	download.setLastModified(new Date());
	    	if(upload.getImage() != null && !upload.getImage().isEmpty()) {
	    		download.setImage(getImage(upload, result));
	    	}
	    	if(upload.getFile() != null && !upload.getFile().isEmpty()) {
	    		download.setUrl(saveFile(upload, result));
	    	}
	    	else if(upload.getUrl() != null && !upload.getUrl().isEmpty()) {
	    		download.setUrl(upload.getUrl());
	    	}
			downloadsManager.updateDownload(download);
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
	
	private String saveFile(Upload upload, BindingResult result) {
		String url = upload.getUrl();
		MultipartFile file = upload.getFile();
		if(file != null && !file.isEmpty()) {
			try {
				saveFile(file);
				url = upload.getFile().getOriginalFilename();
			}
			catch(IOException ioe) {
				result.rejectValue("file", "file.io.error", "Failed to upload file: " + ioe.getMessage());
			}
		}
		return url;
	}
	
	private byte[] getImage(Upload upload, BindingResult result) {
		byte[] image = null;
		if(upload.getImage() != null) {
			try {
				image = upload.getImage() != null ? upload.getImage().getBytes() : null;
			}
			catch(IOException ioe) {
				result.rejectValue("image", "image.io.error", "Failed to upload image: " + ioe.getMessage());
			}
		}
		return image;
	}
}