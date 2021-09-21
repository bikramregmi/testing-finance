/**
 * 
 */
package com.wallet.iso8583.core;

import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.wallet.iso8583.schema.NavaJeevanIsoSchema;

/**
 * @author bibek
 *
 */
@Component
public class NavaJeevanIsoCore {
	
	public static HashMap<String, String> ISO_MESSAGE_RESPONSE = new HashMap<String, String>();
	public static HashMap<String, String> ISO_SCHEMA = new HashMap<String, String>();
	public static HashMap<String, String> ISO_UNPACKED_MESSAGE = new HashMap<String, String>();
	
	@SuppressWarnings("unused")
	public String packMessage(String mti, HashMap<String, String> isoFields) throws Exception {
		String finalPackedIso = "";
		NavaJeevanIsoSchema navaJeevanSchema = new NavaJeevanIsoSchema();
		ISO_SCHEMA = navaJeevanSchema.getIsoSchema();
		ArrayList<String> isoKeys = new ArrayList<String>();
		ArrayList<String> isoValues = new ArrayList<String>();
		ArrayList<Integer> finalFields = new ArrayList<Integer>();
		ISO_MESSAGE_RESPONSE.put("mti", mti);
		Iterator<?> iterator = isoFields.entrySet().iterator();
		while (iterator.hasNext()) {
			Map.Entry mapEntry = (Map.Entry) iterator.next();
			String isoKeyFields = mapEntry.getKey().toString();
//			System.out.println("isoKeyField=" + isoKeyFields);
			isoKeys.add(isoKeyFields);
			String schema = ISO_SCHEMA.get(isoKeyFields);
			if (schema == null) {
				throw new NullPointerException("Schema has no "+isoKeyFields+" defined. Please review data");
			}
			String[] schemaElements = schema.split("-");
			String dataType = schemaElements[0];
			String fieldLength = schemaElements[1];
			String maxLength = schemaElements[2];
			String fieldValue = isoFields.get(isoKeyFields);
//			System.out.println("fieldValue=" + fieldValue);
			if (dataType.equalsIgnoreCase("numeric") || dataType.equalsIgnoreCase("num")) {
				if (fieldValue.length() > Integer.parseInt(maxLength)) {
					throw new IOException("Maximum Length exceeded for " + isoKeyFields);
				}
				if (Integer.parseInt(fieldLength) > 0 && fieldValue.length() < Integer.parseInt(maxLength)) {
					int fieldLengthValue = fieldLength.length();
					if (Integer.parseInt(fieldLength) == 2) {
						if (fieldValue.length() < 10) {
							//add leading 0 in fieldLengthValue
							String fieldLengthWithZero = "0" + String.valueOf(fieldValue.length());
							//String newFieldLength = String.format("%0"+ fieldLengthWithZero + "d",  Long.parseLong(fieldLengthWithZero.toString()));
							ISO_MESSAGE_RESPONSE.put(isoKeyFields, fieldLengthWithZero + fieldValue);
						} else {
							//no need to add leading 0
							String newFieldLength = String.valueOf(fieldValue.length());
							ISO_MESSAGE_RESPONSE.put(isoKeyFields, newFieldLength + fieldValue);
						}
					} else if (Integer.parseInt(fieldLength) == 3) {
						if (fieldValue.length() < 10) {
							//add two leading 0s
							String fieldLengthWithZero = "00" + String.valueOf(fieldValue.length());
							String newFieldLength = String.format("%0"+ fieldLengthWithZero + "d",  Long.parseLong(fieldLengthWithZero.toString()));
							ISO_MESSAGE_RESPONSE.put(isoKeyFields, newFieldLength + fieldValue);
						} else if (fieldValue.length() < 100) {
							//add one lading 0
							String fieldLengthWithZero = "0" + String.valueOf(fieldValue.length());
							String newFieldLength = String.format("%0"+ fieldLengthWithZero + "d",  Long.parseLong(fieldLengthWithZero.toString()));
							ISO_MESSAGE_RESPONSE.put(isoKeyFields, newFieldLength + fieldValue);
						} else {
							//no need to add leading 0
							String newFieldLength = String.format("%0"+ fieldLength +"d", Long.parseLong(fieldLength.toString()));
							ISO_MESSAGE_RESPONSE.put(isoKeyFields, newFieldLength + fieldValue);
						}
						
					} 
				} else {
					String newFieldValue="";
					if (newFieldValue.equals("")) {
			    		newFieldValue = String.format("%0"+ maxLength +"d", Long.parseLong(fieldValue));
					} else if (Integer.parseInt(maxLength) == fieldValue.length()) {
			    		newFieldValue = fieldValue;
			    	} else {
			    		newFieldValue = String.format("%0" + maxLength + "d", Long.parseLong(fieldValue));
			    	}
			    	
			    	ISO_MESSAGE_RESPONSE.put(isoKeyFields, newFieldValue);
				}
			} else if (dataType.equalsIgnoreCase("char")) {
				if (fieldValue.length() > Integer.parseInt(maxLength)) {
					throw new IOException("Maximum Length exceeded for " + isoKeyFields);
				}
				if (Integer.parseInt(fieldLength) > 0 && fieldValue.length() < Integer.parseInt(maxLength)) {
					Integer fieldValueLength = fieldValue.length();
					String newFieldLength = String.valueOf(fieldValueLength);
					if(Integer.parseInt(fieldLength) == 3 && newFieldLength.length() < 3) {
						if(newFieldLength.length() == 1) {
							newFieldLength = "00"+newFieldLength;
						} else if (newFieldLength.length() == 2) {
							newFieldLength = "0"+newFieldLength;
						}
					}else if(Integer.parseInt(fieldLength) == 2){
						if(newFieldLength.length() == 1) {
							newFieldLength = "0"+newFieldLength;
						} 
					}
					ISO_MESSAGE_RESPONSE.put(isoKeyFields, newFieldLength + fieldValue);
				} else {
					String newFieldValue = String.format("%1$-"+maxLength+"s", fieldValue.toString()).replace(' ' , '.');
					ISO_MESSAGE_RESPONSE.put(isoKeyFields, newFieldValue);
				}
			}
		}
		
		finalFields = processBitMapFields(parseIsoFields(isoKeys));
		String isoMessage = buildIsoMessage(finalFields);
		String primaryPackedBitMap;
      	String primaryBitMap;
      	String messageAfterBitMap;
      	String secondaryPackedBitMap;
		String secondaryBitMap = null;
		String tertiaryPackedBitMap;
		String tertiaryBitMap = null;
		
		primaryPackedBitMap = isoMessage.substring(4,12);
		messageAfterBitMap = isoMessage.substring(12);
		primaryBitMap = getBitMapFromHex(primaryPackedBitMap, 1);
		finalPackedIso = mti + primaryBitMap;
		Integer firstBit = Integer.parseInt(primaryBitMap.substring(0,1));
		if (firstBit == 1) {
			secondaryPackedBitMap = isoMessage.substring(12,20);
			messageAfterBitMap = isoMessage.substring(20);
			secondaryBitMap = getBitMapFromHex(secondaryPackedBitMap, 1);
			firstBit = Integer.parseInt(secondaryBitMap.substring(0, 1));
			if (firstBit == 1) {
				tertiaryPackedBitMap = isoMessage.substring(20,28);
				messageAfterBitMap = isoMessage.substring(28);
				tertiaryBitMap = getBitMapFromHex(tertiaryPackedBitMap, 1);
				finalPackedIso = finalPackedIso + tertiaryBitMap;
			} else {
				finalPackedIso = finalPackedIso + secondaryBitMap;
			}
			
		}
		String hexIso = convertBinaryToHex(finalPackedIso.substring(4));
		int length = (mti+hexIso+messageAfterBitMap).length();
		System.out.println("LENGTH " + length);
		String len = "";
		if (length < 100) {
			len = "00" + String.valueOf(length);
		} else if (length < 1000) {
			len = "0" + String.valueOf(length);
		} else {
			len =  String.valueOf(length);
		}
		finalPackedIso = len + mti + hexIso+messageAfterBitMap;
 		return finalPackedIso;
	}
	
	
	public static String buildIsoMessage(ArrayList<Integer> finalIsoFields)
	{
		String isoMessage = ISO_MESSAGE_RESPONSE.get("mti");
		isoMessage = isoMessage + ISO_MESSAGE_RESPONSE.get("1");
		//System.out.println(isoMessage + "bild");
		Iterator<?> iterator = finalIsoFields.iterator();
		while (iterator.hasNext()) {
			String dataElement = iterator.next().toString();
			String schema = ISO_SCHEMA.get(dataElement);
			try {
				String schemaArray[] = schema.split("-");
				String fieldLengthType = schemaArray[1];
				String subFieldIndicator = schemaArray[3];
				String subFieldArray[] = schemaArray[3].split("_");
				String hasSubField = subFieldArray[0];
				if (subFieldArray[0].equalsIgnoreCase("1")) {
					ArrayList<Integer> subFields = new ArrayList<Integer> ();
					 
				} else {
					isoMessage = isoMessage + ISO_MESSAGE_RESPONSE.get(dataElement);
				}
				
			} catch (Exception e) {
				//isoMessage = isoMessage + ISO_MESSAGE_RESPONSE.get(dataElement);
			}
		}
		return isoMessage;
	}
	
