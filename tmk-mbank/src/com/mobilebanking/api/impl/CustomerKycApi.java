/**
 * 
 */
package com.mobilebanking.api.impl;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import javax.servlet.ServletContext;

import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mobilebanking.api.ICustomerKycApi;
import com.mobilebanking.converter.CustomerKycConverter;
import com.mobilebanking.entity.City;
import com.mobilebanking.entity.Customer;
import com.mobilebanking.entity.CustomerKyc;
import com.mobilebanking.entity.DocumentIds;
import com.mobilebanking.entity.State;
import com.mobilebanking.model.CustomerKycDTO;
import com.mobilebanking.model.Status;
import com.mobilebanking.repositories.CityRepository;
import com.mobilebanking.repositories.CustomerKycRepository;
import com.mobilebanking.repositories.CustomerRepository;
import com.mobilebanking.repositories.DocumentIdsRepository;
import com.mobilebanking.repositories.StateRepository;
import com.mobilebanking.util.DateUtil;
import com.mobilebanking.util.FileHandler;

/**
 * @author bibek
 *
 */
@Service
public class CustomerKycApi implements ICustomerKycApi {
	
	@Autowired
	private CustomerKycRepository customerKycRepository;
	
	@Autowired
	private DocumentIdsRepository documentIdsRepository;
	
	@Autowired
	private CustomerRepository customerRepository;
	
	@Autowired
	private CityRepository cityRepository;
	
	@Autowired
	private StateRepository stateRepository;
	
	@Autowired
	private ServletContext servletContext;
	
	@Autowired
	private CustomerKycConverter customerKycConverter;
	
	@Autowired
	private FileHandler fileHandler;
	
	@Override
	public CustomerKycDTO save(CustomerKycDTO customerKycDto) throws JSONException, IOException {
		CustomerKyc customerKyc = new CustomerKyc();
		System.out.println(customerKycDto.getIssuedState() + " STATE");
		System.out.println(customerKycDto.getIssuedCity() + " CITY");
		Customer customer = customerRepository.findCustomerByUniqueId(customerKycDto.getCustomer());
		DocumentIds documentId = documentIdsRepository.findDocumentByDocumentType(customerKycDto.getDocumentId());
		customerKyc.setCustomer(customer);
		customerKyc.setDocumentId(documentId);
		customerKyc.setDocumentNumber(customerKycDto.getDocumentNumber());
		City city = cityRepository.findOne(Long.parseLong(customerKycDto.getIssuedCity()));
		customerKyc.setIssuedCity(city);
		State state = stateRepository.findByState(customerKycDto.getIssuedState());
		customerKyc.setIssuedState(state);
		customerKyc.setIssuedDate(
				DateUtil.convertStringToDate(customerKycDto.getIssuedDate()));
		if (! customerKycDto.getExpiryDate().trim().equals("")) {
			customerKyc.setExpiryDate(
				DateUtil.convertStringToDate(customerKycDto.getExpiryDate()));
		}
		customerKyc.setStatus(Status.Active);
		//byte[] bytes = customerKycDto.getMultiPartFile().getBytes();
		//System.out.println(bytes + " THIS IS BYTES");
		String filePath = servletContext.getRealPath("/")+"images/CustomerDocuments/doc/";
		System.out.println(filePath + " THIS IS FILE PATH");
		Path path = Paths.get(
				filePath);
		System.out.println(path + " PATH PRINT");
		String filename = fileHandler.customerKYCHandler(customerKycDto.getMultiPartFile());
		try {
			//Files.write(path, bytes);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(filename!=null){
		customerKyc.setImageUrl(
				servletContext.getRealPath("/images/customerDocuments/"+filename));
		
		}
		customerKycRepository.save(customerKyc);
		return null;
	}

	/* (non-Javadoc)
	 * @see com.mobilebanking.api.ICustomerKycApi#getCustomerKycByCustomer(java.lang.String)
	 */
	@Override
	public List<CustomerKycDTO> getCustomerKycByCustomer(String uniqueId) {
		List<CustomerKyc> customerKyc = customerKycRepository.findCustomerDocumentsByCustomer(
				uniqueId, Status.Active);
		System.out.println(customerKyc + " THIS IS KYC");
		if (! customerKyc.isEmpty()) {
			return customerKycConverter.convertToDtoList(customerKyc);
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see com.mobilebanking.api.ICustomerKycApi#getCustomerKycById(long)
	 */
	@Override
	public CustomerKycDTO getCustomerKycById(long id) {
		// TODO Auto-generated method stub
		return null;
	}

}
