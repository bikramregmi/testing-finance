package com.mobilebanking.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.servlet.ModelAndView;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

public class ReportGenerator {
	
	public ReportGenerator(){
		
	}
	
	public static ModelAndView generatePdf(List<Object> object){
		Map<String,Object> parameterMap = new HashMap<String,Object>();
        JRDataSource JRdataSource = new JRBeanCollectionDataSource(object);
        parameterMap.put("datasource", JRdataSource);
        ModelAndView modelAndView = new ModelAndView("pdfReport", parameterMap);
        return modelAndView;
	}
	
	public static ModelAndView generateXls(List<Object> object){
		Map<String,Object> parameterMap = new HashMap<String,Object>();
        JRDataSource JRdataSource = new JRBeanCollectionDataSource(object);
        parameterMap.put("datasource", JRdataSource);
        ModelAndView modelAndView = new ModelAndView("xlsReport", parameterMap);
        return modelAndView;
	}
	
	public static ModelAndView generateCsv(List<Object> object){
		Map<String,Object> parameterMap = new HashMap<String,Object>();
        JRDataSource JRdataSource = new JRBeanCollectionDataSource(object);
        parameterMap.put("datasource", JRdataSource);
        ModelAndView modelAndView = new ModelAndView("csvReport", parameterMap);
        return modelAndView;
	}
	
	public static ModelAndView generateHtml(List<Object> object){
		Map<String,Object> parameterMap = new HashMap<String,Object>();
        JRDataSource JRdataSource = new JRBeanCollectionDataSource(object);
        parameterMap.put("datasource", JRdataSource);
        ModelAndView modelAndView = new ModelAndView("htmlReport", parameterMap);
        return modelAndView;
	}

}