	public static ArrayList<Integer> parseIsoFields(ArrayList<String> isoFields)throws Exception
	{
		ArrayList<Integer> newFieldsMap = new ArrayList<Integer>();
		Iterator<?> iterator = isoFields.iterator();
		while (iterator.hasNext()) { //sub field with . split left to work out
			newFieldsMap.add(Integer.parseInt(iterator.next().toString()));
		}
		Collections.sort(newFieldsMap);
		return newFieldsMap;
	}
	
	public static String hexToBinary(String hexIso, String bitMapType) {
		String binary = "";
		String tempBinary = new BigInteger(hexIso, 16).toString(2);
		String formatBit = "%64s";
		switch (bitMapType) {
			case "p": formatBit = "%64s";break;
			case "s" : formatBit = "%128s"; break;
			case "t" : formatBit = "%192s"; break;
			default : formatBit = "%64s"; break;
		}
		binary = String.format(formatBit, tempBinary).replace(" ", "0");
		return binary;
	}
	
	public String convertHexToString(String hex){

		  StringBuilder sb = new StringBuilder();
		  StringBuilder temp = new StringBuilder();

		  //49204c6f7665204a617661 split into two characters 49, 20, 4c...
		  for( int i=0; i<hex.length()-1; i+=2 ){

		      //grab the hex in pairs
		      String output = hex.substring(i, (i + 2));
		      //convert hex to decimal
		      int decimal = Integer.parseInt(output, 16);
		      //convert the decimal to character
		      sb.append((char)decimal);

		      temp.append(decimal);
		  }
		  System.out.println("Decimal : " + temp.toString());

		  return sb.toString();
	  }
	
