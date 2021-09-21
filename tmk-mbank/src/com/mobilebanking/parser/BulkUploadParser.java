package com.mobilebanking.parser;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFFormulaEvaluator;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.google.common.io.Files;
import com.mobilebanking.entity.City;
import com.mobilebanking.entity.State;
import com.mobilebanking.model.AccountMode;
import com.mobilebanking.model.BankBranchDTO;
import com.mobilebanking.model.BankDTO;
import com.mobilebanking.model.CityDTO;
import com.mobilebanking.model.CustomerDTO;
import com.mobilebanking.model.StateDTO;
import com.mobilebanking.model.error.BankError;
import com.mobilebanking.model.error.CityError;
import com.mobilebanking.model.error.StateError;
import com.mobilebanking.repositories.CityRepository;
import com.mobilebanking.repositories.StateRepository;

@Component
public class BulkUploadParser {
	
	@Autowired
	private StateRepository stateRepository;

	@Autowired
	private CityRepository cityRepository;
	
	public List<BankDTO> parseBankFile( MultipartFile multipartFile , String identifier) throws IOException{

		List<BankDTO> bankDtoList = new ArrayList<>();
		
		@SuppressWarnings("resource")
		XSSFWorkbook workbook = new XSSFWorkbook(multipartFile.getInputStream());
		
		XSSFSheet workSheet = workbook.getSheetAt(0);
		System.out.println(workSheet.getLastRowNum());
		int i = 0;
		
		while(i<=workSheet.getLastRowNum()){
			
			BankError bankError = new BankError();
			XSSFRow row = workSheet.getRow(i++);
			
			XSSFCell cell0 = null;
			XSSFCell cell1 = null;
			XSSFCell cell2 = null;
			XSSFCell cell3 = null;
			XSSFCell cell4 = null;
			XSSFCell cell5 = null;
			XSSFCell cell6 = null;
			XSSFCell cell7 = null;
			XSSFCell cell8 = null;
			XSSFCell cell9 = null;
			
			
			
			if(i==1){
				cell0 = row.getCell(0);
				cell1 = row.getCell(1);
				cell2 = row.getCell(2);
				cell3 = row.getCell(3);
				cell4 = row.getCell(4);
				cell5 = row.getCell(5);
				cell6 = row.getCell(6);
				cell7 = row.getCell(7);
				cell8 = row.getCell(8);
				cell9 = row.getCell(9);
				
				

				if(!cell0.toString().equals("S.N.") || !cell1.toString().equals("Bank Name") || !cell2.toString().equals("Address")
						|| !cell3.toString().equals("City") || !cell4.toString().equals("Branch Code")|| !cell5.toString().equals("State")|| !cell6.toString().equals("License Count")|| !cell7.toString().equals("License Expiry Date")|| !cell8.toString().equals("Email Id")|| !cell9.toString().equals("Mobile No.")){
					bankError.setBulkUploadError("UPLOAD FORMAT ERROR");
					}
			}
				else if(i>1){
					BankDTO bankDto = new BankDTO();
					
							/*if(row.getCell(0).toString()==null)
								
							else
					bankDto.setSn(row.getCell(0).toString());*/
					
							if(row.getCell(1).toString()==null)
						bankDto.setName("");
					else
					bankDto.setName(row.getCell(1).toString());
							
					if(row.getCell(2).toString()==null)
						bankDto.setAddress("");
					else
					bankDto.setAddress(row.getCell(2).toString());
					
					if(row.getCell(3).toString()==null)
						bankDto.setCity("");
					else
					bankDto.setCity(row.getCell(3).toString());
					
					if(row.getCell(4).toString()==null)
						bankDto.setSwiftCode("");
					else
					bankDto.setSwiftCode(row.getCell(4).toString());
					
					if(row.getCell(5).toString()==null)
						bankDto.setState("");
					else
					bankDto.setState(row.getCell(5).toString());
					
					if(row.getCell(6).toString()==null)
						bankDto.setLicenseCount(0);
					else
					bankDto.setLicenseCount((int)row.getCell(6).getNumericCellValue());
					
					if(row.getCell(7).toString()==null)
					bankDto.setLicenseExpiryDate("");
				else
				bankDto.setLicenseExpiryDate(row.getCell(7).toString());
					
					if(row.getCell(8).toString()==null)
						bankDto.setEmail("");
					else
					bankDto.setEmail(row.getCell(8).toString());
					
					if(row.getCell(9).toString()==null)
						bankDto.setMobileNumber("");
					else
					bankDto.setMobileNumber(row.getCell(9).toString());
					
					bankDtoList.add(bankDto);
				}
		
	}
		return bankDtoList;
		
	}
	

