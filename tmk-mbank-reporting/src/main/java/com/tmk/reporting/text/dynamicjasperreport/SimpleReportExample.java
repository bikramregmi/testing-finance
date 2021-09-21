/*package com.tmk.reporting.text.dynamicjasperreport;

import java.awt.Color;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.tmk.reporting.model.ReportColumnModel;
import com.tmk.reporting.model.Test;

import net.sf.dynamicreports.jasper.builder.JasperReportBuilder;
import net.sf.dynamicreports.report.builder.DynamicReports;
import net.sf.dynamicreports.report.builder.column.Columns;
import net.sf.dynamicreports.report.builder.column.TextColumnBuilder;
import net.sf.dynamicreports.report.builder.component.Components;
import net.sf.dynamicreports.report.builder.datatype.DataTypes;
import net.sf.dynamicreports.report.builder.style.StyleBuilder;
import net.sf.dynamicreports.report.builder.style.Styles;
import net.sf.dynamicreports.report.constant.HorizontalAlignment;
import net.sf.dynamicreports.report.constant.VerticalAlignment;
import net.sf.dynamicreports.report.exception.DRException;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

public class SimpleReportExample {

	public static void main(String[] args) throws SQLException {
		JasperReportBuilder report = DynamicReports.report();// a new report
		report.columns(Columns.column("CreatedDate", "created", DataTypes.dateType()),
				Columns.column("User Name", "username", DataTypes.stringType()),
				Columns.column("Authority", "authority", DataTypes.stringType()),
				Columns.column("Status", "status", DataTypes.integerType()))
				.title(// title of the report
						Components.text("SimpleReportExample").setHorizontalAlignment(HorizontalAlignment.CENTER))
				.pageFooter(Components.pageXofY())
				.setDataSource(createReportDataSource(getTestList()));

		try {
			report.show();
	
			report.toPdf(new FileOutputStream("Y:/report.pdf"));
		} catch (DRException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	private static ResultSet createDataSource(Connection connection) throws SQLException {
		String query = "SELECT created, username, authority, status FROM users";
		Statement statement =connection.createStatement();
		ResultSet resultSet = statement.executeQuery(query);
		
		return resultSet;

	}
	
	private static JRDataSource createReportDataSource(List<?> includedTests)
	{
		JRBeanCollectionDataSource dataSource;
		dataSource = new JRBeanCollectionDataSource(includedTests);

		return dataSource;
	}
	
	private static List<Test> getTestList(){
		List<Test> testList = new ArrayList<Test>();
		Test test = new Test(new Date(),"Smrita Pokharel","Admin",1);
		testList.add(test);
		test =  new Test(new Date(),"Prashant Wosti","Admin",1);
		testList.add(test);
		test =  new Test(new Date(),"Srija Pokharel","Admin",1);
		testList.add(test);
		test =  new Test(new Date(),"Manish Gyawali","Admin",1);
		testList.add(test);
		test =  new Test(new Date(),"Test Test","Test",1);
		testList.add(test);
		return testList;
	}
	
	public JasperReportBuilder getReport(){
		JasperReportBuilder report = DynamicReports.report();// a new report
		report.columns(Columns.column("CreatedDate", "created", DataTypes.dateType()),
				Columns.column("User Name", "username", DataTypes.stringType()),
				Columns.column("Authority", "authority", DataTypes.stringType()),
				Columns.column("Status", "status", DataTypes.integerType()))
				.title(// title of the report
						Components.text("SimpleReportExample").setHorizontalAlignment(HorizontalAlignment.CENTER))
				.pageFooter(Components.pageXofY())// show page number on the
													// page footer
				.setDataSource(createReportDataSource(getTestList()));
		
		return report;
	}
	
	public void test(){
		StyleBuilder style1 = Styles.style()
				.setName("style1")
				.bold();
			StyleBuilder style2 = Styles.style(style1)
				.setName("style2")
				.italic();
			StyleBuilder columnStyle = Styles.style()
				.setName("columnStyle")
				.setVerticalAlignment(VerticalAlignment.MIDDLE);
			StyleBuilder columnTitleStyle = Styles.style(columnStyle)
				.setName("columnTitleStyle")
				.setBorder(Styles.pen1Point())
				.setHorizontalAlignment(HorizontalAlignment.CENTER)
				.setBackgroundColor(Color.LIGHT_GRAY);
		//	ReportTemplateBuilder template = Styles.templateStyles(style1, style2, columnStyle, columnTitleStyle);

			TextColumnBuilder<String> dateColumn = Columns.column("Created Date", "item", DataTypes.stringType())
				.setStyle(Styles.templateStyle("style1"));
			TextColumnBuilder<Date> userNameColumn = Columns.column("Order date", "orderdate", DataTypes.dateType())
				.setStyle(Styles.templateStyle("style2"));
			TextColumnBuilder<Integer> authorityColumn = Columns.column("Quantity", "quantity", DataTypes.integerType());
			TextColumnBuilder<BigDecimal> statusColumn = Columns.column("Unit price", "unitprice", DataTypes.bigDecimalType());

	}
	
	public List<ReportColumnModel> getColumnValues(){
		
		Map<String,String> columnMap = new HashMap<String,String>();
		
		columnMap.put("Created Date", "created");
		columnMap.put("User Name", "username");
		columnMap.put("Authority", "authority");
		columnMap.put("Status","status");
		
		re;
		
		List<ReportColumnModel> reportColumnModel = new ArrayList<ReportColumnModel>();
		
		ReportColumnModel column = new ReportColumnModel("Created Date","created",DataTypes.stringType());
		reportColumnModel.add(column);
		column = new ReportColumnModel()
		
	}
	
	

}
*/