	public String convertAsciiToHex(String ascii){
		  char[] chars = ascii.toCharArray();

		  StringBuffer hex = new StringBuffer();
		  for(int i = 0; i < chars.length; i++){
		    hex.append(Integer.toHexString((int)chars[i]));
		  }

		  return hex.toString();
		
	}

	
	public static ArrayList<Integer> processBitMapFields(ArrayList<Integer> isoFields) throws Exception
	{
		String bitMapType = "primary";
		char[] bitMap;
		//highest value of elements
		Integer dataElements = isoFields.get(isoFields.size() - 1);
		if (dataElements > 128) {
			isoFields.add(1); //Add 1st Bit for secondary bitmap
			isoFields.add(65); //Add 65th Bit for tertiary bitmap
			ISO_MESSAGE_RESPONSE.put("65", "1");
			bitMapType = "tertiary";
			bitMap = new char[24];
		} else if (dataElements < 128 && dataElements >64) {
			isoFields.add(1);
			bitMapType = "secondary";
			bitMap = new char[16];
		} else {
			//System.out.println("primary");
			bitMapType = "primary";
			bitMap = new char[8];;
		}
		
		Collections.sort(isoFields);
		appendBitMap(bitMap, isoFields);
		ISO_MESSAGE_RESPONSE.put("1", String.valueOf(bitMap, 0, bitMap.length));
		return isoFields;
	}
	
	public static String appendBitMap(char[] bitMap, ArrayList<Integer> isoFields) throws Exception
	{
		for (int i=0;i<isoFields.size();i++) {
			int iPos = (isoFields.get(i) / 8);
			int iPosIn = isoFields.get(i) - (iPos * 8);
			if(iPosIn == 0){
				iPos--;
				iPosIn = 8;
			}	
			if((65 <= isoFields.get(i))  && (isoFields.get(i) < 128))
				bitMap[ 0 ] |= 1 << 7;
			if((128 <= isoFields.get(i))  && (isoFields.get(i) < 192))
				bitMap[ 8 ] |= 1 << 7;
				
			bitMap[ iPos ] |= 1 << (8 - iPosIn);
		}
		return "Ok";
	}
	
