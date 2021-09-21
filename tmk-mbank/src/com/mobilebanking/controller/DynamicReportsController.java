package com.mobilebanking.controller;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.mobilebanking.api.IBankApi;
import com.mobilebanking.api.IBankBranchApi;
import com.mobilebanking.api.IChannelPartnerApi;
import com.mobilebanking.api.ICreditLimitApi;
import com.mobilebanking.api.ICustomerApi;
import com.mobilebanking.api.ILedgerApi;
import com.mobilebanking.api.IMerchantApi;
import com.mobilebanking.api.ISmsLogApi;
import com.mobilebanking.api.ITransactionAlertApi;
import com.mobilebanking.api.ITransactionApi;
import com.mobilebanking.entity.Bank;
import com.mobilebanking.entity.User;
import com.mobilebanking.model.BankDTO;
import com.mobilebanking.model.ChannelPartnerDTO;
import com.mobilebanking.model.PagablePage;
import com.mobilebanking.model.RestResponseDTO;
import com.mobilebanking.model.SmsLogDTO;
import com.mobilebanking.model.TransactionDTO;
import com.mobilebanking.model.UserType;
import com.mobilebanking.util.AuthenticationUtil;
import com.mobilebanking.util.Authorities;
import com.mobilebanking.util.DateUtil;
import com.mobilebanking.util.ETransactionChannelForm;
import com.mobilebanking.util.ReportType;

import net.sf.dynamicreports.jasper.builder.JasperReportBuilder;
import net.sf.dynamicreports.report.builder.DynamicReports;
import net.sf.dynamicreports.report.builder.column.Columns;
import net.sf.dynamicreports.report.builder.component.Components;
import net.sf.dynamicreports.report.builder.datatype.DataTypes;
import net.sf.dynamicreports.report.constant.HorizontalAlignment;
import net.sf.dynamicreports.report.exception.DRException;

@Controller
public class DynamicReportsController {
	
	@Autowired
	private ISmsLogApi smsLogApi;
	@Autowired
	private MessageSource messageSource;
	@Autowired
	private ITransactionApi transactionApi;
	
	@Autowired
	private ITransactionAlertApi transactionAlertApi;
	
	@Autowired
	private IBankApi bankApi;
	
	@Autowired
	private IChannelPartnerApi channelPartnerApi;
	
	@Autowired
	private ILedgerApi ledgerApi;
	
	@Autowired
	private ICreditLimitApi creditLimitApi;
	@Autowired
	private ICustomerApi customerApi;
	@Autowired
	private IMerchantApi merchantApi;
	@Autowired
	private IBankBranchApi bankBranchApi;
	