	public List<BankBranchDTO> parseBankBranchFile( MultipartFile multipartFile) throws IOException{

		List<BankBranchDTO> bankBranchDtoList = new ArrayList<BankBranchDTO>();
		
		@SuppressWarnings("resource")
		XSSFWorkbook workbook = new XSSFWorkbook(multipartFile.getInputStream());
		
		XSSFSheet workSheet = workbook.getSheetAt(0);
		System.out.println(workSheet.getLastRowNum());
		int i = 0;
		
		while(i<=workSheet.getLastRowNum()){
			
			BankError bankError = new BankError();
			XSSFRow row = workSheet.getRow(i++);
			
			XSSFCell cell0 = null;
			XSSFCell cell1 = null;
			XSSFCell cell2 = null;
			XSSFCell cell3 = null;
			XSSFCell cell4 = null;
			XSSFCell cell5 = null;
			XSSFCell cell6 = null;
			XSSFCell cell7 = null;
			
			
			
			if(i==1){
				cell0 = row.getCell(0);
				cell1 = row.getCell(1);
				cell2 = row.getCell(2);
				cell3 = row.getCell(3);
				cell4 = row.getCell(4);
				cell5 = row.getCell(5);
				cell6 = row.getCell(6);
				
				

				if(!cell0.toString().equals("S.N.") || !cell1.toString().equals("Branch Name") || !cell2.toString().equals("Address")
						|| !cell3.toString().equals("City") || !cell4.toString().equals("Branch Code")|| !cell5.toString().equals("State")|| !cell6.toString().equals("Bank Name")){
					bankError.setBulkUploadError("UPLOAD FORMAT ERROR");
					}
			}
				else if(i>1){
					BankBranchDTO bankDto = new BankBranchDTO();
					
							/*if(row.getCell(0).toString()==null)
								
							else
					bankDto.setSn(row.getCell(0).toString());*/
					
							if(row.getCell(1).toString()==null)
						bankDto.setName("");
					else
					bankDto.setName(row.getCell(1).toString());
							
					if(row.getCell(2).toString()==null)
						bankDto.setAddress("");
					else
					bankDto.setAddress(row.getCell(2).toString());
					
					if(row.getCell(3).toString()==null)
						bankDto.setCity("");
					else
					bankDto.setCity(row.getCell(3).toString());
					
					if(row.getCell(4).toString()==null)
						bankDto.setBranchCode("");
					else
					bankDto.setBranchCode(row.getCell(4).toString());
					
					if(row.getCell(5).toString()==null)
						bankDto.setState("");
					else
					bankDto.setState(row.getCell(5).toString());
					
					if(row.getCell(6).toString()==null)
						bankDto.setBank("");
					else
					bankDto.setBank(row.getCell(6).toString());
					
					bankBranchDtoList.add(bankDto);
				}
		
	}
		return bankBranchDtoList;
		
	}


	public List<StateDTO> parseStateFile(MultipartFile multipartFile) throws IOException {
List<StateDTO> stateDtoList = new ArrayList<StateDTO>();
		
		@SuppressWarnings("resource")
		XSSFWorkbook workbook = new XSSFWorkbook(multipartFile.getInputStream());
		
		XSSFSheet workSheet = workbook.getSheetAt(0);
		System.out.println(workSheet.getLastRowNum());
		int i = 0;
		
		while(i<=workSheet.getLastRowNum()){
			
			StateError stateError = new StateError();
			XSSFRow row = workSheet.getRow(i++);
			
			XSSFCell cell0 = null;
			XSSFCell cell1 = null;
			
			
			
			if(i==1){
				cell0 = row.getCell(0);
				cell1 = row.getCell(1);
				
				

				if(!cell0.toString().equals("S.N.") || !cell1.toString().equals("State Name") )
					stateError.setBulkUploadError("UPLOAD FORMAT ERROR");
					}
				else if(i>1){
					StateDTO stateDto = new StateDTO();
					
							/*if(row.getCell(0).toString()==null)
								
							else
					bankDto.setSn(row.getCell(0).toString());*/
					
							if(row.getCell(1).toString()==null)
								stateDto.setName("");
					else{
						State state = stateRepository.getStateByStateName(row.getCell(1).toString());
						if(state==null){
						stateDto.setName(row.getCell(1).toString());
						stateDtoList.add(stateDto);
						}
					}
					
					
				}
		
	}
		return stateDtoList;
	}
	
