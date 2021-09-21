package com.serviceprovider.AirlinesResponse;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class SrcLocation {
	
	@XmlElement
	 private String Source;
	@XmlElement
	    private String Terminal;

	    public String getSource ()
	    {
	        return Source;
	    }

	    public void setSource (String Source)
	    {
	        this.Source = Source;
	    }

	    public String getTerminal ()
	    {
	        return Terminal;
	    }

	    public void setTerminal (String Terminal)
	    {
	        this.Terminal = Terminal;
	    }
}
