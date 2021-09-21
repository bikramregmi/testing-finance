package com.mobilebanking.parser;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;

import com.mobilebanking.model.AmlDTO;



public class AuclXlsParser {
	

	private float entityid;

	private String source = "aucl";

	private String name;

	private String alias="";

	private String address="";

	private String date="";

	private String additional="";

	private String type;

	private int checker = 0;

	public List<AmlDTO> auclParser(String location) throws IOException {
		
		
		boolean idchecker = false;
		boolean precheck=false;

		List<AmlDTO> listuser = new ArrayList<AmlDTO>();
		
		FileInputStream fis = new FileInputStream(new File(location));
		HSSFWorkbook wb = new HSSFWorkbook(fis);

		HSSFSheet sheet = wb.getSheetAt(0);

		FormulaEvaluator forlulaEvaluator = wb.getCreationHelper().createFormulaEvaluator();
		AmlDTO amldto = null;
		for (Row row : sheet) {

			System.out.println("row ready to iterate ");

			for (int cn = 0; cn < row.getLastCellNum(); cn++) {

				Cell cell = row.getCell(cn, Row.CREATE_NULL_AS_BLANK);

				if ((row.getLastCellNum() >= 10) && (row.getLastCellNum() <= 14)) {

					if (cell.getColumnIndex() == 0) {

						float getid = 0;
						try {

							String regex = "[0-9]+";
							if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
								getid = (float) cell.getNumericCellValue();

							} else

							if (cell.toString().matches(regex)) {
								getid = Float.parseFloat(cell.toString());

							}

						} catch (Exception e) {

						}
						if (!(getid == 0)) {

							if (!(checker == 0)) {
								
								amldto.setEntityid(entityid);
								amldto.setName(name);
								amldto.setDate(date);
								amldto.setAlias(alias);
								amldto.setAdditional(additional);
								amldto.setSource(source);
								amldto.setType(type);
								amldto.setAddress(address);
								

								listuser.add(amldto);
								
								date="";
								alias="";
								additional="";
								address="";

							} else {
								checker = 1;
								precheck=true;
							}
							
							
							amldto = new AmlDTO();
							entityid = getid;
							idchecker = true;

						} else {
							idchecker = false;

						}

					} else if (cell.getColumnIndex() == 1) {
						if (idchecker == true) {
							name = cell.toString();
						}
					} else if (cell.getColumnIndex() == 2  ) {

						if (idchecker == true) {
							type = cell.toString();
						}
					} else if (cell.getColumnIndex() == 3 && (precheck==true)) {

						if (cell.toString().equalsIgnoreCase("aka")) {
							alias = alias + " " + row.getCell(1).toString() + ", ";
						}
						else if(cell.toString().equalsIgnoreCase("Original Script")){
							additional = additional + " name type: " + row.getCell(1).toString() + ", ";
						}

					}
					else if (cell.getColumnIndex() == 4 && (precheck==true)) {
						
						if((!(cell.toString().trim().equals(""))) && (!(cell.toString().equals(null)))){
							date =date+" "+cell.toString()+", ";
						}
					}
					else if (cell.getColumnIndex() == 7 && (precheck==true)) {
						if((!(cell.toString().trim().equals(""))) && (!(cell.toString().equals(null)))){
						address =address+" "+cell.toString()+", ";
						}
					}
					else if (cell.getColumnIndex() == 5 && (precheck==true)) {
						additional =additional+" Place of birth: "+cell.toString()+", ";
					}
					else if (cell.getColumnIndex() == 6 && (precheck==true)) {
						if((!(cell.toString().trim().equals(""))) && (!(cell.toString().equals(null)))){
						additional =additional+" citizenship: "+cell.toString()+", ";
						}
					}
					else if((precheck==true)) {
						additional =additional+"  "+cell.toString()+", ";
					}

				}
				
				
				if(sheet.getLastRowNum()==row.getRowNum()){
					
					amldto.setEntityid(entityid);
					amldto.setName(name);
					amldto.setDate(date);
					amldto.setAlias(alias);
					amldto.setAdditional(additional);
					amldto.setSource(source);
					amldto.setType(type);
					amldto.setAddress(address);
					listuser.add(amldto);
					
				}

			}

		}
		
		return listuser;

	}
}