	public List<CityDTO> parseCityFile(MultipartFile multipartFile) throws IOException {
		List<CityDTO> cityList = new ArrayList<CityDTO>();
		
		//Creating workbook
		@SuppressWarnings("resource")
		XSSFWorkbook workbook = new XSSFWorkbook(multipartFile.getInputStream());
		
		//Creating work sheet
		XSSFSheet workSheet = workbook.getSheetAt(0);
		
		int i = 0;
		
		while(i<=workSheet.getLastRowNum()){
			
			CityError error = new CityError();
			XSSFRow row = workSheet.getRow(i++);
			
			XSSFCell cell0 = null;
			XSSFCell cell1 = null;
			XSSFCell cell2 = null;
			XSSFCell cell3 = null;
			XSSFCell cell4 = null;
				
			if(i==1){
				cell0 = row.getCell(0);
				cell1 = row.getCell(1);
				cell2 = row.getCell(2);
				cell3 = row.getCell(3);
				cell4 = row.getCell(4);
				if(!cell0.toString().equals("S.N.") || !cell1.toString().equals("City Name") || !cell2.toString().equals("State Name"))
					error.setBulkUploadError("UPLOAD FORMAT ERROR");
				
			}else if(i>1){
				CityDTO city = new CityDTO();
				if(row.getCell(1).toString()==null)
					city.setName("");
				else{
					City cityName = cityRepository.findByCity(row.getCell(1).toString());
					if(cityName==null){
					city.setName(row.getCell(1).toString());
					}
				}
				
				if(row.getCell(2).toString()==null)
					city.setState("");
				else{
					State state = stateRepository.getStateByStateName(row.getCell(2).toString());
					if(state==null){
					city.setState(row.getCell(2).toString());
					}
				}
				
				cityList.add(city);
			}
			
		}
		return cityList;
	}


