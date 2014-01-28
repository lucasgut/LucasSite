package org.lgg.lucassite.model.download;

import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;

import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * This class represents a download.
 */
@Entity
public class Download
{
    public static final String FIELD_ID = "id";
    
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
	private String subject;
    private Date lastModified;
    private String description;
    private String url;
    @Lob @Basic(fetch = FetchType.LAZY)
    @Column(length=1048576)			//Max image size is 1MB
    private byte[] image;

    /**
     * Empty constructor for Hibernate.
     */
    public Download()  {
    }
    
    public Download(
    		Long id,
    		String subject,
    		Date lastModified,
    		String description,
    		String url,
    		byte[] image) 
    {
    	this.id = id;
    	this.subject = subject;
    	this.lastModified = lastModified;
    	this.description = description;
    	this.url = url;
    	this.image = image;
    }

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public Date getLastModified() {
		return lastModified;
	}

	public void setLastModified(Date lastModified) {
		this.lastModified = lastModified;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	public boolean isValidUrl() {
		return (url.startsWith("http") || url.startsWith("ftp")) &&
				url.contains("://");
	}

	public byte[] getImage() {
		return image;
	}

	public void setImage(byte[] image) {
		this.image = image;
	}

    public String toString()
    {
        ToStringBuilder builder = new ToStringBuilder(this);
        builder.append("id", id);
        builder.append("subject", subject);
        builder.append("lastModified", lastModified);
        builder.append("description", description);
        builder.append("url", url);
        builder.append("image size", image != null ? image.length : 0);
        return builder.toString();
    }
}