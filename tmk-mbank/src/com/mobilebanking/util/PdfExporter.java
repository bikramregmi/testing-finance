package com.mobilebanking.util;

import java.io.FileOutputStream;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.mobilebanking.entity.Bank;
import com.mobilebanking.entity.BankBranch;
import com.mobilebanking.entity.Customer;
import com.mobilebanking.entity.NonFinancialTransaction;
import com.mobilebanking.entity.Transaction;
import com.mobilebanking.model.UserType;
import com.mobilebanking.plasmatech.Passenger;
import com.mobilebanking.repositories.BankBranchRepository;
import com.mobilebanking.repositories.BankRepository;
import com.mobilebanking.repositories.CustomerRepository;

@Component
public class PdfExporter {
	
	@Autowired
	private CustomerRepository customerRepository;
	
	@Autowired
	private BankRepository bankRepository;
	
	@Autowired
	private BankBranchRepository bankBranchRepository;
	
	private static Font catFont = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD);
    private static Font smallBold = new Font(Font.FontFamily.TIMES_ROMAN, 8, Font.BOLD);
    private static Font small = new Font(Font.FontFamily.TIMES_ROMAN, 8, Font.NORMAL);
    private static Font catFontNormal = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL);
	private static Font catFont9 = new Font(Font.FontFamily.TIMES_ROMAN, 9, Font.NORMAL);

	private static Font catFontBoldRed = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD, BaseColor.RED);

	private static Font catFontBoldWhite = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD, BaseColor.WHITE);

    public String exportFinancialReport(List<Transaction> transactionList, String path){
        try {
        	String file = Long.toString(System.currentTimeMillis());
        	Document document = new Document();
			PdfWriter.getInstance(document, new FileOutputStream(path+"\\"+file+".pdf"));
			document.open();
			document.addTitle("Financial Transaction Report");
			Paragraph heading = new Paragraph();
			heading.setAlignment(Paragraph.ALIGN_CENTER);
	        heading.add(new Paragraph("Financial Transaction Report", catFont));
	        addEmptyLine(heading, 1);
	        document.add(heading);
	        
            /*
             * edited by amrit 
             * int column_size=11;
             * if(AuthenticationUtil.getCurrentUser().getUserType()==UserType.BankBranch){
             * column_size=10;
             * }
             * PdfPTable table = new PdfPTable(column_size);
             * */
	        
	        PdfPTable table = new PdfPTable(11);
	        if(AuthenticationUtil.getCurrentUser().getUserType()==UserType.BankBranch){
	        	table = new PdfPTable(10);
	        }
			
			table.setWidthPercentage(100);
			
			table.addCell(addHeader("Date"));
	        table.addCell(addHeader("Cust Name"));
	        table.addCell(addHeader("Cust No"));
	        table.addCell(addHeader("A/C No"));
	        if(AuthenticationUtil.getCurrentUser().getUserType()==UserType.Admin){
	        	 table.addCell(addHeader("Bank Code"));
	        }else if(AuthenticationUtil.getCurrentUser().getUserType()==UserType.Bank){
	        	 table.addCell(addHeader("Branch Code"));
	        }
	        table.addCell(addHeader("Target No"));
	        table.addCell(addHeader("Trans Id"));
	        table.addCell(addHeader("Amount"));
	        table.addCell(addHeader("Trans Type"));
	        table.addCell(addHeader("Status"));
	        table.addCell(addHeader("Message"));
	        
	        table.setHeaderRows(1);
	        if(transactionList!=null && !(transactionList.isEmpty())){
	        	for(Transaction transaction : transactionList){
	        		table.addCell(addCell(transaction.getCreated().toString().substring(0, 16)));
		        	if(transaction.getOriginatingUser().getUserType()== UserType.Customer){
		    			Customer customer = customerRepository.findOne(transaction.getOriginatingUser().getAssociatedId());
		    			if(customer.getMiddleName()==null || customer.getMiddleName().trim().equals("")){
		    				table.addCell(addCell(customer.getFirstName()+" "+customer.getLastName()));
		    			}else{
		    				table.addCell(addCell(customer.getFirstName()+" "+customer.getMiddleName()+" "+customer.getLastName()));
		    			}
		    			table.addCell(addCell(customer.getMobileNumber()));
		    		}else if(transaction.getOriginatingUser().getUserType()== UserType.Bank){
		    			Bank bank = bankRepository.findOne(transaction.getOriginatingUser().getAssociatedId());
		    			table.addCell(addCell(bank.getName()));
		    			table.addCell(addCell(bank.getMobileNumber()));
		    		}else if(transaction.getOriginatingUser().getUserType()== UserType.BankBranch){
		    			BankBranch branch = bankBranchRepository.findOne(transaction.getOriginatingUser().getAssociatedId());
		    			table.addCell(addCell(branch.getName()));
		    			table.addCell(addCell(branch.getMobileNumber()));
		    		}else{
		    			table.addCell("");
		    			table.addCell("");
		    		}
		        	table.addCell(addCell(transaction.getSourceBankAccount()));
		        	if(AuthenticationUtil.getCurrentUser().getUserType()==UserType.Admin){
		        		if(transaction.getBank()!=null){
			        		table.addCell(addCell(transaction.getBank().getSwiftCode()));
			    		}else{
			    			table.addCell("");
			    		}
		        	}else if(AuthenticationUtil.getCurrentUser().getUserType()==UserType.Bank){
		        		if(transaction.getBankBranch()!=null){
			        		table.addCell(addCell(transaction.getBankBranch().getBranchCode()));
			    		}else{
			    			table.addCell("");
			    		}
		        	}
		        	
		        	table.addCell(addCell(transaction.getDestination()));
		        	table.addCell(addCell(transaction.getTransactionIdentifier()));
		        	table.addCell(addCell(Double.toString(transaction.getAmount())));
		        	if(transaction.getMerchantManager()!=null){
		        		table.addCell(addCell(transaction.getMerchantManager().getMerchantsAndServices().getMerchantService().getService()));
		    		}else{
		    			table.addCell("");
		    		}
		        	table.addCell(addCell(transaction.getStatus().toString()));
		        	if(transaction.getResponseDetail()!=null){
		        		table.addCell(addCell(transaction.getResponseDetail().get("Result Message")));
		    		}else{
		    			table.addCell("");
		    		}

		        }
	        }else{
	        	table.addCell("");
	        	table.addCell("");
	        	table.addCell("");
	        	table.addCell("");
	        	table.addCell("");
	        	table.addCell("");
	        	table.addCell("");
	        	table.addCell("");
	        	table.addCell("");
	        	table.addCell("");
	        	table.addCell("");
	        }

            document.add(table);
			
			document.close();
			return file;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public String exportNonFinancialReport(List<NonFinancialTransaction> transactionList, String path){
        try {
        	String file = Long.toString(System.currentTimeMillis());
        	Document document = new Document();
			PdfWriter.getInstance(document, new FileOutputStream(path+"\\"+file+".pdf"));
			document.open();
			document.addTitle("Non-Financial Transaction Report");
			Paragraph heading = new Paragraph();
			heading.setAlignment(Paragraph.ALIGN_CENTER);
	        heading.add(new Paragraph("Non-Financial Transaction Report", catFont));
	        addEmptyLine(heading, 1);
	        document.add(heading);
	        
			PdfPTable table = new PdfPTable(8);
			if(AuthenticationUtil.getCurrentUser().getUserType()==UserType.BankBranch){
				table = new PdfPTable(7);
			}
			
			table.setWidthPercentage(100);
			
			table.addCell(addHeader("Date"));
	        table.addCell(addHeader("Cust Name"));
	        table.addCell(addHeader("Cust No"));
	        table.addCell(addHeader("A/C No"));
	        if(AuthenticationUtil.getCurrentUser().getUserType()==UserType.Admin){
	        	 table.addCell(addHeader("Bank Code"));
	        }else if(AuthenticationUtil.getCurrentUser().getUserType()==UserType.Bank){
	        	 table.addCell(addHeader("Branch Code"));
	        }
	        table.addCell(addHeader("Trans Id"));
	        table.addCell(addHeader("Trans Type"));
	        table.addCell(addHeader("Status"));
	        
	        table.setHeaderRows(1);
	        if(transactionList!=null && !(transactionList.isEmpty())){
	        	for(NonFinancialTransaction transaction : transactionList){
	        		table.addCell(addCell(transaction.getCreated().toString().substring(0,16)));
	        		if(transaction.getCustomer().getMiddleName()==null || transaction.getCustomer().getMiddleName().trim().equals("")){
	    				table.addCell(addCell(transaction.getCustomer().getFirstName()+" "+transaction.getCustomer().getLastName()));
	    			}else{
	    				table.addCell(addCell(transaction.getCustomer().getFirstName()+" "+transaction.getCustomer().getMiddleName()+" "+transaction.getCustomer().getLastName()));
	    			}
	        		table.addCell(addCell(transaction.getCustomer().getMobileNumber()));
	        		table.addCell(addCell(transaction.getAccountNumber()));
	        		if(AuthenticationUtil.getCurrentUser().getUserType()==UserType.Admin){
	        			table.addCell(addCell(transaction.getBank().getSwiftCode()));
	        		}else if(AuthenticationUtil.getCurrentUser().getUserType()==UserType.Bank){
	        			table.addCell(addCell(transaction.getBankBranch().getBranchCode()));
	        		}
	        		table.addCell(addCell(transaction.getTransactionId()));
	        		table.addCell(addCell(transaction.getTransactionType().toString()));
	        		table.addCell(addCell(transaction.getStatus().toString()));
		        }
	        }else{
	        	table.addCell("");
	        	table.addCell("");
	        	table.addCell("");
	        	table.addCell("");
	        	table.addCell("");
	        	table.addCell("");
	        	table.addCell("");
	        	table.addCell("");
	        }

            document.add(table);
			
			document.close();
			return file;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	public Document forSinglePageTicket(Document document, Passenger passenger, String transactionId,
			String reservationStatus) {

		try {

			Paragraph line1 = new Paragraph();
			addEmptyLine(line1, 1);

			document.add(getGreyTableRow("Booking Details"));

			PdfPTable tablePnr = new PdfPTable(2); // 2 columns
			tablePnr.setWidthPercentage(100);

			tablePnr.setWidths(new float[] { 30, 70 });

			tablePnr.addCell(addTableToCell(getPnrTable(passenger.getPnrNo(), passenger.getTicketNo())));
			tablePnr.addCell(
					addTableToCell(getIssueTable(passenger.getIssueDate(), transactionId, passenger.getNationality())));
			document.add(tablePnr);

			document.add(line1);

			document.add(getGreyTableRow("Passenger and Bill Details"));

			document.add(getPassengerNameTable(passenger.getFirstName(), passenger.getPaxType()));

			document.add(line1);

			document.add(getNprTable(passenger));

			document.add(getFlightTable(passenger, reservationStatus));

			document.add(getDepartureTable(passenger));

			document.add(getInfoTable(passenger));

			Paragraph line = new Paragraph();
			line.add(new Paragraph(
					"----------------------------------------------------------------------------------------------------------------------------------",
					catFontNormal));
			document.add(line);

			document.add(getGreyTableRow("Booking Details"));

			document.add(tablePnr);

			document.add(line1);

			document.add(getGreyTableRow("Passenger and Bill Details"));

			document.add(getPassengerNameTable(passenger.getFirstName(), passenger.getPaxType()));

			document.add(line1);

			document.add(getNprTable(passenger));

			document.add(getFlightTable(passenger, reservationStatus));

			document.add(getDepartureTable(passenger));

			document.add(getInfoTable(passenger));

			document.newPage();

			return document;

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}
	
	public PdfPTable getGreyTableRow(String data) {
		PdfPTable table = new PdfPTable(1);
		table.setWidthPercentage(100);
		PdfPCell bookingDetails = new PdfPCell(addHeaderLeftAlign(data));
		bookingDetails.setBorder(Rectangle.NO_BORDER);
		bookingDetails.setHorizontalAlignment(Element.ALIGN_LEFT);
		bookingDetails.setBackgroundColor(BaseColor.LIGHT_GRAY);
		table.addCell(bookingDetails);

		return table;
	}

	public PdfPTable getPnrTable(String pnr, String ticketNo) {
		PdfPTable table = new PdfPTable(1);
		table.setWidthPercentage(40);
		PdfPCell cellOne = new PdfPCell(new Phrase("PNR NO: " + pnr, catFontBoldRed));
		cellOne.setBorder(Rectangle.NO_BORDER);
		cellOne.setHorizontalAlignment(Element.ALIGN_LEFT);
		table.addCell(cellOne);

		PdfPCell cellTwo = new PdfPCell(new Phrase("Ticket No: " + ticketNo, catFontNormal));
		cellTwo.setBorder(Rectangle.NO_BORDER);
		cellTwo.setHorizontalAlignment(Element.ALIGN_LEFT);
		table.addCell(cellTwo);

		return table;
	}

	public PdfPTable getIssueTable(String issueDate, String tId, String nationality) throws Exception {
		PdfPTable table = new PdfPTable(2);
		table.setHorizontalAlignment(Element.ALIGN_LEFT);
		table.setWidthPercentage(60);
		table.setWidths(new float[] { 40, 60 });
		table.addCell(addCellNormal("Issue Date:"));
		table.addCell(addCellNormal(issueDate));
/*
		table.addCell(addCellNormal("Agency:"));
		table.addCell(addCellNormal("E-Net Payment (P) Ltd."));

		table.addCell(addCellNormal("Contact:"));
		table.addCell(addCellNormal("1660-01-44477(Toll Free)"));

		table.addCell(addCellNormal(""));
		table.addCell(addCellNormal("9779801143444(Mobile)"));*/

		table.addCell(addCellNormal("Issued By:"));
		table.addCell(addCellNormal("Mobile Banking System"));

		table.addCell(addHeaderLeftAlign("Ailrlines Ref Stan:"));
		table.addCell(addCellNormal(tId));

		table.addCell(addHeaderLeftAlign("Nationality:"));
		table.addCell(addCellNormal(nationality));

		return table;
	}

	public PdfPTable getPassengerNameTable(String passengerName, String paxType) throws Exception {
		/*
		 * PdfPTable table = new PdfPTable(1);
		 * table.setHorizontalAlignment(Element.ALIGN_LEFT);
		 * table.setWidthPercentage(100);
		 * 
		 * table.addCell(addCellNormal("Passenger Name: " + passengerName));
		 * table.addCell(addCellNormal("Passenger Type: " + paxType));
		 * 
		 * return table;
		 */

		PdfPTable table = new PdfPTable(2);
		table.setWidthPercentage(100);
		table.setWidths(new float[] { 75, 25 });
		table.addCell(addCellNormal("Passenger Name: " + passengerName));
		table.addCell(addCellNormal("Passenger Type: " + paxType));

		return table;
	}

	public PdfPTable getNprTable(Passenger passenger) {

		Double totalAmount = Double.parseDouble(passenger.getFare()) + Double.parseDouble(passenger.getSurcharge())
				+ Double.parseDouble(passenger.getTax());

		PdfPTable table2 = new PdfPTable(5);
		table2.setHorizontalAlignment(Element.ALIGN_LEFT);
		table2.setWidthPercentage(100);

		table2.addCell(addCellNormalCenter(passenger.getCurrency()));
		table2.addCell(addCellNormalCenter("FARE:" + passenger.getFare()));
		table2.addCell(addCellNormalCenter("FSC:" + passenger.getSurcharge()));
		table2.addCell(addCellNormalCenter("PSC:" + passenger.getTax()));
		table2.addCell(addCellNormalCenter("TOTAL:" + totalAmount));

		table2.setHeaderRows(1);

		table2.addCell(addCellNormalCenter("  "));
		table2.addCell(addCellNormalCenter("  "));
		table2.addCell(addCellNormalCenter("  "));
		table2.addCell(addCellNormalCenter("  "));
		table2.addCell(addCellNormalCenter("  "));

		return table2;
	}

	public PdfPTable getFlightTable(Passenger passenger, String reservationStatus) {
		PdfPTable table2 = new PdfPTable(6);
		table2.setHorizontalAlignment(Element.ALIGN_LEFT);
		table2.setWidthPercentage(100);

		table2.addCell(addCellNormalCenterBorderRed("FLIGHT NO."));
		table2.addCell(addCellNormalCenterBorderRed("DEP. DATE"));
		table2.addCell(addCellNormalCenterBorderRed("DEP. TIME"));
		table2.addCell(addCellNormalCenterBorderRed("SECTOR"));
		table2.addCell(addCellNormalCenterBorderRed("STATUS"));
		table2.addCell(addCellNormalCenterBorderRed("CL"));
		table2.setHeaderRows(1);

		table2.addCell(addCellNormalCenterBorderGray(passenger.getFlightNo()));
		table2.addCell(addCellNormalCenterBorderGray(passenger.getFlightDate()));
		table2.addCell(addCellNormalCenterBorderGray(passenger.getFlightTime()));
		table2.addCell(addCellNormalCenterBorderGray(passenger.getSector()));
		table2.addCell(addCellNormalCenterBorderGray(reservationStatus));
		table2.addCell(addCellNormalCenterBorderGray(passenger.getClassCode()));

		return table2;
	}

	public PdfPTable getDepartureTable(Passenger passenger) {
		PdfPTable table = new PdfPTable(2);
		table.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.setWidthPercentage(80);
		table.addCell(
				addCellMid("Departure:" + passenger.getDeparture() + ", Flight Time:" + passenger.getFlightTime()));
		table.addCell(addCellMid("Arrival:" + passenger.getArrival() + ", Arrival Time:" + passenger.getArrivalTime()));

		return table;
	}

	
/*	private String sectorName(String sectorCode){
		try{
			List<Sector> sectorCodes = airlineService.getSectorCode();
			
			if(sectorCodes != null && sectorCodes.size() > 0){
				for(Sector sector : sectorCodes){
					if(sector.getSectorCode().equals(sectorCode)){
						return sector.getSectorName();
					}
				}
			}
		}catch(Exception ex){
			
		}
		return sectorCode;
	}*/
	public PdfPTable getInfoTable(Passenger passenger) {
		PdfPTable table = new PdfPTable(1);
		table.setHorizontalAlignment(Element.ALIGN_LEFT);
		table.setWidthPercentage(60);
		table.addCell(addCellMid(" * " + passenger.getRefundable()));
		table.addCell(addCellMid(" * The passenger will be liable for any misuse of the ticket."));
		table.addCell(addCellMid(" * Airport Tax is included in the ticket."));
		table.addCell(addCellMid(" * REPORTING TIME: " + passenger.getReportingTime()));
		table.addCell(addCellMid(" * FREE BAGGAGE: " + passenger.getFreeBaggage()));
		table.addCell(addCellMid(" * NOT allowed to do a name change."));
		return table;
	}

	
	private PdfPCell addHeader(String header) {
		PdfPCell pdfCell = new PdfPCell(new Phrase(header, smallBold));
		pdfCell.setHorizontalAlignment(Element.ALIGN_CENTER);
		return pdfCell;
	}

	private PdfPCell addHeaderLeftAlign(String header) {
		PdfPCell pdfCell = new PdfPCell(new Phrase(header, catFont));
		pdfCell.setHorizontalAlignment(Element.ALIGN_LEFT);
		pdfCell.setBorder(Rectangle.NO_BORDER);
		return pdfCell;
	}

	private PdfPCell addHeaderCenterAlign(String header) {
		PdfPCell pdfCell = new PdfPCell(new Phrase(header, catFont));
		pdfCell.setHorizontalAlignment(Element.ALIGN_CENTER);
		pdfCell.setBorder(Rectangle.NO_BORDER);
		return pdfCell;
	}

	private PdfPCell addCell(String content) {
		PdfPCell pdfCell = new PdfPCell(new Phrase(content, small));
		pdfCell.setHorizontalAlignment(Element.ALIGN_LEFT);
		return pdfCell;
	}

	private PdfPCell addCellBordered(String content) {
		PdfPCell pdfCell = new PdfPCell(new Phrase(content, catFontNormal));
		pdfCell.setHorizontalAlignment(Element.ALIGN_LEFT);
		return pdfCell;
	}

	private PdfPCell addCellSmallNoBorder(String content) {
		PdfPCell pdfCell = new PdfPCell(new Phrase(content, small));
		pdfCell.setHorizontalAlignment(Element.ALIGN_LEFT);
		pdfCell.setBorder(Rectangle.NO_BORDER);
		return pdfCell;
	}

	private PdfPCell addCellNormal(String content) {
		PdfPCell pdfCell = new PdfPCell(new Phrase(content, catFontNormal));
		pdfCell.setHorizontalAlignment(Element.ALIGN_LEFT);
		pdfCell.setBorder(Rectangle.NO_BORDER);
		return pdfCell;
	}

	private PdfPCell addCellMid(String content) {
		PdfPCell pdfCell = new PdfPCell(new Phrase(content, catFont9));
		pdfCell.setHorizontalAlignment(Element.ALIGN_LEFT);
		pdfCell.setBorder(Rectangle.NO_BORDER);
		return pdfCell;
	}

	private PdfPCell addTableToCell(PdfPTable table) {
		PdfPCell pdfCell = new PdfPCell(table);
		pdfCell.setHorizontalAlignment(Element.ALIGN_LEFT);
		pdfCell.setBorder(Rectangle.NO_BORDER);
		return pdfCell;
	}
	
	private PdfPCell addTableToCellCenter(PdfPTable table) {
		PdfPCell pdfCell = new PdfPCell(table);
		pdfCell.setHorizontalAlignment(Element.ALIGN_CENTER);
		pdfCell.setBorder(Rectangle.NO_BORDER);
		return pdfCell;
	}

	private PdfPCell addCellNormalCenter(String content) {
		PdfPCell pdfCell = new PdfPCell(new Phrase(content, catFontNormal));
		pdfCell.setHorizontalAlignment(Element.ALIGN_CENTER);
		pdfCell.setBorder(Rectangle.NO_BORDER);
		return pdfCell;
	}

	private PdfPCell addCellNormalCenterBorderRed(String content) {
		PdfPCell pdfCell = new PdfPCell(new Phrase(content, catFontBoldWhite));
		pdfCell.setHorizontalAlignment(Element.ALIGN_CENTER);
		pdfCell.setBackgroundColor(BaseColor.RED);
		return pdfCell;
	}

	private PdfPCell addCellNormalCenterBorderGray(String content) {
		PdfPCell pdfCell = new PdfPCell(new Phrase(content, catFontNormal));
		pdfCell.setHorizontalAlignment(Element.ALIGN_CENTER);
		pdfCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
		return pdfCell;
	}

	private PdfPCell addCellNormalLeftGray(String content) {
		PdfPCell pdfCell = new PdfPCell(new Phrase(content, catFontNormal));
		pdfCell.setHorizontalAlignment(Element.ALIGN_LEFT);
		pdfCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
		pdfCell.setBorder(Rectangle.NO_BORDER);
		return pdfCell;
	}

	private void addEmptyLine(Paragraph paragraph, int number) {
		for (int i = 0; i < number; i++) {
			paragraph.add(new Paragraph(" "));
		}
	}
	
}
