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



public class OfsiXmlparser {

	private float entityid;

	private String source;

	private float documentid;

	private String name = "";

	private String alias;

	private String address;

	private String documents;

	private String date;

	private String additional;

	private String type;

	public List<AmlDTO> ofsiParse(String location) throws IOException {

		List<AmlDTO> listuser = new ArrayList<AmlDTO>();
		System.out.println(location);
		FileInputStream fis = new FileInputStream(new File(location));
		HSSFWorkbook wb = new HSSFWorkbook(fis);

		HSSFSheet sheet = wb.getSheetAt(0);

		FormulaEvaluator forlulaEvaluator = wb.getCreationHelper().createFormulaEvaluator();

		for (Row row : sheet) {

			entityid = 0;

			

			name = "";

			alias = "";

			address = "";

			

			date = "";

			additional = "";

			type = "";

			source = "ofsi";

			AmlDTO amldto = null;

			for (int cn = 0; cn < row.getLastCellNum(); cn++) {

				Cell cell = row.getCell(cn, Row.CREATE_NULL_AS_BLANK);

				if (row.getLastCellNum() <= 5) {
					type = "entity";
					if (cell.getColumnIndex() == 0) {
						float getid = 0;
						try {
							getid = (float) cell.getNumericCellValue();
						} catch (Exception e) {

						}
						if (!(getid == 0)) {
							amldto = new AmlDTO();
							entityid = getid;
						}

					} else if (cell.getColumnIndex() == 1) {

						name = cell.toString();

					} else {
						additional = additional + " " + cell.toString() + ", ";
					}

				} else if (row.getLastCellNum() >= 17) {
					type = "individual";
					if (cell.getColumnIndex() == 0) {
						float getid = 0;
						try {
							getid = (float) cell.getNumericCellValue();
						} catch (Exception e) {

						}
						if (!(getid == 0)) {
							amldto = new AmlDTO();
							entityid = getid;
						}

					} else if (cell.getColumnIndex() == 1) {
						name = name + cell.toString();

					} else if (cell.getColumnIndex() == 2) {
						name = name + " " + cell.toString();
					} else if (cell.getColumnIndex() == 3) {
						name = name + " " + cell.toString();
					} else if (cell.getColumnIndex() == 4) {
						name = name + " " + cell.toString();
					} else if (cell.getColumnIndex() == 5) {
						name = name + " " + cell.toString();
					} else if (cell.getColumnIndex() == 8) {
						date = cell.toString();
					} else if ((cell.getColumnIndex() == 9) || (cell.getColumnIndex() == 10)
							|| (cell.getColumnIndex() == 11)) {
						String altdate = null;
						try {
							altdate = cell.toString();
						} catch (Exception e) {

						}
						if ((!(altdate == null)) ){ 
							if(!((altdate) == "")) {
								if(!((altdate.trim()) == "")) {
							additional = additional + " Alternate Date: " + altdate+", ";
								}
							}
						}
					} else if ((cell.getColumnIndex() == 6) || (cell.getColumnIndex() == 7)) {
							
							
							String altdate = null;
							try {
								altdate = cell.toString();
							} catch (Exception e) {

							}
							if  ((!(altdate == null)) ){ 
								if(!((altdate) == "")) {
									if(!((altdate).trim() == "")){
								
								additional=additional+ " POB :  "+ altdate+", ";
									}
								}
							}
					} 
					else if ((cell.getColumnIndex() == 12) || (cell.getColumnIndex() == 13)
							|| (cell.getColumnIndex() == 14)) {
						String altdate = null;
						try {
							altdate = cell.toString();
						} catch (Exception e) {

						}
						if  ((!(altdate == null)) ){ 
							if(!((altdate) == "")) {
								if(!((altdate).trim() == "")) {
							additional = additional + " Nationality: " + altdate+", ";
							
							
								}
							}
						}
					} else if ((cell.getColumnIndex() == 6) || (cell.getColumnIndex() == 7)) {
							
							
							String altdate = null;
							try {
								altdate = cell.toString();
							} catch (Exception e) {

							}
							if ((!(altdate == null)) || (!(altdate == "")) || (!(altdate == " "))) {
								
								additional=additional+ " POB :  "+ altdate+", ";
							}
					} 
					else  {
						additional=additional+ " Extra :  "+ cell.toString()+", ";
					}

				}

			}

			if (!(amldto == null)) {
				additional = additional.trim().replaceAll(",$", "");
				
				amldto.setEntityid(entityid);
				amldto.setAdditional(additional);
				amldto.setName(name);
				amldto.setSource(source);
				amldto.setType(type);
				listuser.add(amldto);
			}

		}

		return listuser;
	}
}