	protected static Logger logger = Logger.getLogger("controller");
	@RequestMapping(value = "/getsmsreport", method = RequestMethod.GET)
	public String getsmsteport(RedirectAttributes redirectAttributes,ModelMap modelMap) {
		
			
		List <SmsLogDTO> smsList = smsLogApi.getSmsLogByBank(AuthenticationUtil.getCurrentUser().getAssociatedId());
		Collection<SmsLogDTO> collection = new ArrayList<SmsLogDTO>(smsList);
		
		JasperReportBuilder report = DynamicReports.report();//a new report
		report.columns(
			      Columns.column("Mobile No", "smsFor", DataTypes.stringType()),
			      Columns.column("SMS For", "smsForUser", DataTypes.stringType()),
			      Columns.column("SMS From", "smsFromUser", DataTypes.stringType()),
			      Columns.column("Status", "statusString", DataTypes.stringType()),
			      Columns.column("Incomming Sms", "isIncommingSms", DataTypes.booleanType()))
			  .title(//title of the report
			      Components.text("SMS Log Report")
				  .setHorizontalAlignment(HorizontalAlignment.CENTER))
				  .pageFooter(Components.pageXofY())//show page number on the page footer
				  .setDataSource(collection);
		
		
		try {
			report.show();
		} catch (DRException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			report.toPdf(new FileOutputStream("E:/report.pdf"));
//			JasperPrint print = JasperFillManager.fillReportToFile(report,new HashMap<String,String>());
//			// export it!
//			File pdf = File.createTempFile("output.", ".pdf");
//			JasperExportManager.exportReportToPdfStream(print, new FileOutputStream(pdf));
		
		} catch (FileNotFoundException | DRException e) {
			// TODO Auto-generated catch block
			
			e.printStackTrace();
		}
		modelMap.put("message", "Report Downloaded Successfully");
		
		List <SmsLogDTO> smsDtoList = smsLogApi.getSmsLogByBank(AuthenticationUtil.getCurrentUser().getAssociatedId());
		modelMap.put("smslogList", smsDtoList);
		return "reports/smsreports";
	}
	
	
//	Columns.column("Customer Id", "id", DataTypes.integerType());
//	report.columns(
//	      Columns.column("Customer Id", "id", DataTypes.integerType()),
//	      Columns.column("First Name", "first_name", DataTypes.stringType()),
//	      Columns.column("Last Name", "last_name", DataTypes.stringType()),
//	      Columns.column("Date", "date", DataTypes.dateType()))
//	  .title(//title of the report
//	      Components.text("SimpleReportExample")
//		  .setHorizontalAlignment(HorizontalAlignment.CENTER))
//		  .pageFooter(Components.pageXofY())//show page number on the page footer
//		  .setDataSource("SELECT id, first_name, last_name, date FROM customers",
//                                  connection);
//
//	try {
//                //show the report
//		report.show();
//
//                //export the report to a pdf file
//		report.toPdf(new FileOutputStream("c:/report.pdf"));
	
	
	@RequestMapping(value = "/getImcommingOrOutgoingSms", method = RequestMethod.POST)
	public String getImcommingOrOutgoingSms(ModelMap modelMap, Model model, HttpServletRequest request, @RequestParam("smsFilter")String filter) {
		List <SmsLogDTO> smsList = new ArrayList<>();
		if(filter.equals("Incoming")){
		 smsList = smsLogApi.getIncomingSms();
	}else if(filter.equals("Outgoing")){
		smsList = smsLogApi.getOutgoingSms();
	}
		modelMap.put("smslogList", smsList);
		return "reports/smsreports";
	}
	
