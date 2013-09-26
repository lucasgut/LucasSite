package org.lgg.lucassite.model.upload;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.springframework.web.multipart.MultipartFile;


/**
 * This class represents an upload.
 */
public class Upload
{
	private Long id;
	private String subject;
    private String description;
    private MultipartFile file;
    private String url;
    private MultipartFile image;
    
    public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getSubject() {
		return subject;
	}


	public void setSubject(String subject) {
		this.subject = subject;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public MultipartFile getFile() {
		return file;
	}


	public void setFile(MultipartFile file) {
		this.file = file;
	}


	public String getUrl() {
		return url;
	}


	public void setUrl(String url) {
		this.url = url;
	}


	public MultipartFile getImage() {
		return image;
	}


	public void setImage(MultipartFile image) {
		this.image = image;
	}

    public String toString()
    {
        ToStringBuilder builder = new ToStringBuilder(this);
        builder.append("id", id);
        builder.append("subject", subject);
        builder.append("description", description);
        builder.append("file size", file != null ? file.getSize() : 0);
        builder.append("url", url);
        builder.append("image size", image != null ? image.getSize() : 0);
        return builder.toString();
    }
}