	public List<CustomerDTO> parseCustomerFile(File file) {
List<CustomerDTO> customerDtoList = new ArrayList<CustomerDTO>();
		
		@SuppressWarnings("resource")
		XSSFWorkbook workbook = null;
		try {
			workbook = new XSSFWorkbook(Files.asByteSource(file).openStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		FormulaEvaluator objFormulaEvaluator = new XSSFFormulaEvaluator((XSSFWorkbook) workbook);
        DataFormatter objDefaultFormat = new DataFormatter();
		XSSFSheet workSheet = workbook.getSheetAt(0);
		System.out.println(workSheet.getLastRowNum());
		int i = 0;
		
		while(i<=workSheet.getLastRowNum()){
			
			BankError bankError = new BankError();
			XSSFRow row = workSheet.getRow(i++);
			
			XSSFCell cell0 = null;
			XSSFCell cell1 = null;
			XSSFCell cell2 = null;
			XSSFCell cell3 = null;
			XSSFCell cell4 = null;
			XSSFCell cell5 = null;
			XSSFCell cell6 = null;
			XSSFCell cell7 = null;
			
			
			
			if(i==1){
				cell0 = row.getCell(0);
				cell1 = row.getCell(1);
				cell2 = row.getCell(2);
				cell3 = row.getCell(3);
				cell4 = row.getCell(4);
				cell5 = row.getCell(5);
				cell6 = row.getCell(6);
				cell7 = row.getCell(7);
				
				

				if(!cell0.toString().equalsIgnoreCase("Name") || !cell1.toString().equalsIgnoreCase("Address") || !cell2.toString().equalsIgnoreCase("Mobile Number")
						|| !cell3.toString().equalsIgnoreCase("Branch Code") || !cell4.toString().equalsIgnoreCase("Account Number")|| !cell5.toString().equalsIgnoreCase("Account Type")|| !cell6.toString().equalsIgnoreCase("App Service")||!cell7.toString().equalsIgnoreCase("SMS Subscribed")){
					bankError.setBulkUploadError("UPLOAD FORMAT ERROR");
					return null;
					}
			}
				else if(i>1){
					CustomerDTO customerDTO = new CustomerDTO();
					
							/*if(row.getCell(0).toString()==null)
								
							else
					bankDto.setSn(row.getCell(0).toString());*/
					
							if(row.getCell(0).toString()==null){
								
								customerDTO.setFullName("");
							}
					else{
						String[] splittedString = row.getCell(0).toString().split(" ");
						
						customerDTO.setFirstName(splittedString[0]);
						if(splittedString.length==3){
							customerDTO.setMiddleName(splittedString[1]);
							customerDTO.setLastName(splittedString[2]);
						}else{
							customerDTO.setLastName(splittedString[1]);
						}
					}
							
					if(row.getCell(1).toString()==null)
						return null;
					else
						customerDTO.setAddressOne(row.getCell(1).toString());
					
					if(row.getCell(2).toString()==null)
						return null;
						else{
						objFormulaEvaluator.evaluate(row.getCell(2));
						customerDTO.setMobileNumber(objDefaultFormat.formatCellValue(row.getCell(2), objFormulaEvaluator));
//						customerDTO.setMobileNumber(""+row.getCell(2).getNumericCellValue());
					}
					if(row.getCell(3).toString()==null)
						return null;
					else
						customerDTO.setBankBranchCode(row.getCell(3).toString());
					
					if(row.getCell(4).toString()==null)
						return null;
					else
						customerDTO.setAccountNumber(customerDTO.getBankBranchCode()+row.getCell(4).toString());
					
					if(row.getCell(5).toString()==null)
						return null;
					else{
						if(row.getCell(5).toString().equalsIgnoreCase("savings")){
							customerDTO.setAccountMode(AccountMode.SAVING);
						}else if(row.getCell(5).toString().equalsIgnoreCase("loan")){
							customerDTO.setAccountMode(AccountMode.LOAN);
						}
						else if(row.getCell(5).toString().equalsIgnoreCase("current")){
							customerDTO.setAccountMode(AccountMode.CURRENT);
						}
					}
					
					if(row.getCell(6).toString()==null)
						return null;
					else{
						if(row.getCell(6).getNumericCellValue()==1){
							customerDTO.setAppService(true);
						}else if(row.getCell(6).getNumericCellValue()==0){
							customerDTO.setAppService(false);
						}
					}
					
					if(row.getCell(7).toString()==null)
						return null;
					else{
						if(row.getCell(7).getNumericCellValue()==1){
							customerDTO.setSmsService(true);
						}else if(row.getCell(7).getNumericCellValue()==0){
							customerDTO.setSmsService(false);
						}
					}
					
					customerDtoList.add(customerDTO);
				}
		
	}
		return customerDtoList;
	}

	
    /*private List<CustomerDTO> processCsvFileOfCustomer(String inputFilePath) {
        System.out.println("Input file path: " + inputFilePath);
        List<CustomerDTO> inputList = new ArrayList<>();
        try (BufferedReader br = Files.newBufferedReader(Paths.get(inputFilePath))) {
            // skip(1) used to skip the heading in the file. If no heading then
            // remove skip
            br.lines().forEach(mapToCustomer);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return inputList;
    }

    private Consumer<String> mapToCustomer = (line) -> {
        System.out.println("Line : " + line);
        String[] column = line.split(",");// a CSV has comma separated lines


            MunicipalityOrVdc municipalityOrVdc = municipalityOrVdcRepository.findByMunicipalityOrVdcName(column[5]);
            if (municipalityOrVdc == null) {
            	MunicipalityOrVdc municipalityOrVdc = new MunicipalityOrVdc();
                municipalityOrVdc.setName(column[5]);
                municipalityOrVdc.setMunicipalityOrVdcCode(column[6]);
                District district = districtRepository.findByDistrictName(column[0]);
                municipalityOrVdc.setDistrict(district);
                municipalityOrVdc.setStatus(Status.Active);
                municipalityOrVdc.setCreated(new Date());
                municipalityOrVdcRepository.save(municipalityOrVdc);
//            }

    };*/
}