	@RequestMapping(value = "/getSmsReport", method = RequestMethod.GET)
	public String getDownloadPage(ModelMap modelMap,
			@RequestParam(value = "pageNo", required = false) Integer pageNo,
			@RequestParam(value = "fromDate", required = false) String fromDate,
			@RequestParam(value = "toDate", required = false) String toDate,
			@RequestParam(value = "mobileNo", required = false) String mobileNo,
			@RequestParam(value = "smsType", required = false) String smsType) {
		if (AuthenticationUtil.getCurrentUser() != null) {
			String authority = AuthenticationUtil.getCurrentUser().getAuthority();
			if (authority.contains(Authorities.ADMINISTRATOR) || authority.contains(Authorities.BANK) || (authority.contains(Authorities.BANK_BRANCH) && authority.contains(Authorities.AUTHENTICATED))) {
				PagablePage smsList = smsLogApi.getSmsLog(pageNo,fromDate, toDate, mobileNo, smsType);
				modelMap.put("smslogList", smsList);
				modelMap.put("fromDate", fromDate);
				modelMap.put("toDate", toDate);
				modelMap.put("mobileNo", mobileNo);
				modelMap.put("smsType", smsType);
				if(AuthenticationUtil.getCurrentUser().getAuthority().contains(Authorities.ADMINISTRATOR)) {
					modelMap.put("user","admin");
					}
				return "reports/smsreports";
			}
		}
		return "redirect:/";
	}
	
	
	@RequestMapping(value = "/gettransactionreport", method = RequestMethod.GET)
	public String getTransactionReport(RedirectAttributes redirectAttributes,ModelMap modelMap,
			@RequestParam("from") String fromDate, @RequestParam("to") String toDate) {
		try{
		DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		List<TransactionDTO> transactionList = transactionApi.getTransactionBetweenDate(formatter.parse(fromDate), formatter.parse(toDate));
		Collection<TransactionDTO> collection = new ArrayList<TransactionDTO>(transactionList);
		
		JasperReportBuilder report = DynamicReports.report();//a new report
		report.columns(
			      Columns.column("Customer Name", "smsFor", DataTypes.stringType()),
			      Columns.column("Customer No", "smsForUser", DataTypes.stringType()),
			      Columns.column("A/C  No.", "smsFromUser", DataTypes.stringType()),
			      Columns.column("Bank Code", "statusString", DataTypes.stringType()),
			      Columns.column("Target No.", "isIncommingSms", DataTypes.stringType()),
				  Columns.column("Transaction Identifier.", "isIncommingSms", DataTypes.stringType()),
				  Columns.column("Transaction Date", "isIncommingSms", DataTypes.stringType()),
				  Columns.column("Transaction Name", "isIncommingSms", DataTypes.stringType()),
				  Columns.column("Reversed", "isIncommingSms", DataTypes.stringType()),
				  Columns.column("Bank Trans. response", "isIncommingSms", DataTypes.stringType()),
				  Columns.column("Bank Reversal response", "isIncommingSms", DataTypes.stringType()),
				  Columns.column("Merchant Check Pay Msg.", "isIncommingSms", DataTypes.stringType()),
				  Columns.column("Merchant Pay. Msg", "isIncommingSms", DataTypes.stringType()))
			  .title(//title of the report
			      Components.text("SMS Log Report")
				  .setHorizontalAlignment(HorizontalAlignment.CENTER))
				  .pageFooter(Components.pageXofY())//show page number on the page footer
				  .setDataSource(collection);
		
		
		try {
			report.show();
		} catch (DRException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			report.toPdf(new FileOutputStream("E:/report.pdf"));
//			JasperPrint print = JasperFillManager.fillReportToFile(report,new HashMap<String,String>());
//			// export it!
//			File pdf = File.createTempFile("output.", ".pdf");
//			JasperExportManager.exportReportToPdfStream(print, new FileOutputStream(pdf));
		
		} catch (FileNotFoundException | DRException e) {
			// TODO Auto-generated catch block
			
			e.printStackTrace();
		}
		modelMap.put("message", "Report Downloaded Successfully");
		
		List <SmsLogDTO> smsDtoList = smsLogApi.getSmsLogByBank(AuthenticationUtil.getCurrentUser().getAssociatedId());
		modelMap.put("smslogList", smsDtoList);
	
		return "reports/smsreports";
	}catch(Exception e){
		e.printStackTrace();
		return "reports/smsreports";
	}
	}
	
	
	//added by amrit
	@RequestMapping(value = "/transaction_alert_report", method = RequestMethod.GET)
	public String getTransactionAlertReport(ModelMap modelMap,
			@RequestParam(value = "pageNo", required = false) Integer pageNo,
			@RequestParam(value = "from-date", required = false) String fromDate,
			@RequestParam(value = "to-date", required = false) String toDate,
			@RequestParam(value = "mobile-no", required = false) String mobileNo,
			@RequestParam(value = "swift-code", required = false) String swiftCode, 
	        @RequestParam(value = "channel-partner", required = false) String channelPartner){
		if (AuthenticationUtil.getCurrentUser() != null) {
			String authority = AuthenticationUtil.getCurrentUser().getAuthority();
			if (authority.contains(Authorities.ADMINISTRATOR) || authority.contains(Authorities.BANK)) {
			//	PagablePage transactionAlertList = smsLogApi.getSmsLog(pageNo,fromDate, toDate, mobileNo, smsType);
				PagablePage transactionAlertList =transactionAlertApi.getTransactionAlertList(pageNo,fromDate, toDate, mobileNo,swiftCode,channelPartner);
				modelMap.put("transactionAlertList", transactionAlertList);
				modelMap.put("fromDate", fromDate);
				modelMap.put("toDate", toDate);
				modelMap.put("mobileNo", mobileNo);
				modelMap.put("swiftCode", swiftCode);
				modelMap.put("channelPartner", channelPartner);
				modelMap.put("bankList", bankApi.getAllBank());
				modelMap.put("channelPartnerList",channelPartnerApi.getAllChannelPartners());
				if(authority.contains(Authorities.ADMINISTRATOR)) {
				modelMap.put("user","admin");
				}
				return "reports/transactionAlertReport";
			}else if(authority.contains(Authorities.CHANNELPARTNER)){
				ChannelPartnerDTO channelPartnerDTO = channelPartnerApi.findChannelPartnerById(AuthenticationUtil.getCurrentUser().getAssociatedId());
				PagablePage transactionAlertList =transactionAlertApi.getTransactionAlertList(pageNo,fromDate, toDate, mobileNo,swiftCode,channelPartnerDTO.getUniqueCode());
				modelMap.put("transactionAlertList", transactionAlertList);
				modelMap.put("fromDate", fromDate);
				modelMap.put("toDate", toDate);
				modelMap.put("mobileNo", mobileNo);
				modelMap.put("swiftCode", swiftCode);
				modelMap.put("bankList", bankApi.getAllBankOfChannelPartner(AuthenticationUtil.getCurrentUser().getAssociatedId()));
				return "/channelPartnerView/transactionAlert/transactionAlert";
			}
		}
		return "redirect:/";
	}
	
