package com.mobilebanking.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.mobilebanking.parser.AuclXlsParser;
import com.mobilebanking.parser.EuclXmlParser;
import com.mobilebanking.parser.OfacXmlParser;
import com.mobilebanking.parser.OfsiXmlparser;
import com.mobilebanking.parser.UnclXmlParser;

@Configuration
public class ParserBean {

	@Bean(name="auclxlsParser")
	public AuclXlsParser auclxlsParser(){
		return new AuclXlsParser();
	}
	
	@Bean(name="euclxmlParser")
	public EuclXmlParser euclxmlParser(){
		return new EuclXmlParser();
	}
	
	@Bean(name="ofacxmlParser")
	public OfacXmlParser ofacxmlParser(){
		return new OfacXmlParser();
	}
	
	@Bean(name="ofsixmlParser")
	public OfsiXmlparser ofsixmlParser(){
		return new OfsiXmlparser();
	}
	
	@Bean(name="unclxmlParser")
	public UnclXmlParser unclxmlParser(){
		return new UnclXmlParser();
	}
}
