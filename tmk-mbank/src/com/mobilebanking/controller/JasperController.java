package com.mobilebanking.controller;
/*package com.remittance.controller;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.remittance.api.IAgentApi;
import com.remittance.api.ICountryApi;
import com.remittance.api.ICurrencyApi;
import com.remittance.api.ICustomerApi;
import com.remittance.api.ISuperAgentApi;
import com.remittance.api.ITransactionApi;
import com.remittance.model.CurrencyDTO;




@Controller
public class JasperController {
	
	private static final Logger logger = LoggerFactory.getLogger(JasperController.class);
	
	@Autowired
	private ICustomerApi customerApi;
	@Autowired
	private IAgentApi agentApi;
	@Autowired
	private ISuperAgentApi superAgentApi;
	@Autowired
	private ITransactionApi transactionApi;
	
	@Autowired
	private ICountryApi countryApi;
	
	@Autowired
	private ICurrencyApi currencyApi;
	
//	/*public JasperController(ICustomerApi customerApi,IAgentApi agentApi,ISuperAgentApi superAgentApi,ITransactionApi transactionApi) {
//		this.customerApi = customerApi;
//		this.agentApi=agentApi;
//		this.superAgentApi=superAgentApi;
//		this.transactionApi=transactionApi;
//	}
//	
//	@RequestMapping(method = RequestMethod.GET, value = "/dailySettlement")
//	public String getDailySettlementReport(ModelMap modelMap, HttpServletRequest request,HttpServletResponse response) {
//		return "/Report/dailySettlement";
//	}
//	
//	@RequestMapping(method = RequestMethod.POST, value = "/dailySettlement")
//	public ModelAndView getDailySettlement(
//			ReportRequestDTO dto,ModelAndView modelAndView,
//			ModelMap modelMap, HttpServletRequest request,
//			HttpServletResponse response, RedirectAttributes redirectAttributes) {
//		List<ReportDTO> transactionTest=transactionApi.dailySettlementReport(dto); 
//		List<ReportDTO> transactionDaily=transactionApi.dailyTxnReport(dto); 
//		List<TransactionDTO> transaction=new ArrayList<TransactionDTO>();
//		transaction=transactionApi.getAllTransaction();
//		//logger.debug("dailySettlement-----transactionTest"+transactionTest);
//		//logger.debug("dailySettlement-----transactionDaily"+transactionDaily);
//		//logger.debug("dailySettlement-----report"+transaction);
//		modelMap.put("reportList", transaction);
//		modelMap.put("search", dto);
//		
//		 JRDataSource datasource = new JRBeanCollectionDataSource(transactionTest);   
//
//		 modelMap.addAttribute("datasource", datasource);
//
//		 modelMap.addAttribute("format", "pdf");
//
//	        modelAndView = new ModelAndView("pdfReport", modelMap);
//		return modelAndView;
//		//return "/Report/dailySettlementReport";
//	}
//	
//	@RequestMapping(method = RequestMethod.GET, value = "/dailyTxnReport")
//	public String getDailyTxnReport(ModelMap modelMap, HttpServletRequest request,HttpServletResponse response) {
//		return "/Report/dailyTxn";
//	}
//	
//	@RequestMapping(method = RequestMethod.POST, value = "/dailyTxnReport")
//	public ModelAndView getDailyTransaction(
//			ReportRequestDTO dto,ModelAndView modelAndView,
//			ModelMap modelMap, HttpServletRequest request,
//			HttpServletResponse response, RedirectAttributes redirectAttributes) {
//		List<ReportDTO> transactionDaily=transactionApi.dailyTxnReport(dto);
//		//logger.debug("dailySettlement-----transactionDaily"+transactionDaily);
//		modelMap.put("reportList", transactionDaily);
//		modelMap.put("search", dto);
//		
//		 JRDataSource datasource = new JRBeanCollectionDataSource(transactionDaily);   
//		 modelMap.addAttribute("datasource", datasource);
//		 modelMap.addAttribute("format", "pdf");
//	     modelAndView = new ModelAndView("pdfReportDailyTxn", modelMap);
//		return modelAndView;
//	}
//	
	@RequestMapping(value="/report",method=RequestMethod.GET)
	public void getReport(HttpServletRequest request,HttpServletResponse response) throws IllegalArgumentException, IllegalAccessException, ClassNotFoundException, SecurityException, JRException, IOException{
		//SimpleReportExample simpleReportExample = new SimpleReportExample();
		Report report = new Report();
		report.getReport("Title", "Subtitle", CustomerDTO.class.getDeclaredFields());
		response.setContentType("application/html");
		//OutputStream out = response.getOutputStream();
		JRDataSource ds = new JRBeanCollectionDataSource(customerApi.findCustomer());
		JasperPrint jp = DynamicJasperHelper.generateJasperPrint(report.getReport("Report of three monks","This report is generated at",Test.class.getDeclaredFields()), new ClassicLayoutManager(), ds);
		JasperViewer.viewReport(jp);
		Report report = new Report();
		JRDataSource ds = new JRBeanCollectionDataSource(currencyApi.findCurrency());
		//JRDataSource ds = new JRBeanCollectionDataSource(getTestList());
		JasperPrint jp = DynamicJasperHelper.generateJasperPrint(report.getReport("Report of three monks","This report is generated at",CurrencyDTO.class.getDeclaredFields()), new ClassicLayoutManager(), ds);
		JasperViewer.viewReport(jp);
		//JRHtmlExporter exporter = new JRHtmlExporter();
		Map parameters = new HashMap();
		DJServletHelper.exportToHtml(request,"reports/image",jp,parameters);
	}
	
	private static List<Test> getTestList() throws IllegalArgumentException, IllegalAccessException {
		List<Test> testList = new ArrayList<Test>();
		Test test = new Test(new Date(), "Smrita Pokharel", "Admin", 1);
		System.out.println("Details");
		for (Field f : test.getClass().getDeclaredFields()) {
			f.setAccessible(true);
			System.out.println("DETAILS " + f.getGenericType().getTypeName() + " " + f.getName() + " = " + f.get(test));
		}
		testList.add(test);
		test = new Test(new Date(), "Prashant Wosti", "Admin", 1);
		testList.add(test);
		test = new Test(new Date(), "Srija Pokharel", "Admin", 1);
		testList.add(test);
		test = new Test(new Date(), "Manish Gyawali", "Admin", 1);
		testList.add(test);
		test = new Test(new Date(), "Test Test", "Test", 1);
		testList.add(test);
		return testList;
	}

}
*/