	@RequestMapping(value="/reports/download_reports", method=RequestMethod.GET)
	public String reportDownloadView(ModelMap modelmap) {
	   if(AuthenticationUtil.getCurrentUser() !=null) {
		   String authority=AuthenticationUtil.getCurrentUser().getAuthority();
		   List<ReportType> reportTypeList=new ArrayList<ReportType>();
		   if(authority.contains(Authorities.ADMINISTRATOR)) {
			   ReportType[] reportTypeArray=ReportType.values();
			   reportTypeList=Arrays.asList(reportTypeArray);
		   }
		   else if (authority.contains(Authorities.BANK)) {
			   reportTypeList.add(ReportType.CUSTOMER_REGISTRATION);
			   reportTypeList.add(ReportType.SMS_BUSINESS_REPORT);
		   }
		   else {
			   return "redirect:/";
			   
		   }
		   modelmap.put("reportTypeList",reportTypeList);
		   return "reports/downloadReportView";
	   }
	   else {
		   return "redirect:/";
	   }
		
	}
	
	@ResponseBody
	@RequestMapping(value="/sms-log-report-data", method=RequestMethod.GET)
	public ResponseEntity<RestResponseDTO> getSMSLogReportData(@RequestParam(value="from_date", required=false) String fromDate,@RequestParam(value="to_date",required=false) String toDate){
		RestResponseDTO restResponseDto = new RestResponseDTO();
		if (AuthenticationUtil.getCurrentUser() != null) {
			String authority = AuthenticationUtil.getCurrentUser().getAuthority();
			if(authority.contains(Authorities.AUTHENTICATED)) {
				try {
		Map map=new HashMap<String,String>();
		map=smsLogApi.getSMSLogReportData(fromDate,toDate);
		restResponseDto.setDetail(map);
		restResponseDto.setMessage("success");
		return new ResponseEntity<>(restResponseDto, HttpStatus.OK);
		}
			catch(Exception e){
				restResponseDto.setMessage("Internal Server Error");
				return new ResponseEntity<>(restResponseDto, HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
	}
	restResponseDto.setMessage("Unauthorized User");
	return new ResponseEntity<>(restResponseDto,HttpStatus.UNAUTHORIZED);
	}
	
	@ResponseBody
	@RequestMapping(value="/credit-limit-ledger-report", method=RequestMethod.GET)
	public ResponseEntity<RestResponseDTO> getCreditLimitLedgerReportData(@RequestParam(value="from_date",required=false) String fromDate, @RequestParam(value="to_date",required=false) String toDate){
		RestResponseDTO restResponeDto =new RestResponseDTO();
		
		String authority= AuthenticationUtil.getCurrentUser().getAuthority();
		if(authority.contains(Authorities.AUTHENTICATED)) {
			try {
				//System.out.println("hey");
				restResponeDto.setDetail(ledgerApi.getCreditLimitReport(fromDate, toDate));
				restResponeDto.setMessage("success");
				//System.out.println("sucessfully retrieve data !!");
				return new ResponseEntity<>(restResponeDto, HttpStatus.OK);
			}
			catch(Exception e) {
				System.out.println("errror:"+e.getMessage());
				restResponeDto.setMessage("Interanl server error");
				return new ResponseEntity<>(restResponeDto,HttpStatus.INTERNAL_SERVER_ERROR);
			}
			
		}
		return new ResponseEntity<>(restResponeDto, HttpStatus.UNAUTHORIZED);
	}
/*	@ResponseBody
	@RequestMapping(value="/credit-limit-ledger-report", method=RequestMethod.GET)
	public List<Ledger> getCreditLimitLedgerReportData(@RequestParam(value="from_date",required=false) String fromDate, @RequestParam(value="to_date",required=false) String toDate){
		RestResponseDTO restResponeDto =new RestResponseDTO();
		List<Ledger> ledgerList=null;
		String authority= AuthenticationUtil.getCurrentUser().getAuthority();
		if(authority.contains(Authorities.AUTHENTICATED)) {
			try {
				System.out.println("hey");
				 ledgerList=ledgerApi.getCreditLimitReport(fromDate, toDate);
			
			}
			catch(Exception e) {
				System.out.println("Exception :"+e.getMessage());
				return ledgerList;
				
			}
			
		}
		return ledgerList;
	}*/
	
	@ResponseBody
	@RequestMapping(value="/credit-limit-report", method=RequestMethod.GET)
	public ResponseEntity<RestResponseDTO> getCreditLimitReportData(){
		RestResponseDTO restResponeDto =new RestResponseDTO();
		
		String authority= AuthenticationUtil.getCurrentUser().getAuthority();
		if(authority.contains(Authorities.AUTHENTICATED)) {
			try {
			//	System.out.println("here i am");
				restResponeDto.setDetail(creditLimitApi.getCreditLimitReportData());
				restResponeDto.setMessage("success");
			
				return new ResponseEntity<>(restResponeDto, HttpStatus.OK);
				
			}
			catch(Exception e) {
				System.out.println("errror:"+e.getMessage());
				restResponeDto.setMessage("Interanl server error");
				return new ResponseEntity<>(restResponeDto,HttpStatus.INTERNAL_SERVER_ERROR);
			}
			
		}
		return new ResponseEntity<>(restResponeDto, HttpStatus.UNAUTHORIZED);
	}
	@RequestMapping(value="/e_banking_report",method=RequestMethod.GET)
	public ResponseEntity<RestResponseDTO> getEbankingReport(
			@RequestParam(value="date")String date,
			@RequestParam(value="bank")String bankId){
		ResponseEntity<RestResponseDTO> resposeEntity=null;
		RestResponseDTO restResponseDTO=new RestResponseDTO();
		String authority=AuthenticationUtil.getCurrentUser().getAuthority();
		//System.out.println("the obtained month year is :"+date);
		if(authority.contains(Authorities.AUTHENTICATED)) {
			try {
			Date monthYear=DateUtil.convertToEnglishDateFromNepaliDate(date, true);
		    Date previousMonthYear=DateUtil.convertPreviousMonthOfNepaliDateToEnglish(date,1);
		   System.out.println("this month:"+monthYear+" previous month :"+previousMonthYear);
			List<Map<String,String>> responseList=new ArrayList<Map<String,String>>();
			Map< String,String> map=new HashMap<String,String>();
			map.put("sn", "2.1");
			map.put("particulars","Number of mobile banking customer*");

			map.put("lastMonth", customerApi.countUptoGivenMonthCustomerByBankAndDate(bankId, previousMonthYear).toString());
			map.put("thisMonth",customerApi.countCustomerThisNepaliMonthByBankAndDate(bankId,  monthYear, previousMonthYear).toString());
			map.put("total",customerApi.countUptoGivenMonthCustomerByBankAndDate(bankId, monthYear).toString());
			map.put("remarks", "");
			responseList.add(map);
			Map< String,String> map1=new HashMap<String,String>();
			map1.put("sn", "2.2");
			map1.put("particulars","Number of total transaction#");
			map1.put("lastMonth", transactionApi.countTransactionUptoGivenMonthByBankAndDate(bankId,previousMonthYear).toString());
			map1.put("thisMonth", transactionApi.countTransactionThisNepaliMonthByBankAndDate(bankId,monthYear,previousMonthYear).toString());
			map1.put("total",transactionApi.countTransactionUptoGivenMonthByBankAndDate(bankId, monthYear).toString());
			map1.put("remarks", "");
			responseList.add(map1);
			Map< String,String> map2=new HashMap<String,String>();
			map2.put("sn", "2.3");
			map2.put("particulars","Total transaction amount (Rs. in '000)#");
		    map2.put("lastMonth", transactionApi.sumTotalTransactionAmountUptoGivenMonthByBankAndDate(bankId,previousMonthYear).toString());
			map2.put("thisMonth",transactionApi.sumTotalTransactionAmountThisNepaliMonthByBankAndDate(bankId,monthYear,previousMonthYear).toString());
			map2.put("total",transactionApi.sumTotalTransactionAmountUptoGivenMonthByBankAndDate(bankId, monthYear).toString());
			map2.put("remarks", "");
		    responseList.add(map2);
		    Map<String,List<Map<String,String>>> responseMap=new HashMap<String,List<Map<String,String>>>();
		    responseMap.put(bankApi.getBankById(Long.parseLong(bankId)).getName(), responseList);
			restResponseDTO.setDetail(responseMap);
			resposeEntity=new ResponseEntity<RestResponseDTO>(restResponseDTO,HttpStatus.OK);
			}
			catch(Exception e) {
				System.out.println("errror:"+e.getMessage());
				restResponseDTO.setMessage("Interanl server error");
				return new ResponseEntity<>(restResponseDTO,HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
		else {
			System.out.println("errror:Unauthorized");
			restResponseDTO.setMessage("Unauthorised");
			resposeEntity=new ResponseEntity<RestResponseDTO>(restResponseDTO, HttpStatus.UNAUTHORIZED);
		}
		return resposeEntity;
	}
	
	@RequestMapping(value="/e_transaction_report",method=RequestMethod.GET)
	public ResponseEntity<RestResponseDTO> getEtransactionReport(@RequestParam(value="bank",required=false) String bankId, 
			@RequestParam(value="date",required=false) String nepaliDate){
		ResponseEntity<RestResponseDTO> responseEntity=null;
		RestResponseDTO restResponseDto=new RestResponseDTO();
		String authority=AuthenticationUtil.getCurrentUser().getAuthority();
		if(authority.contains(Authorities.AUTHENTICATED)) {
			try {
				
			Date thisMonth=DateUtil.convertToEnglishDateFromNepaliDate(nepaliDate, true);
			Date previousMonth=DateUtil.convertPreviousMonthOfNepaliDateToEnglish(nepaliDate,1);
			Date twoMonthAgoDate=DateUtil.convertPreviousMonthOfNepaliDateToEnglish(nepaliDate, 2);
			Map<String, List<Map<String,Object>>> responseMap=new HashMap<String, List<Map<String,Object>>>();
			List<Map<String,Object>> responseList=new ArrayList<Map<String,Object>>();
	/*		Map<String,Object> map1=new HashMap<String,Object>();
			map1.put("transaction_channel", "Merchant Point");
			map1.put("transaction_form", "Bill Payments");
			map1.put("this_month_transaction_detail",transactionApi.getThisMontheTransactionDetail(bankId,thisMonth));
			map1.put("previous_month_transaction_detail",transactionApi.getPreviousMontheTransactionDetail(bankId,previousMonth));
			responseList.add(map1);
			Map<String,Object> map2=new HashMap<String,Object>();
			map1.put("transaction_channel", "Customer Initiated (Not Covered Before)");
			map1.put("transaction_form", "Bill Payments");
			map1.put("this_month_transaction_detail","");
			map1.put("previous_month_transaction_detail","");
			responseList.add(map2);
			Map<String,Object> map3=new HashMap<String,Object>();
			map1.put("transaction_channel", "Customer Initiated (Not Covered Before)");
			map1.put("transaction_form", "Transfer (A/c to A/c)");
			map1.put("this_month_transaction_detail","");
			map1.put("previous_month_transaction_detail","");
			responseList.add(map3);
			Map<String,Object> map4=new HashMap<String,Object>();
			map1.put("transaction_channel", "Customer Initiated (Not Covered Before)");
			map1.put("transaction_form", "Other Transfer (Cardless or OTC)");
			map1.put("this_month_transaction_detail","");
			map1.put("previous_month_transaction_detail","");
			responseList.add(map4);
			Map<String,Object> map5=new HashMap<String,Object>();
			map1.put("transaction_channel", "Mobile, Internet and electronic card Transaction");
			map1.put("transaction_form", "Mobile Banking Transaction");
			map1.put("this_month_transaction_detail","");
			map1.put("previous_month_transaction_detail","");
			responseList.add(map5);*/
			ETransactionChannelForm[] etransactionChannelForms=ETransactionChannelForm.values();
			Bank bank=bankApi.getBankById(Long.parseLong(bankId));
			for(int i=0; i<etransactionChannelForms.length;i++) {
				String[] channelForm=etransactionChannelForms[i].getValue().split("_");
				String t_channel=channelForm[0];
				String t_form=channelForm[1];
				Map<String,Object> map=new HashMap<String,Object>();
				map.put("transaction_channel", t_channel);
				map.put("transaction_form", t_form);
				map.put("this_month_transaction_detail",transactionApi.getTransactionDetailNepaliDateWise(bank,thisMonth,previousMonth,etransactionChannelForms[i]));
				map.put("previous_month_transaction_detail",transactionApi.getTransactionDetailNepaliDateWise(bank,previousMonth,twoMonthAgoDate,etransactionChannelForms[i]));
				responseList.add(map);
			}
			
		    responseMap.put(bank.getName()+"("+bank.getAccountNumber()+")",responseList); 
		    restResponseDto.setDetail(responseMap);
		    restResponseDto.setMessage("Success");
		    responseEntity=new ResponseEntity<RestResponseDTO>(restResponseDto, HttpStatus.OK);
			}
			catch(Exception e) {
				System.out.println("exception occurs :"+e.getMessage());
				restResponseDto.setMessage("Internal Server Error");
				return new ResponseEntity<RestResponseDTO>(restResponseDto, HttpStatus.INTERNAL_SERVER_ERROR);
			}
			
		}
		else {
			restResponseDto.setMessage("Unauthorized !!");
			responseEntity=new ResponseEntity<RestResponseDTO>(restResponseDto, HttpStatus.UNAUTHORIZED);
		}
		return responseEntity;
	}
	@RequestMapping(value="/reports/download_nrb_reports",method=RequestMethod.GET)
	 public String nrbReport(ModelMap map) {
		 
		 if (AuthenticationUtil.getCurrentUser() != null) {
				String authority = AuthenticationUtil.getCurrentUser().getAuthority();
				if(authority.contains(Authorities.AUTHENTICATED) && (authority.contains(Authorities.ADMINISTRATOR)||authority.contains(Authorities.BANK))) {
				
				if (authority.contains(Authorities.ADMINISTRATOR)) {
					map.put("bankList",bankApi.getAllBank());
				}
				else {
					List<BankDTO> banklist=new ArrayList<BankDTO>();
				       banklist.add(bankApi.getBankDtoById(AuthenticationUtil.getCurrentUser().getAssociatedId()));
				       
					map.put("bankList",banklist);
				}
					
				}
				return "reports/nrbReportView";	
		 }
		 return "redirect:/";
	  }
	
	@RequestMapping(value="/reports/s_b_report",method=RequestMethod.GET)
	 public String smsLogReportView(ModelMap map) {
		 
		 if (AuthenticationUtil.getCurrentUser() != null) {
				String authority = AuthenticationUtil.getCurrentUser().getAuthority();
				if(authority.contains(Authorities.AUTHENTICATED) && (authority.contains(Authorities.ADMINISTRATOR))) {
		   
				return "reports/smsLogReportView";	
				}
				 return "redirect:/";
		 }
		 return "redirect:/";
	  }
	
	@RequestMapping(value="/reports/b_s_report",method=RequestMethod.GET)
	 public String businessSummaryReportView(ModelMap map) {
		 
		 if (AuthenticationUtil.getCurrentUser() != null) {
				String authority = AuthenticationUtil.getCurrentUser().getAuthority();
				if(authority.contains(Authorities.AUTHENTICATED) && (authority.contains(Authorities.ADMINISTRATOR))) {
		   
				return "reports/businessSumaryReportView";	
				}
				 return "redirect:/";
		 }
		 return "redirect:/";
	  }
	
	

	@RequestMapping(method = RequestMethod.GET, value = "reports/r_s_report")
	public String reportCommissionTransaction( ModelMap modelMap) {
		if (AuthenticationUtil.getCurrentUser() != null) {
			String authority = AuthenticationUtil.getCurrentUser().getAuthority();
			if ((authority.contains(Authorities.ADMINISTRATOR)||authority.contains(Authorities.BANK)) && authority.contains(Authorities.AUTHENTICATED)) {
				if(authority.contains(Authorities.ADMINISTRATOR)){
				modelMap.put("bankList", bankApi.getAllBank());
				modelMap.put("isAdmin",true);
				}
				modelMap.put("merchantList", merchantApi.getAllMerchant());

				return "reports/revenueReportView";
			}
			return "redirect:/";
		}
		return "redirect:/";
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "reports/r_t_report")
	public String reportreverseTransaction( ModelMap modelMap) {
		if (AuthenticationUtil.getCurrentUser() != null) {
			String authority = AuthenticationUtil.getCurrentUser().getAuthority();
			if ((authority.contains(Authorities.ADMINISTRATOR)||authority.contains(Authorities.BANK)) && authority.contains(Authorities.AUTHENTICATED)) {
				if(authority.contains(Authorities.ADMINISTRATOR)){
				modelMap.put("bankList", bankApi.getAllBank());
				modelMap.put("isAdmin",true);
				}
				

				return "reports/reversalReportView";
			}
			return "redirect:/";
		}
		return "redirect:/";
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "reports/b_a_d_report")
	public String reportBankBalanceCheck(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response, Model model) {
		if (AuthenticationUtil.getCurrentUser() != null) {
			String authority=AuthenticationUtil.getCurrentUser().getAuthority();
			if (AuthenticationUtil.getCurrentUser().isFirstLogin()) {
				return "redirect:/";
			}
			if(authority.contains(Authorities.ADMINISTRATOR)) {
				
		
			List<BankDTO> bankDTOList = new ArrayList<BankDTO>();
			bankDTOList = bankApi.getAllBankWithBalance();
			modelMap.put("bankList", bankDTOList);
		   
	
			logger.debug("agentList" + bankDTOList);
			String param = (String) model.asMap().get("message");
			logger.debug("param:" + param);
		
			if (param != null && !param.trim().equals("")) {
				modelMap.put("message", param == null ? "" : messageSource.getMessage(param, null, Locale.ROOT));
			}
			return "/reports/bankAccountReport";
			}
	    	return	"redirect:/";
		}

		return "redirect:/";
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "reports/c_l_report")
	public String customerLogReportView(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response, Model model) {
		try {
			if (AuthenticationUtil.getCurrentUser() != null) {
				User currentUser = AuthenticationUtil.getCurrentUser();
				String authority = AuthenticationUtil.getCurrentUser().getAuthority();
				if (AuthenticationUtil.getCurrentUser().isFirstLogin()) {
					return "redirect:/";
				}
				if (authority.contains(Authorities.ADMINISTRATOR) && authority.contains(Authorities.AUTHENTICATED)
						|| authority.contains(Authorities.BANK) && authority.contains(Authorities.AUTHENTICATED)
						|| (authority.contains(Authorities.BANK_BRANCH)
								&& authority.contains(Authorities.AUTHENTICATED))) {
					if (currentUser.getUserType().equals(UserType.BankBranch)) {
						if (currentUser.getChecker() == null || !currentUser.getChecker()) {
						
						}
					} else if (authority.contains(Authorities.BANK)) {
						modelMap.put("branchList", bankBranchApi
								.listBankBranchByBank(AuthenticationUtil.getCurrentUser().getAssociatedId()));
						modelMap.put("isBank", true);
						if (currentUser.getChecker() == null || !currentUser.getChecker()) {
					
						}
					} else if (authority.contains(Authorities.ADMINISTRATOR)) {
						modelMap.put("bankList", bankApi.getAllBank());
						modelMap.put("userAdmin", true);
						
					}
					  return "/reports/cusLogReportView"; 
				}
				return	"redirect:/";
			  
			}
	    	return	"redirect:/";
		}
		catch (Exception e) {
			// TODO: handle exception
			System.out.println("exception occurs !!"+e.getMessage());
			logger.debug("Error occur in get Customer Log Report!!"+e.getMessage());
			return	"redirect:/";
		}

		
	}
	@RequestMapping(value="/customerLogReport",method=RequestMethod.GET)
	public ResponseEntity<RestResponseDTO> customerLogReport(@RequestParam(value="from_date",required=false)String fromDate,
			@RequestParam(value="to_date",required=false)String toDate,
			@RequestParam(value="bank",required=false) String bankCode,
			@RequestParam(value="branch",required=false)String branchCode){
		ResponseEntity<RestResponseDTO> responseEntity=null;
		String authority=AuthenticationUtil.getCurrentUser().getAuthority();
		RestResponseDTO restResponseDto=new RestResponseDTO();
		if((authority.contains(Authorities.ADMINISTRATOR)||authority.contains(Authorities.BANK))&&authority.contains(Authorities.AUTHENTICATED)) {
			
			try {
			
			restResponseDto.setDetail(customerApi.getCustomer(fromDate, toDate,branchCode, bankCode));
			restResponseDto.setMessage("success");
		    responseEntity=new ResponseEntity<RestResponseDTO>(restResponseDto, HttpStatus.OK);
			}catch(Exception e) {
				logger.debug("exception occur in the customer log report's controller"+e.getMessage());
				restResponseDto.setMessage("Internal server Error !!");
				responseEntity=new ResponseEntity<RestResponseDTO>(restResponseDto, HttpStatus.INTERNAL_SERVER_ERROR);
				return responseEntity;
			}

		}
		else {
			restResponseDto.setMessage("Unauthorized User");
			responseEntity=new ResponseEntity<RestResponseDTO>(restResponseDto, HttpStatus.UNAUTHORIZED);
		}
		return responseEntity;
		
	}
	
	
}

