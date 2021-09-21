package com.mobilebanking.controller;

public class Report {

	/*public static void main(String[] args) throws JRException, ColumnBuilderException, ClassNotFoundException,
			IllegalArgumentException, IllegalAccessException {
	
		Report report = new Report();
		JRDataSource ds = new JRBeanCollectionDataSource(getTestList());
		JasperPrint jp = DynamicJasperHelper.generateJasperPrint(report.getReport("Report of three monks","This report is generated at",Test.class.getDeclaredFields()), new ClassicLayoutManager(), ds);
		JasperViewer.viewReport(jp);
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

	public DynamicReport getReport(String reportTitle,String subtitle, Field[] fieldNames) throws IllegalArgumentException, IllegalAccessException, ColumnBuilderException, ClassNotFoundException, JRException {
		DynamicReportBuilder drb = new DynamicReportBuilder();
		drb.setTitle(reportTitle).setSubtitle(subtitle + new Date())
				.setPrintBackgroundOnOddRows(true).setUseFullPageWidth(true);
		for(Field field:fieldNames){
			 drb.addColumn(getAbstractColumn(field.getName().toUpperCase(),field.getName(),field.getGenericType(),30));
		}
		
		DynamicReport dynamicReport = drb.build();
		return dynamicReport;

	}
	
	private  AbstractColumn getAbstractColumn(String title,String name,Type dataType,Integer width){
		AbstractColumn column = ColumnBuilder.getNew()
		        .setColumnProperty(name, dataType.getTypeName())
		        .setTitle(title).setWidth(width)
		        .build();
		
		return column;
		
	}*/

}