	public static String convertBinaryToHex(String isoBinary)
	{
		int digitNumber = 1;
	    int sum = 0;
	    String resultHex = "";
	    for(int i = 0; i < isoBinary.length(); i++){
	        if(digitNumber == 1)
	            sum+=Integer.parseInt(isoBinary.charAt(i) + "")*8;
	        else if(digitNumber == 2)
	            sum+=Integer.parseInt(isoBinary.charAt(i) + "")*4;
	        else if(digitNumber == 3)
	            sum+=Integer.parseInt(isoBinary.charAt(i) + "")*2;
	        else if(digitNumber == 4 || i < isoBinary.length()+1){
	            sum+=Integer.parseInt(isoBinary.charAt(i) + "")*1;
	            digitNumber = 0;
	            if(sum < 10)
	                resultHex+=sum;
	            else if(sum == 10)
	            	resultHex+='A';
	            else if(sum == 11)
	            	resultHex+='B';
	            else if(sum == 12)
	            	resultHex+='C';
	            else if(sum == 13)
	            	resultHex+='D';
	            else if(sum == 14)
	            	resultHex+='E';
	            else if(sum == 15)
	            	resultHex+='F';
	            sum=0;
	        }
	        digitNumber++;  
	    }
	    return resultHex;
	    
	}
	
	public static String getBitMapFromHex(String hexBit, int number)
	{
		StringBuffer stringBuffer = new StringBuffer(64);
		
		for(int i = 0; i < 8; i++){
			if((hexBit.charAt(i) & 128) > 0) stringBuffer.append('1'); else stringBuffer.append('0');
			if((hexBit.charAt(i) & 64) > 0) stringBuffer.append('1'); else stringBuffer.append('0');
			if((hexBit.charAt(i) & 32) > 0) stringBuffer.append('1'); else stringBuffer.append('0');
			if((hexBit.charAt(i) & 16) > 0) stringBuffer.append('1'); else stringBuffer.append('0');
			if((hexBit.charAt(i) & 8) > 0) stringBuffer.append('1'); else stringBuffer.append('0');
			if((hexBit.charAt(i) & 4) > 0) stringBuffer.append('1'); else stringBuffer.append('0');
			if((hexBit.charAt(i) & 2) > 0) stringBuffer.append('1'); else stringBuffer.append('0');
			if((hexBit.charAt(i) & 1) > 0) stringBuffer.append('1'); else stringBuffer.append('0');
		}
		
		return stringBuffer.toString();
	}
	
