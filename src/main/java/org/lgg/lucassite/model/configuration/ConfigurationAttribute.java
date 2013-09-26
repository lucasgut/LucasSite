package org.lgg.lucassite.model.configuration;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * This class represents a configuration.
 */
@Entity
@Table(name="CONFIG_DATA")
@XmlRootElement
public class ConfigurationAttribute
{
    public static final String FIELD_ID = "id";
    
    @Id
    private String id;
	private String value;

    /**
     * Empty constructor for Hibernate.
     */
    public ConfigurationAttribute()  {
    }
    
    public ConfigurationAttribute(
    		String id,
    		String value) 
    {
    	this.id = id;
    	this.value = value;
    }

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String toString()
    {
        ToStringBuilder builder = new ToStringBuilder(this);
        builder.append("id", id);
        builder.append("value", value);
        return builder.toString();
    }
}