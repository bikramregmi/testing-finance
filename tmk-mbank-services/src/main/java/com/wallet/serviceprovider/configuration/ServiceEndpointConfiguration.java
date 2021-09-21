package com.wallet.serviceprovider.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

import com.wallet.serviceprovider.dishhome.DishMediaServiceSoap;
import com.wallet.serviceprovider.eprabhu.Utility;
import com.wallet.serviceprovider.ntc.EndPoint;
import com.wallet.serviceprovider.paypoint.OperationsSoap;
import com.wallet.serviceprovider.unitedsolution.UnitedSolutions;

@Configuration
public class ServiceEndpointConfiguration {
	
	@Bean
	public Jaxb2Marshaller marshaller() {
		Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
		marshaller.setContextPaths("com.wallet.serviceprovider.ntc:com.wallet.serviceprovider.paypoint");
		return marshaller;
	}
	
	@Bean
	public EndPoint endPoint(Jaxb2Marshaller marshaller) {
		EndPoint client = new EndPoint();
		client.setDefaultUri("http://soapposapi.eservglobal.com/");
		client.setMarshaller(marshaller);
		client.setUnmarshaller(marshaller);
		return client;
	}
	
	@Bean
	public OperationsSoap operationsSoap(Jaxb2Marshaller marshaller) {
		OperationsSoap client = new OperationsSoap();
		client.setDefaultUri("http://tempuri.org/");
		client.setMarshaller(marshaller);
		client.setUnmarshaller(marshaller);
		return client;
	}
	
	@Bean
	public Utility utility(Jaxb2Marshaller marshaller) {
		Utility client = new Utility();
		client.setDefaultUri("http://tempuri.org/");
		client.setMarshaller(marshaller);
		client.setUnmarshaller(marshaller);
		return client;
	}
	
	@Bean
	public DishMediaServiceSoap dishMediaServiceSoap(Jaxb2Marshaller marshaller) {
		DishMediaServiceSoap client = new DishMediaServiceSoap();
		client.setDefaultUri("http://tempuri.org/");
		client.setMarshaller(marshaller);
		client.setUnmarshaller(marshaller);
		return client;
	}
	
	@Bean
	public UnitedSolutions unitedSolution(Jaxb2Marshaller marshaller) {
		UnitedSolutions client = new UnitedSolutions();
		client.setDefaultUri("http://booking.us.org/");
		client.setMarshaller(marshaller);
		client.setUnmarshaller(marshaller);
		return client;
	}

//	@Bean
//	public NcellServiceSoap ncell(Jaxb2Marshaller marshaller) {
//		NcellServiceSoap client = new NcellServiceSoap();
////		client.setDefaultUri("http://116.68.212.1/");
//		client.setMarshaller(marshaller);
//		client.setUnmarshaller(marshaller);
//		return client;
//	}

	
}