	public static HashMap<String, String> unpackMessage(String isoMessage) {
		ISO_UNPACKED_MESSAGE.clear();
		int bitMapLength = 64;
		NavaJeevanIsoSchema navaJeevanIsoSchema = new NavaJeevanIsoSchema();
		ISO_SCHEMA = navaJeevanIsoSchema.getIsoSchema();
		String overAllBitMap = null;
		String messageAfterBitMap = null;
		String primaryPackedBitMap = "";
		String secondaryPackedBitMap = "";
		String tertiaryPackedBitMap = "";
		String primaryBitMap = "";
		String secondaryBitMap = "";
		String tertiaryBitMap = "";
		String remainingMessage = null;
		isoMessage = isoMessage.substring(4); // discard the length in the message
		
		ISO_UNPACKED_MESSAGE.put("mti", isoMessage.substring(0, 4));
		primaryPackedBitMap = isoMessage.substring(4,20);
		primaryBitMap = primaryPackedBitMap;
		primaryBitMap = hexToBinary(primaryPackedBitMap, "p");
		overAllBitMap = primaryBitMap;
		Integer firstBit = Integer.parseInt(primaryBitMap.substring(0,1));
		if (firstBit > 0) { //secondary bitmap
			bitMapLength = 128;
			secondaryPackedBitMap = isoMessage.substring(20,36);
			messageAfterBitMap = isoMessage.substring(36);
			secondaryBitMap = secondaryPackedBitMap;
			secondaryBitMap = hexToBinary(secondaryPackedBitMap, "s");
			overAllBitMap = overAllBitMap + secondaryBitMap.substring(64);
			firstBit = Integer.parseInt(secondaryBitMap.substring(0, 1));
			if (firstBit > 0) {
				//tertiary bitmap
				bitMapLength = 192;
				tertiaryPackedBitMap = isoMessage.substring(36,52);
				messageAfterBitMap = isoMessage.substring(52);
				tertiaryBitMap = tertiaryPackedBitMap;
				tertiaryBitMap = hexToBinary(tertiaryPackedBitMap, "t");
				overAllBitMap = overAllBitMap + tertiaryBitMap.substring(128);
			}
		} else {
			messageAfterBitMap = isoMessage.substring(20);
		}
		
		for (int i=0; i<bitMapLength-1;i++) {
			Integer field = i + 1;
			char bit = overAllBitMap.charAt(i);
			//field 1 is BITMAP so proceed to other fields
			if (bit == '1') {
				String dataType = null;
				String fieldLengthType = null;
				String fieldMaxLength = null;
				String schema = null;
				try {
					schema = ISO_SCHEMA.get(""+field);
					
				} catch (Exception e) {
					e.printStackTrace();
				}
				if (schema != null) {
					try {
						String schemaArray[] = schema.split("-");
						dataType = schemaArray[0];
						fieldLengthType = schemaArray[1];
						fieldMaxLength = schemaArray[2];
						
					} catch (Exception e) {
						
					}
					
					try {
						
						if (dataType.equalsIgnoreCase("numeric")) {
						
							if (remainingMessage == null) {
								
								if (Integer.parseInt(fieldLengthType) > 0) {
									Integer fieldLen =Integer.parseInt(messageAfterBitMap.substring(0,Integer.parseInt(fieldLengthType))) ;
									messageAfterBitMap = messageAfterBitMap.substring(Integer.parseInt(fieldLengthType));
									String fieldValue = messageAfterBitMap.substring(0, fieldLen);
									ISO_UNPACKED_MESSAGE.put(field.toString(), fieldValue);
									remainingMessage = messageAfterBitMap.substring(fieldLen);
								} else {
									String fieldValue = messageAfterBitMap.substring(0, Integer.parseInt(fieldMaxLength));
									ISO_UNPACKED_MESSAGE.put(field.toString(), fieldValue);
									remainingMessage = messageAfterBitMap.substring(Integer.parseInt(fieldMaxLength));

								}
							} else {
								if (Integer.parseInt(fieldLengthType) > 0) {
									
									Integer fieldLen =Integer.parseInt(remainingMessage.substring(0,Integer.parseInt(fieldLengthType))) ;
									/*if (field == 102) {
										System.out.println(fieldLen + " FIELD LENGTH");
										System.out.println(fieldLengthType + " FIELD LENGTH TYPE");
									}*/
									remainingMessage = remainingMessage.substring(Integer.parseInt(fieldLengthType));
									String fieldValue = remainingMessage.substring(0, fieldLen);
									ISO_UNPACKED_MESSAGE.put(field.toString(), fieldValue);
									remainingMessage = remainingMessage.substring(fieldLen);
								} else {
									String fieldValue = remainingMessage.substring(0, Integer.parseInt(fieldMaxLength));
									ISO_UNPACKED_MESSAGE.put(field.toString(), fieldValue);
									remainingMessage = remainingMessage.substring(Integer.parseInt(fieldMaxLength));
								}
								
							}
						} else if (dataType.equalsIgnoreCase("char") || dataType.equalsIgnoreCase("num")) {
							
							if (remainingMessage == null) {
								String fieldLength = messageAfterBitMap.substring(0, Integer.parseInt(fieldLengthType));
								remainingMessage = messageAfterBitMap.substring(Integer.parseInt(fieldLengthType));
								String fieldValue = remainingMessage.substring(0, Integer.parseInt(fieldLength));
								ISO_UNPACKED_MESSAGE.put(field.toString(), fieldValue);
								remainingMessage = remainingMessage.substring(Integer.parseInt(fieldLength));
							} else {
								if (fieldLengthType.equalsIgnoreCase("0")) {
									String fieldValue = remainingMessage.substring(0,Integer.parseInt(fieldMaxLength));
									remainingMessage = remainingMessage.substring(Integer.parseInt(fieldMaxLength));
									ISO_UNPACKED_MESSAGE.put(field.toString(), fieldValue);
								}else if (fieldLengthType.equalsIgnoreCase(fieldMaxLength)) {
									String fieldValue = remainingMessage.substring(0,Integer.parseInt(fieldLengthType));
									remainingMessage = remainingMessage.substring(Integer.parseInt(fieldLengthType));
									ISO_UNPACKED_MESSAGE.put(field.toString(), fieldValue);
								} else {
									String fieldLength = remainingMessage.substring(0, Integer.parseInt(fieldLengthType));
									remainingMessage = remainingMessage.substring(Integer.parseInt(fieldLengthType));
									
									String fieldValue = remainingMessage.substring(0,Integer.parseInt(fieldLength));
									if(field==54 && fieldValue.equals("")){
										 fieldLength = remainingMessage.substring(0, Integer.parseInt(fieldLengthType));
										 remainingMessage = remainingMessage.substring(Integer.parseInt(fieldLengthType));
										 fieldValue = remainingMessage.substring(0,Integer.parseInt(fieldLength));
									}
									remainingMessage = remainingMessage.substring(Integer.parseInt(fieldLength));
									
									ISO_UNPACKED_MESSAGE.put(field.toString(), fieldValue);
								}
								
								
								
								
							}
						}
					} catch (Exception e) {
						System.out.println(" "+field + " FIELD FOR EXCEPTION");
						e.printStackTrace();
					}
				}
				
				
				
			}
		}
		return ISO_UNPACKED_MESSAGE;
	}
}