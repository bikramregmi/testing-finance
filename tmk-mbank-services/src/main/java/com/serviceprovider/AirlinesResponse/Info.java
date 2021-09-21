package com.serviceprovider.AirlinesResponse;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Info")
@XmlAccessorType(XmlAccessType.FIELD)
public class Info {
	
	@XmlElement(name = "root")
	private Root root;

    private String name;

    public Root getRoot ()
    {
        return root;
    }

    public void setRoot (Root root)
    {
        this.root = root;
    }

    public String getName ()
    {
        return name;
    }

    public void setName (String name)
    {
        this.name = name;
    }
}
