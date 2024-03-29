/*package com.tmk.reporting.text.dynamicjasperreport;

import ar.com.fdvs.dj.core.DynamicJasperHelper;
  import ar.com.fdvs.dj.core.layout.ClassicLayoutManager;
  import ar.com.fdvs.dj.core.layout.LayoutManager;
  import ar.com.fdvs.dj.domain.DynamicReport;
  import ar.com.fdvs.dj.util.SortUtils;
 
  import net.sf.jasperreports.engine.JRDataSource;
  import net.sf.jasperreports.engine.JasperFillManager;
  import net.sf.jasperreports.engine.JasperPrint;
  import net.sf.jasperreports.engine.JasperReport;
  import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
  import org.apache.commons.logging.Log;
  import org.apache.commons.logging.LogFactory;
  
  import java.sql.Connection;
  import java.sql.DriverManager;
  import java.util.*;
  
  public abstract class BaseDjReportTest  {
  
  	public Map getParams() {
  		return params;
  	}
  
  	protected static final Log log = LogFactory.getLog(BaseDjReportTest.class);
  
  	protected JasperPrint jp;
  	protected JasperReport jr;
  	protected Map params = new HashMap();
  	protected DynamicReport dr;
  
  	public abstract DynamicReport buildReport() throws Exception;
  
  	public void testReport() throws Exception {
  			dr = buildReport();
			JRDataSource ds = getDataSource();
 			jr = DynamicJasperHelper.generateJasperReport(dr, getLayoutManager(), params);
  			log.debug("Filling the report");
  			if (ds != null)
  				jp = JasperFillManager.fillReport(jr, params, ds);
  			else
  				jp = JasperFillManager.fillReport(jr, params);
  
  			log.debug("Filling done!");
  			log.debug("Exporting the report (pdf, xls, etc)");
              exportReport();
  
              log.debug("test finished");
  
  	}
  
 	protected LayoutManager getLayoutManager() {
 		return new ClassicLayoutManager();
 	}
 
 	protected void exportReport() throws Exception {
 		ReportExporter.exportReport(jp, System.getProperty("user.dir")+ "/target/reports/" + this.getClass().getName() + ".pdf");
 		exportToJRXML();
 	}
 	
 	protected void exportToJRXML() throws Exception {
 		if (this.jr != null){
 			DynamicJasperHelper.generateJRXML(this.jr, "UTF-8",System.getProperty("user.dir")+ "/target/reports/" + this.getClass().getName() + ".jrxml");
 			
 		} else {
 			DynamicJasperHelper.generateJRXML(this.dr, this.getLayoutManager(), this.params, "UTF-8",System.getProperty("user.dir")+ "/target/reports/" + this.getClass().getName() + ".jrxml");
 		}
 	}	
 
 	protected void exportToHTML() throws Exception {
 		ReportExporter.exportReportHtml(this.jp, System.getProperty("user.dir")+ "/target/reports/" + this.getClass().getName() + ".html");
 	}	
 	protected JRDataSource getDataSource() {
 		Collection dummyCollection = TestRepositoryProducts.getDummyCollection();
 		dummyCollection = SortUtils.sortCollection(dummyCollection,dr.getColumns());
 
 		JRDataSource ds = new JRBeanCollectionDataSource(dummyCollection);		//Create a JRDataSource, the Collection used
 																				//here contains dummy hardcoded objects...
 		return ds;
 	}
 
 	public Collection getDummyCollectionSorted(List columnlist) {
 		Collection dummyCollection = TestRepositoryProducts.getDummyCollection();
 		return SortUtils.sortCollection(dummyCollection,columnlist);
 
 	}
 
 	public DynamicReport getDynamicReport() {
 		return dr;
 	}

public static Connection createSQLConnection() throws Exception {
 		Connection con = null;
 		     Class.forName("org.hsqldb.jdbcDriver" );
 			 con = DriverManager.getConnection("jdbc:hsqldb:file:target/test-classes/hsql/test_dj_db", "sa", "");
 
         return con;
 	}
 
     public int getYear(){
         return Calendar.getInstance().get(Calendar.YEAR);
     }
 }
*/